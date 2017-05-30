package com.xunyuc.xproject.documentconvert.poi;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Xunyuc on 2017/5/29.
 */
public class Pptx2PdfConvert {

    public static void main(String[] args) {
        String inputFile = "E:\\temp\\test.pptx";
        String outFile = "E:\\temp\\test-pptx.pdf";
        try {
            Pptx2PdfConvert.convert(inputFile, outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 使用poi自带的ppt画图方法draw
     * TODO 图表问题
     * @param inputFile
     * @param outFile
     * @throws Exception
     */
    public static void convert(String inputFile, String outFile) throws Exception {


        InputStream iStream = new FileInputStream(inputFile);
        XMLSlideShow ppt = new XMLSlideShow(iStream);
        Dimension pgsize = ppt.getPageSize();
        java.util.List<XSLFSlide> slides = ppt.getSlides();


        double zoom = 2; // magnify it by 2 as typical slides are low res
        AffineTransform at = new AffineTransform();
        at.setToScale(zoom, zoom);


        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outFile));
        document.open();

        for (int i = 0; i < slides.size(); i++) {

            BufferedImage bufImg = new BufferedImage((int)Math.ceil(pgsize.width*zoom), (int)Math.ceil(pgsize.height*zoom), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bufImg.createGraphics();
            graphics.setTransform(at);
            //clear the drawing area
            graphics.setPaint(slides.get(i).getBackground().getFillColor());
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            try{
                // 解决乱码问题
                List<XSLFShape> shapes = slides.get(i).getShapes();
                for (XSLFShape shape : shapes) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape sh = (XSLFTextShape) shape;
                        List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                        for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
                            List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                            for (XSLFTextRun xslfTextRun : textRuns) {
                                xslfTextRun.setFontFamily("宋体");
//								System.out.print("---------------"+xslfTextRun.getFontFamily());
                            }
                        }
                     }
                 }
                slides.get(i).draw(graphics);
            } catch(Exception e){
                //Just ignore, draw what I have
            }

            Image image = Image.getInstance(bufImg, null);
            document.setPageSize(new Rectangle(image.getScaledWidth(), image.getScaledHeight()));
            document.newPage();
            image.setAbsolutePosition(0, 0);
            document.add(image);
        }
        //Seems like I must close document if not output stream is not complete
        document.close();

        //Not sure what repercussions are there for closing a writer but just do it.
        writer.close();


    }

}
