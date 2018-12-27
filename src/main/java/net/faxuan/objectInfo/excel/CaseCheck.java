package net.faxuan.objectInfo.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 * 用例sheet
 */
public class CaseCheck {
    private Long id;
    private String caseName;
    private long contactId; //关联接口ID
    private String precondition; //前置条件
    private String params;
    private String statusCode;
    private Map<Object,Object> responseCheck;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getPrecondition() {
        return precondition;
    }

    public void setPrecondition(String precondition) {
        this.precondition = precondition;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Map<Object,Object> getResponseCheck() {
        return responseCheck;
    }

    public void setResponseCheck(String responseCheck) {
        Map<Object,Object> responseChecks = new HashMap<Object,Object>();
        String[] checks = responseCheck.split("&");
        for (String check:checks) {
            String[] keyValue = check.split("=");
            try {
                responseChecks.put(keyValue[0],keyValue[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                responseChecks.put(keyValue[0],"");
            }
        }
        this.responseCheck = responseChecks;
    }

    @Override
    public String toString() {
        return ("用例ID:" + id + "\t用例名称:" + caseName + "\t关联接口ID："
                + contactId + "\t前置条件：" + precondition + "\t参数："
                + params + "\t状态码：" + statusCode + "\t接口返回检查点：" + responseCheck);
    }
}
