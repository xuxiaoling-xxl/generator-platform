package com.digitalgd.tog.gen.modular.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * <p>
 * 数据源实体
 * </p>
 *
 * @author fengshuonan
 * @since 2019-01-11
 */
@Entity
@Table(name = "TEMPLATE_INFO")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class TemplateInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 模板名称
     */
    @Column(name = "name")
    private String name;
    
    /**
     * 模板内容
     */
    @Column(name = "details")
    private String details;
    
    /**
     * 命名后缀
     */
    @Column(name = "suffix")
    private String suffix;
    
    /**
     * 类型
     */
    @Column(name = "type")
    private String type;
    
    /**
     * 状态 0启用 1停用
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    /**
     * 备注
     */
    @Column(name = "comment")
    private String comment;

}
