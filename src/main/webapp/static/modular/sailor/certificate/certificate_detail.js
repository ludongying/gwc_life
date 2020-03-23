/**
 * 证书信息详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var upload = layui.upload;
    var fileName = "";

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化证书信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/certificate/detail/" + Feng.getUrlParam("certificateId"));
    var result = ajax.start();
    //多图片回显
    if(result.attachFilePath != null){
        fileName = result.attachFilePath;
        var files = fileName.split(",");
        for (i = 0; i < files.length - 1; i++) {
            $('#attachment').append(
                '<div id="" class="file-iteme">' +
                '<div class="handle"><i class="layui-icon layui-icon-delete"></i></div>' +
                '<img style="width: 100px;height: 100px;" src=' + files[i] + '>' +
                '<div class="info">' + files[i] + '</div>' +
                '</div>'
            );
        }
    }
    form.val('certificateForm',result);

    //多图片回显
    $().ready(function() {
        var imgStr = result.attachUrl;
        if(imgStr != null) {
            var imgStr1 = imgStr.substring(0, imgStr.lastIndexOf(","));
            var imgStrArr = imgStr1.split(",")
            // alert(imgStrArr.length);
            for (var i = 0; i < imgStrArr.length; i++) {
                if (imgStrArr[i] != '')
                    $('#attachment').append('<img src="' + imgStrArr[i] + '" class="layui-upload-img" width="200px" height="150px">');
            }
        }
    });

    //证书类型获取下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=CERTIFICATE_PERSON_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#certificateType').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#certificateType').val(result.certificateType);
            form.render('select');//表单渲染 把内容加载进去

        }
    });

    //所属类型获取下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=CERTIFICATE_OWNER',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#ownerType').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#ownerType').val(result.ownerType);
            form.render('select');//表单渲染 把内容加载进去
        }
    });

    //图片放大
    $('#attachment img').on('click', function () {
        layer.photos({
            photos: '#attachment',
            shadeClose: false,
            closeBtn: 2,
            anim: 0
        });
    })

});