
import board.Ladder;
import board.Snake;
import board.Board;
import player.*;
import services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class Main{
    public static void main(String[] args){

        Scanner sc=new Scanner(System.in);

        System.out.println("WELCOME TO CONSOLE BASED SNAKES AND LADDERS GAME !");

        System.out.println("Choose the game mode : ");
        System.out.println("1. Default Settings (Board Size : 100 , No. Of Dice : 1)");
        System.out.println("2. Custom Settings : ");

        int choice = sc.nextInt();

        SnakeAndLadderService snakeAndLadderService;

        if(choice==2){
            System.out.println("Enter the board size: ");
            int boardSize = sc.nextInt();

            System.out.println("Enter the number of dice: ");
            int diceCount = sc.nextInt();


            snakeAndLadderService = new SnakeAndLadderService(boardSize);
            snakeAndLadderService.setDiceCount(diceCount);

            System.out.println("Should multiple rolls be allowed on rolling a 6? (true/false)");
            snakeAndLadderService.setShouldMultipleRollsBeAllowedOnSix(sc.nextBoolean());

            System.out.println("Should the game continue until the last player? (true/false)");
            snakeAndLadderService.setShouldGameContinueTillLastPlayer(sc.nextBoolean());
        } else {
            snakeAndLadderService = new SnakeAndLadderService();
        }


        System.out.println("Enter the number of snakes : ");
        int no_of_snakes=sc.nextInt();
        List<Snake> snakes = new ArrayList<Snake>();

        for(int i=0;i<no_of_snakes;i++){
            System.out.println("Enter the starting and ending position of the " + (i+1) + "th snake : ");
            snakes.add(new Snake(sc.nextInt(),sc.nextInt()));
        }

        System.out.println("Enter the number of ladders : ");
        int no_of_ladders=sc.nextInt();
        List<Ladder> ladders = new ArrayList<Ladder>();

        for(int i=0;i<no_of_ladders;i++){
            System.out.println("Enter the starting and ending position of the " + (i+1) + "th ladder : ");
            ladders.add(new Ladder(sc.nextInt(),sc.nextInt()));
        }

        System.out.println("Enter the number of players : ");
        int no_of_players=sc.nextInt();
        List<PlayerInfo> players = new ArrayList<PlayerInfo>();

        for(int i=0;i<no_of_players;i++){
            System.out.println("Enter the name of " + (i+1) + "th player : ");
            String name=sc.next();
            players.add(new PlayerInfo(name));
        }

        sc.close();

        snakeAndLadderService.setSnakes(snakes);
        snakeAndLadderService.setLadder(ladders);
        snakeAndLadderService.setPlayer(players);

        snakeAndLadderService.startGame();

    }
}