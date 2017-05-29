package com.xunyuc.xproject.documentconvert.poi;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hslf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Xunyuc on 2017/5/29.
 */
public class Ppt2PdfConvert {

    public static void main(String[] args) {
        String inputFile = "E:\\temp\\test.ppt";
        String outFile = "E:\\temp\\test-ppt.pdf";
        try {
            Ppt2PdfConvert.convert(inputFile, outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convert(String inputFile, String outFile) throws Exception {
        HSLFSlideShow ppt = new HSLFSlideShow(new FileInputStream(inputFile));
        Dimension pgsize = ppt.getPageSize();
        List<HSLFSlide> slides = ppt.getSlides();

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
            graphics.setPaint(slides.get(i).getBackground().getFill().getBackgroundColor()); //getFillColor()
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            try{
                // 解决乱码问题
                List<HSLFShape> shapes = slides.get(i).getShapes();
                for (HSLFShape shape : shapes) {
                    if (shape instanceof HSLFTextShape) {
                        HSLFTextShape sh = (HSLFTextShape) shape;
                        List<HSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                        for (HSLFTextParagraph xslfTextParagraph : textParagraphs) {
                            List<HSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                            for (HSLFTextRun xslfTextRun : textRuns) {
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

            // to image
//            ImageIO.write(bufImg, "png", new FileOutputStream("E:\\temp\\test-ppt"+(i+1)+".png"));

            // to pdf
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(bufImg, null);
            document.setPageSize(new com.itextpdf.text.Rectangle(image.getScaledWidth(), image.getScaledHeight()));
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
