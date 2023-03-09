package pingpong_v1_ce160353;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author CE160353 PhamThanhNghiem
 */
public class PingPong extends javax.swing.JFrame {

    public static int _BallSize = 20;

    public static int _RacketHeight = 40;
    public static int _RacketWidth = 6;

    public static int _SceneHeight = 400;
    public static int _SceneWidth = 800;

    int time;
    Thread timer;

    Thread gameThread;

    int xRed, yRed;
    int redPoint;
    JLabel red;

    int xGreen, yGreen;
    int greenPoint;
    JLabel green;

    int xBall, yBall, dxBall, dyBall;
    JLabel ball;

    int speedBall, speedRacket;

    public void reset() {
        redPoint = greenPoint = 0;

        xRed = 10;
        xGreen = _SceneWidth - _RacketWidth - 10;

        yRed = yGreen = (_SceneHeight - _RacketHeight) / 2;

        xBall = (_SceneWidth - _BallSize) / 2;
        yBall = (_SceneHeight - _BallSize) / 2;

        int dx = Randomizer.random(0, 1);
        dxBall = dx == 0 ? -1 : 1;

        int dy = Randomizer.random(0, 1);
        dyBall = dy == 0 ? -1 : 1;

        speedBall = 3;
        speedRacket = 10;

        time = 0;

    }

    public void initScene() {

        pnlScene.setLayout(null);
        pnlScene.removeAll();
        pnlScene.revalidate();
        pnlScene.repaint();
        pnlScene.setSize(_SceneWidth, _SceneHeight);

        red = new JLabel();
        red.setOpaque(true);
        red.setBackground(Color.decode("#FF3333"));
        red.setBounds(xRed, yRed, _RacketWidth, _RacketHeight);

        green = new JLabel();
        green.setOpaque(true);
        green.setBackground(Color.decode("#009966"));
        green.setBounds(xGreen, yGreen, _RacketWidth, _RacketHeight);

        ball = new JLabel();
        ball.setIcon(new ImageIcon(getClass().getResource("/img/ball.png")));
        ball.setBounds(xBall, yBall, _BallSize, _BallSize);

        pnlScene.add(red);
        pnlScene.add(green);
        pnlScene.add(ball);
    }

    String int2time(int time) {
        return String.format("%02d:%02d:%02d", time / 3600, (time / 60) % 60, time % 60);
    }

    public void runTimer() {
        timer = new Thread() {
            public void run() {
                while (true) {
                    try {
                        ++time;
                        lblTime.setText(int2time(time));
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PingPong.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        timer.start();
    }

    public boolean isGameOver() {
        return xBall <= 0 || xBall >= _SceneWidth;
    }

    public boolean isHitGreen() {

        // Caculate the center of the ball
        int ballRadius = _BallSize / 2;
        int bX = xBall + ballRadius;
        int bY = yBall + ballRadius;

        // Caculate the center of the green racket       
        int gX = xGreen + _RacketWidth / 2;

        if (Math.abs(bX - gX) <= ballRadius) {
            //Check for hiting the green racket
            if (yGreen - ballRadius <= bY && bY <= yGreen + _RacketHeight + ballRadius) {
                return true;
            }

        }
        return false;
    }

    public boolean isHitRed() {

        // Caculate the center of the ball
        int ballRadius = _BallSize / 2;
        int bX = xBall + ballRadius;
        int bY = yBall + ballRadius;

        // Caculate the center of the red racket       
        int rX = xRed + _RacketWidth / 2;

        if (Math.abs(bX - rX) <= ballRadius) {
            //Check for hiting the red racket
            if (yRed - ballRadius <= bY && bY <= yRed + _RacketHeight + ballRadius) {
                return true;
            }

        }
        return false;
    }

    public void replay() {

        xBall = (_SceneWidth - _BallSize) / 2;
        yBall = (_SceneHeight - _BallSize) / 2;

        int dx = Randomizer.random(0, 1);
        dxBall = dx == 0 ? -1 : 1;

        int dy = Randomizer.random(0, 1);
        dyBall = dy == 0 ? -1 : 1;
    }

    public void gameOver() {

        if (xBall <= 0) {
            lblMessage.setText("Green player is the winner! Press SPACE BAR to continue");
            lblMessage.setForeground(Color.decode("#009966"));
            greenPoint++;
        }
        if (xBall > _SceneWidth) {
            lblMessage.setText("Red player is the winner! Press SPACE BAR to continue");
            lblMessage.setForeground(Color.decode("#FF3333"));
            redPoint++;
        }

        lblRed.setText(redPoint + "");
        lblGreen.setText(greenPoint + "");

        timer.stop();
        gameThread.stop();
    }

    public void runGame() {
        gameThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        //move red
                        red.setBounds(xRed, yRed, _RacketWidth, _RacketHeight);

                        // move green
                        green.setBounds(xGreen, yGreen, _RacketWidth, _RacketHeight);

                        //move ball
                        xBall += speedBall * dxBall;
                        yBall += speedBall * dyBall;

                        if (isGameOver()) {
                            gameOver();
                        } else if (isHitRed() || isHitGreen()) {
                            dxBall = -dxBall; // reverse
                        }

//                        if (xBall < 0) {
//                            xBall = 0;
//                            dxBall = -dxBall; // reverse
//                        } else if (xBall > _SceneWidth - _BallSize) {
//                            xBall = _SceneWidth - _BallSize;
//                            dxBall = -dxBall; // reverse
//                        }
//
                        if (yBall < 0) {
                            yBall = 0;
                            dyBall = -dyBall; // reverse
                        } else if (yBall > _SceneHeight - _BallSize) {
                            yBall = _SceneHeight - _BallSize;
                            dyBall = -dyBall; // reverse
                        }

                        ball.setBounds(xBall, yBall, _BallSize, _BallSize);
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PingPong.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        gameThread.start();
    }

    /**
     * Creates new form PingPong
     */
    public PingPong() {
        initComponents();
        this.setLocationRelativeTo(null);

        reset();
        initScene();
        runTimer();
        runGame();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGameInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblRed = new javax.swing.JLabel();
        lblGreen = new javax.swing.JLabel();
        lblTimeLabel = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        pnlScene = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        pnlGameInfo.setBorder(javax.swing.BorderFactory.createTitledBorder("Game information"));
        pnlGameInfo.setPreferredSize(new java.awt.Dimension(800, 70));

        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Red:");

        jLabel2.setForeground(new java.awt.Color(0, 153, 102));
        jLabel2.setText("Green:");

        lblRed.setForeground(new java.awt.Color(255, 51, 51));
        lblRed.setText("0");

        lblGreen.setForeground(new java.awt.Color(0, 153, 102));
        lblGreen.setText("0");

        lblTimeLabel.setText("Time:");

        lblTime.setText("00:00:00");

        lblMessage.setForeground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout pnlGameInfoLayout = new javax.swing.GroupLayout(pnlGameInfo);
        pnlGameInfo.setLayout(pnlGameInfoLayout);
        pnlGameInfoLayout.setHorizontalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRed)
                .addGap(72, 72, 72)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGreen)
                .addGap(78, 78, 78)
                .addComponent(lblTimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTime)
                .addGap(47, 47, 47)
                .addComponent(lblMessage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGameInfoLayout.setVerticalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lblRed)
                    .addComponent(lblGreen)
                    .addComponent(lblTimeLabel)
                    .addComponent(lblTime)
                    .addComponent(lblMessage))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pnlScene.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlScene.setPreferredSize(new java.awt.Dimension(800, 400));
        pnlScene.setLayout(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlScene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGameInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlScene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();

        //Move green racket
        if (key == KeyEvent.VK_UP) {
            yGreen -= speedRacket;
        }

        if (key == KeyEvent.VK_DOWN) {
            yGreen += speedRacket;
        }

        if (key == KeyEvent.VK_SPACE) {
            replay();
            initScene();
            runTimer();
            runGame();
        }

        //Move red racket
    }//GEN-LAST:event_formKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PingPong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PingPong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PingPong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PingPong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PingPong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblGreen;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblRed;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTimeLabel;
    private javax.swing.JPanel pnlGameInfo;
    private javax.swing.JPanel pnlScene;
    // End of variables declaration//GEN-END:variables
}
