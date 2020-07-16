package EasyDoesIt;

import java.util.Scanner;
import java.util.Random;
public class EasyDoesIt {
   /**
    * Description
    * 
    * Now it is time to make a working game. In this version of the program, the
    * user will be playing as X, and the "easy" level computer will be playing as
    * O.
    * 
    * Let's make it so that at this level the computer will make random moves. This
    * level will be perfect for those who play this game for the first time! Well,
    * though... You can create a level of difficulty that will always give in and
    * never win the game. You can implement such a level along with "easy" level,
    * if you want, but it will not be tested.
    * 
    * When starting the program, an empty field should be displayed and the first
    * to start the game should be the user as X. Next the computer should make its
    * move as O. And so on until someone will win or there will be a draw.
    * 
    * At the very end of the game you need to print the final result of the game.
    * Example
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
    * Making move level "easy" 
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
    * Making move level "easy" 
    * --------- 
    * | O     | 
    * | O X   | 
    * |     X | 
    * --------- 
    * Enter the coordinates: 1 1 
    * --------- 
    * |     O | 
    * | O X   | 
    * | X   X | 
    * --------- 
    * Making move level "easy"
    * --------- 
    * |     O | 
    * | O X O | 
    * | X   X | 
    * --------- 
    * Enter the coordinates: 2 1
    * --------- 
    * |     O | 
    * | O X O | 
    * | X X X | 
    * --------- 
    * X wins
    */
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      int length = 9;
      int size = (int) Math.sqrt(length);
      char[][] field = new char[size + 2][size + (size - 1) + 4];
      CheckData isValid = new CheckData();
      Busy isBusy = buildField(field, length);
      Random r = new Random();
      String result = null;
      String name = "User";
      do {
         switch(name) {
         case "User":
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
         break;
         case "easy":
            System.out.println("Making move level \"easy\"");
            int[] arIntRandCoor = randomCoordinates(size, r);
            if (isOccupied(arIntRandCoor,isBusy)) {
               continue;
            }
            Game(arIntRandCoor, isBusy, field);
            break;
         }
         print(field);
         result = check(field, isBusy, size);
         name = changeName(name);
      } while (result == null);
      System.out.println(result);
      sc.close();
   }

   static Busy buildField(char[][] field, int length) {
      Busy isBusy = new Busy(length);
      int rowNumber = field.length;
      int columnNumber = field[0].length;
      for (int i = 0; i < rowNumber; i++) {
         for (int j = 0; j < columnNumber; j++) {
            if (i == 0 || i == rowNumber - 1) {
               field[i][j] = '-';
            } else if ((j == 0 || j == columnNumber - 1) && ((i > 0) && (i < rowNumber - 1))) {
               field[i][j] = '|';
            } else if (j % 2 == 0) {
               field[i][j] = ' ';
               if (field[i][j] != ' ') {
                  isBusy.indexI[isBusy.isOccupied] = i;
                  isBusy.indexJ[isBusy.isOccupied] = j;
                  isBusy.isOccupied++;
               }
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
   
   static boolean isOccupied(int[] randomCoordinates, Busy b) {
      int[][] arrIntIsBusy = new int[][] { b.indexI, b.indexJ };
      for (int i = 0; i < b.isOccupied; i++) {
         int equal = 0;
         for (int j = 0; j < randomCoordinates.length; j++) {
            if (randomCoordinates[j] == arrIntIsBusy[j][i]) {
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
   
   static void Game(int[] randomCoordinates, Busy b, char[][] field) {
      if (b.isOccupied % 2 == 0) {
         field[randomCoordinates[0]][randomCoordinates[1]] = 'X';
      } else {
         field[randomCoordinates[0]][randomCoordinates[1]] = 'O';
      }
      b.indexI[b.isOccupied] = randomCoordinates[0];
      b.indexJ[b.isOccupied] = randomCoordinates[1];
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
      return null;
   }
   
   static String changeName(String name) {
      if(name.equals("User")) {
         return "easy";
      } else {
         return "User";
      }
   }
   
   static int[] randomCoordinates(int size, Random r) {
      int coordinates1 = r.nextInt(size) + 1;
      int coordinates2 = r.nextInt(size) + 1;
      return new int[] {size + 1 - coordinates1, 2 * coordinates2};
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