package aplicatie.controller;

import aplicatie.dao.AuditDao;
import aplicatie.model.Audit;
import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

public class MainControllerAudit {

    private HashMap<String,JFrame> listJFrame;
    private ArrayDeque<String> listDequeBackView;
    private AuditDao auditDao;
    private static MainControllerAudit SINGLETON = null;

    private MainControllerAudit(){

        Connection connection = DatabaseConnection.getConnection();
        auditDao = new AuditDao(connection);
        listDequeBackView = new ArrayDeque<>();
        listJFrame = new HashMap<>();

    }

    public static MainControllerAudit getInstance(){

        if (SINGLETON == null) {
            SINGLETON = new MainControllerAudit();
            return SINGLETON;
        }else{
            return SINGLETON;
        }

    }


    public List<Audit> getAuditUserLog(String userNameLog){
        return auditDao.getAuditUserLog(userNameLog);
    }

    public  boolean  insertAudit(String viewPage, String userName) {
        return auditDao.insertAudit(viewPage, userName);
    }

    public  boolean getRestTime(){return auditDao.getRestTime();}

    public void resetOraChangePage(){auditDao.resetOraChangePage();}

    public String getPreviousPage(){

        listDequeBackView.pollLast();
        return listDequeBackView.getLast();

    }

    public String getActivePage(){
        return listDequeBackView.getLast();
    }

    public void setViewPage(String viewPage){
        listDequeBackView.offer(viewPage);
    }

    public JFrame getJFrame(String jFrame){
        return listJFrame.get(jFrame);
    }

    public  void setJFrame(String jFrame, JFrame instanceJFrame){
        listJFrame.put(jFrame,instanceJFrame);
    }

}
