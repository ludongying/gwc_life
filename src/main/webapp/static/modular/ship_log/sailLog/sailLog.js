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
     * 航行日志管理
     */
    var SailLog = {
        tableId: "sailLogTable",
        condition: {
            sailLogName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    SailLog.initColumn = function () {
        return [[
            {title: '序号', field: 'id', align: "center",hide:true},
            {title: '船只编号', field: 'shipId', align: "center",hide:true},
            {title: '船舶名称', field: 'shipName',sort:false,align: "center"},
            // {title: '日期', field: 'date', align: "center"},
            {title: '开始时间', field: 'startTime', align: "center",width: 160,templet: "<div>{{layui.util.toDateString(d.startTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {title: '结束时间', field: 'endTime', align: "center",width: 160, templet: "<div>{{layui.util.toDateString(d.endTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {title: '航行记录内容', field: 'content', align: "center"},
            {title: '气象海况记录', field: 'weather', align: "center"},
            {title: '创建人编号', field: 'createPerson',align: "center",hide:true},
            {title: '创建人', field: 'createPersonName', sort:false,align: "center",width: 90,},
            {title: '创建时间', field: 'createDate', align: "center",width: 160,templet: "<div>{{layui.util.toDateString(d.createDate, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {title: '修改人', field: 'updatePerson', align: "center",hide:true},
            {title: '修改时间', field: 'updateDate', align: "center",hide:true},
            {title: '操作', toolbar: '#tableBar', Width: 200, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + SailLog.tableId,
        url: Feng.ctxPath + '/sailLog/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: SailLog.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        SailLog.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        SailLog.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        SailLog.openAddSailLog();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + SailLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            SailLog.onEditSailLog(data);
        } else if (layEvent === 'delete') {
            SailLog.onDeleteSailLog(data);
        } else if (layEvent === 'detail') {
            SailLog.onDetailSailLog(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    SailLog.search = function () {
        var queryData = {};
        queryData['sailLogName'] = $("#sailLogName").val().trim();
        table.reload(SailLog.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    SailLog.btnReset = function () {
        $("#sailLogName").val("");
    };

    /**
     * 弹出增加航行日志
     */
    SailLog.openAddSailLog = function () {
        func.open({
            title: '增加航行日志',
            area: ['1000px', '580px'],
            content: Feng.ctxPath + '/sailLog/sailLog_add',
            tableId: SailLog.tableId
        });
    };

    /**
     * 点击编辑航行日志
     */
    SailLog.onEditSailLog = function (data) {
        func.open({
            title: '编辑航行日志',
            area: ['1000px', '580px'],
            content: Feng.ctxPath + '/sailLog/sailLog_edit?sailLogId=' + data.id,
            tableId: SailLog.tableId
        });
    };

    /**
     * 点击查看航行日志
     */
    SailLog.onDetailSailLog = function (data) {
        func.open({
            title: '查看航行日志',
            area: ['1000px', '580px'],
            content: Feng.ctxPath + '/sailLog/sailLog_detail?sailLogId=' + data.id,
            tableId: SailLog.tableId
        });
    };


    /**
     * 点击删除航行日志
     *
     * @param data 点击按钮时候的行数据
     */
    SailLog.onDeleteSailLog = function (data) {
        Feng.confirm("是否删除航行日志《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/sailLog/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(SailLog.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("sailLogId", data.id);
            ajax.start();
        });
    };
});
