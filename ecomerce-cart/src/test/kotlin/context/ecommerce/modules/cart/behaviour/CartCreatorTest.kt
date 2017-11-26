package context.ecommerce.modules.cart.behaviour

import context.ecommerce.modules.cart.domain.Cart
import context.ecommerce.modules.cart.domain.CartId
import context.ecommerce.modules.cart.domain.create.CartCreatedEvent
import context.ecommerce.modules.cart.domain.create.CartCreator
import context.ecommerce.modules.cart.domain.create.CreateCartCommandHandler
import context.ecommerce.modules.cart.infrastructure.AxonCartRepository
import context.ecommerce.modules.cart.stub.CreateCartCommandStub
import context.ecommerce.modules.user.domain.UserId
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import shared.expectDomainEvent
import shared.loadAggregate
import kotlin.test.assertEquals

class CartCreatorTest {

    private val fixture = AggregateTestFixture(Cart::class.java)
    private val repository = AxonCartRepository(fixture.repository)
    private val service = CartCreator(repository)
    private val handler = CreateCartCommandHandler(service)

    @BeforeEach
    fun setUp() {
        fixture.registerAnnotatedCommandHandler(handler)
    }

    @Test
    fun `it should create a cart`() {
        val command = CreateCartCommandStub.random()
        val expectedEvent = CartCreatedEvent(command.id, command.userId)

        fixture.givenNoPriorActivity()
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expectedEvent)

        fixture.loadAggregate(command.id)
                .let { aggregate ->
                    assertEquals(CartId.fromString(command.id), aggregate.id)
                    assertEquals(UserId.fromString(command.userId), aggregate.userId)
                }

    }

}