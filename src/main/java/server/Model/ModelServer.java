/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import java.util.ArrayList;
import server.Handler.IServerHandler;
import server.View.IViewServer;

/**
 *
 * @author Vasilisa
 */
public class ModelServer implements IModelServer{

    String mes = "";
    
    String res = "";
    
    String changesText = "";
    
    ArrayList<IViewServer> listOfUsers = new ArrayList<>();
    
    void update(IServerHandler sh){
        for(IViewServer user : listOfUsers){
            if(user.getServerHandler() == sh){
                continue;
            }
            user.send();
        }
    }
    
    void updateCodeToUnpatch(){
        for(IViewServer user : listOfUsers){
            user.sendOriginalText();
        }
    }
    
    void updateResult(){
        for(IViewServer user : listOfUsers){
            user.sendResult();
        }
    }
    
    void updateChanges(){
        for(IViewServer user : listOfUsers){
            user.sendChangeCode();
        }
    }
    
    void udpateChanges(){
        for(IViewServer user : listOfUsers){
            user.sendResult();
        }
    }
    
    @Override
    public void setText(String s,IServerHandler sh) {
         mes = s;
         update(sh);
    }
    
    @Override
    public void setCodeToUnpatch(String s) {
         mes = s;
         updateCodeToUnpatch();
    }
    
    @Override
    public void setResult(String s) {
         res = s;
         updateResult();
    }

    @Override
    public String getText() {
        return mes;
    }
    
    @Override
    public String getResult() {
        return res;
    }
    
    @Override
    public String getChangesCode() {
        return changesText;
    }

    @Override
    public void addUser(IViewServer vs,IServerHandler sh) {
        listOfUsers.add(vs);
        update(sh);
    }

    @Override
    public void removeUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setChanges(String s) {
        changesText = s;
        updateChanges();
    }
    
    
    
}
