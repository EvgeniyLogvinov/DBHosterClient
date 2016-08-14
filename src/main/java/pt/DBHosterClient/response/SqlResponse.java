package pt.DBHosterClient.response;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import pt.DBHosterClient.ClientException;
import pt.DBHosterClient.ServerConnect;
import pt.DBHosterJson.parsejson.elements.Columns;
import pt.DBHosterJson.parsejson.elements.Elements;
import pt.DBHosterJson.parsejson.elements.Errors;
import pt.DBHosterJson.parsejson.elements.Rows;

public class SqlResponse extends ServerResponse {
	private String sql = null;
	
	public SqlResponse(AbstractTableModel model, ServerConnect serverConnect, String sql) throws ClientException {
		super(model, serverConnect);
		this.sql = sql;
		if (sql != null && ((sql = sql.trim()) != null) && sql.length() > 0) {
			if (sql.toLowerCase().startsWith("select")) {
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
			} else {
				throw new ClientException("Please, start you SQL from \"select\".");
			}
		} else {
			throw new ClientException("Have not SQL for request.");
		}
			
	}
	
	protected String getResponse() {
		return serverConnect.getSqlResponse(sql);
	}
	
	public AbstractTableModel getDefaultModel() {
		model = new DefaultTableModel(this.getRows(), this.getColumns());
		return model;		
	}
	
}
