package cams.filters;

import cams.users.User;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class responsible for defining methods for various types of sorting
 * such as by ascending or descending order, by points, role, or name
 */
public class Filter {
    /**
     * Ascending sort based on username alphabet order
     * @param names List of names to be sorted
     * @return List of names sorted in ascending order (A-Z)
     */
    public static List<String> ascendingSort(List<String> names) {
        Collections.sort(names);
        return names;
    }

    /**
     * Descending sort based on username alphabetical order
     * @param names List of names to be sorted
     * @return List of names sorted in ascending order (Z-A)
     */
    public static List<String> descendingSort(List<String> names) {
        return names.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    /**
     * Descending sort of users in a camp committee
     * based on the points, highest first
     * @param users List of users to be sorted
     * @param committee The camp committee whose users are to be sorted
     * @return Sorted list of users
     */
    public static List<String> pointsSort(List<User> users, HashMap<String, Integer> committee) {
        Comparator<User> byPoints = Comparator.comparing(user -> committee.getOrDefault(user.getUserID(), 0));
        return sortUsers(users, byPoints.reversed());
    }

    /**
     * Role sort based on whether they are committee members or not, committee members show first
     * @param users List of users to be sorted
     * @param committee The camp committee whose users are to be sorted
     * @return Sorted list of users
     */
    public static List<String> roleSort(List<User> users, HashMap<String, Integer> committee) {
        Comparator<User> byRole = Comparator.comparing(user -> committee.containsKey(user.getUserID()), Comparator.reverseOrder());
        return sortUsers(users, byRole.thenComparing(Comparator.comparing(User::getName)));
    }


    /**
     * General method for sorting users based on a comparator
     * @param users List of users to be sorted
     * @param comparator The comparator function
     * @return List of sorted users
     */
    private static List<String> sortUsers(List<User> users, Comparator<User> comparator) {
        return users.stream()
                .sorted(comparator)
                .map(User::getName)
                .collect(Collectors.toList());
    }

}
