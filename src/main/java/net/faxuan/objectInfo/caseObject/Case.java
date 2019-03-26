package net.faxuan.objectInfo.caseObject;

import com.alibaba.fastjson.annotation.JSONField;
import net.faxuan.objectInfo.excel.DBCheck;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 */
public class Case {
    @JSONField(name = "caseid",ordinal = 1)
    private long id;
    @JSONField(name = "casename",ordinal = 2)
    private String name;
    @JSONField(name = "url",ordinal = 4)
    private String url;
    @JSONField(name = "requesttype",ordinal = 3)
    private String requestType;
    @JSONField(name = "params",ordinal = 5)
    private Map<Object,Object> params;
    @JSONField(name = "responseCheck",ordinal = 6)
    private Map<Object,Object> responseCheck;
    @JSONField(name = "falseResponseCheck",ordinal = 7)
    private List<Map<Object,Object>> falseResponseCheck;
    @JSONField(name = "dbcheck",ordinal = 8)
    private List<DBCheck> dbChecks;
    @JSONField(name = "isNormalCase",ordinal = 9)
    private boolean isNormalCase = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Map<Object, Object> getParams() {
        return params;
    }

    public void setParams(Map<Object, Object> params) {
        this.params = params;
    }

    public Map<Object, Object> getResponseCheck() {
        return responseCheck;
    }

    public void setResponseCheck(Map<Object, Object> responseCheck) {
        this.responseCheck = responseCheck;
    }

    public List<DBCheck> getDbChecks() {
        return dbChecks;
    }

    public void setDbChecks(List<DBCheck> dbChecks) {
        this.dbChecks = dbChecks;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<Object,Object>> getFalseResponseCheck() {
        return falseResponseCheck;
    }

    public void setFalseResponseCheck(List<Map<Object,Object>> falseResponseCheck) {
        this.falseResponseCheck = falseResponseCheck;
    }

    public boolean isNormalCase() {
        return isNormalCase;
    }

    public void setIsNormalCase(boolean normalCase) {
        isNormalCase = normalCase;
    }

    @Override
    public String toString() {
        return "用例ID:" + id + "\t用例名称："+name + "\t接口URL："+url + "\t接口类型："+requestType +
                "\t用例接口参数："+params + "\t用例接口返回检查："+ responseCheck + "\t数据检查："+ dbChecks;
    }
}
