import java.sql.SQLException;

public class Graph {
    //private int xMax;
    private int yMax;
    private int numPoints;
    private String type;
    private int[][] data;

    public Graph(int numPoints,String type,int driveNum) {
        this.numPoints=numPoints;
        this.type=type;
        data=new int[numPoints][3];
        try {
            DB.getData(numPoints,driveNum,data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int t;
        //following code finds max Y Value
        if(type=="Battery_Temperature") {
            t=0;
        }
        else if(type=="Battery_Voltage_Out") {
            t=1;
        }
        else {
            t=2;
        }
        yMax=0;
        for(int i=0;i<numPoints;i++){
            if(data[i][t]>yMax) {
                yMax=data[i][t];
            }
        }
    }
}
