/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.Constants;
import co.th.aten.football.event.MessageEvent;
import co.th.aten.football.model.MessageModel;
import co.th.aten.football.util.Sound;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.richclient.application.Application;

/**
 *
 * @author Mai
 */
public class MessageManagerBean implements MessageManager {

    private String msg;
    private int type;
    private List<Thread> restoreList;

    public MessageManagerBean() {
        restoreList = new ArrayList<Thread>();
    }

    @Override
    public void showMessage(String msg) {
        // Sound.getInstance().stopPlayError();
        this.msg = msg;
        type = Constants.MSG_NORMAL;
        for (Thread thread : restoreList) {
            if (thread != null) {
                thread.interrupt();
            }
        }
        restoreList.clear();
        Application.instance().getApplicationContext().publishEvent(new MessageEvent(new MessageModel(Constants.MSG_NORMAL, msg)));
    }

    @Override
    public void showMessage(String msg, int wait) {
        //  Sound.getInstance().stopPlayError();
        Sound.getInstance().playClick();
        for (Thread thread : restoreList) {
            if (thread != null) {
                thread.interrupt();
            }
        }
        restoreList.clear();
        Application.instance().getApplicationContext().publishEvent(new MessageEvent(new MessageModel(Constants.MSG_NORMAL, msg)));
        RestoreMessage thread = new RestoreMessage(wait);
        thread.setName("MSG_RESTORE");
        thread.start();
        restoreList.add(thread);
    }

    @Override
    public void showError(String msg) {
//        Sound.getInstance().playContError();
        this.msg = msg;
        type = Constants.MSG_ERROR_BLINK;
        for (Thread thread : restoreList) {
            if (thread != null) {
                thread.interrupt();
            }
        }
        restoreList.clear();
        Application.instance().getApplicationContext().publishEvent(new MessageEvent(new MessageModel(Constants.MSG_ERROR_BLINK, msg)));
    }

    @Override
    public void showError(String msg, int wait) {
//        Sound.getInstance().playError();
        for (Thread thread : restoreList) {
            if (thread != null) {
                thread.interrupt();
            }
        }
        restoreList.clear();
        Application.instance().getApplicationContext().publishEvent(new MessageEvent(new MessageModel(Constants.MSG_ERROR, msg)));

        RestoreMessage thread = new RestoreMessage(wait);
        thread.setName("MSG_RESTORE");
        thread.start();
        restoreList.add(thread);
    }

    @Override
    public void clear() {
        showMessage("");
    }

    @Override
    public String currentMessage() {
        return this.msg;
    }

    class RestoreMessage extends Thread {

        boolean stop;
        int delay;

        public RestoreMessage(int delay) {
            this.delay = delay;
            this.stop = false;
        }

        @Override
        public void interrupt() {
            stop = true;
        }

        @Override
        public void run() {
            try {
                while (!stop && delay > 0) {
                    Thread.sleep(1000);
                    delay--;
                }
                if (!stop) {
                    Application.instance().getApplicationContext().publishEvent(new MessageEvent(new MessageModel(Constants.MSG_NORMAL, currentMessage())));
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MessageManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
