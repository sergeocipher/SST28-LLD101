package com.example.snakeladder;

import java.util.Collections;
import java.util.Map;

public class Board {
    private final int size;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;

    public Board(int size, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        this.size = size;
        this.snakes = snakes;
        this.ladders = ladders;
    }

    public int getSize() {
        return size;
    }

    public int getFinalCell() {
        return size * size;
    }

    public Map<Integer, Integer> getSnakes() {
        return Collections.unmodifiableMap(snakes);
    }

    public Map<Integer, Integer> getLadders() {
        return Collections.unmodifiableMap(ladders);
    }

    public int resolvePosition(int position) {
        int current = position;
        while (true) {
            Integer snakeTail = snakes.get(current);
            if (snakeTail != null) {
                current = snakeTail;
                continue;
            }
            Integer ladderEnd = ladders.get(current);
            if (ladderEnd != null) {
                current = ladderEnd;
                continue;
            }
            return current;
        }
    }
}
