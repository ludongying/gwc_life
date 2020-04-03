
layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func','laytpl','element'], function () {
    let $ = layui.$;
    let layer = layui.layer;
    let form = layui.form;
    let table = layui.table;
    let $ax = layui.ax;
    let laydate = layui.laydate;
    let admin = layui.admin;
    let func = layui.func;
    let laytpl=layui.laytpl;
    let element=layui.element;
    let lay={
         '$':$,
         'layer':layer,
         'form':form,
         'table':table,
         '$ax':$ax,
         'admin':admin,
         'func':func,
         'key':"law_decision",
         'verify':false,
         'element':element
    }

    //初始化显示内容
    initShowContent($("#punishPersonType").val());
    loadVerify(form);
    money_verify(lay,"fine");
    money_verify(lay,"resourceCompensation");
    initPage();
    loadStatus();
    setTipContent();
    function initPage() {
        //编辑
        if($("#id").val()){
            let lawType= $("#lawType").val();
            let url="decision";
            if(lawType==="2"){
                url="decision_safe";
            }
            let ajax= new $ax(Feng.ctxPath + "/lawRecord/"+url+"/detail?id="+$("#id").val());
            let result = ajax.start();
            if(result){
                form.val('decisionForm',result);
                // loadAddr($,form,result.punishAddrStateCode,result.punishAddrCityCode);
                $("#punishPersonType").attr("disabled","disabled");
                form.render();
                return;
            }
        }
            // loadAddr($,form);
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
        let tpl_ = $("#"+tpl).html(),view_ = $('#'+view);
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

    function setMoney(data){
        let fine=$("#fine").val();
        if(fine){
            data.field.fineUpper=number2Chinese(fine);
        }
        let resourceCompensation=$("resourceCompensation").val();
        if(resourceCompensation){
            data.field.resourceCompensationUpper=number2Chinese(resourceCompensation);
        }
    }

    function loadStatus(){
        if($("#severity").data("status")){
            loadSeverity();
        }
    }

    function loadSeverity(){
        let next=$("#severity").next();
        let severity=next.find("dd[lay-value='1']");
        if( $("#severity").val()==1){
            $("#severity").val(2);
            next.find("input").val(next.find("dd[lay-value='2']").text());
            setTipContent();
        }
        if(!severity.hasClass("layui-disabled")){
            severity.addClass("layui-disabled");
        }
    }

    form.on('select(caseViolenceLaw)', function(data){
        if(data.value==1){
            loadSeverity();
        }else{
            if(!$("#severity").data("status")){
                $("#severity").next().find("dd[lay-value='1']").removeClass("layui-disabled");
            }
        }
    });

    form.on('select(severity)', function(data){
        let content=data.elem[data.elem.selectedIndex].dataset.content;
        if(content){
            $("#tip_content").text("注："+content);
        }

    });

    function setTipContent(){
        let val=$("#severity").val();
        let content=$("#severity").find("option[value='"+val+"']").data("content");
        if(content){
            $("#tip_content").text("注："+content);
        }

    }


    //提交数据
    function submitData(des,data){
        //设置数据
        // data.field.punishAddr= JSON.stringify(getLocAddr($,"punish_addr"));
        setMoney(data);
        let url="decision/update";
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
