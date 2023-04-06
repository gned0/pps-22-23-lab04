package u04lab.code
import List.*
import u04lab.code
trait Item {
  def code: Int
  def name: String
  def tags: List[String]
}

object Item:
  def apply(code: Int, name: String, tags: String*): Item =
    var tagsList: List[String] = List.empty
    for e <- tags do tagsList = Cons(e, tagsList)
    ItemImpl(code, name, tagsList)
  private case class ItemImpl(override val code: Int,
                              override val name: String,
                              override val tags: List[String]) extends Item

/**
 * A warehouse is a place where items are stored.
 */
trait Warehouse {
  /**
   * Stores an item in the warehouse.
   * @param item the item to store
   */
  def store(item: Item): Unit
  /**
   * Searches for items with the given tag.
   * @param tag the tag to search for
   * @return the list of items with the given tag
   */
  def searchItems(tag: String): List[Item]
  /**
   * Retrieves an item from the warehouse.
   * @param code the code of the item to retrieve
   * @return the item with the given code, if present
   */
  def retrieve(code: Int): Option[Item]
  /**
   * Removes an item from the warehouse.
   * @param item the item to remove
   */
  def remove(item: Item): Unit
  /**
   * Checks if the warehouse contains an item with the given code.
   * @param itemCode the code of the item to check
   * @return true if the warehouse contains an item with the given code, false otherwise
   */
  def contains(itemCode: Int): Boolean
}

object Warehouse {
  def apply(): Warehouse =
    WarehouseImpl()
  private case class WarehouseImpl() extends Warehouse:
    private var _itemList: u04lab.code.List[Item] = Nil()
    def store(item: Item): Unit =
      this._itemList = Cons(item, this._itemList)
    def contains(itemCode: Int): Boolean =
      List.contains(map(_itemList)(_.code), itemCode)
    def searchItems(tag: String): List[Item] =
      List.filter(_itemList)(i => List.contains(i.tags, tag))
    def retrieve(code: Int): Option[Item] =
      List.find(_itemList)(_.code == code)
    def remove(item: Item): Unit =
      this._itemList = List.remove(this._itemList)(i => i == item)
}

@main def mainWarehouse(): Unit =
  val warehouse = Warehouse()

  val dellXps = Item(33, "Dell XPS 15", "notebook")
  val dellInspiron = Item(34, "Dell Inspiron 13", "notebook")
  val xiaomiMoped = Item(35, "Xiaomi S1", "moped", "mobility")

  println(warehouse.contains(dellXps.code)) // false
  warehouse.store(dellXps) // side effect, add dell xps to the warehouse
  println(warehouse.contains(dellXps.code)) // true
  warehouse.store(dellInspiron) // side effect, add dell inspiron to the warehouse
  warehouse.store(xiaomiMoped) // side effect, add xiaomi moped to the warehouse
  println(warehouse.searchItems("mobility")) // List(xiaomiMoped)
  println(warehouse.searchItems("notebook")) // List(dellXps, dellInspiron)
  println(warehouse.retrieve(11)) // None
  println(warehouse.retrieve(dellXps.code)) // Some(dellXps)
  warehouse.remove(dellXps) // side effect, remove dell xps from the warehouse
  println(warehouse.retrieve(dellXps.code)) // None

/** Hints:
 * - Implement the Item with a simple case class
 * - Implement the Warehouse keeping a private List of items
 * - Start implementing contains and store
 * - Implement searchItems using filter and contains
 * - Implement retrieve using find
 * - Implement remove using filter
 * - Refactor the code of Item accepting a variable number of tags (hint: use _*)
*/