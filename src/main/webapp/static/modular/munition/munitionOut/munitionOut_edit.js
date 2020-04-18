/**
 * 物资出库编辑对话框
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

    laydate.render({
        elem: '#applyTime',
        type: 'date',
        trigger: 'click'
    });

    laydate.render({
        elem: '#inOutTime',
        type: 'date',
        trigger: 'click'
    });

    //初始化物资入库的详情数据
    var ajax = new $ax(Feng.ctxPath + "/munitionOut/detail/" + Feng.getUrlParam("munitionOutId"));
    var result = ajax.start();
    form.val('munitionOutForm',result);

    //初始化物资类型下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=MUNITION_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#munitionType').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#munitionType').val(result.munitionType);
            form.render('select');//表单渲染 把内容加载进去
        }
    });

    //申请人员下拉框
    $.ajax({
        url: Feng.ctxPath + '/person/listPersonsByDept?deptId=',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#applyPerson').append(new Option(item.personName, item.personId));//往下拉菜单里添加元素
            })
            $('#applyPerson').val(result.applyPerson);
            form.render('select');//表单渲染
        }
    });

    // //出库人员下拉框
    // $.ajax({
    //     url: Feng.ctxPath + '/person/listPersonsByDept?deptId=',
    //     dataType: 'json',
    //     type: 'get',
    //     success: function (data) {
    //         $.each(data, function (index, item) {
    //             $('#inOutPerson').append(new Option(item.personName, item.id));//往下拉菜单里添加元素
    //         })
    //         $('#inOutPerson').val(result.inOutPerson);
    //         form.render('select');//表单渲染
    //     }
    // });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/munitionOut/update", function (data) {
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