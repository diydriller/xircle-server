package com.xircle.postservice.domain.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*

@Entity
class Hashtag(
    val name: String
) : BaseEntity() {
    @Id
    @Column(name = "hashtag_id")
    val id: String = TsidCreator.getTsid().toString()

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null
}