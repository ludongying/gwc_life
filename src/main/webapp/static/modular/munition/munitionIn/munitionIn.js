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
     * 物资入库管理
     */
    var MunitionInandout = {
        tableId: "munitionInandoutTable",
        condition: {
            munitionInandoutName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MunitionInandout.initColumn = function () {
        return [[
            {title: '出入库表单编码', field: 'id', align: "center"},
            {title: '仓库操作类型：入库为0，出库为1（枚举）', field: 'actionType', align: "center"},
            {title: '出入库物资列表详情id', field: 'detailId', align: "center"},
            {title: '备注', field: 'remark', align: "center"},
            {title: '审核状态', field: 'status', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MunitionInandout.tableId,
        url: Feng.ctxPath + '/munitionIn/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: MunitionInandout.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MunitionInandout.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        MunitionInandout.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MunitionInandout.openAddMunitionInandout();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + MunitionInandout.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MunitionInandout.onEditMunitionInandout(data);
        } else if (layEvent === 'delete') {
            MunitionInandout.onDeleteMunitionInandout(data);
        } else if (layEvent === 'detail') {
            MunitionInandout.onDetailMunitionInandout(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    MunitionInandout.search = function () {
        var queryData = {};
        queryData['munitionInandoutName'] = $("#munitionInandoutName").val().trim();
        table.reload(MunitionInandout.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    MunitionInandout.btnReset = function () {
        $("#munitionInandoutName").val("");
    };

    /**
     * 弹出增加物资入库
     */
    MunitionInandout.openAddMunitionInandout = function () {
        func.open({
            title: '增加物资入库',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/munitionIn/munitionIn_add',
            tableId: MunitionInandout.tableId
        });
    };

    /**
     * 点击编辑物资入库
     */
    MunitionInandout.onEditMunitionInandout = function (data) {
        func.open({
            title: '编辑物资入库',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/munitionIn/munitionIn_edit?munitionInandoutId=' + data.id,
            tableId: MunitionInandout.tableId
        });
    };

    /**
     * 点击查看物资入库
     */
    MunitionInandout.onDetailMunitionInandout = function (data) {
        func.open({
            title: '查看物资入库',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/munitionIn/munitionIn_detail?munitionInandoutId=' + data.id,
            tableId: MunitionInandout.tableId
        });
    };


    /**
     * 点击删除物资入库
     *
     * @param data 点击按钮时候的行数据
     */
    MunitionInandout.onDeleteMunitionInandout = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/munitionInandout/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(MunitionInandout.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("munitionInandoutId", data.id);
            ajax.start();
        });
    };

});
