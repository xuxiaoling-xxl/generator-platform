package com.digitalgd.tog.generator.modular.tog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.digitalgd.tog.generator.core.generator.param.ContextParam;
import com.digitalgd.tog.generator.core.generator.param.TemplateParam;
import com.digitalgd.tog.generator.modular.restful.mybatisplus.param.MpParam;
import com.digitalgd.tog.generator.modular.tog.controller.GunsControllerGenerator;
import com.digitalgd.tog.generator.modular.tog.controller.GunsUIControllerGenerator;
import com.digitalgd.tog.generator.modular.tog.html.GunsPageAddGenerator;
import com.digitalgd.tog.generator.modular.tog.html.GunsPageEditGenerator;
import com.digitalgd.tog.generator.modular.tog.html.GunsPageIndexGenerator;
import com.digitalgd.tog.generator.modular.tog.js.GunsPageAddJsGenerator;
import com.digitalgd.tog.generator.modular.tog.js.GunsPageEditJsGenerator;
import com.digitalgd.tog.generator.modular.tog.js.GunsPageIndexJsGenerator;
import com.digitalgd.tog.generator.modular.tog.mybatisplus.GunsMpGenerator;
import com.digitalgd.tog.generator.modular.tog.mybatisplus.NewGunsMpGenerator;
import com.digitalgd.tog.generator.modular.tog.sqls.GunsMenuSqlGenerator;

/**
 * 测试的执行器
 *
 * @author fengshuonan
 * @date 2018-12-18-6:39 PM
 */
public class GunsExecutor {

    /**
     * 默认的生成器
     *
     * @author fengshuonan
     * @Date 2019/1/13 22:18
     */
    public static void executor(ContextParam contextParam, MpParam mpContext,List<TemplateParam> tempList) {
    	 //根据数据库内容生成模板文件
        try {
			fileWriter(tempList);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        List<TemplateParam> mpTempList = new ArrayList<TemplateParam>();
        List<TemplateParam> otherTempList = new ArrayList<TemplateParam>();
        for(TemplateParam temp:tempList){
        	if("controller".equals(temp.getType()) || "html".equals(temp.getType())
        			|| "js".equals(temp.getType()) || "ftl".equals(temp.getType()) || "sql".equals(temp.getType())){
        		otherTempList.add(temp);
        	}else{
        		mpTempList.add(temp);
        	}
        }

        //执行mp的代码生成，生成entity,dao,service,model，生成后保留数据库元数据
        NewGunsMpGenerator gunsMpGenerator = new NewGunsMpGenerator(mpContext,mpTempList);
        gunsMpGenerator.initContext(contextParam);
        gunsMpGenerator.doGeneration();

        //获取元数据
        List<TableInfo> tableInfos = gunsMpGenerator.getTableInfos();
        Map<String, Map<String, Object>> everyTableContexts = gunsMpGenerator.getEveryTableContexts();

        //遍历所有表
        for (TableInfo tableInfo : tableInfos) {
            Map<String, Object> map = everyTableContexts.get(tableInfo.getName());

            for(TemplateParam temp:otherTempList){
        		GunsCommonGenerator gunsCommonGenerator = new GunsCommonGenerator(map,temp);
        		gunsCommonGenerator.initContext(contextParam);
        		gunsCommonGenerator.doGeneration();
            }
            
           /* //生成控制器
            GunsControllerGenerator gunsControllerGenerator = new GunsControllerGenerator(map);
            gunsControllerGenerator.initContext(contextParam);
            gunsControllerGenerator.doGeneration();
            
            //ui控制器
            GunsUIControllerGenerator gunsUIControllerGenerator = new GunsUIControllerGenerator(map);
            gunsUIControllerGenerator.initContext(contextParam);
            gunsUIControllerGenerator.doGeneration();

            //生成主页面html
            GunsPageIndexGenerator gunsPageIndexGenerator = new GunsPageIndexGenerator(map);
            gunsPageIndexGenerator.initContext(contextParam);
            gunsPageIndexGenerator.doGeneration();

            //生成主页面js
            GunsPageIndexJsGenerator gunsPageIndexJsGenerator = new GunsPageIndexJsGenerator(map);
            gunsPageIndexJsGenerator.initContext(contextParam);
            gunsPageIndexJsGenerator.doGeneration();

            //生成添加页面html
            GunsPageAddGenerator gunsPageAddGenerator = new GunsPageAddGenerator(map);
            gunsPageAddGenerator.initContext(contextParam);
            gunsPageAddGenerator.doGeneration();

            //生成添加页面的js
            GunsPageAddJsGenerator gunsPageAddJsGenerator = new GunsPageAddJsGenerator(map);
            gunsPageAddJsGenerator.initContext(contextParam);
            gunsPageAddJsGenerator.doGeneration();

            //生成编辑页面html
            GunsPageEditGenerator gunsPageEditGenerator = new GunsPageEditGenerator(map);
            gunsPageEditGenerator.initContext(contextParam);
            gunsPageEditGenerator.doGeneration();

            //生成编辑页面的js
            GunsPageEditJsGenerator gunsPageEditJsGenerator = new GunsPageEditJsGenerator(map);
            gunsPageEditJsGenerator.initContext(contextParam);
            gunsPageEditJsGenerator.doGeneration();

            //生成菜单的sql
            GunsMenuSqlGenerator gunsMenuSqlGenerator = new GunsMenuSqlGenerator(map);
            gunsMenuSqlGenerator.initContext(contextParam);
            gunsMenuSqlGenerator.doGeneration();*/
        }
    }
    
    /**
     * 根据数据库内容生成模板文件
     * @throws IOException 
     */
    public static void fileWriter(List<TemplateParam> tempList) throws IOException{
        //存储目录
    	String rootPath = new File(System.getProperty("user.dir")).getParentFile().toString();
    	String holdPath = rootPath + "/gen-core/src/main/resources/template";
    	File file = new File(holdPath);
        //清除目录下的文件
    	if(file.exists()){
        	File[] files = file.listFiles();
            for (File f : files) {
                f.delete();
            }
        }else{
        	file.mkdirs();
        }
       
		for(TemplateParam temp : tempList){
			String fileName = holdPath + "/" + temp.getName();
			Writer out;
			out = new FileWriter(fileName);
			out.write(temp.getDetails());
			out.close();
		}
    }

    public static void main(String[] args) {

        ContextParam contextParam = new ContextParam();

        contextParam.setJdbcDriver("com.mysql.jdbc.Driver");
        contextParam.setJdbcUserName("root");
        contextParam.setJdbcPassword("root");
        contextParam.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/generator_platform?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=CTT");
        contextParam.setOutputPath("temp");
        contextParam.setAuthor("fengshuonan");
        contextParam.setProPackage("cn.stylefeng.guns.modular.test");

        MpParam mpContextParam = new MpParam();
        mpContextParam.setGeneratorInterface(true);
        mpContextParam.setIncludeTables(new String[]{"test"});
        mpContextParam.setRemoveTablePrefix(new String[]{"sys_"});

        GunsExecutor.executor(contextParam, mpContextParam,null);
    }

}
