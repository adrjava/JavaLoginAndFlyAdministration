package aplicatie.view;


import aplicatie.controller.MainControllerAudit;
import aplicatie.controller.MainControllerUser;
import aplicatie.model.User;
import javax.swing.*;
import java.awt.*;

public  class RegisterUserPage extends JFrame {

    private JPanel buttonPanel, registerPanel;
    private JButton registerButton, exitButton, resetButton, beakButton;
    private ImageIcon imageBackground, imageIcon;
    private JLabel labelBackground, labelIcon;
    private JLabel usernameLabel, passwordLabel, passwordLabelConfirm, emailLabel;
    private JTextField emailField,usernameField;
    private JPasswordField passwordField, passwordFieldConfirm;
    public MainControllerUser mainControllerUser = null;
    public MainControllerAudit mainControllerAudit = null;

    public RegisterUserPage() {

        mainControllerUser = MainControllerUser.getInstance();
        mainControllerAudit = MainControllerAudit.getInstance();

        setLayout(null);
        setTitle("Flight Management Application");
        setSize(800, 485);
        setLocationRelativeTo(null);

        initBackground();
        initPanelButtons();
        initRegisterPanel();
        actionMetode();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initBackground() {

        Image image = new ImageIcon(".//src/main/java/aplicatie/fly3.jpg")
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
        registerButton = new JButton("Register");
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

        usernameLabel = new JLabel("Enter user name:");
        usernameLabel.setBounds(10, 5, 200, 20);
        registerPanel.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setBounds(10, 30, 200, 20);
        registerPanel.add(usernameField);

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

    private void initEmailRegister() {

        emailLabel = new JLabel("Enter Email Adress:");
        emailLabel.setBounds(10, 155, 200, 20);
        registerPanel.add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(10, 180, 200, 20);
        registerPanel.add(emailField);

    }

    private void initIconRegister() {

        Image image = new ImageIcon(".//src/main/java/aplicatie/registerIcon.jpg")
                .getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        labelIcon = new JLabel("", imageIcon, JLabel.CENTER);
        labelIcon.setBounds(330, 50, 150, 70);
        registerPanel.add(labelIcon);

    }

    private void actionMetode() {

        registerButton.addActionListener(e -> {
            mainControllerAudit.resetOraChangePage();
            if (valid()) {

                User user = new User(0,
                        usernameField.getText(),
                        new String(passwordField.getPassword()),
                        emailField.getText());

                if (mainControllerUser.registerUser(user)) {

                    if(mainControllerAudit.insertAudit("register", usernameField.getText())){

                       showMessage("Registration Successful!", JOptionPane.INFORMATION_MESSAGE);
                       usernameField.setText("");
                       passwordField.setText("");
                       passwordFieldConfirm.setText("");
                       emailField.setText("");

                       if(mainControllerAudit.getJFrame("login")==null){
                           LoginPage loginPage = new LoginPage();
                           mainControllerAudit.setJFrame("login", loginPage);
                       }else{
                           mainControllerAudit.getJFrame("login").setVisible(true);
                       }
                       dispose();
                    }
                }else {
                    showMessage("Registration NOT Successful, check the database connection !!!", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        resetButton.addActionListener(event -> {

            mainControllerAudit.resetOraChangePage();
            usernameField.setText("");
            passwordField.setText("");
            passwordFieldConfirm.setText("");
            emailField.setText("");
            showMessage("Fields Reset!", JOptionPane.INFORMATION_MESSAGE);

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

        exitButton.addActionListener(event -> {

                MenuBar.flagTimeData = false;
                StartPage.flagStandby = false;
                dispose();

        });

    }


    private boolean valid() {

        if (mainControllerUser.checkSpaceCredentials(usernameField.getText())) {

            showMessage("Please enter Username", JOptionPane.ERROR_MESSAGE);
            usernameField.requestFocus();
            return false;

        } else if (mainControllerUser.checkUser(usernameField.getText())) {

            showMessage("There is a user with this name. Please another username !!!", JOptionPane.ERROR_MESSAGE);
            usernameField.requestFocus();
            return false;

        } else if (mainControllerUser.checkSpaceCredentials(String.valueOf(passwordField.getPassword()))) {

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

        } else if (mainControllerUser.checkSpaceCredentials(String.valueOf(emailField.getText()))) {

            showMessage("Please enter the same a@y.c Email ", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;

        } else if (!mainControllerUser.checkEmail(String.valueOf(emailField.getText()))) {

            showMessage("Please enter the same a@y.c Email ", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;

        }

        return true;

    }

    public static void showMessage(String msg, int messageType) {

        JOptionPane.showMessageDialog(null, msg, "Warning message", messageType);

    }
}
