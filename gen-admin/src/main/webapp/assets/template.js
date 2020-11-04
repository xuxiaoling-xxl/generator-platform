layui.use(['layer', 'ax', 'form', 'laydate', 'element', 'table','jquery'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    var element = layui.element;
    var table = layui.table;
    var tempEditor;

    $('#code_gen').click(function () {
        window.location.href = Feng.ctxPath + "/";
    });
    
    $('#template_config').click(function () {
    	window.location.href = Feng.ctxPath + "/template";
    });

    $('#db_config').click(function () {
        window.location.href = Feng.ctxPath + "/db";
    });

    //添加模板
    $('#template_add').on('click', function(){
    	openDialog("add",null);
    });
    
    //关闭模板弹框
    $('#closeDialog').on('click', function(){
    	layer.closeAll();
    });

    table.render({
        elem: '#tempTable'
        , url: Feng.ctxPath + '/template/list'
        , page: false
        , height: "full-158"
        , cols: [[
            {type: 'checkbox'}
            , {field: 'name', title: '模板名称'}
            , {field: 'type', title: '类型'}
            , {field: 'suffix', title: '命名后缀'}
            , {field: 'createTime', title: '创建时间',templet :'<div>{{layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:ss")}}</div>'}
            , {field: 'comment', title: '备注'}
            , {field: 'status', toolbar: '#statusBar', title: '状态'}
            , {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]]
    });
    
    // 修改模板状态
    form.on('radio(statusChange)', function (data) {
		var ajax = new $ax(Feng.ctxPath + "/template/updateStatus", function () {
			Feng.success("成功!");
//			table.reload("tempTable");
		}, function (data) {
			Feng.error("状态更新失败!");
		});
		ajax.set("id", data.elem.id);
		ajax.set("status", data.value);
		ajax.start();
    });
    
    // 工具条点击事件
    table.on('tool(tempTable)', function (obj) {
    	var data = obj.data;
    	var layEvent = obj.event;
    	
    	if (layEvent === 'delete') {
    		var operation = function () {
    			var ajax = new $ax(Feng.ctxPath + "/template/delete", function () {
    				Feng.success("删除成功!");
    				table.reload("tempTable");
    			}, function (data) {
    				Feng.error("删除失败!");
    			});
    			ajax.set("id", data.id);
    			ajax.start();
    		};
    		Feng.confirm("是否删除模板 " + data.name + "?", operation);
    	} else{
    		var ajax = new $ax(Feng.ctxPath + "/template/get", function (result) {
    			openDialog(layEvent,result);
    		});
			ajax.set("id", data.id);
			ajax.start();
    	}
    });
    
    // 弹出框
    function openDialog(type,obj){
    	var title = "添加模板";
    	var md = null;
    	$("#tempFoot").show();
    	$("#template-editormd").empty();
    	if("add" == type){
    		$('#id').val("");
    		$('#name').val("");
    		$('#suffix').val("");
    		$('#status').val("");
    	}else{
    		$('#id').val(obj.id);
    		$('#name').val(obj.name);
    		$('#suffix').val(obj.suffix);
    		$('#type').val(obj.type);
    		$('#status').val(obj.status);
    		form.render('select');
    		md = obj.details;
    		if("update" == type){
    			title = "编辑模板";
    		}else{
    			title = "查看模板";
    			$("#tempFoot").hide();
    		}
    	}
    	
    	layer.open({
    		type: 1,
    		title: title,
    		content: $('#open_dialog'),
    		shade: 0, //不显示遮罩
    		area: ['90%', '98%'],
    		success:function () {
    			tempEditor = editormd("template-editormd", {
    				width: "auto",
    				height: 500,
    				path : 'assets/editormd/lib/',
    				theme : "default",
    				previewTheme : "default",
    				editorTheme : "default",
    				markdown : md,             // 初始化编辑区的内容
    				codeFold : true,
    				saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
    				searchReplace : true,
    				//watch : false,                // 关闭实时预览
    				htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
    				//toolbar  : false,             //关闭工具栏
    				taskList : true,
    				tocm : true,         // Using [TOCM]
    				tex : true,                   // 开启科学公式TeX语言支持，默认关闭
    				toolbarIcons : function() {
    		            return ["undo","redo","|","preformatted-text",
    		            	"|","clear","search","watch","preview","|","help","info"]
    		        },
    			});
    		}
    	});
    }
    
    // 表单提交事件
    form.on('submit(submitTemp)', function (data) {
    	if(!tempEditor.getMarkdown()){
    		Feng.error("模板内容不能为空！");
    		return false;
    	}
    	if(!data.field.status){
    		data.field.status = 0;
    	}
    	data.field.details = tempEditor.getMarkdown();
        var ajax = new $ax(Feng.ctxPath + "/template/addItem", function (data) {
            Feng.success("添加成功！");
            layer.closeAll();
            table.reload("tempTable");
        }, function (data) {
            Feng.error("添加失败！");
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    	
    	/*var ajax = new $ax(Feng.ctxPath + "/template/list", function (result) {
    		var flag = true;
    		var list = result.data;
    		if(list){
    			for(var i=0;i<list.length;i++){
    				if((!data.field.id || (data.field.id && list[i].id != data.field.id))
    						&& list[i].name == data.field.name){
    					flag = false;
    					Feng.error("该模板名称已存在！");
    					return false;
    				}
    			}
    		}
    		
    		if(flag){
		    	if(!data.field.status){
		    		data.field.status = 0;
		    	}
		    	data.field.details = tempEditor.getMarkdown();
		        var ajax = new $ax(Feng.ctxPath + "/template/addItem", function (data) {
		            Feng.success("添加成功！");
		            layer.closeAll();
		            table.reload("tempTable");
		        }, function (data) {
		            Feng.error("添加失败！");
		        });
		        ajax.set(data.field);
		        ajax.start();
		        return false;
    		}
    		
    	});
    	ajax.start();
    	return false;*/
    });

});