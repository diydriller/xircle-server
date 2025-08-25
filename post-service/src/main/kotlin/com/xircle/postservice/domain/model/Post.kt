package com.xircle.postservice.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize

@Entity
class Post(
    val content: String,
    val title: String,
    @Column(name = "post_img_src")
    val postImgSrc: String,
    @Column(name = "member_id")
    val memberId: String
) : BaseEntity() {
    @Id
    @Column(name = "post_id")
    val id: String = TsidCreator.getTsid().toString()

    @JsonManagedReference
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "post", cascade = [CascadeType.PERSIST])
    val hashtagList: MutableList<Hashtag> = ArrayList()

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    val commentList: MutableList<Comment> = ArrayList()

    fun addHashtag(hashtag: Hashtag) {
        hashtagList.add(hashtag)
        hashtag.post = this
    }
}