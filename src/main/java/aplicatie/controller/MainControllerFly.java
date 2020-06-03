package aplicatie.controller;

import aplicatie.dao.FlyDao;
import aplicatie.model.Fly;
import java.sql.Connection;
import java.util.List;

public class MainControllerFly {

    private FlyDao flyDao;
    private static MainControllerFly SINGLETON = null;

    private MainControllerFly(){
        Connection connection = DatabaseConnection.getConnection();
        flyDao = new FlyDao(connection);
    }

    public static MainControllerFly getInstance(){
        if (SINGLETON == null) {
            SINGLETON = new MainControllerFly();
            return SINGLETON;
        }else{
            return SINGLETON;
        }
    }

    public boolean registerFly(Fly fly){
        return flyDao.registerFly(fly);
    }

    public boolean checkDepartureDestination(String departureOrDestination){return departureOrDestination.length()<3;}

    public boolean checkTime(String time){return flyDao.checkTime(time);}

    public boolean checkDestination(String source,String destination){ return flyDao.checkDestination(source,destination);}

    public boolean checkPrice(String price){return flyDao.checkPrice(price);}

    public String getOraSosire(String oraPlecare, String durata){return flyDao.getOraSosire(oraPlecare,durata);}

    public List<Fly> getAllFly(){
        return flyDao.getAllFly();
    }

    public boolean deleteFly(int id){
        return flyDao.deleteFly(id);
    }

}
