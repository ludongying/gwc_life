/**
 * 证书信息编辑对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'laydate', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var upload = layui.upload;
    //多图容器
    var image_path = [];

    laydate.render({
        elem: '#issueDate',
        type: 'date',
        trigger: 'click'
    });
    laydate.render({
        elem: '#outDate',
        type: 'date',
        trigger: 'click'
    });

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();
    //表单验证加载
    loadVerify(form);

    //图片放大预览
    var renderImg = function () {
        $('#attachment img').on('click', function () {
            layer.photos({
                photos: '#attachment',
                shadeClose: false,
                closeBtn: 2,
                anim: 0
            });
        })
    }

    //初始化证书信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/certificate/detail/" + Feng.getUrlParam("certificateId"));
    var result = ajax.start();
    form.val('certificateForm', result);

    //多图片回显
    $().ready(function () {
        var imgStr = result.attachUrl;
        // alert(imgStr);
        if (imgStr != null) {
            var imgStr1 = imgStr.substring(0, imgStr.lastIndexOf(","));
            var imgStrArr = imgStr1.split(",")
            // alert(imgStrArr.length);
            for (var i = 0; i < imgStrArr.length; i++) {
                if (imgStrArr[i] != ''){
                    $('#attachment').append('<img src="' + imgStrArr[i] + '" id="' + imgStrArr[i] + '" class="layui-upload-img" width="200px" height="150px">');
                    //如何绑定删除事件？？？
                    // $('#' + imgStrArr[i]).bind('dblclick', function () {
                    //     // delete files[index];//删除指定图片
                    //     alert('db');
                    //     $(this).remove();
                    // });
                }
            }
        }
    });
    //多图片放大预览
    renderImg();



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


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/certificate/update", function (data) {
            if (data.success) {
                Feng.success("编辑成功!");
                admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("编辑失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    //多图片上传
    upload.render({
        elem: '#PreviewBtn'
        , accept: 'images'
        , url: '/file/uploadFile'
        , acceptMime: 'image/jpg,image/png,image/jpeg'
        , exts: 'jpg|png|jpeg'
        , multiple: true
        , number: 6
        , auto: false
        , bindAction: '#UploadBtn'
        , choose: function (obj) {
            files = obj.pushFile();
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#attachment').append('<img src="' + result + '" id="' + index + '" alt="' + file.name + '" class="layui-upload-img" width="200px" height="140px">');
                //双击删除指定预上传图片
                $('#' + index).bind('dblclick', function () {
                    delete files[index];//删除指定图片
                    $(this).remove();
                });
                //放大预览
                renderImg();
            });
        }
        , done: function (res, index, upload) {
            if (res.success) {//上传图片成功
                Feng.success("上传成功!");
                // image_path.push(res.content.path);
                // $('#attachFilePath').val(image_path);
                var appendUrl = $('#attachFilePath').val().trim();
                appendUrl = appendUrl + "," + res.content.path;
                $('#attachFilePath').val(appendUrl);
            }
        },
        error: function () {
            Feng.error("上传图像失败！");
        }
    });


    //清空图片列表
    $('#PreviewClearBtn').click(function () {
        // var ajax = new $ax(Feng.ctxPath + "/file/deleteFile", function (data) {
        //     if (data.success == true) {
        //         Feng.success("修改成功!");
        //     } else {
        //         Feng.success(data.message);
        //     }
        // }, function (data) {
        //     Feng.error("删除失败!" + data.message + "!");
        // });
        $('#attachment').empty();
        $('#attachFilePath').val("");
    });

});