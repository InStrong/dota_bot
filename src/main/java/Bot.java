import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Bot extends TelegramLongPollingBot {

    private SQLHandler sqlHandler = new SQLHandler();
    public String getBotToken() {
        return "";
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
                    sendPhotoToUser(message.getChatId(), sqlHandler.getImage(message.getText()));
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
    private void sendPhotoToUser(long chatId, String url_string) {

        try {

            SendPhoto sendPhoto = new SendPhoto().setChatId(chatId).setPhoto(url_string);
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }





    public String getBotUsername() {
        return "Dota2";
    }



}
