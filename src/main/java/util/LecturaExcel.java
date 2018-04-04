package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LecturaExcel {

    public static ArrayList<String> listado () {
        FileInputStream file = null;
        ArrayList<String> a = null;
        try {
            String archivo = "componente.xlsx";
            String hoja = "Hoja1";
            int contador = 0;
            a = new ArrayList<>();
            file = new FileInputStream(new File(archivo));
            // leer archivo excel
            XSSFWorkbook worbook = new XSSFWorkbook(file);
            //obtener la hoja que se va leer
            XSSFSheet sheet = worbook.getSheetAt(0);
            //obtener todas las filas de la hoja excel
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            // se recorre cada fila hasta el final
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                //se obtiene las celdas por fila
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                //se recorre cada celda
                while (cellIterator.hasNext()) {
                    contador = contador + 1;
                    cell = cellIterator.next();
                    a.add(cell.getStringCellValue());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LecturaExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LecturaExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(LecturaExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return a;
    }
}
