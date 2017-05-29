package com.xunyuc.xproject.documentconvert.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Xunyuc on 2017/5/29.
 */
public class Pdf2ImageConvert {

    public static void main(String[] args) {
        String pdfFile = "E:\\temp\\test-ppt.pdf";
        String outFileDir = "E:\\temp";
        Pdf2ImageConvert.pdf2Image(pdfFile, outFileDir);
    }


    public static void pdf2Image(String pdfFile, String outFileDir) {
        PDDocument doc = null;
        try {
            doc = PDDocument.load(new File(pdfFile));
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for(int i=0;i<pageCount;i++){
                BufferedImage image = renderer.renderImageWithDPI(i, 296);
                // BufferedImage image = renderer.renderImage(i, 2.5f);
                ImageIO.write(image, "PNG", new File(outFileDir + "\\pdfbox_image"+(i+1)+".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
