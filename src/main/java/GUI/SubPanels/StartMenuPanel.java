package GUI.SubPanels;

import Entities.IAction;
import Entities.Player;
import Entities.StartAction;
import GUI.CustomComponents.ButtonCustom;
import GUI.CustomComponents.MainLogo;
import GUI.CustomComponents.TextFieldCustom;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class StartMenuPanel extends JPanel implements Component {
    private final int mainWidth;
    private final int mainHeight;

    private final IAction<StartAction> startAction;
    private List<Component> componentList;
    private JLabel mainLogo;
    private JTextField inputTextFieldPlayer1;
    private JTextField inputTextFieldPlayer2;
    private JButton startButton;

    public StartMenuPanel(IAction<StartAction> startAction, int mainWidth, int mainHeight){
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.startAction = startAction;
        initComponents(mainWidth,mainHeight);
        changeSize(mainWidth,mainHeight);
        setLayout();
        this.setBackground(Color.white);
    }

    private void initComponents(int mainWidth, int mainHeight) {
        componentList = new ArrayList<>();

        mainLogo = new MainLogo(mainWidth, mainHeight);
        mainLogo.setFont(new java.awt.Font("High Tower Text", 1, 44)); // NOI18N
        mainLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inputTextFieldPlayer1 = new TextFieldCustom("Player 1", mainWidth, mainHeight);
        inputTextFieldPlayer2 = new TextFieldCustom("Player 2",mainWidth , mainHeight);
        startButton = new ButtonCustom("Start", mainWidth, mainHeight);
        startButton.addActionListener(e->{

            startAction.execute(new StartAction(new Player(inputTextFieldPlayer1.getText()), new Player(inputTextFieldPlayer2.getText())));
        });
        componentList.add((Component) mainLogo);
        componentList.add((Component) inputTextFieldPlayer1);
        componentList.add((Component) inputTextFieldPlayer2);
        componentList.add((Component) startButton);
    }

    private void setLayout() {

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(243, 243, 243)
                                                .addComponent(mainLogo, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(329, 329, 329)
                                                .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 168, Short.MAX_VALUE)
                                .addComponent(inputTextFieldPlayer1, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(inputTextFieldPlayer2, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
                                .addGap(176, 176, 176))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addComponent(mainLogo, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(inputTextFieldPlayer1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputTextFieldPlayer2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97))
        );
    }

    @Override
    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension(mainWidth , mainHeight));
        for (Component c:componentList) {
            c.changeSize(mainWidth, mainHeight);
        }
    }
}
