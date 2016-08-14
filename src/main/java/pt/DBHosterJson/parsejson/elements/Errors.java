package pt.DBHosterJson.parsejson.elements;

import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import pt.DBHosterClient.ClientException;

public class Errors extends Elements<String> {

	@Override
	public String getElements(String json) throws ClientException {
		return this.parseElements(json);
	}

	@Override
	protected String parseElements(String json) {
		String error = null;

		JsonReader reader = Json.createReader(new StringReader(json));
		JsonObject tableObject = reader.readObject();
		JsonArray errorArr = tableObject.getJsonArray("errors");
		ArrayList<String> errorsName = new ArrayList<String>();
		if (errorArr != null && errorArr.size() != 0) {
			for (int i = 0; i < errorArr.size(); i++) {
				errorsName.add(errorArr.getJsonObject(i).getString("error"));
			}
			error = errorsName.toString();
		} else {
			System.out.println("Have not error");
		}

		if (reader != null) {
			reader.close();
		}

		return error;
	}

}