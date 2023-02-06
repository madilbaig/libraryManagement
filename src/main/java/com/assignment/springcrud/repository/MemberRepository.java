package com.assignment.springcrud.repository;

import com.assignment.springcrud.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {

    List<Member> findAllByName(String name);
    Member findByPhone(Long phone);
    Member findByEmail(String email);

//    Integer deleteAllByName(String name);
//    @Query(name = "delete from member m where m.email =:email",nativeQuery = true)
    Long removeByEmail(String email);
//    @Query(name = "delete from member m where m.phone = :phone",nativeQuery = true)
    Long deleteByPhone(Long phone);
}
