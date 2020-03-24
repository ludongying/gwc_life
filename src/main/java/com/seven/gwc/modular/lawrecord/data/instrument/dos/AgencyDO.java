package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.data.instrument.InstrumentUtil;
import com.seven.gwc.modular.lawrecord.dto.AgencyDTO;
import com.seven.gwc.modular.lawrecord.enums.LawCaseSourceEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description :办案机关模板数据
 */
@Data
@Accessors(chain = true)
public class AgencyDO extends BaseDO {

    /**
     * 执法单位简称
     */
    private String Short;
    /**
     * 执法单位名称
     */
    private String Enforcement_Agency;
    /**
     * 执法船船号
     */
    private String Official_Boat;
    /**
     * 执法人员一
     */
    private String Officer1;
    /**
     * 执法人员一执法证号
     */
    private String OfficerNumber1;
    /**
     * 执法人员二
     */
    private String Officer2;
    /**
     * 执法人员二执法证号
     */
    private String OfficerNumber2;
    /**
     * 处罚机关地址
     */
    private String Official_Address;
    /**
     *处罚机关联系人
     */
    private String Official_Contact;
    /**
     *联系人电话
     */
    private String Official_Contact_Phone;
    /**
     *指定银行
     */
    private String Bank;
    /**
     *复议机关
     */
    private String Reconsideration;
    /**
     *诉讼机关
     */
    private String Litigation;
    /**
     *案件年份
     */
    private Integer YEAR;
    /**
     *案件编号
     */
    private Integer Number;
    /**
     *案件来源
     */
    private String From;
    /**
     *案发地点
     */
    private String Location;
    /**
     *案发时间年
     */
    private Integer Fa_year;
    /**
     *案发时间月
     */
    private Integer Fa_Month;
    /**
     *案发时间日
     */
    private Integer Fa_Day;
    /**
     *案发时间时
     */
    private Integer Fa_Hour;
    /**
     *案发时间分
     */
    private Integer Fa_Minute;
    /**
     *勘验时间年
     */
    private Integer Check_year;
    /**
     *勘验时间月
     */
    private Integer Check_Month;
    /**
     *勘验时间日
     */
    private Integer Check_Day;
    /**
     *勘验时间时
     */
    private Integer Check_Hour1;
    /**
     *勘验时间分
     */
    private Integer Check_Minute1;
    /**
     *至勘验时间时
     */
    private Integer Check_Hour2;
    /**
     *至勘验时间分
     */
    private Integer Check_Minute2;
    /**
     *询问时间年
     */
    private Integer Ask_year;
    /**
     *询问时间月
     */
    private Integer Ask_Month;
    /**
     *询问时间日
     */
    private Integer Ask_Day;
    /**
     *询问时间时
     */
    private Integer Ask_Hour1;
    /**
     *询问时间分
     */
    private Integer Ask_Minute1;
    /**
     *至询问时间时
     */
    private Integer Ask_Hour2;
    /**
     *至询问时间分
     */
    private Integer Ask_Minute2;
    /**
     *立案审批电话时间年
     */
    private Integer Phone_year;
    /**
     *立案审批电话时间月
     */
    private Integer Phone_Month;
    /**
     *立案审批电话时间日
     */
    private Integer Phone_Day;
    /**
     *立案审批电话时间时
     */
    private Integer Phone_Hour;
    /**
     *立案审批电话时间分
     */
    private Integer Phone_Minute;
    /**
     *受案时间年
     */
    private Integer Shou_year;
    /**
     *受案时间月
     */
    private Integer Shou_Month;
    /**
     *受案时间日
     */
    private Integer Shou_Day;
    /**
     *处理意见书电话时间年
     */
    private Integer Idea_year;
    /**
     *处理意见书电话时间月
     */
    private Integer Idea_Month;
    /**
     *处理意见书电话时间日
     */
    private Integer Idea_Day;
    /**
     *处理意见书电话时间时
     */
    private Integer Idea_Hour;
    /**
     *处理意见书电话时间分
     */
    private Integer Idea_Minute;
    /**
     * 处罚审批电话时间年
     */
    private Integer PunishAsk_year;
     /**
     * 处罚审批电话时间月
     */
    private Integer PunishAsk_Month;
     /**
     * 处罚审批电话时间日
     */
    private Integer PunishAsk_Day;
    /**
     * 处罚审批电话时间时
     */
    private Integer PunishAsk_Hour;
     /**
     * 处罚审批电话时间分
     */
    private Integer PunishAsk_Minute;

    /**
     *处罚决定书时间年
     */
    private Integer Punish_year;
    /**
     *处罚决定书时间月
     */
    private Integer Punish_Month;
    /**
     *处罚决定书时间日
     */
    private Integer Punish_Day;
    /**
     *结案时间年
     */
    private Integer Closed_year;
    /**
     *结案时间月
     */
    private Integer Closed_Month;
    /**
     *结案时间日
     */
    private Integer Closed_Day;
    /**
     *保管年限年
     */
    private Integer Keep_year;
    /**
     *保管年限月
     */
    private Integer Keep_Month;
    /**
     *保管年限日
     */
    private Integer Keep_Day;

    public AgencyDO(AgencyDTO dto){

        LawCaseSourceEnum lawCaseSourceEnum = LawCaseSourceEnum.findByCode(dto.getLawCaseSource());
        LocalDateTime fa = InstrumentUtil.toLocalDateTime(dto.getLawCaseDate());
        LocalDateTime checkStart = InstrumentUtil.toLocalDateTime(dto.getProspectStartDate());
        LocalDateTime checkEnd = InstrumentUtil.toLocalDateTime(dto.getProspectEndDate());
        LocalDateTime askStart = InstrumentUtil.toLocalDateTime(dto.getInquireStartDate());
        LocalDateTime askEnd = InstrumentUtil.toLocalDateTime(dto.getInquireEndDate());
        LocalDateTime phone = InstrumentUtil.toLocalDateTime(dto.getTelApplyDate());
        LocalDateTime accept = InstrumentUtil.toLocalDateTime(dto.getAcceptDate());
        LocalDateTime idea = InstrumentUtil.toLocalDateTime(dto.getDealDate());
        LocalDateTime punishAsk = InstrumentUtil.toLocalDateTime(dto.getPunishDate());
        LocalDateTime punish = InstrumentUtil.toLocalDateTime(dto.getDecisionDate());
        LocalDateTime closed = InstrumentUtil.toLocalDateTime(dto.getFinishDate());
        LocalDateTime keep = InstrumentUtil.toLocalDateTime(dto.getSaveDate());

        this.setShort(dto.getShortName()).setEnforcement_Agency(dto.getEnforcementAgency())
                .setOfficial_Boat(dto.getLawShipCode()).setOfficer1(dto.getPersonName1())
                .setOfficerNumber1(dto.getCredentialCode1()).setOfficer2(dto.getPersonName2())
                .setOfficerNumber2(dto.getCredentialCode2()).setOfficial_Address(dto.getAgencyAddr())
                .setOfficial_Contact(dto.getAgencyPerson()).setOfficial_Contact_Phone(dto.getAgencyTel())
                .setBank(dto.getBank()).setReconsideration(dto.getReviewAgency()).setLitigation(dto.getLawsuitAgency())
                .setYEAR(dto.getLawCaseFineCode()).setNumber(dto.getLawCaseCode())
                .setFrom(Objects.nonNull(lawCaseSourceEnum) ? lawCaseSourceEnum.getMessage() : "")
                .setLocation(dto.getLawCaseAddr());

        if (Objects.nonNull(fa)) {
            this.setFa_year(fa.getYear()).setFa_Month(fa.getMonthValue()).setFa_Day(fa.getDayOfMonth())
                    .setFa_Hour(fa.getHour()).setFa_Minute(fa.getMinute());
        }
        if (Objects.nonNull(checkStart)) {
            this.setCheck_year(checkStart.getYear()).setCheck_Month(checkStart.getMonthValue())
                    .setCheck_Day(checkStart.getDayOfMonth()).setCheck_Hour1(checkStart.getHour())
                    .setCheck_Minute1(checkStart.getMinute()).setCheck_Hour2(checkEnd.getHour())
                    .setCheck_Minute2(checkEnd.getMinute());
        }
        if (Objects.nonNull(askStart)) {
            this.setAsk_year(askStart.getYear()).setAsk_Month(askStart.getMonthValue())
                    .setAsk_Day(askStart.getDayOfMonth()).setAsk_Hour1(askStart.getHour())
                    .setAsk_Minute1(askStart.getMinute()).setAsk_Hour2(askEnd.getHour())
                    .setAsk_Minute2(askEnd.getMinute());
        }
        if (Objects.nonNull(phone)) {
            this.setPhone_year(phone.getYear()).setPhone_Month(phone.getMonthValue())
                    .setPhone_Day(phone.getDayOfMonth()).setPhone_Hour(phone.getHour()).setPhone_Minute(phone.getMinute());
        }

        if (Objects.nonNull(accept)) {
            this.setShou_year(accept.getYear()).setShou_Month(accept.getMonthValue())
                    .setShou_Day(accept.getDayOfMonth());
        }

        if (Objects.nonNull(idea)) {
            this.setIdea_year(idea.getYear()).setIdea_Month(idea.getMonthValue()).setIdea_Day(idea.getDayOfMonth())
                    .setIdea_Hour(idea.getHour()).setIdea_Minute(idea.getMinute());
        }

        if (Objects.nonNull(punishAsk)) {
            this.setPunishAsk_year(punishAsk.getYear()).setPunishAsk_Month(punishAsk.getMonthValue())
                    .setPunishAsk_Day(punishAsk.getDayOfMonth()).setPunishAsk_Hour(punishAsk.getHour())
                    .setPunishAsk_Minute(punishAsk.getMinute());
        }

        if (Objects.nonNull(punish)) {
            this.setPunish_year(punish.getYear()).setPunish_Month(punish.getMonthValue()).setPunish_Day(punish.getDayOfMonth());
        }

        if (Objects.nonNull(closed)) {
            this.setClosed_year(closed.getYear()).setClosed_Month(closed.getMonthValue()).setClosed_Day(closed.getDayOfMonth());
        }

        if (Objects.nonNull(keep)) {
            this.setKeep_year(keep.getYear()).setKeep_Month(keep.getMonthValue()).setKeep_Day(keep.getDayOfMonth());
        }
    }




}
