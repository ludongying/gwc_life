/**
 * 执法记录详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    $(".layui-form input").attr("disabled","disabled");

    var fileManager=new initFiles($);
    $("#evidence .demo-down-1").click(function () {
        fileManager.downLoad($(this).data("filePath"));
    });

    $("#evidence .demo-preview-1").click(function () {
        fileManager.preview_img($(this).data("filePath"));
    });
    layer.open({
        title:'目录',
        type: 1,
        area: 'auto',
        offset: 'r',
        shade:false,
        skin:'layui-layer-dir',
        resize:false,
        content: $("#anchor_id").html()
    });


});