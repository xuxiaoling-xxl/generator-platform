package com.digitalgd.tog.generator.modular.tog.sqls;

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

import cn.hutool.core.util.StrUtil;

/**
 * Guns菜单相关的sql生成器
 *
 * @author fengshuonan
 * @date 2018-12-13-2:20 PM
 */
public class GunsMenuSqlGenerator extends AbstractCustomGenerator {

    public GunsMenuSqlGenerator(Map<String, Object> tableContext,TemplateParam templateParam) {
        super(tableContext,templateParam);
    }

    @Override
    public void bindingOthers(Template template) {
        TableInfo tableInfo = (TableInfo) tableContext.get("table");
        template.binding("menus", getMenus(tableInfo));
    }

    @Override
    public String getTemplateResourcePath() {
        return "/gunsTemplates/menu_sql.sql.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String lowerEntity = (String) this.tableContext.get("lowerEntity");
        File file = new File(contextParam.getOutputPath() + "/sqls/" + lowerEntity + "_menus.sql");
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

//        MenuModel addMenu = new MenuModel();
//        addMenu.setMenuId(IdWorker.getId());
//        addMenu.setCode(StrUtil.toUnderlineCase(tableInfo.getEntityName()).toUpperCase() + "_ADD");
//        addMenu.setPcode(parentMenu.getCode());
//        addMenu.setPcodes("[0],[" + parentMenu.getCode() + "],");
//        addMenu.setMenuName(parentMenu.getMenuName() + "添加");
//        addMenu.setIcon("fa-star");
//        addMenu.setSort(999);
//        addMenu.setLevels(2);
//        menuModels.add(addMenu);
//
//        MenuModel editMenu = new MenuModel();
//        editMenu.setMenuId(IdWorker.getId());
//        editMenu.setCode(StrUtil.toUnderlineCase(tableInfo.getEntityName()).toUpperCase() + "_EDIT");
//        editMenu.setPcode(parentMenu.getCode());
//        editMenu.setPcodes("[0],[" + parentMenu.getCode() + "],");
//        editMenu.setMenuName(parentMenu.getMenuName() + "修改");
//        editMenu.setIcon("fa-star");
//        editMenu.setSort(999);
//        editMenu.setLevels(2);
//        menuModels.add(editMenu);
//
//        MenuModel deleteMenu = new MenuModel();
//        deleteMenu.setMenuId(IdWorker.getId());
//        deleteMenu.setCode(StrUtil.toUnderlineCase(tableInfo.getEntityName()).toUpperCase() + "_DELETE");
//        deleteMenu.setPcode(parentMenu.getCode());
//        deleteMenu.setPcodes("[0],[" + parentMenu.getCode() + "],");
//        deleteMenu.setMenuName(parentMenu.getMenuName() + "删除");
//        deleteMenu.setIcon("fa-star");
//        deleteMenu.setSort(999);
//        deleteMenu.setLevels(2);
//        menuModels.add(deleteMenu);

        return menuModels;

    }
}
