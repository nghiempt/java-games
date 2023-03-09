package Views;

import java.awt.*;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;
import Canvas.BoardGraphics;
import Controller.Controller;
import Models.SudokuModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGame extends JFrame implements ActionListener {

    JTextField[][] board = new JTextField[9][9];
    int[][] state = new int[9][9];
    int[][] stateSolve = new int[9][9];
    int numberCellIllegal = 0;
    JButton jbNew, jbSolved, jbCheck, jbReset;
    JButton lbMessage;
    JComboBox<String> comboBox;
    Controller controller;
    SudokuModel model;
    Random random = new Random();
    boolean reset = true;
    Font font = new Font("Arial", Font.BOLD, 16);
    ColorUIResource colorMain = new ColorUIResource(0, 128, 255);
    Color colorButton = new Color(0, 128, 255);
    Color colorOpacity = new Color(93, 174, 255);
    ColorUIResource colorCell = new ColorUIResource(178, 231, 250);
    ColorUIResource colorText = new ColorUIResource(255, 51, 51);
    Color colorSolved = new Color(255, 221, 221);

    public SudokuGame(Controller controller, SudokuModel model) {
        this.controller = controller;
        this.model = model;
        init();
        //

    }

    public SudokuGame() {

    }

    public void init() {
        setTitle("Sudoku Game");
        setSize(700, 500);
        createView();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void createView() {
        JPanel container = new JPanel();
        add(container);
        container.setLayout(new BorderLayout());
        JLayeredPane boardPanel = new BoardGraphics(new GridLayout(9, 9, 2, 2));

//		boardPanel.setBackground(Color.white);
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        controlPanel.setBackground(Color.white);
        boardPanel.setBorder(new LineBorder(colorMain, 5));
        container.add(boardPanel, BorderLayout.CENTER);
        container.add(controlPanel, BorderLayout.EAST);

        //
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                board[i][j] = new JTextField();
                board[i][j].addActionListener(this);
                board[i][j].setHorizontalAlignment(JTextField.CENTER);

                board[i][j].setBorder(new LineBorder(colorMain, 1));
                board[i][j].setFont(new Font("Arial", Font.BOLD, 25));
                board[i][j].setForeground(colorText);

                boardPanel.add(board[i][j]);

            }
        }
        //

        comboBox = new JComboBox<String>(new String[]{"Easy", "Average", "Hard"});
        comboBox.setPreferredSize(new DimensionUIResource(110, 20));
        comboBox.setBackground(colorMain);
        comboBox.setForeground(Color.black);
        comboBox.setOpaque(true);

        JLabel label = new JLabel("Level: ");
        JPanel headerPanel = new JPanel();
        headerPanel.add(label);
        headerPanel.add(comboBox);
        headerPanel.setBackground(Color.white);
        headerPanel.setBorder(new LineBorder(colorMain, 2));

        jbNew = new JButton("New Game");
        jbNew.addActionListener(this);
        setUI(jbNew);
        setHover(jbNew);

        jbSolved = new JButton("Solve");
        setUI(jbSolved);
        setHover(jbSolved);
        jbSolved.addActionListener(this);

        jbCheck = new JButton("Check");
        setUI(jbCheck);
        setHover(jbCheck);
        jbCheck.addActionListener(this);
        JPanel panelBt = new JPanel();
        panelBt.setPreferredSize(new DimensionUIResource(200, 140));
//		BoxLayout boxLayout = new BoxLayout(panelBt, BoxLayout.Y_AXIS);
        panelBt.setLayout(new GridLayout(4, 1, 5, 5));
        panelBt.add(headerPanel);

        panelBt.add(jbNew);
        panelBt.add(jbSolved);
        panelBt.add(jbCheck);
        lbMessage = new JButton("Please make a new game");
        JButton lbTitle = new JButton("Sudoku Game");
        lbTitle.setBackground(Color.white);
        lbTitle.setBorder(null);
        lbTitle.setEnabled(true);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTitle.setForeground(colorMain);
        lbMessage.setFont(new Font("Arial", Font.BOLD, 15));
        lbMessage.setBackground(Color.white);
        lbMessage.setBorder(null);
        lbMessage.setEnabled(true);
        JPanel panelInfo = new JPanel();
        panelInfo.add(lbTitle);
        panelInfo.add(lbMessage);
        panelInfo.setBorder(new LineBorder(colorMain, 2));
        panelInfo.setLayout(new GridLayout(7, 1, 10, 10));

        panelInfo.setBackground(Color.white);

        controlPanel.add(panelInfo, BorderLayout.CENTER);
        controlPanel.add(panelBt, BorderLayout.NORTH);

    }

    public void setUI(JComponent component) {
        component.setBackground(colorButton);
        component.setForeground(Color.black);
        component.setFont(font);
        component.setCursor(new Cursor(Cursor.HAND_CURSOR));
        component.setBorder(null);

    }

    public void setHover(JComponent component) {
        component.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                component.setBackground(colorOpacity);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                component.setBackground(colorButton);
            }
        });
    }

    public void level() {
        int level = 2;
        switch (comboBox.getSelectedIndex()) {
            case 0:
                level = 2;
                break;
            case 1:
                level = 4;
                break;
            case 2:
                level = 8;
                break;

            default:
                break;
        }
        for (int l = 0; l < level; l++) {
            for (int k = 0; k < 9; k++) {
                int i = 1 + random.nextInt(8);
                state[k][i] = 0;
            }
            for (int k = 0; k < 9; k++) {
                int i = 1 + random.nextInt(8);
                state[i][k] = 0;
            }
        }

    }

    public void checkWin() {
        boolean win = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].isEditable()) {
                    String inputUser = board[i][j].getText();
                    if (!inputUser.equals(String.valueOf(stateSolve[i][j]))) {
                        win = false;
                        numberCellIllegal++;
                        board[i][j].setBackground(Color.red);
                        board[i][j].setForeground(Color.yellow);
                    } else {
                        board[i][j].setBackground(Color.white);
                        board[i][j].setForeground(Color.black);
                    }
                }
            }
        }
        if (win == true) {
            JOptionPane.showMessageDialog(this, "Congratulation!");
            lbMessage.setText("Winer !!!\n Make new game");
        } else {
            lbMessage.setText(numberCellIllegal + " box not valid");
        }
        numberCellIllegal = 0;
    }

    public void display2(int board[][]) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + board[i][j]);
            }

            System.out.println();
        }

        System.out.println();
    }

    public void resetBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setText("");
                state[i][j] = 0;
                board[i][j].setBackground(Color.white);
                board[i][j].setEditable(true);
                board[i][j].setForeground(colorText);
            }
        }
    }

    public boolean isNumeric(String strNum) {
        try {
            int d = Integer.parseInt(strNum);
            if (!(d > 0 && d <= 9)) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void handleExceptionInput() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int i1 = i;
                int i2 = j;
                board[i][j].getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        board[i1][i2].setBackground(Color.white);
                        board[i1][i2].setForeground(Color.red);

                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        board[i1][i2].setBackground(Color.white);
                        board[i1][i2].setForeground(Color.red);

                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        board[i1][i2].setBackground(Color.white);
                        board[i1][i2].setForeground(Color.red);
                    }
                });
            }
        }
    }

    public boolean isEmtry() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!(state[i][j] == 0)) {
                    return false;
                }

            }
        }
        return true;
    }

    public void showBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (state[i][j] != 0) {
                    board[i][j].setText(String.valueOf(state[i][j]));
//					board[i][j].setEnabled(false);
//					handleExceptionInput(board[i][j]);
                    board[i][j].setEditable(false);
                    board[i][j].setBackground(colorCell);
                    board[i][j].setForeground(Color.black);
                }

            }
        }
    }

    public int[][] copyState(int[][] state) {
        int[][] result = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = state[i][j];
            }
        }
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbNew) {
            resetBoard();
            controller.makeNewGame();
            state = copyState(model.getGenome().getState());
            stateSolve = copyState(model.getGenome().getState());
            level();
            showBoard();
            display2(state);
            if (model.isSuccess()) {
                lbMessage.setText("Make game successfully");
            } else {
                lbMessage.setText("Make game failed");
            }

            System.out.println("xong");
        }
        if (e.getSource() == jbSolved) {
            if (!isEmtry()) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {

                        if (board[i][j].isEditable()) {
                            board[i][j].setText(String.valueOf(stateSolve[i][j]));
                            board[i][j].setBackground(colorSolved);
                            board[i][j].setForeground(Color.red);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "You have to make a new game");

            }
        }
        if (e.getSource() == jbCheck) {
            checkWin();
            handleExceptionInput();
            System.out.println("hello");
        }
    }
}
