package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import model.Browser;
import model.Classroom;
import model.Gender;

public class ClassroomGUI {

	private Classroom classroom;
	
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private TextField txtLoginName;
	
	@FXML
	private PasswordField txtLoginPassword;
	
	@FXML
	private TextField txtRegisterName;
	
	@FXML
	private PasswordField txtRegisterPassword;
	
	@FXML
	private TextField txtPictureDirectory;
	
	@FXML
	private ToggleGroup genderGroup;
	
	@FXML
	private RadioButton maleButton;
	
	@FXML
	private RadioButton femaleButton;
	
	@FXML
	private RadioButton otherButton;
	
	@FXML
	private CheckBox softwareBox;
	
	@FXML
	private CheckBox telematicBox;
	
	@FXML
	private CheckBox industrialBox;
	
	@FXML
	private DatePicker birthdayPicker;
	
	@FXML
	private SplitMenuButton browserPicker;
	
	@FXML
	private MenuItem Chrome;
	
	@FXML
	private MenuItem Opera;
	
	@FXML
	private MenuItem Edge;
	
	@FXML
	private MenuItem Tor;
	
	@FXML
	private Label labUsername;
	
	@FXML
	private ImageView profilePicture;

	@FXML
	private TableView<model.UserAccount> tvUserList;
	
	@FXML
	private TableColumn<model.UserAccount, String> tcUsername;
	
	@FXML
	private TableColumn<model.UserAccount, String> tcGender;
	
	@FXML
	private TableColumn<model.UserAccount, String> tcCareer;
	
	@FXML
	private TableColumn<model.UserAccount, String> tcBirthday;
	
	@FXML
	private TableColumn<model.UserAccount, String> tcBrowser;
	
	private Dialog<String> dialog;
	private ButtonType okayButton;
	
	public ClassroomGUI(Classroom classroom) {
		this.setClassroom(classroom);
		dialog = new Dialog<>();
		okayButton = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(okayButton);
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	
	private void initializeTableView() {
		ObservableList<model.UserAccount> observableList;
		observableList = FXCollections.observableArrayList(classroom.getUserAccountList());
		tvUserList.setItems(observableList);
		tcUsername.setCellValueFactory(new PropertyValueFactory<model.UserAccount, String>("username"));
		tcGender.setCellValueFactory(new PropertyValueFactory<model.UserAccount, String>("gender"));
		tcCareer.setCellValueFactory(new PropertyValueFactory<model.UserAccount, String>("career"));
		tcBirthday.setCellValueFactory(new PropertyValueFactory<model.UserAccount, String>("birthdayDate"));
		tcBrowser.setCellValueFactory(new PropertyValueFactory<model.UserAccount, String>("favoriteBrowserInString"));
	}
	
	public void loadLogin() throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
		fxmlLoader.setController(this);
		Parent loginPane = fxmlLoader.load();
		mainPane.setCenter(loginPane);
	}

	public void showAlert(String text) {
		dialog.getDialogPane().setHeaderText(text);
		dialog.showAndWait();
	}
	
	public void browse(ActionEvent event) throws Exception {
		JFileChooser fc = new JFileChooser();
		File file;
		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			txtPictureDirectory.setText(file.toURI().toString());
		}
	}

	public Gender selectGender() throws Exception {
		Gender gn = null;
		if(maleButton.isSelected()) {
			gn = Gender.Male;
		}else if(femaleButton.isSelected()) {
			gn = Gender.Female;
		}else if(otherButton.isSelected()) {
			gn = Gender.Other;
		}
		return gn;
	}
	
	public Browser selectBrowser() {
		Browser br = null;
		if(browserPicker.getText().equals("Google Chrome")) {
			br = Browser.Google_Chrome;
		}else if(browserPicker.getText().equals("Opera")) {
			br = Browser.Opera;
		}else if(browserPicker.getText().equals("Microsoft Edge")) {
			br = Browser.Microsoft_Edge;
		}else if(browserPicker.getText().equals("Tor")) {
			br = Browser.Tor;
		}
		return br;
	}

	public void setChromeText(ActionEvent event) {
		browserPicker.setText("Google Chrome");
	}
	
	public void setOperaText(ActionEvent event) {
		browserPicker.setText("Opera");
	}
	
	public void setEdgeText(ActionEvent event) {
		browserPicker.setText("Microsoft Edge");
	}
	
	public void setTorText(ActionEvent event) {
		browserPicker.setText("Tor");
	}
	
	public void createAccount(ActionEvent event) throws Exception {
		try {
			String username = txtRegisterName.getText();
			String password = txtRegisterPassword.getText();
			String imagePath = txtPictureDirectory.getText();
			Image image = new Image(imagePath);
			Gender gn = selectGender();
			boolean software = softwareBox.isSelected();
			boolean telematic = telematicBox.isSelected();
			boolean industrial = industrialBox.isSelected();
			Browser br = selectBrowser();
			String birthday = birthdayPicker.getValue().toString();
			boolean wroteUser = !txtRegisterName.getText().equals("");
			boolean wrotePassword = !txtRegisterPassword.getText().equals("");
			boolean pickedCareer = software || telematic || industrial;
			if(wroteUser && wrotePassword && gn != null && pickedCareer && br != null) {
				saveFile(image, username);
				showAlert(classroom.registerUser(username, password, gn, software, telematic, industrial, birthday, br));
			}else {
				showAlert("You must fill each field in the form");
			}
		}catch(Exception e) {
			showAlert("Invalid birthday format/Invalid picture directory");
		}
	}
	
	public void saveFile(Image image, String username) {
		File file = new File(System.getProperty("user.dir") + "/resources/" + username + ".png");
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(bImage, "png", file);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void signUp(ActionEvent event) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
		fxmlLoader.setController(this);
		Parent registerPane = fxmlLoader.load();
		mainPane.getChildren().clear();
		mainPane.setCenter(registerPane);
	}

	public void logIn(ActionEvent event) throws Exception {
		String username = "Default-Username";
		File file;
		Image image;
		boolean canLogIn = classroom.checkPassword(txtLoginName.getText(), txtLoginPassword.getText());
		if(!(txtLoginName.getText().equals("") || txtLoginPassword.getText().equals(""))) {
			if(canLogIn) {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("account-list.fxml"));
				fxmlLoader.setController(this);
				Parent listPane = fxmlLoader.load();
				mainPane.getChildren().clear();
				mainPane.setCenter(listPane);
				initializeTableView();
				username = txtLoginName.getText();
				labUsername.setText(username);
				file = new File(System.getProperty("user.dir") + "/resources/" + username + ".png");
				image = new Image(file.toURI().toString());
				profilePicture.setImage(image);
			}else {
				showAlert("The username or password given is incorrect");
			}
		}else {
			showAlert("Please enter a username and a password");
		}
	}

	public void signIn(ActionEvent event) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
		fxmlLoader.setController(this);
		Parent loginPane = fxmlLoader.load();
		mainPane.getChildren().clear();
		mainPane.setCenter(loginPane);
	}

	public void logOut(ActionEvent event) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
		fxmlLoader.setController(this);
		Parent loginPane = fxmlLoader.load();
		mainPane.getChildren().clear();
		mainPane.setCenter(loginPane);
	}

}
