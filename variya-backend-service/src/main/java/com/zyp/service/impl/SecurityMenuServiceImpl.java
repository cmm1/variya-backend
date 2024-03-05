package com.zyp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyp.entity.SecurityMenu;
import com.zyp.service.SecurityMenuService;
import com.zyp.mapper.SecurityMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author zyp
* @description 针对表【security_menu(菜单表)】的数据库操作Service实现
* @createDate 2024-03-04 21:27:51
*/
@Service
public class SecurityMenuServiceImpl extends ServiceImpl<SecurityMenuMapper, SecurityMenu>
    implements SecurityMenuService{

}




