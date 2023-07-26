package viniprogramando.socialapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import viniprogramando.socialapp.rest.dto.ResponseError;

public class AlreadyExistsException extends WebApplicationException {

  public AlreadyExistsException(String field) {
    super(ResponseError.createAlreadyExists(field)
            .withStatusCode(Status.CONFLICT.getStatusCode()));
  }

}
