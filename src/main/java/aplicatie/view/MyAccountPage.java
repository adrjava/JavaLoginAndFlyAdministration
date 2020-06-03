package aplicatie.view;

import aplicatie.controller.MainControllerAudit;
import aplicatie.controller.MainControllerUser;
import javax.swing.*;
import java.awt.*;

public  class MyAccountPage extends JFrame {

    private JPanel buttonPanel, registerPanel;
    private JButton changePasswordButton, auditButton, changeEmailButton, exitButton, changeUserButton, beakButton;
    private ImageIcon imageBackground, imageIcon;
    private JLabel userNameLabel, emailLabel,labelBackground, labelIcon;
    private JTextField emailField,userNameField;
    private MainControllerUser mainControllerUser ;
    private MainControllerAudit mainControllerAudit ;
    private MenuBar menuBar;

    public MyAccountPage() {

        mainControllerUser = MainControllerUser.getInstance();
        mainControllerAudit = MainControllerAudit.getInstance();
        menuBar = new MenuBar();

        setLayout(null);
        setTitle("Flight Management Application");
        setSize(800, 485);
        setLocationRelativeTo(null);

        initBackground();
        labelBackground.add(menuBar);
        initPanelButtons();
        initRegisterPanel();
        actionMetode();

        if(!mainControllerAudit.insertAudit("pagina principala", mainControllerUser.getUserNameLog())) {
            JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initBackground() {

        Image image = new ImageIcon(".//src/main/java/aplicatie/fly1.jpg")
                .getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);
        imageBackground = new ImageIcon(image);
        labelBackground = new JLabel("", imageBackground, JLabel.CENTER);
        labelBackground.setBounds(0, 0, 800, 450);
        add(labelBackground);

    }

    private void initPanelButtons() {

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 0, 0, 80));
        buttonPanel.setBounds(0, 400, 800, 50);
        buttonPanel.setLayout(new FlowLayout());
        labelBackground.add(buttonPanel);
        initChangePasswordButton();
        initAuditUserLogButton();
        initBeakButton();
        initExitButton();

    }

    private void initChangePasswordButton() {
        changePasswordButton = new JButton("Change Password");
        buttonPanel.add(changePasswordButton);
    }

    private void initAuditUserLogButton(){
        auditButton = new JButton("Audit User Log");
        buttonPanel.add(auditButton);
    }

    private void initBeakButton() {
        beakButton = new JButton("Beak");
        buttonPanel.add(beakButton);
    }

    private void initExitButton() {
        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);
    }

    private void initRegisterPanel() {

        registerPanel = new JPanel();
        registerPanel.setSize(500, 200);
        registerPanel.setBackground(new Color(0, 0, 0, 80));
        registerPanel.setBounds(20, 120, 500, 230);
        registerPanel.setLayout(null);
        labelBackground.add(registerPanel);
        initViewUser();
        initViewEmail();
        initChangeUserName();
        initChangeEmail();
        initIconRegister();

    }

    private void initViewUser(){

        userNameLabel = new JLabel("User name: " + mainControllerUser.getUserNameLog());
        userNameLabel.setForeground(Color.BLACK);
        userNameLabel.setBounds(10, 5, 200, 20);
        registerPanel.add(userNameLabel);

    }

    private void initViewEmail(){

        emailLabel = new JLabel("Email : " + mainControllerUser.getEmailUserLog());
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(10, 30, 200, 20);
        registerPanel.add(emailLabel);

    }

    private void initChangeUserName() {

        changeUserButton = new JButton("Change User");
        changeUserButton.setBounds(10, 55, 200, 20);
        registerPanel.add(changeUserButton);
        userNameField = new JTextField();
        userNameField.setBounds(10, 80, 200, 20);
        registerPanel.add(userNameField);

    }

    private void initChangeEmail() {

        changeEmailButton = new JButton("Change Email");
        changeEmailButton.setBounds(10, 105, 200, 20);
        registerPanel.add(changeEmailButton);
        emailField = new JTextField();
        emailField.setBounds(10, 130, 200, 20);
        registerPanel.add(emailField);

    }

    private void initIconRegister() {

        Image image = new ImageIcon(".//src/main/java/aplicatie/registerIcon.jpg")
                .getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        labelIcon = new JLabel("", imageIcon, JLabel.CENTER);
        labelIcon.setBounds(330, 5, 150, 70);
        registerPanel.add(labelIcon);

    }

    private void actionMetode() {

        changeUserButton.addActionListener(e -> {

            if (valid("changeUser")) {

                if (mainControllerUser.insertUserName(userNameField.getText())) {

                    if(mainControllerAudit.insertAudit("modificare cont", mainControllerUser.getUserNameLog())) {

                        showMessage("Registration Successful!", JOptionPane.INFORMATION_MESSAGE);
                        MyAccountPage myAccountPage = new MyAccountPage();
                        mainControllerAudit.setJFrame("myAccount",myAccountPage);
                        mainControllerAudit.getJFrame("myAccount").setVisible(true);
                        userNameField.setText("");
                        userNameField.requestFocus();
                        dispose();

                    }else {

                        showMessage("Registration NOT Successful, check the database connection !!!", JOptionPane.INFORMATION_MESSAGE);
                        userNameField.setText("");
                        userNameField.requestFocus();

                    }
                }
            }
        });

        changeEmailButton.addActionListener(e -> {

            mainControllerAudit.resetOraChangePage();

            if (valid("changeEmail")) {

                if (mainControllerUser.insertEmail(emailField.getText())) {

                    if (mainControllerAudit.insertAudit("modificare cont", mainControllerUser.getUserNameLog())) {

                        showMessage("Registration Successful!", JOptionPane.INFORMATION_MESSAGE);
                        MyAccountPage myAccountPage = new MyAccountPage();
                        mainControllerAudit.setJFrame("myAccount", myAccountPage);
                        mainControllerAudit.getJFrame("myAccount").setVisible(true);
                        emailField.setText("");
                        emailField.requestFocus();
                        dispose();


                    } else {

                        showMessage("Registration NOT Successful, check the database connection !!!", JOptionPane.INFORMATION_MESSAGE);
                        emailField.setText("");
                        emailField.requestFocus();

                    }
                }
            }
        });

        changePasswordButton.addActionListener(event ->{

            if (mainControllerAudit.insertAudit("modificare parola", mainControllerUser.getUserNameLog())) {

                mainControllerAudit.resetOraChangePage();
                mainControllerAudit.setViewPage("changePassword");
                ChangePassword changePassword = new ChangePassword();
                mainControllerAudit.setJFrame("changePassword", changePassword);
                mainControllerAudit.getJFrame("changePassword").setVisible(true);
                dispose();

            }
        });

        auditButton.addActionListener(event ->{

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("audit");

            if (mainControllerAudit.insertAudit("audit", mainControllerUser.getUserNameLog())) {

                     AuditPage auditPage = new AuditPage();
                     mainControllerAudit.setJFrame("audit", auditPage);

            }
            dispose();

        });

        beakButton.addActionListener(event -> {

            String backPage =  mainControllerAudit.getPreviousPage();
            mainControllerAudit.resetOraChangePage();

            if(mainControllerAudit.getJFrame("dashboard")==null){

                DashboardPage dashboardPage = new  DashboardPage();
                dashboardPage.setVisible(true);
                mainControllerAudit.setJFrame("dashboard", dashboardPage);

            } else {

                mainControllerAudit.getJFrame(backPage).setVisible(true);

            }
            dispose();

        });

        exitButton.addActionListener(event -> {

            if(!mainControllerAudit.insertAudit("logout", mainControllerUser.getUserNameLog())){

                JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");

            } else

                MenuBar.flagTimeData = false;
                StartPage.flagStandby = false;
                dispose();

        });

        menuBar.getDashboardPage().addActionListener(event->{

            if (mainControllerAudit.insertAudit("principala ", mainControllerUser.getUserNameLog())) {

                mainControllerAudit.resetOraChangePage();
                mainControllerAudit.setViewPage("dashboard");
                DashboardPage dashboardPage = new DashboardPage();
                mainControllerAudit.setJFrame("dashboard", dashboardPage);
                mainControllerAudit.getJFrame("dashboard").setVisible(true);
                dispose();

            }
        });

        menuBar.getLoginPage().addActionListener(event->{

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("login");
            mainControllerAudit.getJFrame("login").setVisible(true);
            dispose();

        });
    }

    private boolean valid(String change) {

        switch (change) {

            case "changeUser":

                 if (mainControllerUser.checkSpaceCredentials(userNameField.getText())) {

                      showMessage("Please enter Username", JOptionPane.ERROR_MESSAGE);
                      userNameField.requestFocus();
                      return false;

                 } else if (mainControllerUser.checkUser(userNameField.getText())) {

                      showMessage("There is a user with this name. Please another username !!!", JOptionPane.ERROR_MESSAGE);
                      userNameField.requestFocus();
                      return false;
                 }
                 break;
            case "changeEmail":

                 if (mainControllerUser.checkSpaceCredentials(String.valueOf(emailField.getText()))) {

                     showMessage("Please enter the same a@y.c Email ", JOptionPane.ERROR_MESSAGE);
                     emailField.requestFocus();
                     return false;

                 } else if (!mainControllerUser.checkEmail(String.valueOf(emailField.getText()))) {

                     showMessage("Please enter the same a@y.c Email ", JOptionPane.ERROR_MESSAGE);
                     emailField.requestFocus();
                     return false;

                 }

                 break;

            default: return true;
        }

        return true;
    }

    public static void showMessage(String msg, int messageType) {
        JOptionPane.showMessageDialog(null, msg, "Warning message", messageType);
    }

}
