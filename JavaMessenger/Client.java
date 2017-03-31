package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {

	private static JTextArea ta;
	private static JTextField tf;
	private static BufferedReader reader;// получение с сервака
	private static PrintWriter writer;// отправка на сервак
	private static String login;

	public static void main(String[] args) {
		
		go();
	}

	private static void go() {
		
		login = JOptionPane.showInputDialog("Введите логин");
		
		JFrame frame = new JFrame("New Telegram 1.0");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		JPanel p = new JPanel();
		ta = new JTextArea(30, 60);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setEditable(false);
		JScrollPane sp = new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tf = new JTextField(40);
		JButton b = new JButton("Отправить");
		b.setPreferredSize(new Dimension(100,20));
		
		b.addActionListener(new send());

		p.add(sp);
		p.add(tf);
		p.add(b);
		setNet();
		
		Thread thread = new Thread(new Listener());
		thread.start();//запуск потока

		frame.getContentPane().add(BorderLayout.CENTER, p);
		frame.setSize(800, 680);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private static void setNet() {
		try {
			Socket sock = new Socket("127.0.0.1", 5000);
			InputStreamReader is = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(is);
			writer = new PrintWriter(sock.getOutputStream());
			writer.flush();
		} catch (Exception ex) {
		}
	}
	
	private static class send implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String msg = login+": "+tf.getText();
			writer.println(msg);
			writer.flush();//закрытие потока для отправки сообщения
			
			tf.setText("");
			tf.requestFocus();
		}
	
	}
	
	private static class Listener implements Runnable{

		@Override
		public void run() {
			String msg;
			try{
				while((msg=reader.readLine())!=null){
					ta.append(msg);
				}
			}catch(Exception ex){}
		}
	
	}

}
