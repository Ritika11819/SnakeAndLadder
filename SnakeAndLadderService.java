package services;

import java.util.*;
import player.PlayerInfo;

import board.*;
public class SnakeAndLadderService {

    private Board board;
    private int noOfPlayers;

    private Queue<PlayerInfo> players;

    private boolean isGameCompleted;
    private int diceCount;

    private boolean shouldGameContinueTillLastPlayer;
    private boolean shouldMultipleRollsBeAllowedOnSix;

    private static final int DEFAULT_BOARD_SIZE=100;
    private static final int DEFAULT_NO_OF_DICE=1;

    public SnakeAndLadderService(int board_size){
        this.board=new Board(board_size);
        this.players=new LinkedList<>();
        this.diceCount=DEFAULT_NO_OF_DICE;
    }

    public SnakeAndLadderService(){
        this(DEFAULT_BOARD_SIZE);
    }

    public void setDiceCount(int dice_count){
        this.diceCount=dice_count;
    }

    public void setShouldGameContinueTillLastPlayer(boolean val){
        this.shouldGameContinueTillLastPlayer=val;
    }

    public void setShouldMultipleRollsBeAllowedOnSix(boolean val){
        this.shouldMultipleRollsBeAllowedOnSix=val;
    }

    public void setSnakes(List<Snake> snakes){
        board.setSnakes(snakes);
    }

    public void setLadder(List<Ladder> ladders){
        board.setLadders(ladders);
    }

    public void setPlayer(List<PlayerInfo> playerList){
        this.players=new LinkedList<PlayerInfo>();
        this.noOfPlayers=playerList.size();
        Map<String,Integer> playerPosition = new HashMap<String,Integer>();

        for(PlayerInfo player : playerList){
            this.players.add(player);
            playerPosition.put(player.getId(),0);
        }
        board.setPlayerPositions(playerPosition);
    }


    private int positionAfterEncounteringSnakeOrLadder(int newPos){
        int prevPos;

        do{
            prevPos=newPos;
            for(Snake snake : board.getSnakes()){
                if(snake.getStart()==newPos){
                    System.out.println("Oh no! Bitten by a snake from " + snake.getStart() + " to " + snake.getEnd());
                    newPos=snake.getEnd();
                }
            }

            for(Ladder ladder : board.getLadders()){
                if(ladder.getStart()==newPos){
                    System.out.println("Yay! Climbed a ladder from " + ladder.getStart() + " to " + ladder.getEnd());
                    newPos=ladder.getEnd();
                }
            }
        }while(prevPos!=newPos);
        return newPos;
    }

    private void movePlayer(PlayerInfo player,int positions){
        int oldPos=board.getPlayerPositions().get(player.getId());
        int newPos=oldPos + positions;

        int boardSize=board.getSize();

        if(newPos>boardSize){
            newPos=oldPos;
        }
        else{
            newPos=positionAfterEncounteringSnakeOrLadder(newPos);
        }

        board.getPlayerPositions().put(player.getId(),newPos);

        System.out.println(player.getName() + " rolled " + positions + " and moved from " + oldPos + " to " + newPos);
    }

    private int totalValueAfterDiceRoll(){
        int total_val=0;
        for(int i=0;i<diceCount;i++){
            total_val+=DiceService.rollDice();
        }
        return total_val;
    }

    private boolean hasPlayerWon(PlayerInfo player){

        if(player==null) return false;
        if(!board.getPlayerPositions().containsKey(player.getId())){

            return true;
        }

        int newPos=board.getPlayerPositions().get(player.getId());

        int winningPos=board.getSize();

        return newPos == winningPos;
    }

    private boolean isGameOver(){
       return players.size()<=1;
    }

    public void startGame(){
        while(!isGameOver()){
            PlayerInfo currPlayer=players.poll();
            if(currPlayer==null) break;
            boolean extra;

            do {
                int totalValOnDice = totalValueAfterDiceRoll();
                movePlayer(currPlayer,totalValOnDice);
                extra=(shouldMultipleRollsBeAllowedOnSix && totalValOnDice==6 && !hasPlayerWon(currPlayer));

                if(hasPlayerWon(currPlayer)){
                    System.out.println(currPlayer.getName() + " wins the game !!");
                    board.getPlayerPositions().remove(currPlayer.getId());
                    break;
                }
            }while(extra);


            if(!hasPlayerWon(currPlayer)){
                players.add(currPlayer);
            }

        }
        isGameCompleted=true;

    }

}
