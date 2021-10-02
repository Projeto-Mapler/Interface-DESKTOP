module mapler_interface {
	

	 
	requires flowless;
	requires fontawesomefx;
	requires interpretadorMACP;
	requires java.desktop;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires reactfx;
	requires richtextfx;
	requires undofx;
	requires com.jfoenix;
	requires transitive javafx.graphics;	  
	
	
	opens mapler to javafx.fxml;
	opens mapler.controller to javafx.fxml;
	opens mapler.model to javafx.fxml;
	opens mapler.model.highlight to javafx.fxml;
	opens mapler.model.resource to javafx.fxml;
	opens mapler.service to javafx.fxml;
	opens mapler.util to javafx.fxml;
	

	
	exports mapler;
	exports mapler.controller;
	exports mapler.model;
	exports mapler.model.highlight;
	exports mapler.model.resource;
	exports mapler.service;
	exports mapler.util;

	
}