package Views;

import Controller.Controller;
import Controller.SudokuController;
import Models.SudokuModel;
import java.awt.*;
import javax.swing.*;

public class Menu {
//    JFrame frame = new JFrame();

    public static void main(String[] args) {
        Menu m = new Menu();
        JFrame frame = new JFrame();

        Container c = frame.getContentPane();
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("sudoku.jpg"));
        Dimension size = label.getPreferredSize();
        label.setBounds(0, 0, size.width, size.height);
        c.add(label);

        JButton btnPlay = new JButton("Play");
        JButton btnHowtoPlay = new JButton("How to play");
        JButton btnAbout = new JButton("About us");
        JButton btnQuit = new JButton("Quit");

        frame.add(btnPlay);
        btnPlay.setSize(100, 30);
        btnPlay.setLocation(272, 330);
        btnPlay.addActionListener(e -> {
            SudokuModel model = new SudokuModel();
            Controller controller = new SudokuController(model);
            frame.dispose();
        });

        frame.add(btnHowtoPlay);
        btnHowtoPlay.setSize(100, 30);
        btnHowtoPlay.setLocation(272, 360);
        btnHowtoPlay.addActionListener(e -> {
            JOptionPane.showMessageDialog(btnHowtoPlay, "Sudoku is played on a grid of 9 x 9 spaces.\n"
                    + "Within the rows and columns are 9 “squares” (made up of 3 x 3 spaces).\n"
                    + "Each row, column and square (9 spaces each) needs to be filled out with the numbers 1-9,\n"
                    + "without repeating any numbers within the row, column or square.", "How to play", 1);
        });

        frame.add(btnAbout);
        btnAbout.setSize(100, 30);
        btnAbout.setLocation(272, 390);
        btnAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(btnHowtoPlay, "FPT University, SE1606, SU22, CSD201\n"
                    + "Group 6\n"
                    + "Member:\n"
                    + "  1. CE160353  Pham Thanh Nghiem\n"
                    + "  2. CE171478  Nguyen Nhat Hung\n"
                    + "  3. CE161034  Truong Hoai Nam\n"
                    + "  4. CE160803  Tang Kim Yen\n"
                    + "  5. CE160823  Nguyen Hong Phong Vy\n"
                    + "Mentor: Vo Hong Khanh\n", "About us", 1);
        });

        frame.add(btnQuit);
        btnQuit.setSize(100, 30);
        btnQuit.setLocation(272, 420);
        btnQuit.addActionListener(e -> {
            m.confirmAndExit(frame);
        });

        frame.setTitle("Menu game");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(640, 500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void confirmAndExit(JFrame frame) {
        if (JOptionPane.showConfirmDialog(
                frame,
                "Do you really want to exit game?",
                "Please confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }
}
