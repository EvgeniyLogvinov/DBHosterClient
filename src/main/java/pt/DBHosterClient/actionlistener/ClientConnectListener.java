package pt.DBHosterClient.actionlistener;

import java.awt.event.ActionEvent;

import pt.DBHosterClient.ClientFrame;

public class ClientConnectListener extends ClientActionListener {

	public ClientConnectListener(ClientFrame frame) {
		super(frame);
	}

	public void actionPerformed(ActionEvent e) {
		frame.connectionPressed();
	}

}
