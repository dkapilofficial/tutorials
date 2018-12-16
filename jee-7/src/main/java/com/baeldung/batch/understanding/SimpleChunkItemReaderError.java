package com.baeldung.batch.understanding;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;

@Named
public class SimpleChunkItemReaderError extends AbstractItemReader {
    private StringTokenizer tokens;
    public static int COUNT = 0;

    @Override
    public Integer readItem() throws Exception {
        if (tokens.hasMoreTokens()) {
            COUNT++;
            int token = Integer.valueOf(tokens.nextToken());
            if (token == 3) {
                throw new RuntimeException("Something happened");
            }
            return Integer.valueOf(token);
        }
        return null;
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        tokens = new StringTokenizer("1,2,3,4,5,6,7,8,9,10", ",");
    }
}