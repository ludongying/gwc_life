document.addEventListener('DOMContentLoaded', function() {
    var Calendar = FullCalendar.Calendar;
    var Draggable = FullCalendarInteraction.Draggable;

    // var containerEl = document.getElementById('external-events-list');
    // new Draggable(containerEl, {
    //     itemSelector: '.fc-event',
    //     eventData: function(eventEl) {
    //         return {
    //             title: eventEl.innerText.trim()
    //         }
    //     }
    // });

    var calendarEl = document.getElementById('calendar');
    var calendar = new Calendar(calendarEl, {
        plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list' ],
        header: {
            left: 'prev,next today',
            center: 'title',
            right: ' '
        },
        //对应顶部操作按钮的名称自定义
        buttonText: {

                prev: '<上一页',
                next: '下一页>',
                today: '今天',
                month: '月',
                week: '周',
                day: '日',
            },
        aspectRatio: 1.7,        //宽度:高度 比例，默认1.35，可自定义
        firstDay: 1,           //视图从每周几开始，默认0为周日，1为周1，2为周2，依此类推
        unselectAuto: true,		//当点击页面日历以外的位置时，是否自动取消当前的选中状态。
        axisFormat: 'H(:mm)tt',  //设置日历agenda视图下左侧的时间显示格式，默认显示如：5:30pm
        locale: 'zh-cn',
        editable: true,
        droppable:true,
        selectable: true,  // dataClick生效
        eventLimit: 4, // 显示更多
        displayEventEnd: true, // 显示结束时间

        events: function (info, successCallback, failureCallback) {
            $.ajax({
                url: Feng.ctxPath + '/shipWorkPlan/listPlans',
                type: "post",
                data: {},
                dataType: "json",
                success: function (data) {
                    successCallback(data.content)
                },
                //#3这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
                error: function f(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus); // paser error;
                }
            });
        }
        // , eventMouseEnter: function (event, jsEvent, view) { //鼠标划过的事件
        //     var eventStart = event.start;
        //     var eventEnd = event.end;
        //     layer.tips(eventStart, this);
        // }
        // , eventMouseLeave: function (event, jsEvent, view) { //鼠标离开的事件
        //     var index = layer.tips();
        //     calendar.fullCalendar('removeEvents', bgEvent.id);
        // }
        , eventClick: function (info) {
            openEditLayer(info);
            calendar.refetchEvents();
        }
        , dateClick: function () {
            openLayer();
            calendar.refetchEvents();
        }
        , eventRender: function (info) {
            info.el.innerHTML = info.event.title;   //主要靠这个实现 显示html内容
        }
    });
    calendar.render();

    $(document).ready(function () {

    });

    function initDateCtrl() {
        //日期时间选择器
        laydate.render({
            elem: '#stime' //指定元素
            , type: 'time'
            , format: 'HH:mm'
            , done: function (value, date) {
                // var etime = $("#etime").val();
                var ehour = parseInt(etime.split(":")[0]);
                var eminute = parseInt(etime.split(":")[1]);
                var hour = date.hours;
                var minute = date.minutes;
                if (ehour < hour) {
                    $("#finishtime").val(value);
                } else {
                    if (eminute < minute) {
                        $("#etime").val(value);
                    }
                }
            }
        });

        //日期时间选择器
        laydate.render({
            elem: '#etime' //指定元素
            , type: 'time'
            , format: 'HH:mm'
            , done: function (value, date) {
                // var stime = $("#stime").val();
                var shour = parseInt(stime.split(":")[0]);
                var sminute = parseInt(stime.split(":")[1]);
                var hour = date.hours;
                var minute = date.minutes;
                if (shour > hour) {
                    $("#stime").val(value);
                } else {
                    if (sminute > minute) {
                        $("#stime").val(value);
                    }
                }
            }
        });
    }

   //新增工作计划
    function openLayer() {
        addIndex = layer.open({
            id: 'add-form',
            title: '<i class="fa fa-plus"></i>&nbsp;新增工作计划',
            type: 2,
            fix: false,            // 加上边框
            skin: 'layui-layer-rim',
            area: ['800px', '550px'],
            shadeClose: true,
            content: Feng.ctxPath + '/shipWorkPlan/shipWorkPlan_add',
        });
    }

    function openEditLayer(info) {
            addIndex = layer.open({
                title: '<i class="fa fa-plus"></i>&nbsp;编辑工作计划',
                type: 2,
                fix: false,
                area: ['800px', '550px'],
                // 宽高
                content: Feng.ctxPath + '/shipWorkPlan/shipWorkPlan_edit?shipWorkPlanId='+info.event.id,
            });
    }

    function del() {
        var id = $("#id").val();
        layer.confirm('确定要删除吗？', {
            offset: '65px',
            title: '提示'
        }, function (i) {
            layer.close(i);
            layer.load(2);
            $.post("/shipWorkPlan/deleteByID", {id: id}, function (res) {
                layer.closeAll('loading');
                if (res.code === 200) {
                    // layer.msg("删除成功");
                    calendar.refetchEvents();
                    layer.closeAll('page');
                } else {
                    layer.msg(res.message);
                }
            });
        });
    }

    // 所有ew-event
    $('body').on('click', '*[ew-event]', function () {
        var event = $(this).attr('ew-event');
        if (event === 'closeDialog') {
            var id = $(this).parents('.layui-layer').attr('id').substring(11);
            layer.close(id);
        }
    });

});