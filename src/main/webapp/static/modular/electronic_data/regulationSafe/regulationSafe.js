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
     * 法律法规/航线安全管理
     */
    var RegulationSafe = {
        tableId: "regulationSafeTable",
        condition: {
            regulationSafeName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    RegulationSafe.initColumn = function () {
        return [[
            {title: 'ID', field: 'id', align: "center"},
            {title: '法规id', field: 'lawRegularId', align: "center"},
            {title: '文档名称', field: 'name', align: "center"},
            {title: '创建时间', field: 'createDate', align: "center"},
            {title: '法律法规/航行安全', field: 'type', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + RegulationSafe.tableId,
        url: Feng.ctxPath + '/regulationSafe/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: RegulationSafe.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        RegulationSafe.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        RegulationSafe.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        RegulationSafe.openAddRegulationSafe();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + RegulationSafe.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            RegulationSafe.onEditRegulationSafe(data);
        } else if (layEvent === 'delete') {
            RegulationSafe.onDeleteRegulationSafe(data);
        } else if (layEvent === 'detail') {
            RegulationSafe.onDetailRegulationSafe(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    RegulationSafe.search = function () {
        var queryData = {};
        queryData['regulationSafeName'] = $("#regulationSafeName").val().trim();
        table.reload(RegulationSafe.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    RegulationSafe.btnReset = function () {
        $("#regulationSafeName").val("");
    };

    /**
     * 弹出增加法律法规/航线安全
     */
    RegulationSafe.openAddRegulationSafe = function () {
        func.open({
            title: '增加法律法规/航线安全',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/regulationSafe/regulationSafe_add',
            tableId: RegulationSafe.tableId
        });
    };

    /**
     * 点击编辑法律法规/航线安全
     */
    RegulationSafe.onEditRegulationSafe = function (data) {
        func.open({
            title: '编辑法律法规/航线安全',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/regulationSafe/regulationSafe_edit?regulationSafeId=' + data.id,
            tableId: RegulationSafe.tableId
        });
    };

    /**
     * 点击查看法律法规/航线安全
     */
    RegulationSafe.onDetailRegulationSafe = function (data) {
        func.open({
            title: '查看法律法规/航线安全',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/regulationSafe/regulationSafe_detail?regulationSafeId=' + data.id,
            tableId: RegulationSafe.tableId
        });
    };


    /**
     * 点击删除法律法规/航线安全
     *
     * @param data 点击按钮时候的行数据
     */
    RegulationSafe.onDeleteRegulationSafe = function (data) {
        Feng.confirm("是否删除法律法规/航线安全《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/regulationSafe/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(RegulationSafe.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("regulationSafeId", data.id);
            ajax.start();
        });
    };

});
