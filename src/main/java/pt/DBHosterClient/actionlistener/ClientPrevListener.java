package pt.DBHosterClient.actionlistener;

import java.awt.event.ActionEvent;

import pt.DBHosterClient.ClientFrame;

public class ClientPrevListener extends ClientActionListener {

	public ClientPrevListener(ClientFrame frame) {
		super(frame);
	}

	public void actionPerformed(ActionEvent e) {
		frame.nextPrevPressed(ClientActions.PREV_ACTION.action());
	}

}