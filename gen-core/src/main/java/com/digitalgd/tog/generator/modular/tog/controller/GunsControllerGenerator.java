package com.digitalgd.tog.generator.modular.tog.controller;

import java.io.File;
import java.util.Map;

import org.beetl.core.Template;

import com.digitalgd.tog.generator.core.generator.AbstractCustomGenerator;
import com.digitalgd.tog.generator.core.generator.param.TemplateParam;

/**
 * 带restful接口控制器生成器
 *
 * @author fengshuonan
 * @date 2018-12-13-2:20 PM
 */
public class GunsControllerGenerator extends AbstractCustomGenerator {

    public GunsControllerGenerator(Map<String, Object> tableContext,TemplateParam templateParam) {
        super(tableContext,templateParam);
    }

    @Override
    public void bindingOthers(Template template) {
        template.binding("controllerPackage", contextParam.getProPackage() + ".controller");
    }

    @Override
    public String getTemplateResourcePath() {
        return "/gunsTemplates/controller.java.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String proPackage = this.contextParam.getProPackage();
        String proPath = proPackage.replaceAll("\\.", "/");
        File file = new File(contextParam.getOutputPath() + "/" + proPath + "/controller/" + tableContext.get("entity") + "Controller.java");
        return file.getAbsolutePath();
    }
}
