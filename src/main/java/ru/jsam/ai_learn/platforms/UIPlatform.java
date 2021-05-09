package ru.jsam.ai_learn.platforms;

import ru.jsam.ai_learn.SIGN;
import ru.jsam.ai_learn.ui.OSignButton;
import ru.jsam.ai_learn.ui.SpaceSignButton;
import ru.jsam.ai_learn.ui.XSignButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UIPlatform implements Platform {

    JFrame mainFrame;
    private JPanel boardPanel;

    private JLabel xWinsLabel;
    private JLabel oWinsLabel;
    private JLabel standOffLabel;
    private JLabel invitationLabel;

    BlockingQueue<Integer> selectedIndexQueue = new LinkedBlockingQueue<>(1);

    public UIPlatform() {
        mainFrame = new JFrame("Tic tac toe");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel gamePanel = new JPanel(new GridLayout(3, 1));
        gamePanel.setMaximumSize(new Dimension(300, 300));

        boardPanel = new JPanel();
        invitationLabel = new JLabel();
        xWinsLabel = new JLabel();
        oWinsLabel = new JLabel();
        standOffLabel = new JLabel();

//        boardPanel.setPreferredSize(new Dimension(90, 90));

        JPanel resultsPanel = new JPanel(new GridLayout(3, 1));
        resultsPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
        resultsPanel.add(xWinsLabel);
        resultsPanel.add(oWinsLabel);
        resultsPanel.add(standOffLabel);

        gamePanel.add(boardPanel);
        gamePanel.add(invitationLabel);
        gamePanel.add(resultsPanel);

        mainFrame.add(gamePanel);
        mainFrame.setSize(300, 300);
        mainFrame.setVisible(true);
    }

    @Override
    public void inviteUserToTurn(SIGN t) {
        invitationLabel.setText("Makes a turn: " + t.getStringValue());
    }

    private void clickIndex(int index) {
        try {
            selectedIndexQueue.put(index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void draw(SIGN[] board) {
        SwingUtilities.invokeLater(() -> {

            boardPanel.removeAll();
            boardPanel.setLayout(new GridLayout(3, 3));

            for (int i = 0; i < board.length; i++) {
                try {
                    if (board[i] == SIGN.X) {
                        boardPanel.add(new XSignButton());
                    } else if (board[i] == SIGN.O) {
                        boardPanel.add(new OSignButton());
                    } else {
                        int finalI = i;
                        boardPanel.add(new SpaceSignButton(e -> clickIndex(finalI)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            repaint();
        });
    }

    private void repaint(){
        Dimension size = mainFrame.getSize();
        int width = 300;
        int height = size.getHeight() > 300 ? 300 : 301;

        mainFrame.setSize(new Dimension(width, height));
    }

    @Override
    public int getUserTurn() {
        try {
            return selectedIndexQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void showResult(int xWin, int oWin, int standoff) {
        SwingUtilities.invokeLater(() -> {
            xWinsLabel.setText("X wins : " + xWin);
            oWinsLabel.setText("O wins : " + oWin);
            standOffLabel.setText("Standoffs: " + standoff);
        });
    }

    public void winX() {

    }

    public void winO() {

    }

    @Override
    public void standOff() {

    }
}
