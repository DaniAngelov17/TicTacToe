package GUI.CustomComponents;

import GUI.SubPanels.Component;

import javax.swing.*;
import java.awt.*;

public class ButtonCustom extends JButton implements Component {

    public ButtonCustom(String text, int width, int height){
        super(text);
        this.changeSize(width/9, height/24);
        this.setBackground(Color.white);
        this.setBorder(new RoundedBorder(25, new Color(35, 98, 223, 128)));
        this.setFocusPainted(false);
    }
    @Override
    public void changeSize(int width, int height) {
        this.setPreferredSize(new Dimension(width / 9, height / 24));
    }
}
