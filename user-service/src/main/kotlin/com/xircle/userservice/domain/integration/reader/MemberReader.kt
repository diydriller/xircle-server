package com.xircle.userservice.domain.integration.reader

import com.xircle.userservice.domain.model.Member
import com.xircle.userservice.domain.query.MemberSearchCondition

interface MemberReader {
    fun findMemberById(memberId: String): Member
    fun findMemberByCondition(
        page: Int,
        size: Int,
        memberId: String,
        memberSearchCondition: MemberSearchCondition
    ): List<Member>
    fun findMemberProfileById(memberId: String): Member
    fun findMemberByEmail(email: String): Member
    fun isExistMemberByEmail(email: String)
    fun findMemberByIdListIn(memberIdList: List<String>): List<Member>
}