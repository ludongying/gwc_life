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
         elem: '.createTime'
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

    //初始值设置
    $("#select_box").html($("#advancedSearch .lawCaseCode").parent().html());

    /**
     * 左侧搜索
     */
    $('#btnSearch').click(function () {
        let queryData=getParam(LawRecord.searchType);
        if (queryData != null){
            table.reload(LawRecord.tableId, {where: queryData});
        }
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
        sel.val("lawCaseCode");
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
            let result=LawRecord.onInstrument(data);
            if(result){
                $(this).next().removeClass("layui-hide")
                $(this).next().next().removeClass("layui-hide")
            }
        } else if(layEvent === 'detail'){
            window.location.href=Feng.ctxPath+"lawRecord/detail?id="+data.id;
        } else if(layEvent === 'export'){
            LawRecord.export(data);
        } else if(layEvent === 'print'){
            LawRecord.preview(data);
        }
    });


    /**
     * 点击查看渔船信息
     */
    LawRecord.onInstrument = function (data) {
       return  func.open({
            title: '文书',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/lawRecord/instrument?id=' + data.id
        });

    };


    /**
     * 导出文件
     */
    LawRecord.export = function (data) {
        let ajax = new $ax(Feng.ctxPath + "/lawRecord/instrument/generate?id=" +data.id);
        let result = ajax.start();
        if(result.success){
            Feng.success("案件文书已生成，正在导出文件...")
            downFile($,result.content);
        }else{
            Feng.error("案件文书生成失败")
        }

    };


    /**
     * 预览文件
     */
    LawRecord.preview = function (data) {
        let ajax = new $ax(Feng.ctxPath + "/lawRecord/instrument/generate?id=" +data.id);
        let result = ajax.start();
        if(result.success){
            Feng.success("案件文书已生成，正在生成预览文件...")
            $.ajax({
                url:Feng.ctxPath + "/lawRecord/instrument/generatePdf",
                dataType: 'json',
                type: 'post',
                data:{path:result.content},
                success: function (result_pdf) {
                    Feng.success("案件文书已生成，正在跳转打印预览...")
                    preview($,Feng.ctxPath + "/lawRecord/instrument/preview",result_pdf.content);

                },
                error:function(data){
                    Feng.error("预览文件生成失败...")
                }
            });
        }else{
            Feng.error("案件文书生成失败")
        }

    };


    /**
     * 跳转预览页面
     * @param $
     * @param url
     * @param path
     */
    function preview($,url,path){
        let exportForm = $("<form action='"+url+"' method='post' target='_blank'></form>")
        exportForm.append("<input type='hidden' name='path' value='"+path+"'/>")
        $(document.body).append(exportForm);
        exportForm.submit();
        exportForm.remove();
    }



});
