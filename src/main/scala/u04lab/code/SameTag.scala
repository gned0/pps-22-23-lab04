package u04lab.code

import u04lab.code.List.*
import u04lab.code.Option.*
import u04lab.code.Item

import scala.annotation.tailrec

object sameTag:
  def unapply(items: List[Item]): scala.Option[String] = items match
    case Cons(head, tail) =>
      // predicate function to check if a tag is shared among all items
      @tailrec
      def isSharedTag(tag: String, items: List[Item]): Boolean = items match
        case Nil() => true
        case Cons(item, rest) => List.contains(item.tags, tag) && isSharedTag(tag, rest)

      List.find(head.tags)(tag => isSharedTag(tag, tail)) match
        case Some(tag) => scala.Some(tag)
        case None() => scala.None
    case _ => scala.None

@main def testExtractor(): Unit =
  val item1 = Item(1, "item1", "tag1", "tag2")
  val item2 = Item(2, "item2", "tag1", "tag3")
  val item3 = Item(3, "item3", "tag1", "tag4")
  val item4 = Item(4, "item4", "tag5", "tag6")

  val sameTagList = List(item1, item2, item3)
  sameTagList match
    case sameTag(t) => println(s"$sameTagList have same tag $t")
    case _ => println(s"$sameTagList have different tags")
  val differentTagList = List(item1, item2, item3, item4)
  differentTagList match
    case sameTag(t) => println(s"$differentTagList have same tag $t")
    case _ => println(s"$differentTagList have different tags")