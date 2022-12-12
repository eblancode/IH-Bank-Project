package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.dtos.TransactionDTO;
import main.modules.accounts.Checking;
import main.modules.accounts.Savings;
import main.modules.accounts.Status;
import main.modules.users.AccountHolder;
import main.modules.users.ThirdParty;
import main.modules.users.embedded.Address;
import main.repositories.accounts.AccountRepository;
import main.repositories.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TransactionControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "tester1", password = "1234", roles = "ACCOUNT_HOLDER")
    void shouldMakeTransfer_whenAccountHolderPerformPatch() throws Exception {
        Address address = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
        AccountHolder accountHolder = new AccountHolder("tester1","1234","name", LocalDate.of(1994,07,01),address);
        AccountHolder accountHolder1 = new AccountHolder("tester2","1234","name", LocalDate.of(1994,07,01),address);
        Savings savings = new Savings(BigDecimal.valueOf(2000),"secretKeyTester", Status.ACTIVE,accountHolder,accountHolder1,BigDecimal.valueOf(250),0.2);
        Savings savings1 = new Savings(BigDecimal.valueOf(1000),"secretKeyTester1", Status.ACTIVE,accountHolder1,accountHolder,BigDecimal.valueOf(250),0.2);
        userRepository.saveAll(List.of(accountHolder,accountHolder1));
        accountRepository.saveAll(List.of(savings,savings1));

        TransactionDTO transactionDTO = new TransactionDTO(savings.getId(),savings1.getId(),accountHolder1.getUserName(),BigDecimal.valueOf(500));

        String body = objectMapper.writeValueAsString(transactionDTO);
        MvcResult result = mockMvc.perform(patch("/transfer").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(":500"));
    }

    @Test
    @WithMockUser(username = "tester3", password = "1234", roles = "THIRD_PARTY")
    void shouldMakeTransfer_whenThirdPartyPerformPatch() throws Exception {
        Address address = new Address("C/ Ironhack, 123","08000","Barcelona","ES");
        ThirdParty thirdParty = new ThirdParty("tester3","1234","name","hashedKey");
        AccountHolder accountHolder = new AccountHolder("tester4","1234","name", LocalDate.of(1994,07,01),address);
        AccountHolder accountHolder1 = new AccountHolder("tester5","1234","name", LocalDate.of(1994,07,01),address);
        Checking checking = new Checking(BigDecimal.valueOf(250),"secretKeyTester", Status.ACTIVE,accountHolder,accountHolder1);
        Savings savings = new Savings(BigDecimal.valueOf(1000),"secretKeyTester1", Status.ACTIVE,accountHolder1,accountHolder,BigDecimal.valueOf(250),0.2);
        userRepository.saveAll(List.of(thirdParty,accountHolder,accountHolder1));
        accountRepository.saveAll(List.of(checking,savings));

        TransactionDTO transactionDTO = new TransactionDTO(checking.getId(),savings.getId(),BigDecimal.valueOf(25));

        String body = objectMapper.writeValueAsString(transactionDTO);
        MvcResult result = mockMvc.perform(
                patch("/transfer/third-party?tpHashedKey=" + thirdParty.getHashedKey() +
                        "&senderAccountSecretKey=" + checking.getSecretKey()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(":25"));
    }

}
