
layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    let $ = layui.$;
    let layer = layui.layer;
    let form = layui.form;
    let table = layui.table;
    let $ax = layui.ax;
    let laydate = layui.laydate;
    let admin = layui.admin;
    let func = layui.func;

    let lay={
         '$':$,
         'layer':layer,
         'form':form,
         'table':table,
         '$ax':$ax,
         'admin':admin,
         'func':func,
         'key':'law_agency'
    }

    form.verify({
        person2:function(value){
            let p1=$("#law_person1").val();
            if (value && p1) {
                if(value===p1){
                    return '执法人员不能选择同一个人';
                }
            }
        }
    });

    //关联时间设置
    relateTime($,laydate);
    //初始化页面内容
    initPage();
    //载入表单验证
     loadVerify(form);
    //办案人员处理
    function getOperators(data){
        let operators=[];
        let inputs = $("#lawOperator select");
        let person1=$("#law_person1").val();
        if(person1){
            let opt=inputs.eq(0).find("option[value='"+person1+"']");
            operators.push({
                "id":$("#id1").val(),
                "lawPersonId":person1,
                "lawPersonName":opt.text(),
                "lawCredentialCode":opt.data("certificate"),
            });
        }
        let person2=$("#law_person2").val();
        if(person2){
            let opt=inputs.eq(1).find("option[value='"+person2+"']");
            operators.push({
                "id":$("#id2").val(),
                "lawPersonId":person2,
                "lawPersonName":opt.text(),
                "lawCredentialCode":opt.data("certificate"),
            });
        }
        data.field.operators=JSON.stringify(operators);
    }


    function initPage(){
        if($("#id").val()){
            let ajax = new $ax(Feng.ctxPath + "/lawRecord/agency/detail?id="+$("#id").val());
            let result = ajax.start();
            if(result){
                form.val('agencyForm',result);
                // loadAddr($,form,result.agencyAddrStateCode,result.agencyAddrCityCode);
                return;
            }
        }
         // loadAddr($,form,"32","7");
    }

    form.on('select(law_person)', function(data){
        let input= $(data.elem).parent().parent().next().find("input").eq(0);
        input.val(data.elem[data.elem.selectedIndex].dataset.certificate);
    });


    //开启表单内容监听
    startListen($,lay.key);
    //下一步
    form.on('submit(nextStep)', function (data) {
        //时间格式修正
        setProspectInquire($,data);
        //设置办案人员
        getOperators(data);
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"agency/update",
            "next":"/lawRecord/inquire?lawType="+$("#lawType").val(),
            "data":data
        });
    });


});
