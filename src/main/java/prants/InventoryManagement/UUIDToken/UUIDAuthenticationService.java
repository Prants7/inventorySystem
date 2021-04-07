package prants.InventoryManagement.UUIDToken;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;
import prants.InventoryManagement.authentication.UserAuthenticationService;
import prants.InventoryManagement.tokenStorage.OneLogin;
import prants.InventoryManagement.tokenStorage.TokenCrudService;
import prants.InventoryManagement.user.User;
import prants.InventoryManagement.user.UserCrudService;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UUIDAuthenticationService implements UserAuthenticationService {
    @NonNull
    UserCrudService users;
    @NonNull
    TokenCrudService tokens;
    @NonFinal
    static long assignedTokenId = 0;

    @Override
    public Optional<String> login(String username, String password) {
        Optional<User> foundUser= users.findByUsername(username);
        if(foundUser.isPresent()) {
            if(foundUser.get().getPassword().equals(password)) {
                final String uuid = UUID.randomUUID().toString();
                tokens.save(new OneLogin(this.getNextTokenId(), foundUser.get(), uuid));
                return Optional.of(uuid);
            }

        }
        return Optional.empty();
        /*final String uuid = UUID.randomUUID().toString();
        final User user = User
                .builder()
                .id(uuid)
                .username(username)
                .password(password)
                .build();

        users.save(user);
        return Optional.of(uuid);*/
    }

    @Override
    public Optional<User> findByToken(String token) {
        return tokens.findUserByToken(token);
        //return users.find(token);
    }

    @Override
    public void logout(User user) {
        this.tokens.removeByUser(user);
    }

    private long getNextTokenId() {
        this.assignedTokenId++;
        return this.assignedTokenId;
    }

}
