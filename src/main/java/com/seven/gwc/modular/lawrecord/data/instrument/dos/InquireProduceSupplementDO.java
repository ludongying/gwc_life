package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.data.instrument.InstrumentUtil;
import com.seven.gwc.modular.lawrecord.dto.InquireSupplementDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-20
 * @description : 模板数据 笔录
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class InquireProduceSupplementDO extends InquireProduceDO{

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

    public InquireProduceSupplementDO(InquireSupplementDTO dto) {
        super(dto);
        LocalDateTime askStart = InstrumentUtil.toLocalDateTime(dto.getInquireStartDate());
        LocalDateTime askEnd = InstrumentUtil.toLocalDateTime(dto.getInquireEndDate());
        if (Objects.nonNull(askStart)) {
            this.setAsk_year(askStart.getYear()).setAsk_Month(askStart.getMonthValue())
                    .setAsk_Day(askStart.getDayOfMonth()).setAsk_Hour1(askStart.getHour())
                    .setAsk_Minute1(askStart.getMinute()).setAsk_Hour2(askEnd.getHour())
                    .setAsk_Minute2(askEnd.getMinute());
        }
        this.Officer1=dto.getPersonName1();
        this.OfficerNumber1=dto.getCredentialCode1();
        this.Officer2=dto.getPersonName2();
        this.OfficerNumber2=dto.getCredentialCode2();
    }



}
