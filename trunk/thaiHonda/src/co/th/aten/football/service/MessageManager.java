/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football.service;

/**
 *
 * @author Mai
 */
public interface MessageManager {

    public void showMessage(String msg);
    public void showMessage(String msg,int wait);

    public void showError(String msg);
    public void showError(String msg,int wait);

    public void clear();


    public String currentMessage();
    
}
