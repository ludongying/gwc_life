/**
 * 渔船信息编辑对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'laydate', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var upload = layui.upload;
    var fileName = "";

    laydate.render({
        elem: '#productDate',
        type: 'date',
        trigger: 'click'
    });


    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化渔船信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/fishShip/detail/" + Feng.getUrlParam("fishShipId"));
    var result = ajax.start();
    if (result.fileName != null) {
        var files = result.fileName.split(",");
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

    upload.render({
        elem: '#test2'
        ,url: '/system/uploadFile'
        ,multiple: true
        ,before: function(obj){
            layer.msg('图片上传中...', {
                icon: 16,
                shade: 0.01,
                time: 0
            })
        }
        ,done: function(res){
            layer.close(layer.msg());//关闭上传提示窗口
            //上传完毕
            $('#uploader-list').append(
                '<div id="" class="file-iteme">' +
                '<div class="handle"><i class="layui-icon layui-icon-delete"></i></div>' +
                '<img style="width: 100px;height: 100px;" src='+ res.fileName +'>' +
                '<div class="info">' + res.fileName + '</div>' +
                '</div>'
            );

            fileName = fileName + res.fileName + ",";
            $("#fileName").val(fileName);

            $('#uploader-list img').on('click', function () {
                layer.photos({
                    photos: '#uploader-list',
                    shadeClose: false,
                    closeBtn: 2,
                    anim: 0
                });
            })
        }
    });

    $(document).on("mouseenter mouseleave", ".file-iteme", function(event){
        if(event.type === "mouseenter"){
            //鼠标悬浮
            //$(this).children(".info").fadeIn("fast");    //文件名
            $(this).children(".handle").fadeIn("fast");   //删除按钮
        }else if(event.type === "mouseleave") {
            //鼠标离开
            //$(this).children(".info").hide();
            $(this).children(".handle").hide();
        }
    });

    // 删除图片
    $(document).on("click", ".file-iteme .handle", function(event){
        $(this).parent().remove();
        //$(this).parent()[0].textContent
        deleteFIle(fileName, $(this).parent()[0].textContent)
    });

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

    function deleteFIle(names, deleteFile) {
        var name = "";
        var files = names.split(",");
        for (i = 0; i < files.length - 1; i++ ) {
            if (files[i] !== deleteFile) {
                name += files[i] + ",";
            }
        }
        fileName = name;
        $("#fileName").val(fileName);
    }

    //放大预览
    $('#uploader-list img').on('click', function () {
        layer.photos({
            photos: '#uploader-list',
            shadeClose: false,
            closeBtn: 2,
            anim: 0
        });
    })
});