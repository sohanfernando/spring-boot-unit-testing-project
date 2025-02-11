package lk.zerocode.api.controller;

import lk.zerocode.api.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void clearData() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("register new user ")
    public void testRegisterNewUser() throws Exception {

        var requestBody = """
                {
                  "name":"Chathuranga",
                  "nic":"123456789"
                }
                """;

        var resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .header("X-Api-Version", "v1")
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}