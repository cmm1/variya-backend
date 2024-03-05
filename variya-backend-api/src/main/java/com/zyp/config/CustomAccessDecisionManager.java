package com.zyp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.List;
@Configuration
public class CustomAccessDecisionManager implements AccessDecisionManager {



    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null || configAttributes.isEmpty()) {
            return;
        }

        for (ConfigAttribute attribute : configAttributes) {
            String neededPermission = attribute.getAttribute();
            // 查询数据库获取用户的权限信息
            List<String> userPermissions = null;
            return;
            // 进行权限匹配
//            if (userPermissions.contains(neededPermission)) {
//                return; // 允许访问
//            }
        }

        throw new AccessDeniedException("Access Denied");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
