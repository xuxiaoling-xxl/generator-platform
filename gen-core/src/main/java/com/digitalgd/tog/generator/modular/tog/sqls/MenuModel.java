package com.digitalgd.tog.generator.modular.tog.sqls;

import java.util.Date;

import lombok.Data;

/**
 * 菜单的实体
 *
 */
@Data
public class MenuModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单id
	 */
	private Long menuId;

	/**
	 * 所属系统id
	 */
	private Long systemId;

	/**
	 * 菜单编号
	 */
	private String code;

	/**
	 * 父菜单编号
	 */
	private String pcode;

	/**
	 * 当前菜单所父菜单编号
	 */
	private String pcodes;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 访问地址
	 */
	private String menuUrl;

	/**
	 * 访问方式
	 */
	private Integer openType;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 序号
	 */
	private Integer sort;

	/**
	 * 菜单层级
	 */
	private Integer levels;

	/**
	 * 关联权限id
	 */
	private Long classPowerId;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 是否删除
	 */
	private Boolean deleted;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private Long createUser;

	/**
	 * 更新人人
	 */
	private Long updateUser;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
