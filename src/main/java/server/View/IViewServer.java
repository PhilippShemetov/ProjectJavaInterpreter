/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.View;

import server.Handler.IServerHandler;
import server.Model.IModelServer;

/**
 *
 * @author Vasilisa
 */
public interface IViewServer {
    void send();
    void sendResult();
    public IServerHandler getServerHandler();
}
