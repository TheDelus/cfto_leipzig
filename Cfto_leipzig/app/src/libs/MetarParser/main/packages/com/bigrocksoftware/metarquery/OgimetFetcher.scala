package com.bigrocksoftware.metarquery

import java.io._

object OgimetFetcher {

  def main(args: Array[String]) {
    val airportCode = "EGLL"
    val startYear = 2014;
    val endYear = 2014;

    val years = startYear until endYear + 1
    val months = 1 until 12 + 1
    val monthsStr = Array[String]("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec")

    val filenameToUrls = years.flatMap { y =>
      months.zip(monthsStr).map {
        case (m, mStr) =>
          val filename = s"metartxts/$airportCode/$mStr$y.txt"
          val urlStr = s"http://www.ogimet.com/display_metars2.php?lang=en&lugar=$airportCode&tipo=SA&ord=REV&nil=NO&fmt=txt&ano=$y&mes=$m&day=01&hora=00&anof=$y&mesf=$m&dayf=31&horaf=23&minf=59&send=send"
          (filename, urlStr)
      }
    }
    filenameToUrls.foreach {
      case (filename, url) =>
        val html = io.Source.fromURL(url)
        val metars = html.getLines.dropWhile(!_.startsWith("#")).toList
        println(filename)
        println(metars.headOption)
        printToFile(new File(filename)) { p =>
          metars.foreach(p.println)
        }
        Thread.sleep(180000)
    }
  }

  def printToFile(f: File)(op: PrintWriter => Unit) {
    val p = new PrintWriter(f)
    try { op(p) } finally { p.close() }
  }

}