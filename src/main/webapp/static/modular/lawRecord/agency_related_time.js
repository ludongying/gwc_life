/**
 * 关联时间初始化以及设置
 * @param $
 * @param laydate
 */
var law_record_relate_time_separate="~";
function relateTime($,laydate) {

    laydate.render({
        elem: '#lawCaseDate'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm'
    });
    //勘验时间
    laydate.render({
        elem: '#prospectDate'
        ,type: 'datetime'
        ,range: law_record_relate_time_separate
        ,format: 'yyyy-MM-dd HH:mm'
    });
    //询问时间
    laydate.render({
        elem: '#inquireDate'
        ,type: 'datetime'
        ,range: law_record_relate_time_separate
        ,format: 'yyyy-MM-dd HH:mm'

    });
    //电话请示时间
    laydate.render({
        elem: '#telApplyDate'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm'
    });
    //受案时间
    laydate.render({
        elem: '#acceptDate'
        ,type: 'date'
        ,format: 'yyyy-MM-dd'
    });
    //处理意见书时间
    laydate.render({
        elem: '#dealDate'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm'
    });
    //处罚审批时间
    laydate.render({
        elem: '#punishDate'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm'
    });
    //处理决定时间
    laydate.render({
        elem: '#decisionDate'
        ,type: 'date'
        ,format: 'yyyy-MM-dd'
    });
    //结案时间
    laydate.render({
        elem: '#finishDate'
        ,type: 'date'
        ,format: 'yyyy-MM-dd'
    });

    /**
     * 点击关联时间
     */
    $("#relatedTime").click(function () {
        // var prospect = $("#prospectDate").val();
        var inquire_end;
        // if(prospect){
        //     var prospect_ = prospect.split(law_record_relate_time_separate);
        //     var prospect_end = prospect_[1];
        //     //获取询问时间
        //     inquire_end = setInquireDate(prospect_end);
        // }else{
            var inquire = $("#inquireDate").val();
            if(inquire){
                var inquire_=inquire.split(law_record_relate_time_separate);
                inquire_end=new Date(inquire_[1]);
                setProspectDate(inquire_[0]);
            }
        // }
        if(inquire_end){
            //获取电话请示时间
            var tel_apply=setTelApplyDate(inquire_end);
            //受案时间
            setAcceptDate(tel_apply)
            //处理意见书时间
            var deal=setDealDate(tel_apply);
            //处罚审批时间
            var punish=setPunishDate(deal);
            //处理决定时间
            var decision=setDecisionDate(punish);
            //结案时间
            setFinishDate(decision);
        }else{
            //提示
            msg_tip($,"请输入案件信息-询问时间")
        }
    });

    /**
     * 设置并获取询问时间
     * @param inquire_start
     */
    function setProspectDate(inquire_start){
        var prospect= $("#prospectDate").val();
        if(prospect){
            var prospect_ = prospect.split(law_record_relate_time_separate);
            return new Date(prospect_[1]);
        }
        var date = new Date(inquire_start);
        date.addMilliseconds(5*60*1000*-1);
        var prospect_end= date.format("yyyy-MM-dd hh:mm");
        date.addMilliseconds(8*60*1000*-1);
        var prospect_start= date.format("yyyy-MM-dd hh:mm");
        var time=prospect_start+" "+law_record_relate_time_separate+" "+prospect_end;
        laydate.render({
            elem: '#prospectDate'
            ,type: 'datetime'
            ,range: law_record_relate_time_separate
            ,format: 'yyyy-MM-dd HH:mm'
            ,value: time
        });
        return date;
    }


    /**
     * 设置并获取询问时间
     * @param prospect_end
     * @returns {Date}
     */
    function setInquireDate(prospect_end) {
        var inquire= $("#inquireDate").val();
        if(inquire){
            var inquire_ = inquire.split(law_record_relate_time_separate);
            return new Date(inquire_[1]);
        }
        var date = new Date(prospect_end);
        date.addMilliseconds(5*60*1000);
        var inquire_start = date.format("yyyy-MM-dd hh:mm");
        date.addMilliseconds(20*60*1000);
        var inquire_end = date.format("yyyy-MM-dd hh:mm");
        var time=inquire_start+" "+law_record_relate_time_separate+" "+inquire_end;
        laydate.render({
            elem: '#inquireDate'
            ,type: 'datetime'
            ,range: law_record_relate_time_separate
            ,format: 'yyyy-MM-dd HH:mm'
            ,value: time
        });
        return date;
    }

    /**
     * 设置并获取电话询问时间
     * @param inquire_end
     * @returns {void|*|Date}
     */
    function setTelApplyDate(inquire_end){
        var telApply=$("#telApplyDate").val();
        if(telApply){
            return new Date(telApply);
        }
        inquire_end.addMilliseconds(10*60*1000);
        var time= inquire_end.format("yyyy-MM-dd hh:mm");
        laydate.render({
            elem: '#telApplyDate'
            ,type: 'datetime'
            ,format: 'yyyy-MM-dd HH:mm'
            ,value: time
        });
        return inquire_end;
    }

    /**
     * 设置并获取受案时间
     * @param tel_apply
     * @returns {Date|*}
     */
    function setAcceptDate(tel_apply){
        var acceptDate=$("#acceptDate").val();
        if(acceptDate){
            return new Date(acceptDate);
        }
        var time= tel_apply.format("yyyy-MM-dd");
        laydate.render({
            elem: '#acceptDate'
            ,type: 'date'
            ,format: 'yyyy-MM-dd'
            ,value: time
        });
        return tel_apply;
    }

    /**
     *设置并获取处理意见书时间
     * @param tel_apply
     * @returns {Date|*}
     */
    function setDealDate(tel_apply){
        var dealDate=$("#dealDate").val();
        if(dealDate){
            return new Date(dealDate);
        }
        tel_apply.addMilliseconds(60*60*1000);
        var time= tel_apply.format("yyyy-MM-dd hh:mm");
        laydate.render({
            elem: '#dealDate'
            ,type: 'datetime'
            ,format: 'yyyy-MM-dd HH:mm'
            ,value: time
        });
        return tel_apply;
    }

    /**
     *设置并获取处罚审批时间
     * @param deal
     * @returns {Date|*}
     */
    function setPunishDate(deal){
        var punishDate=$("#punishDate").val();
        if(punishDate){
            return new Date(punishDate);
        }
        deal.addMilliseconds(60*60*1000);
        var time= deal.format("yyyy-MM-dd hh:mm");
        laydate.render({
            elem: '#punishDate'
            ,type: 'datetime'
            ,format: 'yyyy-MM-dd HH:mm'
            ,value: time
        });
        return deal;
    }

    /**
     * 设置并获取处罚决定时间
     * @param punish
     * @returns {Date|*}
     */
    function setDecisionDate(punish){
        var decisionDate=$("#decisionDate").val();
        if(decisionDate){
            return new Date(decisionDate);
        }
        var time= punish.format("yyyy-MM-dd");
        laydate.render({
            elem: '#decisionDate'
            ,type: 'date'
            ,format: 'yyyy-MM-dd'
            ,value: time
        });
        return punish;
    }

    /**
     * 结案时间
     * @param decision
     * @returns {Date|*}
     */
    function setFinishDate(decision){
        var finishDate=$("#finishDate").val();
        if(finishDate){
            return new Date(finishDate);
        }
        decision.addMonths(6);
        var time= decision.format("yyyy-MM-dd");
        laydate.render({
            elem: '#finishDate'
            ,type: 'date'
            ,format: 'yyyy-MM-dd'
            ,value: time
        });
        return decision;
    }

}

/**
 * 时间段传后台修正
 * @param $
 * @param data
 */
function setProspectInquire($,data) {
    var prospect= $("#prospectDate").val();
    if(prospect){
        var prospect_=prospect.split(law_record_relate_time_separate);
        data.field.prospectStartDate=prospect_[0];
        data.field.prospectEndDate=prospect_[1];
    }
    var inquire= $("#inquireDate").val();
    if(inquire){
        var inquire_=inquire.split(law_record_relate_time_separate);
        data.field.inquireStartDate=inquire_[0];
        data.field.inquireEndDate=inquire_[1];
    }


}
 