package aplicatie.view;

import aplicatie.controller.MainControllerAudit;
import aplicatie.controller.MainControllerUser;
import aplicatie.model.Audit;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AuditPage extends JFrame {

    MainControllerAudit mainControllerAudit = MainControllerAudit.getInstance();
    MainControllerUser mainControllerUser = MainControllerUser.getInstance();
    private JList <String>jListAudit;
    private DefaultListModel<String> modelAudit;
    private ImageIcon imageBackground;
    private JLabel labelBackground;
    private JPanel panelAudit, buttonPanel;
    private JButton  beakButton, exitButton;
    private JScrollPane jScrollPaneAudit;

    public AuditPage(){

        setLayout(null);
        setTitle("Flight Management Application");
        setSize(800, 485);
        setLocationRelativeTo(null);

        initBackground();
        initPanelAudit();
        initPanelButtons();
        actionMetode();

        setVisible(true);

        if(!mainControllerAudit.insertAudit("audit", mainControllerUser.getUserNameLog())) {
            JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");
        }

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

    private void initPanelAudit(){

        panelAudit = new JPanel(new BorderLayout());
        initJListAudit();

    }

    private void initJListAudit(){

        modelAudit = new DefaultListModel<>();
        List<Audit> audits = mainControllerAudit.getAuditUserLog(mainControllerUser.getUserNameLog());
        modelAudit.clear();
        modelAudit.addElement("audit pentru userul  " + mainControllerUser.getUserNameLog());

        for(Audit audit : audits){
            switch (audit.getFrame_page()){
                case "login" :
                    modelAudit.addElement("login pe " + audit.getRegister_date());
                    break;
                case "logout" :
                    modelAudit.addElement("logout pe " + audit.getRegister_date());
                    break;
                case "register" :
                    modelAudit.addElement("contul a fost creat pe " + audit.getRegister_date());
                    break;
                case "modificare cont" :
                    modelAudit.addElement("contul a fost modificata pe " + audit.getRegister_date());
                    break;
                case "modificre parola" :
                    modelAudit.addElement("parola a fost modificata pe " + audit.getRegister_date());
                    break;
                case "zbor nou" :
                    modelAudit.addElement("zbor nou a fost adaugat pe " + audit.getRegister_date());
                    break;
                case "audit" :
                case "adauga zbor" :
                case "modificare parola" :
                case "principala" :
                case "myAccount" :
                    modelAudit.addElement("a fost accesata pagina " + audit.getFrame_page() + " pe " + audit.getRegister_date());
                    break;

            }


        }

        jListAudit = new JList<>(modelAudit);
        jScrollPaneAudit = new JScrollPane();
        jScrollPaneAudit.setViewportView(jListAudit);
        jListAudit.setLayoutOrientation(JList.VERTICAL);
        jListAudit.setVisibleRowCount(8);
        panelAudit.add(jScrollPaneAudit);
        jListAudit.setBackground(Color.LIGHT_GRAY);
        panelAudit.setBounds(20,20, 500,200);
        labelBackground.add(panelAudit);



        if(!mainControllerAudit.insertAudit("audit", mainControllerUser.getUserNameLog())){
            JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");
        }

    }

    private void initPanelButtons(){

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0,0,0,80));
        buttonPanel.setBounds(0,400,800,50);
        buttonPanel.setLayout(new FlowLayout());
        labelBackground.add(buttonPanel);
        initBeakButton();
        initExitButton();

    }

    private    void initBeakButton(){

        beakButton = new JButton("Beak");
        buttonPanel.add(beakButton);

    }

    private void initExitButton(){

        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);

    }

    private void actionMetode(){

        beakButton.addActionListener(event -> {
            mainControllerAudit.resetOraChangePage();
            String backPage =  mainControllerAudit.getPreviousPage();
            mainControllerAudit.getJFrame(backPage).setVisible(true);
            dispose();

        });

        exitButton.addActionListener(event ->{

            if(!mainControllerAudit.insertAudit("logout", mainControllerUser.getUserNameLog())){
                JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");
            } else
                StartPage.flagStandby = false;
                dispose();
        });
    }

}
