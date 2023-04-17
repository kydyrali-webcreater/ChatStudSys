package Auth;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.example.controller.JwtAuthenticationController;
import org.example.model.Dto.LoginRequest;
import org.example.model.Dto.RegistrationRequest;
import org.example.model.JwtResponse;
import org.example.model.User;
import org.example.security.CustomUserDetails;
import org.example.security.CustomUserDetailsService;
import org.example.security.JwtUtils;
import org.example.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private JwtAuthenticationController jwtAuthenticationController;

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpassword";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUserId(USERNAME);
        registrationRequest.setPassword(PASSWORD);

        User user = new User();
        user.setId("ffaf");
        user.setFirstname(USERNAME);
        user.setPassword(PASSWORD);

        when(userService.registerUser(registrationRequest)).thenReturn(user);

        ResponseEntity<?> responseEntity = jwtAuthenticationController.registerUser(registrationRequest);

        assert (responseEntity.getStatusCode() == HttpStatus.OK);
        assert (((User) responseEntity.getBody()).getFirstname().equals(USERNAME));
    }
}
