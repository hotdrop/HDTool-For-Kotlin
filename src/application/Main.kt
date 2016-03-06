package application

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.fxml.FXMLLoader

class Main() : Application() {
	public override fun start(primaryStage: Stage?) {
		try{
			var root: Parent? = FXMLLoader.load(this.javaClass.getResource("SSTMain.fxml"))
			primaryStage!!.setTitle("SummaryStackTraceTool")
			val scene: Scene = Scene(root, 600.toDouble(), 600.toDouble());
			scene.getStylesheets().add(this.javaClass.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

fun main(args: Array<String>) {
	Application.launch(Main().javaClass)
}
