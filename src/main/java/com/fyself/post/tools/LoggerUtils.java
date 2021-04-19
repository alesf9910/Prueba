package com.fyself.post.tools;

import com.fyself.post.configuration.LoggingFilter;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.seedwork.kafka.logback.message.AgentInfo;
import com.fyself.seedwork.kafka.logback.message.BusinessInfo;
import com.fyself.seedwork.kafka.logback.message.Logger;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.fyself.seedwork.util.JsonUtil.write;


public class LoggerUtils {
    private static LoggerUtils INSTANCE = null;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggingFilter._LOG);

    private LoggerUtils() {
    }

    public static LoggerUtils getInstance() {
        if (INSTANCE == null)
            createInstance();
        return INSTANCE;
    }

    private static synchronized void createInstance() {
        if (INSTANCE == null)
            INSTANCE = new LoggerUtils();
    }

    public void sendLogs(AgentInfo agentInfo, BusinessInfo bussines) {
        Logger log;
        if (agentInfo==null)
            log = Logger.newInstance().setBusinessInfo(bussines);
        else
            log = Logger.newInstance().setAgentInfo(agentInfo).setBusinessInfo(bussines);
        logger.info(log.serialize());
    }

    static public void createEvent(DomainEntity entity, FySelfContext context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("create"))
                .setResources(Set.of(entity.getId()))
                .setAction("create")
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "");
        bussines = bussines.setDetails(DETAILS(entity));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }

    static public void createEvent(DomainEntity entity, String postOwner, FySelfContext context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("create"))
                .setResources(Set.of(entity.getId()))
                .setAction("create")
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "");
        bussines = bussines.setDetails(detailsComment(entity, postOwner));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }

    static public void createEventWS(DomainEntity entity, FySelfContext context, String enterprise) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("workspace_create"))
                .setResources(Set.of(entity.getId()))
                //.setDetails(Map.of("enterprise",post.getEnterprise()))
                .setAction("create")
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "");
        bussines = bussines.setDetails(detailsWS(entity, enterprise));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }

    static public void createEvent(DomainEntity entity, String context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("create"))
                .setResources(Set.of(entity.getId()))
                .setAction("create")
                .setUser(context);
        bussines = bussines.setDetails(DETAILS(entity));
        LoggerUtils.getInstance().sendLogs(null, bussines);
    }

    private static Map<String, Object> DETAILS(DomainEntity entity) {
        switch (entity.getClass().getSimpleName().toLowerCase()) {
            default:
                return Map.of(
                        "entity", entity.getClass().getSimpleName().toLowerCase(),
                        "value", write(entity)
                );
        }
    }

    private static Map<String, Object> detailsWS(DomainEntity entity, String enterprise) {
        switch (entity.getClass().getSimpleName().toLowerCase()) {
            default:
                return Map.of(
                        "entity", entity.getClass().getSimpleName().toLowerCase(),
                        "value", write(entity),
                        "enterprise", enterprise
                );
        }
    }

    private static Map<String, Object> detailsComment(DomainEntity entity, String postOwner) {
        switch (entity.getClass().getSimpleName().toLowerCase()) {
            default:
                return Map.of(
                        "entity", entity.getClass().getSimpleName().toLowerCase(),
                        "value", write(entity),
                        "postowner", postOwner
                );
        }
    }

    static public void updateEvent(DomainEntity entity, FySelfContext context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("update"))
                .setResources(Set.of(entity.getId()))
                .setAction("update")
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "");
        bussines = bussines.setDetails(DETAILS(entity));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }

    static public void deleteEvent(DomainEntity entity, FySelfContext context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("delete"))
                .setResources(Set.of(entity.getId()))
                .setAction("delete")
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "");
        bussines = bussines.setDetails(DETAILS(entity));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }
}
