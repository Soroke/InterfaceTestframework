package net.faxuan.objectInfo.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 * 数据库检查sheet
 */
public class DBCheck {
    private String contactId; //关联caseID
    private String sql;
    private boolean resultIsNull;
    private Map<Object,Object> checkPoint;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<Object,Object> getCheckPoint() {
        return checkPoint;
    }

    /**
     * 将接收字符串处理为Map类型键值对
     * @param checkPoint
     */
    public void setCheckPoint(String checkPoint) {
        Map<Object,Object> checkPoints = new HashMap<Object,Object>();
        String[] checks = checkPoint.split("&");
        for (String check:checks) {
            String[] keyValue = check.split("=");
            try {
                checkPoints.put(keyValue[0],keyValue[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                checkPoints.put(keyValue[0],"");
            }
        }
        this.checkPoint = checkPoints;
    }

    public boolean isResultIsNull() {
        return resultIsNull;
    }

    public void setResultIsNull(boolean resultIsNull) {
        this.resultIsNull = resultIsNull;
    }


    @Override
    public String toString() {
        return ("SQL关联用例ID:" + contactId + "\tslq:" + sql + "\t查询结果是否为空：" + resultIsNull + "\t检查点：" + checkPoint);
    }
}
