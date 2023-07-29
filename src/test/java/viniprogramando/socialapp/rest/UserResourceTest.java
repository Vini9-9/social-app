package viniprogramando.socialapp.rest;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import viniprogramando.socialapp.rest.dto.CreateUserRequest;
import viniprogramando.socialapp.service.UserService;
import org.hamcrest.Matchers;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    private String BIRTH_DATE_VALID = "01/01/2000";
    private String BIRTH_DATE_UNDERAGE = "01/01/2013";
    private String EMAIL_VALID = "email@email.com";
    private String USERNAME_VALID = "tester";

    @Inject
    UserService userService;

    @TestHTTPResource("/users")
    URL apiURL;

    @Test
    @DisplayName("Should create an user successfully")
    @Order(2)
    public void shouldCreateUser(){
        CreateUserRequest userRequest = this.getValidUserRequest();
        var response = postUserRequest(userRequest);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatusCode());
        assertNotNull(response.jsonPath().getString("id"));
        assertEquals(response.jsonPath().getString("username"), userRequest.getUsername());
        assertEquals(response.jsonPath().getString("birthDate"), userRequest.getBirthDate());
        assertEquals(response.jsonPath().getString("email"), userRequest.getEmail());

    }
    @Test
    @DisplayName("Should return error when create an underage user")
    @Order(1)
    public void shouldNotCreateUserUnderage(){
        CreateUserRequest userRequest = this.getValidUserRequest();
        userRequest.setBirthDate(this.BIRTH_DATE_UNDERAGE);
        var response = postUserRequest(userRequest);

        assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), response.getStatusCode());
        assertEquals(response.jsonPath().getString("message"), "Not allowed error");
        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertEquals(errors.get(0).get("field"), "age");
        assertEquals(errors.get(0).get("message"), "user is not old enough");

    }

    @Test
    @DisplayName("Should return error when create an user with duplicate email")
    @Order(3)
    public void shouldNotCreateUserWithDuplicateEmail(){
        CreateUserRequest userRequest = this.getValidUserRequest();
        userRequest.setUsername("duplicate");
        var response = postUserRequest(userRequest);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatusCode());
        assertEquals(response.jsonPath().getString("message"), "Already exists error");
        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertEquals(errors.get(0).get("field"), "Email");
        assertEquals(errors.get(0).get("message"), "Email already exists");

    }
    @Test
    @DisplayName("Should return error when create an user with duplicate username")
    @Order(4)
    public void shouldNotCreateUserWithDuplicateUsername(){
        CreateUserRequest userRequest = this.getValidUserRequest();
        userRequest.setEmail("duplicate@example.com");
        var response = postUserRequest(userRequest);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatusCode());
        assertEquals(response.jsonPath().getString("message"), "Already exists error");
        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertEquals(errors.get(0).get("field"), "Username");
        assertEquals(errors.get(0).get("message"), "Username already exists");

    }

    @Test
    @DisplayName("should list all users")
    @Order(5)
    public void listAllUsersTest(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(apiURL)
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }

    public CreateUserRequest getValidUserRequest(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setBirthDate(BIRTH_DATE_VALID);
        userRequest.setEmail(EMAIL_VALID);
        userRequest.setUsername(USERNAME_VALID);
        return userRequest;
    }

    public io.restassured.response.Response postUserRequest(CreateUserRequest userRequest){
        io.restassured.response.Response response = given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post(apiURL)
                .then()
                .extract().response();
        return response;
    }

}