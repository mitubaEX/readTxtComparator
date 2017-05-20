package com.github.mituba

import java.io.PrintWriter
import javax.script.ScriptException;
import com.github.pochi.runner.scripts.ScriptRunner;
import com.github.pochi.runner.scripts.ScriptRunnerBuilder;

object Main{
  
	def fileWrite(filename:String, birthmark:String) : String = {
    val newFile = new PrintWriter("tmp/" + filename + ".csv")
    newFile.write(filename + ",,," + birthmark)
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
    var inputFilePath = ""
		for(s <- run(args(0))){
		  val strSplit = s.split(",",3)
		  if(strSplit(0) == "inputBirthmark"){
        inputFilePath = fileWrite("a", strSplit(2))
      }else if(!strSplit(0).contains("Exception") && strSplit.length >= 3){
        if(strSplit(1).toDouble >= 0.25)
            compare(inputFilePath, fileWrite("b", strSplit(2)))
      }
		}
  }
}
