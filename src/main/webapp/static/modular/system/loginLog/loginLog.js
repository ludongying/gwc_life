layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'element'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var element = layui.element;
    /*var layer = layui.layer;
    var form = layui.form;
    var $ZTree = layui.ztree;
    var admin = layui.admin;
    var func = layui.func;*/


    //渲染时间选择框
    laydate.render({
        elem: '#beginTime',
        type: 'datetime'
    });

    //渲染时间选择框
    laydate.render({
        elem: '#endTime',
        type: 'datetime'
    });

    //渲染时间选择框
    laydate.render({
        elem: '#beginTimeO',
        type: 'datetime'
    });

    //渲染时间选择框
    laydate.render({
        elem: '#endTimeO',
        type: 'datetime'
    });

    /**
     * 系统管理--登录历史管理
     */
    var LoginLog = {
        tableId: "loginLogTable",
        condition: {
            loginLogName: "",
            message: "",
            beginTime: "",
            endTime: ""
        }
    };

    element.on('tab(logTab)', function(elem){


    });

    /**
     * 初始化表格的列
     */
    LoginLog.initColumn = function () {
        return [[
            {title: '主键', field: 'id', hide: true},
            {title: '日志', field: 'logName', align: "center"},
            {title: '姓名', field: 'userName', align: "center"},
            {title: '登录ip', field: 'ipAddress', align: "center", width: 160},
            {title: 'MAC地址', field: 'macAddress', align: "center", width: 160},
            {title: '记录时间', field: 'createTime', align: "center", width: 160, templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {title: '具体消息', field: 'message', align: "center", width: 400},
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + LoginLog.tableId,
        url: Feng.ctxPath + '/loginLog/list',
        page: true,
        cellMinWidth: 100,
        cols: LoginLog.initColumn(),
        even: true,
        height: 'full-95'
    });




    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        LoginLog.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        LoginLog.btnReset();
    });

    // 清空所有点击事件
    $('#delete').click(function () {
        LoginLog.onDeleteLoginLog();
    });

    /**
     * 点击查询按钮
     */
    LoginLog.search = function () {
        var queryData = {};
        queryData['loginLogName'] = $("#loginLogName").val().trim();
        queryData['message'] = $("#message").val().trim();
        queryData['beginTime'] = $("#beginTime").val();
        queryData['endTime'] = $("#endTime").val();
        table.reload(LoginLog.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    LoginLog.btnReset = function () {
        $("#loginLogName").val("");
        $("#message").val("");
        $("#beginTime").val("");
        $("#endTime").val("");
    };

    /**
     * 点击删除登录历史
     * @param data 点击按钮时候的行数据
     */
    LoginLog.onDeleteLoginLog = function (data) {
        Feng.confirm("是否清空所有登录历史吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/loginLog/deleteAll", function (data) {
                if (data.success) {
                    Feng.success("清空所有成功!");
                    table.reload(LoginLog.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("清空所有失败!" + data.responseJSON.message + "!");
            });
            ajax.start();
        });
    };

    /**
     * 系统管理--操作日志管理
     */
    var OperationLog = {
        tableId: "operationLogTable",
        condition: {
            operationLogName: "",
            beginTimeO: "",
            endTimeO: ""
        }
    };

    /**
     * 初始化表格的列
     */
    OperationLog.initColumn = function () {
        return [[
            {title: '日志名称', field: 'logName', align: "center"},
            {title: '用户名称', field: 'userName', align: "center"},
            {title: '类名称', field: 'className', align: "center"},
            {title: '方法名称', field: 'method', align: "center"},
            {title: '创建时间', field: 'createTime', align: "center", width: 160, templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {title: '是否成功', field: 'succeed', align: "center"},
            {title: '具体消息', field: 'message', align: "center", width: 400}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + OperationLog.tableId,
        url: Feng.ctxPath + '/operationLog/list',
        page: true,
        height: "full-95",
        cellMinWidth: 100,
        cols: OperationLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearchO').click(function () {
        OperationLog.search();
    });
    // 重置按钮点击事件
    $('#btnResetO').click(function () {
        OperationLog.btnReset();
    });

    /**
     * 点击查询按钮
     */
    OperationLog.search = function () {
        var queryData = {};
        queryData['operationLogName'] = $("#operationLogName").val().trim();
        queryData['message'] = $("#messageO").val().trim();
        queryData['beginTime'] = $("#beginTimeO").val();
        queryData['endTime'] = $("#endTimeO").val();
        table.reload(OperationLog.tableId, {where: queryData});
    };


    /**
     * 重置查询条件
     */
    OperationLog.btnReset = function () {
        $("#operationLogName").val("");
        $("#messageO").val("");
        $("#beginTimeO").val("");
        $("#endTimeO").val("");
    };

});
