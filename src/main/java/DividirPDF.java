import com.opencsv.CSVReader;
import com.itextpdf.text.DocumentException;

import java.io.FileReader;
import java.io.IOException;

public class DividirPDF {
    // Global Variable
    public static uPdfLib pdf = new uPdfLib();

    public static void main(String[] args) throws IOException, DocumentException {
        SampleReversePdfFile();
    }

    void SampleSplitPdfFile() throws IOException, DocumentException {
        String csvFile = "D:\\invertir.csv";

        String inputDirectory = "D:\\Resoluciones_pdf\\2013\\invertir\\";
        String outputDirectory = "D:\\Resoluciones_pdf\\2013\\invertido\\";

        CSVReader reader;

        reader = new CSVReader(new FileReader(csvFile));
        String[] line;
        while ((line = reader.readNext()) != null) {
            System.out.println("Line [File= " + line[0] + ", R.D.= " + line[1] + " , Begin=" + line[2] + " , Fin=" + line[3] + "]");

            String name = "00000" + line[1];
            int l = name.length();
            String inFile = inputDirectory + line[0] + ".pdf";

            String outFile = outputDirectory + "2015_" + name.substring(l - 5) + ".pdf";

            int begin = Integer.parseInt(line[2]);
            int end = Integer.parseInt(line[3]);

            pdf.splitPdfFile(inFile, outFile, begin, end);
        }

    }

    static void SampleReversePdfFile() throws IOException, DocumentException {
        String csvFile = "D:\\invertir.csv";

        String inputDirectory = "D:\\Resoluciones_pdf\\2013\\invertir\\";
        String outputDirectory = "D:\\Resoluciones_pdf\\2013\\invertido\\";

        CSVReader reader;

        reader = new CSVReader(new FileReader(csvFile));
        String[] line;
        while ((line = reader.readNext()) != null) {
            System.out.println("Line File= " + line[0]);

            String inFile = inputDirectory + line[0];
            String outFile = outputDirectory + line[0];

            pdf.reversePdfFile(inFile, outFile);
        }
    }
}