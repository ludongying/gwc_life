/**
 * 物资信息增加对话框
 */

layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //表单校验
    loadVerify(form);

    //初始化物资类型下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=MUNITION_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#typeId').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            form.render('select');//表单渲染 把内容加载进去
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/munitionInfo/add", function (data) {
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
    });
});