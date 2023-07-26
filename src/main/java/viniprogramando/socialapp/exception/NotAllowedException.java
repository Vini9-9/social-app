package viniprogramando.socialapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import viniprogramando.socialapp.rest.dto.ResponseError;

public class NotAllowedException extends WebApplicationException {


  public NotAllowedException(String field, String reason) {
    super(ResponseError.createNotAllowed(field, reason)
            .withStatusCode(Status.PRECONDITION_FAILED.getStatusCode()));
  }


}
