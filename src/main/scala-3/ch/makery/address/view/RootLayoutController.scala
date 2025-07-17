package ch.makery.address.view

import ch.makery.address.MainApp
import javafx.fxml.FXML
import javafx.event.ActionEvent

@FXML
class RootLayoutController:

    @FXML
    def handleClose(action: ActionEvent): Unit =
      MainApp.stage.close()