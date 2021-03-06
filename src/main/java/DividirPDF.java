import com.opencsv.CSVReader;
import com.itextpdf.text.DocumentException;

import java.io.*;

public class DividirPDF {
    // Global Variable
    private static uPdfLib pdf = new uPdfLib();

    public static void main(String[] args) throws IOException, DocumentException {
        System.setProperty("file.encoding", "UTF-8");
        showPdf("D:\\");
    }

    private static void showPdf(final String pathDirectory) throws IOException {
        File inputDirectory = new File(pathDirectory);
        File[] files =  inputDirectory.listFiles();
        if(files != null) {
            for (final File pdfFile : files) {

                String pdfFilename = pdfFile.getAbsolutePath();
                if (pdfFile.isDirectory() || (!pdfFilename.contains(".pdf")) ) {
                    continue;
                }
                String texto = pdf.parsePdf(pdfFilename, 1);

                // Generado por el Legix
                if (texto.contains("Versión del Sistema:")) {
                    // dni
                    int posDni = texto.indexOf("D.N.I.:");
                    String dni = texto.substring(posDni + 8, posDni + 16);
                    // usuario
                    int posUser = texto.indexOf("Usuario del Sistema:");
                    int posFin = texto.indexOf("Pag. N");
                    String user = texto.substring(posUser + 21, posFin - 1);
                    // fecha y hora
                    int posFecha = texto.indexOf("Fecha y Hora de Impresión:");
                    int posFFin = texto.indexOf("Versión del Sistema:");
                    String fecha = texto.substring(posFecha + 27, posFFin - 1);
                    // Informe escalafonario
                    int posNum = texto.indexOf("ESCALAFONARIO N");
                    int posFNum = texto.indexOf("-UGEL N");
                    String informe = texto.substring(posNum + 17, posFNum + 11);
                    // Numero de hojas
                    int posHojas = texto.indexOf("Pag. N");
                    String hojas = texto.substring(posHojas);
                    int posFHojas = hojas.indexOf(" de");
                    String numHojas = hojas.substring(posFHojas + 3);

                    String line = dni + "\t" + user + "\t" + fecha + "\t" + informe + "\t" + numHojas;
                    System.out.println(line);
                }
            }
        }
    }

    static void SampleSplitPdfFile(String csvFile, String inputDirectory, String outputDirectory) throws IOException, DocumentException {
        CSVReader reader;

        reader = new CSVReader(new FileReader(csvFile));
        String[] line;
        while ((line = reader.readNext()) != null) {
            System.out.println("Line [File= " + line[0] + ", R.D.= " + line[1] + " , Begin=" + line[2] + " , Fin=" + line[3] + "]");

            String name = "00000" + line[1];
            int l = name.length();
            String inFile = inputDirectory + line[0];

            String outFile = outputDirectory + "2018_" + name.substring(l - 5) + ".pdf";

            int begin = Integer.parseInt(line[2]);
            int end = Integer.parseInt(line[3]);

            pdf.splitPdfFile(inFile, outFile, begin, end);
        }
    }

    static void SampleReversePdfFile(String csvFile, String inputDirectory, String outputDirectory) throws IOException, DocumentException {
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