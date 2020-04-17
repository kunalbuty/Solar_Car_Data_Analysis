import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args) throws SQLException {
        int[][] data=new int[5][3];
        DB.getData(5,1,data);
        for(int i=0;i<5;i++) {
            System.out.println(data[i][0] + " " +data[i][1] + " " +data[i][2] + " ");
        }
    }
}
