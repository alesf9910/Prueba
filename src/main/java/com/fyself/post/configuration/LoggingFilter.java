package com.fyself.post.configuration;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LoggingFilter extends Filter<ILoggingEvent> {

    public static final String _LOG = "EVENT-LOG";

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().equalsIgnoreCase(_LOG))
            return FilterReply.ACCEPT;
        else
            return FilterReply.DENY;
    }


}
