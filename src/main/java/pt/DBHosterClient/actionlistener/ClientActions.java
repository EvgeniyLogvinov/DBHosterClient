package pt.DBHosterClient.actionlistener;

enum ClientActions {
	CONNECT_ACTION("connect"), DISCONNECT_ACTION("disconnect"), EXECUTE_ACTION(
			"execute"), NEXT_ACTION("next:"), PREV_ACTION("prev:");

	private String action;

	ClientActions(String action) {
		this.action = action;
	}

	public String action() {
		return action;
	}
}
