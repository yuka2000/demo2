package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {

    @FXML
    private TextField bookTextField;

    @FXML
    private Button getNameButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private ImageView imageView;  // Add ImageView

    @FXML
    private void getname(ActionEvent event) {
        String bookName = bookTextField.getText().trim();
        if (!bookName.isEmpty()) {
            String apiKey = "AIzaSyBbxRu6_J7uj3dyuIQGyc00xrgFrinfUVQ";
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + bookName + "&key=" + apiKey;

            try {
                String jsonResponse = BookAppUtil.fetchDataFromAPI(apiUrl);
                BookData bookData = BookAppUtil.parseJson(jsonResponse);

                nameLabel.setText("Name: " + bookData.getName());
                authorLabel.setText("Author: " + bookData.getAuthor());

                // Set the book cover image
                String imageUrl = bookData.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Image image = new Image(imageUrl);
                    imageView.setImage(image);
                } else {
                    // Handle the case where the image URL is not available
                    imageView.setImage(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                nameLabel.setText("Error fetching book data");
                authorLabel.setText("");
                imageView.setImage(null);
            }
        }
    }
}
