package Watch_emFight;

public class Player {
   
   int age;
   static int size;
   static char[][] field;

   Player(int years) {
      this.age = years;
   }

   void search() {
      System.out.println(age);
   }

   int years() {
      return age;
   }
   void print() {
      for(int i = 0; i < field.length; i++) {
         for(int j = 0; j < field.length; j++) {
            System.out.printf("%c",field[i][j]);
         }
         System.out.println();
      }
   }
}
