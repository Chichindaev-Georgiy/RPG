import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class World {
    private static BufferedReader bufferedReader;
    //private static Scanner scanner = new Scanner(System.in);
    private static Player player = null;
    private static Merchant merchant = null;

    private static void mainMenu(String string) {
        if (player == null) {
            player = new Player(string);

            System.out.printf("Hello good sir %s!%n", player.getName());
            menu();
        }
        switch (string) {
            case "1" -> {
                merchant = new Merchant(player.level);
                merchantSwitch();
            }
            case "2" -> Battle.letsFight(player, new ComeBack() {
                @Override
                public void win(Monster monster) {
                    int levelDifference = monster.level - player.level;
                    long xpGain = (long) (Math.pow(player.getExperienceGain(), levelDifference) * monster.experience);
                    player.gainXp(xpGain);
                    System.out.printf("Enemy destroyed! You've got %d experience and %d gold.", xpGain, monster.money);
                    player.trade(-monster.money);
                    if (player.getExperienceLevel() <= player.experience) {
                        player.levelUp();
                    }
                    //forestMenu();
                }

                @Override
                public void lost() {
                    System.exit(1);
                }
            });
            case "3" -> healYourself();
            case "4" -> System.exit(0);
        }
        try {
            mainMenu(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void merchantMenu() {
        System.out.println("Welcome to my store! What do you want to do?");
        System.out.printf("You've got %d gold%n", player.getMoneyAmount());
        System.out.println("1. Buy potion.");
        System.out.println("2. Sell stuff.");
        System.out.println("3. Exit.");
    }

    private static void merchantSwitch() {
        merchant.getStore().sortStuff();
        merchantMenu();
        int result = 0;
        try {
            result = Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (result) {
            case 1:
                System.out.println("What do you want to buy?");
                merchant.showStore();
                try {
                    buyStuff(Integer.parseInt(bufferedReader.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                sellStuff();
                break;
            default:
                menu();
                try {
                    mainMenu(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private static void buyStuff(int position) {
        merchant.sellItemToPlayer(player, position - 1);
        merchantSwitch();
    }

    private static void healYourself() {
        System.out.printf("%s's backpack:%n", player.getName());
        if (player.getBackpack().getAmountOfStuff() > 0)
        {
        player.getBackpack().showStuff();
        System.out.println("What potion would you use, sir?");

            boolean isNotCorrect = true;
            int posItem = 0;
            Potion healPotion;
            while (isNotCorrect && player.getBackpack().getAmountOfStuff() > 0) {
                try {
                    isNotCorrect = !player.getBackpack().hasItem(posItem = Integer.parseInt(bufferedReader.readLine()) - 1);
                    System.out.println(isNotCorrect);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            healPotion = player.getBackpack().getItem(posItem);
            healPotion.heal(player);
            if (healPotion.getHealth() > 0) {
                player.getBackpack().put(healPotion);
            }
        } else {
            System.out.println("Backpack is empty.");
        }
        menu();
        try {
            mainMenu(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sellStuff() {
        System.out.println("Your stuff:");
        player.getBackpack().showStuff();
        System.out.println("What are you selling?");
        int choice = 1;
        try {
            choice = Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        merchant.buyPlayersItem(player, choice - 1);
        merchantSwitch();
    }

    private static void menu() {
        System.out.println("Where are we going?");
        System.out.println("1. To merchant.");
        System.out.println("2. To Dark forest.");
        System.out.println("3. Heal yourself.");
        System.out.println("4. Exit game.");
    }

    private static void forestMenu() {
        System.out.println("Do you want to continue? (y/n)");
        try {
            forestSwitcher(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void forestSwitcher(String str) {

        switch (str) {
            case "y":
                Battle.letsFight(player, (new ComeBack() {
                    @Override
                    public void win(Monster monster) {
                        int levelDifference = monster.level - player.level;
                        long xpGain = (long) (Math.pow(player.getExperienceGain(), levelDifference) * monster.experience);
                        player.gainXp(xpGain);
                        System.out.printf("Enemy destroyed! You've got %d experience and %d gold.", xpGain, monster.money);
                        player.trade(-monster.money);
                        if (player.getExperienceLevel() <= player.experience) {
                            player.levelUp();
                        }
                        //forestMenu();
                    }

                    @Override
                    public void lost() {
                        System.exit(1);
                    }
                }));
                break;
            case "n":
                menu();
                try {
                    mainMenu(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    forestSwitcher(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    interface ComeBack {
        void win(Monster monster);

        void lost();
    }


    public static void main(String[] args) {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your name good sir:");
        try {
            mainMenu(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
