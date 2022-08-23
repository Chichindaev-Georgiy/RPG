import java.util.Random;

public class Battle {

    public static void letsFight(Player player, World.ComeBack comeBack) {
        Runnable runnable = () -> {
            Random rand = new Random();
            int randInt = rand.nextInt(0, 101);
            int levelDiffer = rand.nextInt(-1, 2);
            int monsterLevel = Math.max(player.getLevel() + levelDiffer, 1);
            Monster monster;
            if (randInt < 60) {
                monster = new Skeleton(monsterLevel);
            } else {
                monster = new Goblin(monsterLevel);
            }
            int round = 1;
            boolean alive = true;
            while(alive) {
                System.out.printf("----Move: %d----%n%s is swinging...%n", round, player.getName());
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                alive = player.attack(monster);
                if (!alive) {
                    break;
                }
                System.out.printf("%s is swinging...%n", monster.getName());
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                alive = monster.attack(player);
                round++;
            }
            if (player.isAlive) {
                comeBack.win(monster);
            } else {
                comeBack.lost();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
