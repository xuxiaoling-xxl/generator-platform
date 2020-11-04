package com.digitalgd.tog.generator.core.generator.param;

import lombok.Data;

@Data
public class TemplateParam{
    /**
     * 模板名称
     */
    private String name;
    
    /**
     * 模板内容
     */
    private String details;
    
    /**
     * 类型
     */
    private String type;
    
    /**
     * 命名后缀
     */
    private String suffix;
}
