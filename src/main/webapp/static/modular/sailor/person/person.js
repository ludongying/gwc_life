layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 船员信息管理
     */
    var Person = {
        tableId: "personTable",
        condition: {
            personName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Person.initColumn = function () {
        return [[
            {title: 'ID', field: 'id', sort: true, align: "center", hide:true},
            {title: '姓名', field: 'personName', sort: true, align: "center"},
            {title: '所属执法船', field: 'lawShipName', sort: false, align: "center"},
            {title: '出生年月', field: 'birthday', sort: true, align: "center", templet: "<div>{{layui.util.toDateString(d.birthday, 'yyyy-MM-dd')}}</div>"},
            {title: '性别', field: 'sex', sort: false, align: "center", templet: function (d) {
                    if (d.sex === 'M')
                        return "<span class='layui-badge layui-bg-blue'>男</span></b>";
                    else if(d.sex === 'F')
                        return "<span class='layui-badge layui-bg-orange'>女</span></b>";
                    else
                        return "</b>";
                }
            },
            {title: '民族', field: 'nation', sort: false, align: "center"},
            {title: '身份证号', field: 'idNumber', sort: false, align: "center"},
            {title: '籍贯', field: 'birthPlace', sort: false, align: "center"},
            {title: '职务', field: 'duty', sort: false, align: "center"},
            {title: '岗位', field: 'positionId', sort: false, align: "center", hide:true},
            {title: '手机IP', field: 'phoneIp', sort: false, align: "center"},
            {title: '联系方式', field: 'phone', sort: false, align: "center", hide:true},
            {title: '证书', field: 'certificateId', sort: false, align: "center", templet: function (d) {
                    // var url = Feng.ctxPath + '/certificate?id=' + d.certificateId + 'htmltype=person';
                    var url = Feng.ctxPath + '/certificate?ids=' + d.certificateId;
                    if(d.certificateId === null || d.certificateId === ''){
                        return '<a style="color: #01AAED;" href="' + url + '">共0张</a>';
                    }
                    else {
                        var certificateStrs = d.certificateId.split(",");
                        //alert(certificateStrs);
                        return '<a style="color: #01AAED;" href="' + url + '">共'+ certificateStrs.length + '张</a>';
                    }
                }},
            {title: '政治面貌（枚举）', field: 'political', sort: true, align: "center", hide:true},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Person.tableId,
        url: Feng.ctxPath + '/person/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: Person.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Person.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        Person.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Person.openAddPerson();
    });




    // 工具条点击事件
    table.on('tool(' + Person.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Person.onEditPerson(data);
        } else if (layEvent === 'delete') {
            Person.onDeletePerson(data);
        } else if (layEvent === 'detail') {
            Person.onDetailPerson(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    Person.search = function () {
        var queryData = {};
        queryData['personName'] = $("#personName").val().trim();
        table.reload(Person.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    Person.btnReset = function () {
        $("#personName").val("");
    };

    /**
     * 弹出增加船员信息
     */
    Person.openAddPerson = function () {
        func.open({
            title: '增加船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/person/person_add',
            tableId: Person.tableId
        });
    };

    /**
     * 点击编辑船员信息
     */
    Person.onEditPerson = function (data) {
        func.open({
            title: '编辑船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/person/person_edit?id=' + data.id,
            tableId: Person.tableId
        });
    };

    /**
     * 点击查看船员信息
     */
    Person.onDetailPerson = function (data) {
        func.open({
            title: '查看船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/person/person_detail?id=' + data.id,
            tableId: Person.tableId
        });
    };


    /**
     * 点击删除船员信息
     *
     * @param data 点击按钮时候的行数据
     */
    Person.onDeletePerson = function (data) {
        Feng.confirm("是否删除船员信息《" + data.personName + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/person/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(Person.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };

});
