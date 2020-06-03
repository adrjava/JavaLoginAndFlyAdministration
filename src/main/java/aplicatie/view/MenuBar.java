package aplicatie.view;

import aplicatie.controller.MainControllerUser;

import javax.swing.*;
import java.awt.*;
import java.time.ZonedDateTime;

public class MenuBar extends JPanel {
    private JMenuBar menuBar;
    private JLabel userNameLogLabel, timeDataLabel;
    private JMenu mainMenu;
    private JMenuItem dashboardPage;
    private JMenuItem myAccountPage;
    private JMenuItem loginPage;
    public static boolean flagTimeData;
    private MainControllerUser mainControllerUser =null;

    MenuBar(){

        mainControllerUser = MainControllerUser.getInstance();
        flagTimeData = true;

        setBounds(0,0,800,27);
        setBackground(Color.GRAY);
        setLayout(null);

        initMainMenu();
        initViewUserLog();
        initViewDataTime();

    }

    private void initMainMenu(){

        menuBar = new JMenuBar();
        menuBar.setBounds(0,0,85,30);
        mainMenu = new JMenu("Options          ");
        mainMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        initDashBoard();
        initMyAccountPage();
        initLoginPage();

        menuBar.add(mainMenu);
        add(menuBar);
    }

    private void initDashBoard(){
        dashboardPage = new JMenuItem("Main page");
        mainMenu.add(dashboardPage);
    }

    private void initMyAccountPage(){
        myAccountPage = new JMenuItem("My account");
        mainMenu.add(myAccountPage);
    }

    private  void initLoginPage(){
        loginPage = new JMenuItem("Log out");
        mainMenu.add(loginPage);
    }

    public JMenuItem getDashboardPage(){
        return dashboardPage;
    }

    public JMenuItem getMyAccountPage(){
        return myAccountPage;
    }

    public JMenuItem getLoginPage(){
        return  loginPage;
    }

    private void initViewUserLog(){

        userNameLogLabel = new JLabel("user : " + mainControllerUser.getUserNameLog() );
        userNameLogLabel.setForeground(Color.BLACK);
        userNameLogLabel.setBounds(550,5, 100,20);
        add(userNameLogLabel);

    }

    private void initViewDataTime(){

        getDataTime();
        timeDataLabel = new JLabel("dataTime");
        timeDataLabel.setForeground(Color.BLACK);
        timeDataLabel.setBounds(650,5,150,20);
        add(timeDataLabel);

    }


    public void  getDataTime(){

        Thread dataTime = new Thread(){
            @Override
            public void run(){
                try {
                    while (flagTimeData) {
                        ZonedDateTime zonedDateTime = ZonedDateTime.now();
                        int day = zonedDateTime.getDayOfMonth();
                        int month = zonedDateTime.getMonthValue();
                        int year = zonedDateTime.getYear();
                        int second = zonedDateTime.getSecond();
                        int minute = zonedDateTime.getMinute();
                        int hour = zonedDateTime.getHour();
                        timeDataLabel.setText(hour + ":" + minute + ":" + second + "    " + day + "-" + month + "-" + year);
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        dataTime.start();

    }

}






