package com.seven.gwc.modular.lawrecord.enums;

import com.seven.gwc.modular.lawrecord.entity.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :文书模板
 */
@Getter
public enum InstrumentEnum {

    INSTRUMENT_01(1,"封面_法人.docx", 1,Arrays.asList(2),Arrays.asList(1),Arrays.asList(1)),
    INSTRUMENT_02(2,"封面_目录.docx", 1,Arrays.asList(2),Arrays.asList(1,2),Arrays.asList(1));

    Integer code;
    /**
     * 模板名称
     */
    String message;
    /**
     *  文书生成方式 1：系统根据录入生成 2需要根据录入生成并且需要手工填写 3 需要手工填写
     */
    Integer generate;

    /**
     * 案件类型 1 生产   2安全
     */
    List<Integer> law;

    /**
     * 决定类型 1 法人  2 公民
     */
    List<Integer> decision;

    /**
     * 数据来源
     *     办案机关：1
     *     询问笔录生产：2
     *     勘验笔录 3
     *     笔录安全 4
     *     决定生产 20
     *     决定安全 21
     *     固定变量 99 //此项目前不可能出现 预留 录入时要录入 为今后扩展用
     */
    List<Integer> source;


    public Integer getBusinessCode(Class<?> clazz){
        if (AgencyEntity.class.equals(clazz)) {
            return 1;
        } else if (InquireEntity.class.equals(clazz)) {
            return 2;
        }else if(InquisitionEntity.class.equals(clazz)){
            return 3;
        }else if(InquireSafeEntity.class.equals(clazz)){
            return 4;
        }else if(DecisionEntity.class.equals(clazz)){
            return 20;
        }else if(DecisionSafeEntity.class.equals(clazz)){
            return 21;
        }
        return 99;
    }

    /**
     * 根据实体修改，修改文书
     * @param clazz
     * @return
     */
    public List<InstrumentEnum> getBusiness(Class<?> clazz){
        List<InstrumentEnum> list=new ArrayList<>();
        for (InstrumentEnum ms : InstrumentEnum.values()) {
                if(ms.getSource().contains(getBusinessCode(clazz))){
                    list.add(ms);
                }
        }
        return list;
    }


    InstrumentEnum(Integer code, String message,Integer generate,List<Integer> law,List<Integer> decision,List<Integer> source) {
        this.code = code;
        this.message = message;
        this.generate=generate;
        this.law=law;
        this.decision=decision;
        this.source=source;
    }

    public static InstrumentEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (InstrumentEnum ms : InstrumentEnum.values()) {
                if (ms.getCode().equals(value)) {
                    return ms;
                }
            }
            return null;
        }
    }

    /**
     * 根据案件类型，和决定类型获取所有模板
     * @param law
     * @param decision
     * @return
     */
    public static List<InstrumentEnum> getList(Integer law,Integer decision){
        List<InstrumentEnum> list=new ArrayList<>();
        for (InstrumentEnum ms : InstrumentEnum.values()) {
             if(ms.getLaw().contains(law) && ms.getDecision().contains(decision)){
                 list.add(ms);
             }
        }
        return list;
    }

    /**
     * 根据案件类型，和决定类型获取需要系统生成的模板
     * @param law
     * @param decision
     * @return
     */
    public static List<InstrumentEnum> getSystem(Integer law,Integer decision){
        List<InstrumentEnum> list=new ArrayList<>();
        for (InstrumentEnum ms : InstrumentEnum.values()) {
            if(ms.getLaw().contains(law) && ms.getDecision().contains(decision)){
                if(ms.getGenerate()<3){
                    list.add(ms);
                }
            }
        }
        return list;
    }

    /**
     * 根据案件类型，和决定类型获取需要手动填写的模板
     * @param law
     * @param decision
     * @return
     */
    public static List<InstrumentEnum> getManually(Integer law,Integer decision){
        List<InstrumentEnum> list=new ArrayList<>();
        for (InstrumentEnum ms : InstrumentEnum.values()) {
            if(ms.getLaw().contains(law) && ms.getDecision().contains(decision)){
                if(ms.getGenerate()>1){
                    list.add(ms);
                }
            }
        }
        return list;
    }


    /**
     * 根据案件类型，和决定类型获取纯手动填写的模板
     * @param law
     * @param decision
     * @return
     */
    public static List<InstrumentEnum> getFile(Integer law,Integer decision){
        List<InstrumentEnum> list=new ArrayList<>();
        for (InstrumentEnum ms : InstrumentEnum.values()) {
            if(ms.getLaw().contains(law) && ms.getDecision().contains(decision)){
                if(ms.getGenerate()==3){
                    list.add(ms);
                }
            }
        }
        return list;
    }


}

