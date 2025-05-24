package services;

import java.util.Random;

public class DiceService{
    public static int rollDice(){

        Random r=new Random();
        return r.nextInt(6)+1;
    }
}