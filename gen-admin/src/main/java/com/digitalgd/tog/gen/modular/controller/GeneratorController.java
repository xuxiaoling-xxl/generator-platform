package com.digitalgd.tog.gen.modular.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.digitalgd.tog.gen.modular.model.DatabaseInfo;
import com.digitalgd.tog.gen.modular.model.TemplateInfo;
import com.digitalgd.tog.gen.modular.repository.DatabaseInfoRepository;
import com.digitalgd.tog.gen.modular.repository.TemplateInfoRepository;
import com.digitalgd.tog.generator.core.generator.param.ContextParam;
import com.digitalgd.tog.generator.core.generator.param.TemplateParam;
import com.digitalgd.tog.generator.modular.restful.mybatisplus.param.MpParam;
import com.digitalgd.tog.generator.modular.tog.GunsExecutor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @date 2019-01-30-2:39 PM
 */
@Controller
public class GeneratorController {

    @Autowired
    private DatabaseInfoRepository databaseInfoRepository;

    @Autowired
    private TemplateInfoRepository templateInfoRepository;

    /**
     * 代码生成主页
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("")
    public String index(Model model) {

        List<DatabaseInfo> all = databaseInfoRepository.findAll();
        model.addAttribute("dataSources", all);

        return "/gen.html";
    }

    /**
     * 执行代码生成
     *
     * @author fengshuonan
     * @Date 2019-01-11
     */
    @RequestMapping(value = "/execute")
    @ResponseBody
    public ResponseEntity<InputStreamResource> execute(String removePrefix, String removeFieldPrefix,
    		String author, String proPackage,Long dataSourceId, String tables,String namingStrategy,String entityLombokModel) {
        tables = tables.substring(3);

        String[] tableArray = tables.split("CAT");

        //获取数据源信息
        Optional<DatabaseInfo> dbInfo = this.databaseInfoRepository.findById(dataSourceId);

        DatabaseInfo databaseInfo = dbInfo.get();

        ContextParam contextParam = new ContextParam();
        contextParam.setAuthor(author);
        contextParam.setProPackage(proPackage);
        contextParam.setJdbcDriver(databaseInfo.getJdbcDriver());
        contextParam.setJdbcUserName(databaseInfo.getUserName());
        contextParam.setJdbcPassword(databaseInfo.getPassword());
        contextParam.setJdbcUrl(databaseInfo.getJdbcUrl());
        
        //获取临时目录
        long fileName = IdWorker.getId();
        String tempPath = System.getProperty("java.io.tmpdir") + File.separator + "gunsGeneration" + File.separator + fileName;
        contextParam.setOutputPath(tempPath);

        MpParam mpContextParam = new MpParam();
        mpContextParam.setGeneratorInterface(true);
        mpContextParam.setIncludeTables(tableArray);

        if (StrUtil.isNotEmpty(removePrefix)) {
            mpContextParam.setRemoveTablePrefix(new String[]{removePrefix});
        }
        if (StrUtil.isNotEmpty(removeFieldPrefix)) {
        	mpContextParam.setRemoveFieldPrefix(new String[]{removeFieldPrefix});
        }
        if (StrUtil.isNotEmpty(namingStrategy) && "underline_to_camel".equals(namingStrategy)) {
        	mpContextParam.setNaming(NamingStrategy.underline_to_camel);
        }
        if (StrUtil.isNotEmpty(entityLombokModel) && "lombok".equals(entityLombokModel)) {
        	mpContextParam.setEntityLombokModel(true);
        }
        
        //获取模板数据
		List<TemplateInfo> templateInfoList = this.templateInfoRepository.findUseList();
		List<TemplateParam> tempList = new ArrayList<>();
		for(TemplateInfo templateInfo : templateInfoList){
			TemplateParam templateParam = new TemplateParam();
			templateParam.setName(templateInfo.getName());
			templateParam.setDetails(templateInfo.getDetails());
			templateParam.setType(templateInfo.getType());
			templateParam.setSuffix(templateInfo.getSuffix());
			tempList.add(templateParam);
		}
		if(tempList == null || tempList.size() == 0){
			return null;
		}
		//代码生成contextParam
        GunsExecutor.executor(contextParam, mpContextParam,tempList);

        //打包下载代码
        File zip = ZipUtil.zip(tempPath);
        
        //清空临时目录
        File holdFile = new File(tempPath);
		deleteFile(holdFile);

        return renderFile(fileName + ".zip", zip.getAbsolutePath());
    }
    
    /**
     * 删除临时文件
     * @param file
     */
    public void deleteFile(File file) {
		if(!file.exists()) return;
		
		if(file.isFile() || file.list()==null) {
			file.delete();
		}else {
			File[] files = file.listFiles();
			for(File f:files) {
				deleteFile(f);					
			}
			file.delete();
		}
	}

    /**
     * 返回前台文件流
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return
     * @author 0x0001
     */
    private ResponseEntity<InputStreamResource> renderFile(String fileName, InputStream inputStream) {
        InputStreamResource resource = new InputStreamResource(inputStream);
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<InputStreamResource> renderFile(String fileName, String filePath) {
        try {
            final FileInputStream inputStream = new FileInputStream(filePath);
            return renderFile(fileName, inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件读取错误");
        }
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<InputStreamResource> renderFile(String fileName, byte[] fileBytes) {
        return renderFile(fileName, new ByteArrayInputStream(fileBytes));
    }


}
