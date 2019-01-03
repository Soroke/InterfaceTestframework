package net.faxuan.interfaceTest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2018/12/25.
 */
public class JsonHelper {

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

    @Test
    public void asd() {
        String a = "{\"msg\":\"操作成功\",\"code\":\"0\",\"data\":[{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"常回家看看\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/3b007c589bc94425bdd27f12a8063210.jpg\",\"createTime\":\"2018-12-20\",\"id\":152,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"见义勇为\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/0803b01ac8e94144b8d702455cf7dbd2.jpg\",\"createTime\":\"2018-12-20\",\"id\":151,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"向英雄致敬\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/35204b9cfbb24df787e4d9d8d2e23646.jpg\",\"createTime\":\"2018-12-20\",\"id\":150,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"扫他人瓦上霜\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/df02a8a8838f41ff96e73cbfce996c69.jpg\",\"createTime\":\"2018-12-20\",\"id\":149,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"白眼狼儿子\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/399d2733d0144e2b89df437404b2c0ac.jpg\",\"createTime\":\"2018-12-20\",\"id\":148,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"啃老\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/2772871c67de40aea04c9f0ff1268b3c.jpg\",\"createTime\":\"2018-12-20\",\"id\":147,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"养老送终\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/6ff77ebcf847421bbe53deb37031d19d.jpg\",\"createTime\":\"2018-12-20\",\"id\":146,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"一元房租\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/0a6b01b99a4946afb4bb8794360cc8f6.jpg\",\"createTime\":\"2018-12-20\",\"id\":145,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"养老院是非\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/429ea15d1f65424ba94bd628d8d76e86.jpg\",\"createTime\":\"2018-12-20\",\"id\":144,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"},{\"areaCode\":\"\",\"areas\":[],\"areasSet\":[],\"comicDetail\":\"\",\"comicStatus\":null,\"comicTitle\":\"保护养母权益\",\"comicType\":null,\"coverPath\":\"http://47.104.105.2/cdn/files//comic/07b2f8738fa94f0e8e4837b154dab990.jpg\",\"createTime\":\"2018-12-20\",\"id\":143,\"str1\":\"\",\"str2\":\"\",\"updateTime\":\"2018-12-20\"}],\"count\":86}";
        List<Map<Object,Object>> asd = convertJsonToListMap(a);
        System.out.println(asd.size());
        for (Map<Object,Object> test:asd) {
            if (!test.isEmpty()) {
//System.out.println(test.size());
                for (Object z : test.keySet()) {
                    System.out.println(z + ":" + test.get(z));
                }
            }
        }
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
}
