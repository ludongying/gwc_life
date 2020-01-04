$(function () {
    //设置工具栏的显示和隐藏。
    $("#header").mouseenter(function () {
        $("#headerContent").css("display", "block");
    })
    $("#header").mouseleave(function () {
        $("#headerContent").css("display", "none");

    })

    var map = L.map('map', {
        center: [34.73, 121.99],
        //minZoom: 2,
        zoom: 7
    });
    var set = "";
    var mbUrl;
    var c1 = null, c2 = null;
    refreshChart();

    function refreshChart() {
        var a = $('input[name="rdoChartTransparent"]:radio:checked').val();
        //var a = $("#selChartTransparent").val();
        if (a == "0") {
            try {
                c1.onRemove(map);
            } catch (e) { }
            try {
                c2.onRemove(map);
            } catch (e) { }
        } else if (a == "1") {
            try {
                c1.onRemove(map);
            } catch (e) { }
            try {
                c2.onRemove(map);
            } catch (e) { }
            c1 = L.tileLayer.wms("http://127.0.0.1:8080/wms?", {
                layers: 'ENC', //必须是ENC
                format: 'image/png'  //只能是png图片，猜测为服务器端为png类型211.154.167.102:9402
            });

            c1.addTo(map);
        }
        var c2= L.tileLayer.wms("http://127.0.0.1:8080/wms?", {
            layers: 'ENC', //必须是ENC
            format: 'image/png'  //只能是png图片，猜测为服务器端为png类型
        });
        c2.addTo(map);
        c2.setParams({
            'LAYERS': 'ENC',
            'CSBOOL': GetCSBOOL(),
            'CSVALUE':GetCSVALUE(),
            'CRS': 'EPSG:3395',
            'TRANSPARENT': 'false'
        });
    }

    //海图图层显示参数
    function GetCSBOOL() {

        //添加
        //strChartInfo += $("#selChartTransparent").val() + "|";
        strChartInfo = $('input[name="rdoChartTransparent"]:radio:checked').val() + "|";
        //添加
        var strCSBOOL = "";
        for (var i = 16; i > 0; i--) {
            var istrue = $('input[name="cbxChartCSBOOL"][value="' + i + '"]').is(':checked');
            if (istrue) {
                strCSBOOL = strCSBOOL + '1';
            } else {
                strCSBOOL = strCSBOOL + '0';
            }
        }
        //将二进制转换成十六进制
        strCSBOOL= parseInt(strCSBOOL, 2).toString(16);
        //添加
        return strCSBOOL;
    }
    function GetCSVALUE(){
        var CSVALUE = txtChart1.value + "," + txtChart2.value + "," + txtChart3.value + "," + txtChart4.value + ",";
        CSVALUE += $("#selChart5").val() + "," + $("#selChart6").val() + "," + $("#selChart7").val() + ",";
        CSVALUE += txtChart8.value + "," + txtChart9.value + "," + txtChart10.value + ",";
        CSVALUE += $("#selChart11").val();
        return CSVALUE;
    }

    //设置页面修改input或者select触发页面刷新
    $("input").click(function () {
        refreshChart();
    });
    $("select").click(function () {
        refreshChart();
    });
});