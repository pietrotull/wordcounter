package fi.piet

import java.io.InputStream

object WordCounter {
  def printComparisonResults(wordFreqWithComparison: Map[String, (Int, Int)]): Unit = {
    val wordsList: Seq[(String,(Int, Int))] = wordFreqWithComparison.toSeq.sortBy(_._2._1).reverse
    wordsList.foreach( wordFreqIten => {
      val word = wordFreqIten._1
      val currentCount = wordFreqIten._2._1
      val prevYearDiff = wordFreqIten._2._2
      println(s"$word: $currentCount ($prevYearDiff)")
    })
  }


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

  def compareCounts(wordFreq1: Map[String, Int], wordsFreq2: Map[String, Int]): Map[String, (Int, Int)] = {
    wordFreq1.map( wordFreq => {
      val oldCount = wordsFreq2.get(wordFreq._1).getOrElse(0)
      (wordFreq._1, (wordFreq._2, wordFreq._2 - oldCount))
    })
  }

  def main(args: Array[String]): Unit = {
    if (args.size == 1) {
      val fileContent = readFile(args.head)
      val sumOfWords = WordCounter.countWordsInSequence(fileContent)
      WordCounter.printTopWords(sumOfWords)
    }

    if (args.size == 2) {
      val file1Content = readFile(args.head)
      val file2Content = readFile(args.last)
      val sumOf1Words = WordCounter.countWordsInSequence(file1Content)
      val sumOf2Words = WordCounter.countWordsInSequence(file2Content)
      val trends = compareCounts(sumOf1Words, sumOf2Words)
      WordCounter.printComparisonResults(trends)
    }
  }
}