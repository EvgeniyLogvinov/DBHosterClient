package pt.DBHosterClient.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pt.DBHosterClient.ClientFrame;

public abstract class ClientActionListener implements ActionListener {
	ClientFrame frame = null;

	public ClientActionListener(ClientFrame frame) {
		this.frame = frame;
	}

	public abstract void actionPerformed(ActionEvent e);

}