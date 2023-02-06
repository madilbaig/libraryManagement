package com.assignment.springcrud.controller;

import com.assignment.springcrud.CustomUserDetails;
import com.assignment.springcrud.entity.Member;
import com.assignment.springcrud.entity.User;
import com.assignment.springcrud.repository.UserRepository;
import com.assignment.springcrud.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MemberControllerTest {

    @MockBean private MemberService service;
    @Autowired MockMvc mockMvc;
    @Autowired UserRepository repository;

    private UserDetails adminUserDetails = new CustomUserDetails(new User("baig", passwordEncoder().encode("1234"),"ADMIN"));
    private UserDetails userDetails = new CustomUserDetails(new User("adil", passwordEncoder().encode("1234"),"USER"));


    @Test
    void testCanAddMember() throws Exception {
        Member mockMember = new Member(1,"abc","abc@abc.com",90l);
        Member postMember = new Member("abc","abc@abc.com",90l);

        doReturn(mockMember).when(service).saveMember(any());

        mockMvc.perform(post("/admin/addamember")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postMember)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.name").value("abc"))
                .andExpect(jsonPath("$.email").value("abc@abc.com"))
                .andExpect(jsonPath("$.phone").value(90l));
    }

    @Test
    void testCanAddMembers() throws Exception {
        List<Member> mockMembers = Arrays.asList(
                new Member(1,"abc","abc@abc.com",90l),
                new Member(2,"def","def@abc.com",91l));
        List<Member> postMembers = Arrays.asList(
                new Member("abc","abc@abc.com",90l),
                new Member("def","def@abc.com",91l));
        doReturn(mockMembers).when(service).saveMembers(any());
        mockMvc.perform(post("/admin/addmembers")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postMembers)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].memberId").value(1))
                .andExpect(jsonPath("$[0].name").value("abc"))
                .andExpect(jsonPath("$[0].email").value("abc@abc.com"))
                .andExpect(jsonPath("$[0].phone").value(90l))
                .andExpect(jsonPath("$[1].memberId").value(2))
                .andExpect(jsonPath("$[1].name").value("def"))
                .andExpect(jsonPath("$[1].email").value("def@abc.com"))
                .andExpect(jsonPath("$[1].phone").value(91l));
    }

    @Test
    void testCanGetMemberById() throws Exception {
        Member mockMember = new Member(1,"abc","abc@abc.com",90l);
        doReturn(mockMember).when(service).findMemberById(any());
        mockMvc.perform(get("/user/getmemberbyid/1")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Member(memberId=1, name=abc, email=abc@abc.com, phone=90)"));
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.memberId").value(1))
//                .andExpect(jsonPath("$.name").value("abc"))
//                .andExpect(jsonPath("$.email").value("abc@abc.com"))
//                .andExpect(jsonPath("$.phone").value(90l));

    }

    @Test
    void testCanGetMembersByName() throws Exception {
        List<Member> mockMembers = Arrays.asList(
                new Member(1,"abc","abc@abc.com",90l),
                new Member(2,"abc","def@abc.com",91l));
        doReturn(mockMembers).when(service).findMembersByName(any());
        mockMvc.perform(get("/user/getmembersbyname/abc")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string(
                        "[Member(memberId=1, name=abc, email=abc@abc.com, phone=90), Member(memberId=2, name=abc, email=def@abc.com, phone=91)]"));
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].memberId").value(1))
//                .andExpect(jsonPath("$[0].name").value("abc"))
//                .andExpect(jsonPath("$[0].email").value("abc@abc.com"))
//                .andExpect(jsonPath("$[0].phone").value(90l))
//                .andExpect(jsonPath("$[1].memberId").value(2))
//                .andExpect(jsonPath("$[1].name").value("abc"))
//                .andExpect(jsonPath("$[1].email").value("def@abc.com"))
//                .andExpect(jsonPath("$[1].phone").value(91l));
    }

    @Test
    void testCanGetMemberByemail() throws Exception {
        Member mockMember = new Member(1, "abc", "abc@abc.com", 90l);
        doReturn(mockMember).when(service).findMemberByEmail(any());
        mockMvc.perform(get("/user/getmemberbyemail/abc@abc.com")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Member(memberId=1, name=abc, email=abc@abc.com, phone=90)"));
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.memberId").value(1))
//                .andExpect(jsonPath("$.name").value("abc"))
//                .andExpect(jsonPath("$.email").value("abc@abc.com"))
//                .andExpect(jsonPath("$.phone").value(90l));
    }

    @Test
    void testCanGetMemberByPhone() throws Exception {
        Member mockMember = new Member(1,"abc","abc@abc.com",90l);
        doReturn(mockMember).when(service).findMemberByPhone(any());
        mockMvc.perform(get("/user/getmemberbyphone/90")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Member(memberId=1, name=abc, email=abc@abc.com, phone=90)"));
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.memberId").value(1))
//                .andExpect(jsonPath("$.name").value("abc"))
//                .andExpect(jsonPath("$.email").value("abc@abc.com"))
//                .andExpect(jsonPath("$.phone").value(90l));
    }

    @Test
    void testCanGetAllMembers() throws Exception {
        List<Member> mockMembers = Arrays.asList(
                new Member(1,"abc","abc@abc.com",90l),
                new Member(2,"def","def@abc.com",91l));
        doReturn(mockMembers).when(service).findAllMembers();
        mockMvc.perform(get("/user/getallmembers")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string(
                        "[Member(memberId=1, name=abc, email=abc@abc.com, phone=90), Member(memberId=2, name=def, email=def@abc.com, phone=91)]"));
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].memberId").value(1))
//                .andExpect(jsonPath("$[0].name").value("abc"))
//                .andExpect(jsonPath("$[0].email").value("abc@abc.com"))
//                .andExpect(jsonPath("$[0].phone").value(90l))
//                .andExpect(jsonPath("$[1].memberId").value(2))
//                .andExpect(jsonPath("$[1].name").value("def"))
//                .andExpect(jsonPath("$[1].email").value("def@abc.com"))
//                .andExpect(jsonPath("$[1].phone").value(91l));
    }

    @Test
    void testCanRemoveAllMembers() throws Exception {
        List<Member> mockMembers = Arrays.asList(
                new Member(1,"abc","abc@abc.com",90l),
                new Member(2,"def","def@abc.com",91l));
        doReturn(mockMembers).when(service).findAllMembers();
        doReturn("Deleted all members").when(service).deleteAllMembers();
        mockMvc.perform(delete("/admin/deleteallmembers")
                        .with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(result -> {String s = "Deleted all members";});
    }

    @Test
    void testCanRemoveMemberById() throws Exception {
        Member mockMember = new Member(1,"abc","abc@abc.com",90l);
        doReturn(mockMember).when(service).findMemberById(any());
        doReturn("Deleted member by id: 1").when(service).deleteMemberById(any());
        mockMvc.perform(delete("/admin/deletememberbyid/1")
                        .with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(result -> {String s = "Deleted member by id: 1";});
    }

    @Test
    void testCanRemoveMemberByEmail() throws Exception {
        Member mockMember = new Member(1,"abc","abc@abc.com",90l);
        doReturn(mockMember).when(service).findMemberByEmail(any());
        doReturn("Deleted member by email: abc@abc.com").when(service).deleteMemberByEmial(any());
        mockMvc.perform(delete("/admin/deletememberbyemail/abc@abc.com")
                        .with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(result -> {String s = "Deleted member by email: abc@abc.com";});
    }

    @Test
    void testCanRemoveMemberByPhone() throws Exception {
        Member mockMember = new Member(1,"abc","abc@abc.com",90l);
        doReturn(mockMember).when(service).findMemberByPhone(any());
        doReturn("Deleted member by phone: 90").when(service).deleteMemberByPhone(any());
        mockMvc.perform(delete("/admin/deletememberbyphone/90")
                        .with(user(adminUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(result -> {String s = "Deleted member by phone: 90";});
    }

    @Test
    void testCanUpdateMemberById() throws Exception {
        Member mockMember = new Member(1,"abc","abc@abc.com",90l);
        Member postMember = new Member("abc","abc@abc.com",90l);
        doReturn(mockMember).when(service).update(any());

        mockMvc.perform(put("/admin/updatememberbyid")
                        .with(user(adminUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postMember)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.name").value("abc"))
                .andExpect(jsonPath("$.email").value("abc@abc.com"))
                .andExpect(jsonPath("$.phone").value(90l));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}