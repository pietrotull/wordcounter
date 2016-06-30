package fi.piet

import java.io.InputStream

object WordCounter {

  val excludedFillerFors = Seq("and", "if", "the", "with", "in", "of", "for", "on", "to")

  def splitToLowerCaseWords(line: String): Seq[String] = {
    line.split("(,|\\s)")
      .map(word => word.toLowerCase())
      .map(word => word.replaceAll("[\\W]|_", ""))
      .filter(word => word.length > 1)
      .filter(word => !excludedFillerFors.contains(word))
  }

  def countWordsInSequence(words: Seq[String], accumulator: Map[String, Int] = Map.empty[String, Int]): Map[String, Int] = {
    words.foldLeft(accumulator)((acc, word) => {
      val newCount = acc.get(word).getOrElse(0) + 1
      acc + (word -> newCount)
    })
  }

  def readFile(fileName: String) = {
    val stream : InputStream = getClass.getResourceAsStream("/"+fileName)
    val lines = scala.io.Source.fromInputStream( stream ).getLines
    lines.map(line => splitToLowerCaseWords(line)).reduce(_++_)
  }

  def printTopWords(wordsFreq: Map[String, Int]) = {
    val wordsList: Seq[(String,Int)] = wordsFreq.toSeq.sortBy(_._2).reverse

    wordsList.foreach( wordStat => {
      val word = wordStat._1
      val count = wordStat._2
      if (count > 1) {
        println(s"$word: $count")
      }
    })
  }

  def main(args: Array[String]): Unit = {
    if (args.size >= 1) {
      println(s"Got file as param Hello, world! ${args.head}")
      val fileContent = readFile(args.head)
      val sumOfWords = WordCounter.countWordsInSequence(fileContent)
      WordCounter.printTopWords(sumOfWords)
    }
  }
}