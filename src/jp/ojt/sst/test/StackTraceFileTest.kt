package jp.ojt.sst.test

import java.io.File
import jp.ojt.sst.file.StackTraceFile

fun main(args: Array<String>) {
	
	val path = createTestLogFile()
	val stl = StackTraceFile(path, "test")
	stl.read()
	
	val resultMap = stl.map
	for(data in resultMap.values) {
		println(data.toCSVString())
	}

	File(path).delete()
}

fun createTestLogFile(): String {
	
	val path = System.getProperty("user.dir") + "/test.log"
	val list = arrayOf(
			"2016-02-17 19:05:34.878,[WebContainer : 0  ],java.lang.NumberFormatException: null",
			"	at java.lang.Integer.parseInt(Integer.java:465)",
			"	at java.lang.Integer.valueOf(Integer.java:593)",
			"   at jp.ojt.sst.test.success(test.java:234)",
			"	at javax.servlet.http.HttpServlet.service(HttpServlet.java:575)",
			"2016-02-17 19:05:40.261,[WebContainer : 0  ],null",
			"java.lang.NumberFormatException: null",
			"	at java.lang.Integer.parseInt(Integer.java:465)",
			"	at jp.co.ojt.sst.test.ConfigFile.<init>(ConfigFile.java:30)",
			"2016-02-17 19:05:34.878,[WebContainer : 0  ],java.lang.NumberFormatException: null",
			"	at java.lang.Integer.parseInt(Integer.java:465)",
			"	at java.lang.Integer.valueOf(Integer.java:593)",
			"   at jp.ojt.sst.test.success(test.java:234)",
			"	at javax.servlet.http.HttpServlet.service(HttpServlet.java:575)"
			)
	
	File(path).bufferedWriter().use { out -> 
		list.forEach { 
			out.write("${it}\n")
		 }
	}
	return path
}