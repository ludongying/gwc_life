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
     * 历史报警管理
     */
    var ShipEquipmentWarn = {
        tableId: "shipEquipmentWarnTable",
        condition: {
            shipEquipmentWarnName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    ShipEquipmentWarn.initColumn = function () {
        return [[
            {title: 'ID', field: 'id', align: "center"},
            {title: '设备id', field: 'equipId', align: "center"},
            {title: '告警类型', field: 'warnType', align: "center"},
            {title: '报警内容', field: 'warnDescribe', align: "center"},
            {title: '创建时间', field: 'createDate', align: "center"},
            // {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ShipEquipmentWarn.tableId,
        url: Feng.ctxPath + '/shipEquipmentWarn/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: ShipEquipmentWarn.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ShipEquipmentWarn.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        ShipEquipmentWarn.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ShipEquipmentWarn.openAddShipEquipmentWarn();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + ShipEquipmentWarn.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ShipEquipmentWarn.onEditShipEquipmentWarn(data);
        } else if (layEvent === 'delete') {
            ShipEquipmentWarn.onDeleteShipEquipmentWarn(data);
        } else if (layEvent === 'detail') {
            ShipEquipmentWarn.onDetailShipEquipmentWarn(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    ShipEquipmentWarn.search = function () {
        var queryData = {};
        queryData['shipEquipmentWarnName'] = $("#shipEquipmentWarnName").val().trim();
        table.reload(ShipEquipmentWarn.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    ShipEquipmentWarn.btnReset = function () {
        $("#shipEquipmentWarnName").val("");
    };

    /**
     * 弹出增加历史报警
     */
    ShipEquipmentWarn.openAddShipEquipmentWarn = function () {
        func.open({
            title: '增加历史报警',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/shipEquipmentWarn/shipEquipmentWarn_add',
            tableId: ShipEquipmentWarn.tableId
        });
    };

    /**
     * 点击编辑历史报警
     */
    ShipEquipmentWarn.onEditShipEquipmentWarn = function (data) {
        func.open({
            title: '编辑历史报警',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/shipEquipmentWarn/shipEquipmentWarn_edit?shipEquipmentWarnId=' + data.id,
            tableId: ShipEquipmentWarn.tableId
        });
    };

    /**
     * 点击查看历史报警
     */
    ShipEquipmentWarn.onDetailShipEquipmentWarn = function (data) {
        func.open({
            title: '查看历史报警',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/shipEquipmentWarn/shipEquipmentWarn_detail?shipEquipmentWarnId=' + data.id,
            tableId: ShipEquipmentWarn.tableId
        });
    };


    /**
     * 点击删除历史报警
     *
     * @param data 点击按钮时候的行数据
     */
    ShipEquipmentWarn.onDeleteShipEquipmentWarn = function (data) {
        Feng.confirm("是否删除历史报警《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/shipEquipmentWarn/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(ShipEquipmentWarn.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("shipEquipmentWarnId", data.id);
            ajax.start();
        });
    };

});
