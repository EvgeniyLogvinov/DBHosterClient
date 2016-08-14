package pt.DBHosterClient.response;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import pt.DBHosterClient.ClientException;
import pt.DBHosterClient.ServerConnect;

public abstract class ServerResponse {
	protected String rows[][] = null;
	protected String columns[] = null;
	protected AbstractTableModel model = null;
	protected ServerConnect serverConnect = null; 
	
	public ServerResponse(AbstractTableModel model, ServerConnect serverConnect) throws ClientException {
		this.model = model;
		this.serverConnect = serverConnect;
	}
	
	protected abstract String getResponse();
	
	public AbstractTableModel getDefaultModel() {
		model = new DefaultTableModel(this.getRows(), this.getColumns());
		return model;		
	}
	
	protected String[][] getRows() {
		return this.rows;
	}
	
	protected String[] getColumns() {
		return this.columns;
	}
	
}
