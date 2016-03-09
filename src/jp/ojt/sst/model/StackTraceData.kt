package jp.ojt.sst.model

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class StackTraceData(date: String, exception: String, message: String, line: String) {
	
	val dateStr: StringProperty = SimpleStringProperty(date)
	val exceptionStr: StringProperty = SimpleStringProperty(exception)
	val message: StringProperty = SimpleStringProperty(message)
	val location: StringProperty = SimpleStringProperty(line)
	var count: Int = 1
	
	/**
	 * Add one to the count field of this class. 
	 */
	fun addCount() {
		count++;
	}
	
	fun dateProperty(): StringProperty {
		return dateStr
	}
	
	fun exceptionProperty(): StringProperty {
		return exceptionStr
	}
	
	fun messageProperty(): StringProperty {
		return message
	}
	
	fun locationProperty(): StringProperty {
		return location
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
		return arrayOf(dateStr.toString(), 
						exceptionStr.toString(), 
						message.toString(), 
						location.toString(), 
						count.toString()).joinToString(",")
	}
}