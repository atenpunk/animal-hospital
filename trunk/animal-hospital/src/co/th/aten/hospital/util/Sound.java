/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.util;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 *
 * @author Mai
 */
public class Sound {

//    private boolean canPlay = true;
//    private boolean canPlayError = true;
//    private boolean playError = false;
    private static Sound instance;
//    private SwingWorker playErrorWorker;
    AudioInputStream soundClick = null;
    Clip clipClick = null;
    DataLine.Info infoClick = null;
    File soundFileClick = null;
    private AePlayWave player;

    public static Sound getInstance() {
        if (instance == null) {
            instance = new Sound();
        }
        return instance;
    }

    public Sound() {
        initSounds();

    }

    private void initSounds() {
//        try {
//            soundFileClick = new File("sounds/click.wav");
//            soundClick = AudioSystem.getAudioInputStream(soundFileClick);
//            // load the sound into memory (a Clip)
//            infoClick = new DataLine.Info(Clip.class, soundClick.getFormat());
//            clipClick = (Clip) AudioSystem.getLine(infoClick);
//            clipClick.open(soundClick);
//        } catch (LineUnavailableException ex) {
//            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedAudioFileException ex) {
//            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void playInfected() {
        new AePlayWave("sounds/infected.wav").start();
    }


    public void playDing() {
        new AePlayWave("sounds/ding.wav").start();
    }

    public void playNotify() {
        new AePlayWave("sounds/notify.wav").start();
    }

    public void playSearchFail() {
        new AePlayWave("sounds/search_fail.wav").start();
    }

    public void playClick() {
//        if (player != null && player.isAlive()) {
//            System.out.println("Sound is playing , ignore new one.");
//            return;
//        }
//        player = new AePlayWave("sounds/click.wav");
//        player.start();
        new AePlayWave("sounds/click.wav").start();

        //        new SwingWorker() {
//
//            @Override
//            protected Object doInBackground() throws Exception {
//                if (canPlay) {
//                    canPlay = false;
////                    AudioInputStream sound = null;
////                    Clip clip = null;
////                    DataLine.Info info = null;
////                    File soundFile = null;
//                    try {
////                        soundFile = new File("sounds/click.wav");
////                        sound = AudioSystem.getAudioInputStream(soundFile);
////                        // load the sound into memory (a Clip)
////                        info = new DataLine.Info(Clip.class, sound.getFormat());
////                        clip = (Clip) AudioSystem.getLine(info);
////                        clip.open(sound);
//
////                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
////                        double gain = 1.0D; // number between 0 and 1 (loudest)
////                        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
////                        gainControl.setValue(dB);
////                        clip.start();
//                        clipClick.start();
//
////                    } catch (LineUnavailableException ex) {
////                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
////                    } catch (UnsupportedAudioFileException ex) {
////                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
////                    } catch (IOException ex) {
////                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } finally {
////                        try {
//                            clipClick.setFramePosition(0);
////                            clip.close();
////                            sound.close();
////                            clip.close();
////                            clip = null;
////                            info = null;
////                            sound = null;
////                            soundFile = null;
////                        } catch (IOException ex) {
////                            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
////                        }
//                        canPlay = true;
////                        clipClick.stop();
//                    }
//                }
//                return null;
//            }
//        }.execute();
    }

    public void playError() {
//        if (canPlayError) {
//        if (player != null && player.isAlive()) {
//            System.out.println("Sound is playing , ignore new one.");
//            return;
//        }
//        player = new AePlayWave("sounds/error.wav");
//        player.start();
        new AePlayWave("sounds/error.wav").start();
//        }
//        new SwingWorker() {
//
//            @Override
//            protected Object doInBackground() throws Exception {
//                if (canPlay) {
//                    canPlay = false;
//                    AudioInputStream sound = null;
//                    Clip clip = null;
//                    DataLine.Info info = null;
//                    File soundFile = null;
//                    try {
//                        soundFile = new File("sounds/error.wav");
//                        sound = AudioSystem.getAudioInputStream(soundFile);
//                        // load the sound into memory (a Clip)
//                        info = new DataLine.Info(Clip.class, sound.getFormat());
//                        clip = (Clip) AudioSystem.getLine(info);
//                        clip.open(sound);
//                        clip.start();
//                    } catch (LineUnavailableException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (UnsupportedAudioFileException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } finally {
//                        try {
//                            clip.close();
//                            sound.close();
//                            clip = null;
//                            info = null;
//                            sound = null;
//                            soundFile = null;
//                        } catch (IOException ex) {
//                            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        canPlay = true;
//                    }
//
//                }
//                return null;
//            }
//        }.execute();
    }

//    public void playContError() {
//        if (canPlayError) {
//            canPlayError = false;
//            new AePlayWave("sounds/error.wav").start();
//            playErrorWorker = new SwingWorker() {
//
//                @Override
//                protected Object doInBackground() {
//                    AudioInputStream sound = null;
//                    Clip clip = null;
//                    DataLine.Info info = null;
//                    File soundFile = null;
//                    try {
//                        soundFile = new File("sounds/error.wav");
//                        sound = AudioSystem.getAudioInputStream(soundFile);
//                        // load the sound into memory (a Clip)
//                        info = new DataLine.Info(Clip.class, sound.getFormat());
//                        clip = (Clip) AudioSystem.getLine(info);
//                        clip.open(sound);
//                        while (!isCancelled()) {
////                            System.out.println("play error sound="+playError);
//                            try {
//                                clip.start();
//                                Thread.sleep(1000);
//                                clip.stop();
//                                clip.setFramePosition(0);
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//
//                        }
//                        System.out.println("***stop play error");
//                    } catch (LineUnavailableException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (UnsupportedAudioFileException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } finally {
//                        try {
//                            clip.close();
//                            sound.close();
//                            clip = null;
//                            info = null;
//                            sound = null;
//                            soundFile = null;
//
//                        } catch (IOException ex) {
//                            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                            System.out.println("stop sound error");
//                        }
//                    }
//
//                    return null;
//                }
//            };
//            playErrorWorker.execute();
//        }
//    }
//    public void stopPlayError() {
//        if (playErrorWorker != null) {
//            playErrorWorker.cancel(false);
//            canPlayError = true;
//        }
//    }
    public void playAsterisk() {

//        if (player != null && player.isAlive()) {
//            System.out.println("Sound is playing , ignore new one.");
//            return;
//        }
//        player = new AePlayWave("sounds/asterisk.wav");
//        player.start();
        new AePlayWave("sounds/asterisk.wav").start();
//        new SwingWorker() {
//
//            @Override
//            protected Object doInBackground() {
//                if (canPlay) {
//                    canPlay = false;
//                    AudioInputStream sound = null;
//                    Clip clip = null;
//                    DataLine.Info info = null;
//                    File soundFile = null;
//                    try {
//                        soundFile = new File("sounds/asterisk.wav");
//                        sound = AudioSystem.getAudioInputStream(soundFile);
//                        // load the sound into memory (a Clip)
//                        info = new DataLine.Info(Clip.class, sound.getFormat());
//                        clip = (Clip) AudioSystem.getLine(info);
//                        clip.open(sound);
//                        clip.start();
//                    } catch (LineUnavailableException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (UnsupportedAudioFileException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                    } finally {
//                        try {
//                            clip.close();
//                            sound.close();
//                            clip = null;
//                            info = null;
//                            sound = null;
//                            soundFile = null;
//
//                        } catch (IOException ex) {
//                            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        canPlay = true;
//                    }
//
//                }
//                return null;
//            }
//        }.execute();
    }
}
