import util.{Compiler, CreatePayment}

import scala.util.Try

object Payment {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) println("Usage java -cp <jar> Payment <request_file> <optional_symbols_file>")
    else println(CreatePayment.create(args(0), Try(args(1)).toOption))
  }
}
