/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimizedproductions.playingfirmas;

import static com.optimizedproductions.playingfirmas.FilesHelper.makePath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author herib_000
 */
public class PoiHelper {
    
    private static final String MY_FILE_PATH = "C:\\Tools\\ImagesData.xls";
    public static final String HALF_CELL_INDEX = "A";
    
    
    public static void saveIntent(String result, float percent, byte[] image1, byte[] image2, byte[] image3){
        
        String pre = "";
        if (FilesHelper.is_windows())
            pre = MY_FILE_PATH.substring(0, 2);
        String[] path = FilesHelper.getStepByStep(MY_FILE_PATH);
        makePath(pre, path);
        
        try {
            File p_f = new File( MY_FILE_PATH );
            FileInputStream file = null;
            HSSFWorkbook workbook;
            HSSFSheet current;
            
            if( !p_f.exists() ){
                workbook = new HSSFWorkbook();
                current = workbook.createSheet("results");
                
                Row r = current.createRow(0);
                r.createCell(0).setCellValue("Index");
                r.createCell(1).setCellValue("Resultado");
                r.createCell(2).setCellValue("Percentage");
                r.createCell(3).setCellValue("______________Image Template For Image 1______________");
                r.createCell(4).setCellValue("______________Image Template For Image 2______________");
                r.createCell(5).setCellValue("______________Relation Matrix Helper______________");
                
                FileOutputStream out = new FileOutputStream(new File( MY_FILE_PATH ));
                workbook.write(out);
                out.close();
            }            

            file = new FileInputStream( p_f );
            workbook = new HSSFWorkbook(file);
            current = workbook.getSheetAt(0);
            int next_row = current.getLastRowNum() + 1;
            
            Row r = current.createRow( next_row );
            Cell tmp = null;
            
            r.createCell(0).setCellValue( (int)(next_row / 4.0) );
            tmp = r.createCell(1);
            tmp.setCellValue( result );
            
            r.createCell(2).setCellValue( percent + "%" );
            
            int temp_row = next_row;
            current.createRow(++next_row);
            current.createRow(++next_row);
            current.createRow(++next_row);
            current.createRow(++next_row);

            
            current.autoSizeColumn(1);
            current.autoSizeColumn(2);
            current.autoSizeColumn(3);
            current.autoSizeColumn(4);
            current.autoSizeColumn(5);
            
            insertImage(workbook, current, temp_row, 3, image1);
            insertImage(workbook, current, temp_row, 4, image2);
            insertImage(workbook, current, temp_row, 5, image3);
            
            if( file != null )
                file.close();
            
            FileOutputStream out = new FileOutputStream(new File( MY_FILE_PATH ));
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PoiHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PoiHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static Cell getCell(final HSSFSheet sheet, final String reference){
        CellReference cr = new CellReference(reference);
        Row row = sheet.getRow(cr.getRow());
        Cell cell = row.getCell(cr.getCol());
        return cell;
    }
    
    public static void insertImage( HSSFWorkbook workbook , HSSFSheet current, int next_row, int col , byte[] image){
            int my_picture_id = workbook.addPicture(image, Workbook.PICTURE_TYPE_JPEG);
            
        /* Create the drawing container */
        HSSFPatriarch drawing = current.createDrawingPatriarch();
        /* Create an anchor point */
        ClientAnchor my_anchor = new HSSFClientAnchor();
        /* Define top left corner, and we can resize picture suitable from there */
        my_anchor.setCol1(col);
        my_anchor.setRow1(next_row); 
        /* Invoke createPicture and pass the anchor point and ID */
        HSSFPicture  my_picture = drawing.createPicture(my_anchor, my_picture_id);
        /* Call resize method, which resizes the image */
        my_picture.resize();    
        my_picture.resize(0.2f, 0.2f);
    }
}
