public class Skeleton extends Monster {
    public Skeleton(int level) {
        super("Skeleton", level);
        healthBase = 35;
        maxHealth = (long) (healthBase * Math.pow(2, healthIncrement * (level - 1)));
        health = maxHealth;

        damageBase = 12;
        damage = (long) (damageBase * Math.pow(2, damageIncrement * (level - 1)));

        dexterity = 13;

        experienceBase = 250;
        experience = (long) (experienceBase * Math.pow(2, experienceIncrement * (level - 1)));

        moneyBase = 40;
        money = (long) (moneyBase * Math.pow(2, moneyIncrement * (level - 1)));
    }
}
