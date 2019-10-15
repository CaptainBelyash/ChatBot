import java.io.*; //Не делать так Google Java StyleGuide
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleBot {
    public static HashMap<String, Command> commandsList = new HashMap<>();

    private static void write(String output) {
        System.out.println(output);
    }

    private static void FillCommands() {
        var newCommand = new Command("error", "print error message", ConsoleBot::error);
        commandsList.put(newCommand.getName(), newCommand);
        newCommand = new Command("time", "print current time", ConsoleBot::time_command);
        commandsList.put(newCommand.getName(), newCommand);
        newCommand = new Command("greetings", "print greetings", ConsoleBot::greetings);
        commandsList.put(newCommand.getName(), newCommand);
        newCommand = new Command("help", "print help. you want something else?", ConsoleBot::help_command);
        commandsList.put(newCommand.getName(), newCommand);
        newCommand = new Command("echo", "print echo", ConsoleBot::echo_command);
        commandsList.put(newCommand.getName(), newCommand);
    }

    public static void main(String[] args) throws IOException {
        FillCommands();
        greetings(new String[0]);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            var input = reader.readLine().split(" ");
            if (input.length < 1) {
                write( commandsList.get("error").execute(new String[0]));
                continue;
            }
            var userCommand = input[0];
            if (!commandsList.containsKey(userCommand)) {
                write(commandsList.get("error").execute(new String[0]));
                continue;
            }
            var commandArgs = new String[0];
            if (input.length > 1) {
                commandArgs = Arrays.copyOfRange(input, 1, input.length);
            }
            write(commandsList.get(userCommand).execute(commandArgs));
        }
    }

    private static String error(String[] args) {
        return "Wrong command or input data";
    }

    private static String time_command(String[] args) {
        var datetime = new Date();
        return"Текущее время: " + datetime.toString();
    }

    private static String greetings(String[] args) {
        write("Привет!");
        write("Сегодня ты шёл по улице и увидел коробку с маленьким котёнком внутри.");
        write("Конечно, ты не смог пройти мимо столь милого создания и забрал его с собой!");
        return "";
    }

    private static String help_command(String[] args) {
        write("Чем я могу тебе помочь?");
        return "";
    }

    private static String echo_command(String[] args) {
        var stringBuilder = new StringBuilder();
        for (var arg: args) {
            stringBuilder.append(arg);
        }
        return stringBuilder.toString();
    }
}