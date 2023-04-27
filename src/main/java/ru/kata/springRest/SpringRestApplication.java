package ru.kata.springRest;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.springRest.service.RoleService;
import ru.kata.springRest.service.UserService;

@SpringBootApplication
public class SpringRestApplication implements ApplicationRunner {

	private final UserService userService;
	private final RoleService roleService;

	public SpringRestApplication(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRestApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {


        /*userService.save(
                new User("admin", "admin", 22, "admin@mail.ru",
                        new BCryptPasswordEncoder().encode("12345"),
                         Collections.singleton(roleService.getRoleByName("ROLE_ADMIN"))));
        userService.save(
                new User("user", "user", 21, "user@mail.ru",
                        new BCryptPasswordEncoder().encode("12345"),
                        Collections.singleton(roleService.getRoleByName("ROLE_USER"))));*/
	}

}
