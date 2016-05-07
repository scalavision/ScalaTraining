package json

object Test extends App {

  import JsonSyntax._
  import DefaultJsonWriters._

  val p = Person("John", "john@doe.com")
  val d = Pet("Maxim", 8)
  println(p)
  println(p.write + " " + d.write)

  val l:List[Earth] = List(p,d)

  val listJson = l.write
  println(listJson)

}
