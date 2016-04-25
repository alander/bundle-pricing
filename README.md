# Bundle Pricing

## Problem Statement

This exercise is a common problem in e-commerce and brick-and-mortar retail systems. 

 A customer shops from some form of catalog, selecting items and quantities they wish to purchase.  When they are ready, they “check out”, that is, complete their purchase, at which point they are given a total amount of money they owe for the purchase of a specific set of items.  In the bounds of this problem, certain groups of items can be taken together as a “bundle” with a different price.  E.g.  if I buy single apple in isolation it costs $1.99, if I buy two apples it’s $2.15.  More complex combinations are possible – e.g. a loaf of bread “A” purchased with two sticks of margarine “B” and the second stick of margarine is free (e.g. $0).
 
The same item may appear in more than one bundle, e.g. any one “cart” of items might be able to be combined in more than one way.  For this exercise, produce an API and implementation for a service that accepts a collection of items the customer wishes to purchase (e.g. items and quantities), and produces the lowest possible price for that collection of items.  The API is to be called by other applications in the same JVM, e.g. don’t worry about providing a REST or other remote interface to the API, just the actual method calls is fine.  The API is initialized with information that provides all possible bundles and the catalog of items and their prices.  Once it is initialized, many calls can be made at once to the API to produce a total price for collections of items, and it should be able to handle multiple simultaneous calls without errors (e.g. if I’m computing the price for one call, that should not interfere with computing the price for another call).  Use what you would consider good Scala style.  

We are not expecting conformance to any specific coding standard for this exercise, although you should be consistence throughout the exercise.

Please return the source code to your solution and any additional code you think is required to demonstrate its correctness under the specification provided.  You can provide the code via any convenient means, e.g. zip file, link to a readable code repository, etc.

## Structure of the Solution

The solution is implemented using the following classes and traits:

### case class Store

The store holds the inventory of items for sale, and all possible bundles that can be applied to a shopping cart.

The store can answer what is the best price for a shopping cart, considering all possible bundles that can be applied to it.

### case class ShoppingCart

A shopping cart represents a group of items that are going to be priced and purchased.

A shopping cart can price itself against an Inventory (without considering bundles), and can apply a bundle, and answer a new shopping cart which removes those items applied to the bundle.

### case class InventoryItem

An inventory item describes an item which can be purchased, its price, and the quantity of items

### trait InventoryLike

InventoryLike provides the behaviour for things that represent an group of items. Its concrete implementations are Inventory and Bundle.

An InventoryLike can answer questions about the items it contains, such as whether a particular item exists (by name); How many items there are; The total price of all items; and it can paritition items based on a predicate function (filter and filterNot methods)


### case class Inventory

Inventory extends InventoryLike, and represents a concrete group of items with prices

### case class Bundle

Bundle extends InventoryLike, and represents a concrete group of items available for a discounted "bundle" price.

## Bundle Algorithm

Finding the best combination of bundles to apply to a shopping cart is factorial in the number of bundles, as all possible combinations must be tried to find the best price. The code, as implemented, performs an exhaustive search of the bundle space, and is fine for a small number of bundles (tens, say). A larger number of bundles would require different treatments. 

One option would be to study buying behaviour and apply the *a priori* or *fp growth* algorithms to learn a set of shopping baskets of items frquently bought together, and precompute the best bundles for those baskets. That way a plurality of shoppers could enjoy a very speedy checkout. 

Another option is to find a way to do the factorial work in advance and cache (memoize) the results in a fast lookup structure which can give answers in linear or log-linear time.

Still another possiblity is to take advantage of distributed computing using map/reduce or Spark, and spread the computation over several computers. The problem appears to be *embarassingly parallel*, at least on first blush, and looks like it might lend itself well to this type of distributed parallel processing.


## Tests

A suite of tests implemented using ScalaTest is provided in the tests subdirectory.