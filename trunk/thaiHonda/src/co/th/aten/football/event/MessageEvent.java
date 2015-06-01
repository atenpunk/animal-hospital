/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.event;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Mai
 */
public class MessageEvent extends ApplicationEvent {

    public MessageEvent(Object source) {
        super(source);
    }
}
