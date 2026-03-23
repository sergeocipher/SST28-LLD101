package com.example.snakeladder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BoardFactory {
    private static final int MAX_TRIES = 20000;

    private final Random random;

    public BoardFactory() {
        this.random = new Random();
    }

    public Board createBoard(int n, DifficultyLevel difficultyLevel) {
        int finalCell = n * n;
        if (finalCell < 8) {
            throw new IllegalArgumentException("Board is too small. Choose n >= 3");
        }

        Map<Integer, Integer> snakes = new HashMap<Integer, Integer>();
        Map<Integer, Integer> ladders = new HashMap<Integer, Integer>();
        Set<Integer> blockedStartCells = new HashSet<Integer>();

        int placedSnakes = 0;
        int tries = 0;
        while (placedSnakes < n && tries < MAX_TRIES) {
            tries++;
            int head = randomBetween(2, finalCell - 1);
            if (blockedStartCells.contains(head)) {
                continue;
            }
            int tail = generateSnakeTail(head, difficultyLevel);
            if (tail <= 0 || tail >= head) {
                continue;
            }
            if (blockedStartCells.contains(tail)) {
                continue;
            }

            snakes.put(head, tail);
            if (hasCycle(snakes, ladders)) {
                snakes.remove(head);
                continue;
            }
            blockedStartCells.add(head);
            placedSnakes++;
        }

        int placedLadders = 0;
        tries = 0;
        while (placedLadders < n && tries < MAX_TRIES) {
            tries++;
            int start = randomBetween(1, finalCell - 1);
            if (blockedStartCells.contains(start)) {
                continue;
            }
            int end = generateLadderEnd(start, finalCell, difficultyLevel);
            if (end <= start || end > finalCell) {
                continue;
            }
            if (blockedStartCells.contains(end)) {
                continue;
            }

            ladders.put(start, end);
            if (hasCycle(snakes, ladders)) {
                ladders.remove(start);
                continue;
            }
            blockedStartCells.add(start);
            placedLadders++;
        }

        if (placedSnakes < n || placedLadders < n) {
            throw new IllegalStateException("Unable to generate board with requested constraints");
        }
        return new Board(n, snakes, ladders);
    }

    private int generateSnakeTail(int head, DifficultyLevel difficultyLevel) {
        if (difficultyLevel == DifficultyLevel.EASY) {
            int maxDrop = Math.max(2, head / 3);
            int drop = randomBetween(1, maxDrop);
            return head - drop;
        }
        int maxDrop = Math.max(2, (2 * head) / 3);
        int minDrop = Math.max(1, maxDrop / 2);
        int drop = randomBetween(minDrop, maxDrop);
        return head - drop;
    }

    private int generateLadderEnd(int start, int finalCell, DifficultyLevel difficultyLevel) {
        int remaining = finalCell - start;
        if (remaining <= 1) {
            return start;
        }

        if (difficultyLevel == DifficultyLevel.EASY) {
            int minGain = Math.max(1, remaining / 3);
            int gain = randomBetween(minGain, remaining);
            return start + gain;
        }

        int maxGain = Math.max(1, remaining / 3);
        int gain = randomBetween(1, maxGain);
        return start + gain;
    }

    private int randomBetween(int low, int high) {
        if (high < low) {
            return low;
        }
        return random.nextInt(high - low + 1) + low;
    }

    private boolean hasCycle(Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        Map<Integer, Integer> transitions = new HashMap<Integer, Integer>();
        transitions.putAll(snakes);
        transitions.putAll(ladders);

        Set<Integer> visitedGlobal = new HashSet<Integer>();
        for (Integer node : transitions.keySet()) {
            if (!visitedGlobal.contains(node)) {
                if (dfsDetectCycle(node, transitions, visitedGlobal, new HashSet<Integer>())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsDetectCycle(
            Integer node,
            Map<Integer, Integer> transitions,
            Set<Integer> visitedGlobal,
            Set<Integer> currentPath
    ) {
        if (currentPath.contains(node)) {
            return true;
        }
        if (visitedGlobal.contains(node)) {
            return false;
        }

        visitedGlobal.add(node);
        currentPath.add(node);

        Integer next = transitions.get(node);
        if (next != null) {
            if (dfsDetectCycle(next, transitions, visitedGlobal, currentPath)) {
                return true;
            }
        }

        currentPath.remove(node);
        return false;
    }
}
