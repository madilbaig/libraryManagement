package com.assignment.springcrud.service;

import com.assignment.springcrud.entity.Member;
import com.assignment.springcrud.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

//    public Member saveMember(){
//        Member member = Member.builder()
//                .email("adil@gmail.com")
//                .name("adil baig")
//                .phone(90_90_90_80_90L)
//                .build();
//        return repository.save(member);
//    }

    public Member saveMember(Member member){
        return repository.save(member);
    }

    public List<Member> saveMembers(List<Member> members){
        return repository.saveAll(members);
    }

    public String saveMembersFromCsvMultiThread() throws ExecutionException, InterruptedException, IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        FileRead fileRead;
        Future<String> future;
        for (int i=0;i<14999;i++){
            fileRead = new FileRead(i);
            future = executorService.submit(fileRead);
            String[] details = future.get().split(",");
            repository.save(Member.builder().name(details[0]).email(details[1]).phone(Long.valueOf(details[2])).build());
        }
        executorService.shutdown();
        return "Books from csv using multi threading saved";
    }

    public Member findMemberById(Integer MemberId){
        return repository.findById(MemberId).orElse(null);
    }

    public List<Member> findMembersByName(String firstname){
        return repository.findAllByName(firstname);
    }

    public Member findMemberByPhone(Long phone){
        return repository.findByPhone(phone);
    }

    public Member findMemberByEmail(String email){
        return repository.findByEmail(email);
    }

    public List<Member> findAllMembers(){
        return repository.findAll();
    }

    public String deleteMemberById(Integer MemberId){
        repository.deleteById(MemberId);
        return "Deleted member by id: " + MemberId;
    }

    public String deleteAllMembersByName(String firstName){
        return  "Delete the Member By using his Id number as per the list\n" + repository.findAllByName(firstName);
    }

    public String deleteMemberByEmial(String email){
        repository.removeByEmail(email);
        return "Deleted member with email: " + email;
    }

    public String deleteMemberByPhone(Long phone){
        repository.deleteByPhone(phone);
        return "Deleted member with email: " + phone;
    }

    public String deleteAllMembers(){
        repository.deleteAll();
        return "Deleted all members";
    }

    public Member update(Member member){
        Member existingMember = repository.findById(member.getMemberId()).orElse(member);
        existingMember.setEmail(member.getEmail());
        existingMember.setPhone(member.getPhone());
        existingMember.setName(member.getName());
        repository.save(existingMember);
        return existingMember;
    }
}
