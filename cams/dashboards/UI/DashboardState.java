package cams.dashboards.UI;

/**
 * Represents a state of the {@link Dashboard}
 * <p>
 * This interface is part of the State design pattern and is used to define different states
 * (e.g., logged in, logged out, viewing enquiries) of the Dashboard. Each state has its
 * own implementation of how to display itself.
 * <p>
 * Implementing classes should define the specific behavior of the dashboard when it is in
 * that particular state.
 */
public interface DashboardState {
    /**
     * Displays the UI associated with a particular state of the dashboard.
     * <p>
     * This method is responsible for rendering the user interface and handling any
     * interactions specific to the state. Implementations might include displaying menus,
     * handling user input, or transitioning to other states.
     *
     * @param dashboard The {@link Dashboard} context in which this state operates.
     */
    void display(Dashboard dashboard);
}
