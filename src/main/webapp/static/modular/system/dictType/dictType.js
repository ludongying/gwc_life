layui.use(['layer', 'form', 'table', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 系统管理--字典类型管理
     */
    var DictType = {
        tableId: "dictTypeTable",
        condition: {
            dictTypeName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    DictType.initColumn = function () {
        return [[
            {field: 'id', title: '字典类型id', hide: true},
            {field: 'name', title: '类型', align: "center", width: 200, templet: function (d) {
                var url = Feng.ctxPath + '/dict?id=' + d.id;
                return '<a style="color: #01AAED;" href="' + url + '">' + d.name + '</a>';
            }},
            {field: 'code', title: '类型编码', align: "center", width: 200, templet: function (d) {
                var url = Feng.ctxPath + '/dict?id=' + d.id;
                return '<a style="color: #01AAED;" href="' + url + '">' + d.code + '</a>';
            }},
            {field: 'systemFlag', align: "center", title: '是否是系统字典', templet: function (d) {
                if (d.systemFlag === 'Y') {
                    return "<span class='layui-badge layui-bg-blue'>是</span></b>";
                } else {
                    return "<span class='layui-badge layui-bg-cyan'>否</span></b>";
                }
            }},
            {field: 'createTime', title: '添加时间', align: "center", width: 160},
            {field: 'createUser', title: '创建人', align: "center"},
            {field: 'sort', align: "right", title: '排序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]]
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + DictType.tableId,
        url: Feng.ctxPath + '/dictType/list',
        page: true,
        cellMinWidth: 100,
        cols: DictType.initColumn(),
        even: true,
        height: 'full-95'
    });


    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        DictType.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        DictType.btnReset();
    });


    /**
     * 左侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        DictType.openAddSysDictType();
    });


    // 工具条点击事件
    table.on('tool(' + DictType.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            DictType.onEditSysDictType(data);
        } else if (layEvent === 'delete') {
            DictType.onDeleteSysDictType(data);
        } else if (layEvent === 'detail') {
            DictType.onDetailSysDictType(data);
        }
    });


    /**
     * 点击查询按钮
     */
    DictType.search = function () {
        var queryData = {};
        queryData['sysDictTypeName'] = $("#dictTypeName").val().trim();
        table.reload(DictType.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    DictType.btnReset = function () {
        $("#dictTypeName").val("");
    };

    /**
     * 弹出添加字典类型
     */
    DictType.openAddSysDictType = function () {
        func.open({
            title: '添加字典类型',
            content: Feng.ctxPath + '/dictType/dictType_add',
            tableId: DictType.tableId
        });
    };

    /**
     * 点击编辑字典类型
     */
    DictType.onEditSysDictType = function (data) {
        func.open({
            title: '编辑字典类型',
            content: Feng.ctxPath + '/dictType/dictType_edit?dictTypeId=' + data.id,
            tableId: DictType.tableId
        });
    };

    /**
     * 点击查看字典类型
     */
    DictType.onDetailSysDictType = function (data) {
        func.open({
            title: '查看字典类型',
            content: Feng.ctxPath + '/dictType/dictType_detail?dictTypeId=' + data.id,
            tableId: DictType.tableId
        });
    };


    /**
     * 点击删除字典类型
     *
     * @param data 点击按钮时候的行数据
     */
    DictType.onDeleteSysDictType = function (data) {
        Feng.confirm("是否删除字典类型《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/dictType/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(DictType.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("sysDictTypeId", data.id);
            ajax.start();
        });
    };

});
