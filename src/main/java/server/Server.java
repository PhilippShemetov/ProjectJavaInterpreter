/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Handler.IServerHandler;
import server.Handler.ServerHandler;
import server.Model.IModelServer;
import server.Model.ModelServer;
import server.View.IViewServer;
import server.View.ViewServer;

/**
 *
 * @author Vasilisa
 */
public class Server {
    int port = 3124;
    InetAddress ip = null;
    
    
    public void serverStart(){
        
        
        //Создаем модель сервера, который позволит манупулировать с данными 
        //клиента
        IModelServer m = new ModelServer();
        ServerSocket ss;
        Socket cs;
        
        DataInputStream dis;
        DataOutputStream dos;
        
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ss = new ServerSocket(port, 0, ip);
            System.out.println("Server start");
            while(true)
            {
                cs = ss.accept(); //Ждем подключения
                System.out.println("Has connect");
                
                
                //Создаем класс обработчик для команд
                IServerHandler srv = new ServerHandler(cs);
                
                //Создаем класс для обработки команд и отправки клиенту, где 
                //принимаем параметры: обработчик и модель сервера
                IViewServer viewServer = new ViewServer(srv, m);
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    public static void main(String[] args) {
        new Server().serverStart();
    }
}
