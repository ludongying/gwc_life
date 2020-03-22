
layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func','laytpl'], function () {
    let $ = layui.$;
    let layer = layui.layer;
    let form = layui.form;
    let table = layui.table;
    let $ax = layui.ax;
    let laydate = layui.laydate;
    let admin = layui.admin;
    let func = layui.func;
    let laytpl=layui.laytpl;
    let lay={
         '$':$,
         'layer':layer,
         'form':form,
         'table':table,
         '$ax':$ax,
         'admin':admin,
         'func':func,
         'key':"law_inquire"
    }
    let unit_data = {
        shipRatedPowerUnit: $("#shipRatedPowerUnit").val()
        , shipRealPowerUnit: $("#shipRealPowerUnit").val()
    };

    function initTime(){
        laydate.render({
            elem: '#shipOutDate'
            ,type: 'datetime'
            ,format: 'yyyy-MM-dd HH:mm'
        });
        laydate.render({
            elem: '#shipFishAreaDate'
            ,type: 'datetime'
            ,format: 'yyyy-MM-dd HH:mm'
        });
    }
    //时间初始化
    initTime();
    initPage();
    loadVerify(form);
    function initPage(){
        if($("#id").val()){
            let ajax = new $ax(Feng.ctxPath + "/lawRecord/inquire/detail?id="+$("#id").val());
            let result = ajax.start();
            if(result){
                form.val('inquireForm',result);
                loadAddr($,form,result.investigateAddrStateCode,result.investigateAddrCityCode);
                setInquireContent(result.inquireContent);
                return;
            }
        }
        //初始化地址
        loadAddr($,form,"32","7");
    }


    form.on('select(powerUnit)', function(data){
        let input_=$(data.elem).parent().prev().find("input");
        let val=input_.val();
        let unitValue=parseInt(data.value);
        let name=$(data.elem).data("name");
        if(unitValue===unit_data[name]){return;}
        unit_data[name]=unitValue;
        if(val && unitValue){
            switch (unitValue) {
                case 1:
                    input_.val(getFloat(parseFloat(val)/1.36));
                    break;
                case 2:
                    input_.val(getFloat(parseFloat(val)*1.36));
                    break;
                default:
            }
        }
    });

    //开启表单内容监听
    startListen($,lay.key);
    //下一步
    form.on('submit(nextStep)', function (data) {
        submitData("inquisition",data);
    });
    //上一步
    form.on('submit(preStep)', function (data) {
        submitData("agency",data);
    });
    function submitData(des,data){
        //获取地址
        data.field.investigateAddr= JSON.stringify(getLocAddr($,"investigate_addr"));
        //获取补录内容
        data.field.inquireContent=JSON.stringify(getInquireContent());
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"inquire/update",
            "next":"/lawRecord/"+des+"?lawType="+$("#lawType").val(),
            "data":data
        });
    }
    //补录笔录
    //初始化内容
    function initContent(content){
        let index=($('#inquire_box .inquire_index')).length+1;
        let data = { //数据
            "index":index,
            "content":content
        }
        let getTpl = $('#inquireTpl').html()
            ,view = $('#inquire_box');
        laytpl(getTpl).render(data, function(html){
            view.append(html);
        });
        laydate.render({
            elem: '#shipOutDate'+index
            ,type: 'datetime'
            ,format: 'yyyy-MM-dd HH:mm'
            ,done:function(value){
               $('#shipOutDate'+index).val(value);
            }
        });
        laydate.render({
            elem: '#shipFishAreaDate'+index
            ,type: 'datetime'
            ,format: 'yyyy-MM-dd HH:mm'
            ,done:function(value){
                $('#shipFishAreaDate'+index).val(value);
            }
        });
        //单位换算初始化默认
        unit_data["hipRatedPowerUnit"+index]=$("#shipRatedPowerUnit"+index).val();
        unit_data["shipRealPowerUnit"+index]=$("#shipRealPowerUnit"+index).val();
        return index;
    }
    //增加补录
    $("#addInquire").click(function () {
        let index=initContent({});
        //地址初始化
        loadAddrIndex({
            $:$,form:form,stateCode:"32",cityCode:"7",index:index
        });

        form.render();
    });
    //删除笔录
    $("#inquire_box").on('click','.del_inquire',function () {
          $(this).parent().parent().parent().parent().remove();
    });
    //获取补录信息
    function getInquireContent(){
        let blocks=$("#inquire_box .inquire_index");
        let arr=[];
        if(blocks.length>0){
            for(let i=0;i<blocks.length;i++){
                let obj= getBlockCon(blocks.eq(i),i+1);
                arr.push(obj);
            }
        }
        return arr;
    }
    //获取每块数据信息
    function getBlockCon(block,index){
        let inputs = block.find("input");
        let obj={};
        for(let i=0;i<inputs.length;i++){
            let input = inputs.eq(i);
            let val=input.val();
            let name=input.data('name');
            let type=input.attr("type");
            if(name && val){
                name=name.toString().substring(0,name.length-index.toString().length);
                switch (type) {
                    case "radio":
                        if(input.next().hasClass("layui-form-radioed")){
                            obj[name]=val;
                        }
                        break;
                    case "checkbox":

                        break;
                    default:
                        obj[name]=val;
                }
            }
        }
        let selects = block.find("select");
        for(let i=0;i<selects.length;i++){
            let select = selects.eq(i);
            let val=select.val();
            let name=select.data('name');
            if(name && val) {
                name = name.toString().substring(0, name.length - index.toString().length);
                obj[name]=val;
            }
        }
        obj["investigateAddr"]=getLocAddr($,"investigate_addr"+index);
        return obj;
    }

    function setInquireContent(data){
        if(data){
           let length = data.length;
           if(length>0){
               for(let i=0;i<length;i++){
                   let index=initContent(data[i]);
                   let block=$("#inquire_box .inquire_index").eq(i);
                   setBlockCon(index,data[i],block);
                   let addr = data[i].investigateAddr;
                   if(addr){
                       let obj=JSON.parse(addr);
                       loadAddrIndex({
                           $:$,form:form,stateCode:obj.state.code,cityCode:obj.city.code,region:obj.region,index:index
                       });
                   }

               }
               form.render();
           }
        }
    }

    function setBlockCon(index,con,block){
        let selects = block.find("select");
        for(let i=0;i<selects.length;i++){
            let select = selects.eq(i);
            let name=select.data('name');
            if(name){
                name = name.toString().substring(0, name.length - index.toString().length);
                let val=con[name];
                if(val){
                    select.val(val);
                }
            }
        }
        let radios = block.find("input[type='radio']");
        let group={};
        for (let i=0;i<radios.length;i++){
           let name=radios.eq(i).data("name");
           name = name.toString().substring(0, name.length - index.toString().length);
           let obj=group[name];
           if(!obj){obj=[];group[name]=obj;}
            obj.push(radios.eq(i));
        }
        for(let prop in group){
            let name=prop.toString();
            let value=con[name];
            let inputs=group[name];
            for(let i=0;i<inputs.length;i++){
                if( parseInt($(inputs[i]).val())===value){
                    if(!$(inputs[i])[0].hasAttribute("checked")){
                        $(inputs[i].attr("checked","checked"))
                    }
                }else{
                    if($(inputs[i])[0].hasAttribute("checked")){
                        $(inputs[i].removeAttr("checked","checked"))
                    }
                }
            }
        }
    }

});
