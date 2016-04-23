package com.bigrocksoftware.metarquery

import com.bigrocksoftware.metarparser.MetarParser
import com.bigrocksoftware.metarparser.Metar
import scala.collection.JavaConversions._
import org.joda.time.LocalDate
import org.joda.time.DateTimeConstants


object VFRDays {

  val months = Seq("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec")
  val years = Seq("2014")

  val airport = "EGLL"
  val startTimeUTC = 9
  val endTimeUTC = 16
  
  val maxWindSpeedKTS = 15
  val maxVisM = 5000
  val minCeiling = 2200

  def main(args: Array[String]) {
    val averages = months.map { m =>
      val yearStats = years.map { y => findPercentageFlyableDays("metartxts/"+airport+"/%s%s.txt".format(m, y)) }
      yearStats.foldLeft(0.0)((s, y) => s + y) / yearStats.size
    }
    averages.foreach { x =>
      println(x)
    }
  }

  def findPercentageFlyableDays(metarFile: String) = {
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

    val flyableReport = parsedMetars.collect {
      case FlyableReport(r) => r
    }

    val percentage = flyableReport.size.toDouble / parsedMetars.size.toDouble * 100.0

    println("%s: total reports %s, of which %s are vfr or %s percent".format(metarFile, parsedMetars.size, flyableReport.size,
      percentage))

    percentage
  }

  object FlyableReport {
    def unapply(report: Metar): Option[Metar] = {
      if (report.getWindSpeedInKnots() <= maxWindSpeedKTS &&
        report.getVisibilityInMeters() >= maxVisM &&
        report.getWindGustsInKnots() == null) {
        (report.getSkyConditions.toList, report.getWeatherConditions.toList) match {
          case (x, _) if (x.exists(y => y.getHeight() < minCeiling && !y.isFewClouds)) => None
          case (_, x) if (x.exists(!_.isLight)) => None
          case _ => Some(report)
        }
      } else None
    }
  }

}