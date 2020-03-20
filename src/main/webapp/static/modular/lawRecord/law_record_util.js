
/**
 * @author zzl
 * @Description 获取省 市
 * 2020-02-28
 */

var loc=(function(){
    var addr={};
    /**
     * 初始化省市
     */
    function initState() {
        var states = window.localStorage.getItem("law_record_states");
        if(!states) {
            var xmlhttp;
            if (window.XMLHttpRequest) {
                // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp = new XMLHttpRequest();
            } else {
                // IE6, IE5 浏览器执行代码
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var data = xmlhttp.responseText;
                    console.log(">>>>缓存城市信息");
                    window.localStorage.setItem("law_record_states", data);
                }
            }
            xmlhttp.open("GET", "/lawRecord/states", false);
            xmlhttp.send();
        }
    }

    /**
     * 获取所有省市
     * @returns json Obj
     */
    function getStates() {
        // window.localStorage.clear();
        initState();
        var str=window.localStorage.getItem("law_record_states");
        return JSON.parse(str);
    }

    /**
     * 根据省code 获取市列表
     * @param stateCode
     * @returns {*}
     */
    function getCities(stateCode){
        var states = getStates();
        for(var i=0;i<states.length;i++){
            if(states[i].code==stateCode){
                return states[i].cityDataList;
            }
        }
    }

    /**
     * 根据 省/市对象生成 <option></option>
     * @param dataList
     */
    function generateOptions(dataList,stateCode){
        var str="";
        for (var i=0;i<dataList.length;i++){
            if(stateCode && stateCode===dataList[i].code){
                str+="<option selected='selected' value='"+dataList[i].code+"'>"+dataList[i].name+"</option>";
            }else{
                str+="<option value='"+dataList[i].code+"'>"+dataList[i].name+"</option>";
            }
        }
        return str;
    }

    /**
     * 获取state <option></option>
     * @returns {string|*}
     */
    addr.getStateOptions=function(stateCode){
        return  generateOptions(getStates(),stateCode);
    }
    /**
     * 获取city <option></option>
     * @returns {string|*}
     */
    addr.getCityOptions=function(stateCode,cityCode) {
        return  generateOptions(getCities(stateCode),cityCode);
    }
    return addr;
})()


/**
 * 初始化from 表单中地址，并监听变化
 * @param form
 */
function loadAddr($,form,stateCode,cityCode,region){
    //初始化数据
    $(".addr_state").html(loc.getStateOptions(stateCode));
    var code=$(".addr_state").val();
    $(".addr_city").html(loc.getCityOptions(code,cityCode));
    form.render();
    //增加监听
    form.on('select(addr_state)', function(data){
        $(data.elem).parent().parent().find(".addr_city").html(loc.getCityOptions(data.value));
        if(!region){region=""}
        $(data.elem).parent().parent().find(".addr_region").val(region);
        //清空数据3
        form.render();
    });
}


/**
 *获取地址
 * @param $
 * @param id
 * @returns {city: *, state: *, addr: *}
 */
function getLocAddr($,id){
    var dds = $("#"+id).find(".layui-this");
    var state={
        name:$(dds[0]).text(),
        code:$(dds[0]).attr("lay-value")
    }
    var city={
        name:$(dds[1]).text(),
        code:$(dds[1]).attr("lay-value")
    }
    var region=$($("#"+id).find("input")[2]).val();
    return{
        state:state,
        city:city,
        region:region
    }
}
/***************************************/

var listen=(function listen(){
    var lis={};
    function getContent($){
        var inputs=$(".layui-form input");
        var content="";
        if(inputs.length>0){
            for (var i=0;i<inputs.length;i++){
                // if($(inputs[i]).attr("type")==="hide"){
                //     continue;
                // }
                if($(inputs[i]).hasClass("layui-upload-file")){
                    continue;
                }
                if($(inputs[i]).attr("type")==="radio"){
                    if($(inputs[i]).next().hasClass("layui-form-radioed")){
                        content+=$(inputs[i]).val();
                    }
                    continue;
                }
                if($(inputs[i]).attr("type")==="checkbox"){
                    if($(inputs[i]).next().hasClass("layui-form-checked")){
                        content+=$(inputs[i]).val();
                    }
                    continue;
                }
                content+=$(inputs[i]).val();
            }
        }
        var textarea=$(".layui-form textarea");
        if(textarea.length>0){
            for (var i=0;i<textarea.length;i++){
                content+=$(textarea[i]).val();
            }
        }

        return content;
    }

    function getMd5($){
       return md5(getContent($));
    }

    lis.start=function ($,key) {
        window.sessionStorage.setItem(key,getMd5($));
    }

    lis.status=function ($,key){
        var md = window.sessionStorage.getItem(key);
        if(md){
            if(md!==getMd5($)){
                return true;
            }
        }
        return false;
    }

    lis.clear=function(key){
        window.sessionStorage.removeItem(key);
    }

    return lis;
})();

/**
 * 开启监听
 * @param $
 * @param key
 */
function startListen($,key){
   listen.start($,key);
}

/**
 * 获取监听状态
 * @param $
 * @param key
 * @returns {boolean}
 */
function listenStatus($,key){
   return listen.status($,key);
}

/**
 * 清空监听
 * @param key
 */
function clearListen(key){
    listen.clear(key);
}
/*************************************************/
/**
 * 提交表单
 * @param $ax
 * @param admin
 * @param url
 * @param f
 */
var submitFrom=function(param){
    var ajax = new param.lay.$ax(Feng.ctxPath + param.url, function (data) {
        if (data.success) {
            Feng.success("提交成功!"+data.message);
            if(param.next){
                window.location.href=param.next+"&id="+data.content;
            }else{
                return true;
            }
        } else {
            Feng.error(data.message);
        }
    }, function (data) {
        Feng.error("提交失败!" + data.message)
    });
    ajax.set(param.data.field);
    ajax.start();
    return false;

}

/**
 * 下一步操作提示
 * @param $
 * @param layer
 * @param key
 */
function nextStep(param){
    if(param.lay.verify){
        return;
    }
    var status = listenStatus(param.lay.$,param.lay.key);
    if(status||param.submit){
        //询问框
        var index=param.lay.layer.confirm('内容已经修改,是否保存', {
            btn: ['确认','取消'] //按钮
        }, function(){
            clearListen(param.lay.key);
            submitFrom(param);
            param.lay.layer.close(index);
        }, function(){
            // startListen(param.lay.$,param.key)
            if(param.next){
                clearListen(param.lay.key);
                window.location.href=param.next+"&id="+param.lay.$("#id").val();
            }else{
                //startListen(param.lay.$,param.lay.key)
            }
            // layer.msg('没有提交', {icon: 1});
        });
    }else{
        if(param.next){
            clearListen(param.lay.key);
            window.location.href=param.next+"&id="+param.lay.$("#id").val();
        }
    }
}


/***************************************/

/**
 * 时间计算 毫秒计算
 * @param number
 */
Date.prototype.addMilliseconds=function (number) {
    this.setTime(this.getTime()+number);
}
/**
 * 月份计算
 * @param number
 */
Date.prototype.addMonths=function (number) {
    let month_total=this.getMonth()+1+number;
    let year_add = month_total/12;
    if(year_add>0){
        this.setFullYear(this.getFullYear()+year_add);
    }
    let month=month_total%12-1;
    this.setMonth(month);
}

Date.prototype.addYears=function(number){
    let fullYear = this.getFullYear();
    this.setFullYear(fullYear+number);
}

function getFloat(number){
   return Math.round(number*100)/100;
}


/**
 * 时间格式化
 * @param pattern
 * @returns {string}
 * @from csdn
 * 对Date的扩展，将 Date 转化为指定格式的String
 *月(M)、日(d)、小时(H)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 *年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 *例子：
 *(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 *(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/************************************************************/
function msg_tip($,msg){
    layer.msg(msg, {icon: 7, shift: 6},function(){});
}

/************************************************************/
function operate_table(param) {
    Feng.confirm(param.title, function () {
        var ajax = new param.$ax(Feng.ctxPath + param.url, function (data) {
            if (data.success) {
                Feng.success("操作成功!");
                param.table.reload(param.tableId);
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("操作失败!" + data.message + "!");
        });
        ajax.set("id",param.data.id);
        ajax.start();
    });
};
