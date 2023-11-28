package com.example.demo.bot;

import com.example.demo.configuration.BotConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class MonkeyServiceBot extends TelegramLongPollingBot {
    public static MonkeyServiceBot INSTANCE;

    //private static final Logger LOG = LoggerFactory.getLogger(MonkeyServiceBot.class);
    //private static final String START = "/start";
    //private static final String HELP = "/help";
    //private static final String BANAN = "/banan";

    final BotConfig config;

    public MonkeyServiceBot(BotConfig config) {
        this.config = config;
        if (this.INSTANCE != null) {
            throw new RuntimeException("Бот уже создан!");
        }
        INSTANCE = this;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message mess = update.getMessage();
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();

            switch (message) {
                case "/start" -> {
                    String userName = update.getMessage().getChat().getUserName();
                    startCommand(chatID, userName);
                }
                case "/banan" -> {
                    String userName = update.getMessage().getChat().getUserName();
                    bananCommand(chatID, userName);
                }
                case "/help" -> {
                    String userName = update.getMessage().getChat().getUserName();
                    helpCommand(chatID, userName);
                }



                //LOG.info("Received from user " + message);
            }
        }

    }

    private void startCommand(Long chatId, String userName) {
        var text = """
                Приветствую, меня зовут Леон.
                Я обезъянбот и сегодня я обслуживаю вас.
                
                ID `вашего` telegram-профиля:  """ +
                + chatId +
                """
                
                Вы можете воспользоваться следующими командами:
                /start
                /banan
                
                Дополнительные команды:
                /help - получение справки
                
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, text);
    }

    private void bananCommand(Long chatId, String userName) {
        var text = """
                У вас есть банан?
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
        //System.out.println(chatId);
    }




    private void helpCommand(Long chatId, String userName) {
        var text = """
                Этот функционал еще не готов, я сейчас над ним работаю
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }

    public void accessCommand(Long chatId, String text) {
        sendMessage(1978715207L,"Пользователь запрашивает у вас доступ");
    }

    private void sendMessage(Long chatId, String text) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка отправки сообщения: " + e);
            //LOG.error("Ошибка отправки сообщения", e);
        }

    }
}
