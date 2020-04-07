package com.seven.gwc.modular.sailor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 证书信息Mapper 接口
 *
 * @author : LDY
 * @date : 2020-02-28
 */
public interface CertificateMapper extends BaseMapper<CertificateEntity> {

    /**
     * 根据证书信息查询证书列表
     *
     * @param certificateName 证书名称或编码
     * @return
     */
    List<CertificateEntity> CertificateEntityListAll(@Param("certificateName") String certificateName);

    /**
     * 根据证书信息查询证书列表
     *
     * @param certificate 证书实体
     * @return
     */
    List<CertificateEntity> CertificateEntityList(@Param("certificate") CertificateEntity certificate, @Param("ids") String ids, @Param("total") Integer total, @Param("size") Integer size);

    List<CertificateEntity> getListSize(@Param("certificate") CertificateEntity certificate, @Param("ids") String ids);

    /**
     * 根据证书ids字符串查询执法证实体
     *
     * @param ids 证书ids
     * @return
     */
    List<CertificateEntity> CertificateLawEntityList(@Param("ids") String ids);
}
