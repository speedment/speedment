package com.speedment.runtime.dashboard.endpoint;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.restservice.Request;
import com.speedment.common.restservice.Response;
import com.speedment.runtime.core.component.InfoComponent;

import static com.speedment.common.restservice.ContentType.APPLICATION_JSON;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public class InfoEndpoint extends AbstractEndpoint {

    private @Inject InfoComponent info;

    @Override
    public String path() {
        return "/info";
    }

    @Override
    public Response serve(Request request) {
        return Response.ok(APPLICATION_JSON,
            "{\"title\":\"" + info.getTitle() + "\"," +
            "\"subtitle\":\"" + info.getSubtitle() + "\"," +
            "\"vendor\":\"" + info.getVendor() + "\"," +
            "\"licenseName\":\"" + info.getLicenseName() + "\"," +
            "\"implementationVersion\":\"" + info.getImplementationVersion() + "\"," +
            "\"specificationVersion\":\"" + info.getSpecificationVersion() + "\"," +
            "\"specificationNickname\":\"" + info.getSpecificationNickname() + "\"," +
            "\"repository\":\"" + info.getRepository() + "\"," +
            "\"production\":" + info.isProductionMode() + "}");
    }
}
