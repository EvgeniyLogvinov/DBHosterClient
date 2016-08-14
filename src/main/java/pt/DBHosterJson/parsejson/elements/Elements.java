package pt.DBHosterJson.parsejson.elements;

import pt.DBHosterClient.ClientException;

public abstract class Elements<T> {
	
	public abstract T getElements(String json) throws ClientException;
	protected abstract T parseElements(String json) throws ClientException;
}
