package jp.ojt.sst.model

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class StackTraceData(argDate: String, argException: String, argMessage: String, argLine: String) {
	
	var dateProperty: StringProperty = SimpleStringProperty(argDate)
	var exceptionProperty: StringProperty? = SimpleStringProperty(argException)
	var messageProperty: StringProperty? = SimpleStringProperty(argMessage)
	var locationProperty: StringProperty? = SimpleStringProperty(argLine)
	var count: Int = 1
	
	/**
	 * Add one to the count field of this class. 
	 */
	fun addCount() {
		count++;
	}
	
	fun numberProperty(): IntegerProperty {
		return SimpleIntegerProperty(count);
	}

	/**
	 * Returns a csv string representation of this stackTraceData.
	 * Fields of this class are separated by the characters comma(,).
	 * @return a csv string representation of this stackTraceData
	 */
	fun toCSVString(): String {
		return arrayOf(dateProperty.toString(), 
						exceptionProperty.toString(), 
						messageProperty.toString(), 
						locationProperty.toString(), 
						count.toString()).joinToString(",")
	}
}