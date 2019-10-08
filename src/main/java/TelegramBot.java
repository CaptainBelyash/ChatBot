import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    public synchronized void sendMsg(String chatId, String s) throws TelegramApiException {
        SendMessage sendMess = new SendMessage();
        sendMess.enableMarkdown(true);
        sendMess.setChatId(chatId);
        sendMess.setText(s);
        execute(sendMess);
    }

    public String getBotUsername() {
        return null;
    }

    public String getBotToken() {
        return null;
    }
}
