package com.digitalgd.tog.generator.modular.restful.mybatisplus.param;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import lombok.Data;

/**
 * 代码生成所需要的上下文参数
 *
 * @author fengshuonan
 * @date 2018-12-12-4:30 PM
 */
@Data
public class MpParam {

    /**
     * 移除表前缀
     */
    private String[] removeTablePrefix = {""};
    
    /**
     * 移除字段前缀
     */
    private String[] removeFieldPrefix = {""};
    
    /**
     * 从数据库表到文件的命名策略
     */
    private NamingStrategy naming = NamingStrategy.nochange;
    
    /**
     * 【实体】是否为lombok模型（默认 false）
     */
    private boolean entityLombokModel = false;

    /**
     * 包含的表名称
     */
    private String[] includeTables;

    /**
     * 是否生成service的接口
     */
    private Boolean generatorInterface = false;

}
