package pt.DBHosterClient.response;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import pt.DBHosterClient.ClientException;
import pt.DBHosterClient.ServerConnect;
import pt.DBHosterJson.parsejson.elements.Columns;
import pt.DBHosterJson.parsejson.elements.Elements;
import pt.DBHosterJson.parsejson.elements.Errors;
import pt.DBHosterJson.parsejson.elements.Rows;

public class NextPrevResponse extends ServerResponse {
	private String command = null;
	
	public NextPrevResponse(AbstractTableModel model, ServerConnect serverConnect, String command) throws ClientException {
		super(model, serverConnect);
		this.command = command;
		String json = this.getResponse();
		Elements<String> parseErrors = new Errors();
		Elements<String[]> parseColumns = new Columns();
		Elements<String[][]> parseRows = new Rows();
		if (parseErrors.getElements(json) == null) {
			columns = parseColumns.getElements(json);
			rows = parseRows.getElements(json);
		} else {
			throw new ClientException("Error from server: " + parseErrors.getElements(json));
		}
	}
	
	protected String getResponse() {
		return serverConnect.getNextPrevResponse(command);
	}
	
	public AbstractTableModel getDefaultModel() {
		model = new DefaultTableModel(this.getRows(), this.getColumns());
		return model;		
	}
	
}
