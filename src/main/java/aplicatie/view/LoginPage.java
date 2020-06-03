package aplicatie.view;

import aplicatie.controller.MainControllerAudit;
import aplicatie.controller.MainControllerUser;
import javax.swing.*;
import java.awt.*;

public  class LoginPage extends JFrame {

    private JPanel buttonPanel, loginPanel;
    private JButton loginButton, beakButton, exitButton;
    private ImageIcon imageBackground, imageIcon;
    private JLabel labelBackground,labelIcon;
    private JLabel userNameLabel, passwordLabel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private MainControllerUser mainControllerUser = null;
    private MainControllerAudit mainControllerAudit = null;

    public LoginPage() {

        mainControllerUser = MainControllerUser.getInstance();
        mainControllerAudit = MainControllerAudit.getInstance();

        setLayout(null);
        setTitle("Flight Management Application");
        setSize(800, 485);
        setLocationRelativeTo(null);

        initBackground();
        initPanelButtons();
        initLoginPanel();
        actionMetode();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initBackground(){

        Image image = new ImageIcon(".//src/main/java/aplicatie/fly6.jpg")
                .getImage().getScaledInstance(800,450,Image.SCALE_SMOOTH);
        imageBackground = new ImageIcon(image);
        labelBackground = new JLabel("",imageBackground,JLabel.CENTER);
        labelBackground.setBounds(0,0,800,450);
        add(labelBackground);

    }
    private void initPanelButtons(){

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0,0,0,80));
        buttonPanel.setBounds(0,400,800,50);
        buttonPanel.setLayout(new FlowLayout());
        labelBackground.add(buttonPanel);
        initLoginButton();
        initBeakButton();
        initExitButton();

    }
    private   void initLoginButton(){

        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);

    }
    private    void initBeakButton(){

        beakButton = new JButton("Beak");
        buttonPanel.add(beakButton);

    }
    private void initExitButton(){

        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);

    }

    private void initLoginPanel(){

        loginPanel = new JPanel();
        loginPanel.setSize(450,170);
        loginPanel.setBackground(new Color(0,0,0,80));
        loginPanel.setBounds(150,20,450,170);
        loginPanel.setLayout(null);
        labelBackground.add(loginPanel);
        initInsertUserName();
        initInsertPassword();
        initLoginIcon();

    }

    private void initInsertUserName(){

        userNameLabel = new JLabel("Enter user name or email:");
        userNameLabel.setForeground(Color.GRAY);
        userNameLabel.setBounds(10,25, 180,20);
        loginPanel.add(userNameLabel);
        userNameField = new JTextField();
        userNameField.setBounds(10,50, 180, 20);
        loginPanel.add(userNameField);

    }

    private  void initInsertPassword(){

        passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setForeground(Color.GRAY);
        passwordLabel.setBounds(10, 75, 180, 20);
        loginPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(10, 100, 180, 20);
        loginPanel.add(passwordField);

    }

    private void initLoginIcon(){

        Image image = new ImageIcon(".//src/main/java/aplicatie/iconLogin.jpg")
                .getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        labelIcon = new JLabel("", imageIcon, JLabel.CENTER);
        labelIcon.setBounds(280, 30, 120, 100);
        loginPanel.add(labelIcon);

    }

    private void actionMetode() {

        loginButton.addActionListener(event->{

            if (mainControllerUser.verificaLogin(userNameField.getText(), new String(passwordField.getPassword()))) {
                if (mainControllerAudit.insertAudit("login", mainControllerUser.getUserNameLog())) {

                    mainControllerAudit.setViewPage("dashboard");
                    DashboardPage dashboardPage = new DashboardPage();
                    mainControllerAudit.setJFrame("dashboard", dashboardPage);
                    dashboardPage.setVisible(true);
                    userNameField.setText("");
                    passwordField.setText("");
                    userNameField.requestFocus();
                    dispose();

                }else {

                    mainControllerAudit.resetOraChangePage();
                    JOptionPane.showMessageDialog(null, "Verificati conexiunea la Baza de date!!!");
                    userNameField.setText("");
                    passwordField.setText("");
                    userNameField.requestFocus();

                }
            } else {

                    mainControllerAudit.resetOraChangePage();
                    JOptionPane.showMessageDialog(null, "Datele de autentificare sunt invalide ! ! !");
                    userNameField.setText("");
                    passwordField.setText("");
                    userNameField.requestFocus();

            }

        });

        beakButton.addActionListener(event -> {

            mainControllerAudit.resetOraChangePage();
            if(mainControllerAudit.getJFrame("start")==null){
                StartPage startPage = new StartPage();
                mainControllerAudit.setJFrame("start", startPage);
            }else{
                mainControllerAudit.getJFrame("start").setVisible(true);
            }
            dispose();

        });

        exitButton.addActionListener(event ->{

            MenuBar.flagTimeData = false;
            StartPage.flagStandby = false;
            dispose();

        });

    }

}

