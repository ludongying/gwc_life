/**
 * 渔船信息编辑对话框
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
    var flag = true;

    laydate.render({
        elem: '#productDate',
        type: 'date',
        trigger: 'click'
    });


    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化渔船信息的详情数据
    ajax = new $ax(Feng.ctxPath + "/fishShip/detail/" + Feng.getUrlParam("fishShipId"));
    var result = ajax.start();
    $('#fileName').val(result.fileName);
    $('.layui-upload').append('<span id="choose" class="layui-inline layui-upload-choose">' + result.fileName + '</span>');
    //$('#fullName').val(result.fullName);
    form.val('fishShipForm',result);

    //获取管理类别下拉框
    $.ajax({
        url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=AREA_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                if (result.areaType == item.id) {
                    $('#areaType').append(new Option(item.name, item.id, null, true));// 下拉菜单里添加元素
                } else {
                    $('#areaType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                }
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
                if (result.shipType == item.id) {
                    $('#shipType').append(new Option(item.name, item.id, null, true));// 下拉菜单里添加元素
                } else {
                    $('#shipType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                }
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
                if (result.workType == item.id) {
                    $('#workType').append(new Option(item.name, item.id, null, true));// 下拉菜单里添加元素
                } else {
                    $('#workType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                }
            });
            layui.form.render("select");
        }
    });

    //获取水域分类下拉框
    $.ajax({
        url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=WATERS_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                if (result.watersType == item.id) {
                    $('#watersType').append(new Option(item.name, item.id, null, true));// 下拉菜单里添加元素
                } else {
                    $('#watersType').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                }
            });
            layui.form.render("select");
        }
    });

    //获取作业方式下拉框
    $.ajax({
        url: Feng.ctxPath +'/dict/getDictListByDictTypeCode?dictTypeCode=PRACTICE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                if (result.practice == item.id) {
                    $('#practice').append(new Option(item.name, item.id, null, true));// 下拉菜单里添加元素
                } else {
                    $('#practice').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                }
            });
            layui.form.render("select");
        }
    });

    /*var uploadInst = upload.render({
        elem: '#upload'
        , url: '/system/uploadFile' //改成您自己的上传接口
        , accept: 'file'
        , exts: 'pdf'
        , size: 1024
        , auto: false
        // , bindAction: '#btnSubmit'
        , choose: function (obj) {
            flag = false;
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
                ajax = new $ax(Feng.ctxPath + "/fishShip/update", function (data) {
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
        if (flag) {
            ajax = new $ax(Feng.ctxPath + "/fishShip/update", function (data) {
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
        } else {
            formData = data.field;
            uploadInst.upload();
        }
        return false
    });*/

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/fishShip/update", function (data) {
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
});