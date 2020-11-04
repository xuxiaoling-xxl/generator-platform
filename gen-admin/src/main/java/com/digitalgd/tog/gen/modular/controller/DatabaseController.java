package com.digitalgd.tog.gen.modular.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalgd.tog.gen.core.DbUtil;
import com.digitalgd.tog.gen.core.page.LayuiPageFactory;
import com.digitalgd.tog.gen.modular.model.DatabaseInfo;
import com.digitalgd.tog.gen.modular.model.SuccessResponseData;
import com.digitalgd.tog.gen.modular.model.TemplateInfo;
import com.digitalgd.tog.gen.modular.repository.DatabaseInfoRepository;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @date 2019-01-30-2:39 PM
 */
@Controller
@RequestMapping("/db")
public class DatabaseController {

    @Autowired
    private DatabaseInfoRepository databaseInfoRepository;

    /**
     * 数据库管理主页
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("")
    public String index() {
        return "/db.html";
    }

    /**
     * 新增页面
     *
     * @author fengshuonan
     * @Date 2019-01-11
     */
    @RequestMapping("/add")
    public String add() {
        return "/db_add.html";
    }

    /**
     * 新增
     *
     * @author fengshuonan
     * @Date 2019-01-11
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public Object addItem(DatabaseInfo databaseInfo) {
        this.databaseInfoRepository.save(databaseInfo);
        return new SuccessResponseData();
    }
    
    
    /**
     * 根据id获取数据
     * @param id
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public DatabaseInfo get(Long id) {
    	DatabaseInfo databaseInfo = new DatabaseInfo();
    	if(id != null){
    		databaseInfo = databaseInfoRepository.getOne(id);
    	}
        return databaseInfo;
    }

    /**
     * 删除
     *
     * @author fengshuonan
     * @Date 2019-01-11
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long dbId) {
        this.databaseInfoRepository.deleteById(dbId);
        return new SuccessResponseData();
    }

    /**
     * 获取数据源列表
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        List<DatabaseInfo> all = databaseInfoRepository.findAll();

        Page<DatabaseInfo> objectPage = new Page<>();
        objectPage.setRecords(all);
        objectPage.setSize(all.size());

        return LayuiPageFactory.createPageInfo(objectPage);
    }

    /**
     * 获取某个数据源下的所有表
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public Object tableList(Long dbId) {
        try {
            Optional<DatabaseInfo> byId = databaseInfoRepository.findById(dbId);
            DatabaseInfo databaseInfo = byId.get();
            List<Map<String, Object>> maps = DbUtil.selectTables(databaseInfo);
            Page<Map<String, Object>> objectPage = new Page<>();
            objectPage.setRecords(maps);
            return LayuiPageFactory.createPageInfo(objectPage);
        } catch (Exception e) {
            Page<Map<String, Object>> objectPage = new Page<>();
            return LayuiPageFactory.createPageInfo(objectPage);
        }
    }
    
    /**
     * 数据库连接测试
     */
    @RequestMapping("/connectVerify")
    @ResponseBody
    public boolean connectVerify(DatabaseInfo dbInfo) {
    	return DbUtil.connectVerify(dbInfo);
    }

}
