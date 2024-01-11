package com.zyp;

import com.zyp.pojo.dto.UserDTO;
import com.zyp.service.SecurityUserService;
import com.zyp.utils.AesUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    public void saveUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin");
        userDTO.setNickName("超级管理员");
        userDTO.setPassword(AesUtil.encrypt("123456","385f33cb91484b04a177828829081ab7"));
        userDTO.setRoleId(1l);
        securityUserService.saveUser(userDTO);
    }
}
