package ch.makery.address

import ch.makery.address.model.Person
import ch.makery.address.util.Database
import ch.makery.address.view.{PersonEditDialogController, PersonOverviewController}
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp3:
  Database.setupDB()
  //Window Root Pane
  var roots: Option[scalafx.scene.layout.BorderPane] = None
  var cssResource = getClass.getResource("view/DarkTheme.css")
  var personOverviewController: Option[PersonOverviewController] = None
  /**
   * The data as an observable list of Persons.
   */
  val personData = new ObservableBuffer[Person]()

  /**
   * Constructor
   */
  personData ++= Person.getAllPersons
  
  override def start(): Unit =
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource)
    // Load root layout from fxml file.
    loader.load()

    // retrieve the root component BorderPane from the FXML
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "AddressApp"
      icons += new Image(getClass.getResource("/images/book.png").toExternalForm)
      scene = new Scene():
        root = roots.get
        stylesheets = Seq(cssResource.toExternalForm)
    // call to display PersonOverview when app start
    showPersonOverview()
  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    personOverviewController = Option(loader.getController[PersonOverviewController])

    this.roots.get.center = roots

  val string1 = new StringProperty("hello")
  val string2 = new StringProperty("sunway")
  val string3  = new StringProperty("segi")
  string1.onChange((a,b,c) => {
    println("String 1 has changed")
  })
  string1.onChange((a, b, c) => {
    println("String 1 has changed from " + b + " to " + c)
  })
  string1.value = "taylor"
  string2 <==> string1
  string3 <== string1
  string2.value = "monash"

  println(string1.value)
  println(string2.value)
  println(string3.value)

  val add: (Int, Int) => Int = (a, b) =>
    a + b

  val a: Int = 67
  extension (d: Int)
    def area: Double = d * d * 3.142
  println(14.area)

  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load();
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PersonEditDialogController]

    val dialog = new Stage():
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene:
        root = roots2
        stylesheets = Seq(cssResource.toExternalForm)
    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()

    control.okClicked
  
  given Int = 9
  
  def add3(a: Int)(implicit b: Int) = a + b
  def add4(a: Int)(implicit c: Int) = a + c
  def add5(a: Int)(implicit d: Int) = a + d
  
  println(add3)
  println(add4(4)(9))
  println(add5(4)(9))
  
  val mystring =
    s""" Hello
        !${add5(5)}""".stripMargin
  
  List(1,2,3,4,5).map(x => x+1)
  

end MainApp
