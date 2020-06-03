package aplicatie.dao;

import aplicatie.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private String userLog;
    Connection connection;
    PreparedStatement insertQuery;
    PreparedStatement selectQueryVerificaUser;
    PreparedStatement selectQueryVerificaEmail;
    PreparedStatement selectQuery1;
    PreparedStatement selectQueryGetEmail;
    PreparedStatement updateQueryUserName;
    PreparedStatement updateQueryEmail;
    PreparedStatement updateQueryPassword;


    public UserDao(Connection connection) {

        this.connection = connection;
        try {

            updateQueryUserName = connection.prepareStatement( "UPDATE users SET userName = ? WHERE users.userName = ?");
            updateQueryEmail = connection.prepareStatement("UPDATE users SET userEmail = ? WHERE users.userName = ?");
            updateQueryPassword = connection.prepareStatement( "UPDATE users SET userPassword = ? WHERE users.userName = ?");
            insertQuery = connection.prepareStatement("INSERT INTO users VALUES (null, ?,?,?)");
            selectQueryVerificaUser = connection.prepareStatement("SELECT userName  FROM users WHERE userName = ? AND userPassword = ? ");
            selectQueryVerificaEmail = connection.prepareStatement("SELECT userName  FROM users WHERE userEmail = ? AND userPassword = ? ");
            selectQuery1 = connection.prepareStatement("SELECT userName FROM users WHERE userName = ?");
            selectQueryGetEmail = connection.prepareStatement("SELECT userEmail FROM users WHERE userName = ?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert (User user){

        try {

            insertQuery.setString(1, user.getUserName());
            insertQuery.setString(2,user.getUserPassword());
            insertQuery.setString(3,user.getUserEmail());
            return insertQuery.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertNewUserName(String newUserName){
        try {

            updateQueryUserName.setString(1,newUserName);
            updateQueryUserName.setString(2,userLog);
            userLog = newUserName;                                         /*  se actualizeaza  la fiecare schimbare */
            return updateQueryUserName.executeUpdate() !=0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean insertNewEmail(String newEmail){

        try {

            updateQueryEmail.setString(1,newEmail);
            updateQueryEmail.setString(2,userLog);
            return updateQueryEmail.executeUpdate() !=0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean insertNewPassword(String newPassword){

        try {

            updateQueryPassword.setString(1,newPassword);
            updateQueryPassword.setString(2,userLog);
            return updateQueryPassword.executeUpdate() !=0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUser(String userOrEmail, String password) {
        if(!verificaUser(userOrEmail,password)){
            try {

                selectQueryVerificaEmail.setString(1, userOrEmail);
                selectQueryVerificaEmail.setString(2,password);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (ResultSet resultSet = selectQueryVerificaEmail.executeQuery()){

                if(resultSet.next()){
                    userLog = resultSet.getString("userName") ;                                                 /*     se initializeaza la logare   */
                    return true;
                }

            } catch (SQLException e) {
                System.out.println("eroare de conexiune este anuntata in RegisterPage si LoginPage!!!");
            }

        }else return true;
        return false;
    }

    public boolean verificaUser(String user, String password) {

        try {

            selectQueryVerificaUser.setString(1, user);
            selectQueryVerificaUser.setString(2,password);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (ResultSet resultSet1 = selectQueryVerificaUser.executeQuery()){

            if(resultSet1.next()){
                userLog = user;                                                  /*     se initializeaza la logare   */
                return true;
            }

        } catch (SQLException e) {
            System.out.println("eroare de conexiune este anuntata in RegisterPage si LoginPage!!!");
        }
        return false;
    }

    public boolean checkUserName(String userName){

        try {

            selectQuery1.setString(1, userName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet resultSet = selectQuery1.executeQuery()){

            if(resultSet.next()){
                userLog = userName;
                return true;
            }

        } catch (SQLException e) {
            System.out.println("eroare de conexiune este anuntata in RegisterPage si LoginPage!!!");
        }
        return false;

    }

    public String getUserLog(){
        return userLog;
    }

    public String getEmailUserLog(){

        try {

            selectQueryGetEmail.setString(1, userLog);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (ResultSet resultSet = selectQueryGetEmail.executeQuery()){

            if(resultSet.next()){
                return  resultSet.getString("userEmail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }return "nulll";
    }

}