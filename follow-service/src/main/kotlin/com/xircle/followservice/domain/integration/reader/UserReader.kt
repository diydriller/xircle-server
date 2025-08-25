package com.xircle.followservice.domain.integration.reader

import com.xircle.common.dto.MemberInfo

interface UserReader {
    fun getMemberInfoList(memberIdList: List<String>): List<MemberInfo>
}