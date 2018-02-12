package com.fraitca.rx.stream;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamCreationTest {

    @Test
    public void creationTest() {

        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> stream = Arrays.stream(arr);
        stream = Stream.of(arr);
        stream = Stream.of("a", "b", "c");

        System.out.println(stream);
    }
}
