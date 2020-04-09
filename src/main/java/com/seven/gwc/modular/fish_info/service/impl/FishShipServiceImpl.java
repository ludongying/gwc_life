package com.seven.gwc.modular.fish_info.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.CalculationDateUtil;
import com.seven.gwc.core.util.ExcelUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.fish_info.dao.FishShipMapper;
import com.seven.gwc.modular.fish_info.entity.FishShipEntity;
import com.seven.gwc.modular.fish_info.service.FishShipService;
import com.seven.gwc.modular.fish_info.vo.ExportFishShipVO;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import com.seven.gwc.modular.system.service.DictService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * description : 渔船信息服务实现类
 *
 * @author : SHQ
 * @date : 2020-03-02
 */
@Service
public class FishShipServiceImpl extends ServiceImpl<FishShipMapper, FishShipEntity> implements FishShipService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FishShipMapper fishShipMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictService dictService;
    @Autowired
    private FileManager fileManager;
    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String uploadPathFile;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<FishShipEntity> selectFishShip(FishShipEntity fishShipEntity, Integer total, Integer size){

        List<FishShipEntity> list = fishShipMapper.getFishShipList(fishShipEntity, total, size);

        for (FishShipEntity fishShip : list) {
            if (ToolUtil.isNotEmpty(fishShip.getShipType())) {
                DictEntity shipTypeDict = dictMapper.selectById(fishShip.getShipType());
                fishShip.setShipTypeName(shipTypeDict.getName());
            }
            if (ToolUtil.isNotEmpty(fishShip.getAreaType())) {
                DictEntity areaTypeDict = dictMapper.selectById(fishShip.getAreaType());
                fishShip.setAreaTypeName(areaTypeDict.getName());
            }
            if (ToolUtil.isNotEmpty(fishShip.getWatersType())) {
                DictEntity watersTypeDict = dictMapper.selectById(fishShip.getWatersType());
                fishShip.setWatersTypeName(watersTypeDict.getName());
            }
            if (ToolUtil.isNotEmpty(fishShip.getPractice())) {
                DictEntity practiceDict = dictMapper.selectById(fishShip.getPractice());
                fishShip.setPracticeName(practiceDict.getName());
            }
            DictEntity workTypeDict = dictMapper.selectById(fishShip.getWorkType());
            fishShip.setWorkTypeName(workTypeDict.getName());
        }
        return list;
    }

    @Override
    public List<FishShipEntity> getListSize(FishShipEntity fishShipEntity) {
        return fishShipMapper.getListSize(fishShipEntity);
    }

    @Override
    public void addFishShip(FishShipEntity fishShip, ShiroUser user) {
        fishShip.setCreateTime(new Date());
        fishShip.setCreateUser(user.getId());
        fishShipMapper.insert(fishShip);
    }

    @Override
    public void deleteFishShip(String fishShipId, ShiroUser user) {
        fishShipMapper.deleteById(fishShipId);
    }

    @Override
    public void editFishShip(FishShipEntity fishShip, ShiroUser user) {
        fishShip.setUpdateTime(new Date());
        fishShip.setUpdateUser(user.getId());
        fishShipMapper.updateById(fishShip);
    }

    @Override
    public FishShipEntity detailFishShip(String fishShipId) throws Exception {
        FishShipEntity fishShipEntity = fishShipMapper.selectById(fishShipId);
        if (ToolUtil.isNotEmpty(fishShipEntity.getShipType())) {
            DictEntity shipTypeDict = dictMapper.selectById(fishShipEntity.getShipType());
            fishShipEntity.setShipTypeName(shipTypeDict.getName());
        }
        if (ToolUtil.isNotEmpty(fishShipEntity.getAreaType())) {
            DictEntity areaTypeDict = dictMapper.selectById(fishShipEntity.getAreaType());
            fishShipEntity.setAreaTypeName(areaTypeDict.getName());
        }
        if (ToolUtil.isNotEmpty(fishShipEntity.getWatersType())) {
            DictEntity watersTypeDict = dictMapper.selectById(fishShipEntity.getWatersType());
            fishShipEntity.setWatersTypeName(watersTypeDict.getName());
        }
        if (ToolUtil.isNotEmpty(fishShipEntity.getPractice())) {
            DictEntity practiceDict = dictMapper.selectById(fishShipEntity.getPractice());
            fishShipEntity.setPracticeName(practiceDict.getName());
        }
        if (ToolUtil.isNotEmpty(fishShipEntity.getProductDate())) {
            Integer shipAge = CalculationDateUtil.getAge(fishShipEntity.getProductDate());
            fishShipEntity.setShipAge(shipAge);
        }

        DictEntity workTypeDict = dictMapper.selectById(fishShipEntity.getWorkType());
        fishShipEntity.setWorkTypeName(workTypeDict.getName());
        fishShipEntity.setFilePath(fileManager.listFile(fishShipEntity.getFileName()));
        return fishShipEntity;
    }

    @Override
    public List<ExportFishShipVO> getExportData(List<FishShipEntity> shipEntityList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<ExportFishShipVO> exportDataList = new ArrayList<>();
        for (FishShipEntity fishShipEntity : shipEntityList) {
            ExportFishShipVO exportFishShipVO = new ExportFishShipVO();
            exportFishShipVO.setCode(fishShipEntity.getCode());
            exportFishShipVO.setName(fishShipEntity.getName());
            exportFishShipVO.setRegistry(fishShipEntity.getRegistry());
            if (ToolUtil.isNotEmpty(fishShipEntity.getWatersType())) {
                DictEntity watersTypeDict = dictMapper.selectById(fishShipEntity.getWatersType());
                exportFishShipVO.setWatersTypeName(watersTypeDict.getName());
            }
            if (ToolUtil.isNotEmpty(fishShipEntity.getAreaType())) {
                DictEntity areaTypeDict = dictMapper.selectById(fishShipEntity.getAreaType());
                exportFishShipVO.setAreaTypeName(areaTypeDict.getName());
            }
            if (ToolUtil.isNotEmpty(fishShipEntity.getShipType())) {
                DictEntity shipTypeDict = dictMapper.selectById(fishShipEntity.getShipType());
                exportFishShipVO.setShipTypeName(shipTypeDict.getName());
            }
            exportFishShipVO.setHullMaterial(fishShipEntity.getHullMaterial());
            exportFishShipVO.setShipLong(fishShipEntity.getShipLong());
            exportFishShipVO.setShipWide(fishShipEntity.getShipWide());
            exportFishShipVO.setShipDeep(fishShipEntity.getShipDeep());
            exportFishShipVO.setTonnage(fishShipEntity.getTonnage());
            exportFishShipVO.setTotalPower(fishShipEntity.getTotalPower());
            DictEntity workTypeDict = dictMapper.selectById(fishShipEntity.getWorkType());
            exportFishShipVO.setWorkTypeName(workTypeDict.getName());
            if (ToolUtil.isNotEmpty(fishShipEntity.getPractice())) {
                DictEntity practiceDict = dictMapper.selectById(fishShipEntity.getPractice());
                exportFishShipVO.setPracticeName(practiceDict.getName());
            }
            if (ToolUtil.isNotEmpty(fishShipEntity.getProductDate())) {
                exportFishShipVO.setProductDate(sdf.format(fishShipEntity.getProductDate()));
                Integer shipAge = CalculationDateUtil.getAge(fishShipEntity.getProductDate());
                exportFishShipVO.setShipAge(shipAge);
            }
            exportFishShipVO.setShipOwner(fishShipEntity.getShipOwner());
            exportFishShipVO.setIdentity(fishShipEntity.getIdentity());
            exportFishShipVO.setAddress(fishShipEntity.getAddress());
            exportFishShipVO.setApprovedArea(fishShipEntity.getApprovedArea());
            exportFishShipVO.setAuthorizedMember(fishShipEntity.getAuthorizedMember());
            exportDataList.add(exportFishShipVO);
        }
        return exportDataList;
    }

    @Override
    public BaseResult importExcelFile(MultipartFile file, HttpServletResponse resp) throws IOException {
        BaseResult baseResult = new BaseResult();
        String message = "";
        message = ExcelUtil.saveFile(uploadPathFile, file);
        if (!message.equals("文件为空")) {
            Workbook workbook = ExcelUtil.importJudgeEdition(message);
            message = this.readExcelValue(workbook, message, resp);
        }
        baseResult.setMessage(message);
        return baseResult;
    }

    public String readExcelValue(Workbook wb, String fileFullName, HttpServletResponse resp) {
        String sheetNumber = "";
        long date = System.currentTimeMillis();
        int addNum = 0;
        Sheet sheet = wb.getSheetAt(0);
        if (wb.getNumberOfSheets() > 1) {
            sheetNumber = "只有第一个sheet数据导入成功。";
        }
        String rowMessage = "操作成功。" + sheetNumber;

        List<Integer> errorList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();     //行数
        Row rowHead = sheet.getRow(0);  //获取表头一行
        if (rowHead != null) {
            int lastCellNum = rowHead.getLastCellNum();   //列数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取表头
            String heads = "";
            for (int i = 0; i < lastCellNum; i++) {
                heads += rowHead.getCell(i) + ",";
            }
            heads = heads.substring(0, heads.length() - 1);
            String[] head = heads.split(",");
            List<String> listHead = Arrays.asList(head);

            if (listHead.get(0).equals("渔船编码") && listHead.get(1).equals("船名") && listHead.get(15).equals("船主名称")) {
                for (int i = 1; i <= lastRowNum; i++) {
                    try {
                        FishShipEntity fishShipEntity = new FishShipEntity();
                        fishShipEntity.setCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(0)));
                        fishShipEntity.setName(ToolUtil.getCellValue(sheet.getRow(i).getCell(1)));
                        fishShipEntity.setRegistry(ToolUtil.getCellValue(sheet.getRow(i).getCell(2)));
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(3)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(3)), "WATERS_TYPE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setWatersType(dictEntity.getId());
                            }
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(4)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(4)), "AREA_TYPE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setAreaType(dictEntity.getId());
                            }
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(5)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(5)), "SHIP_TYPE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setShipType(dictEntity.getId());
                            }
                        }
                        fishShipEntity.setHullMaterial(ToolUtil.getCellValue(sheet.getRow(i).getCell(6)));
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(7)))) {
                            fishShipEntity.setShipLong(Double.parseDouble(ToolUtil.getCellValue(sheet.getRow(i).getCell(7))));
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(8)))) {
                            fishShipEntity.setShipWide(Double.parseDouble(ToolUtil.getCellValue(sheet.getRow(i).getCell(8))));
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(9)))) {
                            fishShipEntity.setShipDeep(Double.parseDouble(ToolUtil.getCellValue(sheet.getRow(i).getCell(9))));
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(10)))) {
                            fishShipEntity.setTotalPower(Double.parseDouble(ToolUtil.getCellValue(sheet.getRow(i).getCell(10))));
                        }
                        fishShipEntity.setTonnage(ToolUtil.getCellValue(sheet.getRow(i).getCell(11)));
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(12)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(12)), "WORK_TYPE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setWorkType(dictEntity.getId());
                            }
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(13)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(13)), "PRACTICE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setPractice(dictEntity.getId());
                            }
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(14)))) {
                            fishShipEntity.setProductDate(sdf.parse(ToolUtil.getCellValue(sheet.getRow(i).getCell(14))));
                        }
                        fishShipEntity.setShipOwner(ToolUtil.getCellValue(sheet.getRow(i).getCell(15)));
                        fishShipEntity.setIdentity(ToolUtil.getCellValue(sheet.getRow(i).getCell(16)));
                        fishShipEntity.setAddress(ToolUtil.getCellValue(sheet.getRow(i).getCell(17)));
                        fishShipEntity.setApprovedArea(ToolUtil.getCellValue(sheet.getRow(i).getCell(18)));
                        fishShipEntity.setAuthorizedMember(ToolUtil.getCellValue(sheet.getRow(i).getCell(19)));
                        fishShipMapper.insert(fishShipEntity);
                        addNum++;
                    } catch (Exception e) {
                        errorList.add(i);
                        rowMessage = "有错误数据！";
                    }
                }
                if (errorList.size() > 0) {
                    ExcelUtil.errorExcel(uploadPathFile, head, errorList, sheet, lastCellNum, date, resp);
                }
                if (addNum > 0 || errorList.size() > 0) {
                    rowMessage = date + "插入" + addNum + "条，失败" + errorList.size() + "条。" + sheetNumber;
                }
            } else {
                rowMessage = "不是有效模板";
            }
        } else {
            rowMessage = "不是有效模板";
        }
        File folder = new File(fileFullName);
        if (folder != null) {
            folder.delete();
        }
        return rowMessage;
    }

}
