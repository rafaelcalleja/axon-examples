package com.kotato.context.ecommerce.modules.cart.domain.add

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import org.axonframework.commandhandling.CommandHandler
import org.javamoney.moneta.Money
import javax.inject.Named

@Named
open class AddCartItemCommandHandler(private val adder: CartItemAdder) {

    @CommandHandler
    fun on(command: AddCartItemCommand) {
        adder(CartId.fromString(command.id), command.toCartItem(), command.quantity)
    }

    private fun AddCartItemCommand.toCartItem() =
            CartItem(itemId = ItemId.fromString(this.itemId),
                     price = Money.of(this.price, this.currency))

}