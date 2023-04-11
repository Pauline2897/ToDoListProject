package development.todolistproject.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoData {
    private static TodoData instance = new TodoData();
    private static String filename = "TodoListItems.text";

    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter formatter;

    public static TodoData getInstance(){
        return instance;
    }

    private TodoData(){
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void addTodoItem(TodoItem todoItem){
        todoItems.add(todoItem);
    }

    public void deleteTodoItem(TodoItem todoItem){
        todoItems.remove(todoItem);
    }

    public void updateTodoItem(TodoItem oldItem, TodoItem newItem){
        todoItems.set(todoItems.indexOf(oldItem),newItem);
    }

    public void loadTodoItems() throws IOException{
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader bufferedReader = Files.newBufferedReader(path);

        String input;

        try{
            while((input=bufferedReader.readLine())!=null){
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);

                TodoItem todoItem = new TodoItem(shortDescription,details,date);
                todoItems.add(todoItem);

            }

        }finally {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
        }
    }

    public void storeTodoItems() throws IOException{
        Path path = Paths.get(filename);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);

        try{
            for(int i=0;i<todoItems.size();i++){
                String todoItemData = todoItems.get(i).getShortDescription() +
                        "\t" +
                        todoItems.get(i).getDetails() +
                        "\t" +
                        todoItems.get(i).getDeadline().format(formatter);

                bufferedWriter.write(todoItemData);
                bufferedWriter.newLine();
            }

        }finally{
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
        }
    }
}
