package viniprogramando.socialapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class NotAllowedException extends WebApplicationException {


  public NotAllowedException(String reason) {
    super(Response.status(Status.PRECONDITION_FAILED)
        .entity(reason)
        .build());
  }


}
