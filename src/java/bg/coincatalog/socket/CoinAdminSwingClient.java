/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class CoinAdminSwingClient extends JFrame{
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    private JTextArea logArea;
    private JTextField commandField;
    private JButton sendButton;
    
 public CoinAdminSwingClient(){
     setTitle("Coin Catalog - Admin Panel");
     setSize(600, 400);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     
     logArea = new JTextArea();
     logArea.setEditable(false);
     logArea.setBackground(Color.BLACK);
     logArea.setForeground(Color.GREEN);
     logArea.setFont(new Font("Monospaced",Font.BOLD,14));
     JScrollPane scrollPane = new JScrollPane(logArea);
     
     commandField = new JTextField();
     commandField.setFont(new Font("SansSerif",Font.PLAIN,16));
     sendButton = new JButton("Изпрати");
     
     JPanel bottomPanel = new JPanel(new BorderLayout());
     bottomPanel.add(commandField, BorderLayout.CENTER);
     bottomPanel.add(sendButton, BorderLayout.EAST);
     
     add(scrollPane, BorderLayout.CENTER);
     add(bottomPanel, BorderLayout.SOUTH);
     
     sendButton.addActionListener(e -> sendCommand());
     commandField.addActionListener(e -> sendCommand());
     
     connectToServer();
 }
 
 private void connectToServer(){
 try{
     socket = new Socket("localhost",5050);
     in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
     out = new PrintWriter(socket.getOutputStream(),true);

     logArea.append("Connected to server on port 5050.\n");
     logArea.append(in.readLine()+"\n");     
 } catch(Exception ex){
     JOptionPane.showInputDialog(this,"Грешка при свързване:" + ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE );
     System.exit(1);
 }    
 }
 private void sendCommand(){
     String cmd = commandField.getText().trim();
     if(cmd.isEmpty()) return;
     
     out.println(cmd);
     logArea.append(">" + cmd + "\n");
     commandField.setText("");
     
     new Thread(() ->{
         try{
             String line;
             while((line = in.readLine()) !=null){
                logArea.append(line+"\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
                if(line.equals("END") || line.equals("BYE") || line.startsWith("ERR")) break;
             }
         }catch(Exception ex){
             logArea.append("Грешка при четене от сървъра.\n");
         }
     }).start();
 }
 public static void main(String[] args){
     SwingUtilities.invokeLater(()->{
         new CoinAdminSwingClient().setVisible(true);
     });
 }
}
