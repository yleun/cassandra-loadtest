package models

import com.outworkers.util.samplers.Sample
import db.model.GroupId
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Assertion, FlatSpec, MustMatchers, OptionValues}
import play.api.libs.json.{Format, Json}
import db.model.JsonProtocol._

class GroupIdTest extends FlatSpec with GeneratorDrivenPropertyChecks with OptionValues with MustMatchers {

  def jsonTest[T : Format : Sample]: Assertion = {
    forAll(Sample.generator[T]) { value =>
      val json = Json.toJson(value)
      json.validate[T].asOpt.value mustEqual value
    }
  }

  "An GroupId" should "be converted to Json" in {
    jsonTest[GroupId]
  }

}
