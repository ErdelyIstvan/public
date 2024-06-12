package com.ierdely.elective_courses.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ierdely.elective_courses.dto.RoleDTO;
import com.ierdely.elective_courses.dto.UserDTO;
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
    
    private ModelMapper modelMapper;
	
	//@Autowired
    //private IUserService service;
	
	@Autowired //not needed, it is the only constructor, Spring does the autowireing anyway
	public ECUsersDetailedService(BCryptPasswordEncoder passwordEncoder, 
			UsersRepository usersRepository, 
			RolesRepository rolesRepository,
			ModelMapper modelMapper) {
		
		this.passwordEncoder = passwordEncoder;
		this.usersRepository = usersRepository;
		this.rolesRepository = rolesRepository;
		this.modelMapper = modelMapper;
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
	
	public Optional<UserDTO> getUser(Long id) {
		Optional<User> opUser = usersRepository.findById(id);
		if(opUser.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(modelMapper.map(opUser.get(), UserDTO.class));
	}
	
	public UserDTO getUser(String username) {
		
		return modelMapper.map(usersRepository.findByUsername(username), UserDTO.class);
	}
	
	public void deleteUserById(Long id) {
		
		usersRepository.deleteById(id);
	}
	
	public User saveUser(User user) {
		
		return usersRepository.save(user);
	}
	
	public List<UserDTO> getAllUsers() {
		
		return usersRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
	}
	

	
	public List<RoleDTO> getAllRoles() {
		
		return rolesRepository.findAll().stream().map(role -> modelMapper.map(role, RoleDTO.class))
				.collect(Collectors.toList());
	}
	
	public RoleDTO getRole(String roleName) {
		
		return modelMapper.map(rolesRepository.findByName(roleName), RoleDTO.class);
	}

	public User saveUserAccount(UserDTO inputUser) throws RuntimeException {

		String iPass = inputUser.getPassword();
		String ePass = passwordEncoder.encode(iPass);
		User userToSave = modelMapper.map(inputUser, User.class);
		userToSave.setPassword(ePass);
		
	    return usersRepository.save(userToSave);
	}

}
