package viniprogramando.socialapp.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseError {
    public static final int UNPROCESSABLE_ENTITY = 422;
    private String message;
    private Collection<FieldError> errors;

    public ResponseError(String message, Collection<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations){
        List<FieldError> errors = violations.stream()
                .map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());
        return new ResponseError("Validation Error", errors);
    }
    public static <T> ResponseError createNotFound(String field){
        List<FieldError> errors = new ArrayList<FieldError>();
        errors.add(new FieldError(field, "Not found user with this " + field));
        return new ResponseError("Not found error", errors);
    }
    public static <T> ResponseError createAlreadyExists(String field){
        List<FieldError> errors = new ArrayList<FieldError>();
        errors.add(new FieldError(field, field + " already exists"));
        return new ResponseError("Already exists error", errors);
    }

    public Response withStatusCode(int code){
        return Response.status(code).entity(this).build();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(Collection<FieldError> errors) {
        this.errors = errors;
    }
}
