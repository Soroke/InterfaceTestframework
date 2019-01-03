package net.faxuan.interfaceTest.util;

import net.faxuan.interfaceTest.exception.CheckException;
import org.apache.log4j.Logger;

import java.util.Map;

public class Check {
    /**
     * log4j打log
     */
    private static Logger log = Logger.getLogger(Check.class);
    /**
     * 数据对比
     * @param value1
     * @param value2
     * @return
     */
    public static boolean Contrast(Object value1,Object value2) {

        if (value1 instanceof String && value2 instanceof String) {
            if (value1.equals(value2)) {
                return true;
            }else {
                return false;
            }
        }else if (value1 instanceof Integer && value2 instanceof Integer) {
            if (value1 == value2) {
                return true;
            } else {
                return false;
            }
        }else if (value1 instanceof Map && value2 instanceof Map) {
            //首先对比两个map的键值对数量是否相同 key值是否全部相同
            if (((Map) value1).size() != ((Map) value2).size()) {
                return false;
            }
            for (Object key1:((Map) value1).keySet()) {
                boolean isInstanceof = false;
                for (Object key2:((Map) value1).keySet()) {
                    if (key1.equals(key2)) {
                        isInstanceof = true;
                    }
                }
                if (!isInstanceof) return false;
            }

            for (Object key:((Map) value1).keySet()) {
                if (!((Map) value1).get(key).equals(((Map) value2).get(key))){
                    return false;
                }
            }
            return true;
        }else if (value1 instanceof Integer && value2 instanceof Integer) {
            if (value1 == value2) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("不支持的类型或两个参数对象不是相同类型");
            return false;
        }

    }


    /**
     * 对比map
     * @param baseData 基础map
     * @param contrastData 对比map
     * @return
     */
    public static boolean contrastMap(Map<Object,Object> baseData,Map<Object,Object> contrastData) {
        String info = "";
        //首先检查基础数据中是否包含全部的对比数据的key
        for (Object key1:contrastData.keySet()) {
            boolean isInstanceof = false;
            for (Object key2:baseData.keySet()) {
                if (key2.toString().equals(key1.toString())) {
                    isInstanceof = true;

                }
            }
            if (!isInstanceof) {
                throw new CheckException("接口返回检查参数：\"" + key1 + "=" + contrastData.get(key1) + "\",在接口返回数据中不存在");
            }
        }
        for (Object key:contrastData.keySet()) {

            if (!(baseData.get(key).toString().equals(contrastData.get(key).toString()))){
                throw new CheckException("接口返回检查参数：\"" + key + "=" + contrastData.get(key) + "\",和实际接口返回：\"" + key + "=" + baseData.get(key) + "\"中的值对比结果不一致");
            }
            log.info("参数：\"" + key + "\"的预期结果为：'" + contrastData.get(key) + "',接口返回的值为：'" + baseData.get(key) + "',对比结果一致");
        }
        log.info("接口返回值和预期结果对比一致；返回值校验结束");
        return true;
    }
}
