/**
 * 物资出库详情对话框
 */

layui.use(['layer', 'form', 'admin', 'ax', 'table'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var table = layui.table;

    /**
     * 物资出库详情管理
     */
    var MunitionOutDetail = {
        tableId: "munitionOutDetailTable",
        condition: {
            munitionName: ""
        }
    };

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化物资入库的详情数据
    var ajax = new $ax(Feng.ctxPath + "/munitionOut/detail/" + Feng.getUrlParam("munitionOutId"));
    var result = ajax.start();
    form.val('munitionOutForm', result);

    //申请人员下拉框
    $.ajax({
        url: Feng.ctxPath + '/person/listPersonsByDept?deptId=',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#applyPerson').append(new Option(item.personName, item.personId));
            })
            $('#applyPerson').val(result.applyPerson);
            form.render('select');//表单渲染
        }
    });

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

    /**
     * 初始化表格的列
     */
    MunitionOutDetail.initColumn = function () {
        return [[
            {title: '出库物资列表编码', field: 'id', align: "center", hide: true},
            {title: '物资表id', field: 'munitionId', align: "center", hide: true},
            {title: '物资名称', field: 'munitionName', align: "center"},
            {title: '物资编码', field: 'code', align: "center"},
            {title: '仓库id', field: 'depotId', align: "center", hide: true},
            {title: '仓库名称', field: 'depotName', align: "center"},
            {title: '数量', field: 'totalNum', align: "center"},
            {title: '单位', field: 'unit', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center', hide: true}
        ]];
    };

    var tableResult = table.render({
        elem: '#' + MunitionOutDetail.tableId,
        url: Feng.ctxPath + '/munitionOutDetail/list?munitionMainId=' + $('#code').val(),
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: MunitionOutDetail.initColumn()
    });
});