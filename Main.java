import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    private static final int WINDOW_SIZE = 640;

    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;

    private static final int GRID_START_X = 70;
    private static final int GRID_START_Y = 30;

    private final Random random = new Random();

    private Pane root;

    @Override
    public void start(Stage stage) {

        root = new Pane();

        Scene scene = new Scene(root, WINDOW_SIZE, WINDOW_SIZE);

        // Draw the 10x10 grid
        drawGrid();

        // Draw the first set of bars
        drawBars();

        // Create Redraw button
        Button redrawButton = new Button("Redraw");

        redrawButton.setLayoutX(285);
        redrawButton.setLayoutY(580);

        // Redraw the bars when clicked
        redrawButton.setOnAction(event -> {
            drawBars();
        });

        root.getChildren().add(redrawButton);

        stage.setTitle("Random Bar Chart");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Draws the 10x10 grid.
     */
    private void drawGrid() {

        // Horizontal grid lines
        for (int row = 0; row <= GRID_SIZE; row++) {

            Line horizontalLine = new Line(
                    GRID_START_X,
                    GRID_START_Y + row * CELL_SIZE,
                    GRID_START_X + GRID_SIZE * CELL_SIZE,
                    GRID_START_Y + row * CELL_SIZE
            );

            horizontalLine.setStroke(Color.BLACK);

            root.getChildren().add(horizontalLine);
        }

        // Vertical grid lines
        for (int column = 0; column <= GRID_SIZE; column++) {

            Line verticalLine = new Line(
                    GRID_START_X + column * CELL_SIZE,
                    GRID_START_Y,
                    GRID_START_X + column * CELL_SIZE,
                    GRID_START_Y + GRID_SIZE * CELL_SIZE
            );

            verticalLine.setStroke(Color.BLACK);

            root.getChildren().add(verticalLine);
        }
    }

    /**
     * Removes the old bars and creates new random bars.
     */
    private void drawBars() {

        // Remove old bars
        root.getChildren().removeIf(node -> node instanceof Bar);

        // Create 10 bars
        for (int i = 0; i < 10; i++) {

            // X position for each bar
            double x = GRID_START_X
                    + i * CELL_SIZE
                    + CELL_SIZE / 2.0;

            // Bottom of the grid
            double bottomY =
                    GRID_START_Y + GRID_SIZE * CELL_SIZE;

            // Random height from 1 to 8 grid squares
            int heightInCells = 1 + random.nextInt(8);

            double height = heightInCells * CELL_SIZE;

            // Calculate top of the bar
            double topY = bottomY - height;

            // Create the vertical bar
            Bar bar = new Bar(
                    x,
                    bottomY,
                    x,
                    topY
            );

            // Random color
            bar.setStroke(randomColor());

            // Make the bar thicker
            bar.setStrokeWidth(10);

            root.getChildren().add(bar);
        }
    }


    //Creates a random color.

    private Color randomColor() {

        return Color.color(
                random.nextDouble(),
                random.nextDouble(),
                random.nextDouble()
        );
    }


    //Special Line class used to identify the bars.

    private static class Bar extends Line {

        public Bar(
                double startX,
                double startY,
                double endX,
                double endY
        ) {
            super(startX, startY, endX, endY);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
