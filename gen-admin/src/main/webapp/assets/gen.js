layui.use(['layer', 'ax', 'form', 'laydate', 'element', 'table'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    var element = layui.element;
    var table = layui.table;

    var Code = {
        tableNames: "",
        tables: {}
    };

    $('#code_gen').click(function () {
        window.location.href = Feng.ctxPath + "/";
    });

    $('#template_config').click(function () {
    	window.location.href = Feng.ctxPath + "/template";
    });

    $('#db_config').click(function () {
        window.location.href = Feng.ctxPath + "/db";
    });

    table.render({
        elem: '#dbTableList'
        , url: Feng.ctxPath + '/db/tableList'
        , page: false
        , cols: [[
            {type: 'checkbox'}
            , {field: 'tableName', title: '表的名称'}
            , {field: 'tableComment', title: '表的名称注释'}
        ]]
    });

    table.on('checkbox(dbTableList)', function (obj) {
        var checkStatus = table.checkStatus('dbTableList');
        var tableNames = "";
        for (var tableItem in checkStatus.data) {
            tableNames += "CAT" + checkStatus.data[tableItem].tableName;
        }
        Code.tableNames = tableNames;
    });

    form.on('select(dataSourceId)', function (data) {
        var dbId = data.value;
        table.reload("dbTableList", {where: {dbId: dbId}});
    });

    form.on('submit(execute)', function (data) {
    	if(!Code.tableNames){
    		Feng.error("数据表不能为空！");
    		return false
    	}
		var dataSourceId = $("#dataSourceId").val();
		var removePrefix = $("#removePrefix").val();
		var removeFieldPrefix = $("#removeFieldPrefix").val();
		var author = $("#author").val();
		var proPackage = $("#proPackage").val();
		var namingStrategy = $("#namingStrategy").val();
		var entityLombokModel = $("#entityLombokModel").val();
		
		window.location.href = Feng.ctxPath + "/execute?dataSourceId=" + dataSourceId + "&author="
		+ author + "&proPackage=" + proPackage + "&removePrefix=" + removePrefix + "&removeFieldPrefix="
		+ removeFieldPrefix + "&tables=" + Code.tableNames + "&namingStrategy=" + namingStrategy + "&entityLombokModel=" + entityLombokModel;
    	return false
    });

});
