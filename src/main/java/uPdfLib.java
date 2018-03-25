import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.*;
import java.util.List;

public class uPdfLib {
    void splitPdfFile(String inPDF, String outPDF, int startPage, int endPage) throws IOException, DocumentException {
        Document document = new Document();
        PdfReader pdfReader = new PdfReader(inPDF);

        int totalPages = pdfReader.getNumberOfPages();
        if (endPage < startPage) endPage = totalPages;

        if (startPage > endPage || endPage > totalPages) {
            System.out.println("Page numbers error...");
        } else {
            PdfCopy copyPdfFile = new PdfCopy(document, new FileOutputStream(outPDF));
            document.open();

            while (startPage <= endPage) {
                float h = pdfReader.getPageSize(startPage).getHeight();
                float w = pdfReader.getPageSize(startPage).getWidth();
                if (w > h) {
                    PdfDictionary pageDict = pdfReader.getPageN(startPage);
                    pageDict.put(PdfName.ROTATE, new PdfNumber(90));
                }
                copyPdfFile.addPage(copyPdfFile.getImportedPage(pdfReader, startPage));
                startPage++;
            }
            document.close();
        }
    }

    void reversePdfFile(String inPDF, String outPDF) throws IOException, DocumentException {
        Document document = new Document();
        PdfReader pdfReader = new PdfReader(inPDF);

        PdfCopy copyPdfFile = new PdfCopy(document, new FileOutputStream(outPDF));

        int totalPages = pdfReader.getNumberOfPages();

        document.open();

        for (int i = totalPages; i > 0; i--) {
            float h = pdfReader.getPageSize(i).getHeight();
            float w = pdfReader.getPageSize(i).getWidth();
            if (w > h) {
                PdfDictionary pageDict = pdfReader.getPageN(i);
                pageDict.put(PdfName.ROTATE, new PdfNumber(90));
            }
            copyPdfFile.addPage(copyPdfFile.getImportedPage(pdfReader, i));
        }
        document.close();
    }

    void doMergeToPrint(List<InputStream> list, String outFile) throws DocumentException, IOException {
        OutputStream out = new FileOutputStream(new File(outFile));
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }
        out.flush();
        document.close();
        out.close();
    }

    String parsePdf(String pdf, Integer pages) throws IOException {
        PdfReader reader = new PdfReader(pdf);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        TextExtractionStrategy strategy;

        String texto = null;
        for (int i = 1; i <= pages; i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            texto = strategy.getResultantText();
        }
        reader.close();
        return texto;

    }
}
