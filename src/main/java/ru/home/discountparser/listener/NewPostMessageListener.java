package ru.home.discountparser.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import ru.home.discountparser.service.PepperService;

@Component
@RequiredArgsConstructor
public class NewPostMessageListener implements MessageListener {

    private final PepperService pepperService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String postId = message.toString();

        pepperService.getPost(postId)
                .ifPresent(pepperService::sendMessagesForNewPosts);
    }
}