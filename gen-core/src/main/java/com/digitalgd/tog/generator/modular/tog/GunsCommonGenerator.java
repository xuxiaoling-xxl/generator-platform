package com.digitalgd.tog.generator.modular.tog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.beetl.core.Template;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.digitalgd.tog.generator.core.generator.AbstractCustomGenerator;
import com.digitalgd.tog.generator.core.generator.param.TemplateParam;
import com.digitalgd.tog.generator.core.util.TemplateUtil;
import com.digitalgd.tog.generator.modular.tog.sqls.MenuModel;

import cn.hutool.core.util.StrUtil;

/**
 * 公共生成器
 *
 */
public class GunsCommonGenerator extends AbstractCustomGenerator {

    public GunsCommonGenerator(Map<String, Object> tableContext,TemplateParam templateParam) {
        super(tableContext,templateParam);
    }

    @Override
    public void bindingOthers(Template template) {
    	template.binding("$", "$");
    	switch(templateParam.getType()){
    		case "controller":
    			template.binding("controllerPackage", contextParam.getProPackage() + ".controller");
    			break;
    		case "html":
    			 super.bindingInputsParams(template);
    			 break;
    		case "ftl":
    			super.bindingInputsParams(template);
    			break;
    		case "sql":
    			TableInfo tableInfo = (TableInfo) tableContext.get("table");
    	        template.binding("menus", getMenus(tableInfo));
    	        break;
    		default:
    			break;
    	}
    }

    @Override
    public String getTemplateResourcePath() {
        return "/template/"+templateParam.getName();
    }

    @Override
    public String getGenerateFilePath() {
	   	String proPackage = this.contextParam.getProPackage().replaceAll("\\.", "/");
	   	String proPath = contextParam.getOutputPath() + "/" + proPackage + "/" + templateParam.getType();
    	
        File file = new File(proPath +"/" + tableContext.get("entity") + templateParam.getSuffix() + "."+templateParam.getType());
        if("controller".equals(templateParam.getType())){
        	file = new File(proPath +"/" + tableContext.get("entity") + templateParam.getSuffix() + "Controller.java");
        }	
        return file.getAbsolutePath();
    }
    
    private List<MenuModel> getMenus(TableInfo tableInfo) {
        ArrayList<MenuModel> menuModels = new ArrayList<>();

        MenuModel parentMenu = new MenuModel();
        parentMenu.setMenuId(IdWorker.getId());
        parentMenu.setCode(StrUtil.toUnderlineCase(tableInfo.getEntityName()).toUpperCase());
        parentMenu.setPcode("0");
        parentMenu.setPcodes("[0],");
        if (StrUtil.isBlank(TemplateUtil.cleanWhite(tableInfo.getComment()))) {
            parentMenu.setMenuName("空表名");
        } else {
            parentMenu.setMenuName(TemplateUtil.cleanWhite(tableInfo.getComment()));
        }
        parentMenu.setIcon("fa-star");
        parentMenu.setMenuUrl("/" + TemplateUtil.lowerFirst((String) tableContext.get("entity")));
        parentMenu.setSort(999);
        parentMenu.setLevels(1);
        menuModels.add(parentMenu);

        return menuModels;
    }
    
}
