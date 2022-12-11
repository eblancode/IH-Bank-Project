package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.modules.accounts.Checking;
import main.modules.accounts.CreditCard;
import main.modules.accounts.Savings;
import main.modules.accounts.Status;
import main.modules.users.AccountHolder;
import main.modules.users.embedded.Address;
import main.repositories.accounts.AccountRepository;
import main.repositories.users.UserRepository;
import main.services.accounts.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountService accountService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void shouldAddNewAccount_whenAdminUserPerformPost() throws Exception {
        String pwd = passwordEncoder.encode("1234");
        Address address = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
        Address address1 = new Address("C/ Calle, 123","08001","Barcelona","ES");

        AccountHolder accountHolder = new AccountHolder("tester1",pwd,"name",LocalDate.of(1994,07,01),address);
        AccountHolder accountHolder1 = new AccountHolder("tester2",pwd,"name",LocalDate.of(2000,01,01),address1);
        userRepository.save(accountHolder);
        userRepository.save(accountHolder1);

        Checking checking = new Checking(BigDecimal.valueOf(2000),"secretKeyTester1", Status.ACTIVE,accountHolder,accountHolder1);
        CreditCard creditCard = new CreditCard(BigDecimal.valueOf(1000),"secretKeyTester2",Status.ACTIVE,accountHolder,accountHolder1);
        Savings savings = new Savings(BigDecimal.valueOf(1000),"secretKeyTester3",Status.ACTIVE,accountHolder,null,BigDecimal.valueOf(250),0.2);

        String body = objectMapper.writeValueAsString(checking);
        MvcResult result = mockMvc.perform(post("/account/checking/add").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("secretKeyTester1"));

        body = objectMapper.writeValueAsString(creditCard);
        result = mockMvc.perform(post("/account/credit-card/add").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("secretKeyTester2"));

        body = objectMapper.writeValueAsString(savings);
        result = mockMvc.perform(post("/account/savings/add").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("secretKeyTester3"));
    }

    @Test
    @WithMockUser(username = "tester1", password = "1234", roles = "ACCOUNT_HOLDER")
    void shouldGetAccountBalance_whenAccountHolderPerformGetToTheirAccount() throws Exception {
        Address address = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
        AccountHolder accountHolder = new AccountHolder("tester1","1234","name",LocalDate.of(1994,07,01),address);
        Savings savings = new Savings(BigDecimal.valueOf(1000),"secretKeyTester4",Status.ACTIVE,accountHolder,accountHolder,BigDecimal.valueOf(250),0.2);
        userRepository.save(accountHolder);
        accountService.saveAccount(savings);

        String body = objectMapper.writeValueAsString(savings);
        MvcResult result = mockMvc.perform(get("/account/get_balance/{id}",savings.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contentEquals("1000.00"));
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void shouldUpdateNewAccountBalance_whenAdminUserPerformPatch() throws Exception {
        Address address = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
        AccountHolder accountHolder = new AccountHolder("tester1","1234","name",LocalDate.of(1994,07,01),address);
        Savings savings = new Savings(BigDecimal.valueOf(1000),"secretKeyTester4",Status.ACTIVE,accountHolder,null,BigDecimal.valueOf(250),0.2);
        userRepository.save(accountHolder);
        accountService.saveAccount(savings);

        String body = objectMapper.writeValueAsString(savings);
        MvcResult result = mockMvc.perform(patch("/account/update_balance/{id}?balance=999",savings.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(":999"));
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void shouldDeleteNewAccount_whenAdminUserPerformDeletion() throws Exception {
        Address address = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
        AccountHolder accountHolder = new AccountHolder("tester1","1234","name",LocalDate.of(1994,07,01),address);
        Savings savings = new Savings(BigDecimal.valueOf(1000),"secretKeyTester4",Status.ACTIVE,accountHolder,null,BigDecimal.valueOf(250),0.2);
        userRepository.save(accountHolder);
        accountService.saveAccount(savings);

        MvcResult result = mockMvc.perform(delete("/account/delete/{id}",savings.getId()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertFalse(result.getResponse().getContentAsString().contains("tester1"));
    }

}
