package store

/**
  * A ShoppingCart is a collection of items for checkout and purchase
  * @param cartItems The InventoryItems to purchase
  */
case class ShoppingCart(cartItems: Inventory) {

  /**
    * Answer the price of the given cartItems against an Inventory
    *
    * @param inventory The Inventory to price against
    * @return The total price
    */
  def priceAgainst(inventory: Inventory): Double = {

    val prices = for {
      cartItem <- cartItems.items
      inventoryItem <- inventory.get(cartItem.description)
    } yield cartItem.quantity * inventoryItem.price
    prices.sum
  }

  /**
    * Answer whether or not the cartItems are compatible with the given bundle
    * (ie enough of each item required to satisfy the bundle)
    *
    * @param bundle The bundle to try to apply against
    * @return true or false
    */
  def canApply(bundle: Bundle): Boolean = {
    val matchingItems = for {
      bundleItem <- bundle.items
      cartItem <- cartItems.get(bundleItem.description)
      if cartItem.quantity >= bundleItem.quantity
    } yield cartItem

    matchingItems.size == bundle.size
  }

  /**
    * Apply a bundle to cartItems, and answer a new ShoppingCart, which is the result of
    * removing those items applied to the bundle.
    *
    * @param bundle The bundle to apply
    * @return A new ShoppingCart, the same as the receiver but without the items applied to the bundle
    */
  def applyBundle(bundle: Bundle): ShoppingCart = {
    def inBundle(x: InventoryItem): Boolean = bundle.contains(x.description)

    val nonBundleItems = cartItems.filterNot(inBundle)
    val bundleItems = cartItems.filter(inBundle)
    val quantityAdjustedBundleItems = bundleItems.map((item) => item - bundle.get(item.description).get.quantity)

    ShoppingCart(Inventory(nonBundleItems ++ quantityAdjustedBundleItems))
  }
}

object ShoppingCart {
  def apply(items: Iterable[InventoryItem]): ShoppingCart = ShoppingCart(Inventory(items))
}