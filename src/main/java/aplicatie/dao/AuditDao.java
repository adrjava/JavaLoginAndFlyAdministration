package aplicatie.dao;

import aplicatie.model.Audit;
import java.sql.*;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import static java.time.temporal.ChronoUnit.MINUTES;


public class AuditDao {

    Connection connection;
    private int id;
    private LocalTime oraChangePage;
    PreparedStatement selectQueryHistoryUserLog;
    PreparedStatement selectQueryGetIdUserLog;
    PreparedStatement insertQueryAudit;

    public AuditDao(Connection connection) {

        this.connection = connection;
        oraChangePage = LocalTime.now();


        try {

            selectQueryHistoryUserLog = connection.prepareStatement("SELECT * FROM history WHERE user_id = ?");
            selectQueryGetIdUserLog = connection.prepareStatement("SELECT id FROM users WHERE  userName = ?");
            insertQueryAudit = connection.prepareStatement("INSERT INTO history VALUES (null,null,?,?)" );

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getIdUserNameLog(String userName){

        try {

            selectQueryGetIdUserLog.setString(1,userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            ResultSet resultSet = selectQueryGetIdUserLog.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public List<Audit> getAuditUserLog(String userNameLog){

        List <Audit>listAuditUserLog = new ArrayList<>();
        id = getIdUserNameLog(userNameLog);
        try {
            selectQueryHistoryUserLog.setInt(1,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet resultSet = selectQueryHistoryUserLog.executeQuery()){

            while (resultSet.next()) {

                String data = resultSet.getDate("register_date").toString();
                LocalTime ora = resultSet.getTime("register_date").toLocalTime().minusHours(2);
                String dataOra = data.concat(" la ora  " + ora.toString());
                listAuditUserLog.add( new Audit(
                            id,
                            dataOra,
                            resultSet.getString("frame_page")));

            }

            listAuditUserLog = listAuditUserLog.parallelStream()
                   .sorted(Comparator.comparing(Audit::getRegister_date).reversed())
                   .collect(Collectors.toList());

            return listAuditUserLog;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listAuditUserLog;
    }

    public boolean insertAudit(String viewPage, String userName){

        id = getIdUserNameLog(userName);
        try {

            insertQueryAudit.setString(1, viewPage );
            insertQueryAudit.setInt(2, id );
            resetOraChangePage();
            return  insertQueryAudit.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean getRestTime(){

        LocalTime localTimeNow = LocalTime.now();
        long time = MINUTES.between(oraChangePage,localTimeNow);
        return time < 15;

    }

    public void resetOraChangePage(){
        oraChangePage = LocalTime.now();
    }

}
