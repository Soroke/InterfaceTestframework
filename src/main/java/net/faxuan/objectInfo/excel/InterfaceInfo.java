package net.faxuan.objectInfo.excel;
/**
 * Created by song on 2018/12/25.
 * 接口信息sheet
 */
public class InterfaceInfo {
    private long id;
    private String requestType;
    private String url;
    private String params;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return ("接口ID:" + id + "\t接口类型:" + requestType + "\t地址：" + url + "\t参数：" + params);
    }
}
