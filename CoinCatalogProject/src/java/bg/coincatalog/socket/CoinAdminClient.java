
package bg.coincatalog.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class CoinAdminClient {

public static void main(String[] args) throws Exception{
    String host = "localhost";
    int port =5050;
    
    try(Socket s = new Socket(host,port);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8")); 
                PrintWriter out = new PrintWriter(s.getOutputStream(),true);
                   BufferedReader console = new BufferedReader(new InputStreamReader(System.in,"UTF-8"))){
        
        System.out.println(in.readLine());
        while(true){
            System.out.print(">");
            String cmd = console.readLine();
            if(cmd.trim().toUpperCase().startsWith("IMPORT")){
                String[] parts = cmd.trim().split("\\s+");
    String fileName = parts.length > 1 ? parts[1] : "coins.csv";
                out.println("IMPORT");
                try (java.io.BufferedReader fr = new java.io.BufferedReader(
            new java.io.InputStreamReader(new java.io.FileInputStream(fileName), "UTF-8"))){
                    
                    String fileLine;
                    while((fileLine=fr.readLine())!=null){
                        out.println(fileLine);
                    }
                }
                out.println("END");
                   String resp;
    while ((resp = in.readLine()) != null) {
        System.out.println(resp);
        if (resp.equals("END") || resp.startsWith("ERR")) break;
    }
    continue;
            }
            if(cmd==null) break;
                            if("EXPORT".equalsIgnoreCase(cmd.trim())){
                                out.println(cmd);
                    java.io.BufferedWriter bw =new java.io.BufferedWriter(
                   new java.io.OutputStreamWriter(new java.io.FileOutputStream("coins.csv"), "UTF-8")
                   ); 
                    String line;
                    while((line=in.readLine())!=null){
                        if(line.equals("END"))break;
                        bw.write(line);
                        bw.newLine();
                    }
                    bw.close();
                     System.out.println("Saved to coins.csv");
                   continue;
                }
            
            out.println(cmd);  

            String line;
            while((line=in.readLine())!=null){
              System.out.println(line);
                    if (line.equals("END") || line.equals("BYE") || line.startsWith("ERR")) break;
                }
                if ("QUIT".equalsIgnoreCase(cmd.trim())) break;

            }
        }
    
}    
}
