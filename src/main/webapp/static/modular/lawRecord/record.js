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
    loadVerify(form)

    function initPage(){
        LawRecord.initColumn = function () {
            return [[
                {title: 'ID', field: 'id', align: "center",hide:true},
                {title: '类型', field: 'lawType', align: "center",hide:true},
                {title: '状态', field: 'status', align: "center",hide:true},
                {title: '序号', type:'numbers'},
                {title: '案件编号', field: 'lawCaseCode', align: "center"},
                {title: '案件创建时间', field: 'createDate', align: "center"},
                {title: '被执法船', field: 'shipName', align: "center"},
                {title: '被询问人', field: 'investigateName', align: "center"},
                {title: '被询问人手机号', field: 'investigateTel', align: "center"},
                {title: '案件来源', field: 'lawCaseSourceName', align: "center"},
                {title: '操作', toolbar: '#tableBar', minWidth: 360, align: 'center'}
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
        var lawCaseCode=$("#lawCaseCode").val().trim();
        if(lawCaseCode.length>50){
            msg_style($,"lawCaseCode","字符长度不能超过50")
          return;
        }
        queryData['lawCaseCode'] = lawCaseCode;
        var shipName=$("#shipName").val().trim();
        if(shipName.length>50){
            msg_style($,"shipName","字符长度不能超过50")
            return;
        }
        queryData['shipName'] = shipName;
        var investigateTel=$("#investigateTel").val().trim();
        if(investigateTel.length>11){
            msg_style($,"investigateTel","字符长度不能超过11")
            return;
        }
        queryData['investigateTel'] = investigateTel;
        queryData['lawType'] = $("#lawType").val().trim();
        var investigateName=$("#investigateName").val().trim();
        if(investigateName.length>20){
            msg_style($,"investigateName","字符长度不能超过20")
            return;
        }
        queryData['investigateName'] = investigateName
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
        $("#conditionInput input").val("");
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
     *文书模板
     */
    $('#clericalTemplate').click(function () {
        msg_tip($,"文书模板功能尚未开发");
    });

    /**
     * 工具条点击事件
     */
    table.on('tool(' + LawRecord.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var name=data.lawCaseCode;
        if(!name){
            name="此项记录";
        }else{
            name="《" +name+ "》";
        }
        if (layEvent === 'edit') {
            window.location.href=Feng.ctxPath+"lawRecord/agency?lawType="+data.lawType+"&id="+data.id;
        } else if (layEvent === 'invalid') {
            operate_table({
                $ax:$ax,
                table:table,
                tableId:LawRecord.tableId,
                data:data,
                title:"是作废"+name+"吗?",
                url:"/lawRecord/invalid"
            })
        } else if (layEvent === 'finish') {
            operate_table({
                $ax:$ax,
                table:table,
                tableId:LawRecord.tableId,
                data:data,
                title:"是结案"+name+"吗?",
                url:"/lawRecord/finish"
            })
        } else if(layEvent === 'instrument'){
            msg_tip($,"文书还未开发");
        } else if(layEvent === 'detail'){
            window.location.href=Feng.ctxPath+"lawRecord/detail?id="+data.id;
        } else if(layEvent === 'export'){
            msg_tip($,"导出还未开发");
        } else if(layEvent === 'print'){
            msg_tip($,"打印还未开发");
        }
    });





});
