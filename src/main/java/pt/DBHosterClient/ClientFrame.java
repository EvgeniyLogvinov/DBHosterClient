package pt.DBHosterClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.SocketException;
import java.rmi.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import pt.DBHosterClient.actionlistener.ClientActionListener;
import pt.DBHosterClient.actionlistener.ClientConnectListener;
import pt.DBHosterClient.actionlistener.ClientDisconnectListener;
import pt.DBHosterClient.actionlistener.ClientExecuteListener;
import pt.DBHosterClient.actionlistener.ClientPrevListener;
import pt.DBHosterClient.actionlistener.ClientNextListener;
import pt.DBHosterClient.response.NextPrevResponse;
import pt.DBHosterClient.response.SqlResponse;
import pt.DBHosterClient.response.ServerResponse;

public class ClientFrame extends JFrame {
	private final String CONNECT_ACTION = "connect";
	private final String DISCONNECT_ACTION = "disconnect";
	private final String EXECUTE_ACTION = "execute";
	private final String PREV_ACTION = "prev";
	private final String NEXT_ACTION = "next";

	private JLabel errorLabel = null;

	private JButton connectButton = null;
	private JButton disconnectButton = null;

	private JTextArea sqlTextArea = null;
	private JScrollPane sqlScrollPane = null;
	private JButton executeButton = null;

	private JTextField hostField = null;
	private JLabel hostLabel = null;

	private JTextField portField = null;
	private JLabel portLabel = null;

	private JTable resTable = null;
	private DefaultTableModel model = null;
	private JScrollPane resScrollPane = null;
	private JButton left = null;
	private JButton right = null;

	private boolean hasError = false;
	private int countOfRows = 50;
	
	ServerConnect sc = null;

	public ClientFrame() {
		super("DBHoster");
		this.setResizable(false);
		createGUI();
	}

	public void createGUI() {
		JPanel mainPanel = null;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		mainPanel = addConnectionElements(mainPanel);
		mainPanel = addSqlElements(mainPanel);
		mainPanel = addResultElements(mainPanel);
		getContentPane().add(mainPanel);
		setPreferredSize(new Dimension(400, 100));
	}

	// add connection elements on Frame
	private JPanel addConnectionElements(JPanel panel) {
		hostField = new JTextField();
		hostField.setColumns(13);

		hostLabel = new JLabel("Host: ");
		hostLabel.setLabelFor(hostField);
		panel.add(hostLabel);
		panel.add(hostField);

		portField = new JTextField();
		portField.setColumns(8);

		portLabel = new JLabel("Port: ");
		portLabel.setLabelFor(portField);
		panel.add(portLabel);
		panel.add(portField);

		connectButton = new JButton("Connect");
		connectButton.setActionCommand(CONNECT_ACTION);
		connectButton.setVisible(true);
		panel.add(connectButton);

		disconnectButton = new JButton("Disconnect");
		disconnectButton.setActionCommand(DISCONNECT_ACTION);
		disconnectButton.setVisible(false);
		panel.add(disconnectButton);

		ClientActionListener connectListener = new ClientConnectListener(this);
		ClientActionListener disconnectListener = new ClientDisconnectListener(this);

		connectButton.addActionListener(connectListener);
		disconnectButton.addActionListener(disconnectListener);

		return panel;
	}

	// add SQL elements on Frame
	private JPanel addSqlElements(JPanel panel) {
		sqlTextArea = new JTextArea(10, 40);
		sqlTextArea.setSize(600, 400);
		sqlScrollPane = new JScrollPane(sqlTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sqlScrollPane.setPreferredSize(new Dimension(650, 200));
		sqlTextArea.setLineWrap(true);
		sqlTextArea.setVisible(false);
		sqlScrollPane.setVisible(false);
		panel.add(sqlScrollPane);

		errorLabel = new JLabel();
		errorLabel.setVisible(false);
		panel.add(errorLabel);

		executeButton = new JButton("Execute");
		executeButton.setActionCommand(EXECUTE_ACTION);
		executeButton.setVisible(false);
		panel.add(executeButton);

		ClientActionListener executionListener = new ClientExecuteListener(this);

		executeButton.addActionListener(executionListener);

		return panel;
	}

	// add result elements on Frame
	private JPanel addResultElements(JPanel panel) {
		resTable = new JTable();
		resTable.setVisible(true);
		resScrollPane = new JScrollPane(resTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		resScrollPane.setPreferredSize(new Dimension(650, 300));
		panel.add(resScrollPane);
		resScrollPane.setVisible(false);

		left = new JButton("<");
		left.setActionCommand(PREV_ACTION);
		left.setVisible(false);
		panel.add(left);

		ClientActionListener leftListener = new ClientPrevListener(this);
		left.addActionListener(leftListener);

		right = new JButton(">");
		right.setActionCommand(NEXT_ACTION);
		right.setVisible(false);
		panel.add(right);

		ClientActionListener rightListener = new ClientNextListener(this);
		right.addActionListener(rightListener);

		return panel;
	}

	// connection button pressed
	public void connectionPressed() {

		try {
			if (hostField.getText().equals("")) {
				throw new UnknownHostException("Please, enter correct host.");
			} else if (portField.getText().equals("")) {
				throw new NumberFormatException("Please, enter correct port.");
			} else {
				sc = new ServerConnect(hostField.getText(), Integer.parseInt(portField.getText()), countOfRows);
				hostField.setEnabled(false);
				portField.setEnabled(false);
				connectButton.setVisible(false);
				disconnectButton.setVisible(true);
				sqlTextArea.setVisible(true);
				sqlScrollPane.setVisible(true);
				executeButton.setVisible(true);

				setSize(700, 350);

				System.out.println("Connected to server.");

				if (hasError) {
					errorLabel.setVisible(false);
				}
			}
		} catch (UnknownHostException uhe) {
			System.err.println(uhe);
			showError("Please, enter correct host.");
		} catch (NumberFormatException nfe) {
			System.err.println(nfe);
			showError("Please, enter correct port.");
		} catch (IllegalArgumentException uhe) {
			System.err.println(uhe);
			showError("Please, enter correct port. Port is out of range");
		} catch (SocketException se) {
			System.err.println(se);
			showError("Please, check host.");
		} catch (IOException e1) {
			System.err.println(e1);
			showError("Please, check connection to socet.");
		}

	}

	// disconnection button pressed
	public void disconnectionPressed() {

		hostField.setEnabled(true);
		portField.setEnabled(true);
		connectButton.setVisible(true);
		disconnectButton.setVisible(false);
		sqlTextArea.setVisible(false);
		sqlScrollPane.setVisible(false);
		resScrollPane.setVisible(false);
		left.setVisible(false);
		right.setVisible(false);
		executeButton.setVisible(false);
		sqlTextArea.setText("");
		System.out.println("isDisconnected: " + sc.disconnect());

		setSize(400, 100);

		if (hasError) {
			errorLabel.setVisible(false);
		}
	}

	// execution button pressed
	public void executionPressed() {
		if (model != null) {
			model.setRowCount(0);
		}

		try {
			String sql = sqlTextArea.getText();
			SqlResponse response = new SqlResponse(model, sc, sql);
			model = (DefaultTableModel) response.getDefaultModel();
			resTable.setModel(model);

			resScrollPane.setVisible(true);
			left.setVisible(true);
			right.setVisible(true);
			
			setSize(700, 650);
			if (hasError) {
				errorLabel.setVisible(false);
			}
		} catch (ClientException e) {
			showError(e.getMessage());
			resScrollPane.setVisible(false);
			left.setVisible(false);
			right.setVisible(false);
			
			setSize(700, 350);
		}

	}

	// next button pressed
	public void nextPrevPressed(String command) {
		if (model != null) {
			model.setRowCount(0);
		}

		try {
			ServerResponse response = new NextPrevResponse(model, sc, command);
			model = (DefaultTableModel) response.getDefaultModel();
			resTable.setModel(model);

			resScrollPane.setVisible(true);
			left.setVisible(true);
			right.setVisible(true);
			
			setSize(700, 650);
			if (hasError) {
				errorLabel.setVisible(false);
			}
		} catch (ClientException e) {
			showError(e.getMessage());
			resScrollPane.setVisible(false);
			left.setVisible(false);
			right.setVisible(false);
			
			setSize(700, 350);
		}

	}

	// show errors in label
	private void showError(String error) {
		hasError = true;
		errorLabel.setText(error);
		errorLabel.setForeground(Color.RED);
		errorLabel.setVisible(true);
	}
}