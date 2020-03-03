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
     * 渔船信息管理
     */
    var FishShip = {
        tableId: "fishShipTable",
        condition: {
            fishShipName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    FishShip.initColumn = function () {
        return [[
            {title: '渔船id', field: 'id', hide: true, align: "center"},
            {title: '船编号', field: 'code', align: "center"},
            {title: '船名称', field: 'name', align: "center"},
            {title: '船主名称', field: 'shipOwner', align: "center"},
            {title: '船主电话', field: 'phone', align: "center"},
            {title: '船舶类型', field: 'shipTypeName', align: "center"},
            {title: '船马力', field: 'totalPower', align: "center"},
            {title: '建成日期', field: 'productDate', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + FishShip.tableId,
        url: Feng.ctxPath + '/fishShip/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: FishShip.initColumn()
    });

    //获取船舶类型下拉框
    $.ajax({
        url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=SHIP_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#shipType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        FishShip.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        FishShip.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        FishShip.openAddFishShip();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + FishShip.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'edit') {
            FishShip.onEditFishShip(data);
        } else if (layEvent === 'delete') {
            FishShip.onDeleteFishShip(data);
        } else if (layEvent === 'detail') {
            FishShip.onDetailFishShip(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    FishShip.search = function () {
        var queryData = {};
        queryData['code'] = $("#code").val().trim();
        queryData['phone'] = $("#phone").val().trim();
        queryData['shipType'] = $("#shipType").val();
        table.reload(FishShip.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    FishShip.btnReset = function () {
        $("#code").val("");
        $("#phone").val("");
        $("#shipType").empty();

        $.ajax({
            url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=SHIP_TYPE',
            dataType: 'json',
            type: 'get',
            success: function (data) {
                $('#shipType').append(new Option("请选择船舶类型", ""));
                $.each(data, function (index, item) {
                    $('#shipType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                });
                layui.form.render("select");
            }
        });
    };

    /**
     * 弹出增加渔船信息
     */
    FishShip.openAddFishShip = function () {
        func.open({
            title: '增加渔船信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/fishShip/fishShip_add',
            tableId: FishShip.tableId
        });
    };

    /**
     * 点击编辑渔船信息
     */
    FishShip.onEditFishShip = function (data) {
        func.open({
            title: '编辑渔船信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/fishShip/fishShip_edit?fishShipId=' + data.id,
            tableId: FishShip.tableId
        });
    };

    /**
     * 点击查看渔船信息
     */
    FishShip.onDetailFishShip = function (data) {
        func.open({
            title: '查看渔船信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/fishShip/fishShip_detail?fishShipId=' + data.id,
            tableId: FishShip.tableId
        });
    };


    /**
     * 点击删除渔船信息
     *
     * @param data 点击按钮时候的行数据
     */
    FishShip.onDeleteFishShip = function (data) {
        Feng.confirm("是否删除渔船信息《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/fishShip/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(FishShip.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("fishShipId", data.id);
            ajax.start();
        });
    };

});
