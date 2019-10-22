import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {
    private ConsoleBot bot = new ConsoleBot();

    public synchronized void sendMsg(String chatId, String s) throws TelegramApiException {
        SendMessage sendMess = new SendMessage();
        sendMess.enableMarkdown(true);
        sendMess.setChatId(chatId);
        try {
            sendMess.setText(bot.commandInput(s));
        }
        catch (IOException e){

        }
        execute(sendMess);
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        try {
            sendMsg(update.getMessage().getChatId().toString(), message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "oop_java_tamagochi_bot";
    }

    public String getBotToken() {
        return "914613974:AAHjtINzfERcjnjVgp105RARUyTcgVUcWFE";
    }
}