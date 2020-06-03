package aplicatie.dao;

import aplicatie.model.Fly;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FlyDao {

    Connection connection;
    PreparedStatement insertQueryAddFly;
    PreparedStatement selectQuerySourceDestination;
    PreparedStatement selectQueryAllFlights;
    PreparedStatement deleteQueryFly;

    public FlyDao(Connection connection) {

        this.connection = connection;
        try {

            insertQueryAddFly = connection.prepareStatement("INSERT INTO fly  VALUES (null,?,?,?,?,?,?) ");
            selectQuerySourceDestination = connection.prepareStatement("SELECT * FROM fly WHERE  fly.sursa = ? AND fly.destinatie = ?");
            selectQueryAllFlights = connection.prepareStatement("SELECT * FROM fly");
            deleteQueryFly = connection.prepareStatement("DELETE  FROM fly WHERE fly.id = ?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean registerFly (Fly fly){

        try {

            insertQueryAddFly.setString(1,fly.getSursa().toLowerCase());
            insertQueryAddFly.setString(2,fly.getDestinatie().toLowerCase());
            insertQueryAddFly.setString(3,fly.getOraPlecare());
            insertQueryAddFly.setString(4,fly.getOraSosire());
            insertQueryAddFly.setString(5,fly.getZile());
            insertQueryAddFly.setString(6, String.valueOf(fly.getPret()));
            return insertQueryAddFly.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean checkDestination(String source, String destination){

        if (destination.equalsIgnoreCase(source)) return true;
        try {

            selectQuerySourceDestination.setString(1,source.toLowerCase());
            selectQuerySourceDestination.setString(2,destination.toLowerCase());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet resultSet = selectQuerySourceDestination.executeQuery()){
            return  resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public  boolean checkPrice(String price){

        String priceRegex = "[1-9][0-9]*";
        Pattern pattern = Pattern.compile(priceRegex);
        return  !pattern.matcher(price).matches();

    }

    public boolean checkTime (String time){

        String timeRegex = "([0-1][0-9]|2[0-3]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(timeRegex);
        return !pattern.matcher(time).matches();
    }

    public String getOraSosire(String oraPlecare, String durata){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime t1 = LocalTime.parse(oraPlecare, dateTimeFormatter);
        LocalTime t2 = LocalTime.parse(durata, dateTimeFormatter);
        LocalTime t3 = t1.plusHours(t2.getHour()).plusMinutes(t2.getMinute());
        return t3.format(dateTimeFormatter);

    }

    public List<Fly> getAllFly(){

        List<Fly>listAllFly = new ArrayList<>();
        try(ResultSet resultSet = selectQueryAllFlights.executeQuery()) {

            while(resultSet.next()){

                int id = resultSet.getInt("id");
                String sursa = resultSet.getString("sursa");
                String destinatie = resultSet.getString("destinatie");
                String oraPlecare = resultSet.getString("oraplecare");
                String oraSosire = resultSet.getString("orasosire");
                String zile = resultSet.getString("zile");
                int pret = resultSet.getInt("pret");

                listAllFly.add(new Fly(
                        id,
                        sursa,
                        destinatie,
                        oraPlecare,
                        oraSosire,
                        zile,
                        pret ));

            }

            return listAllFly;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listAllFly;

    }

    public boolean  deleteFly(int id){
        try {

            deleteQueryFly.setInt(1,id);
            return deleteQueryFly.executeUpdate() !=0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

}
