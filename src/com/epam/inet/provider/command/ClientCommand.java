package com.epam.inet.provider.command;

import com.epam.inet.provider.entity.Role;
import com.epam.inet.provider.entity.User;

/**
 * Client command.
 * Created by Hedgehog on 20.05.2016
 */
public abstract class ClientCommand extends ActionCommand{
	@Override
	public boolean checkAccess(User user) {
		if (user != null){
			String rolename = user.getRole().getRolename();
			return rolename.equals(Role.ROLE_CLIENT);
		}
		return false;
	}
}
