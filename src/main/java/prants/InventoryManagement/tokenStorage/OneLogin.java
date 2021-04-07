package prants.InventoryManagement.tokenStorage;

import lombok.Value;
import prants.InventoryManagement.user.User;

@Value
public class OneLogin {
    long id;
    User targetUser;
    String accessToken;
}
