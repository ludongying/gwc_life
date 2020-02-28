package com.seven.gwc.modular.sailor.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 证书信息服务类
 *
 * @author : LDY
 * @date : 2020-02-28
 */

public interface CertificateService extends IService<CertificateEntity> {

    /**
     * 证书信息查询列表
     *
     * @param certificateName 名称
     * @return List<证书信息服务对象>
     */
    List<CertificateEntity> selectCertificate(String certificateName);

    /**
     * 证书信息新建
     *
     * @param certificate 实体对象
     * @param user 当前用户
     */
    void addCertificate(CertificateEntity certificate, ShiroUser user);

    /**
     * 证书信息删除
     *
     * @param certificateId 唯一标识
     * @param user 当前用户
     */
    void deleteCertificate(String certificateId, ShiroUser user);

    /**
     * 证书信息编辑
     *
     * @param certificate 实体对象
     * @param user 当前用户
     */
    void editCertificate(CertificateEntity certificate, ShiroUser user);

}
