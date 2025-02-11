package lk.zerocode.api.controller;

import lk.zerocode.api.controller.request.CreateMemberRequestDTO;
import lk.zerocode.api.model.Member;
import lk.zerocode.api.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerMockitoUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void testRegisterNewUser() throws Exception {

//        Mockito.doNothing().when(memberService.create(Mockito.any(CreateMemberRequestDTO.class)));

        Mockito.when(memberService.create(Mockito.any(CreateMemberRequestDTO.class))).thenReturn(Mockito.any(Member.class));

        var requestBody = """
                {
                  "name":"Chathuranga",
                  "nic":"123456789"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .header("X-Api-Version", "v1")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //here the same test cases with JSONPATH

    @Test
    @DisplayName("get all users")
    public void testGetAllUsers_JSONPATH() throws Exception {

        Member member1 = new Member();
        member1.setId(1L);
        member1.setName("Chathuranga1");
        member1.setNic("123456");

        Member member2 = new Member();
        member2.setId(1L);
        member2.setName("Chathuranga2");
        member2.setNic("1234567");

        Member member3 = new Member();
        member3.setId(3L);
        member3.setName("Chathuranga3");
        member3.setNic("12345678");

        var memberList = List.of(member1, member2, member3);
        Mockito.when(memberService.findAll()).thenReturn(memberList);

        var resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Version", "v1")
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.members.length()").value(3));
        resultActions.andExpect(jsonPath("$.members[0].id").value(member1.getId()));
        resultActions.andExpect(jsonPath("$.members[0].nic").value(member1.getNic()));
        resultActions.andExpect(jsonPath("$.members[0].name").value(member1.getName()));
        resultActions.andExpect(jsonPath("$.members[1].id").value(member2.getId()));
        resultActions.andExpect(jsonPath("$.members[1].nic").value(member2.getNic()));
        resultActions.andExpect(jsonPath("$.members[1].name").value(member2.getName()));
        resultActions.andExpect(jsonPath("$.members[2].id").value(member3.getId()));
        resultActions.andExpect(jsonPath("$.members[2].nic").value(member3.getNic()));
        resultActions.andExpect(jsonPath("$.members[2].name").value(member3.getName()));
    }


    @Test
    @DisplayName("get all users when there are no users registered")
    public void testGetAllUsersWhenNoRegisteredUsers_JSONPATH() throws Exception {

        List<Member> memberList = Collections.emptyList();
        Mockito.when(memberService.findAll()).thenReturn(memberList);

        var resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Version", "v1")
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.members.length()").value(0));
    }


    // here the same test cases with JSONASSERT

    @Test
    @DisplayName("get all users")
    public void testGetAllUsers_JSONASSERT() throws Exception {

        Member member1 = new Member();
        member1.setId(1L);
        member1.setName("Chathuranga1");
        member1.setNic("123456");

        Member member2 = new Member();
        member2.setId(2L);
        member2.setName("Chathuranga2");
        member2.setNic("1234567");

        Member member3 = new Member();
        member3.setId(3L);
        member3.setName("Chathuranga3");
        member3.setNic("12345678");

        var memberList = List.of(member1, member2, member3);
        Mockito.when(memberService.findAll()).thenReturn(memberList);

        String expectedJsonResponse = """
                 {
                 "members": [
                    {
                        "id":1,
                        "name":"Chathuranga1",
                        "nic":"123456"
                    },
                    {
                        "id":2,
                        "name":"Chathuranga2",
                        "nic":"1234567"
                    },
                    {
                        "id":3,
                        "name":"Chathuranga3",
                        "nic":"12345678"
                    }
                ]
                }
                """;

        var resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Version", "v1")
        );
        String actualJson = resultActions.andReturn().getResponse().getContentAsString();

        // Compare using JSONAssert (strict = false allows flexible order and missing fields)
        JSONAssert.assertEquals(expectedJsonResponse, actualJson, true);
    }


    @Test
    @DisplayName("get all users when there are no users registered")
    public void testGetAllUsersWhenNoRegisteredUsers_JSONASSERT() throws Exception {

        List<Member> memberList = Collections.emptyList();
        Mockito.when(memberService.findAll()).thenReturn(memberList);

        String expectedJsonResponse = """
                 {
                   "members": []
                 }
                """;

        var resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Version", "v1")
        );
        String actualJson = resultActions.andReturn().getResponse().getContentAsString();

        // Compare using JSONAssert (strict = false allows flexible order and missing fields)
        JSONAssert.assertEquals(expectedJsonResponse, actualJson, true);
    }


    // here the same test cases with HAMCREST
    @Test
    @DisplayName("get all users")
    public void testGetAllUsers_HAMCREST() throws Exception {

        Member member1 = new Member();
        member1.setId(1L);
        member1.setName("Chathuranga1");
        member1.setNic("123456");

        Member member2 = new Member();
        member2.setId(2L);
        member2.setName("Chathuranga2");
        member2.setNic("1234567");

        Member member3 = new Member();
        member3.setId(3L);
        member3.setName("Chathuranga3");
        member3.setNic("12345678");

        var memberList = List.of(member1, member2, member3);
        Mockito.when(memberService.findAll()).thenReturn(memberList);

        var resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Version", "v1")
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.members", hasSize(3))) // Check array size
                .andExpect(jsonPath("$.members[0].id", is(1))) // Check first member's ID
                .andExpect(jsonPath("$.members[0].name", is("Chathuranga1"))) // Check first member's name
                .andExpect(jsonPath("$.members[1].id", is(2))) // Check second member's ID
                .andExpect(jsonPath("$.members[1].name", is("Chathuranga2")));
    }


    @Test
    @DisplayName("get all users when there are no users registered")
    public void testGetAllUsersWhenNoRegisteredUsers_HAMCREST() throws Exception {

        List<Member> memberList = Collections.emptyList();
        Mockito.when(memberService.findAll()).thenReturn(memberList);

        var resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Version", "v1")
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.members", hasSize(0))); // Check array size
    }
}