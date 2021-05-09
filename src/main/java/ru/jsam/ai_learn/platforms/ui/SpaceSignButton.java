package ru.jsam.ai_learn.platforms.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SpaceSignButton extends JButton {

    public SpaceSignButton(ActionListener listener) {
        super(" ");
        addActionListener(listener);
    }
}
