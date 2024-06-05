package com.ierdely.elective_courses.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ierdely.elective_courses.entities.Role;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.repositories.RolesRepository;
import com.ierdely.elective_courses.repositories.UsersRepository;

@Service
@Transactional
public class ECUsersDetailedService implements UserDetailsService {
	

	private UsersRepository usersRepository;
	

	private RolesRepository rolesRepository;
	
	 
    private BCryptPasswordEncoder passwordEncoder;
	
	//@Autowired
    //private IUserService service;
	
	@Autowired //not needed, it is the only constructor, Spring does the autowireing anyway
	public ECUsersDetailedService(BCryptPasswordEncoder passwordEncoder, 
			UsersRepository usersRepository, 
			RolesRepository rolesRepository) {
		
		this.passwordEncoder = passwordEncoder;
		this.usersRepository = usersRepository;
		this.rolesRepository = rolesRepository;
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) {
    	
    	User user = usersRepository.findByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true, 
                true, getAuthorities(user.getRoles()));
    }
	
//	@Override
//    public UserDetails loadUserByUsername(String email)
//      throws UsernameNotFoundException {
// 
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            return new org.springframework.security.core.userdetails.User(
//              " ", " ", true, true, true, true, 
//              getAuthorities(Arrays.asList(
//                roleRepository.findByName("ROLE_USER"))));
//        }
//
//        return 
//    }

    private Collection<? extends GrantedAuthority> getAuthorities(
      Collection<Role> roles) {
 
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
 
       List<String> privileges = new ArrayList<>();

        for (Role role : roles) {
            privileges.add(role.getName());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

	
//	public Optional<User> getUser(Student student) {
//		
//		if (student == null) {
//		
//			throw new IllegalArgumentException("Can not ask for the user of a null student object.");
//		}
//		
//		return usersRepository.findByStudent(student);
//	}
	
	public Optional<User> getUser(Long id) {
		
		return usersRepository.findById(id);
	}
	
	public void deleteUserById(Long id) {
		
		usersRepository.deleteById(id);
	}
	
	public User saveUser(User user) {
		
		return usersRepository.save(user);
	}
	
	public List<User> getAllUsers() {
		
		return usersRepository.findAll();
	}
	
	public List<Role> getAllRoles() {
		
		return rolesRepository.findAll();
	}
	

	public User saveUserAccount(User inputUser) throws RuntimeException {
	    User userToSave;
	    Optional<User> loadedUser;
	    if(inputUser.getId() != null) {
	    	loadedUser = usersRepository.findById(inputUser.getId());
	    	if (loadedUser.isEmpty()) {
	    		throw new RuntimeException("Can not find user with id: " + inputUser.getId());
	    	}
	    	userToSave = loadedUser.get();
	    } else {
	    	//it is a new save
	    	userToSave = new User();
	    	//only copy the username from the dto if we create a new user, 
	    	// username should not be changable
	    	userToSave.setUsername(inputUser.getUsername());
	    }

	    userToSave.setPassword(passwordEncoder.encode(inputUser.getPassword()));

	    userToSave.setRoles(inputUser.getRoles());

	    return usersRepository.save(userToSave);
	}
	
	public boolean usernameTakenByOtherUser(Long id, String username) {
		User user = usersRepository.findByUsername(username);
		if (user == null) {
			return false;
		}
		if (id == null) {
			return true;
		}
		return !user.getId().equals(id);
	}
}
