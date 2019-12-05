public class MoveNotificationThread extends Thread {
    private BotLogic bot;
    MoveNotificationThread(BotLogic botLogic){
        this.bot = botLogic;
    }
    @Override
    public void run() {
        while (true) {
            bot.movePetNotify();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
