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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Iterator;

public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private Group root;
    private Scene scene;
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

            // Initialize some empty FastLinkedList to store the keys pressed
            buffer = new FastLinkedList<Text>();

            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simplier!
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
                    addBufferToRoot(buffer, characterTyped, "add");
                    keyEvent.consume();
                } else if (characterTyped.charAt(0) == 8) {
                    // backspace has been pressed, delete the last character from displayString
                    addBufferToRoot(buffer, "", "delete");
                    keyEvent.consume();
                } else if (characterTyped.charAt(0) == 13) {
                    // ENTER was pressed, process newline
                    newLine();
                }
            }

            // Use the KEY_PRESSED event type to handle arrow LEFT, RIGHT, UP, and DOWN
            if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                KeyCode keyPressed = keyEvent.getCode();

                if (keyPressed == KeyCode.LEFT) {
                    if (!buffer.isEmpty()) {
                        cursor.setX(cursor.getX() - (int) Math.round(buffer.getCurrentItem().getLayoutBounds().getWidth()));
                        cursor.setY((int) Math.round(buffer.getCurrentItem().getY()));
                        buffer.moveCurrentNodeBack();
                    }
                } else if (keyPressed == KeyCode.RIGHT) {
                    // allow moving currentNode to the right until we reach the end
                    if (!buffer.isEmpty()) {
                        buffer.moveCurrentNodeNext();
                        cursor.setX(cursor.getX() + (int) Math.round(buffer.getCurrentItem().getLayoutBounds().getWidth()));
                        cursor.setY((int) Math.round(buffer.getCurrentItem().getY()));
                    }
                } else if (keyPressed == KeyCode.UP) {

                } else if (keyPressed == KeyCode.DOWN) {

                }
            }
        }

        // TODO: need to make the cursor position the spot to add new characters

        // function to add new Text object to buffer
        public void addCharToBuffer(String characterTyped) {
            // add new character to buffer
            Text newChar = new Text(cursor.getX(), cursor.getY(), characterTyped);
            newChar.setFont((Font.font(fontName, fontSize)));
            newChar.setTextOrigin(VPos.TOP);
            buffer.addChar(newChar);

            // Shift all the letter to the right if it exists
            shiftRow((int) Math.round(newChar.getLayoutBounds().getWidth()));

            // update textCenterX to the front of the current character
            // so we know right away where the next character will go
            if (!buffer.isEmpty()) {
                cursor.setX(cursor.getX() + (int) Math.round(buffer.getCurrentItem().getLayoutBounds().getWidth()));
                // check to see if we need to go to a new line
                //handleWordWrap();
            } else {
                cursor.setX(0);
            }
        }

        // function to iterate through buffer and add to root
        public void addBufferToRoot(FastLinkedList<Text> buffer, String characterTyped, String action) {
            if (action == "add") {
                addCharToBuffer(characterTyped);
                root.getChildren().add(buffer.getCurrentItem());
                // gonna need to update Y position when we read the end of the line
            } else if (action == "delete" && !buffer.isEmpty()) {
                root.getChildren().remove(buffer.getCurrentItem());
                shiftRow((int) Math.round(buffer.getCurrentItem().getLayoutBounds().getWidth()) * -1);
                buffer.deleteChar();

                // set textCenterX to the current character + it's width
                // set textCenterY to the current character's Y-position
                if (!buffer.isEmpty()) {
                    cursor.setX((int) Math.round(buffer.getCurrentItem().getX() + buffer.getCurrentItem().getLayoutBounds().getWidth()));
                    cursor.setY((int) Math.round(buffer.getCurrentItem().getY()));
                } else {
                    cursor.setX(0);
                    cursor.setY(Math.max(0, (int)cursor.getY() - STARTING_FONT_SIZE));
                }
            }
        }

        /** Need to actually store a "\r" character in addition to moving the cursor. That way we know when to move the cursor and current
         * position when we remove the "\r" character from buffer
         */
        // TODO: figure out how to store a "\r" character (not sure if i actually need to do this)

        // function to process new lines
        public void newLine() {
            cursor.setX(0);
            cursor.setY((int) cursor.getY() + STARTING_FONT_SIZE);
        }

        public void shiftRow(int shiftWidth) {
            Iterator it = buffer.iterator();
            while (it.hasNext()) {
                Text txt = (Text) it.next();
                txt.setX(txt.getX() + shiftWidth);
            }
        }

        public void handleWordWrap() {
            if (cursor.getX() > scene.getWidth()) {
                while (buffer.getCurrentItem().getText().charAt(0) != ' ') {
                    buffer.moveCurrentNodeBack();
                }

                newLine();
                Iterator it = buffer.iterator();
                while (it.hasNext()) {
                    Text txt = (Text) it.next();
                    txt.setX(cursor.getX());
                    txt.setY(cursor.getY());

                    cursor.setX(cursor.getX() + (int) Math.round(buffer.getCurrentItem().getLayoutBounds().getWidth()));
                }
            }
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
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);
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