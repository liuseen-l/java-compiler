package sample.compiler.util;

import java.io.File;

/**
 * @author : HK意境
 * @ClassName : FileUtil
 * @date : 2022/5/1 20:54
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class FileUtil {

    // 获取文件内容
    public static String getFileContent(File file){
        // 获取文件内容
        String content = "";
        try {
            content = FileUtil.readFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private static String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            java.io.FileReader fr = new java.io.FileReader(file);
            java.io.BufferedReader br = new java.io.BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



}
