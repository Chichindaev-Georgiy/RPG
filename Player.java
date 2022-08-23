import java.util.Random;

public class Player extends Fighter {
    private double experienceGain;
    private long experienceLevel;
    private Backpack backpack;
    public Player(String name) {
        super(name);
        healthBase = 100;
        maxHealth = healthBase;
        health = healthBase;
        healthIncrement = 0.2;

        damageBase = 25;
        damage = damageBase;
        damageIncrement = 0.22;

        dexterity = 25;

        experienceBase = 1000;
        experience = 0;
        experienceGain = 1.5;
        experienceIncrement = 0.4;
        experienceLevel = experienceBase;

        money = 0;
        moneyBase = 0;
        moneyIncrement = 0.0;

        level = 1;
        backpack = new Backpack(10);
    }

    @Override
    public boolean attack(Fighter aMonster) {
        Random rand = new Random();
        int randInt = rand.nextInt(0, 101);
        if (dexterity * 3 > randInt) {
            aMonster.health -= damage;
            System.out.printf("%s damaged monster by %d HP! %n%s now have %d health...%n", name,
                    damage, aMonster.name, aMonster.health);

        }
        if (aMonster.getHealth() <= 0) {
            aMonster.isAlive = false;
            int levelDifference = aMonster.level - level;
            long xpGain = (long) (Math.pow(experienceGain, levelDifference) * aMonster.experience);
            experience += xpGain;
            System.out.printf("Enemy destroyed! You've got %d experience and %d gold.%n", xpGain, aMonster.money);
            money += aMonster.money;
        }
        if (experience >= experienceLevel) {
            levelUp();
            System.out.printf("%d experience remaining to level up.%n", experienceLevel - experience);
        }
        return aMonster.isAlive;
    }

    public void levelUp() {
        level += 1;
        System.out.printf("Your level upped to %d! Your experience is %d XP.%n", level, experience);
        experienceLevel = (long) (experienceBase * Math.pow(2, experienceIncrement * (level - 1)));
        maxHealth = (long) (healthBase * Math.pow(2, healthIncrement * (level - 1)));
        health = maxHealth;
        showInfo();
    }

    public void showInfo() {
        int xpBarSize = 25;
        int xp;
        if (level == 0) {
            xp = (int) (xpBarSize * (experienceLevel - experience));
        } else {
            long previousBaseLevel = (long) (experienceBase * Math.pow(2, experienceIncrement * (level - 2)));
            xp = (int) (xpBarSize * (experienceLevel - experience) / (experienceLevel - previousBaseLevel));
        }
        System.out.printf("%s's XP progress: %d%n [", name, level);
        int i = 0;
        for (; i < xp; i++) {
            System.out.print("X");
        }
        for (; i < xpBarSize; i++) {
            System.out.print("_");
        }
        System.out.printf("]%nThere are %d XP remaining until level up.%nHealth: %d out of %d.%n",
                experienceLevel - experience, health, maxHealth);
    }

    public long getMoneyAmount() {
        return money;
    }

    public void trade(long amount) {
        money -= amount;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public double getExperienceGain() {
        return experienceGain;
    }

    public void gainXp(long xp) {
        experience += xp;
    }

    public long getExperienceLevel() {
        return experienceLevel;
    }
}
