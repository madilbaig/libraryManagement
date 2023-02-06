package com.assignment.springcrud.repository;

import com.assignment.springcrud.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository repository;

    @Test
    void findAllByName() {
        System.out.println(repository.findAllByName("def"));
    }

    @Test
    void findByPhone() {
        System.out.println(repository.findByPhone(9090909092L));
    }

    @Test
    void findByEmail() {
        System.out.println(repository.findByEmail("def1@gmail.com"));
    }

    @Transactional
    @Test
    void canDeleteByEmail() {
        System.out.println(repository.removeByEmail("def1@gmail.com"));
    }

    @Transactional
    @Test
    void deleteByPhone() {
        System.out.println(repository.deleteByPhone(9090909090l));
    }
    //    @Test
//    void canSaveAll(){
//        List<Member> members = new ArrayList<>();
//        members.add(Member.builder().phone(9090909090L).email("abc@gmail.com").name("abc").build());
//        members.add(Member.builder().phone(9090909091L).email("def@gmail.com").name("def").build());
//        members.add(Member.builder().phone(9090909092L).email("ghi@gmail.com").name("ghi").build());
//        members.add(Member.builder().phone(9090909093L).email("def1@gmail.com").name("def").build());
//        repository.saveAll(members);
//    }
}