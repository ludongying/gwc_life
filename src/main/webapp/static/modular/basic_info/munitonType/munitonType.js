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
     * 系统管理--物料类型管理
     */
    var MunitonType = {
        tableId: "munitonTypeTable",
        condition: {
            munitonTypeName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MunitonType.initColumn = function () {
        return [[
            {title: '', field: 'id', hide: true},
            {title: '物料类型编码', field: 'typeId', sort: true, align: "center"},
            {title: '物料类型名称', field: 'name', sort: true, align: "center"},
            {title: '显示', field: 'showFlag', sort: true, align: "center", templet: '#statusTpl'},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MunitonType.tableId,
        url: Feng.ctxPath + '/munitonType/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: MunitonType.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MunitonType.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MunitonType.openAddMunitonType();
    });

    // 导出excel
    $('#btnExp').click(function () {
        MunitonType.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MunitonType.tableId + ')', function (obj) {

        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MunitonType.onEditMunitonType(data);
        } else if (layEvent === 'delete') {
            MunitonType.onDeleteMunitonType(data);
        } else if (layEvent === 'detail') {
            MunitonType.onDetailMunitonType(data);
        }
    });


    /**
     * 点击查询按钮
     */
    MunitonType.search = function () {
        var queryData = {};
        queryData['munitonTypeName'] = $("#munitonTypeName").val();
        table.reload(MunitonType.tableId, {where: queryData});
    };

    /**
     * 弹出添加物料类型
     */
    MunitonType.openAddMunitonType = function () {
        func.open({
            title: '添加物料类型',
            content: Feng.ctxPath + '/munitonType/munitonType_add',
            tableId: MunitonType.tableId
        });
    };

    /**
     * 点击编辑物料类型
     */
    MunitonType.onEditMunitonType = function (data) {
        func.open({
            title: '编辑物料类型',
            content: Feng.ctxPath + '/munitonType/munitonType_edit?munitonTypeId=' + data.id,
            tableId: MunitonType.tableId
        });
    };

    /**
     * 点击查看物料类型
     */
    MunitonType.onDetailMunitonType = function (data) {
        func.open({
            title: '查看物料类型',
            content: Feng.ctxPath + '/munitonType/munitonType_detail?munitonTypeId=' + data.id,
            tableId: MunitonType.tableId
        });
    };


    /**
     * 点击删除物料类型
     *
     * @param data 点击按钮时候的行数据
     */
    MunitonType.onDeleteMunitonType = function (data) {
        Feng.confirm("是否删除物料类型 " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/munitonType/delete", function () {
                Feng.success("删除成功!");
                table.reload(MunitonType.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("munitonTypeId", data.id);
            ajax.start();
        });
    };

    /**
     * 导出excel按钮
     */
    MunitonType.exportExcel = function () {
        var checkRows = table.checkStatus(MunitonType.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 修改物料类型状态
     */
    form.on('switch(state)',function(data) {
        var id = data.elem.value;
        var checked = data.elem.checked ? true:false;
        if(checked) {
            var ajax = new $ax(Feng.ctxPath+"/munitonType/unfreeze",function (data) {
                Feng.success( "启用成功！")
            },function (data) {
                Feng.error("启用失败！")
                table.reload(munitonType.tableId)
            });
            ajax.set("id",id)
            ajax.start()
        } else {
           var ajax = new $ax(Feng.ctxPath+"/munitonType/freeze",function(data) {
               Feng.success("禁用成功！")
           },function (data) {
               Feng.error("禁用失败！")
               table.reload(munitionType.tableId)
           });
           ajax.set("id",id);
           ajax.start();
        }
    });

});
