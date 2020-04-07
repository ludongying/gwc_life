package com.seven.gwc.modular.timingTask;

import com.seven.gwc.modular.equipment_info.service.EquipService;
import com.seven.gwc.modular.sailor.service.CertificateService;
import com.seven.gwc.modular.ship_info.service.CertificateShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.ParseException;

@Controller
@EnableScheduling
public class TimingTaskController {

    @Autowired
    private CertificateService certificateService; //船员证书
    @Autowired
    private CertificateShipService certificateShipService; //船舶证书
    @Autowired
    private EquipService equipService;//设备维保

    @Scheduled(cron = "0 */1 * * * ?")
    public void timingTask() throws ParseException {
       certificateService.warn();
       certificateShipService.warn();
       equipService.warn();

    }

    //每隔5秒执行一次：*/5 * * * * ?
    //每隔1分钟执行一次：0 */1 * * * ?
    //每天23点执行一次：0 0 23 * * ?
    //每天凌晨1点执行一次：0 0 1 * * ?
    //每月1号凌晨1点执行一次：0 0 1 1 * ?
    //每月最后一天23点执行一次：0 0 23 L * ?
    //每周星期天凌晨1点实行一次：0 0 1 ? * L
    //在26分、29分、33分执行一次：0 26,29,33 * * * ?
    //每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
    //每隔5分钟执行一次：0 0/5 * * * ?
}
