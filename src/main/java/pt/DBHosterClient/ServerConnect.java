package pt.DBHosterClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnect {
	private Socket fromServer = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private final String DISCONNECT = "disconnect:";
	private int countOfRows = 0;

	public ServerConnect(String host, int port, int countOfRows) throws UnknownHostException, IOException {
		fromServer = new Socket(host, port);
		this.countOfRows = countOfRows;
		in = new BufferedReader(new InputStreamReader(fromServer.getInputStream()));
		out = new PrintWriter(fromServer.getOutputStream(), true);
		setNumberOfRows();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
		if (fromServer != null) {
			fromServer.close();
		}
		System.out.println("IO close streams");
	}

	public boolean disconnect() {
		boolean disconnect = false;
		String response = null;

		try {
			if (fromServer != null && !fromServer.isClosed() && in != null && out != null) {
				System.out.println("To server " + DISCONNECT);
				out.println(DISCONNECT);
				response = in.readLine();
				System.out.println("Server response " + response);
				if (response.equals(DISCONNECT)) {
					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.close();
					}
 					if (fromServer != null) {
 						fromServer.close();
					}
 					
 					disconnect = true;
 					System.out.println(response);
				} else {
					disconnect = false;
				}
			} else {
				System.out.println("In or out stream is null");
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IO to/from server error " + e.getLocalizedMessage());
		}

		return disconnect;
	}

	// Send request and get response from server
	public String getSqlResponse(String sql) {
		String res = null;

		try {
			System.out.println("from: " + fromServer);
			if (fromServer != null) {

				if (sql != null && (sql = sql.trim()) != null && sql.length() > 0) {
					System.out.println("SQL " + sql);
					out.println("executeSql:" + sql);
					res = in.readLine();
					System.out.println("Server result " + res);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IO to/from server error " + e.getLocalizedMessage());
		}

		return res;
	}

	// Send request and get response from server
	public String getNextPrevResponse(String nextPrev) {
		String res = null;

		try {
			System.out.println("from: " + fromServer);
			if (fromServer != null) {
				System.out.println(nextPrev + " pressed");
				out.println(nextPrev);
				res = in.readLine();
				System.out.println("Server result " + res);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IO to/from server error " + e.getLocalizedMessage());
		}

		return res;
	}

	private void setNumberOfRows() {
		if (fromServer != null) {
			out.println("countOfRows:" + countOfRows);
		}
	}
}
