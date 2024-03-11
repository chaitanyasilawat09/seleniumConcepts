package POM;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

public class Read_PDF_File {

    static String[] lines;

//    Import   <groupId>org.apache.pdfbox</groupId>
    public static void main(String[] args) throws Exception {
        PDDocument document = PDDocument.load(new File("3-6 Years - 10-Oct.pdf"));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        lines = text.split(System.getProperty("line.separator"));
        for(String s : lines){
            if(s.contains("Email: "))

            System.out.println(s.replace("Email: ",""));
        }
//        System.out.println(lines[0].toString());
        document.close();


    }
}