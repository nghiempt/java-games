package reversi_ce160353_phamthanhnghiem;

import java.awt.GridLayout;

/**
 *
 * @author CE160353 PhamThanhNghiem
 */
public class Reversi extends javax.swing.JFrame {

    public static final int numCols = 8;
    public static final int numRows = 8;

    public static final int redChess = 0;
    public static final int blueChess = 1;
    public static final int turnOnValue = 10;

    Card map[][];
    int luotDi; //luot di = 0 -> Quan do. luot di = 1 -> Quan xanh duong

    public void generateBoard() {
        int mau = 1;

        map = new Card[numRows][numCols];
        pnlScene.setLayout(new GridLayout(numRows, numCols));
        pnlScene.removeAll();
        pnlScene.revalidate();
        pnlScene.repaint();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                mau = ((i + j) % 2 == 0) ? 2 : 1;
                map[i][j] = new Card(this, i, j, mau);
                pnlScene.add(map[i][j]);
            }
        }
        map[3][3].turnOn(redChess);
        map[4][4].turnOn(redChess);

        map[3][4].turnOn(blueChess);
        map[4][3].turnOn(blueChess);

        luotDi = redChess;
    }

    public int getLuotDi() {
        return luotDi;
    }

    public void luotDiTiepTheo() {
//        if (luotDi == redChess) luotDi = blueChess;
//        else                    luotDi = redChess;
        luotDi = 1 - luotDi;
    }

    public boolean coQuanCo(Card c) {
        return c.getValue() > 9;
    }

    public boolean khacMau(Card c, int luotDi) {
        return (c.getValue() % Reversi.turnOnValue) != luotDi;
    }

    public boolean cungMau(Card c, int luotDi) {
        return (c.getValue() % Reversi.turnOnValue) == luotDi;
    }

    public int countUp(int row, int col) {
        int i = 0, count = 0;
        for (i = row - 1; i >= 0; --i) {
            if (coQuanCo(map[i][col])
                    && khacMau(map[i][col], luotDi)) {
                count++;
            } else {
                break;
            }
        }

        if (i >= 0) {
            if (coQuanCo(map[i][col])
                    && cungMau(map[i][col], luotDi)) {
                for (int j = i; j < row; j++) {
                    map[j][col].reTurn();
                }
                return count;
            }
        }
        return 0;
    }

    public int countDown(int row, int col) {
        int i = 0, count = 0;
        for (i = row + 1; i < numRows; ++i) {
            if (coQuanCo(map[i][col])
                    && khacMau(map[i][col], luotDi)) {
                count++;
            } else {
                break;
            }
        }

        if (i < numRows) {
            if (coQuanCo(map[i][col])
                    && cungMau(map[i][col], luotDi)) {
                for (int j = row + 1; j < i; j++) {
                    map[j][col].reTurn();
                }
                return count;
            }
        }
        return 0;
    }

    public int countLeft(int row, int col) {
        int i = 0, count = 0;
        for (i = col - 1; i >= 0; --i) {
            if (coQuanCo(map[row][i])
                    && khacMau(map[row][i], luotDi)) {
                count++;
            } else {
                break;
            }
        }

        if (i >= 0) {
            if (coQuanCo(map[row][i])
                    && cungMau(map[row][i], luotDi)) {
                for (int j = i + 1; j < col; j++) {
                    map[row][j].reTurn();
                }
                return count;
            }
        }
        return 0;
    }

    public int countRight(int row, int col) {
        int i = 0, count = 0;
        for (i = col + 1; i < numCols; ++i) {
            if (coQuanCo(map[row][i])
                    && khacMau(map[row][i], luotDi)) {
                count++;
            } else {
                break;
            }
        }

        if (i < numCols) {
            if (coQuanCo(map[row][i])
                    && cungMau(map[row][i], luotDi)) {
                for (int j = col + 1; j < i; j++) {
                    map[row][j].reTurn();
                }
                return count;
            }
        }

        return 0;
    }

    public int countLeftUp(int row, int col) {
        int i = 0, j = 0, count = 0;
        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; --i, --j) {
            if (coQuanCo(map[i][j])
                    && khacMau(map[i][j], luotDi)) {
                count++;
            } else {
                break;
            }
        }

        if (i >= 0 && j >= 0) {
            if (coQuanCo(map[i][j])
                    && cungMau(map[i][j], luotDi)) {
                for (int h = i + 1, k = j + 1; h < row && k < col; h++, k++) {
                    map[h][k].reTurn();
                }
                return count;
            }
        }

        return 0;
    }

    public int countRightUp(int row, int col) {
        int i = 0, j = 0, count = 0;
        for (i = row - 1, j = col + 1; i >= 0 && j < numCols; --i, ++j) {
            if (coQuanCo(map[i][j])
                    && khacMau(map[i][j], luotDi)) {
                count++;
            } else {
                break;
            }
        }

        if (i >= 0 && j < numCols) {
            if (coQuanCo(map[i][j])
                    && cungMau(map[i][j], luotDi)) {
                for (int h = i + 1, k = j - 1; h < row && k >= col + 1; h++, k--) {
                    map[h][k].reTurn();
                }
                return count;
            }
        }

        return 0;
    }

    public int countLeftDown(int row, int col) {
        int i = 0, j = 0, count = 0;
        for (i = row + 1, j = col - 1; i < numRows && j >= 0; ++i, --j) {
            if (coQuanCo(map[i][j])
                    && khacMau(map[i][j], luotDi)) {
                count++;
            } else {
                break;
            }
        }

        if (i < numRows && j >= 0) {
            if (coQuanCo(map[i][j])
                    && cungMau(map[i][j], luotDi)) {
                for (int h = i - 1, k = j + 1; h >= row + 1 && k < col; h--, k++) {
                    map[h][k].reTurn();
                }
                return count;
            }
        }

        return 0;
    }

    public int countRightDown(int row, int col) {
        int i = 0, j = 0, count = 0;
        for (i = row + 1, j = col + 1; i < numRows && j < numCols; ++i, ++j) {
            if (coQuanCo(map[i][j])
                    && khacMau(map[i][j], luotDi)) {
                count++;
            } else {
                break;
            }
        }

        if (i < numRows && j < numCols) {
            if (coQuanCo(map[i][j])
                    && cungMau(map[i][j], luotDi)) {
                for (int h = row + 1, k = col + 1; h < i && k < j; h++, k++) {
                    map[h][k].reTurn();
                }
                return count;
            }
        }
        return 0;
    }

    public int countAllDirection(int row, int col) {
        int up = countUp(row, col);
        int down = countDown(row, col);
        int left = countLeft(row, col);
        int right = countRight(row, col);
        int leftUp = countLeftUp(row, col);
        int rightUp = countRightUp(row, col);
        int leftDown = countLeftDown(row, col);
        int rightDown = countRightDown(row, col);

        return up + down + left + right + leftUp + rightUp + leftDown + rightDown;
    }

    public void updatePoint() {
        int countRed = 0;
        int countBlue = 0;
        int v;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (coQuanCo(map[i][j])) {
                    v = map[i][j].getValue() % Reversi.turnOnValue;
                    if (v == redChess) {
                        countRed++;
                    } else {
                        countBlue++;
                    }
                }
            }
        }
        pnlRed.setText(countRed + "");
        pnlBlue.setText(countBlue + "");
    }

    /**
     * Creates new form Reversi
     */
    public Reversi() {
        initComponents();
        this.setLocationRelativeTo(null); // canh giua man hinh
        generateBoard();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        RedPanel = new javax.swing.JLabel();
        pnlRed = new javax.swing.JLabel();
        BluePanel = new javax.swing.JLabel();
        pnlBlue = new javax.swing.JLabel();
        pnlScene = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Game information"));

        RedPanel.setBackground(new java.awt.Color(255, 51, 0));
        RedPanel.setForeground(new java.awt.Color(255, 0, 0));
        RedPanel.setText("Red");

        pnlRed.setBackground(new java.awt.Color(255, 0, 0));
        pnlRed.setForeground(new java.awt.Color(255, 51, 51));
        pnlRed.setText("0");

        BluePanel.setForeground(new java.awt.Color(0, 0, 255));
        BluePanel.setText("Blue");

        pnlBlue.setForeground(new java.awt.Color(0, 0, 255));
        pnlBlue.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(RedPanel)
                .addGap(18, 18, 18)
                .addComponent(pnlRed)
                .addGap(18, 18, 18)
                .addComponent(BluePanel)
                .addGap(18, 18, 18)
                .addComponent(pnlBlue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BluePanel)
                        .addComponent(pnlBlue))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RedPanel)
                        .addComponent(pnlRed)))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pnlScene.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlScene.setPreferredSize(new java.awt.Dimension(400, 400));
        pnlScene.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlScene, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlScene, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Reversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reversi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BluePanel;
    private javax.swing.JLabel RedPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel pnlBlue;
    private javax.swing.JLabel pnlRed;
    private javax.swing.JPanel pnlScene;
    // End of variables declaration//GEN-END:variables
}
