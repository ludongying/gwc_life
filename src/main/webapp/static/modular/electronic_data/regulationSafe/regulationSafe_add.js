/**
 * 法律法规/航线安全增加对话框
 */
var MenuInfoDlg = {
    data: {
        pid: "",
        pName: ""
    }
};
layui.use(['layer', 'form', 'admin', 'ax', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var upload = layui.upload;
    var fileStatus = false;
    var ajax;
    var formData;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto(); 影响选择结构树
    if (Feng.getUrlParam("treeId") !== "") {
        $.ajax({
            url: Feng.ctxPath + "/dict/detail/" + Feng.getUrlParam("treeId"),
            dataType: 'json',
            type: 'get',
            success: function (data) {
                console.log(data);
                $("#lawRegularId").val(Feng.getUrlParam("treeId"));
                $("#lawRegularName").val(data.name);
            }
        })
    }

    // 点击父级菜单
    $('#lawRegularName').click(function () {
        var formName = encodeURIComponent("parent.MenuInfoDlg.data.pcodeName");
        var formId = encodeURIComponent("parent.MenuInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/dict/getDictTreeByDictTypeCode?dictTypeCode=LAWS_REGULATION");
        layer.open({
            type: 2,
            title: '法律法规',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#lawRegularId").val(MenuInfoDlg.data.pid);
                $("#lawRegularName").val(MenuInfoDlg.data.pcodeName);
            }
        });
    });


    //选完文件后不自动上传
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
                $('#name').val(file.name.substring(0, file.name.lastIndexOf(".")));
                $('.layui-upload').append('<span id="choose" class="layui-inline layui-upload-choose">' + file.name + '</span>');
                $('#fileName').val(file.name)
            });
        }
        , done: function (res) {
            if (res.CODE === 200) {
                formData.fileName = res.fileName;
                formData.type = "REGULATIONS";
                ajax = new $ax(Feng.ctxPath + "/regulationSafe/add", function (data) {
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
            }
        }
    });


    function selectOnlyByName() {
        var insertStatus = false;
        $.ajax({
            url: Feng.ctxPath + '/regulationSafe/selectOnlyByName',
            type: "GET",
            async: false,
            data: {
                name: $('#name').val(),
                type: 'REGULATIONS'
            },
            success: function (data) {
                if (data) {
                    insertStatus = data;
                }else {
                    Feng.error("文件名称已存在!");
                }
            }
        });
        return insertStatus;
    }

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if ($('#fileName').val() === "") {
            Feng.error("附件不能为空");
            return false
        } else {
            console.log(selectOnlyByName());
            if (selectOnlyByName()) {
                formData = data.field;
                uploadInst.upload();
            }
            return false
        }
    });
});