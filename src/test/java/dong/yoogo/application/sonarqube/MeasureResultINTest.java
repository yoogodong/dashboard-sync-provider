package dong.yoogo.application.sonarqube;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class MeasureResultINTest {
    @Test
    void name() {
        final List<Integer> a1 = Arrays.asList(1, 2, 3);
        final List<Integer> a2 = Arrays.asList(4, 5, 7);
        final List<List<Integer>> lists = Arrays.asList(a1, a2);
        final List<Integer> collect = lists.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(collect);
    }
}