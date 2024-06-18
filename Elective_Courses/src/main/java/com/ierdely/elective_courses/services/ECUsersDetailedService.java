package com.ierdely.elective_courses.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.ierdely.elective_courses.security.annotations.users.IsUsersCreate;
import com.ierdely.elective_courses.security.annotations.users.IsUsersDelete;
import com.ierdely.elective_courses.security.annotations.users.IsUsersRead;
import com.ierdely.elective_courses.security.annotations.users.IsUsersUpdate;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ECUsersDetailedService implements UserDetailsService {
	

	private UsersRepository usersRepository;
	

	private RolesRepository rolesRepository;
	
	 
    private BCryptPasswordEncoder passwordEncoder;
    
    private ModelMapper modelMapper;
	
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
	
    @IsUsersRead
	public Optional<UserDTO> getUser(Long id) {
		Optional<User> opUser = usersRepository.findById(id);
		if(opUser.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(modelMapper.map(opUser.get(), UserDTO.class));
	}
	
    @IsUsersRead
	public UserDTO getUser(String username) {
		
		return modelMapper.map(usersRepository.findByUsername(username), UserDTO.class);
	}
	
	@IsUsersDelete
	public void deleteUserById(Long id) {
		
		User persistentUser = usersRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User entity with id: " + id + " not found."));
		
		usersRepository.delete(persistentUser);
	}
	
	
	@IsUsersUpdate
	public void resetPassword(Long id) {
		
		User persistentUser = usersRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User entity with id: " + id + " not found."));
		persistentUser.setPassword(passwordEncoder.encode("s"));
		usersRepository.save(persistentUser);
	}
	
	@IsUsersCreate
	public User saveUser(User user) {
		
		return usersRepository.save(user);
	}
	
	@IsUsersRead
	public Page<UserDTO> getAllUsers(Pageable pageable) {
		
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		
		
	        
		
		List<UserDTO> repoList = usersRepository.findAllByOrderByRolesAscUsernameAsc().stream().map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		
		List<UserDTO> retList;
		
		if (repoList.size() < startItem) {
			
			retList = Collections.emptyList();
		} else {
			
			int toIndex = Math.min(startItem + pageSize, repoList.size());
			retList = repoList.subList(startItem, toIndex);
		}
		return new PageImpl<>(retList, PageRequest.of(currentPage, pageSize), repoList.size());
		
	}
	
	@IsUsersRead
	public Page<UserDTO> getAllUsers(Pageable pageable, Integer yearOfStudy) {
		
		if( yearOfStudy == null) {
			return getAllUsers(pageable);
		}
		
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		
		
	        
		
		List<UserDTO> repoList = usersRepository.findAllByStudyYearOrderByRolesAscUsernameAsc(yearOfStudy).stream().map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		
		List<UserDTO> retList;
		
		if (repoList.size() < startItem) {
			
			retList = Collections.emptyList();
		} else {
			
			int toIndex = Math.min(startItem + pageSize, repoList.size());
			retList = repoList.subList(startItem, toIndex);
		}
		return new PageImpl<>(retList, PageRequest.of(currentPage, pageSize), repoList.size());
		
	}
	
	@IsUsersRead
	public List<UserDTO> getAllStudentUsers() {
		
		return usersRepository.findAllByRolesContaining(rolesRepository.findByName("ROLE_STUDENT")).stream().map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
	}
	
	public List<RoleDTO> getAllRoles() {
		
		return rolesRepository.findAll().stream().map(role -> modelMapper.map(role, RoleDTO.class))
				.collect(Collectors.toList());
	}
	
	public RoleDTO getRole(String roleName) {
		
		return modelMapper.map(rolesRepository.findByName(roleName), RoleDTO.class);
	}

	@IsUsersCreate
	public User saveUserAccount(UserDTO inputUser) throws RuntimeException {

		String iPass = inputUser.getPassword();
		String ePass = passwordEncoder.encode(iPass);
		User userToSave = modelMapper.map(inputUser, User.class);
		userToSave.setPassword(ePass);
		
	    return usersRepository.save(userToSave);
	}

}
