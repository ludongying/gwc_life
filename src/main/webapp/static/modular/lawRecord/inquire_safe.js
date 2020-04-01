
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
         'key':"law_inquire_safe",
         'cancel':false,
    }


    initPage();
    loadVerify(form);
    function initPage(){
        if($("#id").val()){
            var ajax = new $ax(Feng.ctxPath + "/lawRecord/inquire_safe/detail?id="+$("#id").val());
            var result = ajax.start();
            if(result){
                form.val('inquireForm',result);
                loadAddr($,form,result.investigateAddrStateCode,result.investigateAddrCityCode);
                return;
            }
        }
        lay.cancel=true;
        lay.content=$("#shipName").val();
        lay.message="请先保存渔船船名号,然后进入下一步";
        loadAddr($,form,"32","7");
    }


    //开启表单内容监听
    startListen($,lay.key);

    //下一步
    form.on('submit(nextStep)', function (data) {
        submitData("evidence",data);
    });

    //上一步
    form.on('submit(preStep)', function (data) {
        submitData("agency",data);
    });

    function submitData(des,data){
        //获取地址
        data.field.investigateAddr= JSON.stringify(getLocAddr($,"investigate_addr"));
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"inquire_safe/update",
            "next":"/lawRecord/"+des+"?lawType="+$("#lawType").val(),
            "data":data
        });
    }

});
