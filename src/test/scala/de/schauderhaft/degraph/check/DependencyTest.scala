package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import Check._

@RunWith(classOf[JUnitRunner])
class DependencyTest extends FunSuite with ShouldMatchers {

    test("Degraph honors its constraints") {
        classpath.including("de.schauderhaft.**").
            withSlicing("part",
                "de.schauderhaft.*.(*).**").
                withSlicing("lib",
                    "de.schauderhaft.**(Test)",
                    ("main", "de.schauderhaft.(*).**"),
                    ("*.(*).**")).
                    withSlicing("internalExternal", ("internal", "de.schauderhaft.**"), ("external", "**")) should be(violationFree)
    }

    test("Check identifies cycles") {
        classpath.including("org.apache.log4j.**") should not be (violationFree)
    }

    test("Degraph has no cycles") {
        classpath.including("de.schauderhaft.**") should be(violationFree)
    }
}