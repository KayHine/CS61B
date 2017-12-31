package editor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Collection;

public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private Group root;

    /** An EventHandler to handle keys that get pressed. */
    private class KeyEventHandler implements EventHandler<KeyEvent> {
        int textCenterX;
        int textCenterY;

        private static final int STARTING_FONT_SIZE = 20;
        private static final int STARTING_TEXT_POSITION_X = 0;
        private static final int STARTING_TEXT_POSITION_Y = 0;

        /**
         * The Text to display on the screen.
         */
        private Text displayText = new Text(STARTING_TEXT_POSITION_X, STARTING_TEXT_POSITION_Y, "");
        private FastLinkedList<Text> buffer;
        private int fontSize = STARTING_FONT_SIZE;

        private String fontName = "Verdana";

        // Constructor
        KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
            textCenterX = 0;
            textCenterY = 0;

            // Initialize some empty text and add it to root so that it will be displayed.
            displayText = new Text(textCenterX, textCenterY, "HELLO!");
            Text anotherText = new Text(textCenterX, textCenterY, "BYE!");

            // Initialize some empty FastLinkedList to store the keys pressed
            buffer = new FastLinkedList<Text>();

            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simplier!
            displayText.setTextOrigin(VPos.TOP);
            displayText.setFont(Font.font(fontName, fontSize));
            anotherText.setTextOrigin(VPos.TOP);
            anotherText.setFont(Font.font(fontName, fontSize));
            buffer.addChar(displayText);
            buffer.addChar(anotherText);

            // All new Nodes need to be added to the root in order to be displayed.
            //addBufferToRoot(buffer, "add");
            root.getChildren().addAll(buffer);
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than the KEY_PRESSED for letter eys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalizations.
                String characterTyped = keyEvent.getCharacter();
                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                    // Ignore control keys, which have non-zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows.
                    Text newChar = new Text(STARTING_TEXT_POSITION_X, STARTING_TEXT_POSITION_Y, characterTyped);
                    newChar.setFont((Font.font(fontName, fontSize)));
                    newChar.setTextOrigin(VPos.TOP);
                    buffer.addChar(newChar);
                    //addBufferToRoot(buffer, "add");
                    //root.getChildren().add(newChar);
                    System.out.println(root.getChildren().toString());
                    //buffer.printList();
                    keyEvent.consume();
                } else if (characterTyped.charAt(0) == 8) {
                    // backspace has been pressed, delete the last character from displayString
                    buffer.deleteChar();
                    //addBufferToRoot(buffer, "delete");
                    //buffer.printList();
                    keyEvent.consume();
                }
            }
        }

        // function to iterate through buffer and add to root
        public void addBufferToRoot(FastLinkedList<Text> buffer, String action) {
            if (action == "add") {
                root.getChildren().add(buffer.getCurrentNode().val);
            } else if (action == "delete") {
                root.getChildren().remove(buffer.getCurrentNode().val);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen
        root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        // To get information about what keys the user is pressing, create an EventHandler
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx
        EventHandler<KeyEvent> keyEventEventHandler =
                new KeyEventHandler(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventEventHandler);
        scene.setOnKeyPressed(keyEventEventHandler);

        // This is boilerplate, necessary to setup the window where things are displayed
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}