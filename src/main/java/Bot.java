import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private SQLHandler sqlHandler = new SQLHandler();
    public String getBotToken() {
        return "826639247:AAGMhc8ItPv0kEWB7R-fA9cbFhXiASjbpTc";
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        long chatId = message.getChatId();
        if (message != null && message.hasText()) {
            sqlHandler.connect();
            if (message.getText().equals("/start")){
                sendMessageToUser(update.getMessage().getChatId(),"Введите имя героя для получения его биографии");
            }
            else {
                if (sqlHandler.isHeroExists(message.getText())) {
                    sendMessageToUser(message.getChatId(), sqlHandler.getImage(message.getText()));
                    sendMessageToUser(message.getChatId(), sqlHandler.getBio(message.getText()));
                }
                else sendMessageToUser(message.getChatId(),"Такого героя нет в игре");
            }
        }
    }

    private void sendMessageToUser(long chatId,String textMessage) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        sendMessage.setText(textMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public String getBotUsername() {
        return "Dota2";
    }



}
