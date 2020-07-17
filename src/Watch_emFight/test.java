package Watch_emFight;
public class test {
   public static void main(String[] args) {
      Player.size = 3;
      Player.field= new char[Player.size][Player.size];
      Player.field[0][0] = 'X';
      Player human = new Player(25);
      human.age = 25;
      human.print();
//      human.andrii = new Player(25);
//      human.yurii = new Player(25);
//      System.out.println(human.andrii.years());
//      System.out.println(human.yurii.years());
   }
}
