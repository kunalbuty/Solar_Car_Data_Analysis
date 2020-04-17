import java.sql.*;

public class DB {
    private static String dbUrl = "jdbc:mysql://localhost:3306/SolarCar?useSSL=false";
    private static String dbUsername = "kunalbuty";
    private static String dbPassword = "mIh2g2tb4$";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            return con;
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static void endConnection(Connection con) {
        try {
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void upload(int batteryTemp,int batteryVOut,int speed,int driveNum) {
        try {
            Connection con=DB.getConnection();
            String query = "INSERT INTO Telemetry (Battery_Temperature,Battery_Voltage_Out,Speed,Drive_Number,Time_Stamp) VALUES (?,?,?,?,NOW()) ;";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, batteryTemp);
            preparedStmt.setInt(2, batteryVOut);
            preparedStmt.setInt(3, speed);
            preparedStmt.setInt(4, driveNum);
            preparedStmt.executeUpdate();
            DB.endConnection(con);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void getData(int numRows,int driveNum, int[][] results) throws SQLException {
        //this will update the results array such that index results[][0]=batteryTemp, [][1]=batteryVout,[][1]=speed
        Connection con=DB.getConnection();
        Statement stmt = con.createStatement();
        String query = "SELECT Battery_Temperature,Battery_Voltage_Out,Speed From Telemetry WHERE Drive_Number =" + driveNum +" LIMIT " + numRows +";";
        ResultSet rs = stmt.executeQuery(query);
        int i=0;
        while( rs.next()) {
            results[i][0]=rs.getInt("Battery_Temperature");
            results[i][1]=rs.getInt("Battery_Voltage_Out");
            results[i][2]=rs.getInt("Speed");
            i++;
        }
        DB.endConnection(con);
    }


}





