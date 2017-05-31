package com.xunyuc.xproject.documentconvert.swftools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by chenwh1 on 2017/5/31.
 */
public class Pdf2SwfConvert {

    public static void main(String[] args) throws  Exception{
        String sourcePath = "F:\\test-ppt.pdf"; //源文件路径
        String destPath = "F:\\";                      //目标路径
        String fileName = "test-ppt.swf";       //生成文件名
        try {
            Pdf2SwfConvert.convertPDF2SWF(sourcePath, destPath, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int convertPDF2SWF(String sourcePath, String destPath,
                                     String fileName) throws IOException {
        // 目标路径不存在则建立目标路径
        File dest = new File(destPath);
        if (!dest.exists())
            dest.mkdirs();

        // 源文件不存在则返回
        File source = new File(sourcePath);
        if (!source.exists())
            return 0;

        // 调用pdf2swf命令进行转换
        String command = "D:\\SWFTools\\pdf2swf.exe" + " " + sourcePath+ " -o "
                + destPath + fileName + " -f -T 9";
        System.out.println(command);
        Process pro = Runtime.getRuntime().exec(command);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(pro.getInputStream()));
        while (bufferedReader.readLine() != null);
        try {
            pro.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pro.exitValue();
    }

}
