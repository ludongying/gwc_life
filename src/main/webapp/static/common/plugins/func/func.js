/**
 * 高频方法集
 */
layui.define(['jquery', 'layer', 'admin', 'table'], function (exports) {
    var $ = layui.$;
    var layer = layui.layer;
    var admin = layui.admin;
    var table = layui.table;

    var func = {

        /**
         * 获取内部高度，返回数值
         */
        getClientHeight: function () {
            var clientHeight = 0;
            if (document.body.clientHeight && document.documentElement.clientHeight) {
                clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
            } else {
                clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
            }
            return clientHeight;
        },

        /**
         * 获取内部高度，返回字符串
         */
        getClientHeightPx: function () {
            return Feng.getClientHeight() + 'px';
        },

        /**
         * 打开表单的弹框
         */
        open: function (param) {

            //计算高度
            var clientHeight = func.getClientHeight();
            if (param.area == null) {
                if (param.height) {
                    if (clientHeight < param.height) {
                        param.area = ['1000px', clientHeight + "px"];
                    } else {
                        param.area = ['1000px', param.height + "px"];
                    }
                } else {
                    param.area = ['1000px', clientHeight + "px"];
                }
            } else {
                param.area = [param.area[0], param.area[1]];
            }

            param.skin = 'layui-layer-admin';
            param.offset = '35px';
            param.type = 2;

            admin.putTempData('formOk', false);
            param.end = function () {
                layer.closeAll('tips');
                admin.getTempData('formOk') && table.reload(param.tableId);
                console.log(param.tableId)
            };

            param.fixed = false;
            param.resize = false;
            param.shade = .1;
            return top.layui.layer.open(param);
        }
    };

    exports('func', func);
});