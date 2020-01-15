layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    /*var layer = layui.layer;
    var form = layui.form;

    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;*/

    /**
     * 系统管理--登录历史管理
     */
    var LoginLog = {
        tableId: "loginLogTable",
        condition: {
            loginLogName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    LoginLog.initColumn = function () {
        return [[
            {title: '主键', field: 'id', hide: true},
            {title: '日志名称', field: 'logName', sort: true, align: "center"},
            {title: '登录用户名称', field: 'userName', sort: true, align: "center"},
            {title: '创建时间', field: 'createTime', sort: true, align: "center"},
            {title: '具体消息', field: 'message', sort: true, align: "center"},
            {title: '登录ip', field: 'ipAddress', sort: true, align: "center"},
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + LoginLog.tableId,
        url: Feng.ctxPath + '/loginLog/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: LoginLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        LoginLog.search();
    });

    /*// 添加按钮点击事件
    $('#btnAdd').click(function () {
        LoginLog.openAddLoginLog();
    });

    // 导出excel
    $('#btnExp').click(function () {
        LoginLog.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + LoginLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            LoginLog.onEditLoginLog(data);
        } else if (layEvent === 'delete') {
            LoginLog.onDeleteLoginLog(data);
        } else if (layEvent === 'detail') {
            LoginLog.onDetailLoginLog(data);
        }
    });*/


    /**
     * 点击查询按钮
     */
    LoginLog.search = function () {
        var queryData = {};
        queryData['loginLogName'] = $("#loginLogName").val().time();
        table.reload(LoginLog.tableId, {where: queryData});
    };

    /*/!**
     * 弹出添加登录历史
     *!/
    LoginLog.openAddLoginLog = function () {
        func.open({
            title: '添加登录历史',
            content: Feng.ctxPath + '/loginLog/loginLog_add',
            tableId: LoginLog.tableId
        });
    };

    /!**
     * 点击编辑登录历史
     *!/
    LoginLog.onEditLoginLog = function (data) {
        func.open({
            title: '编辑登录历史',
            content: Feng.ctxPath + '/loginLog/loginLog_edit?loginLogId=' + data.id,
            tableId: LoginLog.tableId
        });
    };

    /!**
     * 点击查看登录历史
     *!/
    LoginLog.onDetailLoginLog = function (data) {
        func.open({
            title: '查看登录历史',
            content: Feng.ctxPath + '/loginLog/loginLog_detail?loginLogId=' + data.id,
            tableId: LoginLog.tableId
        });
    };


    /!**
     * 点击删除登录历史
     *
     * @param data 点击按钮时候的行数据
     *!/
    LoginLog.onDeleteLoginLog = function (data) {
        Feng.confirm("是否删除登录历史 " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/loginLog/delete", function () {
                Feng.success("删除成功!");
                table.reload(LoginLog.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("loginLogId", data.id);
            ajax.start();
        });
    };

    /!**
     * 导出excel按钮
     *!/
    LoginLog.exportExcel = function () {
        var checkRows = table.checkStatus(LoginLog.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };*/
});
