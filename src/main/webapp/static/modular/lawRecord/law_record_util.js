
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
function loadAddr($,form,stateCode,cityCode){
    //初始化数据
    $(".addr_state").html(loc.getStateOptions(stateCode));
    var code=$(".addr_state").val();
    $(".addr_city").html(loc.getCityOptions(code,cityCode));
    form.render();
    //增加监听
    form.on('select(addr_state)', function(data){
        $(data.elem).parent().parent().find(".addr_city").html(loc.getCityOptions(data.value));
        $(data.elem).parent().parent().find(".addr_region").val("");
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
            Feng.success("提交成功!");
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
    var month_total=this.getMonth()+1+number;
    var year_add = month_total/12;
    if(year_add>0){
        this.setFullYear(this.getFullYear()+year_add);
    }
    var month=month_total%12-1;
    this.setMonth(month);
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
/**
 * 把数字金额转换成中文大写数字的函数(可处理负值)
 * @param money
 * @returns {string}
 * @from https://www.cnblogs.com/myprogramer/p/11528131.html
 */
function changeNumMoneyToChinese(money) {
    var cnNums = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); //汉字的数字
    var cnIntRadice = new Array("", "拾", "佰", "仟"); //基本单位
    var cnIntUnits = new Array("", "万", "亿", "兆"); //对应整数部分扩展单位
    var cnDecUnits = new Array("角", "分", "毫", "厘"); //对应小数部分单位
    var cnInteger = "整"; //整数金额时后面跟的字符
    var cnIntLast = "元"; //整型完以后的单位
    var maxNum = 999999999999999.9999; //最大处理的数字
    var IntegerNum; //金额整数部分
    var DecimalNum; //金额小数部分
    var ChineseStr = ""; //输出的中文金额字符串
    var parts; //分离金额后用的数组，预定义
    var Symbol="";//正负值标记
    if (money == "") {
        return "";
    }

    money = parseFloat(money);
    if (money >= maxNum) {
        alert('超出最大处理数字');
        return "";
    }
    if (money == 0) {
        ChineseStr = cnNums[0] + cnIntLast + cnInteger;
        return ChineseStr;
    }
    if(money<0)
    {
        money=-money;
        Symbol="负 ";
    }
    money = money.toString(); //转换为字符串
    if (money.indexOf(".") == -1) {
        IntegerNum = money;
        DecimalNum = '';
    } else {
        parts = money.split(".");
        IntegerNum = parts[0];
        DecimalNum = parts[1].substr(0, 4);
    }
    if (parseInt(IntegerNum, 10) > 0) { //获取整型部分转换
        var zeroCount = 0;
        var IntLen = IntegerNum.length;
        for (var i = 0; i < IntLen; i++) {
            var n = IntegerNum.substr(i, 1);
            var p = IntLen - i - 1;
            var q = p / 4;
            var m = p % 4;
            if (n == "0") {
                zeroCount++;
            }
            else {
                if (zeroCount > 0) {
                    ChineseStr += cnNums[0];
                }
                zeroCount = 0; //归零
                ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
            }
            if (m == 0 && zeroCount < 4) {
                ChineseStr += cnIntUnits[q];
            }
        }
        ChineseStr += cnIntLast;
        //整型部分处理完毕
    }
    if (DecimalNum != '') { //小数部分
        var decLen = DecimalNum.length;
        for (var i = 0; i < decLen; i++) {
            var n = DecimalNum.substr(i, 1);
            if (n != '0') {
                ChineseStr += cnNums[Number(n)] + cnDecUnits[i];
            }
        }
    }
    if (ChineseStr == '') {
        ChineseStr += cnNums[0] + cnIntLast + cnInteger;
    } else if (DecimalNum == '') {
        ChineseStr += cnInteger;
    }
    ChineseStr = Symbol +ChineseStr;

    return ChineseStr;
}
