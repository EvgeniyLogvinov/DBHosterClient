package pt.DBHosterClient.actionlistener;

import java.awt.event.ActionEvent;

import pt.DBHosterClient.ClientFrame;

public class ClientDisconnectListener extends ClientActionListener {
	
	public ClientDisconnectListener(ClientFrame frame) {
		super(frame);
	}

	public void actionPerformed(ActionEvent e) {
		frame.disconnectionPressed();
	}

}