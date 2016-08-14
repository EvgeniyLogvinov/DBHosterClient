package pt.DBHosterClient.actionlistener;

import java.awt.event.ActionEvent;

import pt.DBHosterClient.ClientFrame;

public class ClientExecuteListener extends ClientActionListener {

	public ClientExecuteListener(ClientFrame frame) {
		super(frame);
	}

	public void actionPerformed(ActionEvent e) {
		frame.executionPressed();
	}

}