package net.faxuan;

import net.faxuan.interfaceTest.core.Response;
import net.faxuan.interfaceTest.exception.CheckException;
import net.faxuan.interfaceTest.util.ExcelUtil;
import net.faxuan.objectInfo.caseObject.Case;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import static net.faxuan.interfaceTest.core.Http.sendRequest;

/**
 * Created by song on 2018/12/25.
 */
public class HttpClientTest extends Init{


    @Test(dataProvider = "cases",priority = 1)
    public void testInterFace(Case caseInfo) {
        Response response = null;
        try {
            response = sendRequest(caseInfo);
            testResult.put(caseInfo.getId(),response);
        } catch (Exception e) {
            response = new Response();
            response.setCaseInfo(caseInfo);
            response.setTestResult(false);
            testResult.put(caseInfo.getId(),response);
            throw new CheckException(e.getLocalizedMessage());
        }

        Assert.assertEquals(response.getTestResult(),true);

//         Response response = sendRequest(caseInfo);
//         testResult.put(caseInfo.getId(),response);
    }

    @Test(priority = 2)
    public void getTestResultCount() {
        System.out.println("测试结果数量："+ testResult.size());
        for (Object id:testResult.keySet()) {
            System.out.println("测试用例ID：" + id + "的测试结果是：" + testResult.get(id).getTestResult());
        }
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

    /**
     * 获取当前目录
     * @return
     */
    public String getExcelPath() {
        String path = System.getProperty("user.dir");

        if (path.contains("target")) {
            path = path.replaceAll("target","");
            path = path.replaceAll("target","");
        }
        return path;
    }
}
