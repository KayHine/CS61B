package editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private Group root;
    private final Rectangle cursor;

    // Editor Constructor
    public Editor() {
        // Create a cursor.
        cursor = new Rectangle(2, 20);
        cursor.setX(0);
        cursor.setY(0);
    }

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
            displayText = new Text(textCenterX, textCenterY, "");

            // Initialize some empty FastLinkedList to store the keys pressed
            buffer = new FastLinkedList<Text>();

            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simplier!
            displayText.setTextOrigin(VPos.TOP);
            displayText.setFont(Font.font(fontName, fontSize));
            buffer.addChar(displayText);

            // All new Nodes need to be added to the root in order to be displayed.
            root.getChildren().addAll(buffer.getCurrentNode().val);
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than the KEY_PRESSED for letter eys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalizations.
                String characterTyped = keyEvent.getCharacter();

                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8 && characterTyped.charAt(0) != 13) {
                    // Ignore control keys, which have non-zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows or the new line key.
                    addCharToBuffer(characterTyped);
                    addBufferToRoot(buffer, "add");
                    keyEvent.consume();
                } else if (characterTyped.charAt(0) == 8) {
                    // backspace has been pressed, delete the last character from displayString
                    addBufferToRoot(buffer, "delete");
                    keyEvent.consume();
                } else if (characterTyped.charAt(0) == 13) {
                    // ENTER was pressed, process newline
                    newLine();
                }
            }
        }

        // function to add new Text object to buffer
        public void addCharToBuffer(String characterTyped) {
            // add new character to buffer
            Text newChar = new Text(textCenterX, textCenterY, characterTyped);
            newChar.setFont((Font.font(fontName, fontSize)));
            newChar.setTextOrigin(VPos.TOP);
            buffer.addChar(newChar);

            // update textCenterX to the front of the current character
            if (!buffer.isEmpty()) {
                textCenterX = textCenterX + (int) Math.round(buffer.getCurrentItem().getLayoutBounds().getWidth());
            } else {
                textCenterX = 0;
            }
        }

        // function to iterate through buffer and add to root
        public void addBufferToRoot(FastLinkedList<Text> buffer, String action) {
            if (action == "add") {
                root.getChildren().add(buffer.getCurrentItem());
                cursor.setX(textCenterX);
            } else if (action == "delete") {
                root.getChildren().remove(buffer.getCurrentItem());
                // decrement textCenterX to account for the deleted character
                if (!buffer.isEmpty()) {
                    textCenterX = Math.max(0, textCenterX - (int) Math.round(buffer.getCurrentItem().getLayoutBounds().getWidth()));
                } else {
                    textCenterX = 0;
                }
                buffer.deleteChar();
                cursor.setX(textCenterX);
            }
        }

        /** Need to actually store a "\r" character in addition to moving the cursor. That way we know when to move the cursor and current
         * position when we remove the "\r" character from buffer
         */
        // TODO: figure out how to store a "\r" character

        // function to process new lines
        public void newLine() {
            textCenterX = 0;
            textCenterY = textCenterY + (int) buffer.getCurrentItem().getLayoutBounds().getHeight();
            cursor.setX(textCenterX);
            cursor.setY(textCenterY);
        }
    }

    /** An EventHandler to make the cursor blink */
    private class BlinkingCursorEventHandler implements EventHandler<ActionEvent> {
        private int currentState = 0;
        private Color[] states = {Color.BLACK, Color.rgb(0, 0, 0, 0)};

        BlinkingCursorEventHandler() {
            blink();
        }

        private void blink() {
            cursor.setFill(states[currentState]);
            currentState = (currentState + 1) % states.length;
        }

        @Override
        public void handle(ActionEvent event) {
            blink();
        }
    }

    /** Makes the cursor blink periodically */
    public void makeCursorBlink() {
        // Create a timeline that will call the "handle" function of BlinkCursorEventHandler
        // every 0.5 seconds
        final Timeline timeline = new Timeline();
        // The cursor should continue blinking forever.
        timeline.setCycleCount(Timeline.INDEFINITE);
        BlinkingCursorEventHandler cursorChange = new BlinkingCursorEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen
        root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);
        scene.getCursor();
        scene.setCursor(Cursor.TEXT);

        root.getChildren().add(cursor);
        makeCursorBlink();

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