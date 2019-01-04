package net.faxuan.interfaceTest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 */
public class JsonHelper {

    /**
     * 将接口返回json类型的字符串信息转换为List Map
     * @param json 源数据字符串
     * @return
     */
    public static List<Map<Object,Object>> convertJsonToListMap(String json) {
        List<Map<Object,Object>> datas = new ArrayList<>();
        try {
            JSON.parse(json);
            //根据json类型字符串分组
            String[] jsons = json.split("},\\{");
            for (String data:jsons) {
                Map<Object,Object> da = new HashMap<>();
                da = convertJsonToMap(data);
                datas.add(da);
            }
        } catch (JSONException jse) {
            return null;
        }
        return datas;
    }

    /**
     * 将json类型String字符串转换为map
     * @param json
     * @return
     */
    private static Map<Object,Object> convertJsonToMap(String json) {
        Map<Object,Object> datas = new HashMap<>();

        //去除json串中的{}括号和[]
        json = json.replaceAll("\\{","");
        json = json.replaceAll("}","");
        json = json.replaceAll("\\[","");
        json = json.replaceAll("]","");
        //首次分割字符串
        String[] data = json.split(",");
        for (String values:data) {
            String[] keyValue = values.split("\":");
            if (keyValue.length == 1) {
                datas.put(keyValue[0].replaceAll("\"",""),"");
            }else {
                keyValue[keyValue.length - 2] = keyValue[keyValue.length - 2] + "\"";
                datas.put(keyValue[keyValue.length - 2].replaceAll("\"", ""), keyValue[keyValue.length - 1].replaceAll("\"", ""));
            }
        }
        return datas;
    }

    /**
     * 获取json中的值
     * @param json
     * @param key
     * @return
     */
//    public static String getValue(String json,String key) {
//        Map<Object,Object> datas = convertJsonToMap(json);
//
//        for (Object key1:datas.keySet()) {
//            if (key.equals(key1.toString())) {
//                return datas.get(key1).toString();
//            }
//        }
//        return null;
//    }
}
