package info.unterstein.akka.persistence.api

import com.google.gson._
import java.lang.reflect.Type
import scala.collection.JavaConverters._

/**
  * @author Johannes Unterstein (unterstein@me.com)
  */
case class PersistentActorMessage(messageType: String, scheduleDate: Option[Long] = Some(System.currentTimeMillis()), originalMessage: Map[String, String]) {

  def toJson: String = PersistentActorMessage.gson.toJson(this)
}

object PersistentActorMessage {

  private val gson = new GsonBuilder()
    .registerTypeAdapter(classOf[Map[String, String]], new MapSerializer())
    .create()

  private class MapSerializer extends JsonSerializer[Map[String, String]] {

    override def serialize(src: Map[String, String], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      gson.toJsonTree(src.asJava)
    }
  }

  def ofJson(json: String): PersistentActorMessage = gson.fromJson(json, classOf[PersistentActorMessage])
}
