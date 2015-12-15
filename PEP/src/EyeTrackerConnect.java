import java.awt.List;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EyeTrackerConnect {

	public static boolean running = true;
	public static ArrayList<Point2D> coordinates = new ArrayList<Point2D>();
	private static Socket clientSocket = new Socket();
	
	private static DataOutputStream outToTracker;
	private static BufferedReader inFromTracker;
	public static void eye_tracker_calibrate() throws IOException{

		
		try{
			clientSocket = new Socket("127.0.0.1", 4242);
		}
		catch(Exception e){
			System.out.println("Unable to connect to server");
			System.exit(1);
		}
		System.out.println("Connected to server");


		//get reference to the socket's output stream		
		DataOutputStream outToTracker = new DataOutputStream(clientSocket.getOutputStream());

		//set up input stream filters
		BufferedReader inFromTracker = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		// Send configuration commands
		String str = null;
		String response = null;

		// Show the calibration screen
		str = "<SET ID=\"CALIBRATE_SHOW\" STATE=\"1\" />\r\n";
		outToTracker.writeBytes(str);
		System.out.println(inFromTracker.readLine()+ "\n");

		// Begin the calibration process
		str = "<SET ID=\"CALIBRATE_START\" STATE=\"1\" />\r\n";
		outToTracker.writeBytes(str);
		System.out.println(inFromTracker.readLine()+ "\n");

		FileWriter outFile = new FileWriter("out.txt");
		try{
			PrintWriter out = new PrintWriter(outFile);
			while(running && (response = inFromTracker.readLine()).length() != 0){
				out.println(response);	
			}

			out.close();
		}catch(Exception e){
			System.out.println("Unable to open file");
			e.printStackTrace();
			//System.exit(1);
		}		 	
		outToTracker.close();
		inFromTracker.close(); 
		clientSocket.close();
	}

	public static void eye_tracker_connect(int sWidth, int sHeight) throws IOException{

		try{
			clientSocket = new Socket("127.0.0.1", 4242);
		}
		catch(Exception e){
			System.out.println("Unable to connect to server");
			System.exit(1);
		}
		System.out.println("Connected to server");


		//get reference to the socket's output stream		
		outToTracker = new DataOutputStream(clientSocket.getOutputStream());

		//set up input stream filters
		inFromTracker = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		// Send configuration commands
		String str = null;
		String response = null;

		//Enable the sending of the fixation point-of-gaze data
		str = "<SET ID=\"ENABLE_SEND_POG_BEST\" STATE=\"1\" />\r\n";
		outToTracker.writeBytes(str);
		System.out.println(inFromTracker.readLine()+ "\n");

		//this variable begins the continuous sending of the eye-gaze data records.
		str = "<SET ID=\"ENABLE_SEND_DATA\" STATE=\"1\" />\r\n";
		outToTracker.writeBytes(str);
		System.out.println(inFromTracker.readLine()+ "\n");

		FileWriter outFile = new FileWriter("out.txt");
		try{
			PrintWriter out = new PrintWriter(outFile);
			while(running && (response = inFromTracker.readLine()).length() != 0){
				out.println(response);	
				parse_and_add_point(response, sWidth, sHeight);
			}

			out.close();
		}catch(Exception e){
			System.out.println("Unable to open file");
			e.printStackTrace();
			System.exit(1);
		}		 	
		outToTracker.close();
		inFromTracker.close(); 
		clientSocket.close();
	}


	public static void parse_and_add_point(String response, int sWidth, int sHeight){
		//Define a pattern for the input to extract the coordinates
		Pattern p = Pattern.compile("<REC BPOGX=\"([-+]?[0-9]*\\.?[0-9]+)\" BPOGY=\"([-+]?[0-9]*\\.?[0-9]+)\" BPOGV=\"([0-9]*)\"/>");
		Matcher myMatcher = p.matcher(response);
		boolean matchFound = myMatcher.find();
		Point2D screen_coordinate;
		if (matchFound) {
			//System.out.println("found one match");
			double bpogx = Double.parseDouble(myMatcher.group(1));
			double bpogy = Double.parseDouble(myMatcher.group(2));
			boolean valid = myMatcher.group(myMatcher.groupCount()).equals("1");
			//we are only going to keep the points with positive coordinates that are valid
			if(valid && 0.0 <= bpogx && bpogx <= 1.0 && 0.0 <= bpogy && bpogy <= 1.0 ){

				double screen_x = bpogx*sWidth;
				double screen_y = bpogy*sHeight;
				screen_coordinate = new Point2D.Double(screen_x,screen_y);
				coordinates.add(screen_coordinate);
			}
		}		
	}	

	public static void stopEyeTracker(){ 
		running = false;
	}
}
