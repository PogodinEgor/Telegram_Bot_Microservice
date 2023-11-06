package ru.relex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relex.configuration.RabbitConfiguration;
import ru.relex.service.UpdateProducer;
import ru.relex.utils.MessageUtils;

@Log4j
@RequiredArgsConstructor
@Component
public class UpdateProcessor {

    private TelegramBot telegramBoot;

    private final MessageUtils messageUtils;

    private final UpdateProducer updateProducer;

    private final RabbitConfiguration rabbitConfiguration;

    public void registerBot(TelegramBot telegramBoot) {
        this.telegramBoot = telegramBoot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.hasMessage()) {
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else if (message.hasDocument()) {
            processDocMessage(update);
        } else if (message.hasPhoto()) {
            processPhotoMessage(update);
        } else {
            setUnsuportedMessageTypeView(update);
        }
    }
    private void setUnsuportedMessageTypeView(Update update) {
        SendMessage sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения");
        setView(sendMessage);

    }

    private void setFileIsReceivedViews(Update update) {
        SendMessage senMessage = messageUtils.generateSendMessageWithText(update,
                "Файл получен! Обрабатывается...");
        setView(senMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBoot.sendAnswerMessage(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(rabbitConfiguration.getPhotoMessageUpdateQueue(), update);
        setFileIsReceivedViews(update);
    }


    private void processDocMessage(Update update) {
        updateProducer.produce(rabbitConfiguration.getDocMessageUpdateQueue(), update);
        setFileIsReceivedViews(update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(rabbitConfiguration.getTextMessageUpdateQueue(), update);


    }
}
