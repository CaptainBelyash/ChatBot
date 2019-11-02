import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        System.getProperties().put("proxySet", "true");
//        System.getProperties().put("socksProxyHost", "127.0.0.1");
//        System.getProperties().put("socksProxyPort", "9150");
        ApiContextInitializer.init();
        TelegramBotsApi bot = new TelegramBotsApi();

        try {
            bot.registerBot(new TelegramBot());
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
        System.out.println("ss");
    }
}
