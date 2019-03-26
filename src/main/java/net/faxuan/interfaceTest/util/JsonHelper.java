package net.faxuan.interfaceTest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

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
     * 从json中取出key路径的value
     * 例子
     * json={"code":200,"data":{"auth":{"id":"10086"}},"data_2":[{"uid":"321"},{"uid":"123"}]}
     *  key="code"          return 200
     *  key="data.auth"     return {"id":"10086"}
     *  key="data.auth.id"  return "10086"
     *  key="data_2[0]"     return {"uid":"321"}
     *  key="data_2[1].uid" return 123
     *
     * @param json
     *      json串 可以是任意类型
     * @param key
     *      完整的key路径 key之间用.连接
     * @return
     *      value
     */
    public static Object getValue(Object json, String key){
        Object obj = getValue_(json, key);
        if(obj instanceof notFond || obj == null){
            String lastValue = ((notFond) obj).getValie();
            throw new JSONException("没有从json中找到key\njson:"+json+"\nkey:" + key.substring(0, key.indexOf(lastValue))+lastValue);
        }else {
            return obj;
        }
    }

    /**
     * 取json里的value
     * @param json
     * @param key
     * @return
     */
    private static Object getValue_(Object json, String key) {
        key = key.replace("]", "");
        key = key.replace("[", ".");
        String keys[] = key.split("\\.");

        try{
            if(json instanceof JSONArray){
                if(keys.length==1){
                    return ((JSONArray) json).get(Integer.parseInt(keys[0]));
                }else {
                    return getValue_(((JSONArray) json).get(Integer.parseInt(keys[0])), key.substring(key.indexOf(".")+1));
                }
            }
            if(json instanceof JSONObject){
                if(keys.length==1){
                    if (null == ((JSONObject) json).get(keys[0])) {
                        return new notFond(keys[0]);
                    }else {
                        return ((JSONObject) json).get(keys[0]);
                    }
                }else {
                    if(null == ((JSONObject) json).get(keys[0]))
                        return new notFond(keys[0]);
                    else
                        return getValue_(((JSONObject) json).get(keys[0]), key.substring(key.indexOf(".")+1));
                }
            }
            if(json instanceof String){
                return getValue_(JSON.parse((String) json), key);
            }
        }catch (Exception e){
            return new notFond(keys[0]);
        }
        return new notFond(keys[0]);
    }

    static class notFond{
        String valie;
        public notFond(String valie){
            this.valie = valie;
        }
        public String getValie(){
            return this.valie;
        }
    }
}
