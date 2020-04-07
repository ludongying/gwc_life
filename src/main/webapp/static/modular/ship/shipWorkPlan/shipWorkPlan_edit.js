/**
 * 工作计划编辑对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'laydate','formSelects','func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var formSelects = layui.formSelects;
    var func= layui.func;

    laydate.render({
        elem: '#startDate',
        type: 'datetime',
        trigger: 'click'
    });
    laydate.render({
        elem: '#endDate',
        type: 'datetime',
        trigger: 'click'
    });


    // 打开弹窗的按钮对象
    var btndelete = document.getElementById("delete");
    var planid = Feng.getUrlParam("shipWorkPlanId");
    console.log(planid.valueOf());
    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化工作计划的详情数据
    var ajax = new $ax(Feng.ctxPath + "/shipWorkPlan/detail/" + Feng.getUrlParam("shipWorkPlanId"));
    var result = ajax.start();
    form.val('shipWorkPlanForm',result);

    //获取下拉框分组多选下拉框
    $.ajax({
        url: Feng.ctxPath + '/person/listPersons?ids='+result.personIds,
        dataType: 'json',
        type: 'get',
        success: function (data) {
            var content = data.content;
            persons=content;
            formSelects.data('selPersons', 'local', {
                arr: content
            });
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/shipWorkPlan/update", function (data) {
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

    // 删除按钮点击事件
    btndelete.onclick = function () {
        // Feng.confirm("是否删除工作日志?", function () {
            $.ajax({
                url: Feng.ctxPath + '/shipWorkPlan/delete?shipWorkPlanId='+result.id,
                type: "post",
                data: {},
                dataType: "json",
                success: function (data) {
                    Feng.success("删除成功!");
                    admin.putTempData('formOk', true);//传给上个页面，刷新table用

                },
                //#3这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
                error: function f(XMLHttpRequest, textStatus, errorThrown) {
                    Feng.error("删除失败!" + data.message + "!");
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus); // paser error;
                }
            })
             admin.closeThisDialog();//关掉对话框
        // });
    }
});