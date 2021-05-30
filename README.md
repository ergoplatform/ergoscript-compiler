# ErgoScript Compiler

[![Build Status](https://www.travis-ci.com/scalahub/ErgoScriptCompiler.svg?branch=main)](https://www.travis-ci.com/scalahub/ErgoScriptCompiler)

A CLI tool for compiling ErgoScript code to an Ergo address.

## How To Use

1. Compile the ErgoScript compiler. If using the precompiled jar, skip to Step 2.
    - Clone the repository using `git clone https://github.com/scalahub/ErgoScriptCompiler.git`.
    - Ensure SBT is installed and set in path.
    - Compile the jar using `sbt assembly` in the project root folder. 
 
      If everything goes well, a new JAR file will be created at the following path: 
      
      `target/scala-2.12/ErgoScriptCompiler-assembly-0.1.jar`
      
      In the following, `<jarFile>` refers to the above JAR.

2. Compile your ErgoScript code:
    - Put your ErgoScript code in a text file named, say, `myScript.es` (the extension can be anything)
    - Put any symbols (constants) in another file named, say, `mySymbols.json`. See below on how to write this file.
    - The symbols file is optional and is needed only if your code references any symbols.
    - Compile the file using `java -cp <jarFile> Compile <ergoScriptFile> <optionalSymbolsFile>`. Examples:
        - `java -cp ErgoScriptCompiler.jar Compile myScript.es mySymbols.json`
        - `java -cp ErgoScriptCompiler.jar Compile myScript.es`

See the example below for a sample output.

## Example 

The folder [src/test/resources](src/test/resources) contains sample ErgoScript and symbol files.
Following is a transcript of an execution.

```bash
java -cp \
      target/scala-2.12/ErgoScriptCompiler-assembly-0.1.jar \
      Compile \
      src/test/resources/AgeUSD.es \
      src/test/resources/AgeUSD_symbols.json 
      
ErgoTree:
102b0400040004000e200fb1eca4646950743bc5a8c341c1...

Blake2b256:
2ffa0259301a5c7cdcf3074f7e5625f4764ca42453102804d4a774c6b39cc2e9

Address:
29irJ65SHH5VxgQaXubC1z9eHzutUWV6BB2QGCbA9eQ53msb...

```

As we can see, the program outputs three values:
1. The ErgoTree corresponding to the Script, serialized and hex-encoded
2. The Blake2b256 hash of the ErgoTree, hex encoded.
3. The address corresponding to the ErgoTree.

## Symbols File

If your ErgoScript code contains reference to a token ids or script hashes, then encode such values into a "symbols" file as follows (the extension can be anything):

```json
{
  "symbols":[
    {
      "name":"poolTokenId",
      "type":"CollByte",
      "value":"0fb1eca4646950743bc5a8c341c16871a0ad9b4077e3b276bf93855d51a042d1"
    },
    {
      "name":"epochPrepScriptHash",
      "type":"CollByte",
      "value":"d998e06e0c093b0990fa3da4f3bea4364546803551ea9cac02623e9675ba4522"
    },
    {
      "name":"buffer",
      "type":"Int",
      "value":"4"
    }
  ]
}
```

The `type` can be any of `CollByte`, `Int`, `Long`, `GroupElement` and `Address`.

- `CollByte` is simply given as a 32-byte long hex-encoded byte-array
- `Int` and `Long` are given as numbers encoded as strings
- `GroupElement` is given as a 33-byte compressed elliptic curve point, with point at infinity represented as all zeros.

## Generating Payment Requests

In addition to compiling ErgoScript, this tool can also be used to generate a "payment request".

- In the Ergo client's REST API, a payment request requires register values to be given as serialized-hex.
- For instance, to store the integer 1, we would need to provide the register value as `0402`. 
  ```json
  {"address": "6Vs43...", "registers": {"R4": "0402"}, "amount": 123}
  ```
- We can use this tool to generate the payment request from human-understandable values.

- The syntax of the command is:
  `java -cp <jarFile> Payment <humanRequest.json> <symbolsFile.json>`

  Here the `humanRequest.json` is a payment request with the register value given as a name:

  ```json
  {"address": "6Vs43...", "registers": {"R4": "myValue"}, "amount": 123}
  ```
  The symbols file should contain the definition of `myValue`:
  ```json
  {"name": "myValue", "type": "Int", "value": "1"}
  ```
  The output is the final payment request with the names replaced with hex-serialized values.

The following shows an example transcript:
```bash
java -cp \
    target/scala-2.12/ErgoScriptCompiler-assembly-0.1.jar \
    Payment \
    src/test/resources/payment_request_AgeUSD.json \
    src/test/resources/payment_request_AgeUSD_symbols.json 
```
Output below:
```json
[ {
  "address" : "29irJ65SHH5VxgQaXubC1z9eHzutUWV6BB2QGCbA9eQ53...",
  "value" : 1000000,
  "assets" : [ {
    "tokenId" : "a908bf2be7e199014b45e421dc4adb846d8de95e37da87c7f97ac6fb8e863fa2",
    "amount" : 10000000000000
  }, {
    "tokenId" : "b240daba6b5f9f9b6d4e6d7fc8b7c0423f1dfa28a883ec626a18b69be6c7590e",
    "amount" : 10000000000000
  }, {
    "tokenId" : "7bd873b8a886daa7a8bfacdad11d36aeee36c248aaf5779bcd8d41a13e4c1604",
    "amount" : 1
  } ],
  "registers" : {
    "R4" : "0500",
    "R5" : "0500"
  }
}, {
  "address" : "6Vs43fLottAzin3EiEiswbSD31ETscqBLy9i3zTWCwUVuG79...",
  "value" : 1000000,
  "assets" : [ {
    "tokenId" : "77d14a018507949d1a88a631f76663e8e5101f57305dd5ebd319a41028d80456",
    "amount" : 1
  } ],
  "registers" : {
    "R4" : "0e0601a2b3c4d5e6"
  }
} ]
```

The resource folder contains a [sample payment request](src/test/resources/payment_request_AgeUSD.json) and a [sample symbols file](src/test/resources/payment_request_AgeUSD_symbols.json).

