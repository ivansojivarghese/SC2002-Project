package cams.users;

import cams.database.UnifiedUserRepository;

public interface Participant {
	void register(User user, String campName);
    void deregister(User user, String campName);
}
