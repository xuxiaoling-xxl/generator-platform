package com.digitalgd.tog.gen.modular.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalgd.tog.gen.core.page.LayuiPageFactory;
import com.digitalgd.tog.gen.modular.model.SuccessResponseData;
import com.digitalgd.tog.gen.modular.model.TemplateInfo;
import com.digitalgd.tog.gen.modular.repository.TemplateInfoRepository;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @date 2019-01-30-2:39 PM
 */
@Controller
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateInfoRepository templateInfoRepository;

    /**
     * 模板管理页面
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("")
    public String index() {
        return "/template.html";
    }

    /**
     * 新增/更新
     *
     * @author fengshuonan
     * @Date 2019-01-11
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public Object addItem(TemplateInfo templateInfo) {
    	if(StringUtils.isEmpty(templateInfo.getId())){
    		templateInfo.setId(UUID.randomUUID().toString());
    	}
        this.templateInfoRepository.save(templateInfo);
        return new SuccessResponseData();
    }
    
    /**
     * 更新模板状态
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Object updateStatus(String id, Integer status) {
    	if(StringUtils.isNotEmpty(id)){
    	TemplateInfo templateInfo = this.templateInfoRepository.getOne(id);
    	templateInfo.setStatus(status);
    	this.templateInfoRepository.save(templateInfo);
    	}
    	return new SuccessResponseData();
    }

    /**
     * 删除
     *
     * @author fengshuonan
     * @Date 2019-01-11
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
        this.templateInfoRepository.deleteById(id);
        return new SuccessResponseData();
    }
    
    /**
     * 根据id获取数据
     * @param id
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public TemplateInfo get(String id) {
    	TemplateInfo templateInfo = new TemplateInfo();
    	if(StringUtils.isNotEmpty(id)){
    		templateInfo = templateInfoRepository.getOne(id);
    	}
        return templateInfo;
    }

    /**
     * 获取模板列表
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        List<TemplateInfo> all = templateInfoRepository.findAll();

        Page<TemplateInfo> objectPage = new Page<>();
        objectPage.setRecords(all);
        objectPage.setSize(all.size());
        return LayuiPageFactory.createPageInfo(objectPage);
    }

}
