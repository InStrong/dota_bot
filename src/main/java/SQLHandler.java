import java.sql.*;

public class SQLHandler {
    private static final String url = "jdbc:mysql://localhost:3306/dota?ftimeCode=false&serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "";

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            con.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getBio(String name) {
        String query = "SELECT bio from dota.heroes where localized_name='" + name + "'";
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getImage(String name) {
        String query = "SELECT url_vertical_portrait from dota.heroes where localized_name='" + name + "'";
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isHeroExists(String name) {
        String query = "SELECT id from dota.heroes where localized_name='" + name + "'";
        int id=-1;
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                id=rs.getInt(1);
                if (id!=-1) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
