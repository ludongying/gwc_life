/**
 * 执法记录详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    $(".layui-form input").attr("disabled","disabled");

    // var fileManager=new initFiles($);
    // $("#evidence .demo-down-1").click(function () {
    //        fileManager.downLoad($(this).data("filePath"));
    // });
    //
    // $("#evidence .demo-preview-1").click(function () {
    //     fileManager.preview_img($(this).data("filePath"));
    // });

    $('#uploader-list img').on('click', function () {
        layer.photos({
            photos: '#uploader-list',
            shadeClose: false,
            closeBtn: 2,
            anim: 0
        });
    });

    layer.open({
        title:'目录',
        type: 1,
        area: 'auto',
        offset: 'r',
        shade:false,
        skin:'layui-layer-dir',
        resize:false,
        content: $("#anchor_id").html(),

    });

    $(document).on("click",".site-dir li",function () {
        $(".site-dir li").removeClass("bold_font");
        $(this).addClass("bold_font");
    });


    $('#evidence img').on('click', function () {
        layer.photos({
            photos: '.uploader-list',
            anim: 0,
            type: 1,
            title:false,
            shade:0.1,
            closeBtn:false,
            shadeClose:true
        });
    })

});