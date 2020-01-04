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
     * 系统管理--path管理
     */
    var Pointlist = {
        tableId: "pointlistTable",
        condition: {
            pointlistName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Pointlist.initColumn = function () {
        return [[
            {title: '执法船编号', field: 'shipId', sort: true, align: "center"},
            {title: '经度', field: 'lon', sort: true, align: "center"},
            {title: '纬度', field: 'lat', sort: true, align: "center"},
            {title: '航向', field: 'course', sort: true, align: "center"},
            {title: '航速', field: 'speed', sort: true, align: "center"},
            {title: '时间', field: 'createTime', sort: true, align: "center"},
            {title: '数据源，北斗/gps', field: 'deviceSource', sort: true, align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Pointlist.tableId,
        url: Feng.ctxPath + '/pointlist/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Pointlist.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Pointlist.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Pointlist.openAddPointlist();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Pointlist.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Pointlist.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Pointlist.onEditPointlist(data);
        } else if (layEvent === 'delete') {
            Pointlist.onDeletePointlist(data);
        } else if (layEvent === 'detail') {
            Pointlist.onDetailPointlist(data);
        }
    });


    /**
     * 点击查询按钮
     */
    Pointlist.search = function () {
        var queryData = {};
        queryData['pointlistName'] = $("#shipId").val().trim();
        table.reload(Pointlist.tableId, {where: queryData});
    };

    /**
     * 弹出添加path
     */
    Pointlist.openAddPointlist = function () {
        func.open({
            title: '添加path',
            content: Feng.ctxPath + '/pointlist/pointlist_add',
            tableId: Pointlist.tableId
        });
    };

    /**
     * 点击编辑path
     */
    Pointlist.onEditPointlist = function (data) {
        func.open({
            title: '编辑path',
            content: Feng.ctxPath + '/pointlist/pointlist_edit?pointlistId=' + data.id,
            tableId: Pointlist.tableId
        });
    };

    /**
     * 点击查看path
     */
    Pointlist.onDetailPointlist = function (data) {
        func.open({
            title: '查看path',
            content: Feng.ctxPath + '/pointlist/pointlist_detail?pointlistId=' + data.id,
            tableId: Pointlist.tableId
        });
    };


    /**
     * 点击删除path
     *
     * @param data 点击按钮时候的行数据
     */
    Pointlist.onDeletePointlist = function (data) {
        Feng.confirm("是否删除path " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/pointlist/delete", function () {
                Feng.success("删除成功!");
                table.reload(Pointlist.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("pointlistId", data.id);
            ajax.start();
        });
    };

    /**
     * 导出excel按钮
     */
    Pointlist.exportExcel = function () {
        var checkRows = table.checkStatus(Pointlist.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };
});
