package pt.DBHosterJson.parsejson.elements;

import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import pt.DBHosterClient.ClientException;

public class Rows extends Elements<String[][]> {

	@Override
	public String[][] getElements(String json) throws ClientException {

		String[][] rows = null;

		if (json != null && ((json = json.trim()) != null) && json.length() > 0) {
			if (this.parseElements(json).length > 0) {
				rows = this.parseElements(json);
			} else {
				throw new ClientException("Your query return 0 rows.");
			}
		} else {
			throw new ClientException("Response json is null.");
		}

		return rows;
	}
	
	@Override
	protected String[][] parseElements(String json) {
		String[][] rows = null;

		if (json != null) {

			JsonReader reader = Json.createReader(new StringReader(json));
			JsonObject tableObject = reader.readObject();

			JsonArray columnsArr = tableObject.getJsonArray("columns");
			JsonArray rowsArr = tableObject.getJsonArray("rows");

			ArrayList<String> columnsName = new ArrayList<String>();

			for (int i = 0; i < columnsArr.size(); i++) {
				columnsName.add(columnsArr.getJsonObject(i).getString("name"));
			}

			rows = new String[rowsArr.size()][columnsName.size()];

			for (int i = 0; i < rowsArr.size(); i++) {
				for (int j = 0; j < columnsName.size(); j++) {
					rows[i][j] = rowsArr.getJsonObject(i).getString(columnsName.get(j));
				}
			}

			if (reader != null) {
				reader.close();
			}

		}

		return rows;
	}
	
}