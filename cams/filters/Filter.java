package cams.filters;

import cams.users.User;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Filter {

    // Ascending sort based on username
    public static List<String> ascendingSort(List<String> names) {
        Collections.sort(names);
        return names;
    }

    // Descending sort based on username
    public static List<String> descendingSort(List<String> names) {
        return names.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

}
