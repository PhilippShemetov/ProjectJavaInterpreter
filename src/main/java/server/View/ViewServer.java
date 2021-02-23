/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.View;

import java.io.StringWriter;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.python.util.PythonInterpreter;
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
                    switch(code){
                        case 1:
                            m.setText(srv.getText());
                            break;
                        case 2:
                            try (PythonInterpreter pyInterp = new PythonInterpreter()) {
                                StringWriter output = new StringWriter();
                                pyInterp.setOut(output);
                                String hello = srv.getText();
                                pyInterp.exec(hello);
                                m.setResult(output.toString());
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    
                }
            }
        }.start();
        
    }

    
    @Override
    public void send() {
        srv.setData(m.getText(),1);
    }
    
    @Override
    public void sendResult() {
        srv.setData(m.getResult(),2);
    }
    
}
