package com.xircle.followservice.domain.model

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Member")
class MemberNode(
    @Id
    val id: String
) {
    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
    var follows: List<MemberNode> = emptyList()
}