package aplicatie.view;


import aplicatie.controller.MainControllerAudit;
import aplicatie.controller.MainControllerUser;
import javax.swing.*;
import java.awt.*;

public  class ChangePassword extends JFrame {

    private JPanel buttonPanel, registerPanel;
    private JButton registerButton, exitButton, resetButton, beakButton;
    private ImageIcon imageBackground, imageIcon;
    private JLabel labelBackground, labelIcon;
    private JLabel passwordLabel, passwordLabelConfirm, emailLabel, usernameLabel;
    private JPasswordField passwordField, passwordFieldConfirm;
    private MainControllerUser mainControllerUser = null;
    private MainControllerAudit mainControllerAudit = null;
    private MenuBar menuBar;


    public ChangePassword() {

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
        actionMethod();

        if(!mainControllerAudit.insertAudit("modificre parola", mainControllerUser.getUserNameLog())) {
            JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initBackground() {

        Image image = new ImageIcon(".//src/main/java/aplicatie/fly5.jpg")
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
        initRegisterButton();
        initResetButton();
        initBeakButton();
        initExitButton();

    }

    private void initRegisterButton() {

        registerButton = new JButton("Change Password");
        buttonPanel.add(registerButton);

    }

    private void initResetButton() {

        resetButton = new JButton("Reset");
        buttonPanel.add(resetButton);

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
        registerPanel.setBounds(150, 20, 500, 230);
        registerPanel.setLayout(null);
        labelBackground.add(registerPanel);
        initRegisterUserName();
        initRegisterPassword();
        initConfirmRegisterPassword();
        initEmailRegister();
        initIconRegister();

    }

    private void initRegisterUserName() {

        usernameLabel = new JLabel("user name: " + mainControllerUser.getUserNameLog());
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(10, 5, 300, 20);
        registerPanel.add(usernameLabel);

    }

    private void initEmailRegister() {

        emailLabel = new JLabel("Email: " + mainControllerUser.getEmailUserLog());
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(10, 30, 300, 20);
        registerPanel.add(emailLabel);

    }

    private void initRegisterPassword() {

        passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setBounds(10, 55, 200, 20);
        registerPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(10, 80, 200, 20);
        registerPanel.add(passwordField);

    }

    private void initConfirmRegisterPassword() {

        passwordLabelConfirm = new JLabel("Confirm Password:");
        passwordLabelConfirm.setBounds(10, 105, 200, 20);
        registerPanel.add(passwordLabelConfirm);
        passwordFieldConfirm = new JPasswordField();
        passwordFieldConfirm.setBounds(10, 130, 200, 20);
        registerPanel.add(passwordFieldConfirm);

    }

    private void initIconRegister() {

        Image image = new ImageIcon(".//src/main/java/aplicatie/registerIcon.jpg")
                .getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        labelIcon = new JLabel("", imageIcon, JLabel.CENTER);
        labelIcon.setBounds(330, 50, 150, 70);
        registerPanel.add(labelIcon);

    }

    private void actionMethod() {

        registerButton.addActionListener(e -> {

            if (valid()) {
                mainControllerAudit.resetOraChangePage();
                if (mainControllerAudit.insertAudit("modificare parola", mainControllerUser.getUserNameLog())) {

                    if (mainControllerUser.insertPassword( new String(passwordField.getPassword()))) {
                        showMessage("Registration Successful!", JOptionPane.INFORMATION_MESSAGE);
                        passwordField.setText("");
                        passwordFieldConfirm.setText("");
                        mainControllerAudit.setViewPage("login");
                        mainControllerAudit.getJFrame("login").setVisible(true);
                        dispose();
                     } else {
                        showMessage("Registration NOT Successful, check the database connection !!!", JOptionPane.INFORMATION_MESSAGE);
                     }
                }
            }
        });

        menuBar.getMyAccountPage().addActionListener(event->{

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("myAccount");
            if (mainControllerAudit.insertAudit("myAccount ", mainControllerUser.getUserNameLog())) {

                if(mainControllerAudit.getJFrame("myAccount")==null){
                      MyAccountPage myAccountPage = new MyAccountPage();
                      mainControllerAudit.setJFrame("myAccount", myAccountPage);
                }else{
                      mainControllerAudit.getJFrame("myAccount").setVisible(true);
                }
            }
            dispose();

        });

        menuBar.getDashboardPage().addActionListener(event->{

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("dashboard");
            if (mainControllerAudit.insertAudit("principala", mainControllerUser.getUserNameLog())) {

                if (mainControllerAudit.getJFrame("dashboard") == null) {
                    DashboardPage dashboardPage = new DashboardPage();
                    mainControllerAudit.setJFrame("dashboard", dashboardPage);
                } else {
                    mainControllerAudit.getJFrame("dashboard").setVisible(true);
                }

            }
            dispose();

        });

        menuBar.getLoginPage().addActionListener(event->{

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("login");
            mainControllerAudit.getJFrame("login").setVisible(true);
            dispose();

        });

        resetButton.addActionListener(event -> {

            mainControllerAudit.resetOraChangePage();
            passwordFieldConfirm.setText("");
            passwordField.setText("");
            passwordField.requestFocus();
            showMessage("Fields Reset!", JOptionPane.INFORMATION_MESSAGE);

        });

        beakButton.addActionListener(event -> {

            mainControllerAudit.resetOraChangePage();
            String backPage =  mainControllerAudit.getPreviousPage();
            mainControllerAudit.getJFrame(backPage).setVisible(true);
            dispose();

        });

        exitButton.addActionListener(event -> {
            MenuBar.flagTimeData = false;
            StartPage.flagStandby = false;
            dispose();

        });

    }

    private boolean valid() {

        if (mainControllerUser.checkSpaceCredentials(String.valueOf(passwordField.getPassword()))) {
            showMessage("Please enter Password", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;

        } else if (!mainControllerUser.checkPasswords(String.valueOf(passwordField.getPassword()))) {
            showMessage("Please enter Password  min 6 characters, min one little and min one big", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;

        } else if (mainControllerUser.checkSpaceCredentials(String.valueOf(passwordFieldConfirm.getPassword()))) {
            showMessage("Please enter the same Password", JOptionPane.ERROR_MESSAGE);
            passwordFieldConfirm.requestFocus();
            return false;

        } else if (!mainControllerUser.checkSamePasswords(String.valueOf(passwordFieldConfirm.getPassword()),
                (String.valueOf(passwordField.getPassword())))) {
            showMessage("Please enter the same Password", JOptionPane.ERROR_MESSAGE);
            passwordFieldConfirm.requestFocus();
            return false;
        }

        return true;
    }

    public static void showMessage(String msg, int messageType) {
        JOptionPane.showMessageDialog(null, msg, "Warning message", messageType);
    }
}
