package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Model;
import it.polito.tdp.borders.model.Country;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtAnno;

    @FXML
    private ComboBox<Country> comboStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	int anno;
    	
    	if(txtAnno.getText().equals("")) {
    		txtResult.setText("Devi inserire un anno!");
    		return;
    	}
    	
    	try {
    		anno = Integer.parseInt(txtAnno.getText());
    	}catch(NumberFormatException ne){
    		txtResult.setText("Puoi inserire solamente caratteri numerici!");
    		return;
    	}
    	
    	if(anno< 1816 || anno>2016) {
    		txtResult.setText("Puoi inserire solamente un anno compreso tra 1816-2016");
    		return;
    	}
    	
    	model.creaGrafo(anno);
    	
    	comboStato.getItems().addAll(model.getVertex());
    	
    	txtResult.clear();
    	
    	for(Country c: model.getVertex()) {
    		txtResult.appendText(c+" Stati confinanti: "+model.numConfini(c)+"\n");
    	}
    	
    	txtResult.appendText("Il numero di componenti connesse è: "+model.componentiConnessi());

    }

    @FXML
    void doCalcolaStatiRaggiungibili(ActionEvent event) {
    	
    	if(comboStato.getValue()==null) {
    		txtResult.setText("Selezionare uno Stato");
    		return;
    	}
    	
    	txtResult.clear();
    	
    	for(Country c: model.trovaVicini(comboStato.getValue())) {
    		if(c.equals(comboStato.getValue())) {
    			
    		}else {
    			txtResult.appendText(c+"\n");
    		}
    	}

    }

    @FXML
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert comboStato != null : "fx:id=\"comboStato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
