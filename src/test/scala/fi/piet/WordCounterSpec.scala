package fi.piet

import org.scalatest._

class WordCounterSpec extends FlatSpec with ShouldMatchers {

  "split line" should "turn a phrase into seq of words" in {
    val source = "this is a phrase"
    val result = WordCounter.splitToLowerCaseWords(source)
    result.length should equal (3)
    result.head should equal("this")
  }

  "split line" should "split based only on space or comma" in {
    val source = "this.is ,a.phrase"
    val result = WordCounter.splitToLowerCaseWords(source)
    result.length should equal (2)
    result.head should equal("thisis")
  }

  "split line" should "return lowercase version of the word" in {
    val source = "tHis .is,a.phrase"
    val result = WordCounter.splitToLowerCaseWords(source)
    result.length should equal (3)
    result.head should equal("this")
  }

  "count words" should "return counts for simple seq" in {
    val source: Seq[String] = Seq("this", "word", "viddu", "this")
    val result = WordCounter.countWordsInSequence(source)

    result.get("word").get should equal (1)
    result.get("this").get should equal (2)
  }

  "count words" should "use accumulated counts" in {
    val source: Seq[String] = Seq("this", "word", "viddu", "this")
    val accu: Map[String, Int] = Map("this"-> 100, "viddu" -> 200)
    val result = WordCounter.countWordsInSequence(source, accu)

    result.get("word").get should equal (1)
    result.get("viddu").get should equal (201)
    result.get("this").get should equal (102)
  }

  "read file" should "read file content" in {
    val allWords = WordCounter.readFile()
    val sumOfWords = WordCounter.countWordsInSequence(allWords)
    WordCounter.printTopWords(sumOfWords)
  }
}
