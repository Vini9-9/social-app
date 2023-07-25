package viniprogramando.socialapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {


    public NotFoundException(String reason) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity("Not found " + reason)
                .build());
    }

}
