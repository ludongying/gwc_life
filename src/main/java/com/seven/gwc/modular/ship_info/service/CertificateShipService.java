package com.seven.gwc.modular.ship_info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.ship_info.entity.CertificateShipEntity;

import java.text.ParseException;
import java.util.List;

/**
 * description : 证书信息服务类
 *
 * @author : LDY
 * @date : 2020-02-28
 */

public interface CertificateShipService extends IService<CertificateShipEntity> {

    /**
     * 证书信息查询列表
     *
     * @param certificateName 名称;
     * @param shipId 船员表id
     * @return List<证书信息服务对象>
     */
    List<CertificateShipEntity> selectCertificate(String certificateName, String shipId);

    /**
     * 证书信息新建
     *
     * @param certificate 实体对象
     * @param user 当前用户
     * @param shipId 船员表id
     */
    boolean addCertificate(CertificateShipEntity certificate, ShiroUser user, String shipId) throws ParseException;

    /**
     * 证书信息删除
     *
     * @param certificateId 唯一标识
     * @param user 当前用户
     * @param shipId 船员表id
     */
    void deleteCertificate(String certificateId, ShiroUser user, String shipId);

    /**
     * 证书信息编辑
     *
     * @param certificate 实体对象
     * @param user 当前用户
     */
    boolean editCertificate(CertificateShipEntity certificate, ShiroUser user) throws ParseException;

    /**
     * 证书到期判断，并更新证书状态
     * @return
     */
    void warn() throws ParseException;

}
