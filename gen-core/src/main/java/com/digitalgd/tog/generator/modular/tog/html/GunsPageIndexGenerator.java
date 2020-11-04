package com.digitalgd.tog.generator.modular.tog.html;

import java.io.File;
import java.util.Map;

import com.digitalgd.tog.generator.core.generator.AbstractCustomGenerator;
import com.digitalgd.tog.generator.core.generator.param.TemplateParam;

/**
 * Guns主页面生成器
 *
 * @author fengshuonan
 * @date 2018-12-13-2:20 PM
 */
public class GunsPageIndexGenerator extends AbstractCustomGenerator {

    public GunsPageIndexGenerator(Map<String, Object> tableContext,TemplateParam templateParam) {
        super(tableContext,templateParam);
    }

    @Override
    public String getTemplateResourcePath() {
        return "/gunsTemplates/page.html.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String lowerEntity = (String) this.tableContext.get("lowerEntity");
        File file = new File(contextParam.getOutputPath() + "/html/" + lowerEntity + "/" + lowerEntity + ".html");
        return file.getAbsolutePath();
    }
}
