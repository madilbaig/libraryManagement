package com.assignment.springcrud.controller;

import com.assignment.springcrud.entity.Member;
import com.assignment.springcrud.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping
public class MemberController {

    @Autowired
    private MemberService service;

    @PostMapping("/admin/addamember")
    public Member addMember(@RequestBody Member member){ return service.saveMember(member); }

    @PostMapping("/admin/addmembers")
    public List<Member> addMembers(@RequestBody List<Member> members){return service.saveMembers(members);}

    @PostMapping("/admin/addmembersfromcsvmultithread")
    public String addMembersFromCsvMultiThread() throws IOException, ExecutionException, InterruptedException { return service.saveMembersFromCsvMultiThread(); }

    @GetMapping("/user/getmemberbyid/{memberId}")
    public String getMemberById(@PathVariable Integer memberId){
        Member member =service.findMemberById(memberId);
        if(member == null)
            return "Error: Member with MemberId: " + memberId + "does not exist.";
        else
            return service.findMemberById(memberId).toString();
    }

    @GetMapping("/user/getmembersbyname/{name}")
    public String getMembersByName(@PathVariable String name){
        List<Member> members =service.findMembersByName(name);
        if(members == null)
            return "Error: Members with name: " + name + "does not exist.";
        else
            return service.findMembersByName(name).toString();
    }

    @GetMapping("/user/getmemberbyemail/{email}")
    public String getMemberByEmail(@PathVariable String email){
        Member member =service.findMemberByEmail(email);
        if(member == null)
            return "Error : Member with email: " + email + "does not exist.";
        else
            return service.findMemberByEmail(email).toString();
    }

    @GetMapping("/user/getmemberbyphone/{phone}")
    public String getMemberByPhone(@PathVariable Long phone){
        Member member =service.findMemberByPhone(phone);
        if(member == null)
            return "Error : Member with phone: " + phone + "does not exist.";
        else
            return service.findMemberByPhone(phone).toString();
    }

    @GetMapping("/user/getallmembers")
    public String getAllMembers(){
        List<Member> members =service.findAllMembers();
        if(members == null)
            return "Error: No members registered.";
        else
            return service.findAllMembers().toString();
    }

    @DeleteMapping("/admin/deleteallmembers")
    public String removeAllMembers(){
        List<Member> member =service.findAllMembers();
        if(member == null)
            return "Error: No Member exists.";
        else
            return service.deleteAllMembers();
    }

    @DeleteMapping("/admin/deletememberbyid/{memberId}")
    public String removeMemberById(@PathVariable Integer memberId){
        Member member =service.findMemberById(memberId);
        if(member == null)
            return "Error : Member with MemberId: " + memberId + "does not exist.";
        else
            return service.deleteMemberById(memberId);
    }

    @Transactional
    @DeleteMapping("/admin/deletemembersbyname/{name}")
    public String removeMemberByFirstName(@PathVariable String name){
        List<Member> members =service.findMembersByName(name);
        if(members == null)
            return "Error : Members with name: " + name + "does not exist.";
        else
            return service.deleteAllMembersByName(name);
    }

    @Transactional
    @DeleteMapping("/admin/deletememberbyemail/{email}")
    public String removeMemberByEmail(@PathVariable String email){
        Member member =service.findMemberByEmail(email);
        if(member == null)
            return "Error : Member with email: " + email + "does not exist.";
        else
            return service.deleteMemberByEmial(email);
    }

    @Transactional
    @DeleteMapping("/admin/deletememberbyphone/{phone}")
    public String removeMemberByPhone(@PathVariable Long phone){
        Member member =service.findMemberByPhone(phone);
        if(member == null)
            return "Error : Member with phone: " + phone + "does not exist.";
        else
            return service.deleteMemberByPhone(phone);
    }

    @PutMapping("/admin/updatememberbyid")
    public Member updateMemberById(@RequestBody Member member){
        return service.update(member);
    }


}
