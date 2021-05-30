{ // This box:
  // R4 the "control value" (such as the hash of a script of some other box)
  //
  // ballot boxes (data Inputs)
  // R4 the new control value
  // R5 the box id of this box

  val updateBoxIn = INPUTS(0)
  val updateBoxOut = OUTPUTS(0)
  val validIn = SELF.id == INPUTS(0).id

  val voteSuccessPath = {
    val newValue = updateBoxOut.R4[Coll[Byte]].get
    val oldValue = updateBoxIn.R4[Coll[Byte]].get

    val validOut = updateBoxOut.propositionBytes == updateBoxIn.propositionBytes &&
                   updateBoxOut.value >= minStorageRent &&
                   updateBoxOut.tokens == updateBoxIn.tokens &&
                   newValue != oldValue

    def validBallotSubmissionBox(b:Box) = b.tokens(0)._1 == ballotTokenId &&
                                          b.R4[Coll[Byte]].get == newValue && // ensure that vote is for the newValue
                                          b.R5[Coll[Byte]].get == SELF.id  // ensure that vote counts only once

    val ballots = CONTEXT.dataInputs.filter(validBallotSubmissionBox)

    val ballotCount = ballots.fold(0L, { (accum: Long, box: Box) => accum + box.tokens(0)._2 })

    val voteAccepted = ballotCount >= minVotes

    validOut && voteAccepted
  }

  val updatePath = {
    val bankBoxIn = INPUTS(1)
    val bankBoxOut = OUTPUTS(1)

    val storedNewHash = SELF.R4[Coll[Byte]].get
    val bankBoxOutHash = blake2b256(bankBoxOut.propositionBytes)

    val validBankBox = bankBoxIn.tokens(2)._1 == bankNFT && // bank box is first input
                       bankBoxIn.tokens == bankBoxOut.tokens &&
                       storedNewHash == bankBoxOutHash &&
                       bankBoxIn.propositionBytes != bankBoxOut.propositionBytes &&
                       bankBoxIn.R4[Long].get == bankBoxOut.R4[Long].get &&
                       bankBoxIn.R5[Long].get == bankBoxOut.R5[Long].get &&
                       bankBoxIn.value == bankBoxOut.value

    val validUpdateBox = updateBoxIn.R4[Coll[Byte]].get == updateBoxOut.R4[Coll[Byte]].get &&
                         updateBoxIn.propositionBytes == updateBoxOut.propositionBytes &&
                         updateBoxIn.tokens == updateBoxOut.tokens &&
                         updateBoxIn.value == updateBoxOut.value

    validBankBox &&
    validUpdateBox
  }

  sigmaProp(
    validIn && (
      voteSuccessPath ||
      updatePath
    )
  )
}
