package cams.users;

import cams.database.UnifiedCampRepository;

public class CampDeleter implements OrganiserActions {
    @Override
    public void manageCamp(User user, String campName) {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        user.removeCamp(campName);
        repo.deleteCamp(campName);
    }
}
