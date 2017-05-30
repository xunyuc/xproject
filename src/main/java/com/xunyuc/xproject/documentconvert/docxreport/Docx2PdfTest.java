package com.xunyuc.xproject.documentconvert.docxreport;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.awt.*;
import java.io.*;

/**
 * Created by Xunyuc on 2017/5/28.
 */
public class Docx2PdfTest {

    public static void main(String[] args) {
        String inputFile = "E:\\temp\\test.docx";
        String outFile = "E:\\temp\\test-docx.pdf";
//        Docx2PdfTest.xdocreport(inputFile, outFile);
        Docx2PdfTest.xdocreport2(inputFile, outFile);

    }

    /**
     * TODO 中文字体问题
     * TODO 统计图表问题
     * convert docx 2 PDF/XHTML by using Apache POI + iText
     * fr.opensagres.xdocreport.converter.docx.xwpf
     *
     * convert docx 2 PDF/XHTML by using docx4j + XSL-FO and FOP:
     * fr.opensagres.xdocreport.converter.docx.docxreport
     *
     * @param inputFile
     * @param outFile
     */
    public static void xdocreport(String inputFile, String outFile) {
        try {
            // 1) Create options ODT 2 PDF to select well converter form the registry
            Options options = Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF);

            // 2) Get the converter from the registry
//            IConverter converter = ConverterRegistry.getRegistry().getConverter(options);
            fr.opensagres.xdocreport.converter.docx.docx4j.pdf.Docx2PDFViaDocx4jConverter converter = new fr.opensagres.xdocreport.converter.docx.docx4j.pdf.Docx2PDFViaDocx4jConverter();
            System.out.println(converter);

            // 3) Convert ODT 2 PDF
            InputStream in= new FileInputStream(new File(inputFile));
            OutputStream out = new FileOutputStream(new File(outFile));
            converter.convert(in, out, options);

//            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(in);
//            Mapper fontMapper = wordMLPackage.getFontMapper();
//            //  SimSun NSimSun
//            PhysicalFonts.addPhysicalFont("SimSun", new URL("file:/C:/Windows/Fonts/simsun.ttc"));
//            PhysicalFonts.addPhysicalFont("NSimSun", new URL("file:/C:/Windows/Fonts/simsun.ttc"));
//            PhysicalFont simsunFont = PhysicalFonts.getPhysicalFonts().get("SimSun");
//            fontMapper.getFontMappings().put("SimSun",simsunFont);
//            fontMapper.getFontMappings().put("NSimSun",simsunFont);
//            wordMLPackage.setFontMapper(fontMapper);
//            PdfConversion c = new Conversion(wordMLPackage);
//            c.output(out, converter.toPdfSettings(options));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * TODO 统计图表问题
     *
     * org.apache.poi.xwpf.converter.pdf
     * https://github.com/opensagres/xdocreport/wiki/XWPFConverterPDFViaIText
     *
     * Limitations
     * draw/shape graphics are not converted.
     * have some bugs with tab stop.
     * table border should be improved.
     * page number are not managed.
     * @param inputFile
     * @param outFile
     */
    public static void xdocreport2(String inputFile, String outFile) {
        try {
            long start = System.currentTimeMillis();

            // 1) Load DOCX into XWPFDocument
            InputStream is = new FileInputStream(new File(inputFile));
            XWPFDocument document = new XWPFDocument(is);

            // 2) Prepare Pdf options
            PdfOptions options = PdfOptions.create();

            options.fontProvider(new IFontProvider() {
                public Font getFont(String familyName, String encoding, float size, int style, Color color) {
                    try {
                        ///C:/Windows/Fonts/simhei.ttf
                        BaseFont bfChinese = BaseFont.createFont("C:/Windows/Fonts/simsun.ttc,0", encoding, BaseFont.NOT_EMBEDDED);
                        Font fontChinese = new Font(bfChinese, size, style, color);
                        if (familyName != null)
                            fontChinese.setFamily(familyName);
                        return fontChinese;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            });

            // 3) Convert XWPFDocument to Pdf
            OutputStream out = new FileOutputStream(new File(outFile));
            PdfConverter.getInstance().convert(document, out, options);

            System.err.println("Generate pdf/HelloWorld.pdf with "
                    + (System.currentTimeMillis() - start) + "ms");

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
