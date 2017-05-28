package com.xunyuc.xproject.documentconvert.docx2pdf;

/**
 * Created by Xunyuc on 2017/5/28.
 */
public class Pptx2PdfConvert {

    public static void main(String[] args) {
        String inputFile = "E:\\temp\\test.docx";
        String outFile = "E:\\temp\\test.pdf";
        Docx2PdfTest.xdocreport(inputFile, outFile);
//        Docx2PdfTest.xdocreport2(inputFile, outFile);

    }

}
