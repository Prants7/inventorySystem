package prants.InventoryManagement.tokenStorage;

import org.springframework.stereotype.Service;
import prants.InventoryManagement.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class InMemoryLogins implements  TokenCrudService {
    Map<String, OneLogin> loggedInUsers = new HashMap<>();

    @Override
    public OneLogin save(OneLogin oneLogin) {
        return loggedInUsers.put(oneLogin.getAccessToken(), oneLogin);
    }

    @Override
    public Optional<OneLogin> find(String accessToken) {
        return ofNullable(loggedInUsers.get(accessToken));
    }

    @Override
    public Optional<User> findUserByToken(String accessToken) {
        if(find(accessToken).isPresent()) {
            return Optional.of(find(accessToken).get().getTargetUser());
        }
        return Optional.empty();
    }

    @Override
    public Optional<OneLogin> findByUser(User user) {
        return loggedInUsers
                .values()
                .stream()
                .filter(oneLogin -> Objects.equals(user, oneLogin.getTargetUser()))
                .findFirst();
    }

    @Override
    public boolean remove(OneLogin oldLogin) {
        if(loggedInUsers.containsValue(oldLogin)) {
            loggedInUsers.remove(oldLogin.getAccessToken());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeByUser(User user) {
        Optional<OneLogin> foundLogin = findByUser(user);
        if(foundLogin.isPresent()) {
            return this.remove(foundLogin.get());
        }
        return false;
    }
}
