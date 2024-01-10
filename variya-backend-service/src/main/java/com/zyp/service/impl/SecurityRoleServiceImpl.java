package com.zyp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zyp.mapper.SecurityRoleMapper;
import com.zyp.pojo.entity.SecurityRole;
import com.zyp.service.SecurityRoleService;
import org.springframework.stereotype.Service;

/**
* @author zyp
* @description 针对表【security_role】的数据库操作Service实现
* @createDate 2023-04-25 17:27:30
*/
@Service
public class SecurityRoleServiceImpl extends ServiceImpl<SecurityRoleMapper, SecurityRole>
    implements SecurityRoleService {

}




