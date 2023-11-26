package cams.dashboards.menu;

/**
 * Functional interface encapsulating a behavior or a block of code that can be executed on demand.
 * It can be implemented using lambda expressions
 * <p>
 * The {@code execute} method is an abstract method defining the action that will be performed.
 */
@FunctionalInterface
public interface MenuAction {
    /**
     * Executes the action defined by this MenuAction.
     * You must provide an overriding implementation for the specific behavior to be executed.
     */
    void execute();
}
