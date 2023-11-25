package cams.filters;

import cams.users.User;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Filter {

    // Ascending sort based on username alphabet order
    public static List<String> ascendingSort(List<String> names) {
        Collections.sort(names);
        return names;
    }

    // Descending sort based on username alphabetical order
    public static List<String> descendingSort(List<String> names) {
        return names.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    // Points sort based on the points from the committee, highest first
    public static List<String> pointsSort(List<User> users, HashMap<String, Integer> committee) {
        Comparator<User> byPoints = Comparator.comparing(user -> committee.getOrDefault(user.getUserID(), 0));
        return sortUsers(users, byPoints.reversed());
    }


    // Role sort based on whether they are committee members or not, committee members show first
    public static List<String> roleSort(List<User> users, HashMap<String, Integer> committee) {
        Comparator<User> byRole = Comparator.comparing(user -> committee.containsKey(user.getUserID()), Comparator.reverseOrder());
        return sortUsers(users, byRole.thenComparing(Comparator.comparing(User::getName)));
    }


    // General method for sorting users based on a comparator
    private static List<String> sortUsers(List<User> users, Comparator<User> comparator) {
        return users.stream()
                .sorted(comparator)
                .map(User::getName)
                .collect(Collectors.toList());
    }

}
