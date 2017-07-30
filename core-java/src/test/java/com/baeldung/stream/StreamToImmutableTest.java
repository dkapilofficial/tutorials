package com.baeldung.stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Test;

import com.baeldung.stream.mycollectors.MyImmutableListCollector;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class StreamToImmutableTest {

    @Test
    public void whenUsingCollectingToImmutableSet_thenSuccess() {
        Set<String> mutableSet = new HashSet<>(Arrays.asList("a", "b", "c"));
        mutableSet.add("test");
        Set<String> immutableSet = mutableSet.stream()
            .collect(collectingAndThen(toSet(), ImmutableSet::copyOf));

        System.out.println(immutableSet.getClass());
    }

    @Test
    public void whenUsingCollectingToUnmodifiableList_thenSuccess() {
        List<String> givenList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> result = givenList.stream()
            .collect(collectingAndThen(toList(), Collections::unmodifiableList));

        System.out.println(result.getClass());
    }

    @Test
    public void whenCollectToImmutableList_thenSuccess() {
        List<Integer> list = IntStream.range(0, 9)
            .boxed()
            .collect(ImmutableList.toImmutableList());

        System.out.println(list.getClass());
    }

    @Test
    public void whenCollectToMyImmutableListCollector_thenSuccess() {
        List<String> givenList = Arrays.asList("a", "b", "c", "d");
        List<String> result = givenList.stream()
            .collect(MyImmutableListCollector.toImmutableList());

        System.out.println(result.getClass());
    }

    @Test
    public void whenPassingSupplier_thenSuccess() {
        List<String> givenList = Arrays.asList("a", "b", "c", "d");
        List<String> result = givenList.stream()
            .collect(MyImmutableListCollector.toImmutableList(LinkedList::new));

        System.out.println(result.getClass());
    }
}
