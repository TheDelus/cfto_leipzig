package com.bigrocksoftware.metarquery

import com.bigrocksoftware.metarparser.MetarParser
import com.bigrocksoftware.metarparser.Metar
import scala.collection.JavaConversions._

object VFRLength {

  val months = Seq("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec")
  val years = Seq("2008", "2009", "2010", "2011", "2012")

  val airport = "LPPR"
  val startTimeUTC = 8
  val endTimeUTC = 16

  val maxWindSpeedKTS = 15
  val maxVisM = 8000
  val minCeiling = 2000

  def main(args: Array[String]) {
    val fileNames = months.flatMap { m =>
      years.map { y => "metartxts/"+airport+"/%s%s.txt".format(m, y) }
    }
    findAvgLengthFlyableDays(fileNames.toList)
  }

  def findAvgLengthFlyableDays(metarFiles: List[String]) = {
    val lines = metarFiles.flatMap(scala.io.Source.fromFile(_).getLines)
    println("Loaded all files")
    val onlyMetars = lines.collect {
      case x: String if (!x.contains("#") && !x.contains("AUTO") && !x.contains("TAF") && x.contains("METAR") && x.trim.size > 0) => x
    }

    val metarsWtDates = onlyMetars.foldLeft(List[String]()) { (endList, str) =>
      str match {
        case contLine if (contLine.head == ' ') =>
          val lastMetar = endList.head + " " + contLine.trim
          lastMetar :: endList.drop(1)
        case startLine => startLine :: endList
      }
    }

    println("Merged two line meters")

    val dayMetars = metarsWtDates.filter(x => x.substring(8, 10).toInt >= startTimeUTC && x.substring(8, 10).toInt < endTimeUTC)

    println("Filtered all meters outside time")

    val metarsAndDates = dayMetars.map(x => (x.substring(0, 12), x.substring(12))).map {
      case (d, s) =>
        "%s/%s/%s %s:%s\n%s".format(
          d.substring(0, 4), d.substring(4, 6), d.substring(6, 8), d.substring(8, 10), d.substring(10),
          s.replace("METAR", "").replace("COR", "").trim)
    }

    val metarsAndDatesNoNils = metarsAndDates.filter(!_.contains("NIL")).map(_.replace("/////////", ""))

    println("Filtered all meters with Nils")

    val parsedMetars = metarsAndDatesNoNils.map { m =>
      val parsedReport = MetarParser.parse(m)
      parsedReport
    }

    println("Parsed all meters")

    val sortedByDateMetars = parsedMetars.sortBy(_.getDate.getTime)

    println("Sorted all meters")

    val goodWeatherPeriods = sortedByDateMetars.foldLeft(List[Int](0)){ case (periods, metar) =>
      metar match {
        case FlyableReport(m) =>
          val t = periods.head + 1
          t :: (periods.drop(1))
        case _ if (periods.head > 0) => 0 :: periods
        case _ => periods
      }
    }

    val goodWeatherPeriodsClean = if (goodWeatherPeriods.head == 0) goodWeatherPeriods.drop(1) else goodWeatherPeriods

    println(goodWeatherPeriodsClean.sum / goodWeatherPeriodsClean.size)
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