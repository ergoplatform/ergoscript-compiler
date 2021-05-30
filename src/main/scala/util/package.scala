import kiosk.ergo
import kiosk.ergo.{DataType, usingSource}
import play.api.libs.json.Json

import scala.io.Source

package object util {

  import Parser._

  case class Symbol(name: String, var `type`: DataType.Type, value: String) {
    def getValue: ergo.KioskType[_] = DataType.getValue(value, `type`)
  }

  case class Symbols(symbols: Seq[Symbol])

  val NoSymbols = Symbols(Nil)

  def loadSymbols(symbolsFile: String): Symbols =
    Json.parse(usingSource(Source.fromFile(symbolsFile))(_.mkString)).as[Symbols]

  case class Token(tokenId: String, amount: Long)

  case class PaymentRequest(address: String, value: Long, assets: Seq[Token], registers: Map[String, String])

  def loadPaymentRequest(paymentRequestFile: String): Seq[PaymentRequest] =
    Json.parse(usingSource(Source.fromFile(paymentRequestFile))(_.mkString)).as[Seq[PaymentRequest]]
}
