/**
 * 渔船信息增加对话框
 */

layui.use(['layer', 'form', 'admin', 'ax', 'laydate', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var upload = layui.upload;
    var ajax;
    var formData;

    laydate.render({
        elem: '#productDate',
        type: 'date',
        trigger: 'click'
    });

    //获取管理类别下拉框
    $.ajax({
        url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=AREA_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#areaType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }
    });

    //获取船舶类型下拉框
    $.ajax({
        url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=SHIP_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#shipType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }
    });

    //获取作业类型下拉框
    $.ajax({
        url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=WORK_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#workType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }
    });

    //获取出海状态下拉框
    $.ajax({
        url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=SEA_STATE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#seaState').append(new Option(item.name, item.id));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }
    });

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    var uploadInst = upload.render({
        elem: '#upload'
        , url: '/system/uploadFile' //改成您自己的上传接口
        , accept: 'file'
        , exts: 'pdf'
        , size: 1024
        , auto: false
        // , bindAction: '#btnSubmit'
        , choose: function (obj) {
            var files = obj.pushLastFile();
            obj.preview(function (index, file, result) {
                $('#choose').remove();
                $('#fileName').val(file.name.substring(0, file.name.lastIndexOf(".")));
                $('.layui-upload').append('<span id="choose" class="layui-inline layui-upload-choose">' + file.name + '</span>');
                $('#fullName').val(file.name)
            });
        }
        , done: function (res) {
            if (res.CODE === 200) {
                formData.fullName = res.fileName;
                ajax = new $ax(Feng.ctxPath + "/fishShip/add", function (data) {
                    if (data.success) {
                        Feng.success("增加成功!");
                        admin.putTempData('formOk', true);//传给上个页面，刷新table用
                        admin.closeThisDialog();//关掉对话框
                    } else {
                        Feng.error(data.message);
                    }
                }, function (data) {
                    Feng.error("增加失败!" + data.message)
                });
                ajax.set(formData);
                ajax.start();

                return false;
            }
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if ($('#fileName').val() === "") {
            Feng.error("附件不能为空");
            return false
        } else {
            formData = data.field;
            uploadInst.upload();
            return false
        }
    });

    /*form.on('submit(btnSubmit)', function (data) {
        uploadInst.upload();
        var ajax = new $ax(Feng.ctxPath + "/fishShip/add", function (data) {
            if (data.success) {
                Feng.success("增加成功!");
                admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("增加失败!" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });*/


});