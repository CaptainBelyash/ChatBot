import java.io.*; //Не делать так Google Java StyleGuide
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

public class ConsoleBot {
    private static HashSet<String> commands = new HashSet<>();
    public static Map<String, Method> CommandsList;

    private static void FillCommands() {
        //var commandArray = new String[]{"exit", "help", "echo", "time"}; //Словарь с функциями
        //commands.addAll(Arrays.asList(commandArray));
        //CommandsList = new HashMap<String, Method>();
        //CommandsList.put("help", Commands.Help); //Не знаю как передать метод. Класс тоже не принимает
    }

    public static void main(String[] args) throws IOException {
        FillCommands();
        greetings();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        loop1:
        while (true) {
            String[] input = reader.readLine().split(" ");
            if (input.length < 1) {
                error();
                continue;
            }
            var command = input[0];
            if (!commands.contains(command))
                error();

            switch (command) {
                case "help": {
                    help_command();
                    break;
                }
                case "echo": {
                    if (input.length < 2) {
                        error();
                        continue;
                    }
                    echo_command(input[1]);
                    break;
                }
                case "time": {
                    time_command();
                    break;
                }
                case "exit": {
                    System.out.println("Удачного дня!");
                    break loop1;
                }
            }
        }
    }

    private static void error() {
        System.out.println("Wrong command or input data");
    }

    private static void time_command() {
        var datetime = new Date();
        System.out.println("Текущее время: " + datetime.toString());
    }

    private static void greetings() {
        System.out.println("Привет!");
        System.out.println("Сегодня ты шёл по улице и увидел коробку с маленьким котёнком внутри.");
        System.out.println("Конечно, ты не смог пройти мимо столь милого создания и забрал его с собой!");
    }

    private static void help_command() throws IOException {
        System.out.println("Чем я могу тебе помочь?");
    }

    private static void echo_command(String inputString) {
        System.out.println(inputString);
    }
}