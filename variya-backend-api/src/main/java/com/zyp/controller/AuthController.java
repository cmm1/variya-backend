package com.zyp.controller;


import com.zyp.core.ResponseResult;
import com.zyp.pojo.dto.UserDTO;
import com.zyp.service.impl.SecurityUserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Resource
    private SecurityUserServiceImpl securityUserService;



    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDTO userDTO) {
        return ResponseResult.responseSuccess(securityUserService.login(userDTO));
    }

    @GetMapping ("/logout")
    public ResponseResult logout(Long id) {
        securityUserService.logout(id);
        return ResponseResult.responseSuccess();
    }

    @PostMapping("/saveUser")
    public ResponseResult saveUser(@RequestBody  UserDTO userDTO) {
        securityUserService.saveUser(userDTO);
        return ResponseResult.responseSuccess();
    }

    @PostMapping("/updateUser")
    public ResponseResult updateUser(@RequestBody UserDTO userDTO) {
        securityUserService.updateUser(userDTO);
        return ResponseResult.responseSuccess();
    }

    @GetMapping("/getUserList")
    public ResponseResult getUserList() {
        return ResponseResult.responseSuccess(securityUserService.getUserList());
    }

    @GetMapping("/getUserListByRoleId")
    public ResponseResult getUserListByRoleId(@RequestParam("roleId") Long roleId) {
        return ResponseResult.responseSuccess(securityUserService.getUserListByRoleId(roleId));
    }

    @GetMapping("/delUser")
    public ResponseResult delUser(Long id){
        securityUserService.delUser(id);
        return ResponseResult.responseSuccess();
    }


}
