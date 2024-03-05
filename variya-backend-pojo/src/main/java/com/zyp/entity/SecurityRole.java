package com.zyp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName security_role
 */
@TableName(value ="security_role")
@Data
public class SecurityRole implements GrantedAuthority,Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称(需要指定别名)
     */
    @TableField("name")
    private String authority;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否删除 1:正常 0：已删除
     */
    private Integer deleted;


    /**
     * 0 超级管理员
     */
    private Integer roleCode;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String creater;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String updater;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}