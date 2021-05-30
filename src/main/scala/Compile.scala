import util.Compiler

import scala.util.Try

object Compile {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) println("Usage java -cp <jar> Compiler <ergoScript_file> <optional_symbols_file>")
    else {
      val (ergoTree, hash, address) = Compiler.compile(args(0), Try(args(1)).toOption)
      Seq("ErgoTree:" -> ergoTree, "Blake2b256:" -> hash, "Address:" -> address) foreach {
        case (key, value) =>
          println(key)
          println(value)
          println
      }
    }
  }

}
