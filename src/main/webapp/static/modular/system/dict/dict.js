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
            {field: 'dictId', title: '字典id', hide: true},
            {field: 'name', title: '字典', align: "center"},
            {field: 'createTime', title: '创建时间', align: "center", templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {field: 'createUser', title: '创建人', align: "center"},
            {field: 'sort', title: '排序', align: "center"},
            {align: 'center', toolbar: '#tableBar', width: 200, title: '操作'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Dict.tableId,
        url: Feng.ctxPath + '/dict/list?dictTypeId=' + $('#dictTypeId').val(),
        page: true,
        cellMinWidth: 100,
        cols: Dict.initColumn(),
        height: 'full-97'
    });

    /**
     * 左侧搜索
     */
    // 关闭页面
    $('#btnBack').click(function () {
        window.location.href = Feng.ctxPath + "/dictType";
    });
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Dict.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        Dict.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Dict.openAddDict();
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
     * 点击搜索按钮
     */
    Dict.search = function () {
        var queryData = {};
        queryData['menuName'] = $("#sysDictTypeName").val().trim();
        Dict.initTable(Dict.tableId, queryData);
    };

    /**
     * 重置查询条件
     */
    Dict.btnReset = function () {
        $("#sysDictTypeName").val("");
    };

    /**
     * 弹出增加字典
     */
    Dict.openAddDict = function () {
        func.open({
            title: '增加字典',
            area: ['500px','500px'],
            content: Feng.ctxPath + '/dict/dict_add?dictTypeId=' + $('#dictTypeId').val(),
            tableId: Dict.tableId
        });
    };

    /**
     * 点击编辑字典
     */
    Dict.onEditSysDictType = function (data) {
        console.log(data);
        func.open({
            title: '编辑字典',
            area: ['500px','500px'],
            content: Feng.ctxPath + '/dict/dict_edit?dictId=' + data.id,
            tableId: Dict.tableId
        });
    };

    /**
     * 点击查看字典
     */
    Dict.onDetailSysDictType = function (data) {
        func.open({
            title: '查看字典',
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
        Feng.confirm("是否删除字典《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/dict/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(Dict.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dictTypeId", data.id);
            ajax.start();
        });
    };

});
