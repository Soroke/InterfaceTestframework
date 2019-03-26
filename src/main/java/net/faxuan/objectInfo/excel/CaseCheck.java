package net.faxuan.objectInfo.excel;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<Map<Object,Object>> falseResponseCheck;

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
        String[] checks = responseCheck.split("&&");
        for (String check:checks) {
            if (! check.contains("!=")) {
                try{
                    String key = check.substring(0,check.indexOf("="));
                    String value = check.substring(check.indexOf("=") + 1);
                    responseChecks.put(key,value);
                } catch (StringIndexOutOfBoundsException e) {}
            }
//            String[] keyValue = check.split("=");
//            try {
//                responseChecks.put(keyValue[0],keyValue[1]);
//            } catch (ArrayIndexOutOfBoundsException e) {
//                responseChecks.put(keyValue[0],"");
//            }
        }
        this.responseCheck = responseChecks;
    }


    public void setFalseResponseCheck(String responseCheck) {
        //使用list避免检查点中有重复key；会覆盖
        List<Map<Object,Object>> falseResponseChecks = new ArrayList<>();
//        Map<Object,Object> falseResponseChecks = new HashMap<Object,Object>();
        String[] checks = responseCheck.split("&&");
        for (String check:checks) {
            if (check.contains("!=")) {
                try{
                    String key = check.substring(0,check.indexOf("!="));
                    String value = check.substring(check.indexOf("!=") + 2);
                    Map<Object,Object> keyValue = new HashMap<Object,Object>();
                    keyValue.put(key,value);
                    falseResponseChecks.add(keyValue);
                } catch (StringIndexOutOfBoundsException e) {}
            }
        }
        this.falseResponseCheck=falseResponseChecks;
    }

    public List<Map<Object,Object>> getFalseResponseCheck() {
        return falseResponseCheck;
    }

    @Override
    public String toString() {
        return ("用例ID:" + id + "\t用例名称:" + caseName + "\t关联接口ID："
                + contactId + "\t前置条件：" + precondition + "\t参数："
                + params + "\t状态码：" + statusCode + "\t接口返回检查点：" + responseCheck);
    }
}
