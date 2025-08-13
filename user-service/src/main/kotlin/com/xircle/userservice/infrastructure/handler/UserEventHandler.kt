package com.xircle.userservice.infrastructure.handler

import com.xircle.common.event.UserCreationEventDto
import com.xircle.common.util.JsonUtil.toJson
import com.xircle.common.util.StringUtil.Companion.CREATE_USER_TOPIC
import com.xircle.userservice.infrastructure.model.Outbox
import com.xircle.userservice.infrastructure.repository.outbox.OutboxRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserEventHandler(
    private val outboxRepository: OutboxRepository
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleUserCreateEvent(event: UserCreationEventDto) {
        val payload = toJson(event)
        val outbox = Outbox(CREATE_USER_TOPIC, payload)
        outboxRepository.save(outbox)
    }
}