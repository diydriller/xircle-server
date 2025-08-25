package com.xircle.userservice.domain.model

import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*

@Entity
class Interest(
    val title: String
) : BaseEntity() {
    @Id
    @Column(name = "interest_id")
    val id: String = TsidCreator.getTsid().toString()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null
}