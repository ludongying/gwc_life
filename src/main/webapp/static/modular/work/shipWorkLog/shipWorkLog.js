// layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
//     var $ = layui.$;
//     var layer = layui.layer;
//     var form = layui.form;
//     var table = layui.table;
//     var $ZTree = layui.ztree;
//     var $ax = layui.ax;
//     var laydate = layui.laydate;
//     var admin = layui.admin;
//     var func = layui.func;
//
//     /**
//      * 工作日志记录管理
//      */
//     var ShipWorkLog = {
//         tableId: "shipWorkLogTable",
//         condition: {
//             shipWorkLogName: ""
//         }
//     };
//
//     /**
//      * 初始化表格的列
//      */
//     ShipWorkLog.initColumn = function () {
//         return [[
//             {title: 'ID', field: 'id', align: "center"},
//             {title: '记录时间（yyyy-MM-dd HH:mm:ss）', field: 'recordDate', align: "center"},
//             {title: '记录内容', field: 'content', align: "center"},
//             {title: '工作日程类型', field: 'recordType', align: "center"},
//             {title: '创建人', field: 'createPerson', align: "center"},
//             {title: '创建时间', field: 'createDate', align: "center"},
//             {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
//         ]];
//     };
//
//     // 渲染表格
//     var tableResult = table.render({
//         elem: '#' + ShipWorkLog.tableId,
//         url: Feng.ctxPath + '/shipWorkLog/list',
//         page: true,
//         height: "full-97",
//         cellMinWidth: 100,
//         cols: ShipWorkLog.initColumn()
//     });
//
//     /**
//      * 左侧搜索
//      */
//     // 搜索按钮点击事件
//     $('#btnSearch').click(function () {
//         ShipWorkLog.search();
//     });
//     // 重置按钮点击事件
//     $('#btnReset').click(function () {
//         ShipWorkLog.btnReset();
//     });
//
//
//     /**
//      * 右侧操作
//      */
//     // 添加按钮点击事件
//     $('#btnAdd').click(function () {
//         ShipWorkLog.openAddShipWorkLog();
//     });
//
//
//     /**
//      * 工具条点击事件
//      */
//     table.on('tool(' + ShipWorkLog.tableId + ')', function (obj) {
//         var data = obj.data;
//         var layEvent = obj.event;
//
//         if (layEvent === 'edit') {
//             ShipWorkLog.onEditShipWorkLog(data);
//         } else if (layEvent === 'delete') {
//             ShipWorkLog.onDeleteShipWorkLog(data);
//         } else if (layEvent === 'detail') {
//             ShipWorkLog.onDetailShipWorkLog(data);
//         }
//     });
//
//
//     /**
//      * 点击搜索按钮
//      */
//     ShipWorkLog.search = function () {
//         var queryData = {};
//         queryData['shipWorkLogName'] = $("#shipWorkLogName").val().trim();
//         table.reload(ShipWorkLog.tableId, {where: queryData});
//     };
//
//     /**
//      * 重置查询条件
//      */
//     ShipWorkLog.btnReset = function () {
//         $("#shipWorkLogName").val("");
//     };
//
//     /**
//      * 弹出增加工作日志记录
//      */
//     ShipWorkLog.openAddShipWorkLog = function () {
//         func.open({
//             title: '增加工作日志记录',
//             area: ['1000px', '500px'],
//             content: Feng.ctxPath + '/shipWorkLog/shipWorkLog_add',
//             tableId: ShipWorkLog.tableId
//         });
//     };
//
//     /**
//      * 点击编辑工作日志记录
//      */
//     ShipWorkLog.onEditShipWorkLog = function (data) {
//         func.open({
//             title: '编辑工作日志记录',
//             area: ['1000px', '500px'],
//             content: Feng.ctxPath + '/shipWorkLog/shipWorkLog_edit?shipWorkLogId=' + data.id,
//             tableId: ShipWorkLog.tableId
//         });
//     };
//
//     /**
//      * 点击查看工作日志记录
//      */
//     ShipWorkLog.onDetailShipWorkLog = function (data) {
//         func.open({
//             title: '查看工作日志记录',
//             area: ['1000px', '500px'],
//             content: Feng.ctxPath + '/shipWorkLog/shipWorkLog_detail?shipWorkLogId=' + data.id,
//             tableId: ShipWorkLog.tableId
//         });
//     };
//
//
//     /**
//      * 点击删除工作日志记录
//      *
//      * @param data 点击按钮时候的行数据
//      */
//     ShipWorkLog.onDeleteShipWorkLog = function (data) {
//         Feng.confirm("是否删除工作日志记录《" + data.name + "》吗?", function () {
//             var ajax = new $ax(Feng.ctxPath + "/shipWorkLog/delete", function (data) {
//                 if (data.success) {
//                     Feng.success("删除成功!");
//                     table.reload(ShipWorkLog.tableId);
//                 } else {
//                     Feng.error(data.message);
//                 }
//             }, function (data) {
//                 Feng.error("删除失败!" + data.message + "!");
//             });
//             ajax.set("shipWorkLogId", data.id);
//             ajax.start();
//         });
//     };
//
// });

document.addEventListener('DOMContentLoaded', function() {
    var Calendar = FullCalendar.Calendar;
    var Draggable = FullCalendarInteraction.Draggable;

    var containerEl = document.getElementById('external-events-list');
    new Draggable(containerEl, {
        itemSelector: '.fc-event',
        eventData: function(eventEl) {
            return {
                title: eventEl.innerText.trim(),
                Color: eventEl.style.backgroundColor
            }
        }
    });
    var calendarEl = document.getElementById('calendar');
    var calendar = new Calendar(calendarEl, {
        plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list' ],

        customButtons: {            //自定义header属性中按钮[customButtons与header并用]
            myCustomButton: {
                text: '导出',
                click: function() {
                    alert('点击导出按钮!');
                }
            }
        }
        ,header: {
            left: 'prev,next today',
            center: 'title',
            right:'myCustomButton'
        },

        defaultView:'timeGridDay',
        locale: 'zh-cn',
        editable: true,
        droppable: true,
        selectable: true,  // dataClick生效
        eventLimit: 4, // 显示更多
        displayEventEnd: true, // 显示结束时间
        //显示事件详情
        events: function (start,end,timezone, callback) {
            $.ajax({
                url: Feng.ctxPath + '/shipWorkLog/listLogs',
                type: "post",
                // contentType: "application/json; charset=utf-8",
                data:{},
                dataType:"json",
                success: function (data) {
                    // alert("成功");
                    console.log(data);
                    var events = [];
                    for (var i = 0; i < data.content.length; i++) {
                        events.push({
                            id: data.content[i].id,
                            title:data.content[i].title,
                            start:'2020-03-12 12:00:00',
                            end: '2020-03-12 18:00:00',
                        });
                    }
                    try {
                        callback(data);

                    } catch (e) {

                        console.info(e);
                    }
                },
                //#3这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
                    error:function f(XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus); // paser error;
                    }

            });
        }
        // events:[
        //     {
        //         title: 'Event1',
        //         start: '2020-03-12 12:00:00',
        //         end:'2020-03-12 18:00:00'
        //     },
        //     {
        //         title: 'Event2',
        //         start: '2020-03-11'
        //     }
        // ]

        , dateClick: function () {
            openLayer();
        }
        ,eventClick: function (calEvent, jsEvent, view, callback) {
            console.log(calEvent.title());
            console.log(jsEvent);
            console.log(view);
        }
    });
    calendar.render();

    $(document).ready(function () {

    });

    //新增工作日程
    function openLayer() {
        addIndex = layer.open({
            id: 'add-form',
            title: '<i class="fa fa-plus"></i>&nbsp;新增工作日程',
            type: 2,
            fix: false,            // 加上边框
            skin: 'layui-layer-rim',
            area: ['600px', '400px'],
            shadeClose: true,
            content: Feng.ctxPath + '/shipWorkLog/shipWorkLog_add',
        });
    }

    //编辑工作日程
    function EditLayer(wlid) {
        addIndex = layer.open({
            title: '<i class="fa fa-plus"></i>&nbsp;编辑工作日程',
            type: 2,
            fix: false,
            area: ['800px', '550px'],
            // 宽高
            content: Feng.ctxPath + '/shipWorkLog/shipWorkLog_detail?shipWorkLogId=' + wlid.id,
        });
    }

});