import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class TgNotifyThread extends Thread{
    private HashMap<String, Pet> pets;
    private TelegramBot tgBot;
    TgNotifyThread(TelegramBot tgBot){
        this.tgBot = tgBot;
        pets = this.tgBot.bot.getPets();
    }

    @Override
    public void run(){
        while (true){
            for (String playerID:
                    pets.keySet()) {
                try {
                    tgBot.sendMsg(playerID, pets.get(playerID).getCharacteristics());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}