package com.epam.inet.provider.command;

import com.epam.inet.provider.entity.Role;
import com.epam.inet.provider.entity.User;

import static com.epam.inet.provider.util.Constants.*;
/**
 * Admin command that is allowed for execution pages only with for admin privileges
 * Created by Hedgehog on 20.05.2016.
 */
public abstract class AdminCommand extends ActionCommand{

    /**
     * Checks access level. Only admin has access to this command
     * @param user - User entity, can be null
     * @return
     */
    @Override
    public boolean checkAccess(User user) {
        if (user != null){
            Role role = user.getRole();
            if (role != null && Role.ROLE_ADMIN.equals(role.getRolename())){
                return true;
            }
        }
        return false;
    }

}
