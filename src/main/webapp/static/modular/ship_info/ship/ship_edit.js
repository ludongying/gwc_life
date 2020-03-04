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

    laydate.render({
        elem: '#finishDate',
        type: 'date'
    });

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化执法船信息管理的详情数据
    var ajax = new $ax(Feng.ctxPath + "/ship/detail/" + Feng.getUrlParam("shipId"));
    var result = ajax.start();
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

                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);

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
        , accept: 'images'
        , url: 'ship/updateImages' //改成您自己的上传接口
        , multiple: true
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#boatPreviewMulti').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img" width="200px" height="100px">')
            });
        }
        , done: function (data) {
            alert('aaaa');
            var ajax = new $ax(Feng.ctxPath + "/system/updateImages",
                function (data) {

                }, function (data) {
                    Feng.error("修改失败!" + data.message + "!");
                });
            alert(data.content.pictureName);
            ajax.set("image", "/common/images/portrait/" + data.content.pictureName);
            ajax.start();
        },
        error: function () {
            Feng.error("上传船舶图像失败！");
        }
    });

    // upload.render({
    //     elem: '#imgBoat',
    //     accept: 'images',
    //     url: '/system/uploadImage', // 上传接口
    //     before: function (obj) {
    //         obj.preview(function (index, file, result) {
    //             $('#boatPreview').attr('src', result);
    //         });
    //     },
    //     done: function (data) {
    //         var ajax = new $ax(Feng.ctxPath + "/system/updateAvatar",
    //             function (data) {
    //
    //             }, function (data) {
    //                 Feng.error("修改失败!" + data.message + "!");
    //             });
    //         ajax.set("image", "/common/images/portrait/"+data.content.pictureName);
    //         ajax.start();
    //     },
    //     error: function () {
    //         Feng.error("上传船舶图像失败！");
    //     }
    // });


});