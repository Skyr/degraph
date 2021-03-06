package de.schauderhaft.degraph.app

import org.rogach.scallop._

/**
 * parses the command line arguments for Degraph
 */
object CommandLineParser {

    def parse(args: Seq[String]) = {
        new LazyScallopConf(args) {

            version("Degraph Version 0.1.0")
            banner("""Degraph analyses class and jar files and creates graphml documents out of it
for visualizing dependencies.""")
            footer("""See https://github.com/schauder/degraph for more information""")

            val output = opt[String]("output",
                default = Some("output.graphml"),
                descr = "the file name of the output file")
            val classpath = opt[String]("classpath",
                default = Some("."),
                descr = "the classpath where Degraph looks for classfiles to analyze")
            val excludeFilter = opt[List[String]]("excludeFilter",
                default = Some(List()),
                descr = "if this argument is given those nodes that are matched by the given regular expression will get excluded from the graph.")
            val includeFilter = opt[List[String]]("includeFilter",
                default = Some(List()),
                descr = "if this argument is given, only those nodes get included in the resulting graph, that match the given regular expression.")
            val file = opt[String]("file", default = None)
            val display = toggle("display", default = Some(false))

        }

    }
}