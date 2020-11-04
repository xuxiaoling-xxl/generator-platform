package com.digitalgd.tog.generator.modular.restful.feign;

import java.io.File;
import java.util.Map;

import org.beetl.core.Template;

import com.digitalgd.tog.generator.core.generator.AbstractCustomGenerator;
import com.digitalgd.tog.generator.core.generator.param.TemplateParam;

/**
 * feign的provider生成器
 *
 * @author fengshuonan
 * @date 2018-12-13-2:20 PM
 */
public class FeignProviderGenerator extends AbstractCustomGenerator {

    public FeignProviderGenerator(Map<String, Object> tableContext,TemplateParam templateParam) {
        super(tableContext,templateParam);
    }

    @Override
    public void bindingOthers(Template template) {
        template.binding("apiPackage", contextParam.getProPackage() + ".api");
        template.binding("providerPackage", contextParam.getProPackage() + ".provider");
    }

    @Override
    public String getTemplateResourcePath() {
        return "/feignTemplates/FeignProvider.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String proPackage = this.contextParam.getProPackage();
        String proPath = proPackage.replaceAll("\\.", "/");
        File file = new File(contextParam.getOutputPath() + "/" + proPath + "/provider/" + tableContext.get("entity") + "Provider.java");
        return file.getAbsolutePath();
    }
}
