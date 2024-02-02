package com.zyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyp.consts.CacheKeyConst;
import com.zyp.mapper.SecurityUserMapper;
import com.zyp.pojo.dto.UserDTO;
import com.zyp.pojo.entity.SecurityUser;
import com.zyp.pojo.entity.SecurityUserRole;
import com.zyp.pojo.vo.UserVO;
import com.zyp.service.SecurityRoleService;
import com.zyp.service.SecurityUserRoleService;
import com.zyp.service.SecurityUserService;
import com.zyp.service.utils.JwtUtil;
import com.zyp.utils.AesUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author zyp
* @description 针对表【security_user】的数据库操作Service实现
* @createDate 2023-04-25 17:28:05
*/
@Service
@Slf4j
public class SecurityUserServiceImpl extends ServiceImpl<SecurityUserMapper, SecurityUser> implements SecurityUserService, UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SecurityUserRoleService securityUserRoleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        queryWrapper.eq("enabled", true);
        SecurityUser securityUser = this.getOne(queryWrapper);
        if (Objects.isNull(securityUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return securityUser;
    }

    @Override
    public UserVO login(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        String password = userDTO.getPassword();
        //解密 SHA256
        try {
            password = AesUtil.decrypt(userDTO.getPassword(),"385f33cb91484b04a177828829081ab7");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadCredentialsException("用户名或密码错误");
        }
        // 创建Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getUsername() ,password);
        // 调用AuthenticationManager的authenticate方法进行认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(authentication == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        //在线
        securityUser.setStatus(1);
        this.updateById(securityUser);
        securityUser.setPassword(null);
        redissonClient.getBucket(CacheKeyConst.USER_INFO + securityUser.getId()).set(securityUser);
        String token = JwtUtil.createToken(securityUser);
        redissonClient.getBucket(CacheKeyConst.USER_TOKEN + securityUser.getId()).set(token);
        BeanUtils.copyProperties(securityUser, userVO);
        userVO.setToken(token);
        userVO.setStatus(1);
        userVO.setRoles(extracted(securityUser.getId()));;
        return userVO;
    }

    private List<Long> extracted(Long id) {
        //获取用户角色
        List<Long> roles = new ArrayList<>();
        List<SecurityUserRole> securityUserRoleList = securityUserRoleService.list(new QueryWrapper<SecurityUserRole>().eq("user_id", id));
        if(!CollectionUtils.isEmpty(securityUserRoleList)){
            securityUserRoleList.stream().forEach(securityUserRole -> {
                roles.add(securityUserRole.getRoleId());
            });
        }
        return roles;
    }


    @Override
    public void saveUser(UserDTO userDTO) {
        String password = userDTO.getPassword();
        //解密 SHA256
        try {
            password = AesUtil.decrypt(userDTO.getPassword(),"385f33cb91484b04a177828829081ab7");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadCredentialsException("用户名或密码错误");
        }
        //添加用户
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(userDTO.getUsername());
        securityUser.setNickName(userDTO.getNickName());
        securityUser.setPassword(bCryptPasswordEncoder.encode(password));
        SecurityUser securityUserContext = null;
        try {
            securityUserContext = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            securityUser.setCreater(securityUserContext.getUsername());
        } catch (Exception e) {
            log.error("单元测试会异常");
            securityUser.setCreater("superAdmin");
        }
        securityUser.setCreateTime(new Date());
        securityUser.setAccountNonExpired(true);
        securityUser.setAccountNonLocked(true);
        securityUser.setCredentialsNonExpired(true);
        securityUser.setEnabled(true);
        this.save(securityUser);
        //添加用户角色关系
        SecurityUserRole securityUserRole = new SecurityUserRole();
        securityUserRole.setUserId(securityUser.getId());
        securityUserRole.setRoleId(userDTO.getRoleId());
        securityUserRoleService.save(securityUserRole);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        //更新用户
        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(userDTO, securityUser);
        if(!Objects.isNull(userDTO.getPassword())) {
            //解密 SHA256
            try {
                String password = AesUtil.decrypt(userDTO.getPassword(),"385f33cb91484b04a177828829081ab7");
                securityUser.setPassword(bCryptPasswordEncoder.encode(password));
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new BadCredentialsException("用户名或密码错误");
            }
        }
        SecurityUser securityUser1 = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        securityUser.setUpdater(securityUser1.getUsername());
        securityUser.setUpdateTime(new Date());
        securityUser.setAccountNonExpired(true);
        securityUser.setAccountNonLocked(true);
        securityUser.setCredentialsNonExpired(true);
        securityUser.setEnabled(true);
        this.updateById(securityUser);
        //更新用户角色关系
        SecurityUserRole securityUserRole = new SecurityUserRole();
        securityUserRole.setUserId(securityUser.getId());
        securityUserRole.setRoleId(userDTO.getRoleId());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", securityUser.getId());
        SecurityUserRole one = securityUserRoleService.getOne(queryWrapper);

        if(Objects.isNull(one)) {
            securityUserRole.setRoleId(userDTO.getRoleId());
            securityUserRoleService.save(securityUserRole);
            return;
        }
        one.setRoleId(userDTO.getRoleId());
        securityUserRoleService.updateById(one);
    }

    @Override
    public void logout(Long id) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //在线
        securityUser.setStatus(0);
        this.updateById(securityUser);
        redissonClient.getBucket(CacheKeyConst.USER_INFO + id).delete();
        redissonClient.getBucket(CacheKeyConst.USER_TOKEN + id).delete();
    }

    @Override
    public List<UserDTO> getUserList() {
        List<UserDTO> dataList = new ArrayList<>();
        List<SecurityUser> list = this.list();
        if(!CollectionUtils.isEmpty(list)) {
            list.forEach(d->{
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(d, userDTO);
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("user_id", d.getId());
                SecurityUserRole one = securityUserRoleService.getOne(queryWrapper);
                if(!Objects.isNull(one)) {
                    userDTO.setRoleId(one.getRoleId());
                }
                dataList.add(userDTO);
            });
        }
        return dataList;
    }

    @Override
    public List<UserDTO> getUserListByRoleId(Long roleId) {
        List<UserDTO> dataList = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        List<SecurityUserRole> list = securityUserRoleService.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)) {
            list.forEach(d->{
                SecurityUser securityUser = this.getById(d.getUserId());
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(securityUser, userDTO);
                userDTO.setRoleId(d.getRoleId());
                dataList.add(userDTO);
            });
        }
        return dataList;
    }

    @Override
    public void delUser(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        this.remove(queryWrapper);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("user_id",id);
        securityUserRoleService.remove(queryWrapper1);
    }

//    @Override
//    public UserDTO infos(String authorization) {
//        Claims claims = JwtUtil.parseToken(authorization);
//        String subject = claims.getSubject();
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("id",subject);
//
//        return new UserDTO();
//    }
}




