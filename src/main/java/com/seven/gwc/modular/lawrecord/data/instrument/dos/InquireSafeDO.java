package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.InquireBase;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zzl
 * @Date: 2020-03-20
 * @description : 模板数据 笔录
 */
@Data
@NoArgsConstructor
public class InquireSafeDO extends InquireDO{

    /**
     *船名号是否清晰 （预留）
     */
    protected String Clear;
    /**
     * 船名号涂写情况
     */
    protected  String A002;
    /**
     * 船籍港涂写情况
     */
    protected  String A003;
    /**
     * 船名牌悬挂情况
     */
    protected  String A004;

    /**
     * 配备救生圈数量
     */
    protected String A008;
    /**
     * 配备灭火器数量
     */
    protected String A009;
    /**
     * 配备救生衣数量
     */
    protected String A010;
    /**
     * 配备救生筏数量
     */
    protected String A011;
    /**
     * 职务船员数量
     */
    protected String A013;
    /**
     * 普通船员数量
     */
    protected String A014;

    /**
     * 整改通知书第一条 （预留）
     */
    protected String A015;
    /**
     * 整改通知书第二条（预留）
     */
    protected String A016;
    /**
     * 处理意见书损失表述（预留）
     */
    protected String A017;

    public InquireSafeDO(InquireBase inquireBase) {
        super(inquireBase);
    }
}
