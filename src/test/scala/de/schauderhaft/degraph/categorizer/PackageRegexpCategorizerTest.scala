package de.schauderhaft.degraph.categorizer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode

@RunWith(classOf[JUnitRunner])
class PackageRegexpCategorizerTest extends FunSuite with ShouldMatchers {

    val cat = PackageRegexpCategorizer.pattern("""de.schauderhaft\.(.*)\..*""".r)

    test("Pattern categorizer returns parameter for ") {
        cat("some String") should be("some String")
    }

    test("Pattern categorizer categorizes not matchin class node correctly") {
        val classNode = new ClassNode(new PackageNode("de.blah.test", true), "Class", true)
        cat(classNode) should be(classNode)
    }

    test("Pattern categorizer categorizes  matching class node with the matchgroup") {
        val classNode = new ClassNode(new PackageNode("de.schauderhaft.module.test", true), "Class", true)
        cat(classNode) should be("module")
    }
}