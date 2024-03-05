package com.zyp.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class UserDTO implements Serializable {
    private Long id;
    @ApiModelProperty(value = "昵称", name = "nickName", dataType = "String")
    private String nickName;
    @ApiModelProperty(value = "登录名", name = "username", dataType = "String")
    private String username;
    @ApiModelProperty(value = "密码", name = "password", dataType = "String")
    private String password;

    @ApiModelProperty(value = "roleId", name = "roleId", dataType = "roleId")
    private Long roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
