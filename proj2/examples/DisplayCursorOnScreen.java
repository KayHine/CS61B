import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class DisplayCursorOnScreen extends Application {
    private final Rectangle mainCursor;

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;

    public DisplayCursorOnScreen() {
        // Create a cursor;
        mainCursor = new Rectangle(2, 20);
        mainCursor.setX(250);
        mainCursor.setY(250);
    }

    /** An EventHandler to make the cursor blink */
    private class BlinkingCursorEventHandler implements EventHandler<ActionEvent> {
        private int currentState = 0;
        private Color[] states = {Color.BLACK, Color.rgb(0, 0, 0, 0)};

        BlinkingCursorEventHandler() {
            // Start blinking
            blink();
        }

        private void blink() {
            mainCursor.setFill(states[currentState]);
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
        Group root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the window display/
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);
        scene.getCursor();
        scene.setCursor(Cursor.TEXT);

        // All new Nodes need to be added to the root in order to be displayed.
        root.getChildren().add(mainCursor);
        makeCursorBlink();

        primaryStage.setTitle("Display cursor in the middle of the screen");

        // Boilerplate necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}