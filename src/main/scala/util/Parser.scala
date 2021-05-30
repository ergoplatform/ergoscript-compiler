package util

import kiosk.ergo.DataType
import play.api.libs.json.{JsResult, JsSuccess, JsValue, Json, Reads}

object Parser {
  implicit val readsDataType = new Reads[DataType.Type] {
    override def reads(json: JsValue): JsResult[DataType.Type] = JsSuccess(DataType.fromString(json.as[String]))
  }
  implicit val readsSymbol = Json.reads[Symbol]
  implicit val readsSymbols = Json.reads[Symbols]

  implicit val readsToken = Json.reads[Token]
  implicit val writesToken = Json.writes[Token]
  implicit val readsPaymentRequest = Json.reads[PaymentRequest]
  implicit val writesPaymentRequest = Json.writes[PaymentRequest]
}
