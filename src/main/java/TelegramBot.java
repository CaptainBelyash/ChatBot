import org.o7planning.googledrive.quickstart.GoogleDrive;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TelegramBot extends TelegramLongPollingBot {
    public BotLogic bot = new BotLogic(); //ВРеменно паблик

    public TelegramBot(){
        TgNotifyThread notifyThread = new TgNotifyThread(this);
        notifyThread.start();
    }

    public synchronized void sendMsg(String chatId, String output) throws TelegramApiException {
        SendMessage sendMess = new SendMessage();
        sendMess.enableMarkdown(true);
        sendMess.setChatId(chatId);
        sendMess.setText(output);

        execute(sendMess);
    }

    public synchronized void userAction(String chatId, String input) throws TelegramApiException{
        try {
            var out = bot.commandInput(chatId, input);
            if(out.equals("выведи изображение")){
                var g = new GoogleDrive();
                SendPhoto message = new SendPhoto().setPhoto(g.getImageByGoogleDisk(g.getDrive(), "всеКоты"));
                execute(message);
            }

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        try {
            userAction(update.getMessage().getChatId().toString(), message);
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