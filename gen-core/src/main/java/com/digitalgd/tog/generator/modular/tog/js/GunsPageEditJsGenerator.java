package com.digitalgd.tog.generator.modular.tog.js;

import java.io.File;
import java.util.Map;

import org.beetl.core.Template;

import com.digitalgd.tog.generator.core.generator.AbstractCustomGenerator;
import com.digitalgd.tog.generator.core.generator.param.TemplateParam;

/**
 * Guns编辑页面js生成器
 *
 * @author fengshuonan
 * @date 2018-12-13-2:20 PM
 */
public class GunsPageEditJsGenerator extends AbstractCustomGenerator {

    public GunsPageEditJsGenerator(Map<String, Object> tableContext,TemplateParam templateParam) {
        super(tableContext,templateParam);
    }

    @Override
    public void bindingOthers(Template template) {
    }

    @Override
    public String getTemplateResourcePath() {
        return "/gunsTemplates/page_edit.js.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String lowerEntity = (String) this.tableContext.get("lowerEntity");
        File file = new File(contextParam.getOutputPath() + "/js/" + lowerEntity + "/" + lowerEntity + "_edit.js");
        return file.getAbsolutePath();
    }
}
