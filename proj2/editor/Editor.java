package editor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.LinkedList;


public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;

    /** An EventHandler to handle keys that get pressed. */
    private class KeyEventHandler implements EventHandler<KeyEvent> {
        int textCenterX;
        int textCenterY;

        private static final int STARTING_FONT_SIZE = 20;
        private static final int STARTING_TEXT_POSITION_X = 0;
        private static final int STARTING_TEXT_POSITION_Y = 0;

        /** The Text to display on the screen. */
        private Text displayText = new Text(STARTING_TEXT_POSITION_X, STARTING_TEXT_POSITION_Y, "");
        private LinkedList<String> displayString;
        private int fontSize = STARTING_FONT_SIZE;

        private String fontName = "Verdana";

        // Constructor
        KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
            textCenterX = 0;
            textCenterY = 0;

            // Initialize some empty text and add it to root so that it will be displayed.
            displayText = new Text(textCenterX, textCenterY, "");

            // Initialize some empty LinkedList to store the keys pressed
            displayString = new LinkedList<String>();

            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simplier!
            displayText.setTextOrigin(VPos.TOP);
            displayText.setFont(Font.font(fontName, fontSize));

            // All new Nodes need to be added to the root in order to be displayed.
            root.getChildren().add(displayText);
        }

        private String convertToString(LinkedList li) {
            String returnString = "";

            // Obtain Iterator
            Iterator it = li.iterator();
            while (it.hasNext()) {
                returnString += it.next();
            }

            return returnString;
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
                    displayString.add(characterTyped);
                    displayText.setText(convertToString(displayString));
                    keyEvent.consume();
                } else if (characterTyped.charAt(0) == 8 && !displayString.isEmpty()) {
                    // backspace has been pressed, delete the last character from displayString
                    displayString.removeLast();
                    displayText.setText(convertToString(displayString));
                    keyEvent.consume();
                }

            } else if (keyEvent.getEventType() == keyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we check (KEY_TYPED events don't have an associated
                // keyCode).
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.UP) {
                    fontSize += 5;
                    displayText.setFont(Font.font(fontName, fontSize));
                } else if (code == KeyCode.DOWN) {
                    fontSize = Math.max(0, fontSize - 5);
                    displayText.setFont(Font.font(fontName, fontSize));
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen
        Group root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the widnow displayed
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