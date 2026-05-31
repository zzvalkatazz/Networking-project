/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadjavaimage;

import java.io.*;
import java.net.URL;


public class DownloadJavaImage {
   public static final int CHUNK =8192;
   
   public static void main(String[] args) throws IOException{
         try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
             System.out.print("Enter image URL:");
             String urlString =console.readLine();
             
             if(urlString == null || urlString.trim().isEmpty()){
                 System.out.println("No URL entered");
                 return;
             }
             URL url = new URL(urlString.trim());
             File uploadDir = new File(System.getProperty("user.dir"), "uploaded-images");
             if(!uploadDir.exists()) uploadDir.mkdirs();
             
             String path = url.getPath();
             String outputFileName = new File(path).getName();
             if (outputFileName.isEmpty()) outputFileName = "downloaded_image.jpg";
             
             File outFile = new File(uploadDir,outputFileName);
             
             System.out.println("Downloading from: " + urlString);
             System.out.println("Saving to: " + outFile.getAbsolutePath());
            
                    
             try(InputStream in = url.openStream();
             FileOutputStream out =new FileOutputStream(outFile)){
                 
                 byte[] buffer = new byte[CHUNK];
                 int length;
                 
                   while ((length = in.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                }
            }

            System.out.println("Done!");
             
         }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
   }
}
