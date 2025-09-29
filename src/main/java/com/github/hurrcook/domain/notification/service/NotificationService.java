package com.github.hurrcook.domain.notification.service;

import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.domain.ingredient.repository.IngredientRepository;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void sendNotification() {

        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        LocalDateTime threeDaysLater = LocalDateTime.now().plusDays(2);

        List<Ingredient> expiringIngredients = ingredientRepository
                .findByExpireDateBetween(tomorrow.toLocalDate().atStartOfDay(),
                        threeDaysLater.toLocalDate().atStartOfDay());

        // 사용자별로 그룹화
        Map<User, List<Ingredient>> ingredientsByUser = expiringIngredients.stream()
                .collect(Collectors.groupingBy(Ingredient::getUser));

        for (Map.Entry<User, List<Ingredient>> entry : ingredientsByUser.entrySet()) {
            User user = entry.getKey();
            List<Ingredient> ingredients = entry.getValue();

            if (user.getFcmToken() == null || user.getFcmToken().isEmpty()) {
                continue;
            }

            String body = generateExpirationBody(ingredients);

            Message message = Message.builder()
                    .setToken(user.getFcmToken())
                    .setNotification(Notification.builder()
                            .setTitle("유통기한이 내일까지예요!")
                            .setBody(body)
                            .build())
                    .putData("type", "EXPIRATION")
                    .putData("count", String.valueOf(ingredients.size()))
                    .build();

            try {
                FirebaseMessaging.getInstance().send(message);

            } catch (FirebaseMessagingException e) {

                if (isInvalidToken(e)) {
                    removeInvalidToken(user);
                }
            }
        }


    }

    private String generateExpirationBody(List<Ingredient> ingredients) {
        if (ingredients.size() == 1) {
            Ingredient ingredient = ingredients.getFirst();
            return String.format("%d%s 남았어요. 빨리 사용해주세요!",
                    ingredient.getAmount(), ingredient.getUnit());
        } else {
            String ingredientNames = ingredients.stream()
                    .limit(3)
                    .map(Ingredient::getName)
                    .collect(Collectors.joining(", "));

            if (ingredients.size() > 3) {
                ingredientNames += " 외 " + (ingredients.size() - 3) + "개";
            }

            return ingredientNames + "의 유통기한이 내일까지예요!";
        }
    }

    private boolean isInvalidToken(FirebaseMessagingException e) {
        String errorCode = e.getMessagingErrorCode().toString();
        return errorCode.equals("INVALID_ARGUMENT") ||
                errorCode.equals("UNREGISTERED");
    }

    // 무효한 토큰 삭제
    private void removeInvalidToken(User user) {
        user.setFcmToken(null);
        userRepository.save(user);
    }
}
