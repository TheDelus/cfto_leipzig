package com.bigrocksoftware.metarquery

import com.bigrocksoftware.metarparser.MetarParser
import com.bigrocksoftware.metarparser.Metar
import scala.math._

object WindStrength {
  val months = Seq("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec")
  val years = Seq("2008", "2009", "2010", "2011", "2012")

  val airport = "LMML"
  val startTimeUTC = 8
  val endTimeUTC = 16

  def main(args: Array[String]) {
    val averages = months.map { m =>
      val yearStats = years.map { y => findWindStrength("metartxts/" + airport + "/%s%s.txt".format(m, y)) }
      yearStats.foldLeft(0.0)((s, y) => s + y) / yearStats.size
    }
    averages.foreach { x =>
      println(x)
    }
  }

  def findWindStrength(metarFile: String) = {
    val lines = scala.io.Source.fromFile(metarFile).getLines
    val onlyMetars = lines.collect {
      case x: String if (!x.contains("#") && !x.contains("AUTO") && !x.contains("TAF") && x.contains("METAR") && x.trim.size > 0) => x
    }

    val metarsWtDates = onlyMetars.foldLeft(List[String]()) { (endList, str) =>
      str match {
        case contLine if (contLine.head == ' ') =>
          val lastMetar = endList.last + " " + contLine.trim
          endList.dropRight(1) ++ List(lastMetar)
        case startLine => endList ++ List(startLine)
      }
    }

    val dayMetars = metarsWtDates.filter(x => x.substring(8, 10).toInt >= startTimeUTC && x.substring(8, 10).toInt < endTimeUTC)

    val metarsAndDates = dayMetars.map(x => (x.substring(0, 12), x.substring(12))).map {
      case (d, s) =>
        "%s/%s/%s %s:%s\n%s".format(
          d.substring(0, 4), d.substring(4, 6), d.substring(6, 8), d.substring(8, 10), d.substring(10),
          s.replace("METAR", "").replace("COR", "").trim)
    }

    val metarsAndDatesNoNils = metarsAndDates.filter(!_.contains("NIL")).map(_.replace("/////////", ""))

    val parsedMetars = metarsAndDatesNoNils.map { m =>
      val parsedReport = MetarParser.parse(m)
      parsedReport
    }

    val windSpeeds = parsedMetars.map{ m =>
      //println(m.getDate()+" "+m.getWindSpeedInKnots())
      m.getWindSpeedInKnots.toDouble
    }

    val average = windSpeeds.foldLeft(0.0)((t, x) => t + x) / windSpeeds.length

    average

  }

  def stdDev(list: List[Double], average: Double) = list.isEmpty match {
    case false =>
      val squared = list.foldLeft(0.0)(_ + squaredDifference(_, average))
      sqrt(squared / list.length.toDouble)
    case true => 0.0
  }

  def squaredDifference(x: Double, y: Double) = pow(x - y, 2.0)

}