package net.faxuan.objectInfo.excel;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 * 数据库检查sheet
 */
public class DBCheck {
    @JSONField(name = "contacctID",ordinal = 1)
    private String contactId; //关联caseID
    @JSONField(name = "sql",ordinal = 2)
    private String sql;
    @JSONField(name = "resultIsNull",ordinal = 3)
    private boolean resultIsNull;
    @JSONField(name = "checkPoint",ordinal = 4)
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
        String[] checks = checkPoint.split("&&");
        for (String check:checks) {
            if (check.contains("!=")) {
                try {
                    String key = check.substring(0,check.indexOf("!="));
                    String value = check.substring(check.indexOf("!=") + 1);
                    checkPoints.put(key,value);
                } catch (StringIndexOutOfBoundsException e) {}
            } else {
                try {
                    String key = check.substring(0,check.indexOf("="));
                    String value = check.substring(check.indexOf("=") + 1);
                    checkPoints.put(key,value);
                } catch (StringIndexOutOfBoundsException e) {}
            }

//            String[] keyValue = check.split("=");
//            try {
//                checkPoints.put(keyValue[0],keyValue[1]);
//            } catch (ArrayIndexOutOfBoundsException e) {
//                checkPoints.put(keyValue[0],"");
//            }
        }
        this.checkPoint = checkPoints;
    }

    public void setCheckPoint(Map<Object,Object> checkPoint) {
        this.checkPoint = checkPoint;
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
