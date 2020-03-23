package com.seven.gwc.core.state;

import lombok.Getter;

/**
 * description : 错误码
 *
 * @author : GD
 * @date : 2018/5/2 15:15
 */
@Getter
public enum ErrorEnum {

    /********************************✨    系统(100 * 999)   ✨*************************************/
    ERROR_ILLEGAL_PARAMS(100, "参数非法"),
    CANT_OPERATION_ADMIN(101, "不能操作系统管理员"),
    UPLOAD_ERROR(102, "上传错误"),
    UPLOAD_LARGE_ERROR(103, "文件过大上传失败"),
    TOKEN_EXPIRED(104, "token过期"),
    TOKEN_ERROR(105, "token验证失败"),
    NO_PRIVILEGES(106, "没有该操作权限"),
    ERROR_SYSTEM(999, "系统异常"),


    /********************************✨    公用(1000 * 1999)   ✨*************************************/
    ERROR_ONLY_NAME(1000, "名称已存在"),
    ERROR_ONLY_STATUS(1001, "已存在有效"),
    ERROR_ONLY_CODE(1002, "编码已存在"),
    THE_DATA_USED(1003, "该数据已被使用"),
    NO_POWER_TO_RESOURCES(1004, "无权访问该资源"),
    ERROR_UPDATE(1005, "修改失败"),


    /********************************✨    用户(2000 * 2999)   ✨*************************************/
    ERROR_USER_FAILURE(2000, "账号密码错误"),
    ERROR_USER_EXIST(2001, "用户已存在"),
    ERROR_VERIFY_CODE(2002, "验证码错误"),
    ERROR_USER_UNLOGIN(2003, "用户未登录"),
    ERROR_VERIFY_CODE_BLANK(2004, "验证码不能为空"),
    OLD_PWD_NOT_RIGHT(2005, "原密码不正确"),
    MENU_PCODE_COINCIDENCE(2006, "菜单编号和副编号不能一致"),
    EXISTED_THE_MENU(2007, "菜单编号重复，不能添加"),
    ERROR_USER_NOT_EXIST(2008, "用户不存在"),
    ERROR_USER_BLANK(2009, "账号密码不能为空"),


    /********************************✨    角色(3000 * 3999)   ✨*************************************/
    EXISTED_THE_ROLE_NAME(3000, "角色名称已存在"),
    EXISTED_SUBORDINATE_DATA(3001, "有下级数据，不可删除"),


    /********************************✨    部门(4000 * 4999)   ✨*************************************/
    EXISTED_THE_DEPT_NAME(4000, "同一公司下的部门名称已存在"),
    THE_DEPART_EXISTED_USER(4001, "该部门下存在人员，不可删除"),
    THE_DEPART_EXISTED_DEPART(4002, "该部门含有下级，不可删除"),
    NOT_SUBORDINATES_AS_SUPERIORS(4003, "不能选自己或自己的下级为上级"),

    /********************************✨    执法船和船员(5000 * 5999)   ✨*************************************/
    ERROR_ONLY_LAWSHIP_CODE(5000,"执法船编码已经存在"),
    ERROR_ONLY_PERSON_ID(5001,"人员身份证号已存在"),
    ERROR_ONLY_CERTIFICATE_ID(5002,"证书编码已存在"),
    ERROR_ONLY_EQUIP_CODE(5003,"设备出厂编码已存在");


    private Integer code;
    private String message;

    ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
