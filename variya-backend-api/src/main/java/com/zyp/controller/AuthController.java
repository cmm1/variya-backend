package com.zyp.controller;


import com.zyp.core.ResponseResult;
import com.zyp.pojo.dto.UserDTO;
import com.zyp.service.impl.SecurityUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/auth")
@RestController
@Api(tags = "认证相关api")
public class AuthController {

    @Resource
    private SecurityUserServiceImpl securityUserService;



    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseResult login(@RequestBody UserDTO userDTO) {
        return ResponseResult.responseSuccess(securityUserService.login(userDTO));
    }

    @GetMapping ("/logout")
    @ApiOperation("登出")
    public ResponseResult logout(Long id) {
        securityUserService.logout(id);
        return ResponseResult.responseSuccess();
    }

    @PostMapping("/saveUser")
    @ApiOperation("注册")
    public ResponseResult saveUser(@RequestBody  UserDTO userDTO) {
        securityUserService.saveUser(userDTO);
        return ResponseResult.responseSuccess();
    }

    @PostMapping("/updateUser")
    @ApiOperation("修改")
    public ResponseResult updateUser(@RequestBody UserDTO userDTO) {
        securityUserService.updateUser(userDTO);
        return ResponseResult.responseSuccess();
    }

    @GetMapping("/getUserList")
    @ApiOperation("获取所有用户")
    public ResponseResult getUserList() {
        return ResponseResult.responseSuccess(securityUserService.getUserList());
    }

    @GetMapping("/getUserListByRoleId")
    @ApiOperation("根据角色获取用户")
    public ResponseResult getUserListByRoleId(@RequestParam("roleId") Long roleId) {
        return ResponseResult.responseSuccess(securityUserService.getUserListByRoleId(roleId));
    }

    @GetMapping("/delUser")
    @ApiOperation("删除")
    public ResponseResult delUser(Long id){
        securityUserService.delUser(id);
        return ResponseResult.responseSuccess();
    }
    @GetMapping("/infos")
    @ApiOperation("验证token并返回用户信息")
    public ResponseResult infos(@RequestHeader(value="authorization") String authorization){

        return ResponseResult.responseSuccess();
    }
}
