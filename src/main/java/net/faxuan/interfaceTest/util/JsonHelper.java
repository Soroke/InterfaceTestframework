package net.faxuan.interfaceTest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 */
public class JsonHelper {

    /**
     * json串获取其map
     * @param json
     * @return
     */
    public static Map<Object,Object> convertJsonToMap(String json) {
        Map<Object,Object> datas = new HashMap<>();
        try{
            JSON.parse(json);
            //去除json串中的{}括号和[]
            json = json.replaceAll("\\{","");
            json = json.replaceAll("}","");
            json = json.replaceAll("\\[","");
            json = json.replaceAll("]","");
            //首次分割字符串
            String[] data = json.split(",");
            for (String values:data) {
                String[] keyValue = values.split("\":");
                keyValue[keyValue.length-2] = keyValue[keyValue.length-2] + "\"";
                datas.put(keyValue[keyValue.length-2].replaceAll("\"",""),keyValue[keyValue.length-1].replaceAll("\"",""));
            }

        }catch (JSONException jse){
            return null;
        }
        return datas;
    }

    /**
     * 获取json中的值
     * @param json
     * @param key
     * @return
     */
    public static String getValue(String json,String key) {
        Map<Object,Object> datas = convertJsonToMap(json);

        for (Object key1:datas.keySet()) {
            if (key.equals(key1.toString())) {
                return datas.get(key1).toString();
            }
        }
        return null;
    }

    @Test
    public void te() {
        String ss = "{\"code\":200,\"msg\":\"登陆成功\",\"data\":{\"orderNumber\":\"\",\"userPhone\":\"15508976543\",\"commentScore\":\"\",\"lawyerPhone\":\"\",\"practiceYears\":\"\",\"operator\":\"AK05\",\"lawyerDescribe\":\"\",\"sid\":\"99b9a389cce6481e91e991006fd4c834\",\"lawyerEmail\":\"\",\"serverNames\":\"\",\"imageUrl\":\"https://fzfile.t.faxuan.net/user/userAccount/AK05/AK051544086955817.png\",\"userEmail\":\"Dfg@aa.cn\",\"id\":\"194\",\"ext2\":\"\",\"ext1\":\"0\",\"identificationStatus\":\"3\",\"roleId\":\"2\",\"caringIndex\":\"\",\"nickName\":\"岂曰无衣与子同袍\",\"updateTime\":\"2018-12-11 10:47:34\",\"userName\":\"\",\"payAccount\":\"\",\"token\":\"NmoqErr7H96ltTKvx7fo/rtcELwU5Rf85ELSFBIIo7GsFUZypF8w0tKGmaMe5xE36X7OPqTxFcoov/1J0BOhmA==\",\"totalIncome\":\"\",\"areaCode\":\"\",\"createTime\":\"2018-12-06 16:11:02\",\"servicePrice\":\"\",\"monthIncome\":\"\",\"userAccount\":\"AK05\",\"userType\":\"2\",\"fieldIds\":\"\",\"status\":\"0\"}}\n";
        System.out.println(ss);
        ss = ss.replaceAll("\\{","");
        ss = ss.replaceAll("}","");
        ss = ss.replaceAll("}","");
//        ss = ss.replaceAll("\"","");
        String[] datas = ss.split(",");
        for (String s:datas) {
//            System.out.print(s + "\t\t\t===\t\t\t");
            String[] a = s.split("\":");
            a[a.length-2] = a[a.length-2] + "\"";
            System.out.println(a[a.length-2] + "=" + a[a.length-1]);

        }
//        convertJsonToMap(ss);
    }
}
