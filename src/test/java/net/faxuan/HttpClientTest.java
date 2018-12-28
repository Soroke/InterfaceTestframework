package net.faxuan;

import net.faxuan.interfaceTest.core.Response;
import net.faxuan.interfaceTest.exception.CheckException;
import net.faxuan.interfaceTest.util.Check;
import net.faxuan.interfaceTest.util.ExcelUtil;
import net.faxuan.objectInfo.caseObject.Case;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import static net.faxuan.interfaceTest.core.Http.post;

/**
 * Created by song on 2018/12/25.
 */
public class HttpClientTest extends Init{


    @Test(dataProvider = "cases",priority = 1)
    public void testInterFace(Case caseInfo) {
        Response response = post(caseInfo.getUrl(),caseInfo.getParams());
        response.setCaseInfo(caseInfo);
        System.err.println("返回数据：" + response.getBody() + "\n预期结果：" + caseInfo.getResponseCheck());
        if (Check.contrastMap(response.getBody(),caseInfo.getResponseCheck())) {
            if (caseInfo.getStatusCode().equals(String.valueOf(response.getStatusCode()))) {
                response.setTestResult(true);
            } else new CheckException("返回状态码对比失败，预期状态码：" + caseInfo.getStatusCode() + "，实际返回状态码：" + response.getStatusCode() );
        } else {
            new CheckException("返回信息对比失败");
        }
        testResult.put(caseInfo.getId(),response);
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
        List<Case> item = excelUtil.readExcelGetCase("D:\\workspace\\接口测试用例模板.xlsx");
        List<Object[]> cases = new ArrayList<Object[]>();
        for (Object caseInfo : item) {
            //做一个形式转换
            cases.add(new Object[] { caseInfo });
        }

        return cases.iterator();
    }
}
