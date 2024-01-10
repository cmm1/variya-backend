package com.zyp.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserVO implements  Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称（姓名）
     */
    private String nickName;

    /**
     * 账号（手机号）
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updater;

    //0离线 1在线 2忙碌
    private Integer status;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;


    private List<Long> roles;
    @ApiModelProperty(value = "token", name = "token", dataType = "String")
    private String token;

}
