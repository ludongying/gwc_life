
layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func','laytpl'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;
    var laytpl=layui.laytpl;

    var lay={
         '$':$,
         'layer':layer,
         'form':form,
         'table':table,
         '$ax':$ax,
         'admin':admin,
         'func':func,
         'key':"law_decision",
         'verify':false
    }

    //初始化显示内容
    initShowContent($("#punishPersonType").val());
    loadVerify(form);
    money_verify(lay,"fine");
    money_verify(lay,"resourceCompensation");
    initPage();

    function initPage() {
        //编辑
        if($("#id").val()){
            var ajax = new $ax(Feng.ctxPath + "/lawRecord/decision/detail?id="+$("#id").val());
            var result = ajax.start();
            if(result){
                form.val('decisionForm',result);
                loadAddr($,form,result.punishAddrStateCode,result.punishAddrCityCode);
                $("#punishPersonType").attr("disabled","disabled")
                form.render();
                return;
            }
        }
            loadAddr($,form);
    }

    form.on('select(punishPersonType)', function(data){
        initShowContent(data.value);
        form.render();
    });
    function initShowContent(val){
        if(parseInt(val)===1) {
            setTpl("tpl_legal_person","view");
        }else{
            setTpl("tpl_citizen","view");
        }
    }

    function setTpl(tpl,view,data){
        if(!data){
            data={};
        }
        var tpl_ = $("#"+tpl).html(),view_ = $('#'+view);
        laytpl(tpl_).render(data, function(html){
            view_.html(html);
        });
    }
    //上一步
    form.on('submit(preStep)', function (data) {
        submitData("/lawRecord/reason?lawType="+$("#lawType").val(),data);
    });

    //保存
    form.on('submit(nextStep)', function (data) {
        submitData("",data);
    });

    startListen($,lay.key);
    //提交数据
    function submitData(des,data){
        //设置数据
        data.field.punishAddr= JSON.stringify(getLocAddr($,"punish_addr"));
        var url="decision/update";
        if($("#lawType").val()==2){
            url="decision_safe/update";
        }
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":url,
            "next":des,
            "data":data,
        });
    }


});
