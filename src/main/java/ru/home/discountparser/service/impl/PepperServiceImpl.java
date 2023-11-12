package ru.home.discountparser.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.home.discountparser.parser.pepper.dto.Pepper;
import ru.home.discountparser.repository.PepperRepository;
import ru.home.discountparser.service.MessageSender;
import ru.home.discountparser.service.PepperService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.home.discountparser.utils.FormatPost.formatPepperPostMessage;

@Service
@Log4j2
@AllArgsConstructor
public class PepperServiceImpl implements PepperService {

    private final MessageSender telegramMessageSender;
    private final PepperRepository pepperRepository;
    private final RedisTemplate<String, Pepper> redisTemplate;

    public void addPost(Pepper pepper) {
        pepperRepository.save(pepper);
        redisTemplate.convertAndSend("new_post_channel", pepper.getProductDescription());
    }

    public List<Pepper> getAllPosts() {
        List<Pepper> allPosts = new ArrayList<>();
        pepperRepository.findAll().forEach(allPosts::add);
        return allPosts;
    }

    public boolean existsById(String id) {
        return pepperRepository.existsById(id);
    }

    public Optional<Pepper> getPost(String id) {
        return pepperRepository.findById(id);
    }

    public void deleteById(String id) {
        pepperRepository.deleteById(id);
    }

    public void deleteAll() {
        pepperRepository.deleteAll();
    }

    public void sendMessagesForNewPosts(Pepper pepper) {
        telegramMessageSender.prepareMessageWithTextAndImageUrl(formatPepperPostMessage(pepper), pepper.getImageUrl());
    }
}
