/*
 *  @author - Yash Khanduja, 000826385
 *  @date - 08/10/2022
 * "StAuth10185: Yash Khanduja, 000826385 certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 *  I have not made my work available to anyone else." 
 *
 * The code achieves the following results: The operation took 68.32 seconds.
 *       Iterations: 1000000
 *       Min Score: 29
 *       Max Score: 892	
 *       Average Score: 169.93
 *       Games>150: 32.70%	
 *       Games>200: 21.33% 
 * 
 */
 
import java.util.Arrays;
import java.util.Map;

public class YahtzeeStrategy {
    //final boolean DEBUG = true;
    final boolean DEBUG = false;

    public void debugWrite( String str ) {
        if ( DEBUG )
            System.out.println( str );
    }


    Yahtzee game = new Yahtzee();


    // Used enumMap instead of boolean[] so I can use enums as indexes.
    // Keep track of which boxes I've already filled.
    Map<Yahtzee.Boxes, Boolean> boxFilled;

    boolean[] keep; // flag array of dice to keep
    int[] roll;  // current turn's dice state

    // EXAMPLE GAME PLAY
    // YOU SHOULD HEAVILY EDIT THIS LOGIC TO SUIT YOUR STRATEGY

    // Track what pattern matches are in the roll.
    Map<Yahtzee.Boxes, Boolean> thisRollHas;

    public int play() {
        debugWrite( game.toString() );
        for (int turnNum = 1; turnNum <= 13; turnNum++) {
            debugWrite( "Playing turn " + turnNum + ": ");
            boxFilled = game.getScoreState();
            keep = new boolean[5];
            roll = game.play();
            debugWrite( "Turn " + turnNum + " Roll 1: " + Arrays.toString( roll ) );
            thisRollHas = game.has();

            if (thisRollHas.get(Yahtzee.Boxes.Y)) {
                if (game.setScore(Yahtzee.Boxes.Y)) {
                    continue;
                }
            }

            if (thisRollHas.get(Yahtzee.Boxes.LS)) {
                if (game.setScore(Yahtzee.Boxes.LS)) {
                    continue;
                }
            }
            int[] tempRoll = roll.clone();
            Arrays.sort(tempRoll);
            keep = HighestMultiplicity(roll);


            // START ROLL 2
            roll = game.play(keep);
            debugWrite( "Turn " + turnNum + " Roll 2: " + Arrays.toString( roll ) );
            thisRollHas = game.has();

            if (thisRollHas.get(Yahtzee.Boxes.Y)) {
                if (game.setScore(Yahtzee.Boxes.Y)) {
                    continue;
                }
            }

            if (thisRollHas.get(Yahtzee.Boxes.LS)) {
                if (game.setScore(Yahtzee.Boxes.LS)) {
                    continue;
                }
            }
            keep = HighestMultiplicity(roll);



            // START ROLL 3
            roll = game.play(keep);

            debugWrite( "Turn " + turnNum + " Roll 3: " + Arrays.toString( roll ) );
            thisRollHas = game.has();

            // MUST SCORE SOMETHING!!
            if (thisRollHas.get(Yahtzee.Boxes.Y))
                if (game.setScore(Yahtzee.Boxes.Y)) {
                    continue;
                }

            if (thisRollHas.get(Yahtzee.Boxes.LS)) {
                if (game.setScore(Yahtzee.Boxes.LS)) {
                    continue;
                }
            }

            if (thisRollHas.get(Yahtzee.Boxes.SS))
                if (game.setScore(Yahtzee.Boxes.SS)) {
                    continue;
                }

            if (thisRollHas.get(Yahtzee.Boxes.FH))
                if (game.setScore(Yahtzee.Boxes.FH)) {
                    continue;
                }

            if (thisRollHas.get(Yahtzee.Boxes.FK) && tempRoll[2]>3)
                if (game.setScore(Yahtzee.Boxes.FK)) {
                    continue;
                }

            if (thisRollHas.get(Yahtzee.Boxes.TK) && tempRoll[2]>3)
                if (game.setScore(Yahtzee.Boxes.TK)) {
                    continue;
                }


            // score it anywhere
            boolean scored = false;
            for (Yahtzee.Boxes b : Yahtzee.Boxes.values()) {
                switch (b) {
                    // but I can set priority by rearranging things
                    case C:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.C);
                        break;
                    case FK:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.FK);
                        break;

                    case TK:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.TK);
                        break;
                    case U1:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.U1);
                        break;
                    case U2:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.U2);
                        break;
                    case U3:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.U3);
                        break;
                    case U4:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.U4);
                        break;
                    case U5:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.U5);
                        break;
                    case U6:
                        if (!boxFilled.get(b) && thisRollHas.get(b)) scored = game.setScore(Yahtzee.Boxes.U6);
                        break;
                }

                if (scored) {
                    break;
                }
            }

            boolean scratched = false;
            if (!scored) {
                // must scratch, let's do it stupidly
                for (Yahtzee.Boxes b : Yahtzee.Boxes.values()) {
                    scratched = game.scratchBox(b);

                    if (scratched) {
                        break;
                    }
                }
            }

            if (!scored && !scratched)
                System.err.println("Invalid game state, can't score, can't scratch.");

            debugWrite( game.toString() );
        }
        return game.getGameScore() >= 0 ? game.getGameScore() : 0;
    }

    /**
     *
     * @param arr
     * @return boolean Array keep - for the Elements to keep.
     * Calculates the Highest Multiplicity of an Element in an Array.
     */
    public boolean[] HighestMultiplicity(int[] arr){
        int arrLength = 6;
        int highestMultiple = 0;
        int[] diceRolls = new int[arrLength];
        boolean[] keep = new boolean[arrLength-1];
        for(int dice: arr){
            diceRolls[dice-1]++;
        }


        // If there are no same number - highest multiplicity is zero.
        //Check if the Array has all Distinct Values
        for (int i = 0;i < arr.length-1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if(arr[i] != arr[j]) {
                    highestMultiple = 0;
                }
            }
        }

        for(int i=0; i<diceRolls.length;i++){
            if(diceRolls[i]>=diceRolls[highestMultiple]){
                highestMultiple=i;
            }
        }

        for(int i = 0; i < arr.length; i++) {
            if (arr[i] == (highestMultiple + 1)) keep[i] = true;
        }
        return keep;
    }

}
