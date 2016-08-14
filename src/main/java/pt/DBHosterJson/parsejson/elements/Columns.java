package pt.DBHosterJson.parsejson.elements;

import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import pt.DBHosterClient.ClientException;

public class Columns extends Elements<String[]> {

	@Override
	public String[] getElements(String json) throws ClientException {
		String[] columns = null;

		if (json != null && ((json = json.trim()) != null) && json.length() > 0) {
			if (this.parseElements(json) != null && this.parseElements(json).length > 0) {
				columns = this.parseElements(json);
			} else {
				throw new ClientException("Your query return 0 columns.");
			}
		} else {
			throw new ClientException("Response json is null.");
		}

		return columns;
	}
	
	@Override
	protected String[] parseElements(String json) {
		String[] col = null;

		if (json != null) {
			
			JsonReader reader = Json.createReader(new StringReader(json));
			JsonObject tableObject = reader.readObject();
			JsonArray columnsArr = tableObject.getJsonArray("columns");
			ArrayList<String> columnsName = new ArrayList<String>();

			for (int i = 0; i < columnsArr.size(); i++) {
				columnsName.add(columnsArr.getJsonObject(i).getString("name"));
			}

			if (reader != null) {
				reader.close();
			}

			int numberOfCols = columnsName.size();

			col = new String[numberOfCols];
			col = columnsName.toArray(col);
		}

		return col;
	}
	
}