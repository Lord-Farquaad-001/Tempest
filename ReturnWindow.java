/*
return type: graph
 */


import javax.swing.*;
import java.awt.*;

class ReturnWindow extends JFrame{

    final Color turqTheme = new Color(0x08EAFF);
    final Color darkTheme = new Color(0x242120);

    final Font themeFont = new Font("", Font.BOLD, 14);

    JFrame frame = new JFrame();

    ReturnWindow(JTable table) {

        //frame formatting
        //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(1000, 250);
        frame.setLayout(null);
        frame.setTitle("Weather Data");
        frame.getContentPane().setBackground(darkTheme);


        //scrollPanel housing table
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0,0,1000,200);
        sp.setBackground(darkTheme);
        sp.getViewport().setBackground(darkTheme);

        frame.add(sp);
        frame.setVisible(true);

    }

    //error window for exception handling (single string)
    ReturnWindow(String message){

        System.out.println(message);

        frame.setSize(350,150);
        frame.getContentPane().setBackground(darkTheme);
        frame.setLayout(null);

        JLabel errorLabel = new JLabel(message);
        errorLabel.setBounds(10,10,340,25);
        errorLabel.setForeground(turqTheme);
        frame.add(errorLabel);

        JButton okButton = new JButton("Dismiss");
        okButton.setBounds(100,70,150,25);
        okButton.addActionListener(e -> frame.dispose());
        frame.add(okButton);

        frame.setVisible(true);


    }

    //option for passing an array into ReturnWindow constructor
    ReturnWindow(String [] errorMessages) {
        //frame.setSize(350, (10 + (errorMessages.length * 25) + 55));
        frame.setSize(330,230);
        frame.getContentPane().setBackground(darkTheme);
        frame.setTitle("Error Messages");
        frame.setLayout(null);

        JPanel messagePanel = new JPanel(new GridLayout(errorMessages.length, 1,10,10));
        messagePanel.setBounds(0,0,330,300);
        messagePanel.setBackground(darkTheme);

        JScrollPane sp = new JScrollPane(messagePanel);
        sp.setBounds(15,0,300,150);
        sp.setBackground(darkTheme);
        sp.getViewport().setBackground(darkTheme);

        frame.add(sp);

        for (int i = 0; i < errorMessages.length; i++) {

            JLabel label = new JLabel((i+1) +". " + errorMessages[i]);
            label.setForeground(Color.red);
            messagePanel.add(label);
        }


        JButton okButton = new JButton("Dismiss");
        okButton.setBounds(125,160,100,35);
        okButton.setFont(themeFont);
        okButton.addActionListener(e -> frame.dispose());
        frame.add(okButton);

        frame.setVisible(true);

    }


    //window that appears if user presses help button
    ReturnWindow(JTextArea help){
        frame.setSize(350, 230);
        frame.getContentPane().setBackground(darkTheme);
        frame.setTitle("Help Message");
        frame.setLayout(null);

        help.setBackground(darkTheme);
        help.setForeground(turqTheme);
        help.setBounds(0,0,320,300);

        JScrollPane sp = new JScrollPane(help);
        sp.setBounds(15,0,320,150);
        sp.setBackground(darkTheme);
        sp.getViewport().setBackground(darkTheme);

        JButton okButton = new JButton("Dismiss");
        okButton.setBounds(125,160,100,35);
        okButton.setFont(themeFont);
        okButton.addActionListener(e -> frame.dispose());
        frame.add(okButton);


        frame.add(sp);

        frame.setVisible(true);
    }
}