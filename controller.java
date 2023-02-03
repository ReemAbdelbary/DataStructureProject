/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;


public class FXMLController implements Initializable  {
    public  final static int NUMBER_CHAR = 256;
	public  static int next = -1;
	public static TreeNode huffmanRoot;

    @FXML
    private TextArea output;
    @FXML
    private TextField searchtf;
    @FXML
    private TextArea XMLScreen;
    @FXML
    private TextArea Path;
    @FXML
    private TextField compressPath;
    @FXML
    private TextField User1;
    @FXML
    private TextField User2;
    @FXML
    public Button ManualXML;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BtnClicked(ActionEvent event) {
        XMLanalysis obj = new XMLanalysis();
         Phase1 Obj = new Phase1();
        String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
        ArrayList<String> NoSpace = Obj.spaceRemover(result);
        output.setText(obj.MostActive(NoSpace));
    }

    @FXML
    private void btninfluencer(ActionEvent event) {
        XMLanalysis obj = new XMLanalysis();
         Phase1 Obj = new Phase1();
         String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
        ArrayList<String> NoSpace = Obj.spaceRemover(result);
        output.setText(obj.influencer(NoSpace));
    }

    @FXML
    private void btnSearch(ActionEvent event) {
        XMLanalysis obj = new XMLanalysis();
         Phase1 Obj = new Phase1();
        String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
        ArrayList<String> NoSpace = Obj.spaceRemover(result);
        output.setText(obj.search(searchtf.getText(), NoSpace));
    }

    @FXML
    private void ReadXMLbtn(ActionEvent event) {
        Phase1 Obj = new Phase1();
        String Lines = "";
        ArrayList<String> result = Obj.ReadXML(Path.getText());
        for(String s: result){
            Lines += s + "\n";
        }
        XMLScreen.setText(Lines);
    }

    @FXML
    private void btnMinify(ActionEvent event) {
        Phase1 Obj = new Phase1();
         String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
        ArrayList<String> NoSpace = Obj.spaceRemover(result);
        output.setText(Obj.XMLminify(NoSpace));
    }
}