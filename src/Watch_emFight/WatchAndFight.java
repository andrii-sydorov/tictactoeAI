package Watch_emFight;

import java.util.Scanner;

public class WatchAndFight {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      // implement the main class
      Field.length = 9;
      Field.size = (int) Math.sqrt(Field.length);
      Field.field = new char[Field.size + 2][Field.size + (Field.size - 1) + 4];
      // create
      CheckInput checkInputLine = new CheckInput();
      while (true) {
         System.out.print("Input command: ");
         String[] arguments = sc.nextLine().toUpperCase().split(" ");
         if (checkInputLine.exitGame(arguments)) {
            break;
         }
         if (checkInputLine.lengthIsNotValid(arguments) || checkInputLine.isCommandsLegal(arguments)) {
            System.out.println("Bad parameters!");
            continue;
         }
      }
      sc.close();
   }
}

enum Comands {
   START, EASY, USER, STOP
}

class Field {
   static char[][] field;
   static int length;
   static int size;
}

class CheckInput implements checkUserInput{
   
    public boolean lengthIsNotValid(String[] str) {
      return str.length != 3 ? true : false;
   }
   
    public boolean isCommandsLegal(String[] str) {
      for (int i = 1; i < str.length; i++) {
         for (Comands com : Comands.values()) {
            if (str[i].equalsIgnoreCase(com.toString())) {
               return false;
            }
         }
      }
      return true;
   }
   
   public boolean exitGame(String[] str) {
     if(str[0].equalsIgnoreCase("stop")) {
        return true;
     }
     return false;
  }
}

interface checkUserInput {
   
    boolean lengthIsNotValid(String[] str);
    boolean isCommandsLegal(String[] str);
    boolean exitGame(String[] str);
   
  }
