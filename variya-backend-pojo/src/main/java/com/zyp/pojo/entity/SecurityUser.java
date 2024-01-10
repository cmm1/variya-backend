package com.zyp.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.BooleanTypeHandler;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 
 * @TableName security_user
 */
@TableName(value ="security_user")
@Data
public class SecurityUser implements UserDetails,Serializable {

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

    /**
     * 加急次数，每天两次
     */
    private Integer rushCount;

    //0离线 1在线 2忙碌
    private Integer status;

    @TableField(typeHandler = BooleanTypeHandler.class)
    private boolean accountNonExpired;
    @TableField(typeHandler = BooleanTypeHandler.class)
    private boolean accountNonLocked;
    @TableField(typeHandler = BooleanTypeHandler.class)
    private boolean credentialsNonExpired;
    @TableField(typeHandler = BooleanTypeHandler.class)
    private boolean enabled;

    @TableField(exist = false)
    private Set<SecurityRole> authorities;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}