package test;

import junit.framework.TestCase;
import pt.DBHosterClient.ClientException;

public class ClientExceptionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetMessage() {
		ClientException clientException = new ClientException("test");
		assertEquals("test", clientException.getMessage());
	}
	
	public void testGetNullMessage() {
		ClientException clientException = new ClientException(null);
		assertNull(clientException.getMessage());
	}

}
