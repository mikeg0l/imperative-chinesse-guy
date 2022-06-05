import java.util.Random;
import java.util.Scanner;

public class S26525P02 {
    static char[] Map = new char[40];
    static Random r = new Random();
    static Scanner scan = new Scanner(System.in);
    static int[] players = new int[] {0,0,0,0}, scores = new int[] {0,0,0,0};

    public static void main(String[] args) {
        fillOutMap();
        System.out.println("Enter player count [1-4]: ");
        int playerCount = scan.nextInt();
        start(playerCount);
    }

    static void start(int playerCount) {
        int roll, intput;
        String input;
        boolean gameEnd = false, turnEnd, moveEnd;
        fillOutPlayers(playerCount);
        scan.nextLine();
        while (!gameEnd) {
            for (int i = 0; i < playerCount; i++) {
                turnEnd = false;
                while (!turnEnd) {
                    if (players[i] == 0 && scores[i] == 4) break;
                    System.out.printf("Player %d turn %n", i+1);
                    printMap();
                    roll = roll();
                    System.out.println("Roll: " + roll);
                    moveEnd = false;
                    if (roll == 6 && players[i] + scores[i] == 4) {
                        placeNewPawn(i);
                        players[i]--;
                        System.out.println("Placed new pawn. Press [Enter] to continue...");
                        scan.nextLine();
                    }
                    else if (roll != 6 && players[i] + scores[i] == 4) {
                        System.out.println("No action possible. Press [Enter] to continue...");
                        scan.nextLine();
                        turnEnd = true;
                    }
                    else if (roll == 6 && isPawnOfPlayerOnIndex(0, getStartingIndexByPlayer(i))) {
                        while (!moveEnd) {
                            System.out.println("Enter position of pawn that you want to move with:");
                            intput = scan.nextInt();
                            scan.nextLine();
                            if (isPawnOfPlayerOnIndex(intput%40, i)) {
                                if (shouldAddPoint(i, intput, (intput+roll)%40)){
                                    scores[i]++;
                                    Map[intput] = 'x';
                                    break;
                                }
                                else if (!isIndexFree((intput+roll)%40)){
                                    if (isPawnOfPlayerOnIndex((intput+roll)%40, i)) {
                                        System.out.println("You can't kill your own pawn!");
                                        break;
                                    }
                                    else
                                        removePawnFromIndex((intput+roll)%40);
                                }
                                movePawn(intput, (intput+roll)%40, i);
                                moveEnd = true;
                            }
                            else System.out.println("Wrong position provided!");
                        }
                    }
                    else if (roll == 6 && players[i] == 0) {
                        while (!moveEnd) {
                            System.out.println("Enter position of pawn that you want to move with:");
                            intput = scan.nextInt();
                            scan.nextLine();
                            if (isPawnOfPlayerOnIndex(intput%40, i)) {
                                if (shouldAddPoint(i, intput, (intput+roll)%40)){
                                    scores[i]++;
                                    Map[intput] = 'x';
                                    break;
                                }
                                else if (!isIndexFree((intput+roll)%40)){
                                    if (isPawnOfPlayerOnIndex((intput+roll)%40, i)) {
                                        System.out.println("You can't kill your own pawn");
                                        break;
                                    }
                                    else {
                                        removePawnFromIndex((intput+roll)%40);
                                    }
                                }
                                movePawn(intput, (intput+roll)%40, i);
                                moveEnd = true;
                            }
                            else System.out.println("Wrong position provided!");
                        }
                    }

                    else if (roll == 6 && players[i] + scores[i] < 4) {
                        System.out.println("Would you like to place a new pawn? (y/n)");
                        input = scan.nextLine();
                        if (input.equals("y")) {
                            placeNewPawn(i);
                            players[i]--;
                            System.out.println("Placed new pawn. Press [Enter] to continue...");
                            scan.nextLine();
                        }
                        else {
                            while (!moveEnd) {
                                System.out.println("Enter position of pawn that you want to move with:");
                                intput = scan.nextInt();
                                scan.nextLine();
                                if (isPawnOfPlayerOnIndex(intput%40, i)) {
                                    if (shouldAddPoint(i, intput, (intput+roll)%40)){
                                        scores[i]++;
                                        Map[intput] = 'x';
                                        break;
                                    }
                                    else if (!isIndexFree((intput+roll)%40)){
                                        if (isPawnOfPlayerOnIndex((intput+roll)%40, i)) {
                                            System.out.println("You can't kill your own pawn");
                                            break;
                                        }
                                        else {
                                            removePawnFromIndex((intput+roll)%40);
                                        }
                                    }
                                    movePawn(intput, (intput+roll)%40, i);
                                    moveEnd = true;
                                }
                                else System.out.println("Wrong position provided!");
                            }
                        }
                    }
                    else if (roll != 6 && players[i] + scores[i] < 4) {
                        while (!moveEnd) {
                            System.out.println("Enter position of pawn that you want to move with:");
                            intput = scan.nextInt();
                            scan.nextLine();
                            if (isPawnOfPlayerOnIndex(intput%40, i)) {
                                if (shouldAddPoint(i, intput, (intput+roll)%40)){
                                    scores[i]++;
                                    Map[intput] = 'x';
                                    break;
                                }
                                else if (!isIndexFree((intput+roll)%40)){
                                    if (isPawnOfPlayerOnIndex((intput+roll)%40, i)) {
                                        System.out.println("You can't kill your own pawn!");
                                        break;
                                    }
                                    else {
                                        removePawnFromIndex((intput+roll)%40);
                                    }
                                }
                                movePawn(intput, (intput+roll)%40, i);
                                moveEnd = true;
                            }
                            else System.out.println("Wrong position provided!");
                        }
                        turnEnd = true;
                        if (scores[i] == 4) {
                            System.out.println("Player " + i + " finished");
                            break;
                        }
                    }
                }
            }
            printScores(playerCount);
            if (checkIfGameEnd(playerCount)) gameEnd = true;
        }
    }

    static void start(int playerCount, int[][] gameState, int[] rolls, int[] decisions) {

    }

    static int roll() {
        //return r.nextInt(7 - 1) + 1;
        return 6;
    }

    static boolean isIndexFree(int index) {
        return Map[index] == 'x';
    }

    static boolean isPawnOfPlayerOnIndex(int index, int player) {
        return Map[index] == getPawnByPlayer(player);
    }

    static void removePawnFromIndex(int index) {
        switch (Map[index]) {
            case 'a' -> players[0]++;
            case 'b' -> players[1]++;
            case 'c' -> players[2]++;
            case 'd' -> players[3]++;
        }
        Map[index] = 'x';
    }

    static int getStartingIndexByPlayer(int player) {
        int index;
        switch (player){
            case 0 -> index = 0;
            case 1 -> index = 10;
            case 2 -> index = 20;
            case 3 -> index = 30;
            default -> index = -1;
        }
        return index;
    }

    static void printScores(int playerCount) {
        System.out.println("###########################");
        System.out.println("#         SCORES          #");
        System.out.println("# Player 1: " + scores[0] + "             #");
        if (playerCount > 1) System.out.println("# Player 2: " + scores[1] + "             #");
        if (playerCount > 2) System.out.println("# Player 3: " + scores[2] + "             #");
        if (playerCount > 3) System.out.println("# Player 4: " + scores[3] + "             #");
        System.out.println("###########################");
    }
    static boolean shouldAddPoint(int player, int indexFrom, int indexTo) {
        int startingIndex = getStartingIndexByPlayer(player);
        if (player == 0) {
            return (indexFrom >= 34 && indexFrom <= 39 && indexTo >= 0 && indexTo <= 5);
        }
        else {
            return (indexFrom < startingIndex && indexTo >= startingIndex);
        }
    }
    static void movePawn(int indexFrom, int indexTo, int player) {
        Map[indexFrom] = 'x';
        Map[indexTo] = getPawnByPlayer(player);
    }
    static void placeNewPawn(int player) {
        switch (player) {
            case 0 -> Map[0] = 'a';
            case 1 -> Map[10] = 'b';
            case 2 -> Map[20] = 'c';
            case 3 -> Map[30] = 'd';
        }
    }

    static char getPawnByPlayer(int player) {
        char pawn;
        switch (player) {
            case 0 -> pawn = 'a';
            case 1 -> pawn = 'b';
            case 2 -> pawn = 'c';
            case 3 -> pawn = 'd';
            default -> pawn = 'x';
        }
        return pawn;
    }

    static void printPawn(int index) {
        System.out.print(Map[index]);
    }
    static boolean checkIfGameEnd(int playerCount) {
        int playerEnd = 0;
        for (int i = 0; i < 4; i++) {
            if (scores[i] == 4) playerEnd++;
        }
        if (playerCount == 1 && playerEnd == 1) return true;
        else return playerCount > 1 && playerCount - playerEnd == 1;
    }
    static void fillOutMap() {
        for (int i = 0; i < 40; i++)
            Map[i] = 'x';
    }

    static void fillOutPlayers(int playerCount) {
        for (int i = 0; i < playerCount; i++)
            players[i] = 4;
    }

    static void printString(String f) {
        System.out.print(f);
    }

    static void printSpace(int j) {
        for (int i = 0; i < j; i++) System.out.print(' ');
    }

    static void printMap() {
        printSpace(15);
        printString("0");
        System.out.println();
        printSpace(3);
        printString( players[3] >= 4 ? "d " : "  ");
        printString( players[3] >= 3 ? "d " : "  ");
        printSpace(4);
        printPawn(38);
        printSpace(1);
        printPawn(39);
        printSpace(1);
        printPawn(0);
        printSpace(1);
        printSpace(4);
        printString( players[0] >= 3 ? "a " : "  ");
        printString( players[0] >= 4 ? "a " : "  ");
        System.out.println();
        printSpace(3);
        printString( players[3] >= 2 ? "d " : "  ");
        printString( players[3] >= 1 ? "d " : "  ");
        printSpace(4);
        printPawn(37);
        printSpace(3);
        printPawn(1);
        printSpace(1);
        printSpace(4);
        printString( players[0] >= 1 ? "a " : "  ");
        printString( players[0] >= 2 ? "a " : "  ");
        System.out.println();
        printSpace(11);
        printPawn(36);
        printSpace(3);
        printPawn(2);
        printSpace(10);
        System.out.println();
        printSpace(11);
        printPawn(35);
        printSpace(3);
        printPawn(3);
        printSpace(10);
        System.out.println();
        printString("30 ");
        printPawn(30);
        printSpace(1);
        printPawn(31);
        printSpace(1);
        printPawn(32);
        printSpace(1);
        printPawn(33);
        printSpace(1);
        printPawn(34);
        printSpace(1);
        printSpace(2);
        printPawn(4);
        printSpace(1);
        printPawn(5);
        printSpace(1);
        printPawn(6);
        printSpace(1);
        printPawn(7);
        printSpace(1);
        printPawn(8);
        printSpace(1);
        System.out.println();
        printSpace(3);
        printPawn(29);
        printSpace(19);
        printPawn(9);
        System.out.println();
        printSpace(3);
        printPawn(28);
        printSpace(1);
        printPawn(27);
        printSpace(1);
        printPawn(26);
        printSpace(1);
        printPawn(25);
        printSpace(1);
        printPawn(24);
        printSpace(3);
        printPawn(14);
        printSpace(1);
        printPawn(13);
        printSpace(1);
        printPawn(12);
        printSpace(1);
        printPawn(11);
        printSpace(1);
        printPawn(10);
        printSpace(1);
        printString("10");
        System.out.println();
        printSpace(11);
        printPawn(23);
        printSpace(3);
        printPawn(15);
        System.out.println();
        printSpace(11);
        printPawn(22);
        printSpace(3);
        printPawn(16);
        System.out.println();
        printSpace(3);
        printString( players[2] >= 2 ? "c " : "  ");
        printString( players[2] >= 1 ? "c " : "  ");
        printSpace(4);
        printPawn(21);
        printSpace(3);
        printPawn(17);
        printSpace(5);
        printString( players[1] >= 1 ? "b " : "  ");
        printString( players[1] >= 2 ? "b " : "  ");
        System.out.println();
        printSpace(3);
        printString( players[2] >= 4 ? "c " : "  ");
        printString( players[2] >= 3 ? "c " : "  ");
        printSpace(4);
        printPawn(20);
        printSpace(1);
        printPawn(19);
        printSpace(1);
        printPawn(18);
        printSpace(5);
        printString( players[1] >= 3 ? "b " : "  ");
        printString( players[1] >= 4 ? "b " : "  ");
        System.out.println();
        printSpace(11);
        printString("20");
        System.out.println();
    }
}
