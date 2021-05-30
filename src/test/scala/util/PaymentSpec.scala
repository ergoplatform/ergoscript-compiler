package util

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

class PaymentSpec extends WordSpec with Matchers {
  val expected =
    """[
      |   {
      |      "address":"29irJ65SHH5VxgQaXubC1z9eHzutUWV6BB2QGCbA9eQ53msbyzXdh6bSXm64WMwkiBNRgeXZy1ySSvVuKtEE4ifUbdXiV4PtdqsdxWxrhmJoqnDHSimsCz2zLsUGFm9j16kivMoS3ek4JPzvxuLddru5DLGaLJaLqN3dSjrY398Puyd2SxaxmUNpLy4PscVv8Gs7x4TYdBffnb9CvTZekitcwgu8hqgzU9yFHo37czxDJSg95gMyevKUivy7wfoxinoCdpKcN8WnHQ8jXA6kVxt2n55EAjJpaP6RkXRdszWAxZGFYHGvaNrRoFzThkpddgtm23P2ayQAYcUyTky8LtAgep1DpPueLWw25tN3hebEkfBy5K95vThXk5ChCBrxKzi6jFtWARdJ7Gm3F61L44jjhBn4qCFizqyJJ62axxhSv8sPnMXu8Je4sSFo9UBCheNoobYDyteatm8RpKLR6vDiKyZSyhKBYgcccNfCfhHHj8N6qr1gYueVvkb6SMxQpFbtHV8pqvdUUKhThNMVRkeBQz6ZeuPdnXEXBU5HZsJbT4oN51T5jUEfF5FfHWHuX9bnC9wfLzx7z7fehiSNdfb1XSG1UDfgs2sDZD6G6TJ5TQev8s5nXtUaJoz3xddnfxLnYDFjJqZnnhQSqmtndBkPZGHgRmt2fY4UxF2AqZ7E6RrK3d6QtaXkYAyLke7GrDTbUgAGXUtizy3tEhAtK3dy46oCVMdkqA2go4iXZ1gFVeuzxGPAvEUW8gRsHHSyP3hAeA7n1tbS9qRajCFB19YLnSd8UPRzkBU4e4NdAneZqZ5TqK9T34eusMLqqriJ1Qp7oAVcG2tbtik82nzX8WhctoXFGQJh1UAeJKFpQ22YCUX7vR17BwehSjpRwc5EK5ZqtCnGpUsfmGY6eyc1txwGRT1AS2m9NYweAmGh7Eq8VZbaXtbJUAhdLrBD8GpVnYjCzZiboZxyokbjFqmifFLYVkRXoZLG7CzqSEBhgtiew",
      |      "value":1000000,
      |      "assets":[
      |         {
      |            "tokenId":"a908bf2be7e199014b45e421dc4adb846d8de95e37da87c7f97ac6fb8e863fa2",
      |            "amount":10000000000000
      |         },
      |         {
      |            "tokenId":"b240daba6b5f9f9b6d4e6d7fc8b7c0423f1dfa28a883ec626a18b69be6c7590e",
      |            "amount":10000000000000
      |         },
      |         {
      |            "tokenId":"7bd873b8a886daa7a8bfacdad11d36aeee36c248aaf5779bcd8d41a13e4c1604",
      |            "amount":1
      |         }
      |      ],
      |      "registers":{
      |         "R4": "0500",
      |         "R5": "0500"
      |      }
      |   },
      |   {
      |     "address": "6Vs43fLottAzin3EiEiswbSD31ETscqBLy9i3zTWCwUVuG79fWuP7S3Kko5PEK56UEBWSTE8GuuXq3gMfYSUNJ1ujSTy5yKzrAHRGLk5rw1UTecdQZfPJTdpxMvkEvG9TYm4ie8E3z1MMfTJ3wzPxgQ2fhHzxaCLZAdNPF3Ji56VktsB4kQZsadxQDbYjwXWAxypA1YkRzLujhmPZELJR6EiRz2pdR15iKPNJh6pDo3CFqb9eySCbHk1EgwPo8C6nvHpqyb2c6bKkW2p4vQL2BudZhq6CZPToCGd376EZy3mgawmeWePrvFCrCDsrnEccfkr3PhcvtRqHzJD7m2oTFfmyiWvD7NakGh3woNU8XEV4uKULieaHWbs21M3mDe4CZ7Vg477GeWkqc8jaP4LGjfuEM5cqpNyct8CYrYoJCDB18RhWqtCL2ddEBwFCXRiFujrKQjSbncSJ2xd4BkmBcHrjBzskMAat1N6A72ZN8KNYsWBQBRLX4VoJwVTvtaSFAuCSBaDtoq1uwVG",
      |     "value": 1000000,
      |     "assets": [
      |       {
      |          "tokenId": "77d14a018507949d1a88a631f76663e8e5101f57305dd5ebd319a41028d80456",
      |          "amount": 1
      |       }
      |     ],
      |     "registers": {
      |       "R4": "0e0601a2b3c4d5e6"
      |     }
      |   }
      |]""".stripMargin

  "CreatePayment" should {
    "create PaymentRequest correctly" in {
      val paymentRequest = CreatePayment.create("src/test/resources/payment_request_AgeUSD.json", Some("src/test/resources/payment_request_AgeUSD_symbols.json"))
      val actualJson = Json.parse(paymentRequest)
      val expectedJson = Json.parse(expected)
      actualJson shouldBe expectedJson
    }
  }
}
