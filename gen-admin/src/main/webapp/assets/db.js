layui.use(['layer', 'ax', 'form', 'laydate', 'element', 'table','jquery'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    var element = layui.element;
    var table = layui.table;

    $('#code_gen').click(function () {
        window.location.href = Feng.ctxPath + "/";
    });

    $('#template_config').click(function () {
    	window.location.href = Feng.ctxPath + "/template";
    });

    $('#db_config').click(function () {
        window.location.href = Feng.ctxPath + "/db";
    });
    
    //添加数据源
    $('#db_add').on('click', function(){
    	openDialog("add",null);
    });
    
    //关闭数据源弹框
    $('#closeDialog').on('click', function(){
    	layer.closeAll();
    });

    table.render({
        elem: '#dbTable'
        , url: Feng.ctxPath + '/db/list'
        , page: false
        , height: "full-158"
        , cols: [[
            {type: 'checkbox'}
            /*, {field: 'dbId', title: 'id'}*/
            , {field: 'dbName', title: '数据源名称'}
            , {field: 'jdbcDriver', title: 'jdbc的驱动类型'}
            , {field: 'userName', title: '数据库连接的账号'}
            , {field: 'password',width:'10%', title: '密码'}
            , {field: 'jdbcUrl', title: 'jdbc的url'}
            , {field: 'type',width:'10%', title: '类型',templet:function(d){
            	if(d.type == 'yw'){
            		return '业务'
            	}else if(d.type == 'jc'){
            		return '基础'
            	}
            }}
            , {field: 'createTime', title: '创建时间',templet :'<div>{{layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:ss")}}</div>'}
            , {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]]
    });

    // 工具条点击事件
    table.on('tool(dbTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'delete') {
            var operation = function () {
                var ajax = new $ax(Feng.ctxPath + "/db/delete", function () {
                    Feng.success("删除成功!");
                    table.reload("dbTable");
                }, function (data) {
                    Feng.error("删除失败!");
                });
                ajax.set("dbId", data.dbId);
                ajax.start();
            };
            Feng.confirm("是否删除数据源 " + data.dbName + "?", operation);
        } else if(layEvent === 'update'){
    		openDialog(layEvent,data);
    	} else {
    		connectVerify(data);
    	}
    });
    
    // 弹出框
    function openDialog(type,obj){
    	var title = "添加数据源";
    	if("add" == type){
    		$("#resetBtn").click();
    	}else{
    		title = "编辑数据源";
    		$('#dbId').val(obj.dbId);
    		$('#dbName').val(obj.dbName);
    		$('#jdbcDriver').val(obj.jdbcDriver);
    		$('#userName').val(obj.userName);
    		$('#password').val(obj.password);
    		$('#jdbcUrl').val(obj.jdbcUrl);
    		$('#type').val(obj.type);
    		form.render('select');
    	}
    	
     	layer.open({
    		type: 1,
    		title: title,
    		content: $('#open_dialog'),
    		shade: 0, //不显示遮罩
    		area: ['75%', '80%'],
    		success:function () {
    		}
    	});
    }
    
    form.on('submit(connectVerify)', function (data) {
    	connectVerify(data.field);
    	return false;
    });
    
    // 连接测试
    function connectVerify(data){
    	var ajax = new $ax(Feng.ctxPath + "/db/connectVerify", function (result) {
            if(result){
           	 Feng.success("连接成功!");
            }else{
           	 Feng.error("连接失败!");
            }
        }, function (result) {
            Feng.error("连接失败!");
        });
        ajax.set("jdbcUrl", data.jdbcUrl);
        ajax.set("jdbcDriver", data.jdbcDriver);
        ajax.set("userName", data.userName);
        ajax.set("password", data.password);
        ajax.start();
        return false;
    }
    
    // 表单提交事件
    form.on('submit(submitDb)', function (data) {
    	var ajax = new $ax(Feng.ctxPath + "/db/list", function (result) {
    		var flag = true;
    		var list = result.data;
    		if(list){
    			for(var i=0;i<list.length;i++){
    				if((!data.field.dbId || (data.field.dbId && list[i].dbId != data.field.dbId))
    						&& list[i].dbName == data.field.dbName){
    					flag = false;
    					Feng.error("该数据源名称已存在！");
    					return false;
    				}
    			}
    		}
    		
    		if(flag){
    			if(!data.field.type){
    				data.field.type = "yw";
    			}
    			var ajax = new $ax(Feng.ctxPath + "/db/addItem", function (data) {
    	              Feng.success("添加成功！");
    	              layer.closeAll();
    	              table.reload("dbTable");
    	          }, function (data) {
    	              Feng.error("添加失败！");
    	          });
    	          ajax.set(data.field);
    	          ajax.start();
    	          return false;
    		}
    		
    	});
    	ajax.start();
    	return false;
    });

});