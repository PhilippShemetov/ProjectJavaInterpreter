/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Model;

import client.View.IClientView;
import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vasilisa
 */
public class ModelClient {
    int port = 3124;
    InetAddress ip = null;
    
    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;
    
    ArrayList<IClientView> listOfUsers = new ArrayList<>();
    
    public void addClient(IClientView clv){
        listOfUsers.add(clv);
    }

    public ModelClient() {
    }
    
    
    
    void refresh()
    {
        for (IClientView user : listOfUsers) {
            user.update();
        }
    }
    
    public void init(){
        if(cs != null) return;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ModelClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            
            cs = new Socket(ip, port);
            System.out.append("Client start \n");
            
            dis = new DataInputStream(cs.getInputStream());
            dos = new DataOutputStream(cs.getOutputStream());
            
            new Thread(){
                @Override
                public void run() {
                    try {
                        while(true) {
                          int code = dis.readInt();
                          if(code == 1){
                              mes = dis.readUTF();
                              refresh();
                          }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ModelClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }             
            }.start();
            
        }  catch (IOException ex) {
            Logger.getLogger(ModelClient.class.getName()).log(Level.SEVERE, null, ex);
        }
               
           
            
    }
    
    String mes = "";
    
    public String getCodeText(){
        if(cs == null) return "";
        
        return mes;
    }
    
    public void setCodeText(String mes){
        if(cs == null) return ;
        try {
            dos.writeInt(1);
            dos.writeUTF(mes);
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ModelClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUpdateText(String mes){
        if(cs == null) return ;
        try {
            dos.writeInt(1);
            dos.writeUTF(mes);
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ModelClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
