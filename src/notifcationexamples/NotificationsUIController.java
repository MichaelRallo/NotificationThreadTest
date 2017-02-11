/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author mikerallo
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    @FXML
    private Button task1Button;
    @FXML
    private Button task2Button;
    @FXML
    private Button task3Button;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest((WindowEvent we) -> {
            if (task1 != null) task1.end();
            if (task2 != null) task2.end();
            if (task3 != null) task3.end();
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            task1Button.setText("End Task1");
        }
        //Check if task is running when button is click
        else if(task1.getTaskState() == TaskState.RUNNING){
            task1.end();
            task1 = null;
            task1Button.setText("Start Task1");
        }
    }
    
    @Override
    public void notify(String message) {
        //Check if Task has finished
        if (task1 != null && task1.getTaskState() == TaskState.FINISHED) {
            task1.end();
            task1 = null;
            task1Button.setText("Start Task1");
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                //Check if Task has finished
                if (task2 != null && task2.getTaskState() == TaskState.FINISHED) {
                    task2.end();
                    task2 = null;
                    task2Button.setText("Start Task2");
                } 
                textArea.appendText(message + "\n");
            });
            task2.start();
            task2Button.setText("End Task2");
        }    
        //Check if task is running when button is click
        else if (task2 != null && task2.getTaskState() == TaskState.RUNNING) {
            task2.end();
            task2 = null;
            task2Button.setText("Start Task2");
        } 
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                //Check if Task has finished
                if (task3 != null && task3.getTaskState() == TaskState.FINISHED) {
                    task3.end();
                    task3 = null;
                    task3Button.setText("Start Task3");
                } 
                textArea.appendText((String)evt.getNewValue() + "\n");
            });
            
            task3.start();
            task3Button.setText("End Task3");
        }
        //Check if task is running when button is click
        else if (task3 != null && task3.getTaskState() == TaskState.RUNNING) {
            task3.end();
            task3 = null;
            task3Button.setText("Start Task3");
        } 
                
    } 
}
