package com.fyself.post.tools;

import com.fyself.post.configuration.LoggingFilter;
import com.fyself.seedwork.kafka.logback.message.AgentInfo;
import com.fyself.seedwork.kafka.logback.message.BusinessInfo;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.slf4j.LoggerFactory;
import com.fyself.seedwork.kafka.logback.message.Logger;

import java.util.Map;
import java.util.Set;


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
        Logger log = Logger.newInstance().setAgentInfo(agentInfo).setBusinessInfo(bussines);
        logger.info(log.serialize());
    }


    static public void customEvent(String custom, DomainEntity entity, FySelfContext context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("create"))
                .setResources(Set.of(entity.getId()))
                .setAction(custom)
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "")
                .setDetails(Map.of(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("value"), entity));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }

    static public void createEvent(DomainEntity entity, FySelfContext context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("create"))
                .setResources(Set.of(entity.getId()))
                .setAction("create")
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "")
                .setDetails(Map.of(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("value"), entity));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }

    static public void updateEvent(DomainEntity category, DomainEntity entity, FySelfContext context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("update"))
                .setResources(Set.of(entity.getId()))
                .setAction("update")
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "")
                .setDetails(Map.of(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("last"), category, entity.getClass().getSimpleName().toLowerCase().concat(".").concat("value"), entity));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }

    static public void deleteEvent(DomainEntity entity, FySelfContext context) {
        BusinessInfo bussines = BusinessInfo.newInstance().setResourcetype(entity.getClass().getSimpleName().toLowerCase())
                .setEvent(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("delete"))
                .setResources(Set.of(entity.getId()))
                .setAction("delete")
                .setUser(context.getAccount().isPresent() ? context.getAccount().get().getId() : "")
                .setDetails(Map.of(entity.getClass().getSimpleName().toLowerCase().concat(".").concat("value"), entity));
        LoggerUtils.getInstance().sendLogs(context.getAgentInfo().get(), bussines);
    }
}
