package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


class PongWindow extends JFrame {

    private final int BALL_DIAMETER= 50,
            PADDLE_WIDTH = 30, PADDLE_HEIGHT = 125;
    private Point ball = new Point(100,60), paddleRight = new Point(), paddLeft = new Point();
    private GamePanel gamePanel = new GamePanel();
    private int ball_dx = 2, ball_dy = 2;

    public PongWindow() {

        setSize(500, 800);

        Timer ballUpdater = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ball.translate(ball_dx, ball_dy);
                System.out.println(ball);
                if (ball.x>400 || ball.x < 10)
                {
                    ball_dx = -ball_dx;
                }
                if (ball.y>400 || ball.y < 10)
                {
                    ball_dy = -ball_dy;
                }
                repaint();
            }
        });

        ballUpdater.start();

        setContentPane(gamePanel);

       // setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                try (Scanner scanner = new Scanner(new File("pong.txt"))) {
                    ball.x = scanner.nextInt();
                    scanner.nextLine();
                    ball.y = scanner.nextInt();
                    } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try (PrintWriter pw = new PrintWriter("pong.txt")) {
                    pw.println(ball.x);
                    pw.println(ball.y);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.exit(0);

            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
        setVisible(true);
    }

    class GamePanel extends JPanel
    {
        GamePanel()
        {
            setBackground(new Color(155,60, 140));

            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {

                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {

                }

                @Override
                public void keyReleased(KeyEvent keyEvent) {

                }
            });

            addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                    paddLeft.translate(0, 5 * mouseWheelEvent.getWheelRotation());
                    System.out.println(mouseWheelEvent.getWheelRotation());
                    repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            g.fillOval(ball.x,ball.y, BALL_DIAMETER, BALL_DIAMETER);
            g.setColor(Color.white);
            g.fillRect(paddLeft.x,paddLeft.y, PADDLE_WIDTH, PADDLE_HEIGHT);

        }
    }
}



