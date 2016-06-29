package fi.piet

import org.scalatest._

class WordCounterSpec extends FlatSpec {

  "foo" should "print crap" in {
    val counter = new WordCounter()
    val text = counter.foobar()
    println(s"Text: $text")
  }
}
