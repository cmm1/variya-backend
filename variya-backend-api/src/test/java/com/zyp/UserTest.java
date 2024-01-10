package com.zyp;

import com.zyp.pojo.dto.UserDTO;
import com.zyp.service.SecurityUserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    public void saveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin");
        userDTO.setNickName("超级管理员");
        userDTO.setPassword(bCryptPasswordEncoder.encode("123456"));
        userDTO.setRoleId(1l);
        securityUserService.saveUser(userDTO);
    }
}
