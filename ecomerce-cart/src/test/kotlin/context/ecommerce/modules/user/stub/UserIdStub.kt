package context.ecommerce.modules.user.stub

import context.ecommerce.modules.user.domain.UserId
import java.util.UUID

class UserIdStub {
    companion object {
        fun random(): UserId {
            return UserId(UUID.randomUUID())
        }
    }
}