package InitialSetUp;

import java.util.Scanner;

public class InitialSetUp {
   /**
    * Description
    * 
    * In this project, you'll write a game called Tic-Tac-Toe that you can play
    * with your computer. The computer will have three levels of difficulty - easy,
    * medium, hard.
    * 
    * But, for starters, let's write a program that knows how to work with
    * coordinates and determine the state of the game.
    * 
    * Suppose the bottom left cell has the coordinates (1, 1) and the top right
    * cell has the coordinates (3, 3) like in this table:
    * 
    * (1, 3) (2, 3) (3, 3) 
    * (1, 2) (2, 2) (3, 2) 
    * (1, 1) (2, 1) (3, 1)
    * 
    * The program should work in the following way:
    * 
    * 1. Get the 3x3 field from the first input line (it contains 9 symbols containing X, O and _, the latter means it's an empty cell), 
    * 2. Output this 3x3 field with cells before the user's move, 
    * 3. Then ask the user about his next move, 
    * 4. Then the user should input 2 numbers that represent the cell on which user wants to
    * make his X or O. (9 symbols representing the field would be on the first line
    * and these 2 numbers would be on the second line of the user input). Since the
    * game always starts with X, if the number of X's and O's on the field is the
    * same, the user should make a move with X, and if X's is one more than O's,
    * then the user should make a move with O. 
    * 5. Then output the table including the user's most recent move. 
    * 6. Then output the state of the game.
    * 
    * Possible states:
    * 
    * "Game not finished" - when no side has a three in a row but the field has empty cells; 
    * "Draw" - when no side has a three in a row and the field has no empty cells; 
    * "X wins" - when the field has three X in a row; 
    * "O wins" - when the field has three O in a row;
    * 
    * If the user input wrong coordinates, the program should keep asking until the
    * user enters coordinate that represents an empty cell on the field and after
    * that output the field with that move. You should output the field only 2
    * times - before the move and after a legal move. 
    * 
    * Examples
    * 
    * The examples below show how your program should work.
    * 
    * Example 1:
    * 
    * Enter cells: _XXOO_OX_ 
    * --------- 
    * |   X X | 
    * | O O   | 
    * | O X   | 
    * --------- 
    * Enter the coordinates: 1 1 
    * This cell is occupied! Choose another one! 
    * Enter the coordinates: one 
    * You should enter numbers! Enter the coordinates: one three
    * You should enter numbers! Enter the coordinates: 4 1 
    * Coordinates should be from 1 to 3! 
    * Enter the coordinates: 1 3 
    * --------- 
    * | X X X | 
    * | O O   | 
    * | O X   |
    * --------- 
    * X wins
    * 
    * Example 2:
    * 
    * Enter cells: XX_XOXOO_ 
    * --------- 
    * | X X   | 
    * | X O X | 
    * | O O   | 
    * --------- 
    * Enter the coordinates: 3 1 
    * --------- 
    * | X X   | 
    * | X O X | 
    * | O O O | 
    * --------- 
    * O wins
    * 
    * Example 3:
    * 
    * Enter cells: OX_XOOOXX 
    * --------- 
    * | O X   | 
    * | X O O | 
    * | O X X | 
    * --------- 
    * Enter the coordinates: 3 3 
    * --------- 
    * | O X X | 
    * | X O O | 
    * | O X X | 
    * --------- 
    * Draw
    * 
    * Example 4:
    * 
    * Enter cells: _XO_OX___ 
    * --------- 
    * |   X O | 
    * |   O X | 
    * |       | 
    * --------- 
    * Enter the coordinates: 1 1 
    * --------- 
    * |   X O | 
    * |   O X | 
    * | X     | 
    * --------- 
    * Game not finished
    * 
    */
   public static void main(String[] args) {
      System.out.print("Enter cells: ");
      Scanner sc = new Scanner(System.in);
      String init = sc.nextLine().replaceAll("_", " ");
      int length = init.length();
      int size = (int) Math.sqrt(length);
      char[][] field = new char[size + 2][size + (size - 1) + 4];
      CheckData isValid = new CheckData();
      Busy isBusy = buildField(field, init, length);
      String result = null;
      do {
         System.out.print("Enter the coordinates: ");
         String data = sc.nextLine();
         isValid = isInteger(data, size);
         if (!isValid.valid) {
            System.out.println("You should enter numbers!");
            continue;
         }
         if (!inRange(isValid, size)) {
            System.out.printf("Coordinates should be from 1 to %d!", size);
            System.out.println();
            continue;
         }
         if (isOccupied(isValid, isBusy)) {
            System.out.println("This cell is occupied! Choose another one!");
            continue;
         }
         Game(isValid, isBusy, field);
         print(field);
         result = check(field, isBusy, size);
      } while (result == null);
      System.out.println(result);
      sc.close();
   }

   static Busy buildField(char[][] field, String init, int length) {
      Busy isBusy = new Busy(length);
      int rowNumber = field.length;
      int columnNumber = field[0].length;
      int index = 0;
      for (int i = 0; i < rowNumber; i++) {
         for (int j = 0; j < columnNumber; j++) {
            if (i == 0 || i == rowNumber - 1) {
               field[i][j] = '-';
            } else if ((j == 0 || j == columnNumber - 1) && ((i > 0) && (i < rowNumber - 1))) {
               field[i][j] = '|';
            } else if (j % 2 == 0) {
               field[i][j] = init.charAt(index);
               if (field[i][j] != ' ') {
                  isBusy.indexI[isBusy.isOccupied] = i;
                  isBusy.indexJ[isBusy.isOccupied] = j;
                  isBusy.isOccupied++;
               }
               index++;
            } else {
               field[i][j] = ' ';
            }
         }
         System.out.print(field[i]);
         System.out.println();
      }
      return isBusy;
   }

   static CheckData isInteger(String data, int size) {
      CheckData check = new CheckData();
      if (data == null) {
         check.valid = false;
         return check;
      }
      if (data.isEmpty()) {
         check.valid = false;
         return check;
      }
      String[] strArrCoord = data.split(" ");
      if (strArrCoord.length != 2) {
         check.valid = false;
         return check;
      }
      int[] intArrCoord = new int[strArrCoord.length];
      for (int i = 0; i < strArrCoord.length; i++) {
         try {
            intArrCoord[i] = Integer.valueOf(strArrCoord[i]);
         } catch (NumberFormatException nfe) {
            check.valid = false;
            return check;
         }
      }
      check.coordinates = intArrCoord;
      check.valid = true;
      return check;
   }

   static boolean inRange(CheckData a, int size) {
      for (int i = 0; i < a.coordinates.length; i++) {
         if (a.coordinates[i] < 1 || a.coordinates[i] > size) {
            return false;
         }
      }
      a.coordinates = new int[] { size + 1 - a.coordinates[1], 2 * a.coordinates[0] };
      return true;
   }

   static boolean isOccupied(CheckData a, Busy b) {
      int[][] arrIntIsBusy = new int[][] { b.indexI, b.indexJ };
      for (int i = 0; i < b.isOccupied; i++) {
         int equal = 0;
         for (int j = 0; j < a.coordinates.length; j++) {
            if (a.coordinates[j] == arrIntIsBusy[j][i]) {
               equal++;
            }
         }
         if (equal == 2) {
            return true;
         }
      }
      return false;
   }

   static void Game(CheckData a, Busy b, char[][] field) {
      if (b.isOccupied % 2 == 0) {
         field[a.coordinates[0]][a.coordinates[1]] = 'X';
      } else {
         field[a.coordinates[0]][a.coordinates[1]] = 'O';
      }
      b.indexI[b.isOccupied] = a.coordinates[0];
      b.indexJ[b.isOccupied] = a.coordinates[1];
      b.isOccupied++;
   }

   static void print(char[][] field) {
      for (int i = 0; i < field.length; i++) {
         for (int j = 0; j < field[i].length; j++) {
            System.out.print(field[i][j]);
         }
         System.out.println();
      }
   }

   static String check(char[][] field, Busy a, int size) {
//      check the row and main diagonal
      int countMainDiagX = 0;
      int countMainDiagO = 0;
      for (int i = 1; i < field.length - 1; i++) {
         int countRowX = 0;
         int countRowO = 0;
         for (int j = 2; j < field[i].length - 1; j += 2) {
            if (field[i][j] == 'X') {
               countRowX++;
            }
            if (field[i][j] == 'O') {
               countRowO++;
            }
            if (j == 2 * i) {
               if (field[i][j] == 'X') {
                  countMainDiagX++;
               }
               if (field[i][j] == 'O') {
                  countMainDiagO++;
               }
            }
         }
         if (countRowX == size) {
            return "X wins";
         }
         if (countRowO == size) {
            return "O wins";
         }
      }
      if (countMainDiagX == size) {
         return "X wins";
      }
      if (countMainDiagO == size) {
         return "O wins";
      }
//      check the column and secondary diagonal
      int countSecDiagX = 0;
      int countSecDiagO = 0;
      for (int j = 2; j < field[0].length - 1; j += 2) {
         int countColX = 0;
         int countColO = 0;
         for (int i = 1; i < field.length - 1; i++) {
            if (field[i][j] == 'X') {
               countColX++;
            }
            if (field[i][j] == 'O') {
               countColO++;
            }
            if ((field[i].length - 1) - i * 2 == j) {
               if (field[i][j] == 'X') {
                  countSecDiagX++;
               }
               if (field[i][j] == 'O') {
                  countSecDiagO++;
               }
            }
         }
         if (countColX == size) {
            return "X wins";
         }
         if (countColO == size) {
            return "O wins";
         }
      }
      if (countSecDiagX == size) {
         return "X wins";
      }
      if (countSecDiagO == size) {
         return "O wins";
      }

//      Draw
      if (a.isOccupied == Math.pow(size, 2)) {
         return "Draw";
      }
//      not finished
      return "Game not finished";
   }
}

class CheckData {
   int[] coordinates;
   boolean valid;
}

class Busy {
   int isOccupied;
   int[] indexI;
   int[] indexJ;

   Busy(int size) {
      indexI = new int[size];
      indexJ = new int[size];
      isOccupied = 0;
   }

   Busy() {
   }
}