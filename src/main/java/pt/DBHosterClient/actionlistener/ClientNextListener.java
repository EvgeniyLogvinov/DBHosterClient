package pt.DBHosterClient.actionlistener;

import java.awt.event.ActionEvent;

import pt.DBHosterClient.ClientFrame;

public class ClientNextListener extends ClientActionListener {

	public ClientNextListener(ClientFrame frame) {
		super(frame);
	}

	public void actionPerformed(ActionEvent e) {
		frame.nextPrevPressed(ClientActions.NEXT_ACTION.action());
	}

}