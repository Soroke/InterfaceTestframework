package net.faxuan.objectInfo.caseObject;

import net.faxuan.objectInfo.excel.DBCheck;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 */
public class Case {
    private long id;
    private String name;
    private String url;
    private String requestType;
    private Map<Object,Object> params;
    private Map<Object,Object> responseCheck;
    private List<DBCheck> dbChecks;

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

    @Override
    public String toString() {
        return "用例ID:" + id + "\t用例名称："+name + "\t接口URL："+url + "\t接口类型："+requestType +
                "\t用例接口参数："+params + "\t用例接口返回检查："+ responseCheck + "\t数据检查："+ dbChecks;
    }
}
