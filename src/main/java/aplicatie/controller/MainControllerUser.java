package aplicatie.controller;

import aplicatie.dao.UserDao;
import aplicatie.model.User;

import java.sql.Connection;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainControllerUser {

    private UserDao userDao;
    private static MainControllerUser SINGLETON = null;

    private MainControllerUser(){

        Connection connection = DatabaseConnection.getConnection();
        userDao = new UserDao(connection);

    }

    public static MainControllerUser getInstance(){

        if (SINGLETON == null) {
            SINGLETON = new MainControllerUser();
            return SINGLETON;
        }else{
            return SINGLETON;
        }

    }

    public ZonedDateTime getDateTime(){
        return ZonedDateTime.now();
    }

    public boolean registerUser(User user){
        return  userDao.insert(user);
    }

    public boolean insertUserName(String userName){ return userDao.insertNewUserName(userName); }

    public boolean insertEmail(String userEmail){ return userDao.insertNewEmail(userEmail); }

    public boolean insertPassword(String userPassword){ return userDao.insertNewPassword(userPassword); }

    public String getUserNameLog(){ return userDao.getUserLog();}

    public String getEmailUserLog(){ return userDao.getEmailUserLog();}

    public boolean checkUser(String userName){
        return  userDao.checkUserName(userName);
    }

    public boolean checkSamePasswords(String password, String passwordConfirm){
        return password.equals(passwordConfirm);
    }

    public boolean checkSpaceCredentials(String credentials){
        return credentials.equals("");
    }

    public  boolean  verificaLogin (String username, String password){
        return  userDao.checkUser(username, password);
    }


    public boolean checkPasswords(String password){

        int lower = 0, upper = 0, digits = 0;
        char [] arrayPassword = password.toCharArray();
        if (arrayPassword.length <= 6) return false;
        for(char ch : arrayPassword){
            if(Character.isDigit(ch))  digits++;
            if(Character.isUpperCase(ch))  upper++;
            if(Character.isLowerCase(ch))  lower++;
        }
        if(digits > 0)
          if(upper > 0)
           if(lower > 0) return true;
        return false;

    }

    public boolean checkEmail(String email){

        String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{1,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence) email);
        return matcher.matches();

    }

}
