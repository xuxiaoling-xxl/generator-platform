package com.digitalgd.tog.gen.modular.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据源实体
 * </p>
 *
 * @author fengshuonan
 * @since 2019-01-11
 */
@Entity
@Table(name = "DATABASE_INFO")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class DatabaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "DB_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbId;

    /**
     * 数据源名称
     */
    @Column(name = "DB_NAME", unique = true)
    private String dbName;

    /**
     * jdbc的驱动类型
     */
    @Column(name = "JDBC_DRIVER")
    private String jdbcDriver;

    /**
     * 数据库连接的账号
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * jdbc的url
     */
    @Column(name = "JDBC_URL")
    private String jdbcUrl;
    
    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();

}
