package util

import play.api.libs.json.Json

import Parser._

object CreatePayment {

  /**
    * Generates a payment request using the supplied params
    *
    * @param requestFile The file containing the payment request JSON
    * @param symbolsFile The file containing the symbols used in the request
    * @return The payment request encoded for an Ergo node
    */
  def create(requestFile: String, symbolsFile: Option[String]): String = {
    val origPaymentRequest = loadPaymentRequest(requestFile)
    val symbols = symbolsFile.map(loadSymbols).getOrElse(NoSymbols).symbols
    def encode(regsterName: String) = symbols.find(symbol => symbol.name == regsterName).getOrElse(throw new Exception(s"No symbol found with name $regsterName")).getValue.hex
    val newPpaymentRequests = origPaymentRequest.map(paymentRequest => paymentRequest.copy(registers = paymentRequest.registers.map { case (key, value) => key -> encode(value) }))
    Json.prettyPrint(Json.toJson(newPpaymentRequests))
  }

}
