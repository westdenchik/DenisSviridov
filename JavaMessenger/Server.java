package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {

	private static ArrayList streams;
	private static ResultSet rs;
	private static java.sql.Statement st;
	private static Connection c;
	private static PrintWriter writer;

	public static void main(String[] args) throws Exception {

		go();

	}

	private static void go() throws Exception {
		streams = new ArrayList<PrintWriter>();
		setDB();
		try {
			ServerSocket ss = new ServerSocket(5000);
			while (true) {
				Socket sock = ss.accept();
				System.out.println("Got it!");
				writer = new PrintWriter(sock.getOutputStream());
				sendHistory();
				streams.add(writer);

				Thread thread = new Thread(new Listener(sock));
				thread.start();
			}
		} catch (Exception ex) {
		}

	}
	
	

	private static void sendHistory() throws Exception {
		
		ArrayList list= new ArrayList<String>();
		String SQL="SELECT msg FROM `chat`";
		ResultSet rs=st.executeQuery(SQL);
		
		while(rs.next()){
			writer.println(rs.getString("msg"));
			writer.flush();
		}
		
	}

	private static void tellEveryone(String msg) throws Exception{
		int x = msg.indexOf(':');
		String login = msg.substring(0, x);
		
		save(login,msg);
		
		java.util.Iterator<PrintWriter> it = streams.iterator();
		while(it.hasNext()){
			try{
				writer =  it.next();
				writer.println(msg);
				writer.flush();
			}catch(Exception ex){}
		}
	}
	
	
	private static void setDB() throws Exception {
		
		String url="jdbc:mysql://localhost:3306/messenger";
		String login="root";
		String pass="";
		Class.forName("com.mysql.jdbc.Driver");
		c=DriverManager.getConnection(url ,login, pass);
		st=c.createStatement();
		
	}
	
	
	private static void save(String login, String msg) throws Exception {
		
		String SQL="INSERT INTO `chat` ( `login`, `msg`) VALUES ( '"+login+"', '"+msg+"');";
		st.executeUpdate(SQL);
		
	}

	private static class Listener implements Runnable {
		
		BufferedReader reader;
		
		Listener(Socket sock) {
			InputStreamReader is;
			try {
				is = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(is);
			} catch (Exception ex) {}
		}

		@Override
		public void run() {
			String msg;
			try{
				while((msg=reader.readLine())!=null){
					System.out.println(msg);
					tellEveryone(msg);
				}
			}catch(Exception ex){}
		}

	}
}
