package db;

public class DBUtil {
	// authentification
	// setting of database
    private static final String HOSTNAME = "localhost";
    // MAMP mySQL port
    private static final String PORT_NUM = "3306";
    private static final String DB_NAME = "laiproject";
    // default username and password
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    public static final String URL;
    // concat the string of visiting mySQL
    static {
   	 URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
   			 + "?user=" + USERNAME + "&password=" + PASSWORD;
    }
}