package jp.ojt.sst.model

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class StackTraceData(date: String, exception: String, message: String, line: String) {
	
	val dateProperty: StringProperty = SimpleStringProperty(date)
	val exceptionProperty: StringProperty? = SimpleStringProperty(exception)
	val messageProperty: StringProperty? = SimpleStringProperty(message)
	val locationProperty: StringProperty? = SimpleStringProperty(line)
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