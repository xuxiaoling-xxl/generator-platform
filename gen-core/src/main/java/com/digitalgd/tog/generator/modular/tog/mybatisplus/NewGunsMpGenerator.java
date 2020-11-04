package com.digitalgd.tog.generator.modular.tog.mybatisplus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.digitalgd.tog.generator.core.generator.AbstractMpGenerator;
import com.digitalgd.tog.generator.core.generator.param.TemplateParam;
import com.digitalgd.tog.generator.modular.restful.mybatisplus.param.MpParam;

/**
 * 默认的mybatis-plus生成器
 *
 * @author fengshuonan
 * @date 2018-12-13-2:20 PM
 */
public class NewGunsMpGenerator extends AbstractMpGenerator {

    private MpParam mpContextParam;
    
    private List<TemplateParam> tempList;

    public NewGunsMpGenerator(MpParam mpContextParam, List<TemplateParam> tempList) {
        this.mpContextParam = mpContextParam;
        this.tempList = tempList;
    }

    /**
     * 代码生成之前，配置代码生成器所需要的配置
     *
     * @author fengshuonan
     * @Date 2018/12/13 2:48 PM
     */
    @Override
    protected void beforeGeneration() {

        // 全局配置
        globalConfig.setOutputDir(contextParam.getOutputPath());
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setEnableCache(false);
        globalConfig.setIdType(IdType.ID_WORKER);
        globalConfig.setOpen(false);
        globalConfig.setAuthor(contextParam.getAuthor());

        //时间格式还用旧的Date
        globalConfig.setDateType(DateType.ONLY_DATE);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        if (mpContextParam.getGeneratorInterface()) {
            globalConfig.setServiceName("%sService");
            globalConfig.setServiceImplName("%sServiceImpl");
        } else {
            globalConfig.setServiceName("%sService");
            globalConfig.setServiceImplName("%sService");
        }

        // 数据源配置
        if (contextParam.getJdbcUrl().contains("oracle")) {
            dataSourceConfig.setDbType(DbType.ORACLE);
        } else if (contextParam.getJdbcUrl().contains("postgresql")) {
            dataSourceConfig.setDbType(DbType.POSTGRE_SQL);
        } else if (contextParam.getJdbcUrl().contains("sqlserver")) {
            dataSourceConfig.setDbType(DbType.SQL_SERVER);
        } else {
            dataSourceConfig.setDbType(DbType.MYSQL);
        }
        dataSourceConfig.setDriverName(contextParam.getJdbcDriver());
        dataSourceConfig.setUrl(contextParam.getJdbcUrl());
        dataSourceConfig.setUsername(contextParam.getJdbcUserName());
        dataSourceConfig.setPassword(contextParam.getJdbcPassword());

        // 策略配置
        // 大写命名
        strategyConfig.setCapitalMode(false);

        // 此处可以移除表前缀
        strategyConfig.setTablePrefix(this.mpContextParam.getRemoveTablePrefix());
        // 此处可以移除字段前缀
        strategyConfig.setFieldPrefix(this.mpContextParam.getRemoveFieldPrefix());
        // 表名生成策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        //字段生成策略
        strategyConfig.setColumnNaming(this.mpContextParam.getNaming());
        strategyConfig.setEntityLombokModel(this.mpContextParam.isEntityLombokModel());

        // 需要生成的表
        strategyConfig.setInclude(this.mpContextParam.getIncludeTables());

        // 公共字段填充
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(new TableFill("CREATE_TIME", FieldFill.INSERT));
        tableFills.add(new TableFill("UPDATE_TIME", FieldFill.UPDATE));
        tableFills.add(new TableFill("CREATE_USER", FieldFill.INSERT));
        tableFills.add(new TableFill("UPDATE_USER", FieldFill.UPDATE));
        strategyConfig.setTableFillList(tableFills);
        
        String entityPath = null;
        String mapperPath = null;
        String servicePath = null;
        String serviceImplPath = null;
        String xmlPath = null;
        List<TemplateParam> modelTempList = new ArrayList<>();
        if(tempList != null && tempList.size()>0){
        	for(TemplateParam temp : tempList){
        		if("entity".equals(temp.getType())){
        			entityPath = "/template/" + temp.getName();
        		}else if("mapper".equals(temp.getType())){
        			mapperPath = "/template/" + temp.getName();
        		}else if("service".equals(temp.getType())){
        			servicePath = "/template/" + temp.getName();
        		}else if("serviceImpl".equals(temp.getType())){
        			serviceImplPath = "/template/" + temp.getName();
        		}else if("xml".equals(temp.getType())){
        			xmlPath = "/template/" + temp.getName();
        		}else if("model".equals(temp.getType())){
        			modelTempList.add(temp);
        		}
        	}
        }

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        templateConfig.setController(null);
        templateConfig.setXml(xmlPath);
        templateConfig.setEntity(entityPath);
        templateConfig.setMapper(mapperPath);

        //如果不生成接口，就走不生成接口的模板
        if (!this.mpContextParam.getGeneratorInterface()) {
            templateConfig.setService(null);
            templateConfig.setServiceImpl("/mpTemplates/NoneInterfaceServiceImpl.java");
        } else {
            templateConfig.setService(servicePath);
            templateConfig.setServiceImpl(serviceImplPath);
        }

        // 包配置
        packageConfig.setParent(this.contextParam.getProPackage());
        packageConfig.setModuleName("");
        packageConfig.setXml("mapper.mapping");

        if (this.mpContextParam.getGeneratorInterface()) {
            packageConfig.setServiceImpl("service.impl");
            packageConfig.setService("service");
        } else {
            packageConfig.setServiceImpl("service");
            packageConfig.setService("service");
        }

        //自定义specification model的生成
        List<FileOutConfig> focList = new ArrayList<>();
        for(TemplateParam temp: modelTempList){
        	String templatePath = "/template/" + temp.getName();
       	 	String proPackage = this.contextParam.getProPackage().replaceAll("\\.", "/");
       	 	String proPath = contextParam.getOutputPath() + "/" + proPackage + "/"+temp.getType();
            File file = new File(proPath);
           if (!file.exists()) {
           	file.mkdirs();
           }
           focList.add(new FileOutConfig(templatePath) {
               @Override
               public String outputFile(TableInfo tableInfo) {
                   return proPath +"/"+tableInfo.getEntityName()+temp.getSuffix()+"Model.java";
               }
           });
           injectionConfig.setFileOutConfigList(focList);
        }
        HashMap<String, Object> contexMap = new HashMap<>();
        contexMap.put("EntitySpecParams", this.contextParam.getProPackage() + ".model");
        contexMap.put("EntitySpecResult", this.contextParam.getProPackage() + ".model");
        injectionConfig.setMap(contexMap);
    }

}
