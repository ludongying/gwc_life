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
     * 物资信息管理
     */
    var MunitionInfo = {
        tableId: "munitionInfoTable",
        condition: {
           name: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MunitionInfo.initColumn = function () {
        return [[
            {title: '编码', field: 'id', align: "center", hide:true},
            {title: '物资名称', field: 'name', align: "center"},
            {title: '物资编码', field: 'code', align: "center"},
            {title: '物资类型', field: 'typeDesp', align: "center"},
            {title: '物资规格', field: 'description', align: "center"},
            {title: '单位', field: 'unit', align: "center", hide:true},
            {title: '库存数量', field: 'inventory', align: "center"},
            {title: '最低库存数量', field: 'warnMinCount', align: "center", templet: function(d){
                    if(d.warnMinCount != null && d.warnMinCount != ''){
                        return "<div>" + d.warnMinCount + d.unit + "</div>";
                    }else{
                        return "<div></div>";
                    }
                }},
            {title: '备注', field: 'remark', align: "center", hide:true},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MunitionInfo.tableId,
        url: Feng.ctxPath + '/munitionInfo/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: MunitionInfo.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MunitionInfo.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        MunitionInfo.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MunitionInfo.openAddMunitionInfo();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + MunitionInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MunitionInfo.onEditMunitionInfo(data);
        } else if (layEvent === 'delete') {
            MunitionInfo.onDeleteMunitionInfo(data);
        } else if (layEvent === 'detail') {
            MunitionInfo.onDetailMunitionInfo(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    MunitionInfo.search = function () {
        var queryData = {};
        queryData['name'] = $("#munitionInfoName").val().trim();
        table.reload(MunitionInfo.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    MunitionInfo.btnReset = function () {
        $("#munitionInfoName").val("");
    };

    /**
     * 弹出增加物资信息
     */
    MunitionInfo.openAddMunitionInfo = function () {
        func.open({
            title: '增加物资信息',
            area: ['1000px', '300px'],
            content: Feng.ctxPath + '/munitionInfo/munitionInfo_add',
            tableId: MunitionInfo.tableId
        });
    };

    /**
     * 点击编辑物资信息
     */
    MunitionInfo.onEditMunitionInfo = function (data) {
        func.open({
            title: '编辑物资信息',
            area: ['1000px', '300px'],
            content: Feng.ctxPath + '/munitionInfo/munitionInfo_edit?munitionInfoId=' + data.id,
            tableId: MunitionInfo.tableId
        });
    };

    /**
     * 点击查看物资信息
     */
    MunitionInfo.onDetailMunitionInfo = function (data) {
        func.open({
            title: '设置库存下限预警',
            area: ['400px', '200px'],
            content: Feng.ctxPath + '/munitionInfo/munitionInfo_detail?munitionInfoId=' + data.id,
            tableId: MunitionInfo.tableId
        });
    };


    /**
     * 点击删除物资信息
     *
     * @param data 点击按钮时候的行数据
     */
    MunitionInfo.onDeleteMunitionInfo = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/munitionInfo/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(MunitionInfo.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("munitionInfoId", data.id);
            ajax.start();
        });
    };

});
