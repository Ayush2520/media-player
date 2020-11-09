/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author Asus
 */
public class FXMLDocumentController implements Initializable {
    
    private String filepath;
    private MediaPlayer mediaplayer;
    @FXML
    private MediaView mediaview;
    @FXML
    private Slider progressbar;
    @FXML
    private Slider volumeprogressbar;
    
    public void choosefile(ActionEvent event)
    {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        filepath = file.toURI().toString();
        if(filepath != null)
        {
           Media media = new Media(filepath);
           mediaplayer = new MediaPlayer(media);
           mediaview.setMediaPlayer(mediaplayer);
           DoubleProperty widthprop = mediaview.fitWidthProperty();
           DoubleProperty heightprop = mediaview.fitHeightProperty();
           
           widthprop.bind(Bindings.selectDouble(mediaview.sceneProperty(),"width" ));
           heightprop.bind(Bindings.selectDouble(mediaview.sceneProperty(),"height" ));

           mediaplayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
               @Override
               public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                   
                   progressbar.setValue(newValue.toSeconds());
                   
               }
               
           });
           
           progressbar.setOnMousePressed(new  EventHandler<MouseEvent>()
           {
               @Override
               public void handle(MouseEvent event) {
                   mediaplayer.seek(Duration.seconds(progressbar.getValue()));
               }
               
               
           });
           progressbar.setOnMouseDragged(new  EventHandler<MouseEvent>()
           {
               @Override
               public void handle(MouseEvent event) {
                   mediaplayer.seek(Duration.seconds(progressbar.getValue()));
               }
               
               
           });
           
           mediaplayer.setOnReady(new Runnable() {
               @Override
               public void run() {
                   Duration total = media.getDuration();
                   progressbar.setMax(total.toSeconds());
               }
           });
           
           volumeprogressbar.setValue(mediaplayer.getVolume()*100);
           volumeprogressbar.valueProperty().addListener(new InvalidationListener() {
               @Override
               public void invalidated(Observable observable) {
                   mediaplayer.setVolume(volumeprogressbar.getValue()/100);
                   
               }
           });
           
           
           mediaplayer.play();
        }
    }
    
    public void playact(ActionEvent event)
    {
        mediaplayer.play();
        mediaplayer.setRate(1);
    }
    public void pauseact(ActionEvent event)
    {
        mediaplayer.pause();
    }
    public void stopact(ActionEvent event)
    {
        mediaplayer.stop();
    }
    public void slowact(ActionEvent event)
    {
        mediaplayer.setRate(0.5);
    }
    public void fastact(ActionEvent event)
    {
        mediaplayer.setRate(2);
    }
    public void skipact(ActionEvent event)
    {
        mediaplayer.seek(mediaplayer.getCurrentTime().add(Duration.seconds(+10)));
    }
    public void backact(ActionEvent event)
    {
        mediaplayer.seek(mediaplayer.getCurrentTime().add(Duration.seconds(-10)));
    }
        
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
