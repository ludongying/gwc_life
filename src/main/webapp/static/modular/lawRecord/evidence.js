
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
         'upload':upload,
         'table':table,
         '$ax':$ax,
         'admin':admin,
         'func':func,
         'key':'law_evidence'
    }
        initPage();
        setTimeout(function () {
            startListen($,lay.key);
        },500);


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
        initContent();
    });

    function initPage(){
        //编辑
        if($("#id").val()){
            let ajax = new $ax(Feng.ctxPath + "/lawRecord/evidence/detail?id="+$("#id").val());
            let result = ajax.start();
            if(result.success){
                let datas = result.content;
                if(datas){
                    let length = datas.length;
                    if(length>0){
                        setDatas(datas);
                        return;
                    }
                }

            }
        }
        initContent();

    }
    //初始化内容
    function initContent(content){
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
        loadUpload(lay,index,content);
        return index;
    }

    //设置数据
    function setDatas(datas){
        for(let i=0;i<datas.length;i++){
            let data=datas[i];
            let index=initContent(data.path);
            let inputs=$("#evidence_box .content_"+index).find("input");
            inputs.eq(0).val(data.evidenceContent);
            inputs.eq(1).val(data.evidenceTime);
            inputs.eq(3).val(data.id);
        }
    }
    //获取数据
    function getDatas() {
        let content=[];
        let cards = $("#evidence_box .layui-card-body");
        for(let i=0;i<cards.length;i++){
            let card = cards.eq(i);
            let inputs=card.find("input");
            let data={
                evidenceContent:inputs.eq(0).val(),
                evidenceTime:inputs.eq(1).val(),
                evidenceFilePath:inputs.eq(2).val(),
                id:inputs.eq(3).val()

            }
            content.push(data);
        }
        console.log(JSON.stringify(content));
        return content;
    }
    //提交数据
    function submitData(des,data){
        //获取数据
        data.field.content=JSON.stringify(getDatas());
        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"evidence/update",
            "next":"/lawRecord/"+des+"?lawType="+$("#lawType").val(),
            "data":data
        });
    }


    upload.render({
        elem: '#video'
        , url: '/system/uploadVideo'
        , field: "file"
        , accept: 'video' //视频
        , done: function (res) {
            layer.close(layer.msg());//关闭上传提示窗口
            //上传完毕
            $('#video-list').append(
                '<div id="" class="file-iteme">' +
                '<div class="handle"><i class="layui-icon layui-icon-delete"></i></div>' +
                '<img style="width: 100px;height: 100px;" src='+ res.fileName +'>' +
                '<div class="info">' + res.videoName + '</div>' +
                '</div>'
            );

            $('#video-list img').on('click', function () {
                func.open({
                    title: '视频',
                    area: ['600px', '500px'],
                    content: Feng.ctxPath + "system/videoPlayback?videoName="+$(this).parent()[0].textContent
                })
            })
        }
    });

});
