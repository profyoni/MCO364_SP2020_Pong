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
    private int ball_dx = 1, ball_dy = 1;

    public PongWindow() {

        setSize(500, 800);

        javax.swing.Timer ballUpdater = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ball.translate(ball_dx, ball_dy);
               // System.out.println(ball);
                if (ball.x>400 || ball.x < 10)
                {
                    ball_dx = -ball_dx;
                }
                if (ball.y>400 || ball.y < 10)
                {
                    ball_dy = -ball_dy;
                }
                final int BUFFER = 90;
                Point panelD = gamePanel.getLocationOnScreen();
                        Point ballAbs = ball.getLocation();
                ballAbs.translate(panelD.x, panelD.y);
                repaint(ballAbs.x-2, ballAbs.y-2,
                        BALL_DIAMETER+4, BALL_DIAMETER+4);
            }
        });

        ballUpdater.start();

        setContentPane(gamePanel);

       // setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                try (Scanner scanner = new Scanner(new File("pong.txt"))) {
                    ball.x = scanner.nextInt();
                    scanner.nextLine();
                    ball.y = scanner.nextInt();
                    scanner.nextLine();
                    ball_dx = scanner.nextInt();
                    scanner.nextLine();
                    ball_dy= scanner.nextInt();
                    } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try (PrintWriter pw = new PrintWriter("pong.txt")) {
                    pw.println(ball.x);
                    pw.println(ball.y);
                    pw.println(ball_dx);
                    pw.println(ball_dy);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();;
                }
                System.exit(0);

            }

        });
        setVisible(true);
        revalidate(); // updates location of components in this GUI

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



