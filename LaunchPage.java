import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

class LaunchPage {

    //final theme formatting tools
    final Color turqTheme = new Color(0x08EAFF);
    final Color darkTheme = new Color(0x242120);

    final Font themeFont = new Font("", Font.BOLD, 14);

    //constructor for class
    LaunchPage() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0,0,400,220);
        panel.setBackground(darkTheme);
        panel.setLayout(null);


        URL imgURL = getClass().getClassLoader().getResource("TempestHigh.png");
        ImageIcon img = new ImageIcon(imgURL);

        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(100,0,200,200);
        panel.add(imgLabel);;




        //panel to house following buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0,220,400,105);
        buttonPanel.setBackground(darkTheme);

        JPanel helpPanel = new JPanel(null);
        helpPanel.setBounds(0,325, 400,75);
        helpPanel.setBackground(turqTheme);

        //radio buttons that allow user to select which process they wish to run
        JRadioButton driverButton = new JRadioButton("Driver");
        driverButton.setFont(themeFont);
        JRadioButton analysisButton = new JRadioButton("Analysis");
        analysisButton.setFont(themeFont);
        JRadioButton graphButton = new JRadioButton("Graph");
        graphButton.setFont(themeFont);

        //button that initiates launch of a new page depending on radio button selection
        JButton goButton = new JButton("Go");
        goButton.setFont(themeFont);

        JButton helpButton = new JButton("?");
        helpButton.setFont(themeFont);
        helpButton.setBounds(10,5,35,35);
        helpPanel.add(helpButton);

        //grouping of radio buttons to allow for only one selection at a time
        ButtonGroup group = new ButtonGroup();
        group.add(driverButton);
        driverButton.setForeground(turqTheme);
        group.add(analysisButton);
        analysisButton.setForeground(turqTheme);
        group.add(graphButton);
        graphButton.setForeground(turqTheme);

        driverButton.setBounds(50,0,100,25);
        analysisButton.setBounds(150,0,100,25);
        graphButton.setBounds(250,0,100,25);
        goButton.setBounds(175,50,50,35);

        goButton.setForeground(turqTheme);

        buttonPanel.add(driverButton);
        buttonPanel.add(analysisButton);
        buttonPanel.add(graphButton);
        buttonPanel.add(goButton);

        frame.add(panel);
        frame.add(buttonPanel);
        frame.add(helpPanel);

        frame.setLocationRelativeTo(null); //centers frame on computer screen when application is launched
        frame.setVisible(true);


        //actionListener for buttons
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == goButton){
                    if(driverButton.isSelected()){
                        NewPage driverPage = new NewPage(1);
                    } else if(analysisButton.isSelected()){
                        NewPage analysisPage = new NewPage(2);
                    } else if(graphButton.isSelected()){
                        NewPage graphParameterPage = new NewPage(3);
                    }
                } else if(e.getSource() == helpButton){
                    String message = "Selecting the driver button will bring you\n" +
                            "to a page that takes the day's weather data for\n" +
                            "a specified location. It will then write this data\n" +
                            "to a .txt file for analysis.\n\n" +
                            "Selecting the analysis button will bring you\n" +
                            "to a page that allows you to analyze weather\n" +
                            "patterns for locations that have data\n" +
                            "associated with them.\n\n" +
                            "Make a selection, then press the Go button.";

                    JTextArea helpMessage = new JTextArea(message);
                    ReturnWindow helpWindow = new ReturnWindow(helpMessage);

                }
            }
        }; //end of buttonListener

        goButton.addActionListener(buttonListener);
        driverButton.addActionListener(buttonListener);
        analysisButton.addActionListener(buttonListener);
        graphButton.addActionListener(buttonListener);
        helpButton.addActionListener(buttonListener);

    } //end of constructor

} //end of class