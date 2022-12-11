package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.modules.users.AccountHolder;
import main.modules.users.ThirdParty;
import main.repositories.users.UserRepository;
import main.services.users.UserService;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final String pwd = passwordEncoder.encode("1234");

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    // Check adding new user, using Mock admin credentials (third party in this case)
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void shouldAddNewUser_whenPostIsPerformedByAnAdmin() throws Exception {
        //AccountHolder ah = new AccountHolder("eduard",pwd, LocalDate.of(1994,07,01),addr);
        String pwd = passwordEncoder.encode("1234");
        ThirdParty tp = new ThirdParty("username",pwd.toString(),"name","hashedkey");

        String body = objectMapper.writeValueAsString(tp);
        MvcResult result = mockMvc.perform(post("/user/third-party/add")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("username"));
    }

    // Check deleting an account holder, using Mock admin credentials
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void shouldDeleteAccountHolder_whenDeleteIsPerformedByAnAdmin() throws Exception {
        String pwd = passwordEncoder.encode("1234");
        AccountHolder accountHolder = new AccountHolder("tester",pwd,"name",LocalDate.of(1994,07,01),null);
        userRepository.save(accountHolder);
        MvcResult result = mockMvc.perform(delete("/user/account_holder/delete/{id}",accountHolder.getId()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertFalse(result.getResponse().getContentAsString().contains("tester"));
    }

}
