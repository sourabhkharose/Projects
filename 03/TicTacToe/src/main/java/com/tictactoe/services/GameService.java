package com.tictactoe.services;

import com.tictactoe.models.Game;
import com.tictactoe.models.GameState;
import com.tictactoe.models.Player;
import com.tictactoe.services.strategies.winningStrategy.WinningStrategy;

import java.util.List;

public class GameService {
    private Game game;

    public void startGame(List<Player> players, List<WinningStrategy> winningStrategies){
        game = Game.getBuilder()
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void makeMove(){

    }

    public Player getWinner(){
        return null;
    }

    public GameState getGameState(){
        return null;
    }

    public void printBoard(){
        game.getBoard().printBoard();
    }
}
