/**
 * 执法船信息管理编辑对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'laydate', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var upload = layui.upload;
    var fileName = "";

    laydate.render({
        elem: '#finishDate',
        type: 'date',
        trigger: 'click'
    });

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();
    //表单验证
    loadVerify(form);

    //初始化执法船信息管理的详情数据
    var ajax = new $ax(Feng.ctxPath + "/ship/detail/" + Feng.getUrlParam("shipId"));
    var result = ajax.start();
    //多图片回显
    if(result.attachFilePath != null){
        fileName = result.imageFilePath;
        var files = fileName.split(",");
        for (i = 0; i < files.length - 1; i++) {
            $('#boatPreviewMulti').append(
                '<div id="" class="file-iteme">' +
                '<div class="handle"><i class="layui-icon layui-icon-delete"></i></div>' +
                '<img style="width: 100px;height: 100px;" src=' + files[i] + '>' +
                '<div class="info">' + files[i] + '</div>' +
                '</div>'
            );
        }
    }
    form.val('shipForm', result);

    //船籍获取下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=BOAT_NATION',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#nationality').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#nationality').val(result.nationality);
            form.render('select');//表单渲染 把内容加载进去

        }
    });

    //船舶类型获取下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=BOAT_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#type').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#type').val(result.type);
            form.render('select');//表单渲染 把内容加载进去
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/ship/update", function (data) {
            if (data.success == true) {
                Feng.success("修改成功!");

                // //传给上个页面，刷新table用
                // admin.putTempData('formOk', true);
                parent.location.reload();
                //关掉对话框
                admin.closeThisDialog();
            } else {
                Feng.success(data.message);
            }
        }, function (data) {
            Feng.error("修改失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    //多图片上传
    upload.render({
        elem: '#boatPreviewBtn'
        , url: '/system/uploadFile'
        , multiple: true
        , accept: 'images'
        , acceptMime: 'image/jpg,image/png,image/jpeg'
        , exts: 'jpg|png|jpeg'
        , number: 6
        , before: function (obj) {
            layer.msg('图片上传中...', {
                icon: 16,
                shade: 0.01,
                time: 0
            })
        }
        , done: function (res) {
            layer.close(layer.msg());//关闭上传提示窗口
            //上传完毕
            $('#boatPreviewMulti').append(
                '<div id="" class="file-iteme">' +
                '<div class="handle"><i class="layui-icon layui-icon-delete"></i></div>' +
                '<img style="width: 100px;height: 100px;" src=' + res.fileName + '>' +
                '<div class="info">' + res.fileName + '</div>' +
                '</div>'
            );
            //放大预览（若不加，新上传的图片无法放大预览）
            $('#boatPreviewMulti img').on('click', function () {
                layer.photos({
                    photos: '#boatPreviewMulti',
                    shadeClose: false,
                    closeBtn: 2,
                    anim: 0
                });
            })
            fileName = fileName + res.fileName + ","
            $("#imageFilePath").val(fileName);
        }
        , error: function () {
            Feng.error("上传船舶图像失败！");
        }
    });

    $(document).on("mouseenter mouseleave", ".file-iteme", function (event) {
        if (event.type === "mouseenter") {
            //鼠标悬浮
            //$(this).children(".info").fadeIn("fast");    //文件名
            $(this).children(".handle").fadeIn("fast");   //删除按钮
        } else if (event.type === "mouseleave") {
            //鼠标离开
            //$(this).children(".info").hide();
            $(this).children(".handle").hide();
        }
    });

    // 删除图片
    $(document).on("click", ".file-iteme .handle", function (event) {
        $(this).parent().remove();
        deleteFIle(fileName, $(this).parent()[0].textContent);
    });

    function deleteFIle(names, deleteFile) {
        var name = "";
        var files = names.split(",");
        for (i = 0; i < files.length - 1; i++) {
            if (files[i] !== deleteFile) {
                name += files[i] + ",";
            }
        }
        fileName = name;
        $("#imageFilePath").val(fileName);
    }

    //放大预览
    $('#boatPreviewMulti img').on('click', function () {
        layer.photos({
            photos: '#boatPreviewMulti',
            shadeClose: false,
            closeBtn: 2,
            anim: 0
        });
    })
});