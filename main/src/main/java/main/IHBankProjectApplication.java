package main;

import main.modules.accounts.*;
import main.modules.security.Role;
import main.modules.users.embedded.Address;
import main.modules.users.*;
import main.repositories.security.RoleRepository;
import main.repositories.accounts.AccountRepository;
import main.repositories.users.AccountHolderRepository;
import main.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IHBankProjectApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountHolderRepository accountHolderRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(IHBankProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TO APPLY SPECIFIED FUNCTIONALITIES ALL ACCOUNTS SHOULD CALL
		// checkAndGetBalance/checkAndSetBalance RESPECTIVELY RATHER THAN REGULAR
		// getBalance/setBalance
		temporarySetUp();
	}

	public void temporarySetUp() {
		List<Account> accountList = new ArrayList<>();
		List<User> userList = new ArrayList<>();

		Address addr = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
		Address addr1 = new Address("C/ Calle, 123","08001","Barcelona","ES");
		Address addr2 = new Address("C/ Barcelona, 123","08002","Barcelona","ES");
		Address addr3 = new Address("C/ Bcn, 123","08003","Barcelona","ES");

		Admin admin = new Admin("admin",passwordEncoder.encode("1234"));
		Role adminRole = new Role("ADMIN",admin);
//		adminRole.addToUserList(admin);
//		admin.addRoleToRoles(adminRole);

		String pwd = passwordEncoder.encode("1234");
		AccountHolder ah = new AccountHolder("eduard",passwordEncoder.encode("1234"), LocalDate.of(1994,07,01),addr);
		AccountHolder ah1 = new AccountHolder("Holder A",pwd, LocalDate.of(2000,01,01),addr1);
		AccountHolder ah2 = new AccountHolder("Holder B",pwd, LocalDate.of(2001,01,01),addr2);
		AccountHolder ah3 = new AccountHolder("Holder C",passwordEncoder.encode("1234"), LocalDate.of(2002,01,01),addr3);
		AccountHolder ah4 = new AccountHolder("Hodler",passwordEncoder.encode("1234"), LocalDate.of(1899,01,01),addr3);
		Role accountHolderRole = new Role("ACCOUNT_HOLDER",ah);

		ThirdParty tp = new ThirdParty("tp",pwd,"hashedkey");
		Role thirdPartyRole = new Role("THIRD_PARTY",tp);

		Checking chk = new Checking(BigDecimal.valueOf(2000),"secretkey123", Status.ACTIVE,ah,ah1);
		Checking chk1 = new Checking(BigDecimal.valueOf(1000),"secretkey321",Status.ACTIVE,ah,ah1);
		Checking chk2 = new Checking(BigDecimal.valueOf(1500),"secretkey213",Status.FROZEN,ah,ah1);

		CreditCard cd = new CreditCard(BigDecimal.valueOf(200),"secretkey213",Status.ACTIVE,ah,ah1);
		CreditCard cd1 = new CreditCard(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah,ah1,BigDecimal.valueOf(250),0.2);

		Savings sa = new Savings(BigDecimal.valueOf(200),"secretkey213",Status.ACTIVE,ah,ah3,BigDecimal.valueOf(250),0.2);
		Savings sa1 = new Savings(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah2,ah1,BigDecimal.valueOf(250),0.2);
		Savings sa2 = new Savings(BigDecimal.valueOf(1000000),"secretkey213",Status.FROZEN,ah2,ah1,BigDecimal.valueOf(250),0.01);

		StudentChecking st = new StudentChecking(BigDecimal.valueOf(200),"secretkey213",Status.ACTIVE,ah,ah1);
		StudentChecking st1 = new StudentChecking(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah2,ah3);

		BigDecimal getBalance = sa.checkAndGetBalance();
		sa.checkAndSetBalance(sa.getBalance().add(BigDecimal.valueOf(200)));
		chk.checkAndSetBalance(chk.getBalance().add(BigDecimal.valueOf(200)));
		chk1.checkAndSetBalance(chk1.getBalance().subtract(BigDecimal.valueOf(751)));

		userRepository.save(admin);
		userRepository.save(tp);
		accountHolderRepository.saveAll(List.of(ah,ah1,ah2,ah3,ah4));
		//roleRepository.save(adminRole);
		roleRepository.saveAll(List.of(adminRole,accountHolderRole,thirdPartyRole));


		accountRepository.saveAll(List.of(chk,chk1,chk2,cd,cd1,sa,sa1,sa2,st,st1));
		//create role
		//roleRepository.save(new Role("CONTRIBUTOR", author1));
	}

}
