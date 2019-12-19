import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class BotLogic {
    private static HashMap<String, Command> commandsList = new HashMap<>();
    private static ConcurrentHashMap<String, ArrayDeque<String>> notifyQueue = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
    private static PetLife petLife;
    private static String helpOutput = "";
    private static FoodShop foodShop;

    public BotLogic() {
        foodShop = new FoodShop();
        fillCommands();
        var notifyMoveThread = new MoveNotificationThread(this);
        notifyMoveThread.start();
    }

    private static void makeCommand(String name, String help, Function<String[], String> action) {
        var newCommand = new Command(name, help, action);
        commandsList.put(newCommand.getName(), newCommand);
    }

    private static void fillCommands() {
        makeCommand("help", "help: Выводит список доступных комманд.", BotLogic::helpCommand);
        makeCommand("create", "create [name]: Создаёт нового питомца с именем name.",
                BotLogic::createCommand);
        //makeCommand("avatars","avatars: Выводит доступные аватарки для вашего питомца", );
        makeCommand("feed", "feed [food]: Покормить питомца едой food. Увеличивает сытость. Кормить питомца можно только едой из холодильника.",
                BotLogic::feedCommand);
        makeCommand("play", "play [game]: Поиграть с питомцем в игру game. Прибавляет 1 к счастью и отнимает 1 от бодрости. Добавляет деньги.",
                BotLogic::playCommand);
        makeCommand("sleep",
                "sleep [hours]: Отправить питомца спать на hours часов. Прибавляет hours к бодрости.",
                BotLogic::sleepCommand);
        makeCommand("chars", "chars: Получить характеристики питомца",
                BotLogic::getCharacteristicsCommand);
        makeCommand("delete", "delete: Удалить питомца",
                BotLogic::deleteCommand);
        makeCommand("buy", "buy [food]: Купить еду food. Купленная еда добавляется в холодильник.",
                BotLogic::buyCommand);
        makeCommand("shop", "shop: Получить ассортимент товаров в магазине.",
                BotLogic::getAssortmentCommand);
        makeCommand("fridge", "fridge: Получить содержимое холодильника.",
                BotLogic::getFridgeAssortmentCommand);
        makeCommand("games", "games: Показать доступные игры.",
                BotLogic::getGamesAssortmentCommand);
        makeCommand("/start", "start",
                BotLogic::greetings);
    }

    public static String commandInput(String playerID, String args) throws IOException {
        var input = args.split(" ");
        var output = "";
        if (players.containsKey(playerID))
            if (players.get(playerID).playerInGame()){
                output = players.get(playerID).play(input);
                queueAdd(playerID, output);
                return output;
            }
        var userCommand = input[0];
        if (!commandsList.containsKey(userCommand)) {
            output = error("No such command");
            queueAdd(playerID, output);
            return output;
        }
        var commandArgs = new String[input.length];
        commandArgs[0] = playerID;
        if (input.length > 1) {
            for (var i = 1; i < input.length; i++){
                commandArgs[i] = input[i];
            }
        }
        try {
            output = commandsList.get(userCommand).execute(commandArgs);
            queueAdd(playerID, output);
            return output;
        } catch (Exception e) {
            output = error("Something broke everything here. Maybe it was a ghost?");
            queueAdd(playerID, output);
            return output;
        }
    }

    private static void queueAdd(String playerID, String value){
        if (!notifyQueue.containsKey(playerID))
            notifyQueue.put(playerID, new ArrayDeque<String>());
        var success = notifyQueue.get(playerID).offer(value);
    }

    public static String error(String e) {
        return "Error: " + e;
    }

    private static String greetings(String[] args) {
        String hello = "Привет!\nВ этом чат-боте ты можешь создать и ухаживать за своим питомцем.";
        return hello + commandsList.get("help").execute(new String[0]);
    }

    private static String helpCommand(String[] args) {
        if (helpOutput.length() == 0) {
            StringBuilder help = new StringBuilder("\nКомманды, которые ты можешь использовать:");
            for (var command : commandsList.keySet()) {
                help.append("\n");
                help.append(commandsList.get(command).help());
            }
            helpOutput = help.toString();
        }
        return helpOutput;
    }

    public synchronized static String createCommand(String[] args) {
        var playerID = args[0];
        if (args.length == 1)
            return error("Не указано имя питомца");
        var name = args[1];
        if (players.containsKey(playerID))
            return error("Pet exist");
        players.put(playerID, new Player(playerID, name));

        petLife = new PetLife(players.get(playerID).getPet());

        Thread myThready = new Thread(petLife);
        myThready.start();

        return players.get(playerID).getPet().getCharacteristics();

    }

    public synchronized static String deleteCommand(String[] args) {
        var playerID = args[0];
        players.remove(playerID);
        return "Питомец удален";
    }

    public synchronized static String buyCommand(String[] args) {
        var playerID = args[0];
        if (args.length == 1)
            return error("Не указана еда.");
        var food = foodShop.buy(args[1]);
        var player = players.get(playerID);
        if (food.getPrice() > player.getMoney())
            return error("Недостаточно денег");
        player.buyFood(food);
        return "Холодильник пополнен!";
    }

    public synchronized static String feedCommand(String[] args) {
        var playerID = args[0];
        if (args.length == 1)
            return error("Не указана еда.");
        var player = players.get(playerID);
        var fridge = player.getFridge();
        if (!fridge.containsFood(args[1]))
            return error("Такого продукта нет в холодильнике");
        return player.feedPet(args[1]);
    }

    public synchronized static String getGamesAssortmentCommand(String[] args) {
        return Games.getGamesAssortment();
    }

    public synchronized static String playCommand(String[] args) {
        var player = players.get(args[0]);
        return player.letsPlay(args[1]);
    }

    private synchronized static String sleepCommand(String[] args) {
        var player = players.get(args[0]);
        if (args.length == 1)
            return error("Не указано количестов часов.");
        int hours = Integer.parseInt(args[1]);
        player.sleepPet(hours);
        return "+" + Integer.toString(hours) + " к бодрости";
    }

    private synchronized static String getCharacteristicsCommand(String[] args) {
        var player = players.get(args[0]);
        var result = player.getPet().getCharacteristics();
        result += "Деньги: " + player.getMoney() + "\n";
        return result;
    }

    private synchronized static String getAssortmentCommand(String[] args) {
        return foodShop.showAssortment();
    }

    private synchronized static String getFridgeAssortmentCommand(String[] args) {
        var player = players.get(args[0]);
        return player.getFridge().getAssortment();
    }

    public ConcurrentHashMap<String, Player> getPlayers() {
        return players;
    }

    public void movePetNotify() {
        for (var playerID: players.keySet()){
            var nextNotify = players.get(playerID).getPet().getNotifys().poll();
            while (nextNotify != null){
                queueAdd(playerID, nextNotify);
                nextNotify = players.get(playerID).getPet().getNotifys().poll();
            }
        }
    }

    public ConcurrentHashMap<String, ArrayDeque<String>> getNotifys() {
        return notifyQueue;
    }
}