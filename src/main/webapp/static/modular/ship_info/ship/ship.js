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
     * 系统管理--执法船信息管理管理
     */
    var Ship = {
        tableId: "shipTable",
        condition: {
            name: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Ship.initColumn = function () {
        return [[
            {title: '编码', field: 'id', sort: true, align: "center", hide:true},
            {title: '船舶编码', field: 'shipCode', sort: true, align: "center"},
            {title: '名称', field: 'name', sort: true, align: "center"},
            {title: '船主名称', field: 'owner', sort: true, align: "center"},
            {title: '船籍', field: 'nationality', sort: true, align: "center"},
            {title: '船舶类型', field: 'type', sort: true, align: "center"},
            {title: '船长(m)', field: 'length', sort: true, align: "center"},
            {title: '船宽(m)', field: 'width', sort: true, align: "center"},
            {title: '船深(m)', field: 'deep', sort: true, align: "center"},
            {title: '吨位数', field: 'tonnage', sort: true, align: "center"},
            {title: '船体材质', field: 'material', sort: true, align: "center"},
            {title: '核定航区', field: 'area', sort: true, align: "center", hide:true},
            {title: '核载人数', field: 'peopleNum', sort: true, align: "center"},
            {title: '主功率(kW)', field: 'mainPower', sort: true, align: "center", hide:true},
            {title: '船舶图片', field: 'image', sort: true, align: "center", hide:true},
            {title: '证书编码', field: 'certificateId', sort: true, align: "center", hide:true},
            {title: '船舶制造厂', field: 'manufacturer', sort: true, align: "center", hide:true},
            {title: '完成日期', field: 'finishDate', sort: true, align: "center", hide:true},
            {title: '核定干舷(mm)', field: 'gunwaleCount', sort: true, align: "center", hide:true},
            {title: '备注', field: 'remark', sort: true, align: "center", hide:true},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Ship.tableId,
        url: Feng.ctxPath + '/ship/list',
        page: true,
        height: "full-158",
        cellMinWidth: 120,
        cols: Ship.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Ship.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Ship.openAddShip();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Ship.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Ship.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Ship.onEditShip(data);
        } else if (layEvent === 'delete') {
            Ship.onDeleteShip(data);
        } else if (layEvent === 'detail') {
            Ship.onDetailShip(data);
        }
    });


    /**
     * 点击查询按钮
     */
    Ship.search = function () {
        var queryData = {};
        queryData['name'] = $("#shipName").val().trim();
        table.reload(Ship.tableId, {where: queryData});
    };

    /**
     * 弹出添加执法船信息管理
     */
    Ship.openAddShip = function () {
        func.open({
            title: '添加执法船信息',
            content: Feng.ctxPath + '/ship/ship_add',
            tableId: Ship.tableId
        });
    };

    /**
     * 点击编辑执法船信息管理
     */
    Ship.onEditShip = function (data) {
        func.open({
            title: '编辑执法船信息',
            content: Feng.ctxPath + '/ship/ship_edit?shipId=' + data.id,
            tableId: Ship.tableId
        });
    };

    /**
     * 点击查看执法船信息管理
     */
    Ship.onDetailShip = function (data) {
        func.open({
            title: '查看执法船信息',
            content: Feng.ctxPath + '/ship/ship_detail?shipId=' + data.id,
            tableId: Ship.tableId
        });
    };


    /**
     * 点击删除执法船信息管理
     *
     * @param data 点击按钮时候的行数据
     */
    Ship.onDeleteShip = function (data) {
        Feng.confirm("是否删除执法船信息 " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/ship/delete", function () {
                Feng.success("删除成功!");
                table.reload(Ship.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("shipId", data.id);
            ajax.start();
        });
    };

    /**
     * 导出excel按钮
     */
    Ship.exportExcel = function () {
        var checkRows = table.checkStatus(Ship.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };
});
