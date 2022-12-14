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
		// Uncomment method to test DB and API functionality
		//temporarySetUp();
	}

	public void temporarySetUp() {
		List<Account> accountList = new ArrayList<>();
		List<User> userList = new ArrayList<>();

		Address addr = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
		Address addr1 = new Address("C/ Ironhack, 321","08001","Barcelona","ES");
		Address addr2 = new Address("C/ Ironhack, 222","08002","Barcelona","ES");
		Address addr3 = new Address("C/ Ironhack, 111","08003","Barcelona","ES");

		Admin admin = new Admin("admin",passwordEncoder.encode("1234"),"name");
		Role adminRole = new Role("ADMIN",admin);

		String pwd = passwordEncoder.encode("1234");
		AccountHolder ah = new AccountHolder("ihacker",pwd,"name",LocalDate.of(1994,07,01),addr);
		AccountHolder ah1 = new AccountHolder("ihacker1",pwd,"name",LocalDate.of(2000,01,01),addr1);
		AccountHolder ah2 = new AccountHolder("ihacker2",pwd,"name",LocalDate.of(2001,01,01),addr2);
		AccountHolder ah3 = new AccountHolder("ihacker3",passwordEncoder.encode("1234"),"name",LocalDate.of(2002,01,01),addr3);
		AccountHolder ah4 = new AccountHolder("ihacker4",passwordEncoder.encode("1234"),"name",LocalDate.of(1899,01,01),addr3);
		Role accountHolderRole = new Role("ACCOUNT_HOLDER",ah);

		ThirdParty tp = new ThirdParty("tp",passwordEncoder.encode("1234"),"name","hashedkey");
		Role thirdPartyRole = new Role("THIRD_PARTY",tp);

		Checking chk = new Checking(BigDecimal.valueOf(2000),"secretkey123", Status.ACTIVE,ah,ah1);
		Checking chk1 = new Checking(BigDecimal.valueOf(1000),"secretkey321",Status.ACTIVE,ah,ah1);
		Checking chk2 = new Checking(BigDecimal.valueOf(1500),"secretkey222",Status.FROZEN,ah,ah1);
		Checking chk3 = new Checking(BigDecimal.valueOf(1700),"secretkey111",Status.FROZEN,ah,ah1);

		CreditCard cd = new CreditCard(BigDecimal.valueOf(200),"secretkey333",Status.ACTIVE,ah,ah1);
		CreditCard cd1 = new CreditCard(BigDecimal.valueOf(200),"secretkey444",Status.FROZEN,ah,ah1,BigDecimal.valueOf(250),0.2);

		Savings sa = new Savings(BigDecimal.valueOf(200),"secretkey012",Status.ACTIVE,ah,ah3,BigDecimal.valueOf(250),0.2);
		Savings sa1 = new Savings(BigDecimal.valueOf(200),"secretkey210",Status.FROZEN,ah2,ah1,BigDecimal.valueOf(250),0.2);
		Savings sa2 = new Savings(BigDecimal.valueOf(1000000),"secretkey120",Status.FROZEN,ah2,ah1,BigDecimal.valueOf(250),0.01);

		StudentChecking st = new StudentChecking(BigDecimal.valueOf(200),"secretkey001",Status.ACTIVE,ah,ah1);
		StudentChecking st1 = new StudentChecking(BigDecimal.valueOf(200),"secretkey002",Status.FROZEN,ah2,ah3);

		sa.checkAndSetBalance(sa.getBalance().add(BigDecimal.valueOf(200)));
		chk.checkAndSetBalance(chk.getBalance().add(BigDecimal.valueOf(200)));
		chk1.checkAndSetBalance(chk1.getBalance().subtract(BigDecimal.valueOf(751)));

		userRepository.save(admin);
		userRepository.save(tp);
		accountHolderRepository.saveAll(List.of(ah,ah1,ah2,ah3,ah4));
		roleRepository.saveAll(List.of(adminRole,accountHolderRole,thirdPartyRole));

		accountRepository.saveAll(List.of(chk,chk1,chk2,chk3,cd,cd1,sa,sa1,sa2,st,st1));
	}

}
