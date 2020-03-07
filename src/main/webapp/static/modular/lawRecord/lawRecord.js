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
     * 执法记录管理
     */
    var LawRecord = {
        tableId: "lawRecordTable",
        condition: {
            lawRecordName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    LawRecord.initColumn = function () {
        return [[
            {title: 'id', field: 'id', align: "center",hide:true},
            {title: '序号', type:'numbers'},
            {title: '案件创建时间', field: 'createDate', align: "center"},
            {title: '被执法船', field: ' illegalShipName', align: "center"},
            {title: '被执法负责人', field: ' illegalPerson', align: "center"},
            {title: '被询问人手机号', field: ' illegalPersonTel', align: "center"},
            {title: '案件来源', field: 'source', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + LawRecord.tableId,
        url: Feng.ctxPath + '/lawRecord/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: LawRecord.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        LawRecord.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        LawRecord.btnReset();
    });


    /**
     * 右侧操作
     */
    //新建生产案件
    $('#createProduce').click(function () {
        window.location.href=Feng.ctxPath+"lawRecord/agency?lawType=1&id=af23cfb101a773d2daf55ebab8d4e5d7";
    });

    //新建安全案件
    $('#createSafe').click(function () {
        window.location.href=Feng.ctxPath+"lawRecord/agency?lawType=2&id=e93f3bd555b8c492300bc4b22dc62d8e";
    });
    /**
     * 工具条点击事件
     */
    table.on('tool(' + LawRecord.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            LawRecord.onEditLawRecord(data);
        } else if (layEvent === 'delete') {
            LawRecord.onDeleteLawRecord(data);
        } else if (layEvent === 'detail') {
            LawRecord.onDetailLawRecord(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    LawRecord.search = function () {
        var queryData = {};
        queryData['lawRecordName'] = $("#lawRecordName").val().trim();
        table.reload(LawRecord.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    LawRecord.btnReset = function () {
        $("#lawRecordName").val("");
    };

    /**
     * 弹出增加执法记录
     */
    LawRecord.openAddLawRecord = function () {
        func.open({
            title: '增加执法记录',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/lawRecord/lawRecord_add',
            tableId: LawRecord.tableId
        });
    };

    /**
     * 点击编辑执法记录
     */
    LawRecord.onEditLawRecord = function (data) {
        func.open({
            title: '编辑执法记录',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/lawRecord/lawRecord_edit?lawRecordId=' + data.id,
            tableId: LawRecord.tableId
        });
    };

    /**
     * 点击查看执法记录
     */
    LawRecord.onDetailLawRecord = function (data) {
        func.open({
            title: '查看执法记录',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/lawRecord/lawRecord_detail?lawRecordId=' + data.id,
            tableId: LawRecord.tableId
        });
    };


    /**
     * 点击删除执法记录
     *
     * @param data 点击按钮时候的行数据
     */
    LawRecord.onDeleteLawRecord = function (data) {
        Feng.confirm("是否删除执法记录《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/lawRecord/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(LawRecord.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("lawRecordId", data.id);
            ajax.start();
        });
    };

});
