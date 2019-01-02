package net.faxuan;

import net.faxuan.interfaceTest.core.Response;

import java.util.Map;

public class Init {
    public Map<Long, Response> testResult;

    /**
     * 获取当前系统目录
     * @return
     */
    public String getExcelPath() {
        String path = System.getProperty("user.dir");

        if (path.contains("target")) {
            path = path.replaceAll("target","");
            path = path.replaceAll("target","");
        }
        return path;
    }
}
