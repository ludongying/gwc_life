layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 系统管理--操作日志管理
     */
    var OperationLog = {
        tableId: "operationLogTable",
        condition: {
            operationLogName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    OperationLog.initColumn = function () {
        return [[
            {title: '主键', field: 'operationLogId', sort: true, align: "center"},
            {title: '日志类型(字典)', field: 'logType', sort: true, align: "center"},
            {title: '日志名称', field: 'logName', sort: true, align: "center"},
            {title: '用户id', field: 'userId', sort: true, align: "center"},
            {title: '类名称', field: 'className', sort: true, align: "center"},
            {title: '方法名称', field: 'method', sort: true, align: "center"},
            {title: '创建时间', field: 'createTime', sort: true, align: "center"},
            {title: '是否成功(字典)', field: 'succeed', sort: true, align: "center"},
            {title: '备注', field: 'message', sort: true, align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + OperationLog.tableId,
        url: Feng.ctxPath + '/operationLog/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OperationLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OperationLog.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OperationLog.openAddOperationLog();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OperationLog.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OperationLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OperationLog.onEditOperationLog(data);
        } else if (layEvent === 'delete') {
            OperationLog.onDeleteOperationLog(data);
        } else if (layEvent === 'detail') {
            OperationLog.onDetailOperationLog(data);
        }
    });


    /**
     * 点击查询按钮
     */
    OperationLog.search = function () {
        var queryData = {};
        queryData['operationLogName'] = $("#operationLogName").val().trim();
        table.reload(OperationLog.tableId, {where: queryData});
    };

    /**
     * 弹出添加操作日志
     */
    OperationLog.openAddOperationLog = function () {
        func.open({
            title: '添加操作日志',
            content: Feng.ctxPath + '/operationLog/operationLog_add',
            tableId: OperationLog.tableId
        });
    };

    /**
     * 点击编辑操作日志
     */
    OperationLog.onEditOperationLog = function (data) {
        func.open({
            title: '编辑操作日志',
            content: Feng.ctxPath + '/operationLog/operationLog_edit?operationLogId=' + data.id,
            tableId: OperationLog.tableId
        });
    };

    /**
     * 点击查看操作日志
     */
    OperationLog.onDetailOperationLog = function (data) {
        func.open({
            title: '查看操作日志',
            content: Feng.ctxPath + '/operationLog/operationLog_detail?operationLogId=' + data.id,
            tableId: OperationLog.tableId
        });
    };


    /**
     * 点击删除操作日志
     *
     * @param data 点击按钮时候的行数据
     */
    OperationLog.onDeleteOperationLog = function (data) {
        Feng.confirm("是否删除操作日志《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/operationLog/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(OperationLog.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("operationLogId", data.id);
            ajax.start();
        });
    };

    /**
     * 导出excel按钮
     */
    OperationLog.exportExcel = function () {
        var checkRows = table.checkStatus(OperationLog.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };
});
