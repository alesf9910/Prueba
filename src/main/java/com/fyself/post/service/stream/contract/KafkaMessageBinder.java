package com.fyself.post.service.stream.contract;

import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
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
        return MAPPER.convertValue(answer, Map.class);
    }

    default Map bindPostNotif(String user, String post) {
        return Map.of("type", "POST", "user", user, "post", post);
    }
}
