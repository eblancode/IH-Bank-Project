package main;

import main.modules.accounts.*;
import main.modules.embedded.Address;
import main.modules.users.AccountHolder;
import main.modules.users.Admin;
import main.modules.users.User;
import main.repositories.accounts.*;
import main.repositories.users.AccountHolderRepository;
import main.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountHolderRepository accountHolderRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	CreditCardRepository creditCardRepository;
	@Autowired
	CheckingRepository checkingRepository;
	@Autowired
	SavingsRepository savingsRepository;
	@Autowired
	StudentCheckingRepository studentCheckingRepository;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Account> accountList = new ArrayList<>();
		List<User> userList = new ArrayList<>();

		Address addr = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
		Address addr1 = new Address("C/ Calle, 123","08001","Barcelona","ES");
		Address addr2 = new Address("C/ Barcelona, 123","08002","Barcelona","ES");
		Address addr3 = new Address("C/ Bcn, 123","08003","Barcelona","ES");

		Admin admin = new Admin("God");

		AccountHolder ah = new AccountHolder("Eduard", LocalDate.of(1994,07,01),addr);
		AccountHolder ah1 = new AccountHolder("Holder A", LocalDate.of(2000,01,01),addr1);
		AccountHolder ah2 = new AccountHolder("Holder B", LocalDate.of(2001,01,01),addr2);
		AccountHolder ah3 = new AccountHolder("Holder C", LocalDate.of(2002,01,01),addr3);
		AccountHolder ah4 = new AccountHolder("Hodler", LocalDate.of(1899,01,01),addr3);

		Checking chk = new Checking(BigDecimal.valueOf(2000),"secretkey123",Status.ACTIVE,ah,ah1,BigDecimal.valueOf(300),BigDecimal.valueOf(12));
		Checking chk1 = new Checking(BigDecimal.valueOf(1000),"secretkey321",Status.ACTIVE,ah,ah1,BigDecimal.valueOf(250),BigDecimal.valueOf(12));
		Checking chk2 = new Checking(BigDecimal.valueOf(1500),"secretkey213",Status.FROZEN,ah,ah1,BigDecimal.valueOf(250),BigDecimal.valueOf(12));

		CreditCard cd = new CreditCard(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah,ah1);
		CreditCard cd1 = new CreditCard(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah,ah1,BigDecimal.valueOf(250),0.2);

		Savings sa = new Savings(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah,ah3,BigDecimal.valueOf(250),0.2);
		Savings sa1 = new Savings(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah2,ah1,BigDecimal.valueOf(250),0.2);

		StudentChecking st = new StudentChecking(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah,ah1);
		StudentChecking st1 = new StudentChecking(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah2,ah3);

		sa.checkAndSetBalance(sa.getBalance().add(BigDecimal.valueOf(200)));
		chk.checkAndSetBalance(sa.getBalance().add(BigDecimal.valueOf(200)));
		chk1.checkAndSetBalance(sa.getBalance().subtract(BigDecimal.valueOf(751)));

		userRepository.save(admin);
		accountHolderRepository.saveAll(List.of(ah,ah1,ah2,ah3,ah4));
		accountRepository.saveAll(List.of(chk,chk1,chk2,cd,cd1,sa,sa1,st,st1));
		/*checkingRepository.saveAll(List.of(chk,chk1,chk2));
		creditCardRepository.saveAll(List.of(cd,cd1));
		savingsRepository.saveAll(List.of(sa,sa1));
		studentCheckingRepository.saveAll(List.of(st,st1));*/
		//passwordEncoder.encode("1234")
		//create role
		//roleRepository.save(new Role("CONTRIBUTOR", author1));
	}

}
