package com.xircle.userservice.infrastructure.repository.member

import com.xircle.userservice.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface MemberRepository : JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {
    fun findMemberByEmail(email: String): Member?

    fun findMemberById(id: String): Member?

    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.interestList WHERE m.id = :id")
    fun findMemberProfileById(id: String): Member?

    @Query("SELECT m FROM Member m WHERE m.id IN :memberIdList ")
    fun findMemberByIdListIn(memberIdList: List<String>): List<Member>
}