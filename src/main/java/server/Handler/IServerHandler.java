/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Handler;

/**
 *
 * @author Vasilisa
 */
public interface IServerHandler {
    int getCode();
    String getText();
    void setCode(int code);
    void setText(String msg);
}
