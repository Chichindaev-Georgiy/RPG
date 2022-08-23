public abstract class Fighter {
    protected final String name;
    protected boolean isAlive;

    protected long healthBase;
    protected long maxHealth;
    protected long health;
    protected double healthIncrement;

    protected long damageBase;
    protected long damage;
    protected double damageIncrement;

    protected int dexterity;

    protected long experienceBase;
    protected long experience;
    protected double experienceIncrement;

    protected long money;
    protected long moneyBase;
    protected double moneyIncrement;

    protected int level;

    public abstract boolean attack(Fighter fighter);

    public Fighter(String name) {
        this.name = name;
        isAlive = true;
    }

    public long getHealth() {
        return health;
    }

    public long getMaxHealth() {
        return maxHealth;
    }

    public String getName() {
        return name;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }
}
