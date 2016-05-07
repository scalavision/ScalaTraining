package json

object Test extends App {

  import JsonSyntax._
  import DefaultJsonWriters._

  val p = Person("Nils", "simula@oslo.com")
  val d = Dog("Maxim", "maxin@oslo.com")
  println(p)
  println(p.write + " " + d.write)

  val l:List[Earth] = List(p,d)

  val listJson = l.write
  println(listJson)

}
