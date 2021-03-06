/*-----------------------------------------------------------------------------------------------------------*/
//          Developer 1: Kunal Buty                                                                          //
/*-----------------------------------------------------------------------------------------------------------*/


import java.sql.*;

public class DB {
    private static String dbUrl = "jdbc:mysql://localhost:3306/SolarCar?useSSL=false";

    /*-----------------------------------------------------------------------------------------------------------/*
    //  NOTE: These credentials should be stored in a text file that is not in GitHub for security purposes     //
    /*----------------------------------------------------------------------------------------------------------*/
    private static String dbUsername = "solarCar";
    private static String dbPassword = "zot";


    //initialize a connection to MySQL DB
    private static Connection getConnection() {
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
    //Terminate connection to MySQL DB
    private static void endConnection(Connection con) {
        try {
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    //Upload data into MySQL DB
    public static void upload(int batteryTemp,int batteryVOut,int speed,int driveNum,int batteryCharge) {
        try {
            Connection con=DB.getConnection();
            String query = "INSERT INTO Telemetry (Battery_Temperature,Battery_Voltage_Out,Speed,Drive_Number,Time_Stamp,Battery_Charge) VALUES (?,?,?,?,NOW(),?) ;";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, batteryTemp);
            preparedStmt.setInt(2, batteryVOut);
            preparedStmt.setInt(3, speed);
            preparedStmt.setInt(4, driveNum);
            preparedStmt.setInt(5, batteryCharge);
            preparedStmt.executeUpdate();
            DB.endConnection(con);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    //query database and get the number of rows of data that you want from a particular drive
    static void getData(int numRows, int driveNum, int[][] results) throws SQLException {
        //this will update the results array such that index results[][0]=batteryTemp, [][1]=batteryVout,[][1]=speed
        Connection con=DB.getConnection();
        Statement stmt = con.createStatement();
        String query = "SELECT Battery_Temperature,Battery_Voltage_Out,Speed,Battery_Charge From Telemetry WHERE Drive_Number =" + driveNum +" LIMIT " + numRows +";";
        ResultSet rs = stmt.executeQuery(query);
        int i=0;
        while(rs.next()) {
            results[i][0]=rs.getInt("Battery_Temperature");
            results[i][1]=rs.getInt("Battery_Voltage_Out");
            results[i][2]=rs.getInt("Speed");
            results[i][3]=rs.getInt("Battery_Charge");
            i++;
        }
        DB.endConnection(con);
    }


}





