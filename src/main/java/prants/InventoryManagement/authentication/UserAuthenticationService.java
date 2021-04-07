package prants.InventoryManagement.authentication;

import prants.InventoryManagement.user.User;

import java.util.Optional;

/**
 * From https://octoperf.com/blog/2018/03/08/securing-rest-api-spring-security/#user-auth-api
 */
public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<String> login(String username, String password);

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<User> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param user the user to logout
     */
    void logout(User user);
}
