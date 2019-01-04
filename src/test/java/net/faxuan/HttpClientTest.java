package net.faxuan;

import net.faxuan.interfaceTest.core.Response;
import net.faxuan.interfaceTest.exception.CheckException;
import net.faxuan.interfaceTest.util.Check;
import net.faxuan.interfaceTest.util.DatabaseUtil;
import net.faxuan.interfaceTest.util.ExcelUtil;
import net.faxuan.objectInfo.caseObject.Case;
import net.faxuan.objectInfo.excel.DBCheck;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import static net.faxuan.interfaceTest.core.Http.sendRequest;

/**
 * Created by song on 2018/12/25.
 */
public class HttpClientTest extends Init{

    /**
     * log4j打log
     */
    private static Logger log = Logger.getLogger(HttpClientTest.class);

    @Test(dataProvider = "cases",priority = 1)
    public void testInterFace(Case caseInfo) {
        log.info("================================用例ID" + caseInfo.getId() + "_START================================");
        log.info("开始测试用例ID:" + caseInfo.getId() + ",名称："+ caseInfo.getName());
        /**
         * 请求接口，获取返回数据并检验返回结果
         * 存储测试结果和用例信息
         */
        Response response = null;
        try {
            //发送接口请求
            response = sendRequest(caseInfo);
            if (response == null) {
                System.err.println("不支持的接口类型：" + caseInfo.getRequestType());
                log.error("不支持的接口类型：" + caseInfo.getRequestType());
            }
//System.out.println(JSON.toJSON(response));
            //检查接口返回
            Assert.assertEquals(responseCheck(response),true);
            //检查数据库
            Assert.assertEquals(databaseCheck(response),true);
            //记录测试结果
            log.info("当前用例ID:" + caseInfo.getId() + "测试通过。");
            log.info("=================================用例ID" + caseInfo.getId() + "_END=================================");
            response.setTestResult(true);
            //将测试信息存储在map中
            testResult.put(caseInfo.getId(),response);
        } catch (Exception e) {
            /**
             * 捕获到异常后，将当前用例的测试结果标记为失败，将当前用例的信息传入response中
             * 然后将测试用例的测试信息记录到map中，最后抛出异常
             */
            response = new Response();
            response.setCaseInfo(caseInfo);
            response.setTestResult(false);
            response.setFailures(e.getMessage());
            testResult.put(caseInfo.getId(),response);
            log.info("=================================用例ID" + caseInfo.getId() + "_END=================================");
            throw new CheckException(e.getMessage());
//            throw new CheckException(e.getLocalizedMessage());
        }

        Assert.assertEquals(response.getTestResult(),true);

    }

//    @Test(priority = 2)
//    public void getTestResultCount() {
//        System.out.println("测试结果数量："+ testResult.size());
//        for (Object id:testResult.keySet()) {
//            if (!testResult.get(id).getTestResult()) {
//                System.out.println("测试用例ID：" + id + "的测试结果是：" + testResult.get(id).getTestResult() + "；失败原因为：" + testResult.get(id).getFailures());
//            }else {
//                System.out.println("测试用例ID：" + id + "的测试结果是：" + testResult.get(id).getTestResult());
//            }
//        }
//    }

    /**
     * 对比接口返回数据
     * @param response
     */
    private boolean responseCheck(Response response) {
        log.info("对比检查点数据和接口返回中是否一致");
        if (!Check.contrastMap(response.getBody(),response.getCaseInfo().getResponseCheck())) {
            throw new CheckException("返回信息对比失败");
        }
        return true;
    }

    /**
     * 对比数据库检查
     * @param response
     * @return
     */
    public boolean databaseCheck(Response response) {
        if (response.getCaseInfo().getDbChecks().isEmpty()) return true;
        log.info("对比数据库检查点和SQL查询结果是否一致");
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("system.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = properties.getProperty("url");
        String user = properties.getProperty("username");
        String password = properties.getProperty("userpassword");
        DatabaseUtil databaseUtil = new DatabaseUtil(url,user,password);
        databaseUtil.connSQL();
        for (DBCheck dbCheck:response.getCaseInfo().getDbChecks()) {
            log.info("开始执行SQL：\"" + dbCheck.getSql() + "\"的检查");
            try {
                ResultSet resultSet = databaseUtil.selectSQL(dbCheck.getSql());
                if (dbCheck.isResultIsNull()) {
                    resultSet.last();
                    if (resultSet.getRow() >= 1) {
                        throw new CheckException("用例\"" + response.getCaseInfo().getName() + "\"的SQL:\"" + dbCheck.getSql() + "\"查询结果不为空。");
                    }
                }else {
                    resultSet.last();
                    int rowCount = resultSet.getRow();
                    resultSet.beforeFirst();
                    if (rowCount<=0 || rowCount > 1) {
                        throw new CheckException("用例\"" + response.getCaseInfo().getName() + "\"的SQL:\"" + dbCheck.getSql() + "\"查询结果应该为一条数据。");
                    } else {
                        while (resultSet.next()) {
                            Map<Object,Object> checkPoint = dbCheck.getCheckPoint();
                            for (Object key : checkPoint.keySet()) {
                                String result = resultSet.getString(key.toString());
                                log.info("参数：\"" + key + "\"的预期结果为：'" + checkPoint.get(key) + "',实际查询结果为：'" + result + "',对比结果一致");
//System.out.println("SQL\"" + dbCheck.getSql() + "\"的预期结果为" + checkPoint.get(key) + "；实际查询结果为：" + result);
                                if (! result.equals(checkPoint.get(key))) {
                                    throw new CheckException("用例\"" + response.getCaseInfo().getName() + "\"的SQL:\"" + dbCheck.getSql() + "\"查询结果" + key + "的值为：" +result+ "；与预期结果" + checkPoint.get(key) + "不一致");
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        log.info("数据库检查点数据检查通过，校验结束");
        databaseUtil.deconnSQL();
        return true;
    }


    /**
     * 获取excel中所有测试用例的信息；并将其装载到测试方法中
     * @return 用例的集合
     */
    @DataProvider(name = "cases")
    @BeforeClass
    public Iterator<Object[]> getCases() {
        super.testResult = new HashMap<>();
        ExcelUtil excelUtil = new ExcelUtil();
//        List<Case> item = excelUtil.readExcelGetCase(getExcelPath() + "/接口测试用例模板.xlsx");
        List<Case> item = excelUtil.readExcelGetCase(getExcelPath());
        List<Object[]> cases = new ArrayList<Object[]>();
        for (Object caseInfo : item) {
            //做一个形式转换
            cases.add(new Object[] { caseInfo });
        }
        return cases.iterator();
    }


}
