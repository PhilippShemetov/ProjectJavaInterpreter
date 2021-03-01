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
    public void setCodeToUnpatch(String s);
    void setResult(String s);
    void setChanges(String s);
    String getText();
    String getResult();
    String getChangesCode();
    void addUser(IViewServer vs,IServerHandler sh);
}
