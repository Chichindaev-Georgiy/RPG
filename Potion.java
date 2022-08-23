import java.util.Random;

public class Potion {
    public Potion(int level) {
        this.level = level;
        Random rand = new Random();

        int gradeScale = rand.nextInt(1, 101);
        if (gradeScale <= 50) {
            grade = 1;
        } else if (gradeScale <= 75) {
            grade = 2;
        } else if (gradeScale <= 88) {
            grade = 3;
        } else if (gradeScale <= 95) {
            grade = 4;
        } else {
            grade = 5;
        }
        health = (long) (Math.pow(2, healthIncrement * (level - 1)) * healthBase * grade * grade / 6);
        maxHealth = health;
        cost = (long) (Math.pow(2,costIncrement * (level - 1)) * costBase * grade * grade / 2.5);
    }

    public Potion(int level, int grade, long health, long maxHealth, int cost) {
        this.level = level;
        this.maxHealth = maxHealth;
        this.health = health;
        this.cost = cost;
        this.grade = grade;
    }

    private final long healthBase = 100;
    private final double healthIncrement = 0.2;
    private long health;
    private long maxHealth;
    private final int level;
    private long cost;
    private final long costBase = 40;
    private final double costIncrement = 0.2;
    private final int grade;

    public void heal(Player player) {
        long healthToHeal = player.getMaxHealth() - player.getHealth();
        if (healthToHeal >= health) {
            player.setHealth(player.getHealth() + health);
            health = 0;
            cost = 0;
        } else {
            health -= healthToHeal;
            player.setHealth(player.getMaxHealth());
            cost = health * cost / maxHealth;
        }
    }

    public int getLevel() {
        return level;
    }

    public long getCost() {
        return cost;
    }

    public long getHealth() {
        return health;
    }
}
