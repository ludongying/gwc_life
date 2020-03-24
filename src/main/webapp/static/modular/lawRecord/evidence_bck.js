
layui.use(['layer', 'form','upload', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func','laytpl'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var upload=layui.upload;
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
         'key':'law_evidence'
    }
    var fileParam={
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
            var ajax = new $ax(Feng.ctxPath + "/lawRecord/evidence/detail?id="+$("#id").val());
            var result = ajax.start();
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
        var index=($('#evidence_box .layui-col-md12')).length+1;
        var data = { //数据
            "index":index
        }
        var getTpl = $('#evidenceTpl').html()
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
            var content=res.content;
            if(content){
                var data_length = content.length;
                if(data_length>0){
                    for(var i=0;i<data_length;i++){
                        var index = initContent();
                        var evidence=content[i];
                        var param=Object.assign({},fileParam);;
                        param.data=evidence.path;
                        initFiles($,upload,param,index);
                        var inputs = $("#evidence_box .content_"+index+" input");
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
        var content=[];
        var cards = $("#evidence_box .layui-card-body");
        for(var i=0;i<cards.length;i++){
            var card = cards.eq(i);
            var index=card.data("index");
            var inputs=card.find("input");
            var data={
                evidenceContent:inputs.eq(0).val(),
                evidenceTime:inputs.eq(1).val(),
                path:[]
            }
            var evidenceId=inputs.eq(2).val();
            if(evidenceId){
                data.id=evidenceId;
            }
            content.push(data);
            var trs=$("#file_list"+index+" tr");
            var trs_length=trs.length;
            if(trs_length>0){
                for(var j=0;j<trs_length;j++){
                    var tds=trs.eq(j).find("td");
                    var filePath=tds.eq(2).find("span").data("filePath");
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
        var dataStr=JSON.stringify(getDatas());
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
