package net.faxuan.interfaceTest.core;

import com.alibaba.fastjson.JSON;
import net.faxuan.interfaceTest.exception.CheckException;
import net.faxuan.interfaceTest.util.Check;
import net.faxuan.interfaceTest.util.JsonHelper;
import net.faxuan.objectInfo.caseObject.Case;
import org.apache.http.client.CookieStore;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 */
public class Response {
    private Case caseInfo;
    /**
     * headers
     */
    private Map<Object,Object> headers;

    /**
     * Cookies
     */
    private CookieStore cookies;
    /**
     * 返回结果
     */
    private List<Map<Object,Object>> body;
    /**
     * 运行时间
     */
    private Long runTime;
    /**
     * 状态码
     */
    private Integer statusCode;

    /**
     * 测试结果
     */
    private boolean testResult;

    /**
     * 失败原因
     */
    private String failures;

    /**
     * log4j打log
     */
    private Logger log = Logger.getLogger(this.getClass());


    public Case getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(Case caseInfo) {
        this.caseInfo = caseInfo;
    }

    public Map<Object, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<Object, Object> headers) {
        this.headers = headers;
    }

    public CookieStore getCookies() {
        return cookies;
    }

    public void setCookies(CookieStore cookies) {
        this.cookies = cookies;
    }

    public List<Map<Object,Object>> getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = JsonHelper.convertJsonToListMap(body);
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean getTestResult() {
        return testResult;
    }

    public void setTestResult(boolean testResult) {
        this.testResult = testResult;
    }

    public String getFailures() {
        return failures;
    }

    public void setFailures(String failures) {
        this.failures = failures;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
