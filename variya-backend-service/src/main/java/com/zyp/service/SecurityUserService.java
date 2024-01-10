package com.zyp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyp.pojo.dto.UserDTO;
import com.zyp.pojo.entity.SecurityUser;
import com.zyp.pojo.vo.UserVO;


import java.util.List;

/**
* @author zyp
* @description 针对表【security_user】的数据库操作Service
* @createDate 2023-04-25 17:28:05
*/
public interface SecurityUserService extends IService<SecurityUser> {

    UserVO login(UserDTO userDTO);
    void saveUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void logout(Long id);

    List<UserDTO> getUserList();

    List<UserDTO> getUserListByRoleId(Long roleId);


    void delUser(Long id);
}
