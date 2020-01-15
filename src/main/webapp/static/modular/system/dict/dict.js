layui.use(['layer', 'form', 'table', 'laydate', 'ax', 'func', 'treetable'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var treetable = layui.treetable;

    /**
     * 系统管理--字典管理
     */
    var Dict = {
        tableId: "dictTable",
        condition: {
            sysDictTypeName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Dict.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'dictId', hide: true, title: '字典id'},
            {field: 'name', sort: true, title: '字典名称', align: "center"},
            {field: 'code', sort: true, title: '字典编码', align: "center"},
            {field: 'description', sort: true, title: '字典的描述', align: "center"},
            {
                field: 'status', sort: true, title: '状态', align: "center", templet: function (d) {
                    if (d.status === 'ENABLE') {
                        return "启用";
                    } else {
                        return "禁用";
                    }
                }
            },
            {field: 'createTime', sort: true, align: "center", title: '创建时间'},
            {field: 'createUser', sort: true, align: "center", title: '创建人'},
            {field: 'sort', sort: true, align: "center", title: '排序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    // 渲染表格
    // var tableResult = table.render({
    //     elem: '#' + Dict.tableId,
    //     url: Feng.ctxPath + '/dict/ztree?dictTypeId=' + $('#dictTypeId').val(),
    //     page: true,
    //     height: "full-158",
    //     cellMinWidth: 100,
    //     cols: Dict.initColumn()
    // });

    // 渲染表格
    Dict.initTable = function (id, data) {
        return treetable.render({
            elem: '#' + id,
            url: Feng.ctxPath + '/dict/list?dictTypeId=' + $('#dictTypeId').val(),
            where: data,
            page: false,
            height: "full-158",
            cellMinWidth: 100,
            cols: Dict.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'dictId',
            treePidName: 'parentId',
            treeDefaultClose: false,
            treeLinkage: true
        });
    };
    Dict.initTable(Dict.tableId);

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Dict.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Dict.openAddDict();
    });

    // 关闭页面
    $('#btnBack').click(function () {
        window.location.href = Feng.ctxPath + "/dictType";
    });


    // 工具条点击事件
    table.on('tool(' + Dict.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Dict.onEditSysDictType(data);
        } else if (layEvent === 'delete') {
            Dict.onDeleteSysDictType(data);
        } else if (layEvent === 'detail') {
            Dict.onDetailSysDictType(data);
        }
    });


    /**
     * 点击查询按钮
     */
    Dict.search = function () {
        var queryData = {};
        queryData['menuName'] = $("#sysDictTypeName").val().trim();
        Dict.initTable(Dict.tableId, queryData);
    };

    /**
     * 弹出添加字典
     */
    Dict.openAddDict = function () {
        func.open({
            title: '添加字典',
            content: Feng.ctxPath + '/dict/dict_add?dictTypeId=' + $('#dictTypeId').val(),
            tableId: Dict.tableId
        });
    };

    /**
     * 点击编辑字典
     */
    Dict.onEditSysDictType = function (data) {
        func.open({
            title: '编辑字典类型',
            content: Feng.ctxPath + '/dict/dict_edit?dictId=' + data.id,
            tableId: Dict.tableId
        });
    };

    /**
     * 点击查看字典
     */
    Dict.onDetailSysDictType = function (data) {
        func.open({
            title: '查看字典类型',
            content: Feng.ctxPath + '/dict/dictType_detail?dictTypeId=' + data.id,
            tableId: Dict.tableId
        });
    };


    /**
     * 点击删除字典
     *
     * @param data 点击按钮时候的行数据
     */
    Dict.onDeleteSysDictType = function (data) {
        Feng.confirm("是否删除" + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/dict/delete", function () {
                Feng.success("删除成功!");
                table.reload(Dict.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dictTypeId", data.id);
            ajax.start();
        });
    };

});
