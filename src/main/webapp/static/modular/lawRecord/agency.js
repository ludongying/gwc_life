
layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;

    var lay={
         '$':$,
         'layer':layer,
         'form':form,
         'table':table,
         '$ax':$ax,
         'admin':admin,
         'func':func,
         'key':'law_agency'
    }


    //关联时间设置
    relateTime($,laydate);
    //初始化页面内容
    initPage();
    //办案人员处理
    function getOperators(data){
        var operators=[];
        var inputs = $("#lawOperator input");
        for (var i=0;i<inputs.length;i=i+2){
            var id=$(inputs[i]).data("id");
            var lawPersonName =$(inputs[i]).val();
            var lawCredentialCode=$(inputs[i+1]).val();
            operators.push({
                "id":id,
                "lawPersonName":lawPersonName,
                "lawCredentialCode":lawCredentialCode
            });
        }
        data.field.operators=JSON.stringify(operators);

    }
    function setOperators(data){
        var inputs = $("#lawOperator input");
        if(data){
            for(var i=0,j=0;j<data.length;i += 2,j++){
                var operator = data[j];
                $(inputs[i]).data("id",operator.id);
                $(inputs[i]).val(operator.lawPersonName);
                $(inputs[i+1]).val(operator.lawCredentialCode);
            }
        }
    }

    function initPage(){
        if($("#id").val()){
            var ajax = new $ax(Feng.ctxPath + "/lawRecord/agency/detail?id="+$("#id").val());
            var result = ajax.start();
            if(result){
                form.val('agencyForm',result);
                setOperators(result.operators);
                loadAddr($,form,result.agencyAddrStateCode,result.agencyAddrCityCode);
                return;
            }
        }
         loadAddr($,form,"32","7");

    }

    //开启表单内容监听
    startListen($,lay.key);
    //下一步
    form.on('submit(nextStep)', function (data) {
        //时间格式修正
        setProspectInquire($,data);
        //设置办案人员
        getOperators(data);
        data.field.agencyAddr= JSON.stringify(getLocAddr($,"agency_addr"));
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"agency/update",
            "next":"/lawRecord/inquire?lawType="+$("#lawType").val(),
            "data":data
        });
    });


});
