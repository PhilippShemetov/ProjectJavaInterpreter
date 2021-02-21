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
public class ViewServer implements IViewServer{
    
    IServerHandler srv;
    IModelServer m;
    
    public ViewServer(IServerHandler _srv,IModelServer _m) {
        m = _m;
        srv = _srv;
        
        start();
        m.addUser(this);
    }
    
    void start()
    {
        //Controller p = this;
        new Thread(){
            @Override
            public void run() {
                int code = -2;
                
                while(code != -1)
                {
                    code = srv.getCode();
                    if(code == 1)
                    {
                        //srv.setCode(1);
                        m.setText(srv.getText());
                    }
                    
                }
            }
        }.start();
        
    }

    
    @Override
    public void refresh() {
        srv.setCode(1);
        srv.setText(m.getText());
    }
    
}
