package com.fraitca.rx.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParallelStreamTest {

    List<Product> productList;

    @Before
    public void setUp() throws Exception {
        this.givenProductList();
    }

    @Test
    public void parallelStreamTest() {

        Stream<Product> streamOfCollection = productList.parallelStream();
        boolean isParallel = streamOfCollection.isParallel();
        boolean bigPrice = streamOfCollection
            .map(product -> {
                System.out.println(product.getName() + " - " + Thread.currentThread().getName());
                return product.getPrice() * 12;
            })
            .anyMatch(price -> {
                System.out.println(price + " - " + Thread.currentThread().getName());
                return price > 200;
            });

//        Assert.assertTrue(isParallel);
//        Assert.assertTrue(bigPrice);
    }


    @Test
    public void name() {

        Stream<Product> streamOfCollection = productList.stream();
        boolean isParallel = streamOfCollection.isParallel();
        boolean bigPrice = streamOfCollection
            .map(product -> {
                System.out.println(product.getName() + " - " + Thread.currentThread().getName());
                return product.getPrice() * 12;
            })
            .anyMatch(price -> {
                System.out.println(price + " - " + Thread.currentThread().getName());
                return price > 1200;
            });

//        Assert.assertTrue(isParallel);
//        Assert.assertTrue(bigPrice);
    }

    private void givenProductList() {

        productList = Arrays.asList(
            new Product(23, "potatoes"),
            new Product(14, "orange"),
            new Product(13, "lemon"),
            new Product(23, "bread"),
            new Product(13, "sugar"));
    }
}
