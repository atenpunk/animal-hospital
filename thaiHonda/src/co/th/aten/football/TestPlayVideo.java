/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football;

import java.io.IOException;

/**
 *
 * @author Atenpunk
 */
public class TestPlayVideo {

    public static void main(String[] args) {
        try {
//            Runtime.getRuntime().exec(System.getProperty("user.dir") + "/VLC/vlc.exe video/Complicated.avi");
            Runtime.getRuntime().exec("C:/Program Files/The KMPlayer/KMPlayer.exe video\\Complicated.avi");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}