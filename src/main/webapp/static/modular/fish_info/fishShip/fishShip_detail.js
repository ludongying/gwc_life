/**
 * 渔船信息详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化渔船信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/fishShip/detail/" + Feng.getUrlParam("fishShipId"));
    var result = ajax.start();
    if (result.fileName != null) {
        fileName = result.fileName;
        for(let i=0; i<result.filePath.length; i++){
            $('#uploader-list').append(
                '<div id="" class="file-iteme">' +
                '<div class="handle"><i class="layui-icon layui-icon-download-circle"></i></div>' +
                '<img style="width: 100px;height: 100px;" src=' + result.filePath[i].url + '>' +
                '<div class="info">' + result.filePath[i].path + '</div>' +
                '</div>'
            );
        }
    }

    $(document).on("mouseenter mouseleave", ".file-iteme", function(event){
        if(event.type === "mouseenter"){
            //鼠标悬浮
            $(this).children(".handle").fadeIn("fast");   //删除按钮
        }else if(event.type === "mouseleave") {
            //鼠标离开
            $(this).children(".handle").hide();
        }
    });

    $(document).on("click", ".file-iteme .handle", function(event){
        downloadImage($(this).parent().eq(0).find("img").attr("src"));
    });

    form.val('fishShipForm',result);

    $('#uploader-list img').on('click', function () {
        layer.photos({
            photos: '#uploader-list',
            shadeClose: false,
            closeBtn: 2,
            anim: 0
        });
    })

});