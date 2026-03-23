package com.example.snakeladder;

import java.util.ArrayList;
import java.util.List;

public class SnakeLadderGame {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private final List<Player> winners;

    public SnakeLadderGame(Board board, int playerCount) {
        if (playerCount < 2) {
            throw new IllegalArgumentException("At least 2 players required");
        }
        this.board = board;
        this.players = new ArrayList<Player>();
        this.dice = new Dice();
        this.winners = new ArrayList<Player>();
        for (int i = 1; i <= playerCount; i++) {
            this.players.add(new Player("P" + i));
        }
    }

    public void play() {
        int activePlayers = players.size();
        while (activePlayers >= 2) {
            for (Player player : players) {
                if (!player.isActive()) {
                    continue;
                }

                int roll = dice.roll();
                int current = player.getPosition();
                int next = current + roll;

                if (next <= board.getFinalCell()) {
                    next = board.resolvePosition(next);
                    player.setPosition(next);
                }

                System.out.println(player.getId() + " rolled " + roll + " and moved to " + player.getPosition());

                if (player.getPosition() == board.getFinalCell()) {
                    player.setActive(false);
                    winners.add(player);
                    activePlayers--;
                    System.out.println(player.getId() + " reached " + board.getFinalCell() + " and secured rank " + winners.size());
                    if (activePlayers < 2) {
                        break;
                    }
                }
            }
        }

        System.out.println("Game ended");
        if (!winners.isEmpty()) {
            for (int i = 0; i < winners.size(); i++) {
                System.out.println("Rank " + (i + 1) + ": " + winners.get(i).getId());
            }
        }
        for (Player player : players) {
            if (player.isActive()) {
                System.out.println("Still in game: " + player.getId() + " at " + player.getPosition());
            }
        }
    }
}
