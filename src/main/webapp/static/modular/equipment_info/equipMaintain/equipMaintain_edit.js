/**
 * 设备维护编辑对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

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
                $('#endTime').val($('#startTime').val());
                layer.msg('结束时间不能小于开始时间');
            }
        }
    });

    //设备名称下拉框初始化(设备类型为空时，显示所有设备名称）
    equipNameSels($("#type").val());


    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化设备维护的详情数据
    var ajax = new $ax(Feng.ctxPath + "/equipMaintain/detail/" + Feng.getUrlParam("equipMaintainId"));
    var result = ajax.start();
    form.val('equipMaintainForm',result);

    // 点击父级菜单
    $('#typeDesp').click(function () {
        var formName = encodeURIComponent("parent.MenuInfoDlg.data.pcodeName");
        var formId = encodeURIComponent("parent.MenuInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/dict/getDictTreeByDictTypeCode?dictTypeCode=EQUIPMENT_TYPE");
        layer.open({
            type: 2,
            title: '设备类型',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#type").val(MenuInfoDlg.data.pid);
                $("#typeDesp").val(MenuInfoDlg.data.pcodeName);
                if(MenuInfoDlg.data.pid!=null && MenuInfoDlg.data.pid!=""){
                    //设备名称下拉框重新渲染
                    equipNameSels(MenuInfoDlg.data.pid);
                }
            }
        });
    });


    //设备名称下拉框渲染
    function equipNameSels(equipType) {
        if(equipType!=null && equipType!="") {
            $('#equipId').empty();
        }
        //设备名称下拉框
        var params={
            equipName:"",
            equipType:equipType
        }
        $.ajax({
            url: Feng.ctxPath + '/equip/listByTypeAndName',
            data: params,
            dataType: 'json',
            type: 'post',
            success: function (data) {
                $.each(data, function (index, item) {
                    $('#equipId').append(new Option(item.name, item.id));//往下拉菜单里添加元素
                })
                $('#equipId').val(result.equipId);
                //设备名称改变，设备型号下拉框联动
                specSelEvent();
                form.render('select');//表单渲染 把内容加载进去
            }
        });
    }

    // 设备名称与设备型号下拉框联动
    layui.use('form', function() {
        form.on('select(nameFilter)', function (data) {
            $('#specification').empty();
            var params = {
                equipName:data.value,
                equipType:$("#type").val()
            }
            //检查项目添加到下拉框中
            $.ajax({
                url: Feng.ctxPath + '/equip/listByTypeAndName',
                dataType: 'json',
                data: params,
                type: 'post',
                success: function (data) {
                    $("#specification").empty();//清空下拉框的值
                    $.each(data, function (index, item) {
                        $('#specification').append(new Option(item.specification, item.specification));// 下拉菜单里添加元素
                    });
                    $('#specification').val(result.specification);
                    form.render("select");//重新渲染
                }
            });
        });
    });

    //当设备名称赋值时，未启动select事件，则设备规格联动事件
    function specSelEvent(){
        $('#specification').empty();
        var params = {
            equipName:$("#equipId").val(),
            equipType:$("#type").val()
        }
        //检查项目添加到下拉框中
        $.ajax({
            url: Feng.ctxPath + '/equip/listByTypeAndName',
            dataType: 'json',
            data: params,
            type: 'post',
            success: function (data) {
                $("#specification").empty();//清空下拉框的值
                $.each(data, function (index, item) {
                    $('#specification').append(new Option(item.specification, item.specification));// 下拉菜单里添加元素
                });
                $('#specification').val(result.specification);
                form.render("select");//重新渲染
            }
        });
    }

    //工作类型下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=EQUIP_WORK_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#problemType').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#problemType').val(result.problemType);
            form.render('select');//表单渲染
        }
    });

    //负责人员下拉框
    $.ajax({
        url: Feng.ctxPath + '/person/listPersonsByDept?deptId=',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#maintainPerson').append(new Option(item.personName, item.id));//往下拉菜单里添加元素
            })
            $('#maintainPerson').val(result.maintainPerson);
            form.render('select');//表单渲染
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/equipMaintain/update", function (data) {
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