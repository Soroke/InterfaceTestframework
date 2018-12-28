package net.faxuan.interfaceTest.util;

import com.alibaba.fastjson.JSON;
import net.faxuan.interfaceTest.exception.CheckException;
import net.faxuan.objectInfo.caseObject.Case;
import net.faxuan.objectInfo.excel.CaseCheck;
import net.faxuan.objectInfo.excel.DBCheck;
import net.faxuan.objectInfo.excel.InterfaceInfo;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by song on 2018/12/25.
 */
public class ExcelUtil {
    private static String SHEET1="接口";
    private static String SHEET2="用例";
    private static String SHEET3="数据库检查";
    private static String SHEET4="系统设置";
    private String host = "";


    public static void main(String[] ages) {
        new ExcelUtil().readExcelGetCase("D:\\workspace\\接口测试用例模板.xlsx");
    }

    /**
     * 读取Excel中用例信息，
     * 创建host和MySQL连接方式配置文件
     * @param excelPath  Excel文件路径
     * @return 返回用例的实例
     */
    public List<Case> readExcelGetCase(String excelPath) {
        List<InterfaceInfo> interfaceInfos = new ArrayList<>();
        List<CaseCheck> caseChecks = new ArrayList<>();
        List<DBCheck> dbChecks = new ArrayList<>();
        FileInputStream excelFileInputStream = null;
        XSSFWorkbook workbook = null;
        try {
            excelFileInputStream = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(excelFileInputStream);
            excelFileInputStream.close();
            XSSFSheet sheet1 = workbook.getSheet(SHEET1);
            XSSFSheet sheet2 = workbook.getSheet(SHEET2);
            XSSFSheet sheet3 = workbook.getSheet(SHEET3);
            XSSFSheet sheet4 = workbook.getSheet(SHEET4);

            /**
             * 循环获取sheet1的所有对象信息
             */
//System.out.println("接口sheet中记录数量：" + sheet1.getLastRowNum());
            for (int rowIndex = 2; rowIndex <= sheet1.getLastRowNum(); rowIndex++) {
                InterfaceInfo interfaceInfo = new InterfaceInfo();
                XSSFRow row = sheet1.getRow(rowIndex);
                XSSFCell interfaceIDCell = row.getCell(0); // 接口ID
                XSSFCell interfaceTypeCell = row.getCell(1); // 接口类型
                XSSFCell interfaceURLCell = row.getCell(2); // 接口url
                XSSFCell interfaceParamsCell = row.getCell(3); // 接口参数

                /**
                 * 检查获取数据是否为空
                 */
//                boolean interfaceIDIsNull = false;
//                boolean interfaceTypeIsNull = false;
//                boolean interfaceURLIsNull = false;
//                boolean interfaceParamsIsNull = false;
                interfaceInfo.setId( Long.valueOf(getCellStringValue(interfaceIDCell)));
                interfaceInfo.setRequestType(getCellStringValue(interfaceTypeCell));
                interfaceInfo.setUrl(getCellStringValue(interfaceURLCell));
                interfaceInfo.setParams(getCellStringValue(interfaceParamsCell));
                interfaceInfos.add(interfaceInfo);
            }

            /**
             * 循环获取sheet2的所有对象信息
             */
//System.out.println("用例sheet中记录数量：" + sheet2.getLastRowNum());
            for (int rowIndex = 2; rowIndex <= sheet2.getLastRowNum(); rowIndex++) {
                CaseCheck caseCheck = new CaseCheck();
                XSSFRow row = sheet2.getRow(rowIndex);
                XSSFCell caseIDCell = row.getCell(0); // 用例ID
                XSSFCell caseNameCell = row.getCell(1); // 用例名称
                XSSFCell contactIdCell = row.getCell(2); // 关联接口ID
                XSSFCell preconditionCell = row.getCell(3); // 前置条件
                XSSFCell caseParamsCell = row.getCell(4); // 参数值
                XSSFCell statusCodeCell = row.getCell(5); // 状态码
                XSSFCell responseCheckCell = row.getCell(6); // 返回校验

                /**
                 * 检查获取数据是否为空
                 */
//                boolean interfaceIDIsNull = false;
//                boolean interfaceTypeIsNull = false;
//                boolean interfaceURLIsNull = false;
//                boolean interfaceParamsIsNull = false;

                caseCheck.setId(Long.valueOf(getCellStringValue(caseIDCell)));
                caseCheck.setCaseName(getCellStringValue(caseNameCell));
                caseCheck.setContactId(Long.valueOf(getCellStringValue(contactIdCell)));
//                caseCheck.setPrecondition(preconditionCell.getStringCellValue());
                caseCheck.setParams(getCellStringValue(caseParamsCell));
                caseCheck.setStatusCode(getCellStringValue(statusCodeCell));
                caseCheck.setResponseCheck(getCellStringValue(responseCheckCell));
                caseChecks.add(caseCheck);

            }


            /**
             * 循环获取sheet3的所有对象信息
             */
            for (int rowIndex = 2; rowIndex <= sheet3.getLastRowNum(); rowIndex++) {
                DBCheck dbCheck = new DBCheck();
                XSSFRow row = sheet3.getRow(rowIndex);
                XSSFCell contactIdCell = row.getCell(0); // 管理用例ID
                XSSFCell sqlCell = row.getCell(1); // SQL语句
                XSSFCell checkPointCell = row.getCell(2); // SQL返回值校验

                /**
                 * 检查获取数据是否为空
                 */
//                boolean checkPointIsNull = false;
//                boolean interfaceTypeIsNull = false;
//                boolean interfaceURLIsNull = false;
//                boolean interfaceParamsIsNull = false;

                dbCheck.setContactId(getCellStringValue(contactIdCell));
                dbCheck.setSql(getCellStringValue(sqlCell));
                String checkP = getCellStringValue(checkPointCell);
                dbCheck.setCheckPoint(checkP);
                if (checkP.equals("") || checkP.length() <=0) {
                    dbCheck.setResultIsNull(true);
                }else {
                    dbCheck.setResultIsNull(false);
                }
                dbChecks.add(dbCheck);

            }

            /**
             * 获取系统配置并创建配置文件
             */
            XSSFRow row = sheet4.getRow(2);
            Map<Object,Object> systemProper = new HashMap<Object,Object>();
            host = getCellStringValue(row.getCell(0));
            systemProper.put("systemhost",host);
            systemProper.put("mysqlhost",getCellStringValue(row.getCell(1)));
            systemProper.put("mysqlport",getCellStringValue(row.getCell(2)));
            systemProper.put("mysqluser",getCellStringValue(row.getCell(3)));
            systemProper.put("mysqlpassword",getCellStringValue(row.getCell(4)));
            new FileUtil().createPropertiesFile("system.properties",systemProper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        /**
         * 获取所有用例的组合信息
         */
//System.out.println("用例数量：" + caseChecks.size());
//        List<Case> cases = getCaseInfo(interfaceInfos,caseChecks,dbChecks);
//        for (Case caseInfo:cases) {
//            System.out.println(JSON.toJSONString(caseInfo));
//        }
        return getCaseInfo(interfaceInfos,caseChecks,dbChecks);
    }

    /**
     * 获取所有用例的信息
     * @param interfaceInfos
     * @param caseChecks
     * @param dbChecks
     * @return
     */
    private List<Case> getCaseInfo(List<InterfaceInfo> interfaceInfos,List<CaseCheck> caseChecks,List<DBCheck> dbChecks) {
        List<Case> caseList = new ArrayList<>();
        for (CaseCheck caseCheck:caseChecks) {
            Case caseInfo = new Case();
            caseInfo.setId(caseCheck.getId());
            caseInfo.setName(caseCheck.getCaseName());
            caseInfo.setResponseCheck(caseCheck.getResponseCheck());
            caseInfo.setStatusCode(caseCheck.getStatusCode());
            for (InterfaceInfo interfaceInfo:interfaceInfos) {
                if (interfaceInfo.getId() == caseCheck.getContactId()) {
                    Map<Object,Object> params = new HashMap<>();
                    caseInfo.setUrl(host + interfaceInfo.getUrl());
                    caseInfo.setRequestType(interfaceInfo.getRequestType());
                    String [] paramNames = interfaceInfo.getParams().split("&");
                    String[] paramValues = caseCheck.getParams().split("&");
                    if (paramNames.length == paramValues.length) {
                        for (int i=0;i<paramNames.length;i++) {
                            params.put(paramNames[i],paramValues[i]);
                        }
                    } else {
                        throw new CheckException("用例ID：" +caseCheck.getId() + "中的参数配置数量和其关联接口的参数不一致");
                    }
                    caseInfo.setParams(params);
                    break;
                }
            }

            List<DBCheck> dbCheckList = new ArrayList<>();
            for (DBCheck dbCheck:dbChecks) {
                if (dbCheck.getContactId().contains(caseCheck.getId().toString())) {
                    dbCheckList.add(dbCheck);
                }
            }
            caseInfo.setDbChecks(dbCheckList);
            caseList.add(caseInfo);
        }

        return caseList;
    }


    /**
     * 将获取的所有类型值都转换为String
     * 如果当前字段为空 返回空字符串
     * @param cell
     * @return
     */
    private String getCellStringValue(XSSFCell cell) {
        String cellValue = "";
        try {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    if(cellValue.trim().equals("")||cellValue.trim().length()<=0)
                        cellValue="";
                    break;
                case NUMERIC:
                    cellValue = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
                    break;
                case FORMULA:
                    cell.setCellType(CellType.NUMERIC);
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case BLANK:
                    break;
                case BOOLEAN:
                    break;
                case ERROR:
                    break;
                default: break;
            }
        } catch (NullPointerException npe) {
            return "";
        }
        return cellValue;
    }


}
