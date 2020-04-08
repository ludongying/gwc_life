layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func','path','navi'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;
    var path = layui.path;
    var navi = layui.navi;
    //图层定义
    let measureControl;//测距和面积
    let drawnItems;//标绘
    let pointsGroup = L.layerGroup();//执法船
    let ForbiddenFishLine = L.layerGroup();//禁渔区
    let FishLine = L.layerGroup();//渔区
    //菜单栏
    var menuid = document.getElementById('menuid');
    var hide = document.getElementById('hide');
    var CloseHide = document.getElementById('Closehide');
    hide.onclick = function () {
        menuid.style.display = "block";
        drawmenu.style.display = "none";
        AISmenu.style.display = "none";
    }
    CloseHide.onclick = function(){
        menuid.style.display = "none";
    }

    var map = L.map('map', {
        center: [34.73, 121.99],
        //minZoom: 2,
        zoom: 7,
        zoomControl: false,
        // measureControl: true,
        attributionControl: false,
    });
    //实时显示鼠标位置
    L.control.mousePosition().addTo(map);
    //比例尺
    L.control.betterscale().addTo(map);
    //平移
    L.control.pan().addTo(map);
    //放大缩小
    map.addControl(new L.Control.Zoomslider());
    //一键复位功能 前进/后退
    L.control.navbar().addTo(map);

    //测距和面积
    measureControl = new L.Control.Measure({measureid:"measureid",});
    measureControl.addTo(map);
    $('#measureid').mouseenter(function(){
        $('#measureimg').attr("src","/common/plugins/map/areas/assets/measureopen.png");
        // $('#measureimg').attr("alt","open");
    });
    $('#measureid').mouseleave(function(){
        $('#measureimg').attr("src","/common/plugins/map/areas/assets/measure.png");
        // $('#measureimg').attr("alt","close");
    });
    //图形绘制
    //标绘
    drawnItems = L.featureGroup().addTo(map);
    map.addControl(new L.Control.Draw({
        parentid:"drawmenu",
        edit: {
            featureGroup: drawnItems,
            poly: {
                allowIntersection: false
            }
        },
        draw: {
            polygon: {
                allowIntersection: false,
                showArea: true
            }
        }
    }));
    map.on(L.Draw.Event.CREATED, function (event) {
        var layer = event.layer;
        drawnItems.addLayer(layer);
    });
    $('#display').click(function () {
        if ($(this).attr("alt")=="close"){
            $(this).attr("src","/common/plugins/map/images/open.png");
            $(this).attr("alt","open");
            map.addLayer(drawnItems);
        }
        else {
            $(this).attr("src","/common/plugins/map/images/close.png");
            $(this).attr("alt","close");
            map.removeLayer(drawnItems);
        }
    });
    var drawid = document.getElementById('drawid');
    var drawmenu = document.getElementById('drawmenu');
    var menuClose = document.getElementById('menuClose');
    drawid.onclick = function () {
        drawmenu.style.display = "block";
        menuid.style.display = "none";
        AISmenu.style.display = "none";
        // alert(1);
    }
    menuClose.onclick = function(){
        drawmenu.style.display = "none";
    }
    $('#drawid').mouseenter(function(){
        $('#drawimg').attr("src","/common/plugins/map/draw/images/drawo.png");
        // $('#measureimg').attr("alt","open");
    });
    $('#drawid').mouseleave(function(){
        $('#drawimg').attr("src","/common/plugins/map/draw/images/drawclose.png");
        // $('#measureimg').attr("alt","close");
    });

    //北斗
    $('#compassid').mouseenter(function(){
        $('#compassimg').attr("src","/common/plugins/map/images/compasso.png");
        // $('#measureimg').attr("alt","open");
    });
    $('#compassid').mouseleave(function(){
        $('#compassimg').attr("src","/common/plugins/map/images/compassc.png");
        // $('#measureimg').attr("alt","close");
    });
    //AIS
    $('#AISid').mouseenter(function(){
        $('#AISimg').attr("src","/common/plugins/map/images/AISopen.png");
        // $('#measureimg').attr("alt","open");
    });
    $('#AISid').mouseleave(function(){
        $('#AISimg').attr("src","/common/plugins/map/images/AISc.png");
        // $('#measureimg').attr("alt","close");
    });
    var AISid = document.getElementById('AISid');
    var AISmenu = document.getElementById('AISmenu');
    var AISClose = document.getElementById('AISClose');
    AISid.onclick=function(){
        AISmenu.style.display = "block";
        drawmenu.style.display = "none";
        menuid.style.display = "none";
    }
    AISClose.onclick=function(){
        AISmenu.style.display = "none";
    }
    //雷达
    $('#radioid').mouseenter(function(){
        $('#radioimg').attr("src","/common/plugins/map/images/radioopen.png");
        // $('#measureimg').attr("alt","open");
    });
    $('#radioid').mouseleave(function(){
        $('#radioimg').attr("src","/common/plugins/map/images/radioc.png");
        // $('#measureimg').attr("alt","close");
    });
    //目录
    $('#menu-id').mouseenter(function(){
        $('#menuimg').attr("src","/common/plugins/map/images/menuopen.png");
        // $('#measureimg').attr("alt","open");
    });
    $('#menu-id').mouseleave(function(){
        $('#menuimg').attr("src","/common/plugins/map/images/menuc.png");
        // $('#measureimg').attr("alt","close");
    });
    refreshChart();
    var c2=null;
    var print=null;
    //小地图
    var mini_map = L.tileLayer.wms("http://192.168.18.212:8080/wms?", {
        layers: 'ENC', //必须是ENC
        format: 'image/png'  //只能是png图片，猜测为服务器端为png类型
    });
    mini_map.setParams({
        'LAYERS': 'ENC',
        'CSBOOL': GetCSBOOL(),
        'CSVALUE': GetCSVALUE(),
        'CRS': 'EPSG:3395',
        'TRANSPARENT': 'false'
    });
    var miniMap = new L.Control.MiniMap(mini_map, { toggleDisplay: true });
    miniMap.addTo(map);
    // miniMap.addLayer(ForbiddenFishLine);
    function refreshChart() {
        c2= L.tileLayer.wms("http://192.168.18.212:8080/wms?", {
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
        //打印
        print = L.control.browserPrint({
                title: GetCSBOOL(),
                CSBOOL:GetCSBOOL(),
                CSVALUE:GetCSVALUE(),
                printModes: [L.control.browserPrint.mode.custom()]
            }
        );
        map.addControl(print);
    }

    //海图图层显示参数
    function GetCSBOOL() {
        var strCSBOOL = '1'+'0'+'0'+'0'+'0'+'0'+'0';
        strCSBOOL += $("#deep").attr("alt")=="close" ? '0':'1';
        strCSBOOL =strCSBOOL+'0'+'0'+'0'+'0'+'0'+'0'+'0';
        strCSBOOL += $("#lights").attr("alt")=="close" ? '0':'1';

        //将二进制转换成十六进制
        strCSBOOL= parseInt(strCSBOOL, 2).toString(16);
        //添加
        // alert("CSBOOL:"+strCSBOOL);
        return strCSBOOL;
    }
    function GetCSVALUE(){
        var CSVALUE ="10,5,20,10,1,";
        CSVALUE += $("#DisplayCategory").val();
        CSVALUE += ",1,100000000,100000000,100000000,1";
        // alert("CSVALUE:"+CSVALUE);
        return CSVALUE;
    }
    //灯塔
    $('#lights').click(function () {
        if ($(this).attr("alt")=="close"){
            $(this).attr("src","/common/plugins/map/images/open.png")
            $(this).attr("alt","open")
        }
        else {
            $(this).attr("src","/common/plugins/map/images/close.png");
            $(this).attr("alt","close")
        }
        refreshChart();
    });
    //深度信息
    $('#deep').click(function () {
        if ($(this).attr("alt")=="close"){
            $(this).attr("src","/common/plugins/map/images/open.png")
            $(this).attr("alt","open")
        }
        else {
            $(this).attr("src","/common/plugins/map/images/close.png");
            $(this).attr("alt","close")
        }
        refreshChart();
    });
    //显示模式
    $('#DisplayCategory').change(function () {
        refreshChart();
        //小地图
        var mini_map1 = L.tileLayer.wms("http://192.168.18.212:8080/wms?", {
            layers: 'ENC', //必须是ENC
            format: 'image/png'  //只能是png图片，猜测为服务器端为png类型
        });
        mini_map1.setParams({
            'LAYERS': 'ENC',
            'CSBOOL': GetCSBOOL(),
            'CSVALUE': GetCSVALUE(),
            'CRS': 'EPSG:3395',
            'TRANSPARENT': 'false'
        });
        miniMap.changeLayer(mini_map1);
    });
    //鹰眼图
    var c2_glass = L.tileLayer.wms("http://192.168.18.212:8080/wms?", {
        layers: 'ENC', //必须是ENC
        format: 'image/png'  //只能是png图片，猜测为服务器端为png类型
    });
    c2_glass.setParams({
        'LAYERS': 'ENC',
        'CSBOOL': GetCSBOOL(),
        'CSVALUE': GetCSVALUE(),
        'CRS': 'EPSG:3395',
        'TRANSPARENT': 'false'
    });
    var magnifyingGlass = L.magnifyingGlass({
        zoomOffset: 3,
        layers: [c2_glass]
    });
    $('#eyeid').click(function () {
        if ($(this).attr("alt")=="close"){
            $(this).attr("src","/common/plugins/map/images/open.png");
            $(this).attr("alt","open");
            if(map.hasLayer(magnifyingGlass)) {
                return;
            }
            map.addLayer(magnifyingGlass);
        }
        else {
            $(this).attr("src","/common/plugins/map/images/close.png");
            $(this).attr("alt","close");
            map.removeLayer(magnifyingGlass);
        }

    });
    ////////////////////////////////绘制轨迹开始////////////////////////////////////////////////////////////////////
    //执法船
    function getDate(mydate,time) {
        //var t = new Date().getTime() - time;
        var t = mydate - time;
        var d = new Date(t);

        var theMonth = d.getMonth() + 1;
        var theDate = d.getDate();
        var theHours = d.getHours();
        var theMinutes = d.getMinutes();
        var theSeconds = d.getSeconds();

        //if (theMonth<10)
        //{
        //    theMonth = '0' + theMonth;
        //}
        //if (theDate < 10) {
        //    theDate = '0' + theDate;
        //}
        //if (theHours < 10) {
        //    theHours = '0' + theHours;
        //}
        //if (theMinutes < 10) {
        //    theMinutes = '0' + theMinutes;
        //}
        //if (theSeconds < 10) {
        //    theSeconds = '0' + theSeconds;
        //}
        var date = d.getFullYear() + '-' + theMonth + '-' + theDate + ' ' + theHours + ':' + theMinutes + ':' + theSeconds;
        return date;
    }
    //绘制执法船的轨迹点以及两点间连线
    function PointJSON(jsonArr) {
        let points = [];
        //遍历json数组
        for(let i=0;i<jsonArr.length;i++)
        {
            let jsonObj = jsonArr[i];
            let point = [jsonObj.lon,jsonObj.lat];
            // alert(jsonObj.lon);
            points.push(point);
            if(i!=(jsonArr.length-1))//最后一个点
            {
                let circle = L.circle([jsonObj.lat,jsonObj.lon],{radius:3,color: "#2B79E6",fillColor:"#FFFFFF", fillOpacity: 1});
                circle.addTo(pointsGroup);
                // bind event on marker
                circle.on('click', function(e) {
                    console.log(e);
                    alert(jsonObj.lat);
                })
            }

        }
        let Line = {
            "type": "FeatureCollection",
            "features": [
                {
                    "type": "Feature",
                    "geometry": {
                        "type": "LineString",
                        "coordinates": points
                    }
                }
            ]
        };

        let lineGeo = L.geoJson(Line, {
            style: {
                weight: 1.5,
                color:"#2B79E6",
                opacity:0.6
            }
        });
        lineGeo.addTo(pointsGroup);
    }
    //绘制执法船最新位置点
    //obj 点的位置信息
    function LastPointJSON(obj) {
        //执法船最新位置图标
        var LastPointIcon = new L.icon({
            iconUrl: 'common/plugins/map/images/lawship.png',
            iconSize: [38, 38],
            iconAnchor: [19, 19],
            popupAnchor: [0, -10]
        });
        //绘制执法船的轨迹点
        var point = L.marker([obj.lat, obj.lon], {
            icon: LastPointIcon,
            // rotationAngle: obj.heading,
        });
        var date = getDate(obj.timeStamp,0);
        //标牌信息
        // point.bindPopup("执法船编号：" + "0" + "<br />" + "经度： " + obj.lon + "<br />" + "纬度： " + obj.lat + "<br />" + "航向： " + obj.heading + "<br />" + "航速：" + obj.speed + "<br />" + "时间：" + date);
        return point;

    }
    //绘制指向线
    //大圆计算公式
    function speeddisplay(obj) {

        //设置线的属性
        var lineStyle = {
            "color": "#000000",
            "weight": 1.5,
            "opacity": 0.65
        };

        //如果当前航速为0时，不绘制
        if (obj.speed == 0) {
            return null;
        }

        var distance = 1852 * obj.speed / 60.0;//换算成米每分钟;//航程
        var course = obj.heading * 0.017453292519943295769236907684886;//角度转弧度
        var lon = obj.lon;//经度
        var lat = obj.lat;//纬度

        var obj1 = w84ge_sa2ll(lat, lon, distance, course);

        var lon1 = obj1[1];//经度
        var lat1 = obj1[0];//纬度

        //添加线
        var myLines = [{
            "type": "LineString",
            "coordinates": [[obj.lon, obj.lat], [lon1, lat1]]
        }];
        let line = L.geoJSON(myLines, { style: lineStyle });
        let HeadingIcon = new L.icon({
            iconUrl: 'common/plugins/map/images/shipheading.png',
            iconSize: [39, 57],
            iconAnchor: [19.5, 28.5],
            popupAnchor: [0, 0]
        });
        //绘制执法船的轨迹点
        let Headingpoint = L.marker([obj.lat, obj.lon], {
            icon: HeadingIcon,
            rotationAngle: obj.heading,
        });
        let result = new Array(2);
        result[0] = line;
        result[1] = Headingpoint;
        return result;
    }
    //绘制执法船轨迹
    function getPoint() {
        // let startTime = "2019-09-28";
        // let ajax1 = new $ax(Feng.ctxPath + "/path/setfishShipTrackdata");
        // ajax1.set("startTime", startTime);
        // let data1 = ajax1.start();
        let ajax = new $ax(Feng.ctxPath + "/map/fishShipTrack");
        let data = ajax.start();
        // alert(result1.content);
        if(data)
        {
            //清空之前绘制的点
            pointsGroup.clearLayers();
            // linesGroup.clearLayers();
            // 将String转为JSON
            let jsonArr = JSON.parse(data.content);
            PointJSON(jsonArr);
            let heading = speeddisplay(jsonArr[jsonArr.length-1]);
            heading[0].addTo(pointsGroup);
            heading[1].addTo(pointsGroup);
            LastPointJSON(jsonArr[jsonArr.length-1]).addTo(pointsGroup);
            map.addLayer(pointsGroup);
        }
    }
    //定时刷新执法船位置信息

    var i = window.setInterval(getPoint, 5000);//5秒刷新一次
    var state = true;
    L.easyButton({
        states:[
            {
                icon: '<span class="star">轨迹</span>',
                onClick: function(){
                    if(state == false)
                    {
                        //清空之前绘制的点
                        pointsGroup.clearLayers();
                        getPoint();
                        i = window.setInterval(getPoint, 5000);//5秒刷新一次
                    }
                    else
                    {
                        //清空之前绘制的点
                        pointsGroup.clearLayers();
                        clearInterval(i);//取消定时刷新
                    }
                    state = !state;
                }
            }
        ]
    }).addTo(map);
    ////////////////////////////////绘制轨迹结束////////////////////////////////////////////////////////////////
    //禁渔区
    function getForbiddenFishPoint() {
        var AreaId = "1";//约定禁渔区编号为1
        //初始化轨迹的详情数据
        var ajax1 = new $ax(Feng.ctxPath + "/map/fishForbiddenArea");
        ajax1.set("AreaId", AreaId);
        var data = ajax1.start();
        if(data)
        {
            let result=data.content;
            //清空之前绘制的点
            ForbiddenFishLine.clearLayers();
            var ForbiddenFishPoint = [];
            for (var i = 0; i < result.length; i++) {
                var point = [result[i].lon,result[i].lat];
                ForbiddenFishPoint.push(point);
            }
            var flightsEW = {
            "type": "FeatureCollection",
            "features": [
                {
                    "type": "Feature",
                    "properties": {
                        "name": "机轮拖网渔业禁渔区线"
                    },
                    "geometry": {
                        "type": "LineString",
                        "coordinates": ForbiddenFishPoint
                    }
                }
            ]
        };

        L.geoJson(flightsEW, {
                onEachFeature: function (feature, layer) {
                    layer.setText(feature.properties.name, {center: true,offset: -5});
                },
                style: {
                    weight: 2,
                    color:"#666666",
                }
            }).addTo(ForbiddenFishLine);
        map.addLayer(ForbiddenFishLine);
        }
    }

    $('#forbiddenfishId').click(function () {
        if ($(this).attr("alt")=="close"){
            $(this).attr("src","/common/plugins/map/images/open.png");
            $(this).attr("alt","open");
            getForbiddenFishPoint();
            // miniMap.addLayer(ForbiddenFishLine);
        }
        else {
            $(this).attr("src","/common/plugins/map/images/close.png");
            $(this).attr("alt","close");
            //清空之前绘制的点
            // miniMap.removeLayer(ForbiddenFishLine);
            ForbiddenFishLine.clearLayers();

        }
    });
    //渔区
    function getFishPoint() {

        //初始化轨迹的详情数据
        var ajax = new $ax(Feng.ctxPath + "/map/fishArea");
        var data = ajax.start();
        if(data)
        {
            // var fontsize = map.getZoom()*3;
            // alert(fontsize);
            let result=data.content;
            //清空之前绘制的点
            FishLine.clearLayers();
            var FishArea = [];
            var FishAreaName = [];
            for (var i = 0; i < result.length; i++) {
                var FishPolygon = {
                    "type": "Feature",
                    "geometry": {
                        "type": "Polygon",
                        "coordinates": [[
                            [
                                result[i].point1Lon,
                                result[i].point1Lat
                            ],
                            [
                                result[i].point2Lon,
                                result[i].point2Lat
                            ],
                            [
                                result[i].point3Lon,
                                result[i].point3Lat
                            ],
                            [
                                result[i].point4Lon,
                                result[i].point4Lat
                            ],
                            [
                                result[i].point1Lon,
                                result[i].point1Lat
                            ]
                        ]]
                    }
                };
                var lat = (parseFloat(result[i].point1Lat)+parseFloat(result[i].point4Lat))/2;

                var FishPolygonN = {
                    "type": "Feature",
                    "properties": {
                        "name": result[i].name
                    },
                    "geometry": {
                        "type": "LineString",
                        "coordinates": [
                            [
                                result[i].point2Lon,
                                result[i].point2Lat
                            ],
                            [
                                result[i].point4Lon,
                                result[i].point4Lat
                            ]
                        ]
                    }
                };
                FishArea.push(FishPolygon);
                FishAreaName.push(FishPolygonN);
            }
            var FishAreaPolygon = {
                "type": "FeatureCollection",
                "features": FishArea
            };
            var FishAreaPolygonN = {
                "type": "FeatureCollection",
                "features": FishAreaName
            };
            L.geoJson(FishAreaPolygon, {
                style: {
                    fillOpacity: 0,
                    weight: 1,
                    color: '#D74D56',
                    Opacity: 0.3,
                }
            }).addTo(FishLine);
            L.geoJson(FishAreaPolygonN, {
                onEachFeature: function (feature, layer) {
                    layer.setText(feature.properties.name, {center: true,offset: 5,orientation:232});
                },
                style: {
                    weight: 1,
                    color: 'transport'
                }
            }).addTo(FishLine);
            map.addLayer(FishLine);
        }
    }

    $('#fishId').click(function () {
        if ($(this).attr("alt")=="close"){
            $(this).attr("src","/common/plugins/map/images/open.png");
            $(this).attr("alt","open");
            getFishPoint();
        }
        else {
            $(this).attr("src","/common/plugins/map/images/close.png");
            $(this).attr("alt","close");
            //清空之前绘制的点
            FishLine.clearLayers();
        }
    });
});

