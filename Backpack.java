public class Backpack {
    private final int totalAmount;
    private int amountOfStuff;
    private final Potion[] stuff;

    public Backpack(int totalAmount) {
        this.totalAmount = totalAmount;
        amountOfStuff = 0;
        stuff = new Potion[totalAmount];
        for (int i = 0; i < totalAmount; i++) {
            stuff[i] = null;
        }
    }

    public boolean put(int level) {
        boolean notFull = totalAmount > amountOfStuff;
        if (notFull) {
            stuff[amountOfStuff] = new Potion(level);
            amountOfStuff++;
        }
        return notFull;
    }

    public boolean put(Potion potion) {
        boolean notFull = totalAmount > amountOfStuff;
        if (notFull) {
            stuff[amountOfStuff] = potion;
            amountOfStuff++;
        }
        return notFull;
    }

    public boolean drop(int position) {
        boolean hasItem = false;
        if (position < totalAmount) {
            hasItem = stuff[position] != null;
            if (hasItem) {
                stuff[position] = null;
                amountOfStuff--;
            }
        }
        return hasItem;
    }

    public long sell(int position) {
        long amount = 0L;
        if (position < totalAmount) {
            if (stuff[position] != null) {
                amount = stuff[position].getCost();
                stuff[position] = null;
                amountOfStuff--;
            }
        }
        sortStuff();
        return amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getAmountOfStuff() {
        return amountOfStuff;
    }

    public boolean hasItem(int position) {
        boolean answer = false;
        if (position < totalAmount) {
            answer = stuff[position] != null;
        }
        return answer;
    }

    public Potion getItem(int position) {
        if (hasItem(position)) {
            Potion potion = stuff[position];
            stuff[position] = null;
            sortStuff();
            return potion;
        } else {
            return null;
        }
    }

    public Potion showItem(int position) {
        if (hasItem(position)) {
            return stuff[position];
        } else {
            return null;
        }
    }

    public boolean notFull() {
        return totalAmount > amountOfStuff;
    }

    public void sortStuff() {
        int[] order = new int[totalAmount];
        for (int i = 0; i < totalAmount; i++) {
            order[i] = -1;
        }

        int pos = 0;
        long healthOrder = getMaxHealth();
        if (healthOrder < 0) {
            return;
        }
        for (int i = 0; i < totalAmount; i++) {
            if (stuff[i] != null) {
                if (stuff[i].getHealth() == healthOrder) {
                    order[pos] = i;
                    pos++;
                }
            }
        }

        for (int i = 0; i < totalAmount; i++) {
            long secondMax = getNextMax(healthOrder);
            if (secondMax < 0) {
                break;
            }
            for (int j = 0; j < totalAmount; j++) {
                if (stuff[j] != null) {
                    if (stuff[j].getHealth() == secondMax) {
                        order[pos] = j;
                        pos++;
                    }
                }
            }
            healthOrder = secondMax;
        }
        Backpack bp = new Backpack(totalAmount);
        for (int i = 0; i < pos; i++) {
            bp.put(stuff[order[i]]);
        }

        for (int i = 0; i < totalAmount; i++) {
            if (stuff[i] != null) {
                stuff[i] = null;
            }
        }

        amountOfStuff = 0;
        for (int i = 0; i < pos; i++) {
            stuff[i] = bp.stuff[i];
            amountOfStuff++;
        }
    }

    private long getMaxHealth() {
        long max = -1;
        for (int i = 0; i < totalAmount; i++) {
            if (stuff[i] != null) {
                if (stuff[i].getHealth() > max) {
                    max = stuff[i].getHealth();
                }
            }
        }
        return max;
    }

    private long getNextMax(long moreMax) {
        long secondMax = -1;
        for (int i = 0; i < totalAmount; i++) {
            if (stuff[i] != null) {
                if (stuff[i].getHealth() < moreMax && stuff[i].getHealth() > secondMax) {
                    secondMax = stuff[i].getHealth();
                }
            }
        }
        return secondMax;
    }

    public void showStuff() {
        for (int i = 0; i < totalAmount; i++) {
            if (stuff[i] == null) {
                System.out.printf("%d: empty%n", i + 1);
            } else {
                System.out.printf("%d: %d HP., %d gold%n", i + 1, stuff[i].getHealth(), stuff[i].getCost());
            }
        }
    }
}
