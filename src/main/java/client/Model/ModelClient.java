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
            user.send();
        }
    }
    
    void refreshResult() {
        for (IClientView user : listOfUsers) {
            user.sendResult();
        }
    }
    
    void refreshCodeChanges() {
        for (IClientView user : listOfUsers) {
            user.sendChanges();
        }
    }
    
    void refreshCodeFromUnpatch() {
        for (IClientView user : listOfUsers) {
            user.setOriginalText();
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
                            System.out.println(code);
                            if(code == 1){
                                mes = dis.readUTF();
                                refresh();
                            } else if(code == 2){
                                res = dis.readUTF();
                                refreshResult();
                            } else if(code == 3){
                                codeChanges = dis.readUTF();
                                refreshCodeChanges();
                            } else if (code == 4){
                                mes = dis.readUTF();
                                refreshCodeFromUnpatch();
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
    String res = "";
    String codeChanges = "";
    
    public String getCodeText(){
        if(cs == null) return "";
        
        return mes;
    }
    
    public String getChangesCode(){
        if(cs == null) return "";
        
        return codeChanges;
    }
    
    public String getTextResult(){
        if(cs == null) return "";
        
        return res;
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
    
    public void setTextForInterpreter(String mes){
        if(cs == null) return ;
        try {
            dos.writeInt(2);
            dos.writeUTF(mes);
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ModelClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setTextForPatch(String mes){
        if(cs == null) return ;
        try {
            dos.writeInt(3);
            dos.writeUTF(mes);
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ModelClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setCodeForUnPatch(){
        if(cs == null) return ;
        try {
            dos.writeInt(4);
            //dos.writeUTF(mes);
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ModelClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
