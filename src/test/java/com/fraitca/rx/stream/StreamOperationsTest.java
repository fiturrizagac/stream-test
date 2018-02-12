package com.fraitca.rx.stream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StreamOperationsTest {


    private List<String> list;

    @Before
    public void setUp() throws Exception {
        this.givenList();
    }

    @Test
    public void countTest() {

        long count = list.stream().count();
        System.out.println(count);
    }

    @Test
    public void iteratingTest() {

        boolean isExist = list.stream().anyMatch(element -> element.contains("a"));
        System.out.println(isExist);
    }


    /**
     * The {@link Stream#filter(Predicate)} method allows us to pick stream of elements which satisfy a predicate.
     */
    @Test
    public void filteringTest() {

        Stream<String> stream = list.stream();
        Stream<String> filteredStream = stream.filter(element -> element.toLowerCase().contains("d"));

        // The Streams are immutable, the result of their operations are new Streams
        Assert.assertNotEquals(stream, filteredStream);

        System.out.println(list.stream().collect(Collectors.toList()));
        System.out.println(filteredStream.collect(Collectors.toList()));
    }

    /**
     * The {@link Stream#map(Function)} method convert elements of a Stream by applying a special function to them,
     * and to collect these new elements into a Stream.
     */
    @Test
    public void mappingTest() {

        // Stream of Strings
        List<String> uris = Arrays.asList("C:\\My.txt");
        Stream<String> strStream = uris.stream();

        // Stream of paths
        Stream<Path> stream = strStream.map(strUri -> Paths.get(strUri));

    }

    /**
     * Stream API gives a handy set of instruments to validate elements of a sequence according to some predicate.
     * To do this one of the following methods can be used: anyMatch(), allMatch(), noneMatch(). Their names are self-explaining.
     * Those are terminal operations which return a boolean.
     */
    @Test
    public void matchingTest() {

        boolean isValid = list.stream().anyMatch(element -> element.contains("h"));
        boolean isValidOne = list.stream().allMatch(element -> element.contains("h"));
        boolean isValidTwo = list.stream().noneMatch(element -> element.contains("h"));

        Assert.assertTrue(isValid);
        Assert.assertFalse(isValidOne);
        Assert.assertFalse(isValidTwo);
    }

    /**
     * The following table explain the reduce behavior
     */
    @Test
    public void reductionTest() {

        List<Integer> integers = Arrays.asList(1, 2, 3);

        // where 23 is the start value
        // iteration    |   element |   function    |   result
        // 1°           |   1       |   23 + 1      |   24
        // 2°           |   2       |   24 + 2      |   26
        // 3°           |   3       |   26 + 3      |   29
        Integer reduced = integers.stream().reduce(23, (result, element) -> result + element);

        Assert.assertTrue(reduced == 29);
    }

    /**
     * The reduction can also be provided by the collect() method of type Stream.
     * This operation is very handy in case of converting a stream to a Collection or a Map and representing a stream in form of a single string.
     * There is a utility class Collectors which provide a solution for almost all typical collecting operations.
     * For some, not trivial tasks, a custom Collector can be created.
     */
    @Test
    public void collectingTest() {

        List<String> resultList
            = list.stream().map(element -> element.toUpperCase()).collect(Collectors.toList());

        System.out.println(resultList);
    }

    @Test
    public void name() {
        list.parallelStream().count();
    }

    private void givenList() {
        list = Arrays.asList("Franco", "Brigeth", "Fernanda", "Raul", "Emperatriz", "Diego", "Rosario", "Juan");
    }
}
