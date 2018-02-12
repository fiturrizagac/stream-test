package com.fraitca.rx.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * To perform a sequence of operations over the elements of the data source and aggregate their results, three parts are needed.
 * The source, intermediate operation(s) and a terminal operation.
 */
public class StreamPipelineTest {

    /**
     * Intermediate operations return a new modified stream. For example, to create a new stream of the existing one.
     */
    @Test
    public void skipTest() {

        Stream<String> onceModifiedStream =
            Stream.of("abcd", "bbcd", "cbcd").skip(2);
        System.out.println(onceModifiedStream.collect(Collectors.toList()));
    }

    @Test
    public void chainedOperationsTest() {
        Stream<String> twiceModifiedStream = Stream.of("abcd", "bbcd", "cbcd")
            .skip(1).map(element -> element.substring(0, 3));
        System.out.println(twiceModifiedStream.collect(Collectors.toList()));
    }

    /**
     * A stream by itself is worthless, the real thing a user is interested in is a result of the terminal operation.
     * which can be a value of some type or an action applied to every element of the stream.
     * <b>Only one terminal operation can be used per stream.</b>
     */
    @Test
    public void pipelineTest() {
        List<String> list = Arrays.asList("abc1", "abc3", "abc2");
        long size = list.stream()
            .skip(1)
            .map(element -> element.substring(0, 3))
            .sorted()
            .count();

        System.out.println(size);
        System.out.println(
            list.stream()
            .skip(1)
            .sorted().collect(Collectors.toList()));
    }

    /**
     * Intermediate operations are lazy.
     * This means that they will be invoked only if it is necessary for the terminal operation execution.
     */
    @Test
    public void lazyInvocationMissinTerminalOperationTest() {
        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        counter = 0;
        Stream<String> stream = list.stream().filter(element -> {
            wasCalled();
            return element.contains("2");
        });
        System.out.println(counter);
        Assert.assertTrue(counter == 0);
    }

    @Test
    public void lazyInvocationTest() {

        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        Optional<String> stream = list.stream().filter(element -> {
            this.wasCalled();
            System.out.println("filter() was called");
            return element.contains("2");
        }).map(element -> {
            System.out.println("map() was called");
            return element.toUpperCase();
        }).findFirst();
        System.out.println(counter);
        Assert.assertTrue(counter == 2);
    }

    @Test
    public void sequentiallyWorkingTest() {

        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        Stream<String> stream = list.stream()
            .filter(element -> {
                System.out.println("filter() was called for " + element );
                return element.contains("2");
            })
            .map(e ->  {
                System.out.println("map() was called for " + e);
                return e.toUpperCase();
            });

        System.out.println(stream.collect(Collectors.toList()));

        // Streams work sequentially

        // filter() was called for abc1
        // filter() was called for abc2
        // map() was called for abc2
        // filter() was called for abc3
        // [ABC2]
    }

    /**
     * For testing purposes
     */
    private long counter;

    private void wasCalled() {
        counter++;
    }
}
