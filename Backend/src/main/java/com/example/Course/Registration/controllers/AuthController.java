package com.example.Course.Registration.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.Course.Registration.payload.response.AbstractResponseFactory;
import com.example.Course.Registration.payload.response.JwtResponseFactory;
import com.example.Course.Registration.payload.response.MessageResponseFactory;
import com.example.Course.Registration.security.jwt.JwtUtils;
import com.example.Course.Registration.security.services.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Course.Registration.models.ERole;
import com.example.Course.Registration.models.Role;
import com.example.Course.Registration.models.User;
import com.example.Course.Registration.payload.request.LoginRequest;
import com.example.Course.Registration.payload.request.SignupRequest;
import com.example.Course.Registration.payload.response.MessageResponse;
import com.example.Course.Registration.repository.RoleRepository;
import com.example.Course.Registration.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	AuthenticationManager authenticationManager;

	UserRepository userRepository;

	RoleRepository roleRepository;

	// PasswordEncoder encoder;

	JwtUtils jwtUtils;

	AbstractResponseFactory MessageResponseFactory = new MessageResponseFactory();

	AbstractResponseFactory JwtResponseFactory = new JwtResponseFactory();

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		// this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// Authentication authentication = authenticationManager.authenticate(
		// new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
		// loginRequest.getPassword()));

		Optional<User> user = userRepository.findByEmail(loginRequest.getUsername());

		if (user == null) {
			return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Invalid username or password"));
		}

		if (!user.get().getPassword().equals(loginRequest.getPassword())) {
			return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Invalid username or password"));
		}

		// System.out.println("Authentication :" + authentication);
		// SecurityContextHolder.getContext().setAuthentication(authentication);
		// String jwt = jwtUtils.generateJwtToken(authentication);

		// UserDetailsImpl userDetails = (UserDetailsImpl)
		// authentication.getPrincipal();

		UserDetailsImpl userDetails = new UserDetailsImpl(
				user.get().getId(),
				user.get().getEmail(),
				user.get().getPassword(),
				user.get().getRoles().stream()
						.map(role -> new SimpleGrantedAuthority(role.getName().name()))
						.collect(Collectors.toList()),
				user.get().getName());

		// Generate JWT token without using authentication
		String jwt = jwtUtils.generateJwtToken(userDetails);

		return ResponseEntity.ok(JwtResponseFactory.getResponse(jwt + "," + userDetails.toString()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		// if (userRepository.existsByUsername(signUpRequest.getUsername())) {
		// return ResponseEntity
		// .badRequest()
		// .body(new MessageResponse("Error: Username is already taken!"));
		// }

		// System.out.println("TESTING");
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(MessageResponseFactory.getResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getName(),
				signUpRequest.getEmail(),
				signUpRequest.getPassword(),
				signUpRequest.getBranch(),
				signUpRequest.getDegree());

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "mod":
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		System.out.println("Details of User:");
		System.out.println("Name :" + user.getName());
		System.out.println("Email :" + user.getEmail());
		System.out.println("Password :" + user.getPassword());
		System.out.println("Branch :" + user.getBranch());
		System.out.println("Degree :" + user.getDegree());
		// System.out.println("\nRoles :"+user.getRoles());
		for (Role role2 : user.getRoles()) {
			System.out.println("Role :" + role2.getName());
		}
		userRepository.save(user);

		return ResponseEntity.ok(MessageResponseFactory.getResponse("User registered successfully!"));
	}

}
