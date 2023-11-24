package cams.dashboards;
import cams.users.User;

public class Dashboard {
	private DashboardState currentState;
	private boolean quit = false;
	private User authenticatedUser;

	public Dashboard() {
		// Initially, the dashboard might be in the options state
		this.currentState = new LogoutState();
	}

	public boolean isQuit() {
		return quit;
	}

	public void setQuit(boolean quit) {
		this.quit = quit;
	}

	public User getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(User authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public void setState(DashboardState state) {
		this.currentState = state;
	}

	public void request() {
		currentState.display(this);
	}

	// Method to simulate logout
	public void logout() {
		System.out.println("Logging out...");
		setState(new LogoutState());
	}
	//Method to start main menu
	public void startMain() {
		setState(new LogoutState());
	}

	//Sets the loggedIn menu depending on user role
	public void loggedIn() {
		setState(authenticatedUser.getMenuState());
	}
}