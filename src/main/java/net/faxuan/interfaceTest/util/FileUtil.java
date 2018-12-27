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

    public void createPropertiesFile(String fileName,Map<Object,Object> fileContent) {
        File file = new File(this.getClass().getResource("/").getPath() + "/" + fileName);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file,false);
            for (Object key:fileContent.keySet()) {
                fileWriter.write(key + "=" + fileContent.get(key) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
