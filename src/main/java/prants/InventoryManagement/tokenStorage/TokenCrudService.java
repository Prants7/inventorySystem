package prants.InventoryManagement.tokenStorage;

import prants.InventoryManagement.user.User;

import java.util.Optional;

public interface TokenCrudService {

    OneLogin save(OneLogin oneLogin);

    Optional<OneLogin> find(String accessToken);

    Optional<User> findUserByToken(String accessToken);

    Optional<OneLogin> findByUser(User user);

    boolean remove(OneLogin oldLogin);

    boolean removeByUser(User user);

}
