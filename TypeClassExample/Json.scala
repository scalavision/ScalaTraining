package json

trait Json

final case class JsArray(list:List[Json]) extends Json
final case class JsObject(obj: Map[String, Json]) extends Json
final case class JsString(value:String) extends Json
final case class JsNumber(value:Double) extends Json
final case object JsEmpty extends Json

trait JsonWriter[A] {
  def write(json:A):Json
}

sealed trait Earth
final case class Person(name:String, email:String) extends Earth
final case class Pet(name:String, age:Int) extends Earth

object DefaultJsonWriters {

  implicit val stringJsonWriter = new JsonWriter[String] {
    override def write(s:String):Json = JsString(s)
  }

  implicit val personJsonWriter = new JsonWriter[Person] {
    override def write(person:Person):Json =
      JsObject(
        Map(
          "name" -> JsString(person.name),
          "email" -> JsString(person.email)
        )
      )
  }

  implicit val petJsonWriter = new JsonWriter[Pet] {
    override def write(pet:Pet):Json =
      JsObject(
        Map(
          "name" -> JsString(pet.name),
          "age" -> JsNumber(pet.age.toDouble)
        )
      )
  }

  implicit val earthWriter = new JsonWriter[Earth] {
    override def write(e:Earth):Json =
      e match {
        case p @ Person(_,_) => personJsonWriter.write(p)
        case p @ Pet(_,_) => petJsonWriter.write(p)
      }
  }

  implicit val listWriter = new JsonWriter[List[Earth]] {
    override def write(e:List[Earth]):Json =
      JsArray(e.map {
        e =>
          earthWriter.write(e)
      })
  }
}

object JsonSyntax {
  import DefaultJsonWriters._

  implicit class PersonWriter(val p:Person) extends AnyVal {
      def write(implicit writer:JsonWriter[Person]):Json =
        writer.write(p)
  }

  implicit class PetWriter(val d:Pet) extends AnyVal {
    def write(implicit writer:JsonWriter[Pet]):Json =
      writer.write(d)
  }

  implicit class EarthWriter(val e:Earth) extends AnyVal {
    def write(implicit writer:JsonWriter[Earth]):Json =
      writer.write(e)
  }

  implicit class ListWriter(val l:List[Earth]) extends AnyVal {
    def write(implicit writer:JsonWriter[List[Earth]]):Json =
      writer.write(l)
  }

}
