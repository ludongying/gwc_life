layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
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
            {title: '字典类型id', field: 'id', hide: true},
            {
                field: 'name', sort: true, title: '类型名称', align: "center", templet: function (d) {
                    var url = Feng.ctxPath + '/dict?id=' + d.id;
                    return '<a style="color: #01AAED;" href="' + url + '">' + d.name + '</a>';
                }
            }, {
                field: 'code', sort: true, align: "center", title: '类型编码', templet: function (d) {
                    var url = Feng.ctxPath + '/dict?id=' + d.id;
                    return '<a style="color: #01AAED;" href="' + url + '">' + d.code + '</a>';
                }
            },
            {
                field: 'systemFlag', sort: true, align: "center", title: '是否是系统字典', templet: function (d) {
                    if (d.systemFlag === 'Y') {
                        return "是";
                    } else {
                        return "否";
                    }
                }
            },
            {field: 'description', sort: true, align: "center", title: '字典描述'},
            {
                field: 'status', align: "center", title: '状态', templet: function (d) {
                    if (d.status === 'ENABLE') {
                        return "启用";
                    } else {
                        return "禁用";
                    }
                }
            },
            {field: 'createTime', sort: true,align: "center", title: '添加时间'},
            {field: 'createUser', sort: true,align: "center", title: '创建人'},
            {field: 'sort', sort: true,align: "center", title: '排序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]]
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + DictType.tableId,
        url: Feng.ctxPath + '/dictType/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: DictType.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        DictType.search();
    });

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
        Feng.confirm("是否删除" + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/dictType/delete", function () {
                Feng.success("删除成功!");
                table.reload(DictType.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("sysDictTypeId", data.id);
            ajax.start();
        });
    };

});
