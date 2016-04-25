package Store$Test


import store._
import org.scalatest.{FlatSpec, Matchers}


class Store$Test extends FlatSpec with Matchers {
  "This shopping cart costs 4.00. It" should "cost 4.00 without any bundles." in {
    val inventory = Inventory(Seq(
      InventoryItem("Apple", price=0.75),
      InventoryItem("Pear", price=1.25),
      InventoryItem("Banana", price=0.50)))

    val shoppingCart = ShoppingCart(Seq(
      InventoryItem("Apple", quantity = 3),
      InventoryItem("Pear", quantity = 1),
      InventoryItem("Banana", quantity = 1)))

    val p = shoppingCart.priceAgainst(inventory)
    assert(p == 4.00)
  }

  "This shopping cart costs 4.00, but you can buy two apples for 1.00. It" should "cost 3.50." in {
    val inventory = Inventory(Seq(
      InventoryItem("Apple", price=0.75),
      InventoryItem("Pear", price=1.25),
      InventoryItem("Banana", price=0.50)))

    val shoppingCart = ShoppingCart(Seq(
      InventoryItem("Apple", quantity = 3),
      InventoryItem("Pear", quantity = 1),
      InventoryItem("Banana", quantity = 1)))

    val bundle = Bundle(discountedPrice=1.0, Seq(InventoryItem("Apple", 2)))

    val cache = Store(bundles = Set(bundle), inventory = inventory)
    assert(cache.bestPrice(shoppingCart) == 3.50)
  }


  "This shopping cart costs 4.00, but you can buy either two apples for 1.00 OR 3 apples for 1.50 (better deal!). It" should "cost $3.25" in {
    val inventory = Inventory(Seq(
      InventoryItem("Apple", price=0.75),
      InventoryItem("Pear", price=1.25),
      InventoryItem("Banana", price=0.50)))

    val shoppingCart = ShoppingCart(Seq(
      InventoryItem("Apple", quantity = 3),
      InventoryItem("Pear", quantity = 1),
      InventoryItem("Banana", quantity = 1)))

    val bundle1 = Bundle(discountedPrice=1.00, Seq(InventoryItem("Apple", 2)))
    val bundle2 = Bundle(discountedPrice=1.50, Seq(InventoryItem("Apple", 3)))

    val cache = Store(Set(bundle1, bundle2), inventory)
    assert(cache.bestPrice(shoppingCart) == 3.25)
  }


  "This shopping cart costs 4.00, but you can buy 10 apples for 1.00, but there aren't enough apples in the cart. It" should "cost $4.00" in {
    val inventory = Inventory(Seq(
      InventoryItem("Apple", price=0.75),
      InventoryItem("Pear", price=1.25),
      InventoryItem("Banana", price=0.50)))

    val shoppingCart = ShoppingCart(Seq(
      InventoryItem("Apple", quantity = 3),
      InventoryItem("Pear", quantity = 1),
      InventoryItem("Banana", quantity = 1)))

    val bundle1 = Bundle(discountedPrice=1.00, Seq(InventoryItem("Apple", 10)))

    val cache = Store(Set(bundle1), inventory)
    assert(cache.bestPrice(shoppingCart) == 4.00)
  }


  "This shopping cart costs 4.00, but you can buy the whole thing for $1.11! It" should "cost $1.11" in {
    val inventory = Inventory(Seq(
      InventoryItem("Apple", price=0.75),
      InventoryItem("Pear", price=1.25),
      InventoryItem("Banana", price=0.50)))

    val shoppingCart = ShoppingCart(Seq(
      InventoryItem("Apple", quantity = 3),
      InventoryItem("Pear", quantity = 1),
      InventoryItem("Banana", quantity = 1)))

    val bundle1 = new Bundle(discountedPrice=1.11, Seq(
      InventoryItem("Apple", 3),
      InventoryItem("Pear", 1),
      InventoryItem("Banana", 1)))

    val cache = Store(Set(bundle1), inventory)
    assert(cache.bestPrice(shoppingCart) == 1.11)
  }


  "This shopping cart costs 4.00. If you bought just one more banana, you could buy the whole thing for $2.00. But you didn't.It" should "cost $4.00" in {
    val inventory = Inventory(Seq(
      InventoryItem("Apple", price=0.75),
      InventoryItem("Pear", price=1.25),
      InventoryItem("Banana", price=0.50)))

    val shoppingCart = ShoppingCart(Seq(
      InventoryItem("Apple", quantity = 3),
      InventoryItem("Pear", quantity = 1),
      InventoryItem("Banana", quantity = 1)))

    val bundle1 = new Bundle(discountedPrice=2.00, Seq(
      InventoryItem("Apple", 3),
      InventoryItem("Pear", 1),
      InventoryItem("Banana", 2)))

    val cache = Store(Set(bundle1), inventory)
    assert(cache.bestPrice(shoppingCart) == 4.00)
  }


  "This shopping cart costs 4.00. But there two deals: 3 apples for $1.00 and a pear and a banana for $1.00 It" should "cost $2.00" in {
    val inventory = Inventory(Seq(
      InventoryItem("Apple", price=0.75),
      InventoryItem("Pear", price=1.25),
      InventoryItem("Banana", price=0.50)))

    val shoppingCart = ShoppingCart(Seq(
      InventoryItem("Apple", quantity = 3),
      InventoryItem("Pear", quantity = 1),
      InventoryItem("Banana", quantity = 1)))

    val bundle1 = new Bundle(discountedPrice=1.00, Seq(InventoryItem("Apple", 3)))
    val bundle2 = new Bundle(discountedPrice=1.00, Seq(InventoryItem("Pear", 1), InventoryItem("Banana", 1)))

    val cache = Store(Set(bundle1, bundle2), inventory)
    assert(cache.bestPrice(shoppingCart) == 2.00)
  }
}
