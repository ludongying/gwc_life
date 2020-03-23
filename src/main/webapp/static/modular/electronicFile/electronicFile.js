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
    var ElectronicFile = {
        tableId: "lawRecordTable"
    };

    initPage();

    function initPage() {
        ElectronicFile.initColumn = function () {
            return [[
                {title: 'ID', field: 'id', align: "center", hide: true},
                {title: '类型', field: 'lawType', align: "center", hide: true},
                {title: '状态', field: 'status', align: "center", hide: true},
                {title: '序号', type: 'numbers'},
                {title: '案件编号', field: 'lawCaseCode', align: "center"},
                {title: '案件创建时间', field: 'createDate', align: "center"},
                {title: '被执法船', field: 'shipName', align: "center"},
                {title: '被询问人', field: 'investigateName', align: "center"},
                {title: '被询问人电话', field: 'investigateTel', align: "center"},
                {title: '案件来源', field: 'lawCaseSourceName', align: "center"},
                {title: '操作', toolbar: '#tableBar', minWidth: 360, align: 'center'}
            ]];
        };
        table.render({
            elem: '#' + ElectronicFile.tableId,
            url: Feng.ctxPath + '/electronic/list?status=90',
            page: true,
            height: "full-164",
            cellMinWidth: 100,
            cols: ElectronicFile.initColumn()
        });
    }

    /**
     * 左侧搜索
     */
    $('#btnSearch').click(function () {
        var queryData = {};
        queryData['lawCaseCode'] = $("#lawCaseCode").val().trim();
        queryData['shipName'] = $("#shipName").val().trim();
        queryData['investigateTel'] = $("#investigateTel").val().trim();
        queryData['lawType'] = $("#lawType").val().trim();
        queryData['investigateName'] = $("#investigateName").val().trim();
        var createTime=$("#createTime").val().trim();
        if(createTime){
            var times = createTime.split("~");
            queryData['createStartTime'] = times[0].trim();
            queryData['createEndTime'] = times[1].trim();
        }
        table.reload(ElectronicFile.tableId, {where: queryData});
    });

    /**
     * 工具条点击事件
     */
    table.on('tool(' + ElectronicFile.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'detail') {
            window.location.href=Feng.ctxPath+"electronic/detail?id="+data.id;
        }
    });

});