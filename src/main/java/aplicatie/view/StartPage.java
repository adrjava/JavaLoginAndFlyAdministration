package aplicatie.view;

import aplicatie.controller.MainControllerAudit;
import javax.swing.*;
import java.awt.*;

public  class StartPage extends JFrame {

    public static boolean flagStandby;
    private JPanel buttonPanel;
    private JButton loginButton, registerButton, exitButton;
    private ImageIcon imageBackground;
    private JLabel labelBackground;
    private MainControllerAudit mainControllerAudit = null;

    public StartPage() {

        mainControllerAudit = MainControllerAudit.getInstance();
        flagStandby=false;
        setLayout(null);
        setTitle("Flight Management Application");
        setSize(800, 485);
        setLocationRelativeTo(null);


        initBackground();
        initPanelButtons();
        actionMetode();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initBackground(){

        Image image = new ImageIcon(".//src/main/java/aplicatie/fly2.jpg")
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
        initRegisterButton();
        initExitButton();

    }

    private void initLoginButton(){
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
    }

    private    void initRegisterButton(){
       registerButton = new JButton("Register");
       buttonPanel.add(registerButton);
    }

    private void initExitButton(){
        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);
    }

    private void actionMetode() {

        loginButton.addActionListener(event -> {

            if(!flagStandby) {
                flagStandby = true;
                startStandby();

            }
            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("login");
            if(mainControllerAudit.getJFrame("login")==null){
                LoginPage loginPage = new LoginPage();
                mainControllerAudit.setJFrame("login", loginPage);
            }else{
                mainControllerAudit.getJFrame("login").setVisible(true);
            }
            dispose();

        });

        registerButton.addActionListener(event -> {

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("registerUser");

            if(mainControllerAudit.getJFrame("registerUser")==null){

                RegisterUserPage registerUserPage = new RegisterUserPage();
                mainControllerAudit.setJFrame("registerUser", registerUserPage);

            }else{

                mainControllerAudit.getJFrame("registerUser").setVisible(true);

            }

            if(!flagStandby) {
                flagStandby = true;
                startStandby();
            }
            dispose();

        });

        exitButton.addActionListener(event ->{

            MenuBar.flagTimeData = false;
            flagStandby = false;
            dispose();

        });

    }

    public void startStandby(){

        Thread standby = new Thread() {
            @Override
            public void run() {

                try {
                   System.out.println(" thread standby pornit");
                   while (flagStandby) {
                        if (mainControllerAudit.getRestTime()){
                            sleep(60000);
                        }else{

                            standBy();

                        }
                   }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        standby.start();

    }

    private void standBy(){

        mainControllerAudit.resetOraChangePage();
        mainControllerAudit.getJFrame("login").setVisible(true);
        String activePage = mainControllerAudit.getActivePage();
        JFrame activeJFrame = mainControllerAudit.getJFrame(activePage);
        activeJFrame.dispose();

    }

    public static void main(String[] args) {
        new StartPage();
    }
}

