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
            console.log(res);
            // if (res.CODE === 200) {
            //     fileStatus= true;
            //     alert("aaaa");
            //     var formSatellite = document.getElementById("regulationSafeForm");//获取所要提交form的id
            //     var data = new FormData(formSatellite);  //用所要提交form做参数建立一个formdata对象
            //     fsubmit(data);//调用函数
            // //     var tr = demoListView.find('tr#upload-' + index)
            // //         , tds = tr.children();
            // //     tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
            // //     tds.eq(3).html(''); //清空操作
            // //
            // //     name += res.fileName + ",";
            // //     $("#enclosure").val(name);
            // }
        }
        , error: function () {
            console.log("异常")
        }
    });

    // function fsubmit(data) {
    //     $.ajax({
    //         url: Feng.ctxPath + "/regulationSafe/add",
    //         type: "POST",
    //         data: date,
    //         async: false,
    //         contentType: false,   //jax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
    //         processData: false,   //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
    //         error: function (request) {
    //             parent.layer.alert("网络超时");
    //         },
    //         success: function (data) {
    //             alert("上传成功！");
    //         }
    //     });
    //     return false;
    // }

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
        // console.log(uploadInst.upload());
        uploadInst.upload();
        if (fileStatus){
            alert("aaa");
            var ajax = new $ax(Feng.ctxPath + "/regulationSafe/add", function (data) {
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
        }

    });
});