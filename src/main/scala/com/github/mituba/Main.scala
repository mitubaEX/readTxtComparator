package com.github.mituba

import java.io.PrintWriter
import javax.script.ScriptException;
import com.github.pochi.runner.scripts.ScriptRunner;
import com.github.pochi.runner.scripts.ScriptRunnerBuilder;
import java.net.URLEncoder

object Main{

  def fileWrite(filename:String, birthmark:String) : String = {
    val newFile = new PrintWriter("tmp/" + filename + ".csv", "UTF-8")
    newFile.write(birthmark)
    newFile.close()
    "tmp/" + filename + ".csv"
  }

  def compare(inputFilePath:String, filePath:String){
    val runner = new ScriptRunnerBuilder build
    val args = Array("./compare_input_csv_test.js", "2-gram", "./"+ inputFilePath, "./" + filePath)
    runner.runsScript(args)
  }

  def run(arg : String) = scala.io.Source.fromFile(arg).getLines()

  def main(args : Array[String]){
    val start = System.currentTimeMillis
    val readFile = run(args(0)).toList
    // val inputBirthmark = readFile.head
    if(readFile.size <= 1)
      return;
    val a = fileWrite("a", readFile.head)
    // for(s <- readFile.tail.tail.tail.tail.tail){
    for(s <- readFile.tail.tail){
      val splitString = s.split(",", 5)
      if(splitString.length >= 5){
        val b = fileWrite("b", (splitString(0) + "," + splitString(2) + "," + splitString(3) + "," + splitString(4)).replace("quot;", "").replace("\\", "").replace("\"", ""))

        compare(a, b)
      } else {
        println(s)
      }

      // val strSplit = s.split(",",3)
      // if(strSplit(0) == "inputBirthmark"){
      //   inputFilePath = fileWrite("a", strSplit(2))
      // }else if(!strSplit(0).contains("Exception") && strSplit.length >= 3){
      //   if(strSplit(1).toDouble >= 0.25)
      //     compare(inputFilePath, fileWrite("b", strSplit(2)))
      // }
    }
    println("compareTime:" + (System.currentTimeMillis - start) + "msec")
  }
}
