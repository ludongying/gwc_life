
layui.use(['layer', 'form','upload', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func','laytpl'], function () {
    let $ = layui.$;
    let layer = layui.layer;
    let form = layui.form;
    let upload=layui.upload;
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
         'key':'law_evidence'
    }
    let fileParam={
        "delete":false,
        "down":true,
        "exts":'png|jpg|jpeg|avi|mp4',
        "size":50*1024,
        "preview":true
        }

        initPage();
        setTimeout(function () {
            //开启表单内容监听 加载时序问题调整
            $("#evidence_content").val(md5(JSON.stringify(getDatas())));
            startListen($,lay.key);
        },300)

    //下一步
    form.on('submit(nextStep)', function (data) {
        submitData("reason",data);
    });
    //上一步
    form.on('submit(preStep)', function (data) {
        if($("#lawType").val()==="1"){
            submitData("inquisition",data);
        }else{
            submitData("inquire",data);
        }

    });
    //添加
    $("#addEvidence").click(function () {
        initFiles($,upload,fileParam,initContent());
    });

    function initPage(){
        //编辑
        if($("#id").val()){
            let ajax = new $ax(Feng.ctxPath + "/lawRecord/evidence/detail?id="+$("#id").val());
            let result = ajax.start();
            if(result.content){
                if(setDatas(result)){
                    return;
                }
            }
        }
        initFiles($,upload,fileParam,initContent());
    }
    //初始化内容
    function initContent(){
        let index=($('#evidence_box .layui-col-md12')).length+1;
        let data = { //数据
            "index":index
        }
        let getTpl = $('#evidenceTpl').html()
            ,view = $('#evidence_box');
        laytpl(getTpl).render(data, function(html){
            view.append(html);
        });
        laydate.render({
            elem: '#time'+index
            ,type: 'datetime'
            ,format: 'yyyy-MM-dd HH:mm:ss'
            ,value: new Date()
        });
        return index;
    }
    //设置数据
    function setDatas(res){
        if(res.success){
            let content=res.content;
            if(content){
                let data_length = content.length;
                if(data_length>0){
                    for(let i=0;i<data_length;i++){
                        let index = initContent();
                        let evidence=content[i];
                        let param=Object.assign({},fileParam);;
                        param.data=evidence.path;
                        initFiles($,upload,param,index);
                        let inputs = $("#evidence_box .content_"+index+" input");
                        if(inputs){
                            if(inputs.length>2){
                                inputs.eq(0).val(evidence.evidenceContent);
                                inputs.eq(1).val(evidence.evidenceTime);
                                inputs.eq(2).val(evidence.id);
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    //获取数据
    function getDatas() {
        let content=[];
        let cards = $("#evidence_box .layui-card-body");
        for(let i=0;i<cards.length;i++){
            let card = cards.eq(i);
            let index=card.data("index");
            let inputs=card.find("input");
            let data={
                evidenceContent:inputs.eq(0).val(),
                evidenceTime:inputs.eq(1).val(),
                path:[]
            }
            let evidenceId=inputs.eq(2).val();
            if(evidenceId){
                data.id=evidenceId;
            }
            content.push(data);
            let trs=$("#file_list"+index+" tr");
            let trs_length=trs.length;
            if(trs_length>0){
                for(let j=0;j<trs_length;j++){
                    let tds=trs.eq(j).find("td");
                    let filePath=tds.eq(2).find("span").data("filePath");
                    if(filePath){
                        data.path.push({"path":filePath});
                    }
                }
            }
        }
        return content;
    }
    //提交数据
    function submitData(des,data){
        //获取数据
        let dataStr=JSON.stringify(getDatas());
        data.field.content=dataStr;
        //
        $("#evidence_content").val(md5(dataStr));
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"evidence/update",
            "next":"/lawRecord/"+des+"?lawType="+$("#lawType").val(),
            "data":data
        });
    }




});
