package cpt_arcade;

import towerstack.TowerStack;
import static towerstack.TowerStack.towerStack;
import DodgySquare.DodgySquare;
import static DodgySquare.DodgySquare.dodgySquare;
import checkers.Checkers;
import static checkers.Checkers.checkers;
import doodlejump.DoodleJump;
import static doodlejump.DoodleJump.doodleJump;
import brickbreaker.BrickBreaker;
import static brickbreaker.BrickBreaker.brickBreaker;
import boxhead.BoxHead;
import static boxhead.BoxHead.boxHead;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import sun.applet.Main;

public class CPT_Arcade {

    public static int gameChoice = 0, gamePoint;
    public static boolean gameIsBeingPlayed = false, playCheckers = false, playDoodleJump = false, playBrickBreaker = false, playBoxHead = false, playDodgySquare = false, playTowerStack = false;
    public static Mixer mixer;
    public static Clip clip;
    
    public static void main(String[] args) throws InterruptedException, IOException {
//        backgroundMusic();
        do {
            gameEnder();
            if (!gameIsBeingPlayed) {
                gameChoice = map();
            }
            if (gameChoice == 4) {
                checkers = new Checkers();
                gameIsBeingPlayed = true;
                playCheckers = true;
                gameChoice=20;

            } else if (gameChoice == 5) {
                towerStack = new TowerStack();
                gameIsBeingPlayed = true;
                playTowerStack = true;
                gameChoice=20;

            } else if (gameChoice == 6) {
                brickBreaker = new BrickBreaker();
                gameIsBeingPlayed = true;
                playBrickBreaker = true;
                gameChoice=20;

            } else if (gameChoice == 7) {
                doodleJump = new DoodleJump();
                gameIsBeingPlayed = true;
                playDoodleJump = true;
                gameChoice=20;

            } else if (gameChoice == 8) {
                dodgySquare = new DodgySquare();
                gameIsBeingPlayed = true;
                playDodgySquare = true;
                gameChoice=20;

            } else if (gameChoice == 9) {
                boxHead = new BoxHead();
                gameIsBeingPlayed = true;
                playBoxHead = true;
                gameChoice=20;

            } else if (gameChoice == 10) {
                Snake();
            } else if (gameChoice == 11) {
                Fall();
            } else if (gameChoice == 12) {
                DarkEkko();
            } else if (gameChoice == 13) {
                Duet();
            } else if (gameChoice == 14) {
                Defence();
            }
        } while (true);
    }

    public static void gameEnder() {
        if (playCheckers && checkers.isGameOver()) {
            checkers.jframe.dispatchEvent(new WindowEvent(checkers.jframe, WindowEvent.WINDOW_CLOSING));
            gamePoint += checkers.gamePoint;
            gameIsBeingPlayed=false;
            playCheckers=false;
        }
        if (playTowerStack && towerStack.isGameOver()) {
            towerStack.jframe.dispatchEvent(new WindowEvent(towerStack.jframe, WindowEvent.WINDOW_CLOSING));
            gamePoint += towerStack.gamePoint;
            gameIsBeingPlayed=false;
            playTowerStack=false;
        }
        if (playBrickBreaker && brickBreaker.isGameOver()) {
            brickBreaker.jframe.dispatchEvent(new WindowEvent(brickBreaker.jframe, WindowEvent.WINDOW_CLOSING));
            gamePoint += brickBreaker.gamePoint + brickBreaker.gamePoint1;
            gameIsBeingPlayed=false;
            playBrickBreaker=false;
        }
        if (playDoodleJump && doodleJump.isGameOver()) {
            doodleJump.jframe.dispatchEvent(new WindowEvent(doodleJump.jframe, WindowEvent.WINDOW_CLOSING));
            gamePoint += doodleJump.gamePoint/1000;
            gameIsBeingPlayed=false;
            playDoodleJump=false;
        }
        if (playDodgySquare && dodgySquare.isGameOver()) {
            dodgySquare.jframe.dispatchEvent(new WindowEvent(dodgySquare.jframe, WindowEvent.WINDOW_CLOSING));
            gamePoint += dodgySquare.gamePoint;
            gameIsBeingPlayed=false;
            playDodgySquare=false;
        }
        if (playBoxHead && boxHead.isGameOver()) {
            boxHead.jframe.dispatchEvent(new WindowEvent(boxHead.jframe, WindowEvent.WINDOW_CLOSING));
            gamePoint += boxHead.gamePoint;
            gameIsBeingPlayed=false;
            playBoxHead=false;
        }
    }

    public static int map() throws InterruptedException {
        JFrame frame = new JFrame();
        ArcadeMap Map = new ArcadeMap();
        ArcadeCharacter character = new ArcadeCharacter();
        frame.add(Map);
        frame.add(character);
        frame.setSize(ArcadeMap.imageBackground.getIconWidth(), ArcadeMap.imageBackground.getIconHeight());
        frame.setVisible(true);
        character.runGame = false;
        character.gameNumber = 0;
        character.gamePoint += gamePoint;
        gamePoint = 0;
        while (!ArcadeCharacter.runGame) {
            character.repaint();
            frame.addKeyListener(character);
            Thread.sleep(75);
        }
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        return character.gameNumber;
    }

    public static void Snake() throws InterruptedException {
        int counter = 0, delay = 100;
        JFrame frame = new JFrame();
        Snake snake = new Snake();
        frame.add(snake);
        frame.setSize(515, 540);
        frame.setVisible(true);
        snake.gameOver = false;
        snake.GameOver();
        while (!snake.gameOver) {
            snake.repaint();
            frame.addKeyListener(snake);
            if (!snake.paused) {
                counter += 1;
            }
            if (counter == 100 * (11 - (delay / 10)) && delay > 0) {
                delay -= 10;
                counter = 0;
            }
            Thread.sleep(delay);
        }
        frame.dispatchEvent(new WindowEvent(frame ,WindowEvent.WINDOW_CLOSING));
        gamePoint += snake.gamePoint;
    }

    public static void Fall() throws InterruptedException, IOException {
        JFrame frame = new JFrame();
        Fall fall = new Fall();
        frame.add(fall);
        frame.setSize(fall.ScreenLength, fall.ScreenHeight);
        frame.setVisible(true);
        fall.gameOver = false;
        fall.GameOver();
        while (!fall.gameOver) {
            fall.repaint();
            frame.addKeyListener(fall);
            Thread.sleep(fall.delay);
        }
        frame.dispatchEvent(new WindowEvent(frame ,WindowEvent.WINDOW_CLOSING));
        gamePoint += fall.gamePoint;
    }

    public static void DarkEkko() throws InterruptedException {
        JFrame frame = new JFrame();
        DarkEkko darkekko = new DarkEkko();
        frame.add(darkekko);
        frame.setSize(995, 700);
        frame.setVisible(true);
        darkekko.gameOver = false;
        darkekko.GameOver();
        while (!darkekko.gameOver) {
            darkekko.repaint();
            frame.addKeyListener(darkekko);
            Thread.sleep(100);
        }
        frame.dispatchEvent(new WindowEvent(frame ,WindowEvent.WINDOW_CLOSING));
        gamePoint += darkekko.gamePoint;
    }

    public static void Duet() throws InterruptedException, IOException {
        JFrame frame = new JFrame();
        Duet duet = new Duet();
        frame.add(duet);
        frame.setSize(500, 700);
        frame.setVisible(true);
        frame.addKeyListener(duet);
        duet.gameOver = false;
        duet.GameOver();
        while (!duet.gameOver) {
            duet.repaint();
            if (duet.point % 50 == 0 && duet.point > 0 && duet.delay > 1) {
                duet.delay = 4 - duet.point / 50;
            }
            Thread.sleep(duet.delay);
        }
        frame.dispatchEvent(new WindowEvent(frame ,WindowEvent.WINDOW_CLOSING));
        gamePoint += duet.gamePoint;
    }

    public static void Defence() throws InterruptedException, IOException {
        JFrame frame = new JFrame();
        Defence defence = new Defence();
        frame.add(defence);
        frame.setSize(700, 700);
        frame.setVisible(true);
        defence.gameOver = false;
        defence.GameOver();
        while (!defence.gameOver) {
            defence.repaint();
            frame.addKeyListener(defence);
            Thread.sleep(25);
        }
        frame.dispatchEvent(new WindowEvent(frame ,WindowEvent.WINDOW_CLOSING));
        gamePoint += defence.gamePoint;
    }
//    public static void backgroundMusic() {
//        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
//        mixer = AudioSystem.getMixer(mixInfos[0]);
//        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
//        try {
//            clip = (Clip) mixer.getLine(dataInfo);
//        } catch (LineUnavailableException lue) {
//            lue.printStackTrace();
//        }
//        try {
//            URL soundURL = Main.class.getResource("/cpt_arcade/Piano.wav");
//            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
//            clip.open(audioStream);
//        } catch (LineUnavailableException lue) {
//            lue.printStackTrace();
//        } catch (UnsupportedAudioFileException uafe) {
//            uafe.printStackTrace();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//
//        clip.start();
//
//    }

}

//Fix sprites, out of order arcades (grey), get your games to close when they're done playing
//I have the background music on my computer, but the file's really big so I couldn't send it
//Don't delete the commented music code because we'll be using it. Glhf rip comsci