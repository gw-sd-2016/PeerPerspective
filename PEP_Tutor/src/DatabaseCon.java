
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseCon {
		private Connection connect = null;
		public void DatabseConnect() throws Exception {
			
		}
		
		public void upload(ArrayList<String[]> data) throws ClassNotFoundException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection("jdbc:mysql://sausingh.seas.gwu.edu:3306/sausingh","sausingh","s3cr3t201e");
				
				for (int i = 0; i< data.size(); i++ ) {
					String[] dataSet = data.get(i);
					Statement st = (Statement) connect.createStatement(); 
					st.executeUpdate("INSERT INTO datapoints VALUES (1, sausingh,"+dataSet[0]+","+dataSet[1]+","+dataSet[2]+","+dataSet[3]+"");
				}
				
			} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
			}
		}
		
}
