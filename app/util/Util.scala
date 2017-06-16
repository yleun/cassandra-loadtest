package util

import scala.language.implicitConversions
import java.time.ZonedDateTime
import java.util.TimeZone
import java.util.Date
import java.text.SimpleDateFormat

import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.{DateTime, DateTimeZone}

object Util {

  val fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    .withZone(DateTimeZone.forID("UTC"))

  val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

  sdf.setTimeZone(TimeZone.getTimeZone("UTC"))

  implicit def toZonedDateTime(d: DateTime): ZonedDateTime = {
    val cal = d.toGregorianCalendar
    cal.setTimeZone(TimeZone.getDefault)
    cal.toZonedDateTime
  }

  implicit def toDateTime(zdt: ZonedDateTime): DateTime =
    new DateTime(
      zdt.toInstant.toEpochMilli,
      DateTimeZone.forTimeZone(TimeZone.getTimeZone(zdt.getZone))
    )
}
