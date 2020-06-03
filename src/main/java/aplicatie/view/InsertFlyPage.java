package aplicatie.view;

import aplicatie.controller.MainControllerAudit;
import aplicatie.controller.MainControllerFly;
import aplicatie.controller.MainControllerUser;
import aplicatie.model.Fly;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InsertFlyPage extends JFrame {

    private JPanel buttonPanel, registerPanel;
    private JButton insertFlyButton, cancelButton, beakButton, exitButton;
    private ImageIcon imageBackground;
    private JLabel labelBackground;
    private JLabel sursaLabel, destinatieLabel, oraPlecareLabel, durataZborLabel, pretZborLabel;
    private JTextField sursaField, destinatieField, oraPlecareField, durataZborField, pretZborField;
    final List<String> zile = Arrays.asList("luni", "marti", "miercuri", "joi", "vineri", "sambata", "duminica");
    private JCheckBox checkBox;
    private List <JCheckBox> box;
    private MainControllerFly mainControllerFly = null;
    private MainControllerAudit mainControllerAudit = null;
    private MainControllerUser mainControllerUser = null;
    private MenuBar menuBar = null;
    private TablePanel tablePanel = null;
    private DashboardPage dashboardPage = null;


    public InsertFlyPage() {

        mainControllerFly = MainControllerFly.getInstance();
        mainControllerAudit = MainControllerAudit.getInstance();
        mainControllerUser = MainControllerUser.getInstance();
        menuBar = new MenuBar();
        tablePanel = new TablePanel();


        setLayout(null);
        setTitle("Flight Management Application");
        setSize(800, 485);
        setLocationRelativeTo(null);

        initBackground();
        labelBackground.add(menuBar);
        initPanelButtons();
        initRegisterPanel();
        actionMetode();

        if(!mainControllerAudit.insertAudit("adauga zbor", mainControllerUser.getUserNameLog())) {
            JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initBackground() {

        Image image = new ImageIcon(".//src/main/java/aplicatie/fly4.jpg")
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
        initCancelButton();
        initBeakButton();
        initExitButton();

    }

    private void initRegisterButton() {

        insertFlyButton = new JButton("Register");
        buttonPanel.add(insertFlyButton);

    }

    private void initCancelButton() {

        cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);

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
        registerPanel.setSize(500, 270);
        registerPanel.setBackground(new Color(0, 0, 0, 80));
        registerPanel.setBounds(150, 50, 500, 270);
        registerPanel.setLayout(null);
        labelBackground.add(registerPanel);
        initDepartureRegister();
        initRegisterPassword();
        initStartTimeRegister();
        initDurationRegister();
        initPriceRegister();
        initCheckBox();

    }

    private void initDepartureRegister() {

        sursaLabel = new JLabel("Introdu locul de plecare:");
        sursaLabel.setBounds(10, 5, 200, 20);
        sursaLabel.setForeground(Color.WHITE);
        registerPanel.add(sursaLabel);
        sursaField = new JTextField();
        sursaField.setBounds(10, 30, 200, 20);
        registerPanel.add(sursaField);

    }

    private void initRegisterPassword() {

        destinatieLabel = new JLabel("Introdu locul de sosire:");
        destinatieLabel.setBounds(10, 55, 300, 20);
        destinatieLabel.setForeground(Color.WHITE);
        registerPanel.add(destinatieLabel);
        destinatieField = new JTextField();
        destinatieField.setBounds(10, 80, 200, 20);
        registerPanel.add(destinatieField);

    }

    private void initStartTimeRegister() {

        oraPlecareLabel = new JLabel("Introdu ora de plecare  sub forma 'HH:mm': ");
        oraPlecareLabel.setBounds(10, 105, 300, 20);
        oraPlecareLabel.setForeground(Color.WHITE);
        registerPanel.add(oraPlecareLabel);
        oraPlecareField = new JTextField();
        oraPlecareField.setBounds(10, 130, 200, 20);
        registerPanel.add(oraPlecareField);

    }

    private void initDurationRegister() {

        durataZborLabel = new JLabel("Introdu durata zborului sub forma 'HH:mm': ");
        durataZborLabel.setBounds(10, 155, 300, 20);
        registerPanel.add(durataZborLabel);
        durataZborField = new JTextField();
        durataZborField.setBounds(10, 180, 200, 20);
        registerPanel.add(durataZborField);

    }

    private void initPriceRegister() {

        pretZborLabel = new JLabel("Introdu pretul zborului in lei:");
        pretZborLabel.setBounds(10, 205, 200, 20);
        registerPanel.add(pretZborLabel);
        pretZborField = new JTextField();
        pretZborField.setBounds(10, 230, 200, 20);
        registerPanel.add(pretZborField);

    }

    private void initCheckBox() {

        box = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            checkBox = new JCheckBox(zile.get(i));
            checkBox.setOpaque(true);
            checkBox.setBounds(350, 30 + (23 * i), 100, 25);
            registerPanel.add(checkBox);
            box.add(checkBox);
        }

    }

    private String getZile() {

        String zilele = "";
        for (int i = 0; i < 7; i++) {
            checkBox = box.get(i);
            if (checkBox.isSelected()) {
                if (zilele.equals("")) zilele = zilele.concat(zile.get(i));
                else zilele = zilele.concat(", " + zile.get(i));
            }
        }

        return zilele;

    }

    private void actionMetode() {

        insertFlyButton.addActionListener(e -> {

            mainControllerAudit.resetOraChangePage();

            if (valid()) {

                Fly fly = new Fly(
                        0,
                        sursaField.getText(),
                        destinatieField.getText(),
                        oraPlecareField.getText(),
                        mainControllerFly.getOraSosire(oraPlecareField.getText(), durataZborField.getText()),
                        getZile(),
                        Integer.parseInt(pretZborField.getText()));

                if (mainControllerFly.registerFly(fly)) {
                    if (mainControllerAudit.insertAudit("zbor nou", mainControllerUser.getUserNameLog())) {

                        showMessageFly("Registration Successful!", JOptionPane.INFORMATION_MESSAGE);
                        mainControllerAudit.setViewPage("dashboard");
                        sursaField.setText("");
                        destinatieField.setText("");
                        oraPlecareField.setText("");
                        durataZborField.setText("");
                        pretZborField.setText("");

                        mainControllerAudit.getJFrame("dashboard").setVisible(true);
                        DashboardPage dashboardPage = (DashboardPage) mainControllerAudit.getJFrame("dashboard");
                        dashboardPage.reactualizeazaTabelul();

                        dispose();

                    }
                } else {
                        showMessageFly("Registration NOT Successful, check the database connection !!!", JOptionPane.INFORMATION_MESSAGE);
                }


            }

        });

        menuBar.getMyAccountPage().addActionListener(event->{

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("myAccount");
            if (mainControllerAudit.insertAudit("myAccount ", mainControllerUser.getUserNameLog())) {

                if (mainControllerAudit.getJFrame("myAccount") == null) {
                    MyAccountPage myAccountPage = new MyAccountPage();
                    mainControllerAudit.setJFrame("myAccount", myAccountPage);
                } else {
                    mainControllerAudit.getJFrame("myAccount").setVisible(true);
                }
            }
            dispose();

        });

        menuBar.getDashboardPage().addActionListener(event->{

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("dashboard");
            if (mainControllerAudit.insertAudit("principala ", mainControllerUser.getUserNameLog())) {

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

        cancelButton.addActionListener(event -> {

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.getJFrame("dashboard").setVisible(true);
            dispose();

        });

        beakButton.addActionListener(event -> {

            String backPage =  mainControllerAudit.getPreviousPage();
            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.getJFrame(backPage).setVisible(true);
            dispose();

        });

        exitButton.addActionListener(event -> {

            if(!mainControllerAudit.insertAudit("logout", mainControllerUser.getUserNameLog())){
                showMessageFly("Verificati conexiunea la baza de date !!!", JOptionPane.ERROR_MESSAGE);
            } else
                MenuBar.flagTimeData = false;
                StartPage.flagStandby = false;
                dispose();

        });

    }

    private boolean valid() {

        if (mainControllerFly.checkDepartureDestination(sursaField.getText())) {
            showMessageFly("Locul de plecare trebuie sa contina minim trei caractere!!! ", JOptionPane.ERROR_MESSAGE);
            sursaField.requestFocus();
            return false;

        } else if (mainControllerFly.checkDepartureDestination(destinatieField.getText())) {
            showMessageFly("Locul de sosire trebuie sa contina minim trei caractere!!! ", JOptionPane.ERROR_MESSAGE);
            destinatieField.setText("");
            destinatieField.requestFocus();
            return false;

        } else if (mainControllerFly.checkDestination(sursaField.getText(), destinatieField.getText())) {
            showMessageFly("Aceasta  ruta exista !!! ", JOptionPane.ERROR_MESSAGE);
            destinatieField.setText("");
            destinatieField.requestFocus();
            return false;

        }else if (mainControllerFly.checkTime(oraPlecareField.getText())) {
            showMessageFly("Ora de plecare trebuie sa fie de forma HH:mm !!! ", JOptionPane.ERROR_MESSAGE);
            oraPlecareField.setText("");
            oraPlecareField.requestFocus();
            return false;

        }else if (mainControllerFly.checkTime(durataZborField.getText())) {
            showMessageFly("Durata zborului trebuie sa fie de forma HH:mm !!! ", JOptionPane.ERROR_MESSAGE);
            durataZborField.setText("");
            durataZborField.requestFocus();
            return false;

        }else if (mainControllerFly.checkPrice(pretZborField.getText())) {
            showMessageFly("Pretul trebuie sa fie intreg, pozitiv si diferit de zero !!! ", JOptionPane.ERROR_MESSAGE);
            pretZborField.setText("");
            pretZborField.requestFocus();
            return false;

        }

        return true;
    }

    public static void showMessageFly(String msg, int messageType) {

        JOptionPane.showMessageDialog(null, msg, "Warning message", messageType);

    }

}
