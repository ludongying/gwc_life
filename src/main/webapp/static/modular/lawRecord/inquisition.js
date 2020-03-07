
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
         'key':'law_inquisition'
    }

    //编辑
    if($("#id").val()){
        var ajax = new $ax(Feng.ctxPath + "/lawRecord/inquisition/detail?id="+$("#id").val());
        var result = ajax.start();
        if(result){
            form.val('inquisitionForm',result);
        }
    }

    //开启表单内容监听
    startListen($,lay.key);

    //下一步
    form.on('submit(nextStep)', function (data) {
        submitData("evidence",data);
    });

    //上一步
    form.on('submit(preStep)', function (data) {
        submitData("inquire",data);
    });

    function submitData(des,data){
        //获取地址
        data.field.investigateAddr= JSON.stringify(getLocAddr($,"investigate_addr"));
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"inquisition/update",
            "next":"/lawRecord/"+des+"?lawType="+$("#lawType").val(),
            "data":data
        });
    }

});
