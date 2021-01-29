package com.fyself.post.service.stream.contract;

import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerAsk;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerChoice;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerHierarchy;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerRate;
import com.fyself.post.service.stream.contract.to.MessageTO;
import com.fyself.post.service.stream.contract.to.PayloadTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.fyself.post.tools.Mapper.toMap;
import static java.util.stream.Collectors.toList;

/**
 * Temporal binder for the kafka service operations.
 *
 * @author jmmarin
 * @since 0.1.0
 */
@SuppressWarnings("unchecked")
@Mapper
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
        response.put("tag", Arrays.asList(answer.getAnswer().split(" ")).stream().filter(s -> s.length()>3).collect(toList()));
    }

    default Map bindPostNotif(String user, String post, String from) {


        MessageTO message = new MessageTO();
        message.setType("POST");
        message.setTo(user);
        message.setFrom(from);

        PayloadTO payload = new PayloadTO();
        payload.setBody(Map.of("post", post));
        message.setPayload(payload);

        message.setTodb(true);
        message.setFix("pu-" + user + "-" + from + "-" + post);


        return toMap(message);
    }

    default Map bindPostWSNotif(String user, String post, String from, String enterprise) {


        MessageTO message = new MessageTO();
        message.setType("POST");
        message.setTo(user);
        message.setFrom(from);

        PayloadTO payload = new PayloadTO();
        payload.setBody(Map.of("post", post, "workspace", true ,"enterprise" ,enterprise));
        message.setPayload(payload);

        message.setTodb(true);
        message.setFix("pu-" + user + "-" + from + "-" + post);


        return toMap(message);
    }

    default Map bindPostCommentNotif(String user, String post,String comment, String from) {


        MessageTO message = new MessageTO();
        message.setType("COMMENT");
        message.setTo(user);
        message.setFrom(from);

        PayloadTO payload = new PayloadTO();
        //payload.setBody(Map.of("post", from));
        payload.setBody(Map.of("post", post, "comment", comment));
        message.setPayload(payload);

        message.setTodb(true);
        message.setFix("co-" + user + "-" + from + "-" + post + "-" + comment);


        return toMap(message);
    }

    default Map bindPostReactionNotif(String user, String post,String reaction, String type_reaction, String from) {


        MessageTO message = new MessageTO();
        message.setType("REACTION");
        message.setTo(user);
        message.setFrom(from);

        PayloadTO payload = new PayloadTO();
        //payload.setBody(Map.of("post", from));
        payload.setBody(Map.of("post", post, "reaction", reaction, "type_reaction", type_reaction));
        message.setPayload(payload);

        message.setTodb(true);
        message.setFix("re-" + user + "-" + from + "-" + post + "-" + reaction);


        return toMap(message);
    }

    default Map bindPostWSCommentNotif(String user, String post,String comment, String from, String enterprise) {


        MessageTO message = new MessageTO();
        message.setType("COMMENT");
        message.setTo(user);
        message.setFrom(from);

        PayloadTO payload = new PayloadTO();
        //payload.setBody(Map.of("post", from));
        payload.setBody(Map.of("post", post, "comment", comment, "workspace", true, "enterprise", enterprise));
        message.setPayload(payload);

        message.setTodb(true);
        message.setFix("co-" + user + "-" + from + "-" + post + "-" + comment);


        return toMap(message);
    }

    default Map bindPostWSReactionNotif(String user, String post,String reaction, String type_reaction, String from, String enterprise) {


        MessageTO message = new MessageTO();
        message.setType("REACTION");
        message.setTo(user);
        message.setFrom(from);

        PayloadTO payload = new PayloadTO();
        //payload.setBody(Map.of("post", from));
        payload.setBody(Map.of("post", post, "reaction", reaction, "type_reaction", type_reaction, "workspace", true, "enterprise", enterprise));
        message.setPayload(payload);

        message.setTodb(true);
        message.setFix("re-" + user + "-" + from + "-" + post + "-" + reaction);


        return toMap(message);
    }

}
