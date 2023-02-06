package com.assignment.springcrud.service;

import com.assignment.springcrud.entity.Book;
import com.assignment.springcrud.entity.Member;
import com.assignment.springcrud.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberServiceTest {
    @Autowired private MemberService service;
    @MockBean private MemberRepository repository;

    private Member mockMember = new Member(1,"Member","member@gmail.com",1l);
    private List<Member> ockMembers = Arrays.asList(mockMember, new Member(2,"Member2","member2@gmail.com",2l));
    private Member postMember = new Member("Member","member@gmail.com",1l);

    @Test
    void saveMember() {
        doReturn(mockMember).when(repository).save(any());

        Member returnedProduct = service.saveMember(postMember);

        Assertions.assertNotNull(returnedProduct,"The saved member should not be null");
        Assertions.assertEquals(1,returnedProduct.getMemberId().intValue(),"The id for the new member should be 1");
        Assertions.assertEquals("Member",returnedProduct.getName(),"The name for the new member should be Member");
        Assertions.assertEquals("member@gmail.com",returnedProduct.getEmail(),"The mail for the new member should be member@gmail.com");
        Assertions.assertEquals(1l,returnedProduct.getPhone(),"The phone for the new member should be 1");
    }

    @Test
    void findMemberById() {
        doReturn(Optional.of(mockMember)).when(repository).findById(any());
        Member returnedProduct = service.findMemberById(1);
        Assertions.assertNotNull(returnedProduct,"The returned member should not be null");
        Assertions.assertEquals(1,returnedProduct.getMemberId().intValue(),"The id for the returned member should be 1");
        Assertions.assertEquals("Member",returnedProduct.getName(),"The name for the returned member should be Member");
        Assertions.assertEquals("member@gmail.com",returnedProduct.getEmail(),"The mail for the returned member should be member@gmail.com");
        Assertions.assertEquals(1l,returnedProduct.getPhone(),"The phone for the returned member should be 1");

    }

    @Test
    void deleteMemberById() {
        doNothing().when(repository).deleteById(any());
        String returnedMember = service.deleteMemberById(1);
        Assertions.assertNotNull(returnedMember,"No deletion");
        Assertions.assertEquals("Deleted member by id: 1",returnedMember,"Some error with deletion");
    }

    @Test
    void update() {
        doReturn(Optional.of(mockMember)).when(repository).findById(any());
        Member returnedProduct = service.update(mockMember);
        Assertions.assertNotNull(returnedProduct,"The returned member should not be null");
        Assertions.assertEquals(1,returnedProduct.getMemberId().intValue(),"The id for the returned member should be 1");
        Assertions.assertEquals("Member",returnedProduct.getName(),"The name for the returned member should be Member");
        Assertions.assertEquals("member@gmail.com",returnedProduct.getEmail(),"The mail for the returned member should be member@gmail.com");
        Assertions.assertEquals(1l,returnedProduct.getPhone(),"The phone for the returned member should be 1");
    }
}