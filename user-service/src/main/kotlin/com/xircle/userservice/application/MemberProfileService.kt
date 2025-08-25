package com.xircle.userservice.application

import com.xircle.userservice.domain.integration.reader.MemberReader
import com.xircle.userservice.domain.model.Member
import com.xircle.userservice.domain.query.MemberSearchCondition
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MemberProfileService(
    private val memberReader: MemberReader
) {
    @Transactional
    fun searchMember(
        page: Int,
        size: Int,
        memberId: String,
        memberSearchCondition: MemberSearchCondition
    ): List<Member> {
        return memberReader.findMemberByCondition(page, size, memberId, memberSearchCondition)
    }

    @Transactional
    fun getMemberProfile(memberId: String): Member {
        return memberReader.findMemberProfileById(memberId)
    }
}