package ru.home.discountparser.service;

import ru.home.discountparser.parser.pepper.dto.Pepper;

import java.util.List;
import java.util.Optional;

public interface PepperService {
    void addPost(Pepper pepper);

    List<Pepper> getAllPosts();

    boolean existsById(String id);

    /**
     * Отправляет сообщения через сервис Telegram о новых публикациях на Pepper.
     */
    void sendMessagesForNewPosts(Pepper pepper);

    Optional<Pepper> getPost(String id);

    void deleteById(String id);

    void deleteAll();
}
