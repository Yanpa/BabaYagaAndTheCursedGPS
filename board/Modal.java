package board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Modal extends JDialog {
    public static boolean isClicked = false;

    /**
     * Constructor to create a modal window for the win
     * @param parent
     * @param title
     * @param message
     */
    public Modal(JFrame parent, String title, String message){
        super(parent, title, true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);

        panel.add(label);
        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        this.setLocationRelativeTo(parent);

        setVisible(true);
    }

    /**
     * Constructor to create a modal window when you are stuck
     * @param message
     * @param title
     * @param parent
     */
    public Modal(String message, String title, JFrame parent){
        super(parent, title, true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        JButton button = new JButton("restart");

        panel.add(label);
        panel.add(button);
        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        this.setLocationRelativeTo(parent);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isClicked = true;
            }
        });

        setVisible(true);
    }
}
