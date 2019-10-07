import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

public class ConsoleBot {
    private static HashMap<String, Command> commandsList = new HashMap<>();
    private static Pet pet;

    private static void write(String output) {
        System.out.println(output);
    }

    private static void createCommand(String name, String help, Function<String[], String> action){
        var newCommand = new Command(name, help, action);
        commandsList.put(newCommand.getName(), newCommand);
    }

    private static void fillCommands() {
        createCommand("help", "help: Выводит список доступных комманд.", ConsoleBot::helpCommand);
        createCommand("create", "create [name]: Создаёт нового питомца с именем name.",
                ConsoleBot::createCommand);
        createCommand("feed", "feed: Покормить питомца. Прибавляет 1 к сытости.",
                ConsoleBot::feedCommand);
        createCommand("play", "play: Поиграть с питомцем. Прибавляет 1 к счастью и отнимает 1 от бодрости.",
                ConsoleBot::playCommand);
        createCommand("sleep",
                "sleep [hours]: Отправить питомца спать на hours часов. Прибавляет hours к бодрости.",
                ConsoleBot::sleepCommand);
        createCommand("chars", "chars: Получить характеристики питомца",
                ConsoleBot::getCharacteristicsCommand);
    }

    public static void main(String[] args) throws IOException {
        fillCommands();
        write(greetings());
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            var input = reader.readLine().split(" ");
            if (input.length < 1) {
                write(error());
                continue;
            }
            var userCommand = input[0];
            if (!commandsList.containsKey(userCommand)) {
                write(error());
                continue;
            }
            var commandArgs = new String[0];
            if (input.length > 1) {
                commandArgs = Arrays.copyOfRange(input, 1, input.length);
            }
            try{
                write(commandsList.get(userCommand).execute(commandArgs));
            }
            catch (Exception e){
                write(error());
            }
        }
    }

    private static String error() {
        return "Wrong command or input data";
    }

    private static String greetings() throws IOException {
        String hello = new String(Files.readAllBytes(Paths.get("C:\\Users\\Ирина Заец\\Documents\\GitHub\\ChatBot\\src\\Greetings.txt"))); //TODO: Исправить
        return hello + commandsList.get("help").execute(new String[0]);
    }

    private static String helpCommand(String[] args) {
        StringBuilder help = new StringBuilder("\nКомманды, которые ты можешь использовать:");
        for (String command: commandsList.keySet()){
            help.append("\n");
            help.append(commandsList.get(command).help());
        }
        return help.toString();
    }

    private static String createCommand(String[] args){
        var name = args[0];
        pet = new Pet(name);
        //TODO: запись характеристик в json-файл
        return pet.getCharacteristics();
    }

    private static String feedCommand(String[] args){
        pet.feed();
        return "Очень вкусно! +1 к сытости";
    }

    private static String playCommand(String[] args){
        pet.play();
        return "Как весело! +1 к счастью";
    }

    private static String sleepCommand(String[] args){
        int hours = Integer.parseInt(args[0]);
        pet.sleep(hours);
        return "+" + Integer.toString(hours) + " к бодрости";
    }

    private static String getCharacteristicsCommand(String[] args){
        return pet.getCharacteristics();
    }
}