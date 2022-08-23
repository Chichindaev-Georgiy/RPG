import java.util.Random;

public class Merchant {
    private final Backpack store;

    public Merchant(int playerLevel) {
        store = new Backpack(20);
        int amountOfStuff = 5 + playerLevel / 5;
        if (amountOfStuff > store.getTotalAmount()) {
            amountOfStuff = store.getAmountOfStuff();
        }
        Random rand = new Random();
        for (int i = 0; i < amountOfStuff; i++) {
            int difference = rand.nextInt(0, 101);
            int level;
            if (difference < 20) {
                if (playerLevel == 1) {
                    level = 1;
                } else {
                    level = playerLevel - 1;
                }
                store.put(level);
            } else if (difference < 50) {
                store.put(playerLevel);
            } else if (difference < 75) {
                store.put(playerLevel + 1);
            } else {
                store.put(playerLevel + 2);
            }
        }
        store.sortStuff();
    }

    public void sellItemToPlayer(Player player, int position) {
        if (player.getBackpack().notFull()) {
            Potion potion = store.getItem(position);
            if (potion == null) {
                System.out.println("This place is empty.");
            } else {
                if (player.getMoneyAmount() < potion.getCost()) {
                    System.out.println("It is to much cost for you!");
                    store.put(potion);
                    store.sortStuff();
                } else {
                    player.trade(potion.getCost());
                    player.getBackpack().put(potion);
                    player.getBackpack().sortStuff();
                    System.out.printf("Sold! Amount of gold remaining: %d.%n", player.getMoneyAmount());
                }
            }
        } else {
            System.out.println("Sell me your stuff first!");
        }
    }

    public void buyPlayersItem(Player player, int position) {
        if (player.getBackpack().hasItem(position)) {
            Potion potion = player.getBackpack().getItem(position);
            player.getBackpack().drop(position);
            player.trade(-potion.getCost());
            store.put(potion);
            store.sortStuff();
            System.out.printf("Ok.%nYou've got %d gold.%n", player.getMoneyAmount());
        } else {
            System.out.println("You have nothing there!");
        }
    }

    public void showStore() {
        System.out.println("I've got some potions for you!");
        for (int i = 1; i < 11; i++) {
            Potion item1 = store.showItem(i - 1);
            Potion item2 = store.showItem(i + 9);
            String string1;
            String string2;
            if (item1 == null) {
                string1 = String.format("%d: empty", i);
            } else {
                string1 = String.format("%d: %d HP., %d gold", i, item1.getHealth(), item1.getCost());
            }
            if (item2 == null) {
                string2 = String.format("%d: empty%n", i + 10);
            } else {
                string2 = String.format("%d: %d HP., %d gold%n", i + 10, item2.getHealth(), item2.getCost());
            }
            System.out.printf("%s", string1);
            for (int j = string1.length(); j < 50; j++) {
                System.out.print(" ");
            }
            System.out.printf("%s", string2);
        }
    }

    public Backpack getStore() {
        return store;
    }
}
