/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import java.util.ArrayList;
import server.View.IViewServer;

/**
 *
 * @author Vasilisa
 */
public class ModelServer implements IModelServer{

    String mes = "";
    
    ArrayList<IViewServer> listOfUsers = new ArrayList<>();
    
    void update(){
        for(IViewServer user : listOfUsers){
            user.refresh();
        }
    }
    
    @Override
    public void setText(String s) {
         mes = s;
         update();
    }

    @Override
    public String getText() {
        return mes;
    }

    @Override
    public void addUser(IViewServer vs) {
        listOfUsers.add(vs);
        update();
    }

    @Override
    public void removeUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
