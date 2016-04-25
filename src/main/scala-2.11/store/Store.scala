package store

import scala.annotation.tailrec
import scala.collection.immutable.Set

/**
  * A Store represents the inventory and all possible discount bundles that can be shopped
  *
  * @param bundles The discount bundles that can be applied to a shopping cart
  * @param inventory The inventory of items that can be shopped
  */
case class Store(bundles: Set[Bundle], inventory: Inventory) {

  /**
    * Compute and answer the best possible price for the given shopping cart, considering all the possible bundles
    * that can be applied to it.
    *
    * Enumerating the bundles is factorial in the number of bundles, so this is fine for a small number of
    * them, but if there are more than a few tens of bundles available, a different method should be employed.

    * The exponential work must be done once at setup time to create a lookup table which can be traversed in
    * linear or log-linear time at runtime.
    *
    * @param shoppingCart The ShoppingCart of items to price
    * @return The best possible price for the shopping cart
    */
  def bestPrice(shoppingCart: ShoppingCart): Double = {
    val subsets = bundles.subsets
    val minPrice = subsets.flatMap((bundles) => priceShoppingCartAgainstBundles(bundles.toList, shoppingCart)).min

    minPrice.min(shoppingCart.priceAgainst(inventory))
  }

  /**
    * Answer the price of shoppingCart after applying the given bundles.
    * Answer None if the bundles cannot be applied.
    *
    * @param bundles Bundles to apply
    * @param shoppingCart The shopping cart to price
    * @param priceForBundles The price of bundles that have already been applied (initially 0.0)
    * @return Optionally, a double representing the price of the shopping cart.
    */
  @tailrec
  private def priceShoppingCartAgainstBundles(bundles: List[Bundle],
                                              shoppingCart: ShoppingCart,
                                              priceForBundles: Double = 0.0): Option[Double] = bundles match {
      case Nil => Some(priceForBundles + shoppingCart.priceAgainst(inventory))
      case x :: xs if !shoppingCart.canApply(x) => None
      case x :: xs => priceShoppingCartAgainstBundles(xs, shoppingCart.applyBundle(x), priceForBundles + x.discountedPrice)
    }
}
