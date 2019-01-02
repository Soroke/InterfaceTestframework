package net.faxuan;

import com.alibaba.fastjson.JSON;
import net.faxuan.interfaceTest.core.Response;
import net.faxuan.interfaceTest.exception.CheckException;
import net.faxuan.interfaceTest.util.Check;
import net.faxuan.interfaceTest.util.DatabaseUtil;
import net.faxuan.interfaceTest.util.ExcelUtil;
import net.faxuan.objectInfo.caseObject.Case;
import net.faxuan.objectInfo.excel.DBCheck;
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


    @Test(dataProvider = "cases",priority = 1)
    public void testInterFace(Case caseInfo) {
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
            }
            System.out.println(JSON.toJSON(response));
            //检查接口返回
            Assert.assertEquals(responseCheck(response),true);
            //检查数据库
            Assert.assertEquals(databaseCheck(response),true);
            //记录测试结果
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
            testResult.put(caseInfo.getId(),response);
            throw new CheckException(e.getLocalizedMessage());
        }

        Assert.assertEquals(response.getTestResult(),true);

    }

    @Test(priority = 2)
    public void getTestResultCount() {
        System.out.println("测试结果数量："+ testResult.size());
        for (Object id:testResult.keySet()) {
            System.out.println("测试用例ID：" + id + "的测试结果是：" + testResult.get(id).getTestResult());
        }
    }

    /**
     * 对比接口返回数据
     * @param response
     */
    private boolean responseCheck(Response response) {
        if (Check.contrastMap(response.getBody(),response.getCaseInfo().getResponseCheck())) {
//System.out.println("状态码对比结果：" + response.getCaseInfo().getStatusCode().equals(String.valueOf(response.getStatusCode())));
            if (response.getCaseInfo().getStatusCode().equals(String.valueOf(response.getStatusCode()))) {
                response.setTestResult(true);
            } else throw new CheckException("用例id:" + response.getCaseInfo().getId() + "预期状态码：" + response.getCaseInfo().getStatusCode() + "，实际返回状态码：" + response.getStatusCode() );
        } else {
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

        databaseUtil.deconnSQL();
        return true;
    }


    @DataProvider(name = "cases")
    @BeforeClass
    public Iterator<Object[]> getCases() {
        super.testResult = new HashMap<>();
        ExcelUtil excelUtil = new ExcelUtil();
//        List<Case> item = excelUtil.readExcelGetCase(getExcelPath() + "/接口测试用例模板.xlsx");
        List<Case> item = excelUtil.readExcelGetCase(getExcelPath() + "/接口测试用例模板.xlsx");
        List<Object[]> cases = new ArrayList<Object[]>();
        for (Object caseInfo : item) {
            //做一个形式转换
            cases.add(new Object[] { caseInfo });
        }
        return cases.iterator();
    }


}
