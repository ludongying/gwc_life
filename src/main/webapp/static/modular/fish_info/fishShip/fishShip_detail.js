/**
 * 渔船信息详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var func = layui.func;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化渔船信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/fishShip/detail/" + Feng.getUrlParam("fishShipId"));
    var result = ajax.start();
    var fileName = result.fileName;
    if (fileName != null) {
        var files = fileName.split(",");
        for (i = 0; i < files.length - 1; i++) {
            $('#uploader-list').append(
                '<div id="" class="file-iteme">' +
                '<div class="handle"><i class="layui-icon layui-icon-delete"></i></div>' +
                '<img style="width: 100px;height: 100px;" src=' + files[i] + '>' +
                '<div class="info">' + files[i] + '</div>' +
                '</div>'
            );
        }
    }
    form.val('fishShipForm',result);

    $('#preview').click(function () {
        func.open({
            title: '预览',
            maxmin: true,
            content: Feng.ctxPath + '/system/previewPdf?fileName=' + fuiiName
        });
    });

});