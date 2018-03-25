import com.opencsv.CSVReader;
import com.itextpdf.text.DocumentException;

import java.io.*;

public class DividirPDF {
    // Global Variable
    private static uPdfLib pdf = new uPdfLib();

    public static void main(String[] args) throws IOException, DocumentException {
        System.setProperty("file.encoding", "UTF-8");
        showPdf();
    }

    private static void showPdf() throws IOException {
        String csvFile = "D:\\UGEL05\\_23032018\\repositorio\\listado.csv.txt";

        String inputDirectory = "D:\\UGEL05\\_23032018\\repositorio\\";

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "8859_1"));

        String line;

        while ((line = in.readLine()) != null) {
            String pdfFile = inputDirectory + line;
            String texto = pdf.parsePdf(pdfFile, 1);

            // Generado por el Legix
            if(texto.contains("Versión del Sistema:")) {
                // dni
                int posDni = texto.indexOf("D.N.I.:");
                String dni = texto.substring(posDni+8, posDni+16);
                // usuario
                int posUser = texto.indexOf("Usuario del Sistema:");
                int posFin = texto.indexOf("Pag. N");
                String user = texto.substring(posUser+21, posFin-1);
                // fecha y hora
                int posFecha = texto.indexOf("Fecha y Hora de Impresión:");
                int posFFin = texto.indexOf("Versión del Sistema:");
                String fecha = texto.substring(posFecha+27, posFFin-1);
                // Informe escalafonario
                int posNum = texto.indexOf("ESCALAFONARIO N");
                int posFNum = texto.indexOf("-UGEL N");
                String informe = texto.substring(posNum+17, posFNum+11);

                String line0 = dni + "," + user + "," + fecha + "," + informe;
                System.out.println(line0);
            }
            texto = null;
        }
    }

    static void SampleSplitPdfFile() throws IOException, DocumentException {
        String csvFile = "F:\\_20180316_\\2018\\2018.csv";

        String inputDirectory = "F:\\_20180316_\\2018\\";
        String outputDirectory = "F:\\_20180316_\\2018\\output\\";

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