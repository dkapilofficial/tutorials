package com.baeldung.stream;

import java.util.List;
import java.util.stream.Stream;

public class StreamApi {

    public String getLastElementUsingReduce(List<String> valueList) {
        Stream<String> stream = valueList.stream();
        return stream.reduce((first, second) -> second).orElse(null);
    }
    
    public Integer getInfiniteStreamLastElementUsingReduce() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        return stream.limit(20).reduce((first, second) -> second).orElse(null);
    }
    
    public String getLastElementUsingSkip(List<String> valueList) {
        long count = valueList.stream().count();
        Stream<String> stream = valueList.stream();
        return stream.skip(count - 1).findFirst().orElse(null);
    }

}
