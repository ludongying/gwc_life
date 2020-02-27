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
            var files = obj.pushFile();
            obj.preview(function (index, file, result) {
                console.log(index); //得到文件索引
                console.log(file); //得到文件对象
                $('.layui-upload').append('<span class="layui-inline layui-upload-choose">' + file.name + '</span>');
                console.log($('#name').val());
                if ($('#name').val() === "") {
                    $('#name').val(file.name);
                }
            });
        }
        // , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
        //
        //     // if (selectOnlyByName("111")) {
        //     //     layer.load(); //上传loading
        //     // } else {
        //     //     alert("aaa")
        //     // }
        //
        // }
        , done: function (res) {
            if (res.CODE === 200) {
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


    function selectOnlyByName(data) {
        $.ajax({
            url: Feng.ctxPath + '/regulationSafe/selectOnlyByName',
            type: "GET",
            data: {
                name: '组织结构20200210.pdf',
                type: 'SAFE'
            }
        });
    }

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        formData = data.field;
        console.log(formData);
        uploadInst.upload();
        return false

    });
});