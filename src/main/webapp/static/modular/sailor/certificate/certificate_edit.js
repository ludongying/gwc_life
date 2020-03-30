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
    var fileName = "";

    laydate.render({
        elem: '#issueDate',
        type: 'date',
        trigger: 'click',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#outDate').val()).getTime();
            if (endTime < startDate) {
                layer.msg('到期日期不能小于签发日期');
                $('#issueDate').val($('#outDate').val());
            }
        }
    });

    laydate.render({
        elem: '#outDate',
        type: 'date',
        trigger: 'click',
        done: function (value, date, endDate) {
            var startDate = new Date($('#issueDate').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                $('#outDate').val($('#issueDate').val());
                layer.msg('到期日期不能小于签发日期');
            }
        }
    });

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();
    //表单验证加载
    loadVerify(form);

    //初始化证书信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/certificate/detail/" + Feng.getUrlParam("certificateId"));
    var result = ajax.start();
    //多图片回显
    if (result.attachFilePath != null) {
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
    form.val('certificateForm', result);

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
            $('#attachment').append(
                '<div id="" class="file-iteme">' +
                '<div class="handle"><i class="layui-icon layui-icon-delete"></i></div>' +
                '<img style="width: 100px;height: 100px;" src=' + res.fileName + '>' +
                '<div class="info">' + res.fileName + '</div>' +
                '</div>'
            );
            //放大预览（若不加，新上传的图片无法放大预览）
            $('#attachment img').on('click', function () {
                layer.photos({
                    photos: '#attachment',
                    shadeClose: false,
                    closeBtn: 2,
                    anim: 0
                });
            })
            fileName = fileName + res.fileName + ","
            $("#attachFilePath").val(fileName);
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
        $("#attachFilePath").val(fileName);
    }

    //放大预览
    $('#attachment img').on('click', function () {
        layer.photos({
            photos: '#attachment',
            shadeClose: false,
            closeBtn: 2,
            anim: 0
        });
    })

    // var issueDate = laydate.render({
    //     elem: '#issueDate',
    //     type: 'date',
    //     trigger: 'click',
    //     max: "2099-12-31",//设置一个默认最大值
    //     done: function (value, date) {
    //         outDate.config.min = {//限制日历范围
    //             year: date.year,
    //             month: date.month - 1,
    //             date: date.date,
    //             hours: 0,
    //             minutes: 0,
    //             seconds: 0
    //         };
    //         var startDate = new Date(value).getTime();
    //         var endTime = new Date($('#outDate').val()).getTime();
    //         if (endTime < startDate) {
    //             layer.msg('签发日期不能小于到期日期,请重新选择');
    //             $('#issueDate').val();
    //         }
    //
    //     }
    // });
    //
    // var outDate = laydate.render({
    //     elem: '#outDate',
    //     type: 'date',
    //     trigger: 'click',
    //     min: "1900-1-1",//设置min默认最小值
    //     done: function (value, date) {
    //         issueDate.config.max = {//限制日历范围
    //             year: date.year,
    //             month: date.month - 1,//注意是month-1，写在date上的话你后边的日期选择不了
    //             date: date.date,
    //             hours: 0,
    //             minutes: 0,
    //             seconds: 0
    //         };
    //         var startDate = new Date($('#issueDate').val()).getTime();
    //         var endTime = new Date(value).getTime();
    //         if (endTime < startDate) {
    //             layer.msg('签发日期不能小于到期日期,请重新选择');
    //             $('#outDate').val();
    //             form.render();
    //         }
    //     }
    //     });

});