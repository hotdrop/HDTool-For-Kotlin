package jp.ojt.sst.file

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.IOException;

import jp.ojt.sst.model.StackTraceData;
import kotlin.io.*;
import kotlin.text.*;

class StackTraceFile(path: String, word: String) {
	
	val filePath = path
	val searchWord = word
	
	val LEGEX_DATE: String = "(\\d{4}-\\d{2}-\\d{2})"
	val LEGEX_EXCEPTION: String = "([\\w\\.]*Exception)"
	var map = HashMap<String, StackTraceData>()
	
	fun read() {
		val dateRegex = Regex(LEGEX_DATE)
		val exceptionRegex = Regex(LEGEX_EXCEPTION);
		
		var matchDateStr = ""
		var exceptionStr = ""
		var messages = ""
		
		var foundDate = false
		var foundException = false
		var foundWord = false
		
		File(filePath).absoluteFile.forEachLine { line ->
				
			if(dateRegex.containsMatchIn(line)) {
				matchDateStr = dateRegex.find(line)!!.value
				foundDate = true;
				foundException = false;
				foundWord = false;
			}
			
			if(foundDate && !foundException) {
				if(exceptionRegex.containsMatchIn(line)) {
					exceptionStr = exceptionRegex.find(line)!!.value
					val idx = line.indexOf(exceptionStr) + exceptionStr.length
					if (line.length > idx + 1) {
						messages = line.substring(idx + 1);
					}
					foundException = true;
				}
			}
				
			if(foundException && !foundWord) {
				if(line.contains(searchWord)) {
					val key = matchDateStr + exceptionStr + messages + line;
					if(map.containsKey(key)) {
						var stData: StackTraceData = map.get(key)!!
						stData.addCount();
						map.replace(key, stData);
					} else {
						var stData = StackTraceData(matchDateStr, exceptionStr, messages, line);
						map.put(key, stData);
					}
					foundWord = true;
				}
			}
		}
	}
}