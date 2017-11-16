package com.github.mituba

import java.io.PrintWriter
import javax.script.ScriptException;
import com.github.pochi.runner.scripts.ScriptRunner;
import com.github.pochi.runner.scripts.ScriptRunnerBuilder;
import java.net.URLEncoder
import java.io.File

object Main{

  def fileWrite(filename:String, birthmark:String) : String = {
    val newFile = new PrintWriter("tmp/" + filename + ".csv", "UTF-8")
    newFile.write(birthmark)
    newFile.close()
    "tmp/" + filename + ".csv"
  }

  def compare(inputFilePath:String, filePath:String){
    val runner = new ScriptRunnerBuilder build
    val args = Array("./compare_input_csv_test.js", "2-gram", inputFilePath, "./" + filePath)
    runner.runsScript(args)
  }

  def run(arg : String) = scala.io.Source.fromFile(arg).getLines()

  def main(args : Array[String]){
    // val readFile = run(args(0)).toList
    //
    //
    // val inputBirthmark = readFile.head
    // if(readFile.size <= 1)
    //   return;

    // val a = fileWrite("a", readFile.head)
    //
    // for(s <- readFile.tail.tail.tail.tail.tail){

    for(file <- new File(args(0)).listFiles().filter(n => n.getName().contains(args(1)))){
      val thresholdFile = run("../simpleSearcher/parse/result_" +args(1) +"/" + file.getName() + "_result_.txt").toList
      val threshold075 = thresholdFile.tail.head.toInt
      val threshold05 = thresholdFile.tail.tail.head.toInt
      val threshold025 = thresholdFile.tail.tail.tail.head.toInt
      val readFile = run(file.getAbsolutePath()).toList
      val a = fileWrite("a", readFile.head.replace("quot;", "").replace("\\", "").replace("\"", ""))
      var count = 0
      val start = System.currentTimeMillis
      for(s <- readFile.tail){
        try {
          val splitString = s.split(",", 4)
          if(splitString.length >= 4){
            val b = fileWrite("b", (splitString(0) + "," + splitString(1) + "," + splitString(2) + "," + splitString(3)).replace("quot;", "").replace("\\", "").replace("\"", ""))
            if(count <= threshold025){
              compare(a, b)
              if(threshold075 == count) {
                println("0.75:compareTime:" + (System.currentTimeMillis - start) + "msec")
              }
              if (threshold05 == count){
                println("0.5:compareTime:" + (System.currentTimeMillis - start) + "msec")
              }
              if (threshold025 == count){
                println("0.25:compareTime:" + (System.currentTimeMillis - start) + "msec")
              }
              count += 1
            }
          } else {
            // println(s)
          }

        } catch {
          case e:Exception => println(e)
        }

      }


      // val strSplit = s.split(",",3)
      // if(strSplit(0) == "inputBirthmark"){
      //   inputFilePath = fileWrite("a", strSplit(2))
      // }else if(!strSplit(0).contains("Exception") && strSplit.length >= 3){
      //   if(strSplit(1).toDouble >= 0.25)
      //     compare(inputFilePath, fileWrite("b", strSplit(2)))
      // }
    }
    // println("compareTime:" + (System.currentTimeMillis - start) + "msec")
  }
}
