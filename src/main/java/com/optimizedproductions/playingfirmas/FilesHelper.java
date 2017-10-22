/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimizedproductions.playingfirmas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herib_000
 */
public class FilesHelper {
    
    private FilesHelper(){}
    
    public static boolean getSomething(String from,  String to){
        if(from==null||to==null)
            return false;
        String pre = "";
        if (is_windows())
            pre = to.substring(0, 2);
        String[] path = getStepByStep(to);
        to=to.substring(to.lastIndexOf('/')+1);

        return makeFile(from, makePath(pre, path),to);
    }
    private static boolean makeFile(String resPath, String fullPath,String nombre){
                    //resPAth es path del recurso, fullPath es dirección a donde se creará el archivo nombre.
        InputStream res = FilesHelper.class.getResourceAsStream(resPath);
        byte[] b = new byte[2048];
        int d;
        FileOutputStream file;
        fullPath+='/'+nombre;
        System.out.println( fullPath );
        try {
            if (!new File(fullPath).exists()) {
                file = new FileOutputStream(fullPath);
                d = res.read(b);
                while (d != -1) {
                    file.write(b, 0, d);
                    d = res.read(b);
                }
                file.close();
            }
            return true;
        }catch(Exception e){
                System.out.println(e.getMessage());
                return false;
        }
    }
    public static String[] getStepByStep(String g){
        LinkedList<String> aux=new LinkedList<String>();
        
        if( is_windows() )
            g=g.substring(2, g.length());
        g=g.substring(1, g.length());

        String util;
        int i;
        while((i=g.indexOf('/'))!=-1)
        {
            util=g.substring(0, i);
            g=g.substring(i+1);
            aux.offer(util);
        }
        String[] gg=new String[aux.size()];

        for(int ii=0;ii<gg.length;ii++)
            gg[ii]=(String)aux.poll();
        return gg;
    }

    public static String makePath(String prefix, String[] path){
        String fullPath=prefix;
        for(int i=0;i<path.length;i++){
            fullPath+='/'+path[i];
            if(!new File(fullPath).exists())
                new File(fullPath).mkdir();
            //System.out.println(fullPath);
        }
        return fullPath;
    }
    
    public static boolean copy_file(String from, String to){
        boolean success = true;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try{
            File f = new File(to);      f.createNewFile();
            fis = new FileInputStream(from);
            fos = new FileOutputStream(f, false);
            
            byte[] buffer = new byte[1024];
            int noOfBytes = 0;
            
            System.out.println("Creating Group File From Base.");
            
            while ((noOfBytes = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, noOfBytes);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesHelper.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        } catch (IOException ex) {
            Logger.getLogger(FilesHelper.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }finally{
            try{
                if( fis != null )   fis.close();
                if( fos != null )   fos.close();
            }catch(IOException e){
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }
    
    public static boolean is_windows(){
        return System.getProperty("os.name").contains( "indows" );
    }
    
}
