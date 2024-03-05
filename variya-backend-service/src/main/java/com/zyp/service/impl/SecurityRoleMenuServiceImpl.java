package com.zyp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyp.entity.SecurityRoleMenu;
import com.zyp.service.SecurityRoleMenuService;
import com.zyp.mapper.SecurityRoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author zyp
* @description 针对表【security_role_menu(角色菜单中间表;)】的数据库操作Service实现
* @createDate 2024-03-05 22:05:24
*/
@Service
public class SecurityRoleMenuServiceImpl extends ServiceImpl<SecurityRoleMenuMapper, SecurityRoleMenu>
    implements SecurityRoleMenuService{

}




