import java.util.Random;

public class Monster extends Fighter {
    public Monster(String name, int level) {
        super(name);
        this.level = level;

        healthBase = 60;
        healthIncrement = 0.25;
        maxHealth = (long) (healthBase * Math.pow(2, healthIncrement * (level - 1)));
        health = maxHealth;


        damageBase = 15;
        damageIncrement = 0.22;
        damage = (long) (damageBase * Math.pow(2, damageIncrement * (level - 1)));

        dexterity = 15;

        experienceBase = 300;
        experienceIncrement = 0.4;
        experience = (long) (experienceBase * Math.pow(2, experienceIncrement * (level - 1)));


        moneyBase = 100;
        moneyIncrement = 0.3;
        money = (long) (moneyBase * Math.pow(2, moneyIncrement * (level - 1)));
    }

    @Override
    public boolean attack(Fighter fighter) {
        Random rand = new Random();
        int randInt = rand.nextInt(0, 101);
        if (dexterity * 3 < randInt) {
            System.out.printf("%s missed!%n", name);
        } else {
            fighter.health -= damage;
            System.out.printf("%s damaged player by %d HP!%n%s has %d health remaining!%n", name, damage, fighter.name,
                    fighter.health);
            if (fighter.health < 0) {
                fighter.isAlive = false;
                System.out.printf("Oh no! %s is dead!%n", fighter.name);
            }
        }
        return fighter.isAlive;
    }
}
