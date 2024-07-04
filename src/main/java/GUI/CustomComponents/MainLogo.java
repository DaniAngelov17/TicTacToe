package GUI.CustomComponents;

import GUI.SubPanels.Component;

import javax.swing.*;
import java.awt.*;

public class MainLogo extends JLabel implements Component {
    public MainLogo(int mainWidth, int mainHeight){
        changeSize(mainWidth, mainHeight);
        this.setText("TicTacToe");
        this.setFont(new Font("Arial", Font.PLAIN, 44));
    }

    @Override
    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension((int)(mainWidth/10), (int)(mainHeight)/10));
    }
}
