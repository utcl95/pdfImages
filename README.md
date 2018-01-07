1. Archivo PDF: "R.D. N° 0764-0858.pdf"
   El archivo tiene resoluciones de la 764 a la 858, el archivo podiar tener paginas en vertical y tambien en horizontal.

Funcion: splitPdfFile(String inPDF, String outPDF, int startPage, int endPage)

Divide (extrae) el archivo desde una pagina de inicio hasta una pagina final y copia a un archivo, si hay paginas en horizontal gira la pagina y todas las copia en vertical, ups el inicio y fin de cada documentos lo tienes que hacer manual, un archivito y lo procesas :/)

Archivo	        Resolucion	Inicio	fin
rd_00001-01000	   1	      1	      4
rd_00001-01000	   2	      5	      6
rd_00001-01000	   3	      7	      8

2. Habia archivos PDF escaneados al reves, cientos de estos, habia que "reverse" el archivo, la ultima pagina a la primera...etc, busque un software o algo para hacer esto y no lo encontre, y tuve que crear mi propio programa.

void reversePdfFile(String inPDF, String outPDF)

"Reverse" un PDF file, si hay paginas en horizontal, las pasa a vertical.

3. Habia que imprimir varias resoluciones, pero, cuando hay una sola impresora y envias varias hojas y otros envian impresiones, imprime algunas tuyas, luego la de otros, otras tuyas, luego de otros, etc, queria enviar todos mis documentos en un solo archivo, pero, habia documentos con hojas pares e impares, asi que este pequeño programa a los documentos con hojas impares les añade una hoja en blanco y une todos los archivos en uno solo y asi imprimo varios PDF como si fueran uno solo :>)

 doMergeToPrint(List<InputStream> list, String outFile)
 
 








