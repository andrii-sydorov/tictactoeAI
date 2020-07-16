package tictactoe;

import java.util.Scanner;

public class Fight {
   /**
    * Description
    * 
    * Now it is time to make a working game!
    * 
    * In the last stage, make it so you can play a full game with a friend. First
    * one of you moves as X, and then the other one moves as O.
    * 
    * You need to create a game loop. The game starts with empty cells and ends
    * when someone wins or there is a draw. You need to output the final result
    * after the end of the game.
    * 
    * Good luck gaming! Example
    * 
    * The example below shows how your program should work.
    * 
    * --------- 
    * |       | 
    * |       |
    * |       | 
    * --------- 
    * Enter the coordinates: 2 2 
    * --------- 
    * |       | 
    * |   X   |
    * |       | 
    * --------- 
    * Enter the coordinates: 2 2 
    * This cell is occupied! Choose another one! 
    * Enter the coordinates: two two 
    * You should enter numbers! 
    * Enter the coordinates: 1 4 
    * Coordinates should be from 1 to 3! 
    * Enter the coordinates: 1 3 
    * --------- 
    * | O     | 
    * |   X   | 
    * |       | 
    * --------- 
    * Enter the coordinates: 3 1 
    * --------- 
    * | O     | 
    * |   X   | 
    * |     X | 
    * --------- 
    * Enter the coordinates: 1 2 
    * ---------
    * | O     | 
    * | O X   | 
    * |     X | 
    * --------- 
    * Enter the coordinates: 1 1 
    * --------- 
    * | O     | 
    * | O X   | 
    * | X   X | 
    * --------- 
    * Enter the coordinates: 3 2 
    * --------- 
    * | O     | 
    * | O X O | 
    * | X   X | 
    * --------- 
    * Enter the coordinates: 2 1 
    * --------- 
    * | O     | 
    * | O X O | 
    * | X X X | 
    * --------- 
    * X wins
    * 
    * @param args
    */
   public static void main(String[] args) {
      int size = 9; // matrix size 3x3
      char[][] field = new char[(int) Math.sqrt(size) + 2][(int) Math.sqrt(size) + ((int) Math.sqrt(size) - 1) + 4];
      SolutionFight a = new SolutionFight(size);
      Scanner sc = new Scanner(System.in);
      String s = null;
      buildFrame(field);
      int index = 0;
      while (s == null) {
         int[] coordinates = checkIn(sc, a, size);
         userFillMatrix(a, field, coordinates, index);
         s = checkMatrix(a, field, size);
         index++;
      }
      print(field);
      System.out.println(s);
      sc.close();
   }

   static void buildFrame(char[][] field) {

      for (int i = 0; i < field.length; i++) {
         for (int j = 0; j < field[i].length; j++) {
            if ((j == 0 || j == field[i].length - 1) && (i != 0 && i != field.length - 1)) {
               field[i][j] = '|';
            } else if (i == 0 || i == field.length - 1) {
               field[i][j] = '-';
            } else {
               field[i][j] = ' ';
            }
         }
      }
      print(field);
   }

   static void print(char[][] field) {
      for (int i = 0; i < field.length; i++) {
         for (int j = 0; j < field[i].length; j++) {
            System.out.print(field[i][j]);
         }
         System.out.println();
      }
   }

   static void print(int[] arInt) {
      for (int i = 0; i < arInt.length; i++) {
         System.out.print(arInt[i] + " ");
      }
   }

   static SolutionFight fillMatrix(char[][] field, Scanner sc, int size) {
      String in = sc.nextLine().trim();
      SolutionFight ans = new SolutionFight();
      ans.indexI = new int[size];
      ans.indexJ = new int[size];
      int indexAns = 0;
      for (int i = 0; i < field.length; i++) {
         for (int j = 0; j < field[i].length; j++) {
            if (i > 0 && i < field.length - 1 && j > 0 && j < field[i].length - 1) {
               if (j % 2 == 0) {
                  field[i][j] = in.charAt(0);
                  if (field[i][j] == 'X' || field[i][j] == 'O') {
                     ans.indexI[indexAns] = i;
                     ans.indexJ[indexAns] = j;
                     indexAns++;
                  }
               } else {
                  field[i][j] = ' ';
               }
            } else {
               continue;
            }
         }
      }
      return ans;
   }

   static String checkMatrix(SolutionFight arrInt, char[][] field, int size) {
//      check row
      boolean xRowWins = false;
      boolean oRowWins = false;
      for (int i = 0; i < field.length; i++) {
         int countRowX = 0;
         int countRowO = 0;
         for (int j = 0; j < field[i].length; j++) {
            if (i > 0 && i < field.length - 1 && j > 0 && j < field[i].length - 1) {
               if (j % 2 == 0) {
                  if (field[i][j] == 'X') {
                     countRowX++;
                  }
                  if (field[i][j] == 'O') {
                     countRowO++;
                  }
               }
            }
         }
//         results of checking rows
         if (countRowX == Math.sqrt(size)) {
            xRowWins = true;
         }
         if (countRowO == Math.sqrt(size)) {
            oRowWins = true;
         }
      }
// check column
      boolean xColWins = false;
      boolean oColWins = false;
      for (int j = 0; j < field[0].length; j++) {
         int countColX = 0;
         int countColO = 0;
         for (int i = 0; i < field.length; i++) {
            if (i > 0 && i < field.length - 1 && j > 0 && j < field[i].length - 1) {
               if (j % 2 == 0) {
                  if (field[i][j] == 'X') {
                     countColX++;
                  }
                  if (field[i][j] == 'O') {
                     countColO++;
                  }
               }
            }
         }
         if (countColX == Math.sqrt(size)) {
            xColWins = true;
         }
         if (countColO == Math.sqrt(size)) {
            oColWins = true;
         }
      }
//      results of checking columns
      if (xColWins || xRowWins == true) {
         return "X wins";
      }

      if (oColWins || oRowWins == true) {
         return "O wins";
      }
//      check main diagonal
      int countMainDiagX = 0;
      int countMainDiagO = 0;
      for (int i = 0; i < field.length; i++) {
         for (int j = 0; j < field[i].length; j++) {
            if (i > 0 && i < field.length - 1 && j > 0 && j < field[i].length - 1) {
               if (i * 2 == j) {
                  if (field[i][j] == 'X') {
                     countMainDiagX++;
                  }
                  if (field[i][j] == 'O') {
                     countMainDiagO++;
                  }
               }
            }
         }
      }
      // check secondary diagonal
      int countSecDiagX = 0;
      int countSecDiagO = 0;
      for (int i = 0; i < field.length; i++) {
         for (int j = 0; j < field[i].length; j++) {
            if (i > 0 && i < field.length - 1 && j > 0 && j < field[i].length - 1) {
               if ((field[i].length - 1) - i * 2 == j) {
                  if (field[i][j] == 'X') {
                     countSecDiagX++;
                  }
                  if (field[i][j] == 'O') {
                     countSecDiagO++;
                  }
               }
            }
         }
      }
      if (countMainDiagX == Math.sqrt(size)) {
         return "X wins";
      }
      if (countMainDiagO == Math.sqrt(size)) {
         return "O wins";
      }
      if (countSecDiagX == Math.sqrt(size)) {
         return "X wins";
      }
      if (countSecDiagO == Math.sqrt(size)) {
         return "O wins";
      }
      if (arrInt.numberOfOccupiedEl == size) {
         return "Draw";
      }
      print(field);
      return null;
   }

   static int[] checkIn(Scanner inSc, SolutionFight arrInt, int size) {
      while (true) {
         System.out.print("Enter the coordinates: ");
         String[] strIn = inSc.nextLine().trim().split(" ");
//      if (strIn.length != 2) {
//         continue;
//      }
         if (isInteger(strIn[0]) && isInteger(strIn[1]) == true) {
            int[] coordinates = new int[] { Integer.valueOf(strIn[0]), Integer.valueOf(strIn[1]) };
            boolean notInLimit = false;
            for (int i = 0; i < coordinates.length; i++) {
               if (coordinates[i] < 1 || coordinates[i] > Math.sqrt(size)) {
                  notInLimit = true;
                  break;
               }
            }
            if (notInLimit) {
               System.out.printf("Coordinates should be from 1 to %d!%n", (int) Math.sqrt(size));
               continue;
            } else {
               boolean isOccupied = false;
               for (int i = 0; i < arrInt.indexI.length; i++) {
                  if (arrInt.indexI[i] == Math.sqrt(size) + 1 - coordinates[1]
                        && arrInt.indexJ[i] == coordinates[0] * 2) {
                     isOccupied = true;
                     break;
                  } else {
                     continue;
                  }
               }
               if (isOccupied) {
                  System.out.println("This cell is occupied! Choose another one!");
                  continue;
               } else {
                  return new int[] { (int) Math.sqrt(size) + 1 - coordinates[1], coordinates[0] * 2 };
               }
            }
         } else
            System.out.println("You should enter numbers!");
         ;
      }
   }

   static boolean isInteger(String inStr) {
      if (inStr == null) {
         return false;
      }
      if (inStr.isEmpty()) {
         return false;
      }
      try {
         int temp = Integer.valueOf(inStr);
      } catch (NumberFormatException nfe) {
         return false;
      }
      return true;
   }

   static void userFillMatrix(SolutionFight a, char[][] field, int[] coordinates, int index) {
      if (index % 2 == 0) {
         field[coordinates[0]][coordinates[1]] = 'X';
      } else {
         field[coordinates[0]][coordinates[1]] = 'O';
      }
      a.indexI[a.numberOfOccupiedEl] = coordinates[0];
      a.indexJ[a.numberOfOccupiedEl] = coordinates[1];
      a.numberOfOccupiedEl++;
   }
}
