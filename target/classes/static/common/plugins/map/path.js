layui.define(['jquery', 'layer', 'admin', 'table', 'navi'], function (exports) {
    var $ = layui.$;
    var layer = layui.layer;
    var admin = layui.admin;
    var table = layui.table;
    var navi = layui.navi;

    var path = {
        getDate: function (time) {
            var t = new Date().getTime() - time;
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
        },

        //绘制指向线
        //大圆计算公式
        speeddisplay: function (obj) {
        //设置线的属性
        var lineStyle = {
            "color": "#000000",
            "weight": 1,
            "opacity": 0.65
        };
        //如果当前航速为0时，不绘制
        if (obj.speed == 0) {
            return null;
        }
        var distance = 1852 * obj.speed / 60.0;//换算成米每分钟;//航程
        var course = obj.course * 0.017453292519943295769236907684886;//角度转弧度
        var lon = obj.lon;//经度
        var lat = obj.lat;//纬度
        var obj1 = navi.w84ge_sa2ll(lat, lon, distance, course);
        var lon1 = obj1[1];//经度
        var lat1 = obj1[0];//纬度
        //添加线
        var myLines = [{
            "type": "LineString",
            "coordinates": [[obj.lon, obj.lat], [lon1, lat1]]
        }];
        var line = L.geoJSON(myLines, { style: lineStyle });
        return line;
        },

        //绘制执法船的轨迹点以及两点间连线
        //obj 执法船点的位置信息
        //next 临近下一时刻的点的位置信息
        PointJSON: function (obj, next) {
        //添加执法船轨迹点图片
        var pointIcon = new L.icon({
            iconUrl: '${ctxPath}/common/plugins/map/images/point.png',
            iconSize: [4, 4],
            iconAnchor: [2, 2],
            popupAnchor: [0, -10]
        });
        //执法船两点间连线的属性
        var myStyle = {
            "color": "#00ff00",
            "weight": 1,
            "opacity": 0.65
        };
        //绘制执法船的轨迹点

        var point = L.marker([obj.lat, obj.lon], {icon: pointIcon});
        //标牌信息
        point.bindPopup("执法船编号：" + obj.shipId + "<br />" + "经度： " + obj.lon + "<br />" + "纬度： " + obj.lat + "<br />" + "航向： " + obj.course + "<br />" + "航速：" + obj.speed + "<br />" + "时间：" + obj.createTime);
        //points.push(point);

        //添加线
        var myLines = [{
            "type": "LineString",
            "coordinates": [[obj.lon, obj.lat], [next.lon, next.lat]]
        }];
        var p = L.geoJSON(myLines, { style: myStyle });

        //points.push(p);
        var result = new Array(2);
        result[0] = point;
        result[1] = p;
        return result;
    },

        //绘制执法船最新位置点
        //obj 点的位置信息
        LastPointJSON: function(obj) {
            //执法船最新位置图标
            var LastPointIcon = new L.icon({
                iconUrl: 'images/执法船.png',
                iconSize: [10, 24],
                iconAnchor: [5, 24],
                popupAnchor: [0, -10]
            });
        //绘制执法船的轨迹点
        var point = L.marker([obj.lat, obj.lon], {
            icon: LastPointIcon,
            rotationAngle: obj.course,
        });
        //标牌信息
        point.bindPopup("执法船编号：" + obj.shipId + "<br />" + "经度： " + obj.lon + "<br />" + "纬度： " + obj.lat + "<br />" + "航向： " + obj.course + "<br />" + "航速：" + obj.speed + "<br />" + "时间：" + obj.createTime);
        return point;

        },

        //绘制被执法船的轨迹点以及两点间连线
        //obj 轨迹点经纬度信息
        //next 临近的下一个轨迹点经纬度信息
        //shipId 被执法船的编号
        ReturnPointJSON: function(obj, next) {
        //添加被执法船轨迹点图片
        var ReturnPointIcon = new L.icon({
            iconUrl: 'images/ReturnPoint.png',
            iconSize: [4, 4],
            iconAnchor: [2, 2],
            popupAnchor: [0, -10]
        });
        //被执法船两点间连线属性设置
        var returnlineStyle = {
            "color": "#ff0000",
            "weight": 1,
            "opacity": 0.65
        };
        //绘制被执法船的轨迹点
        var point = L.marker([obj.lat, obj.lon], { icon: ReturnPointIcon });
        //标牌信息
        point.bindPopup("被执法船编号：" + obj.shipId + "<br />" + "经度： " + obj.lon + "<br />" + "纬度： " + obj.lat + "<br />" + "航向： " + obj.course + "<br />" + "航速：" + obj.speed + "<br />" + "时间：" + obj.createTime);

        //添加线
        var myLines = [{
            "type": "LineString",
            "coordinates": [[obj.lon, obj.lat], [next.lon, next.lat]]
        }];
        var p = L.geoJSON(myLines, { style: returnlineStyle });

        var result = new Array(2);
        result[0] = point;
        result[1] = p;
        return result;
        },

        //绘制被执法船最新位置图标
        //obj 点的位置信息
        LastReturnPointJSON: function(obj) {
        //被执法船最新位置图标属性
        var LastReturnPointIcon = new L.icon({
            iconUrl: 'images/LastReturnPoint.png',
            iconSize: [10, 24],
            iconAnchor: [5, 24],
            popupAnchor: [0, -10]
        });
        //绘制图表
        var point = L.marker([obj.lat, obj.lon], {
            icon: LastReturnPointIcon,
            rotationAngle: obj.course

        });
        point.bindPopup("被执法船编号：" + obj.shipId + "<br />" + "经度： " + obj.lon + "<br />" + "纬度： " + obj.lat + "<br />" + "航向： " + obj.course + "<br />" + "航速：" + obj.speed + "<br />" + "时间：" + obj.createTime);

        return point;
    },

        pathJSON: function(point, returnpoint) {
        //被执法船两点间连线属性设置
        var pathlineStyle = {
            "color": "#ff0000",
            "weight": 2,
            "opacity": 0.65
        };
        //添加线
        var myLines = [{
            "type": "LineString",
            "coordinates": [[point._lon, point._lat], [returnpoint._lon, returnpoint._lat]]
        }];
        var p = L.geoJSON(myLines, { style: pathlineStyle });

        var result = navi.w84ge_ll2sa(point._lat, point._lon, returnpoint._lat, returnpoint._lon);
        var distance = result[0];
        p.bindPopup("距离：" + distance+"米");
        return p;
    }

};

    exports('path', path);

});
