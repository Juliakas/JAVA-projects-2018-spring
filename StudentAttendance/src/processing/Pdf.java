package processing;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Pdf {

    private Document document = new Document();
    private Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private String fileStr;

    public Pdf(String fileStr, int fontSize) {
        try {
            this.fileStr = fileStr;
            File file = new File("src/" + fileStr + ".pdf");
            if(file.exists()) file.delete();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            font.setSize(fontSize);
            addMetaData(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMetaData(Document document) {
        document.addTitle(fileStr);
        document.addSubject("Student Attendance");
        document.addKeywords("Java, PDF");
        document.addAuthor("Attendance");
        document.addCreator("Attendance");
    }
    public void addContent(ArrayList<String> content) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph(
                "List of students present at " + fileStr, new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD)));
        addEmptyLine(preface, 1);
        for(String item : content) {
            preface.add(new Paragraph(item, font));
        }
        document.add(preface);
    }

    public void close() {
        document.close();
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


}
