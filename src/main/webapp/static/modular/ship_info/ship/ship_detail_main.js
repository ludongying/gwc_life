/**
 * 执法船信息管理详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'carousel', 'func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var carousel = layui.carousel;
    var func = layui.func;

    // 让当前iframe弹层高度适应
    //admin.iframeAuto();

    //图片轮播
    //获取浏览器宽度
    var H = $(window).height();
    var ins = carousel.render({
        elem: '#imgBoat'
        , width: '100%'
        , height: (H * 0.75).toString() + 'px'
        , arrow: 'always'
        , indicator: 'none'
        , autoplay: 'false'
    });

    //初始化执法船信息管理的详情数据
    var ajax = new $ax(Feng.ctxPath + "/ship/detailFirst/");
    var result = ajax.start();
    // form.val('shipForm',result);
    $('#id').val(result.id);
    $('#name').text(result.name);
    $('#shipCode').text(result.shipCode);
    $('#owner').text(result.owner);
    $('#nationality').text(result.nationalityDesp);
    $('#type').text(result.typeDesp);
    $('#length_total').text(result.lengthTotal);
    $('#length').text(result.length);
    $('#width').text(result.width);
    $('#deep').text(result.deep);
    $('#material').text(result.material);
    $('#tonnage').text(result.tonnage);
    $('#tonnage_net').text(result.tonnageNet);
    $('#area').text(result.area);
    $('#gunwaleCount').text(result.gunwaleCount);
    $('#peopleNum').text(result.peopleNum);
    $('#mainPower').text(result.mainPower);
    $('#manufacturer').text(result.manufacturer);
    $('#finishDate').text(result.finishDate);
    $('#remark').text(result.remark);
    // 证书信息
    var url = Feng.ctxPath + '/certificateShip?ids=' + result.certificateId + '&shipId=' + result.id;
    // var url = Feng.ctxPath + '/certificate/certificate_list?id=' + result.certificateId + '&htmltype=ship';
    if (result.certificateId === null || result.certificateId === '') {
        $('#certificateId').append('<a style="color: #01AAED;" href="' + url + '">共0张</a>');
    } else {
        var certificateStrs = result.certificateId.split(",");
        //alert(certificateStrs);
        $('#certificateId').append('<a style="color: #01AAED;" href="' + url + '">共' + certificateStrs.length + '张</a>');
    }

    //加载船舶图像轮播
    if (result.imageUrl != null) {
        var images = result.imageUrl.split(",");
        var count = 0;
        $.each(images, function (index, item) {
            $('#image').append('<div style="text-align :center"><img class="img-thumbnail" src=' + item + '></div>');
        })
        ins.reload({
            elem: '#imgBoat'
            , width: '100%'
            , height: (H * 0.75).toString() + 'px'
            , arrow: 'always'
            , indicator: 'none'
            , autoplay: 'false'
        });
    }

    /**
     * 点击编辑执法船信息管理
     */
    $('#edit').click(function () {
        // alert($('#id').val());
        func.open({
            title: '编辑执法船信息',
            area: ['1000px', '805px'],
            content: Feng.ctxPath + '/ship/ship_edit?shipId=' + $('#id').val()
            // tableId: Ship.tableId
        });
    });
});