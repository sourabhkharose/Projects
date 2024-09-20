package com.tictactoe.controllers;

import com.tictactoe.models.Game;
import com.tictactoe.models.GameState;
import com.tictactoe.models.Player;
import com.tictactoe.services.GameService;
import com.tictactoe.services.strategies.winningStrategy.WinningStrategy;

import java.util.List;

public class GameController {

    private GameService gameService;

    public GameController() {
        this.gameService = new GameService();
    }

    public void startGame(List<Player> players, List<WinningStrategy> winningStrategies){
        gameService.startGame(players, winningStrategies);
    }

    public void makeMove(){

    }

    public Player getWinner() {
        return null;
    }

    public GameState getGameState(){
        return null;
    }

    public void printBoard() {
        gameService.printBoard();
    }
}
