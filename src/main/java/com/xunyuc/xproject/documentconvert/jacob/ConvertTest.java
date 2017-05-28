package com.xunyuc.xproject.documentconvert.jacob;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Created by Xunyuc on 2017/5/28.
 * mvn install:install-file -DgroupId=com.jacob -DartifactId=jacob -Dversion=1.18 -Dfile=E:/temp/jacob-1.18/jacob.jar -Dpackaging=jar -DgeneratePom=true
 * copy jacob-1.18-x64.dll to java.library.path
 */
public class ConvertTest {

    /*转PDF格式值*/
    private static final int wdFormatPDF = 17;
    private static final int xlFormatPDF = 0;
    private static final int ppFormatPDF = 32;

    public static void main(String[] args) {
        String inputFile = "E:\\temp\\test.docx";
        String outFile = "E:\\temp\\test.pdf";
        ConvertTest.word2PDF(inputFile, outFile);
    }


    private static boolean word2PDF(String inputFile, String pdfFile) {
        ComThread.InitMTA();

        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        Dispatch doc = null;
        try {
            app = new ActiveXComponent("Word.Application");// 创建一个word对象
            app.setProperty("Visible", new Variant(false)); // 不可见打开word
            app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch docs = app.getProperty("Documents").toDispatch();// 获取文挡属性

            System.out.println("打开文档 >>> " + inputFile);
            // Object[]第三个参数是表示“是否只读方式打开”
            // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            doc = Dispatch.call(docs, "Open", inputFile, false, true).toDispatch();
            // 调用Document对象的SaveAs方法，将文档保存为pdf格式
            System.out.println("转换文档 [" + inputFile + "] >>> [" + pdfFile + "]");
            Dispatch.call(doc, "SaveAs", pdfFile, wdFormatPDF);//word保存为pdf格式宏，值为17
//            Dispatch.call(doc, "ExportAsFixedFormat", pdfFile, wdFormatPDF); // word保存为pdf格式宏，值为17

            long end = System.currentTimeMillis();

            System.out.println("用时：" + (end - start) + "ms.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========Error:文档转换失败：" + e.getMessage());
        } finally {
            Dispatch.call(doc, "Close", false);
            System.out.println("关闭文档");
            if (app != null)
                app.invoke("Quit", new Variant[] {});
            // 如果没有这句话,winword.exe进程将不会关闭
            ComThread.Release();
            ComThread.quitMainSTA();
        }

        return false;
    }

    private static boolean ppt2PDF(String inputFile, String pdfFile) {
        ComThread.InitMTA();

        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        Dispatch ppt = null;
        try {
            app = new ActiveXComponent("PowerPoint.Application");// 创建一个PPT对象
            // app.setProperty("Visible", new Variant(false)); // 不可见打开（PPT转换不运行隐藏，所以这里要注释掉）
            // app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch ppts = app.getProperty("Presentations").toDispatch();// 获取文挡属性

            System.out.println("打开文档 >>> " + inputFile);
            // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            ppt = Dispatch.call(ppts, "Open", inputFile,
                    true,// ReadOnly
                    true,// Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();

            System.out.println("转换文档 [" + inputFile + "] >>> [" + pdfFile + "]");
            Dispatch.call(ppt, "SaveAs", pdfFile, ppFormatPDF);

            long end = System.currentTimeMillis();

            System.out.println("用时：" + (end - start) + "ms.");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========Error:文档转换失败：" + e.getMessage());
        } finally {
            Dispatch.call(ppt, "Close");
            System.out.println("关闭文档");
            if (app != null)
                app.invoke("Quit", new Variant[] {});
            ComThread.Release();
            ComThread.quitMainSTA();
        }

        return false;
    }

    private static boolean excel2PDF(String inputFile, String pdfFile) {
        ComThread.InitMTA();

        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        Dispatch excel = null;
        try {
            app = new ActiveXComponent("Excel.Application");// 创建一个PPT对象
            app.setProperty("Visible", new Variant(false)); // 不可见打开
            // app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch excels = app.getProperty("Workbooks").toDispatch();// 获取文挡属性

            System.out.println("打开文档 >>> " + inputFile);
            // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            excel = Dispatch.call(excels, "Open", inputFile, false, true).toDispatch();
            // 调用Document对象方法，将文档保存为pdf格式
            System.out.println("转换文档 [" + inputFile + "] >>> [" + pdfFile + "]");
            // Excel 不能调用SaveAs方法
            Dispatch.call(excel, "ExportAsFixedFormat", xlFormatPDF, pdfFile);

            long end = System.currentTimeMillis();

            System.out.println("用时：" + (end - start) + "ms.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========Error:文档转换失败：" + e.getMessage());
        } finally {
            Dispatch.call(excel, "Close", false);
            System.out.println("关闭文档");
            if (app != null)
                app.invoke("Quit", new Variant[] {});
            ComThread.Release();
            ComThread.quitMainSTA();
        }

        return false;
    }

}
