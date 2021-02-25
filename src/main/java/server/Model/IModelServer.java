/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import server.Handler.IServerHandler;
import server.View.IViewServer;

/**
 *
 * @author Vasilisa
 */
public interface IModelServer {
    public void setText(String s,IServerHandler sh);
    void setResult(String s);
    String getText();
    String getResult();
    void addUser(IViewServer vs,IServerHandler sh);
    void removeUser();
}
