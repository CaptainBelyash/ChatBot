import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayDeque;
import java.util.HashMap;

public class TgNotifyThread extends Thread{
    private HashMap<String, ArrayDeque<String>> notifys;
    private TelegramBot tgBot;
    TgNotifyThread(TelegramBot tgBot){
        this.tgBot = tgBot;
        notifys = this.tgBot.bot.getNotifys();
    }

    @Override
    public void run(){
        while (true){
            for (String playerID:
                    notifys.keySet()) {
                try {
                    var nextNotify = notifys.get(playerID).poll();
                    while (nextNotify != null){
                        tgBot.sendMsg(playerID, nextNotify);
                        nextNotify = notifys.get(playerID).poll();
                    }
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
}