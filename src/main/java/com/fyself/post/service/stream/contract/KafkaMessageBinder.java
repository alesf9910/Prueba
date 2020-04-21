package com.fyself.post.service.stream.contract;

import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerAsk;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerChoice;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerHierarchy;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerRate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static com.fyself.seedwork.util.JsonUtil.MAPPER;

/**
 * Temporal binder for the kafka service operations.
 *
 * @author jmmarin
 * @since 0.1.0
 */
@SuppressWarnings("unchecked")
@Mapper(imports = {SimpleDateFormat.class})
public interface KafkaMessageBinder {
    KafkaMessageBinder KAFKA_MESSAGE_BINDER = Mappers.getMapper(KafkaMessageBinder.class);

    default Map bindAnswer(AnswerSurvey answer) {

        Map<String, Object> response = new HashMap<>();

        response.put("id", answer.getId());
        response.put("post", answer.getPost().getId());
        response.put("type", answer.getAnswer().getType().toString().toLowerCase());

        switch (answer.getAnswer().getType()) {
            case SURVEY_ASK:
                SURVEY_ASK(response, (AnswerAsk) answer.getAnswer());
                break;
            case SURVEY_CHOICE:
                SURVEY_CHOICE(response, (AnswerChoice) answer.getAnswer());
                break;
            case SURVEY_HIERARCHY:
                SURVEY_HIERARCHY(response, (AnswerHierarchy) answer.getAnswer());
                break;
            case SURVEY_RATE:
                SURVEY_RATE(response, (AnswerRate) answer.getAnswer());
                break;
        }

        return response;

    }

    default void SURVEY_HIERARCHY(Map<String, Object> response, AnswerHierarchy answer) {
        answer.getAnswers().forEach((s, integer) -> response.put("option-" + integer, s));
    }

    default void SURVEY_RATE(Map<String, Object> response, AnswerRate answer) {
        response.put("rate", answer.getAnswer());
    }

    default void SURVEY_CHOICE(Map<String, Object> response, AnswerChoice answer) {
        response.put("choice", answer.getAnswers());
    }

    default void SURVEY_ASK(Map<String, Object> response, AnswerAsk answer) {
        response.put("response", answer.getAnswer());
    }

    default Map bindPostNotif(String user, String post) {
        return Map.of("type", "POST", "user", user, "post", post);
    }
}
