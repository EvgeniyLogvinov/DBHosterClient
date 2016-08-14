package pt.DBHosterClient;

import java.io.IOException;
import javax.swing.JFrame;;

public class Client {

	public static void main(String[] args) throws IOException {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				System.out.println("Welcome to client side");
				JFrame.setDefaultLookAndFeelDecorated(true);

				ClientFrame frame = new ClientFrame();
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}