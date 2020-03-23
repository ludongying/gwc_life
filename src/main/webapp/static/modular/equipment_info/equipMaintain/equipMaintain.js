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
     * 设备维护管理
     */
    var EquipMaintain = {
        tableId: "equipMaintainTable",
        condition: {
            equipMaintainName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    EquipMaintain.initColumn = function () {
        return [[
            {title: '', field: 'id', align: "center", hide:true},
            {title: '设备名称', field: 'equipId', align: "center"},
            {title: '设备型号', field: 'equipId', align: "center"},
            {title: '工作类型', field: 'problemType', align: "center"},
            {title: '开始时间', field: 'startTime', align: "center"},
            {title: '结束时间', field: 'endTime', align: "center"},
            {title: '消耗物料备件', field: 'startTime', align: "center"},
            {title: '工作内容', field: 'endTime', align: "center"},
            {title: '备注', field: 'remark', align: "center", hide:true},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + EquipMaintain.tableId,
        url: Feng.ctxPath + '/equipMaintain/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: EquipMaintain.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        EquipMaintain.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        EquipMaintain.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        EquipMaintain.openAddEquipMaintain();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + EquipMaintain.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            EquipMaintain.onEditEquipMaintain(data);
        } else if (layEvent === 'delete') {
            EquipMaintain.onDeleteEquipMaintain(data);
        } else if (layEvent === 'detail') {
            EquipMaintain.onDetailEquipMaintain(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    EquipMaintain.search = function () {
        var queryData = {};
        queryData['equipMaintainName'] = $("#equipMaintainName").val().trim();
        table.reload(EquipMaintain.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    EquipMaintain.btnReset = function () {
        $("#equipMaintainName").val("");
    };

    /**
     * 弹出增加设备维护
     */
    EquipMaintain.openAddEquipMaintain = function () {
        func.open({
            title: '增加设备维护',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/equipMaintain/equipMaintain_add',
            tableId: EquipMaintain.tableId
        });
    };

    /**
     * 点击编辑设备维护
     */
    EquipMaintain.onEditEquipMaintain = function (data) {
        func.open({
            title: '编辑设备维护',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/equipMaintain/equipMaintain_edit?equipMaintainId=' + data.id,
            tableId: EquipMaintain.tableId
        });
    };

    /**
     * 点击查看设备维护
     */
    EquipMaintain.onDetailEquipMaintain = function (data) {
        func.open({
            title: '查看设备维护',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/equipMaintain/equipMaintain_detail?equipMaintainId=' + data.id,
            tableId: EquipMaintain.tableId
        });
    };


    /**
     * 点击删除设备维护
     *
     * @param data 点击按钮时候的行数据
     */
    EquipMaintain.onDeleteEquipMaintain = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/equipMaintain/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(EquipMaintain.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("equipMaintainId", data.id);
            ajax.start();
        });
    };

});
