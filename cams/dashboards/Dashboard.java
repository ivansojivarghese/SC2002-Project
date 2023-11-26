package cams.dashboards;
import cams.dashboards.menu.LogoutState;
import cams.users.User;
/**
 * The main dashboard user interface for the application.
 * This class is responsible for storing the current state of the user interface,
 * and transitioning between different states (menus) of the dashboard.
 */
public class Dashboard {
	private DashboardState currentState;
	private boolean quit = false;
	private User authenticatedUser;

	/**
	 * Constructs a Dashboard instance initializing it to the specified state.
	 */
	public Dashboard(DashboardState state) {
		this.currentState = state;
	}

	/**
	 * Constructs a Dashboard instance initializing it to the LogoutState.
	 */
	public Dashboard() {
		this.currentState = new LogoutState();
	}

	/**
	 * Checks if the dashboard is set to quit.
	 *
	 * @return A boolean indicating if the dashboard is in the quit state.
	 */
	public boolean isQuit() {
		return quit;
	}

	/**
	 * Sets the quit state of the dashboard.
	 *
	 * @param quit A boolean to set the quit state.
	 */
	public void setQuit(boolean quit) {
		this.quit = quit;
	}

	/**
	 * Retrieves the currently authenticated user.
	 *
	 * @return The authenticated {@link User}.
	 */
	public User getAuthenticatedUser() {
		return authenticatedUser;
	}

	/**
	 * Sets the authenticated user.
	 *
	 * @param authenticatedUser The {@link User} to set as authenticated.
	 */
	public void setAuthenticatedUser(User authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	/**
	 * Sets the current state of the dashboard.
	 *
	 * @param state The new {@link DashboardState} of the dashboard.
	 */
	public void setState(DashboardState state) {
		this.currentState = state;
	}

	/**
	 * Requests the current state to display its UI.
	 */
	public void request() {
		currentState.display(this);
	}

	/**
	 * Communicates the logout process and sets the dashboard state to LogoutState.
	 */
	public void logout() {
		System.out.println("Logging out...");
		setState(new LogoutState());
	}


	/**
	 * Starts the main menu by setting the dashboard state to the initial LogoutState.
	 */
	public void startMain() {
		setState(new LogoutState());
	}

	/**
	 * Sets the dashboard to the logged-in state (also known as the user's main menu)
	 * based on the authenticated user's role.
	 */
	public void loggedIn() {
		setState(authenticatedUser.getMenuState());
	}
}