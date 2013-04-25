package de.schauderhaft.degraph

import _root_.java.awt.Color.RED
import scala.xml.XML
import de.schauderhaft.degraph.analysis.dependencyFinder.Analyzer
import de.schauderhaft.degraph.configuration.Configuration
import de.schauderhaft.degraph.writer.DefaultEdgeStyle
import de.schauderhaft.degraph.writer.EdgeStyle
import de.schauderhaft.degraph.writer.PredicateStyler
import de.schauderhaft.degraph.writer.Writer
import de.schauderhaft.degraph.writer.SlicePredicate
import de.schauderhaft.degraph.java.JavaGraph

/**
 * The main class of Degraph, plugging everything together,
 * starting the analysis process and writing the result to an XML file
 */
object Degraph {

    def main(args: Array[String]): Unit = {
        Configuration(args) match {
            case Left(m) => println(m)
            case Right(c) =>
                val g = c.copy(analyzer = Analyzer).createGraph()
                val violations = c.constraint.
                    flatMap(_.violations(g)).
                    flatMap(_.dependencies);
                if (c.display) {
                    println("Sorry no GUI available")
                } else {
                    val styler = PredicateStyler.styler(new SlicePredicate(c.slicing, violations), EdgeStyle(RED, 2.0), DefaultEdgeStyle)
                    val xml = (new Writer(styler)).toXml(g)
                    XML.save(c.output.get, xml, "UTF8", true, null)
                }
                println("Found %d nodes, with %d slice edges in violation of dependency constraints.".format(g.allNodes.size, violations.size))
        }
    }

}