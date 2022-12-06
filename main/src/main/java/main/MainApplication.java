package main;

import main.modules.accounts.Account;
import main.modules.accounts.Checking;
import main.modules.accounts.CreditCard;
import main.modules.accounts.Status;
import main.modules.embedded.Address;
import main.modules.users.AccountHolder;
import main.modules.users.User;
import main.repositories.AccountHolderRepository;
import main.repositories.CheckingRepository;
import main.repositories.CreditCardRepository;
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
	/*@Autowired
	AccountRepository accountRepository;
	@Autowired
	UserRepository userRepository;*/
	@Autowired
	AccountHolderRepository accountHolderRepository;
	@Autowired
	CheckingRepository checkingRepository;
	@Autowired
	CreditCardRepository creditCardRepository;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Account> accountList = new ArrayList<>();
		List<User> userList = new ArrayList<>();

		Address addr = new Address("C/ calle, 123","08000","Barcelona","ES");
		Address addr1 = new Address("C/ callejon, 123","08001","Barcelona","ES");
		Address addr2 = new Address("C/ callejon, 123","08002","Barcelona","ES");
		Address addr3 = new Address("C/ callejon, 123","08003","Barcelona","ES");

		AccountHolder ah = new AccountHolder("Eduard", LocalDate.of(1994,07,01),addr);
		AccountHolder ah1 = new AccountHolder("A", LocalDate.of(2000,01,01),addr1);
		AccountHolder ah2 = new AccountHolder("B", LocalDate.of(2001,01,01),addr2);
		AccountHolder ah3 = new AccountHolder("C", LocalDate.of(2002,01,01),addr3);

		Checking chk = new Checking(BigDecimal.valueOf(2000),"secretkey123",Status.ACTIVE,ah,ah1,BigDecimal.valueOf(300),BigDecimal.valueOf(12));
		Checking chk1 = new Checking(BigDecimal.valueOf(1000),"secretkey321",Status.ACTIVE,ah,ah1,BigDecimal.valueOf(250),BigDecimal.valueOf(12));
		Checking chk2 = new Checking(BigDecimal.valueOf(1500),"secretkey213",Status.FROZEN,ah,ah1,BigDecimal.valueOf(250),BigDecimal.valueOf(12));

		CreditCard cd = new CreditCard(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah,ah1);
		CreditCard cd1 = new CreditCard(BigDecimal.valueOf(200),"secretkey213",Status.FROZEN,ah,ah1,BigDecimal.valueOf(250),0.2);

		accountHolderRepository.saveAll(List.of(ah,ah1,ah2,ah3));
		checkingRepository.saveAll(List.of(chk,chk1,chk2));
		creditCardRepository.saveAll(List.of(cd,cd1));
		//passwordEncoder.encode("1234")
		//create role
		//roleRepository.save(new Role("CONTRIBUTOR", author1));
	}

}
