/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.event;

import org.springframework.context.ApplicationEvent;


/**
 *
 * @author Aten
 */
public class LogoutEvent extends ApplicationEvent{

    public LogoutEvent(Object obj) {
        super(obj);

    }

    

}
