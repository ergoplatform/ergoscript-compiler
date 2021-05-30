package util

import kiosk.encoding.ScalaErgoConverters._
import kiosk.ergo._
import kiosk.script.ScriptUtil
import scorex.crypto.hash.Blake2b256
import sigmastate.Values

import scala.io.Source
import scala.collection.mutable.{Map => MMap}

object Compiler {

  /**
    * Used to compile ErgoScript code
    *
    * @param ergoScriptFile The file containing the ErgoScript source code
    * @param symbolsFile The file defining the symbols used in the ErgoScript source code
    * @return (1) The compiled ErgoTree, (2) The hash of the ErgoTree, and (3) The address
    */
  def compile(ergoScriptFile: String, symbolsFile: Option[String]): (String, String, String) = {
    val src: String = usingSource(Source.fromFile(ergoScriptFile))(_.mkString)

    val symbols = symbolsFile.map(loadSymbols).getOrElse(NoSymbols)
    val env = MMap[String, KioskType[_]]()

    symbols.symbols.foreach(symbol => ScriptUtil.addIfNotExist(env, symbol.name, symbol.getValue))

    val ergoTree: Values.ErgoTree = ScriptUtil.compile(env.toMap, src)
    val ergoTreeEncoded = ergoTreeToString(ergoTree)
    val hashEncoded = Blake2b256(ergoTree.bytes).encodeHex
    val address = getStringFromAddress(getAddressFromErgoTree(ergoTree))
    (ergoTreeEncoded, hashEncoded, address)
  }
}
