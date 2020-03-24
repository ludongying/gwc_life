/**
 * 航行日志增加对话框
 */

layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

    // laydate.render({
    //     elem: '#date',
    //     type: 'date',
    //     format: 'yyyy年MM月dd日',
    //     trigger: 'click'
    // });

    laydate.render({
        elem: '#startTime',
        type: 'datetime',
        trigger: 'click',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
            if (endTime < startDate) {
                layer.msg('结束时间不能小于开始时间');
                $('#startTime').val($('#endTime').val());
            }
        }
    });

    laydate.render({
        elem: '#endTime',
        type: 'datetime',
        trigger: 'click',
        done: function (value, date, endDate) {
            var startDate = new Date($('#startTime').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('结束时间不能小于开始时间');
                $('#endTime').val($('#startTime').val());
            }
        }
    });

    //执法船信息获取
    $.ajax({
        url: Feng.ctxPath + '/ship/listShips/',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#shipId').val(item.id);
                $('#shipName').val(item.name);
            })
            form.render('input');//表单渲染 把内容加载进去
        }
    });

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/sailLog/add", function (data) {
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