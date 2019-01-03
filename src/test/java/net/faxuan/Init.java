package net.faxuan;

import net.faxuan.interfaceTest.core.Response;
import net.faxuan.interfaceTest.exception.CheckException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

        List<File> files = getExcelFileList(path);
        if (files.size() == 1) {
            return files.get(0).getAbsolutePath();
        }else throw new CheckException("项目下不存在或存在多个excel文件");
    }


    /**
     * 获取文件夹下的所有excel文件对象
     * @param strPath
     * @return
     */
    public static List<File> getExcelFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        List<File> filelist = new ArrayList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();


                if (!files[i].isDirectory()) { // 判断是文件还是文件夹
                    if (fileName.endsWith("xlsx") || fileName.endsWith("xls")) {
                        String strFileName = files[i].getAbsolutePath();
//System.out.println("excel文件名称：" + strFileName);
                        filelist.add(files[i]);
                    }
//                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else {
                    continue;
                }
            }
        }
        return filelist;
    }
}
