package net.faxuan.interfaceTest.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by song on 2018/12/25.
 */
public class FileUtil {

    /**
     * 创建配置文件
     * @param fileName
     * @param fileContent
     */
    public void createPropertiesFile(String fileName,Map<Object,Object> fileContent) {
        File file = new File(this.getClass().getResource("/").getPath() + "/" + fileName);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file,false);
            fileWriter.write("systemhost=" + fileContent.get("systemhost") + "\n");
            fileWriter.write("url=jdbc:mysql://" + fileContent.get("mysqlhost") + ":" + fileContent.get("mysqlport") + "/" + fileContent.get("dbName") + "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10\n");
            fileWriter.write("username=" + fileContent.get("mysqluser") + "\n");
            fileWriter.write("userpassword=" + fileContent.get("mysqlpassword"));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createJsonFile(){

    }


//    private Properties properties = new Properties();
//    public void read() {
//        try {
//            properties.load(this.getClass().getClassLoader().getResourceAsStream("soroke.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("a的值：" + properties.getProperty("a") +"\tb的值：" + properties.getProperty("b")
//                + "\tc的值：" + properties.getProperty("c"));
//
//    }
//
//    public static void main(String[] ages) {
//        Map<Object,Object> soroke = new HashMap<Object,Object>();
//        soroke.put("a","song");
//        soroke.put("b","ren");
//        soroke.put("c","kun");
//        new FileUtil("soroke.txt",soroke).read();
//    }

}
