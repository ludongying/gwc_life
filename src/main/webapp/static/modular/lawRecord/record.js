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
    laydate.render({
        elem: '#createTime'
        ,type: 'date'
        ,range: '~'
    });

    initPage();

    function initPage(){
        LawRecord.initColumn = function () {
            return [[
                {title: 'id', field: 'id', align: "center",hide:true},
                {title: '类型', field: 'lawType', align: "center",hide:true},
                {title: '序号', type:'numbers'},
                {title: '案件编号', field: 'lawCaseCode', align: "center"},
                {title: '案件创建时间', field: 'createDate', align: "center"},
                {title: '被执法船', field: 'shipName', align: "center"},
                {title: '被询问人', field: 'investigateName', align: "center"},
                {title: '被询问人手机号', field: 'investigateTel', align: "center"},
                {title: '案件来源', field: 'lawCaseSourceName', align: "center"},
                {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
            ]];
        };
        table.render({
            elem: '#' + LawRecord.tableId,
            url: Feng.ctxPath + '/lawRecord/list',
            page: true,
            height: "full-164",
            cellMinWidth: 100,
            cols: LawRecord.initColumn()
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
        table.reload(LawRecord.tableId, {where: queryData});
    });

    /**
     * 重置按钮点击事件
     */
    $('#btnReset').click(function () {
        LawRecord.btnReset();
    });


    /**
     * 新建生产案件
     */
    $('#createProduce').click(function () {
        window.location.href=Feng.ctxPath+"lawRecord/agency?lawType=1";
    });

    /**
     * 新建安全案件
     */
    $('#createSafe').click(function () {
        window.location.href=Feng.ctxPath+"lawRecord/agency?lawType=2";
    });
    /**
     * 工具条点击事件
     */
    table.on('tool(' + LawRecord.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'edit') {
            window.location.href=Feng.ctxPath+"lawRecord/agency?lawType="+data.lawType+"&id="+data.id;
        } else if (layEvent === 'delete') {
            LawRecord.onDeleteLawRecord(data);
        } else if (layEvent === 'detail') {
            LawRecord.onDetailLawRecord(data);
        }
    });

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
