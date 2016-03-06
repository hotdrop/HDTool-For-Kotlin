package jp.ojt.sst.file;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ojt.sst.exception.ASTException;
import jp.ojt.sst.model.StackTraceData;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * StackTraceFile
 *
 */
public class StackTraceFile {

	/** Regex of date(log4j %d) */
	private static final String LEGEX_DATE = "(\\d{4}-\\d{2}-\\d{2})";
	
	/** Regex of Exception(contain package) */
	private static final String LEGEX_EXCEPTION="([\\w\\.]*Exception)";
	
	/** Target StackTraceLog absolute path */
	private String filePath;
	
	/** Search word into stackTraceLog */
	private String searchWord;
	
	/** Store StackTraceData.(date, Exception, detail, searchWord line) */
	private HashMap<String, StackTraceData> map = new HashMap<>();
	
	/**
	 * Constructor
	 * 
	 * @param path stackTraceLogFile absolute path
	 * @param word search word
	 */
	public StackTraceFile(String path, String word) {
		filePath = path;
		searchWord = word;
	}
	
	/**
	 * Read from Stack Trace File
	 */
	public void read() {
		
		Pattern datePtn = Pattern.compile(LEGEX_DATE);
		Pattern exceptionPtn = Pattern.compile(LEGEX_EXCEPTION);
		
		String matchDateStr = "";
		String exceptionStr = "";
		String messages = "";
		
		boolean foundDate = false;
		boolean foundException = false;
		boolean foundWord = false;
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			for(String line; (line = br.readLine()) != null; ) {
				
				Matcher dateMacher = datePtn.matcher(line);
				if(dateMacher.find()) {
					matchDateStr = dateMacher.group();
					foundDate = true;
					foundException = false;
					foundWord = false;
				}
				
				if(foundDate && !foundException) {
					Matcher exceptionMacher = exceptionPtn.matcher(line);
					if(exceptionMacher.find()) {
						exceptionStr = exceptionMacher.group();
						int idx = line.indexOf(exceptionStr) + exceptionStr.length();
						if (line.length() > idx + 1) {
							messages = line.substring(idx + 1);
						}
						foundException = true;
					}
				}
				
				if(foundException && !foundWord) {
					if(line.contains(searchWord)) {
						String key = matchDateStr + exceptionStr + messages + line;
						if(map.containsKey(key)) {
							StackTraceData stData = map.get(key);
							stData.addCount();
							map.replace(key, stData);
						} else {
							//TODO
							//StackTraceData stData = new StackTraceData(matchDateStr, exceptionStr, messages, line);
							//map.put(key, stData);
						}
						foundWord = true;
					}
				}
			}
		} catch (IOException ioe) {
			throw new ASTException(ioe);
		}
	}
	
	/**
	 * 
	 * @return summary stackTraceLogFile Map
	 */
	public HashMap<String, StackTraceData> getResultMap() {
		return map;
	}
}
