package viniprogramando.socialapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class AlreadyExistsException extends WebApplicationException {

  public AlreadyExistsException(String reason) {
    super(Response.status(Status.CONFLICT)
        .entity(reason + " already exists")
        .build());
  }

}
