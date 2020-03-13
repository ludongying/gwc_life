package com.seven.gwc.modular.ship_info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.ship_info.entity.CertificateShipEntity;

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
     * @param  ids 所属的所有证书编码
     * @param personId 船员表id
     * @return List<证书信息服务对象>
     */
    List<CertificateShipEntity> selectCertificate(String certificateName, String ids, String personId);

    /**
     * 证书信息新建
     *
     * @param certificate 实体对象
     * @param user 当前用户
     * @param personId 船员表id
     */
    boolean addCertificate(CertificateShipEntity certificate, ShiroUser user, String personId);

    /**
     * 证书信息删除
     *
     * @param certificateId 唯一标识
     * @param user 当前用户
     * @param personId 船员表id
     */
    void deleteCertificate(String certificateId, ShiroUser user, String personId);

    /**
     * 证书信息编辑
     *
     * @param certificate 实体对象
     * @param user 当前用户
     */
    void editCertificate(CertificateShipEntity certificate, ShiroUser user);

    /**
     * 获证书详细信息（图片url转换）
     * @param id 表编码
     * @return
     */
    CertificateShipEntity getCertificateById(String id);

}
