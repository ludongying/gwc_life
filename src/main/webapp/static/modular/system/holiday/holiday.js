layui.use(['layer', 'form', 'table', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;

    /**
     * 系统管理--假日管理
     */
    var Holiday = {
        tableId: "holidayTable",
        condition: {
            holidayName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Holiday.initColumn = function () {
        return [[
            {title: '主键id', field: 'id', hide: true},
            {title: '假日名称', field: 'name', sort: true, align: "center"},
            {title: '排序', field: 'sort', sort: true, align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Holiday.tableId,
        url: Feng.ctxPath + '/holiday/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Holiday.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Holiday.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Holiday.openAddHoliday();
    });


    // 工具条点击事件
    table.on('tool(' + Holiday.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Holiday.onEditHoliday(data);
        } else if (layEvent === 'delete') {
            Holiday.onDeleteHoliday(data);
        } else if (layEvent === 'detail') {
            Holiday.onDetailHoliday(data);
        }
    });


    /**
     * 点击查询按钮
     */
    Holiday.search = function () {
        var queryData = {};
        queryData['holidayName'] = $("#holidayName").val().time();
        table.reload(Holiday.tableId, {where: queryData});
    };

    /**
     * 弹出添加假日
     */
    Holiday.openAddHoliday = function () {
        func.open({
            title: '添加假日',
            content: Feng.ctxPath + '/holiday/holiday_add',
            tableId: Holiday.tableId
        });
    };

    /**
     * 点击编辑假日
     */
    Holiday.onEditHoliday = function (data) {
        func.open({
            title: '编辑假日',
            content: Feng.ctxPath + '/holiday/holiday_edit?holidayId=' + data.id,
            tableId: Holiday.tableId
        });
    };

    /**
     * 点击查看假日
     */
    Holiday.onDetailHoliday = function (data) {
        func.open({
            title: '查看假日',
            content: Feng.ctxPath + '/holiday/holiday_detail?holidayId=' + data.id,
            tableId: Holiday.tableId
        });
    };


    /**
     * 点击删除假日
     *
     * @param data 点击按钮时候的行数据
     */
    Holiday.onDeleteHoliday = function (data) {
        Feng.confirm("是否删除假日 " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/holiday/delete", function () {
                Feng.success("删除成功!");
                table.reload(Holiday.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("holidayId", data.id);
            ajax.start();
        });
    };
});
