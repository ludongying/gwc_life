
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
        startListen($,lay.key);


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

    });

    function initPage(){
        //编辑
        if($("#id").val()){
            let ajax = new $ax(Feng.ctxPath + "/lawRecord/evidence/detail?id="+$("#id").val());
            let result = ajax.start();
            if(result){
                return;
            }

        }
        initContent();

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

    }
    //获取数据
    function getDatas() {

    }
    //提交数据
    function submitData(des,data){
        //获取数据



        //开启监听后，下一步操作验证
        nextStep({
            "lay":lay,
            "url":"evidence/update",
            "next":"/lawRecord/"+des+"?lawType="+$("#lawType").val(),
            "data":data
        });
    }

    //多图片上传
    upload.render({
        elem: '#test2'
        ,url: 'https://httpbin.org/post' //改成您自己的上传接口
        ,multiple: true
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 120px" class="layui-upload-img">')
            });
        }
        ,done: function(res){
            //上传完毕
        }
    });




});
