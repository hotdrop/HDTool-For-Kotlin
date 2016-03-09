package jp.ojt.sst.file

import java.io.File
import java.util.HashMap
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.io.BufferedReader
import java.io.IOException

import jp.ojt.sst.model.StackTraceData

class StackTraceFile(path: String, word: String) {
	
	val filePath = path
	val searchWord = word
	
	var map = HashMap<String, StackTraceData>()
	
	fun read() {
		
		val dateRegex = Regex("(\\d{4}-\\d{2}-\\d{2})")
		val exceptionRegex = Regex("([\\w\\.]*Exception)")
		
		var matchDateStr = ""
		var exceptionStr = ""
		var messages = ""
		
		var foundDate = false
		var foundException = false
		var foundWord = false
		
		File(filePath).bufferedReader().forEachLine {
			if(dateRegex.containsMatchIn(it)) {
				matchDateStr = dateRegex.find(it)!!.value
				foundDate = true
				foundException = false
				foundWord = false
			}
			
			if(foundDate && !foundException) {
				if(exceptionRegex.containsMatchIn(it)) {
					exceptionStr = exceptionRegex.find(it)!!.value
					val idx = it.indexOf(exceptionStr) + exceptionStr.length
					if (it.length > idx + 1) {
						messages = it.substring(idx + 1)
					}
					foundException = true
				}
			}
				
			if(foundException && !foundWord) {
				if(it.contains(searchWord)) {
					val key = matchDateStr + exceptionStr + messages + it
					if(map.containsKey(key)) {
						var stData = map[key]
						stData?.addCount()
						map.replace(key, stData)
					} else {
						map[key] = StackTraceData(matchDateStr, exceptionStr, messages, it)
					}
					foundWord = true
				}
			}
		}
	}
}