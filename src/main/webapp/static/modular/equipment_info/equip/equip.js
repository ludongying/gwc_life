layui.use(['layer', 'form', 'table', 'ztree', 'ax', 'func'], function () {
    var $ = layui.$;
    // var layer = layui.layer;
    // var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    // var laydate = layui.laydate;
    // var admin = layui.admin;
    var func = layui.func;

    /**
     * 设备信息管理
     */
    var Equip = {
        tableId: "equipTable",
        condition: {
            equipName: "",
            treeId: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Equip.initColumn = function () {
        return [[
            {title: '', field: 'id', align: "center", hide:true},
            {title: '设备名称', field: 'name', align: "center"},
            {title: '设备类型', field: 'typeDesp', align: "center"},
            {title: '设备型号', field: 'specification', align: "center"},
            {title: '生产厂商', field: 'producer', align: "center", hide:true},
            {title: '出厂编码', field: 'serialNumber', align: "center"},
            {title: '图纸编码', field: 'drawingNumber', align: "center", hide:true},
            {title: '出厂日期', field: 'produceDate', align: "center"},
            {title: '保养周期', field: 'maintainCycle', align: "center"},
            {title: '设备状态', field: 'stateDesp', align: "center"},
            {title: '备注', field: 'remark', align: "center", hide:true},
            {title: '最近保养时间', field: 'lastMaintenanceDate', align: "center", hide:true},
            {title: '设备信息', field: 'info', align: "center", hide:true},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Equip.tableId,
        url: Feng.ctxPath + '/equip/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: Equip.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Equip.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        Equip.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Equip.openAddEquip();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + Equip.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Equip.onEditEquip(data);
        } else if (layEvent === 'delete') {
            Equip.onDeleteEquip(data);
        } else if (layEvent === 'detail') {
            Equip.onDetailEquip(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    Equip.search = function () {
        var queryData = {};
        queryData['equipName'] = $("#equipName").val().trim();
        queryData['equipType'] = Equip.condition.treeId;
        table.reload(Equip.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    Equip.btnReset = function () {
        $("#equipName").val("");
    };

    /**
     * 弹出增加设备信息
     */
    Equip.openAddEquip = function () {
        func.open({
            title: '增加设备信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/equip/equip_add?treeId=' + Equip.condition.treeId,
            tableId: Equip.tableId
        });
    };

    /**
     * 点击编辑设备信息
     */
    Equip.onEditEquip = function (data) {
        func.open({
            title: '编辑设备信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/equip/equip_edit?equipId=' + data.id,
            tableId: Equip.tableId
        });
    };

    /**
     * 点击查看设备信息
     */
    Equip.onDetailEquip = function (data) {
        func.open({
            title: '查看设备信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/equip/equip_detail?equipId=' + data.id,
            tableId: Equip.tableId
        });
    };


    /**
     * 点击删除设备信息
     *
     * @param data 点击按钮时候的行数据
     */
    Equip.onDeleteEquip = function (data) {
        Feng.confirm("是否删除设备信息《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/equip/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(Equip.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("equipId", data.id);
            ajax.start();
        });
    };

    Equip.onClickTree = function (e, treeId, treeNode) {
        Equip.condition.treeId = treeNode.id;
        Equip.search();
    };

    var ztree = new $ZTree("equipTypeTree", "/dict/getDictTreeByDictTypeCode?dictTypeCode=EQUIPMENT_TYPE");
    ztree.bindOnClick(Equip.onClickTree);
    ztree.init();

});
