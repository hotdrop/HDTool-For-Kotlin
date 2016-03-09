package application

import java.net.URL
import java.util.ResourceBundle
import java.io.File
import java.util.HashMap

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.beans.binding.BooleanBinding
import javafx.event.ActionEvent
import javafx.scene.control.TextField
import javafx.scene.control.Button
import javafx.scene.control.TableView
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.PropertyValueFactory

import javafx.stage.FileChooser
import javafx.stage.Stage
import javafx.stage.FileChooser.ExtensionFilter

import jp.ojt.sst.file.StackTraceFile
import jp.ojt.sst.model.StackTraceData

class SSTController() : Initializable {
	
	@FXML
	private var filePathField: TextField? = null
	@FXML
	private var searchWordField: TextField? = null
	@FXML
	private var buttonExecute: Button? = null
	
	@FXML
	private var resultTableView: TableView<StackTraceData>? = null
	@FXML
	private var dateViewId: TableColumn<StackTraceData, String>? = null
	@FXML
	private var numViewId: TableColumn<StackTraceData, Int>? = null
	@FXML
	private var exceptionViewId: TableColumn<StackTraceData, String>? = null
	@FXML
	private var messageViewId: TableColumn<StackTraceData, String>? = null
	@FXML
	private var locationViewId: TableColumn<StackTraceData, String>? = null

	public override fun initialize(location: URL?, resources: ResourceBundle?) {
		dateViewId?.setProperty("date")
		numViewId?.setProperty("number")
		exceptionViewId?.setProperty("exception")
		messageViewId?.setProperty("message")
		locationViewId?.setProperty("location")
		
		val bb: BooleanBinding = filePathField!!.textProperty().isEmpty().or(searchWordField!!.textProperty().isEmpty()) 
		buttonExecute!!.disableProperty().bind(bb)
	}
	
	@FXML
	fun onReference(event: ActionEvent) {
		var fc: FileChooser = FileChooser()
		fc.setTitle("Choose StackTraceLog")
		fc.getExtensionFilters().addAll(ExtensionFilter("Log Files", "*.log"))
		var selectedFile: File = fc.showOpenDialog(null)
		filePathField?.setText(selectedFile.getAbsolutePath())
	}
	
	@FXML
	fun onExecute(event: ActionEvent) {
		var stFile = StackTraceFile(filePathField!!.getText(), searchWordField!!.getText())
		stFile.read()
		var resultMap: HashMap<String, StackTraceData> = stFile.map
		resultTableView?.getItems()?.clear()
		for(stData in resultMap.values) {
			resultTableView?.getItems()?.add(stData)
		}
	}
	
	@FXML
	fun onExit(event: ActionEvent) {
		((event.getSource() as Button).getScene().getWindow() as Stage).close()
	}
	
	fun<T, S> TableColumn<S, T>.setProperty(property: String) {
		setCellValueFactory(PropertyValueFactory<S, T>(property))
	} 
}
