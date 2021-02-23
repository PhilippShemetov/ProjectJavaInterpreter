/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import server.View.IViewServer;

/**
 *
 * @author Vasilisa
 */
public interface IModelServer {
    void setText(String s);
    void setResult(String s);
    String getText();
    String getResult();
    void addUser(IViewServer vs);
    void removeUser();
}
