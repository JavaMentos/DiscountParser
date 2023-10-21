package ru.home.discountparser.telegram.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.home.discountparser.parser.pepper.PepperParserPost;
import ru.home.discountparser.parser.pepper.dto.Pepper;
import ru.home.discountparser.telegram.service.MessageSender;
import ru.home.discountparser.telegram.service.PepperService;

import java.time.LocalDate;

import static ru.home.discountparser.parser.pepper.PepperListContainer.*;

@Service
@Log4j2
@AllArgsConstructor
public class PepperServiceImpl implements PepperService {

    private final MessageSender telegramMessageSender;

    private final PepperParserPost pepperParserPost;

    @Override
    public void checkNewPosts() {
        pepperParserPost.checkNewPosts();
        sendMessagesForNewPepperPosts();
        getAllPepperPosts().forEach(pepperPost -> pepperPost.setNew(false));
    }

    @Override
    public void removeYesterdayPosts() {
        if (!isPepperPostListEmpty()) {
            log.info("Стартовал планировщик и очистил список постов, current "
                    + getPepperPostListSize());
            LocalDate yesterday = LocalDate.now().minusDays(1);
            getAllPepperPosts().removeIf(pepperPost -> pepperPost.getDate().equals(yesterday));
        }
    }

    /**
     * Отправляет сообщения через сервис Telegram о новых публикациях на Pepper.
     */
    private void sendMessagesForNewPepperPosts() {
        getAllPepperPosts().stream()
                .filter(Pepper::isNew)
                .forEach(pepperPost -> telegramMessageSender.prepareMessageWithTextAndImageUrl(
                        pepperParserPost.formatPepperPostMessage(pepperPost), pepperPost.getImageUrl())
                );
    }
}
