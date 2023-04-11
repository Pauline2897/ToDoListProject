package development.todolistproject;

import development.todolistproject.datamodel.TodoData;
import development.todolistproject.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private DatePicker deadlinePicker;

    public void populateFieldWithTodoItemValues(TodoItem todoItem){
        shortDescriptionField.setText(todoItem.getShortDescription());
        detailsArea.setText(todoItem.getDetails());
        deadlinePicker.setValue(todoItem.getDeadline());
    }

    public TodoItem updateTodoItem(TodoItem oldItem){
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate date = deadlinePicker.getValue();

        TodoItem newItem = new TodoItem(shortDescription,details,date);

        TodoData.getInstance().updateTodoItem(oldItem,newItem);
        return newItem;
    }

    public TodoItem processResults(){
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate date = deadlinePicker.getValue();

        TodoItem todoItem = new TodoItem(shortDescription,details,date);
        TodoData.getInstance().addTodoItem(todoItem);
        return todoItem;
    }

}
