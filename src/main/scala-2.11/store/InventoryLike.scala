package store

/**
  * An InventoryItem represents an item that is held in an InventoryLike object
  * @param description Name of the item
  * @param quantity Quantity of items
  * @param price Price per item
  */
case class InventoryItem(description: String, quantity: Int = 1, price: Double = 0.0) {
  def -(qty: Int) = InventoryItem(description, quantity=(quantity - qty).max(0), price=price)
  def totalPrice = price * quantity
}


/**
  * InventoryLike is a trait representing thing that behave like an inventory of items
  */
trait InventoryLike {
  val items: Iterable[InventoryItem]
  lazy val itemsMap: Map[String, InventoryItem] = items.map((i) => i.description -> i).toMap

  def contains(description: String): Boolean = itemsMap.contains(description)
  def size: Int = itemsMap.size
  def get(description: String): Option[InventoryItem] = itemsMap.get(description)

  def filter(p: (InventoryItem) => Boolean): Iterable[InventoryItem] = items.filter(p)
  def filterNot(p: (InventoryItem) => Boolean): Iterable[InventoryItem] = items.filterNot(p)

  def totalPrice: Double = items.foldLeft(0.0)(_ + _.totalPrice)
}


/**
  * An Inventory is a collection of items

  * @param items The items in the inventory
  */
case class Inventory(override val items: Iterable[InventoryItem]) extends InventoryLike


/**
  * A Bundle is a collection of items, with a discounted price.
  * @param discountedPrice The price for purchasing all items in the bundle
  * @param items The items in the bundle
  */
case class Bundle(discountedPrice: Double, override val items: Iterable[InventoryItem]) extends InventoryLike