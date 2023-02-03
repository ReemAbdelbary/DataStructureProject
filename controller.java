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
	    @FXML
    private void btnPrettify(ActionEvent event) {
        Phase1 Obj = new Phase1();
        String Lines = "";
          String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
         ArrayList<String> NoSpace = Obj.spaceRemover(result);
         ArrayList<String> PrettifyResult = Obj.XMLprettify(NoSpace);
         for(String s: PrettifyResult){
            Lines += s + "\n";
        }
         output.setText(Lines);
    }

    @FXML
    private void btnJSON(ActionEvent event) {
        Phase1 Obj = new Phase1();
        String Lines = "";
          String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
        ArrayList<String> NoSpace = Obj.spaceRemover(result);
        ArrayList<String> JsonResult = Obj.json(NoSpace);
        for(String s: JsonResult){
            Lines += s + "\n";
            System.out.println(s);
        }
        output.setText(Lines);
        
        
    }

    @FXML
    private void btnValidate(ActionEvent event) {
        Phase1 Obj = new Phase1();
        String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
         Object[] validationResult = Obj.validation(result);
        ArrayList<String> correctedResult = new ArrayList<String>();
        int x =0;
        String s ="";
        boolean error = false;
        while(!(((Integer)validationResult[0] == -1) && ((Integer)validationResult[2] ==-1))){
             error = true;
             
            s += "Mismatched tags in Lines:" +validationResult[0] +"&" + validationResult[2] + "\n" +"tags:" + validationResult[1] + "&" + validationResult[3] + "\n";
            if(x==0){
               correctedResult = Obj.correction(validationResult, result); 
               x++;
            }
            else{
                correctedResult = Obj.correction(validationResult, correctedResult); 
            }
            validationResult = Obj.validation(correctedResult);  
        }
        if(error){
            s += "This File isn't Valid" +"\n" + "If you want to correct it press Correct";
        }
        
        else{
            s += "This file is valid";
        }
        
        output.setText(s);
    }

    @FXML
    private void btnCorrect(ActionEvent event) {
        Phase1 Obj = new Phase1();
         String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
         Object[] validationResult = Obj.validation(result);
        ArrayList<String> correctedResult = new ArrayList<String>();
        int x =0;
        String s ="";
        boolean error = false;
        while(!(((Integer)validationResult[0] == -1) && ((Integer)validationResult[2] ==-1))){
             error = true;
            if(x==0){
               correctedResult = Obj.correction(validationResult, result); 
               x++;
            }
            else{
                correctedResult = Obj.correction(validationResult, correctedResult); 
            }
            validationResult = Obj.validation(correctedResult);  
        }
        for(String k: correctedResult){
            s += k + "\n";
           
        }
        output.setText(s);
    }

    @FXML
    private void btnCompress(ActionEvent event) {
        
        String str="";
        Phase1 Obj = new Phase1();
        String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }

         ArrayList<String> LinesNoSpace = Obj.spaceRemover(result);
         String minified = Obj.XMLminify(LinesNoSpace);
         String clear = minified.substring(4); 

          //System.out.println(clear);

    StringBuilder g = Obj.encode(clear);
    String nn = g.toString();
    try {
         Obj.Compress(nn,compressPath.getText());
       } 
    catch (IOException e) {

        e.printStackTrace();
    }

//    System.out.println(g);
//    StringBuilder n = Obj.decode(g,huffmanRoot);
    str += "The file is saved in the given path" + "\n";
    str += "The binary Representation: " + nn + "\n";
    output.setText(str);
    
    }
	@FXML
    private void btnDecompress(ActionEvent event) {
         Phase1 Obj = new Phase1();
        String Lines = "";
       String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
        for(String s: result){
            Lines += s + "\n";
        }
        output.setText(Lines);
    }

    @FXML
    private void btnSave(ActionEvent event) {
        String savePATH = compressPath.getText();
        String g = output.getText();
           Path path = Paths.get(savePATH);
                 byte[] bytes = g.getBytes(StandardCharsets.UTF_8);

                 try {
                     Files.write(path, bytes);
                     output.setText("Successfully written data to the file");

                 }
                 catch (IOException e) {
                     e.printStackTrace();
                 }
    }

    @FXML
    private void btnGetMutual(ActionEvent event) {
         XMLanalysis obj = new XMLanalysis();
         Phase1 Obj1 = new Phase1();
         String str = "";
        String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
        ArrayList<String> LinesNoSpace = Obj1.spaceRemover(result);
        ArrayList<String> Mutual = obj.MutualUsers(User1.getText(), User2.getText(), LinesNoSpace);
        str += "Mutual Users: " + "\n";
        
        for(String s : Mutual){
            str += "User ID: " + s +"\n";
            
        }
        output.setText(str);
       
    }

    @FXML
    private void btnReadManual(ActionEvent event) {
        Phase1 Obj1 = new Phase1();
        ArrayList<String> str = Obj1.ReadXMLManual(Path.getText());
        String Lines = "";
        for(String s: str){
            Lines += s + "\n";
        }
        XMLScreen.setText(Lines);
    }

    @FXML
    private void btnSuggest(ActionEvent event) {
        XMLanalysis obj = new XMLanalysis();
        Phase1 Obj1 = new Phase1();
         String str = "";
         String[] resultarray = XMLScreen.getText().split("\n");
        ArrayList<String> result = new ArrayList<String>();
         for(int x=0; x<resultarray.length; x++){
            result.add(resultarray[x]);
        }
        ArrayList<String> LinesNoSpace = Obj1.spaceRemover(result);
        ArrayList<String> Suggestions = obj.Suggestions(User1.getText(), LinesNoSpace);
         str += "Suggested Users: " + "\n";
        
        for(String s : Suggestions){
            str += "User ID: " + s +"\n";
            
        }
        output.setText(str);
        
    }

}
