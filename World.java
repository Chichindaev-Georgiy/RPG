import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class World {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
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
            });
            case "3" -> System.exit(0);
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
        System.out.println("3. Exit game.");
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
        str = str.toLowerCase();
        switch (str) {
            case "y":
                Battle.letsFight(player, (new ComeBack() {
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
        default void win(Monster monster) {
            int levelDifference = monster.level - player.level;
            long xpGain = (long) (Math.pow(player.getExperienceGain(), levelDifference) * monster.experience);
            player.gainXp(xpGain);
            System.out.printf("Enemy destroyed! You've got %d experience and %d gold.", xpGain, monster.money);
            player.trade(-monster.money);
            if (player.getExperienceLevel() <= player.experience) {
                player.levelUp();
            }
            forestMenu();
        }

        default void lost() {
            System.exit(1);
        }
    }


    public static void main(String[] args) {
        System.out.println("Enter your name good sir:");
        try {
            mainMenu(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
