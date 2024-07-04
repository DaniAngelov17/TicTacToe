package GUI.CustomComponents;

import GUI.SubPanels.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextFieldCustom extends JTextField implements Component {

    private final String defaultText;
    public TextFieldCustom(String text, int width, int height){
        super(text);
        changeSize(width, height);
        this.defaultText = text;
        this.setBackground(Color.white);
        this.setBorder(new RoundedBorder(25, new Color(35, 98, 223, 100)));
        this.setHorizontalAlignment(JTextField.CENTER);
        setUpFocusListener();
    }

    private void setUpFocusListener() {
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(defaultText)) {
                    setText("");
                    setForeground(new Color(35, 98, 223, 250)); //
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(new Color(35, 98, 223, 250));
                    setText(defaultText);
                } else {
                    setForeground(Color.BLACK);
                }
            }
        });
    }
    public String getDefaultText(){
        return defaultText;
    }
    @Override
    public void changeSize(int width, int height) {
        this.setPreferredSize(new Dimension(width/9, height/24));
    }
}
