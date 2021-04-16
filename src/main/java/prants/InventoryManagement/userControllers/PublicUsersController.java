package prants.InventoryManagement.userControllers;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import prants.InventoryManagement.authentication.UserAuthenticationService;
import prants.InventoryManagement.user.LogInInfo;
import prants.InventoryManagement.user.UserCrudService;
import prants.InventoryManagement.user.User;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    UserCrudService users;

    /*@PostMapping("/register")
    String register(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        users
                .save(
                        User
                                .builder()
                                .id(username)
                                .username(username)
                                .password(password)
                                .build()
                );

        return login(username, password);
    }*/

    @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    String registerWithForm( @ModelAttribute LogInInfo registerForm) {
        users
                .save(
                        User
                                .builder()
                                .id(registerForm.getUsername())
                                .username(registerForm.getUsername())
                                .password(registerForm.getPassword())
                                .build()
                );

        return login(registerForm.getUsername(), registerForm.getPassword());
    }

    @PostMapping("/login")
    String login(
            /*@RequestParam("username") final String username,
            @RequestParam("password") final String password) {*/
            @RequestBody final String username,
            @RequestBody final String password) {
        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}
