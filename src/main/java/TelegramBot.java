import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {
    private BotLogic bot = new BotLogic();

    public synchronized void sendMsg(String chatId, String input) throws TelegramApiException {
        SendMessage sendMess = new SendMessage();
        sendMess.enableMarkdown(true);
        sendMess.setChatId(chatId);
        try {
            sendMess.setText(bot.commandInput(chatId, input));
        } catch (IOException e) {

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
        return System.getenv("BOT_NAME");
    }

    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}