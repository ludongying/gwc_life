layui.use(['form', 'upload', 'element', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var upload = layui.upload;
    var $ax = layui.ax;
    var laydate = layui.laydate;

    //渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });

    //获取用户详情
    var ajax = new $ax(Feng.ctxPath + "/system/currentUserInfo");
    var result = ajax.start();

    //用这个方法必须用在class有layui-form的元素上
    form.val('userInfoForm', result.content);

    //表单提交事件
    form.on('submit(userInfoSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/user/update", function (data) {
            if (data.success) {
                Feng.success("编辑成功!");
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("编辑失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
    });

    upload.render({
        elem: '#imgHead',
        url: '/system/uploadImage', // 上传接口
        before: function (obj) {
            obj.preview(function (index, file, result) {
                $('#avatarPreview').attr('src', result);
            });
        },
        done: function (data) {
            var ajax = new $ax(Feng.ctxPath + "/system/updateAvatar",
                function (data) {
                    Feng.success("上传成功!");
                    $('.layui-nav-img').attr('src', $("#avatarPreview")[0].src);
                }, function (data) {
                    Feng.error("上传失败!" + data.message + "!");
                });
            ajax.set("portraitUrl", data.content.pictureName);
            ajax.start();
        },
        error: function () {
            Feng.error("上传头像失败！");
        }
    });
});