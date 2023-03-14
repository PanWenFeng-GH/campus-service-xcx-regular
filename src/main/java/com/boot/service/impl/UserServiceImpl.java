package com.boot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.boot.entity.Role;
import com.boot.repository.MenuRepository;
import com.boot.repository.RoleRepository;
import com.boot.service.UserService;
import com.boot.util.StringUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void roleAdd(Role role, String ztreeMenu) throws Exception {
//		Token token = TokenHandler.getBusinesser();
//		User user = new User();
//		user.setId(token.getBusinessId());
//		role.setUpdateUser(user);
		roleRepository.save(role);
		menuRepository.dellByRoleId(role.getId());
		if (StringUtil.isNotBlank(ztreeMenu)) {
			String[] mString = ztreeMenu.split(",");
			for (String m : mString) {
				roleRepository.insertRoleMenu(role.getId(),Integer.valueOf(m));
			}
		}
	}
}
