package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.AgencyDTO;
import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.modular.lawrecord.vo.AgencyVO;

/**
 * description : 办案机关服务类
 *
 * @author : ZZL
 * @date : 2020-02-28
 */

public interface AgencyService extends IService<AgencyEntity> {

     /**
      * 更新办案机关
      * @param agencyVO
      * @return
      */
     BaseResult updateAgency(AgencyVO agencyVO);

     /**
      * 获取详情
      * @param id
      * @return
      */
     AgencyDTO detail(String id);


     /**
      * 获取案件编号
      * @param fineCode
      * @return
      */
     Integer getLawCode(String fineCode);



}
