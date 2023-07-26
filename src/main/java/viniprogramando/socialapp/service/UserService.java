package viniprogramando.socialapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import viniprogramando.socialapp.domain.model.User;
import viniprogramando.socialapp.domain.repository.UserRepository;
import viniprogramando.socialapp.exception.AlreadyExistsException;
import viniprogramando.socialapp.exception.NotAllowedException;
import viniprogramando.socialapp.rest.dto.CreateUserRequest;
import viniprogramando.socialapp.rest.dto.UserDtoResponse;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Transactional
    public UserDtoResponse createUser(CreateUserRequest request) throws NotAllowedException {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setBirthDate(stringToLocalDate(request.getBirthDate()));
        userRepository.persist(user);
        return new UserDtoResponse(user);
    }
    @Transactional
    public UserDtoResponse updateUser(User user, CreateUserRequest request) throws Exception {
        if(validChangeUsername(user.getUsername(), request.getUsername())) {
            user.setUsername(request.getUsername());
        }
        if(validChangeEmail(user.getEmail(), request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if(!validChangeBirthDate(request.getBirthDate())){
            user.setBlocked(true);
        }
        user.setBirthDate(stringToLocalDate(request.getBirthDate()));
        return new UserDtoResponse(user);
    }

    private boolean validChangeEmail(String oldEmail, String newEmail) {
        User user = userRepository.findByEmail(newEmail);
        if (user != null && !oldEmail.equalsIgnoreCase(newEmail)) {
            throw new AlreadyExistsException("Email");
        } else {
            return true;
        }
    }
    private boolean validChangeUsername(String oldUsername, String newUsername) {
        User user = userRepository.findByUsername(newUsername);
        if (user != null && !oldUsername.equalsIgnoreCase(newUsername)) {
            throw new AlreadyExistsException("Username");
        } else {
            return true;
        }
    }
    private boolean validChangeBirthDate(String newBirthDate) {
        return isOfLegalAge(stringToLocalDate(newBirthDate));
    }

    public boolean requestIsValid(CreateUserRequest request){
        return validEmail(request.getEmail()) &&
                validUsername(request.getUsername()) &&
                validBirthDate(request.getBirthDate());
    }

    public LocalDate stringToLocalDate(String birthDateStr){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(birthDateStr, dtf);
    }

    public boolean validBirthDate(String birthDateStr) throws NotAllowedException {
        if(isOfLegalAge(stringToLocalDate(birthDateStr))){
            return true;
        } else {
            throw new NotAllowedException("age","user is not old enough");
        }
    }

    public boolean isOfLegalAge(LocalDate dateUser){
        int legalAge = 16;
        int age = Period.between(dateUser, LocalDate.now()).getYears();
        return age >= legalAge;
    }

    public boolean validUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null){
            throw new AlreadyExistsException("Username");
        } else {
            return true;
        }
    }
    public boolean validEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null){
            throw new AlreadyExistsException("Email");
        } else {
            return true;
        }
    }
}
