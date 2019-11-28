import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.function.Function;

public class Command {
    private String name;
    private String help;
    private Function<String[], String> action;

    public Command(String name, String help, Function<String[], String> action) {
        this.name = name;
        this.help = help;
        this.action = action;
    }

    public String execute(String[] args) {
        return action.apply(args);
    }

    public String help() {
        return help;
    }

    public String getName() {
        return name;
    }
}
