package com.speedment.runtime.dashboard.endpoint;

import com.speedment.common.logger.LoggerEventListener;
import com.speedment.common.logger.LoggerFactory;
import com.speedment.common.logger.LoggerManager;
import com.speedment.common.restservice.Request;
import com.speedment.common.restservice.Response;
import com.speedment.common.restservice.StreamingResponse;

import java.io.IOException;

import static com.speedment.common.restservice.ContentType.TEXT_EVENT_STREAM;

/**
 * @author Emil Forslund
 * @since  3.1.1
 */
public final class LoggerEndpoint extends AbstractEndpoint  {

    @Override
    public String path() {
        return "/logs";
    }

    @Override
    public Response serve(Request request) {
        final LoggerFactory logger = LoggerManager.getFactory();

        try {
            final StreamingResponse response = Response.stream(TEXT_EVENT_STREAM);
            final LoggerEventListener listener = ev -> {
                try {
                    response.write(
                        "data: {\"name\":\"" + ev.getName() +
                            "\",\"level\":\"" + ev.getLevel().toText() +
                            "\",\"msg\":\"" + ev.getMessage() + "\"} \n\n");
                } catch (final IOException ex) {
                    // Swallow it so that the Speedment instance isn't affected
                    // if a client simply shutdown the connection unexpectedly.
                    response.stop();
                }
            };

            response.setOnStop(() -> logger.removeListener(listener));
            logger.addListener(listener);

            return response;
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}