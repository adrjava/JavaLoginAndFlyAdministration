package aplicatie.view;

import aplicatie.controller.MainControllerAudit;
import aplicatie.controller.MainControllerFly;
import aplicatie.controller.MainControllerUser;
import javax.swing.*;
import java.awt.*;

public class DashboardPage extends JFrame {

    private JPanel   registerPanel, buttonPanel;
    private ImageIcon imageBackground;
    private JLabel labelBackground;
    private JButton insertFlyButton, beakButton, exitButton;
    private MainControllerUser mainControllerUser = null;
    private MainControllerAudit mainControllerAudit = null;
    private MainControllerFly mainControllerFly = null;
    private MenuBar menuBar = null;
    private TablePanel tablePanel = null;

    public DashboardPage(){

        mainControllerUser = MainControllerUser.getInstance();
        mainControllerAudit = MainControllerAudit.getInstance();
        mainControllerFly =MainControllerFly.getInstance();

        menuBar = new MenuBar();

        tablePanel = new TablePanel();

        setLayout(null);
        setTitle("Flight Management Application");
        setSize(800, 485);
        setLocationRelativeTo(null);


        initBackground();
        labelBackground.add(menuBar);
        initPanelButtons();
        initRegisterFlyPanel();
        actionMethod();

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
        initInsertFlyButton();
        initBeakButton();
        initExitButton();

    }

    private void initInsertFlyButton(){
        insertFlyButton = new JButton("Insert New Fly");
        buttonPanel.add(insertFlyButton);
    }

    private void initBeakButton(){
        beakButton = new JButton("Beack");
        buttonPanel.add(beakButton);
    }

    private void initExitButton(){
        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);
    }

    private void initRegisterFlyPanel() {

        registerPanel = new JPanel();
        registerPanel.setLayout(new BorderLayout());
        registerPanel.setSize(600, 200);
        registerPanel.setBackground(new Color(0, 0, 0, 80));
        registerPanel.setBounds(20, 70, 740, 280);
        registerPanel.add(tablePanel);
        labelBackground.add(registerPanel);

    }

    public void reactualizeazaTabelul() {
        tablePanel.teRogActualizeazaTabelul();
        registerPanel.add(tablePanel);
    }

    private  void actionMethod(){

        menuBar.getMyAccountPage().addActionListener(event->{
            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("myAccount");
            if (mainControllerAudit.insertAudit("myAccount", mainControllerUser.getUserNameLog())) {
                if(mainControllerAudit.getJFrame("myAccount")==null) {
                    MyAccountPage myAccountPage = new MyAccountPage();
                    mainControllerAudit.setJFrame("myAccount", myAccountPage);
                }else {
                    mainControllerAudit.getJFrame("myAccount").setVisible(true);
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

        exitButton.addActionListener(event ->{

            if(!mainControllerAudit.insertAudit("logout", mainControllerUser.getUserNameLog())){
                JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");
            } else {
                mainControllerAudit.resetOraChangePage();
                MenuBar.flagTimeData= false;
                StartPage.flagStandby = false;
                dispose();
            }

        });

        beakButton.addActionListener(event->{

            String backPage =  mainControllerAudit.getPreviousPage();
            System.out.println(backPage);
            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.getJFrame(backPage).setVisible(true);
            dispose();

        });

        insertFlyButton.addActionListener(event->{

            mainControllerAudit.resetOraChangePage();
            mainControllerAudit.setViewPage("insertFly");
            if (mainControllerAudit.insertAudit("adauga zbor", mainControllerUser.getUserNameLog())) {

                if (mainControllerAudit.getJFrame("insertFly") == null) {
                    InsertFlyPage insertFlyPage = new InsertFlyPage();
                    mainControllerAudit.setJFrame("insertFly", insertFlyPage);
                } else {
                    mainControllerAudit.getJFrame("insertFly").setVisible(true);
                }

            }
            dispose();

        });

    }

}
