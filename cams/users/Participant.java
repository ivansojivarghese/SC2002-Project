package cams.users;

public interface Participant {
    public void register(User user, String campName);
    public void deregister(User user, String campName);
}
