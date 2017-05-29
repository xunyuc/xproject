package com.xunyuc.xproject.documentconvert.libreoffice;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Created by Xunyuc on 2017/5/28.
 *
 * /usr/bin
 * ./soffice --accept="socket,host=0,port=8100;urp;StarOffice.ServiceManager" --headless --nofirststartwizard --nologo --nodefault --nocrashreport --nolockcheck
 *  ./soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard &
 *  netstat -ntlp|grep 8100
 *  进入命令行   ps -a
 *  找到进openoffice进程ID
 *  export JAVA_HOME=/usr/local/java/jdk1.8.0_131
 *  export JRE_HOME=${JAVA_HOME}/jre
 *  export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
 *  export PATH=${JAVA_HOME}/bin:$PATH
 *  java -Djava.ext.dirs=/home/xproject/lib/ -cp xproject.jar com.xunyuc.xproject.documentconvert.libreoffice.ConvertTest /home/test.docx /home/test.pdf
 */
public class ConvertTest {

    public static void main(String[] args) {
        String inputFile = "E:\\temp\\test.docx";
        String outFile = "E:\\temp\\test.pdf";
        if (args != null && args.length>1 ) {
            inputFile = args[0];
            outFile = args[1];
        }
        ConvertTest.convert2Pdf(inputFile, outFile);
    }

    public static void convert2Pdf(String inputFile, String outFile) {

        File docFile = new File(inputFile);
        String extension = FilenameUtils.getExtension(docFile.getName());
        File pdfFile = new File(outFile);

        DocumentFormat inputFormat = null;
        if ("docx".equals(extension)) {
            inputFormat = new DocumentFormat("Microsoft Word 2007 XML", DocumentFamily.TEXT, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");

        } else if ("xlsx".equals(extension)) {
            inputFormat = new DocumentFormat("Microsoft Excel 2007 XML", DocumentFamily.SPREADSHEET, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
        } else if ("pptx".equals(extension)) {
            inputFormat = new DocumentFormat("Microsoft PowerPoint 2007 XML", DocumentFamily.PRESENTATION, "application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
        }

        if (docFile.exists()) {
            if (!pdfFile.exists()) {
//                OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.80.131", 8100);
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
                try {
                    connection.connect();
                    /*
                        Stream-based conversions are slower than the default file-based ones (provided by OpenOfficeDocumentConverter)
                        but they allow to run the OpenOffice.org service on a different machine,
                        or under a different system user on the same machine without file permission problems.
                     */
//                    DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
                    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);


                    converter.convert(docFile, inputFormat, pdfFile, null);

                    System.out.println("****pdf转换成功，PDF输出：" + pdfFile.getPath()+ "****");
                } catch (java.net.ConnectException e) {
                    e.printStackTrace();
                    System.out.println("****转换器异常，openoffice服务未启动！****");
                } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                    e.printStackTrace();
                    System.out.println("****转换器异常，读取转换文件失败****");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // close the connection
                    connection.disconnect();
                }
            } else {
                System.out.println("****已经转换为pdf，不需要再进行转化****");
            }
        } else {
            System.out.println("****doc文档不存在，无法转换****");
        }

//        File outDir = new File("F:\\pdfbox_result\\"+extension + time);
//        if (!outDir.exists()) {
//            outDir.mkdir();
//        }
//        Pdf2ImageUtil.pdf2Image(pdfFile,"F:\\pdfbox_result\\"+extension + time);


    }
}
