/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CoinAdminServer {
public static void main(String[] args) throws Exception{
    int port =5050;
    
    ExecutorService pool =Executors.newFixedThreadPool(10);
    System.out.println("CoinAdminServer listening on port " + port);
    try(ServerSocket server = new ServerSocket(port)){
        while(true){
            Socket client = server.accept();
            System.out.println("Client connected: " + client.getInetAddress());
            
           pool.execute(new ClientHandler(client));
        }
    }

}    
}
