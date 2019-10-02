import java.lang.reflect.Method;
import java.util.HashMap;

public class Command {
    private String name;
    private String help;

    public Command(String inputName, String inputHelp){
        name = inputName;
        help = inputHelp;
    }

    public void Help(){
        System.out.println(help);
    }

    public void GetName(){
        System.out.println(name);
    }
}
