
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
          'key':"law_reason"
    }

    initPage();

    function initPage(){
        if($("#id").val()){
            var ajax = new $ax(Feng.ctxPath + "/lawRecord/reason/detail?id="+$("#id").val());
            var result = ajax.start();
            //编辑
            if(result.mainReason){
                form.val('reasonForm',result);
                setData(result.secondReason);
                return;
            }
        }
        initRadio();
    }


    //初始化页面 单选
    function initRadio(){
        $("#secondReason div.layui-form-checkbox").eq(0).data("relateFlag",1);
        form.on('radio(mainReason)', function(data){
            var val=data.value;
            $("#secondReason div.layui-form-checkbox").each(function(index,item){
                if($(item).data("relateFlag")){
                    $(item).data("relateFlag",0);
                    $(item).removeClass("layui-form-checked");
                    return false;
                }
            });
            $("#secondReason input").each(function(index,item){
                if($(item).val()===val){
                    if(!$(item).next().hasClass("layui-form-checked")){
                        $(item).next().addClass("layui-form-checked");
                        $(item).next().data("relateFlag",1);
                    }
                    return false;
                }
            })
        });
    }

    function getData(){
        var second=[];
        $("#secondReason div.layui-form-checked").each(function(index,item){
           second.push($(item).prev().val());
        })
        return second;
    }

    function setData(arr){
        if(arr){
            $("#secondReason div.layui-form-checkbox").each(function(index,item){
                if(arr.includes(parseInt($(item).prev().val()))){
                    if(!$(item).hasClass("layui-form-checked")){
                        $(item).data("relateFlag",0);
                        $(item).addClass("layui-form-checked")
                    }
                }else{
                    if($(item).hasClass("layui-form-checked")){
                        $(item).removeClass("layui-form-checked");
                    }
                }
            });
        }

    }

    //下一步
    form.on('submit(nextStep)', function (data) {
        submitData("decision",data);
    });
    //上一步
    form.on('submit(preStep)', function (data) {
        submitData("evidence",data);
    });

    startListen($,lay.key);
    //提交数据
    function submitData(des,data){
        //设置数据
        data.field.secondReason=JSON.stringify(getData());
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"reason/update",
            "next":"/lawRecord/"+des+"?lawType="+$("#lawType").val(),
            "data":data
        });
    }


});
