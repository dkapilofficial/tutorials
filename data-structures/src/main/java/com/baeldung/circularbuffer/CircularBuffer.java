package com.baeldung.circularbuffer;

public class CircularBuffer<E> {

    private static final int DEFAULT_CAPACITY = 8;

    private final int capacity;
    private final E[] data;
    private volatile int writeSequence, readSequence;

    @SuppressWarnings("unchecked")
    public CircularBuffer(int capacity) {

        this.capacity = (capacity < 1 ? DEFAULT_CAPACITY : capacity);
        this.data = (E[]) new Object[capacity];

        this.readSequence = 0;
        this.writeSequence = -1;
    }

    public boolean offer(E element) {

        if (!isFull()) {

            int nextWriteSeq = writeSequence + 1;
            data[nextWriteSeq % capacity] = element;

            writeSequence += 1;
            return true;
        }

        return false;
    }

    public E poll() {

        if (!isEmpty()) {

            E nextValue = data[readSequence % capacity];
            readSequence += 1;
            return nextValue;
        }

        return null;
    }

    public int capacity() {
        return capacity;
    }

    public int size() {

        return (writeSequence - readSequence) + 1;
    }

    public boolean isEmpty() {

        return writeSequence < readSequence;
    }

    public boolean isFull() {

        return size() >= capacity;
    }
}
