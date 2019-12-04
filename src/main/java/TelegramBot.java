import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.HashMap;

public class TelegramBot extends TelegramLongPollingBot {
    public BotLogic bot = new BotLogic(); //ВРеменно паблик
    private TgNotifyThread notifyThread;
    
    public TelegramBot(){
        notifyThread = new TgNotifyThread(this);
        notifyThread.start();
    }

    public synchronized void sendMsg(String chatId, String output) throws TelegramApiException {
        SendMessage sendMess = new SendMessage();
        sendMess.enableMarkdown(true);
        sendMess.setChatId(chatId);
        sendMess.setText(output);

        execute(sendMess);
    }

    public synchronized void giveAnswer(String chatId, String input) throws TelegramApiException{
        try {
            sendMsg(chatId, bot.commandInput(chatId, input));
        } catch (IOException e) {

        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        try {
            giveAnswer(update.getMessage().getChatId().toString(), message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return System.getenv("BOT_NAME");
    }

    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}