layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    let $ = layui.$;
    let layer = layui.layer;
    let form = layui.form;
    let table = layui.table;
    let $ax = layui.ax;
    let laydate = layui.laydate;
    let admin = layui.admin;
    let func = layui.func;

    /**
     * 执法记录管理
     */
    let LawRecord = {
        tableId: "lawRecordTable",
        condition: {
            lawRecordName: ""
        },
        searchType:"commonSearch"
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
                {title: '被询问人电话', field: 'investigateTel', align: "center"},
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
            cols: LawRecord.initColumn(),
            done:function(){
                this.where={};
            }
        });
    }

    /**
     * 更多 -返回
     *
     */
    $('#moreSearch').click(function () {
        $(this).parent().parent().addClass("layui-hide");
        $(this).parent().parent().next().removeClass("layui-hide");
        LawRecord.searchType="advancedSearch";
    });

    $('#backSearch').click(function () {
        $(this).parent().parent().addClass("layui-hide");
        $(this).parent().parent().prev().removeClass("layui-hide");
        LawRecord.searchType="commonSearch";
    });

    form.on('select(selectType)', function(data){
        $("#select_box").html($("#advancedSearch ."+data.value).parent().html());
        $("#select_box").find("input").eq(0).focus();
        form.render();
    });

    /**
     * 左侧搜索
     */
    $('#btnSearch').click(function () {
        let queryData=getParam(LawRecord.searchType);
        table.reload(LawRecord.tableId, {where: queryData});
    });

    function getParam(id){
        let queryData = {};
        let id_="#"+id;
        let lawCaseCode=$(id_+" .lawCaseCode").val();
        if(lawCaseCode){
            lawCaseCode=lawCaseCode.trim();
            if(lawCaseCode.length>50){
                msg_style($,"lawCaseCode","字符长度不能超过50")
                return;
            }
            queryData['lawCaseCode'] = lawCaseCode;
        }
        let shipName=$(id_+" .shipName").val();
        if(shipName){
            shipName=shipName.trim();
            if(shipName.length>50){
                msg_style($,"shipName","字符长度不能超过50")
                return;
            }
            queryData['shipName'] = shipName;
        }

        let investigateTel=$(id_+" .investigateTel").val();
        if(investigateTel){
            investigateTel=investigateTel.trim();
            if(investigateTel.length>11){
                msg_style($,"investigateTel","字符长度不能超过11")
                return;
            }
            queryData['investigateTel'] = investigateTel;
        }
        let lawType=$(id_+" .lawType").val();
        if(lawType){
            queryData['lawType'] =lawType;
        }

        let investigateName=$(id_+" .investigateName").val();
        if(investigateName){
            investigateName=investigateName.trim();
            if(investigateName.length>20){
                msg_style($,"investigateName","字符长度不能超过20")
                return;
            }
            queryData['investigateName'] = investigateName
        }
        let createTime=$(id_+" .createTime").val();
        if(createTime){
            let times = createTime.split("~");
            queryData['createStartTime'] = times[0].trim();
            queryData['createEndTime'] = times[1].trim();
        }
        return queryData;
    }

    /**
     * 重置按钮点击事件
     */
    $('#btnReset').click(function () {
        $("#conditionInput input").val("");
        $("#conditionInput select").val("");
        $("#select_box").html($("#advancedSearch .lawType").parent().html());
        form.render();
        let sel=$("#conditionInput select").eq(0);
        let val=sel.find("option").eq(0).text();
        sel.next().find("input").val(val);

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
        let data = obj.data;
        let layEvent = obj.event;
        let name=data.lawCaseCode;
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
            LawRecord.onInstrument(data);
        } else if(layEvent === 'detail'){
            window.location.href=Feng.ctxPath+"lawRecord/detail?id="+data.id;
        } else if(layEvent === 'export'){
            msg_tip($,"导出还未开发");
        } else if(layEvent === 'print'){
            msg_tip($,"打印还未开发");
        }
    });


    /**
     * 点击查看渔船信息
     */
    LawRecord.onInstrument = function (data) {
        func.open({
            title: '文书',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/lawRecord/instrument?id=' + data.id,
        });
    };






});
