/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vasilisa
 */
public class ServerHandler implements IServerHandler{

    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;
    
    public ServerHandler(Socket _cs) {
        try {
            cs = _cs;
            dis = new DataInputStream(cs.getInputStream());
            dos = new DataOutputStream(cs.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public int getCode(){
        try {
            return dis.readInt();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    @Override
    public String getText(){
        try {
            return dis.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    @Override
    public void setData(String msg, int code){
        new Thread()
        {
            @Override
            public void run() {
                try {
                    dos.writeInt(code);
                    dos.writeUTF(msg);
                    dos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    public void setCode(int code){
        new Thread()
        {
            @Override
            public void run() {
                try {
                    dos.writeInt(code);
                    dos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
}
