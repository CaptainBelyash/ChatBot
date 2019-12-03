import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.HashMap;

public class TelegramBot extends TelegramLongPollingBot {
    private BotLogic bot = new BotLogic();
    
    public TelegramBot(){
        var thread = new NotifyThread(this);
        thread.start();
    }

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


    private static class NotifyThread extends Thread{
        private HashMap<String, Pet> pets;
        private TelegramBot tgBot;
        NotifyThread(TelegramBot tgBot){
            this.tgBot = tgBot;
            pets = this.tgBot.bot.getPets();
        }

        @Override
        public void run(){
            for (String playerID:
                 pets.keySet()) {
                try {
                    tgBot.sendMsg(playerID, pets.get(playerID).getCharacteristics());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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