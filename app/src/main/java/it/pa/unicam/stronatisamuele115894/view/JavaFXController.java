package it.pa.unicam.stronatisamuele115894.view;

import it.pa.unicam.stronatisamuele115894.controller.MapBasedSession;
import it.pa.unicam.stronatisamuele115894.controller.SessionController;
import it.pa.unicam.stronatisamuele115894.controller.SessionInjector;
import it.pa.unicam.stronatisamuele115894.controller.StreamBasedSessionController;
import it.pa.unicam.stronatisamuele115894.io.*;
import it.pa.unicam.stronatisamuele115894.io.modelWriter.FieldWriter;
import it.pa.unicam.stronatisamuele115894.model.GeoPath;
import it.pa.unicam.stronatisamuele115894.model.GeoSection;
import it.pa.unicam.stronatisamuele115894.model.Position;
import it.pa.unicam.stronatisamuele115894.model.RGBColor;
import it.pa.unicam.stronatisamuele115894.utils.ResizableCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class JavaFXController {


    private ResizableCanvas canvas;

    @FXML
    private Pane pane;

    @FXML
    private TextArea codeEditor;

    @FXML
    private Button run;

    @FXML
    private Button loadButton;

    @FXML
    private Button runAll;

    private GraphicsContext gc;

    private SessionController controller;

    private Position turtlePosition;

    private GeoPath lastPath;

    private Circle shape;

    private RGBColor strokeColor;

    private RGBColor fillColor;

    private FileSystemEngine fileSystemInterface;

    private File loadedFile;
    private RGBColor defaultScreenFill;


    public void initialize() {
        createController();
        initializeCanvas(controller);
        initializeSessionElement(controller);
        shape = null;
        setScreenColor(defaultScreenFill);
        drawInitializedElements();
        updateTurtlePositionInCanvas(turtlePosition);
        fileSystemInterface = new DefaultFileSystemEngine(controller);
    }

    private void initializeCanvas(SessionController controller) {
        pane.setPrefSize(controller.getSession().turtle().getField().getWidth(),controller.getSession().turtle().getField().getHeight());
        canvas = new ResizableCanvas(pane.getWidth(), pane.getHeight());
        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());
        pane.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
    }

    private void initializeSessionElement(SessionController controller) {
        turtlePosition = controller.getSession().turtle().getPosition();
        lastPath = controller.getSession().turtle().getField().getDrawingPath();
        strokeColor = controller.getSession().turtle().getStroke();
        fillColor = controller.getSession().turtle().getFill();
        defaultScreenFill = controller.getSession().turtle().getField().getDefaultFill();
    }

    private SessionController createController() {
        controller = new StreamBasedSessionController(
                new MapBasedSession(SessionInjector.provideTurtle(),SessionInjector.provideCommands()));
        return controller;
    }

    private void drawInitializedElements() {
        setScreenColor(defaultScreenFill);
        gc.beginPath();
        gc.moveTo(turtlePosition.x(),turtlePosition.y());
        gc.setLineWidth(controller.getSession().turtle().getPenSize());
        gc.setStroke(setColor(strokeColor));
        gc.setFill(setColor(fillColor));
    }

    private void updateTurtlePositionInCanvas(Position newPosition) {
        int pensize = controller.getSession().turtle().getPenSize();
        if (shape != null) {
            pane.getChildren().remove(shape);
            shape.setCenterX(newPosition.x());
            shape.setCenterY(newPosition.y());
            shape.setRadius((pensize*4));
        } else {
            shape = new Circle(turtlePosition.x()-(pensize*2),turtlePosition.y()-(pensize*2), (pensize*4), setColor(strokeColor));
        }
        pane.getChildren().add(shape);
    }


    private Color setColor(RGBColor color) {
        return (Objects.isNull(color)) ? Color.TRANSPARENT : color.toJavaFXColor();
    }

    @FXML
    void onAbout(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("LOGO4J");
        WebView webView = new WebView();
        final String html = "<html>"
                + "<body>"
                + "<p>Logo commands interpreter and painter. This project  has been used as a final exam for \"Advanced Programming\" at the Bacheleror Degree in Computer Science at University of Camerino.</p>"
                + "<p><b>Author:</b> Samuele Stronati<p>"
                + "<p><b>Copyright:</b> Universit&agrave; degli Studi di Camerino <p>"
                + "</body>"
                + "</html>";
        webView.getEngine().loadContent(html);
        webView.setPrefSize(300, 250);
        alert.getDialogPane().setContent(webView);
        alert.showAndWait();
    }

    @FXML
    void onOpen(ActionEvent event) {
        try {
            FileChooser logoFileChooser = new FileChooser();
            logoFileChooser.setTitle("Select Logo File");
            logoFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Logo Files", "*.logo"));
            loadedFile = logoFileChooser.showOpenDialog(null);
            this.codeEditor.setText(fileSystemInterface.getFileSystemLoader().loadProgramAsString(loadedFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onSave(ActionEvent event) {
        try {
            FileChooser logoFileChooser = new FileChooser();
            logoFileChooser.setTitle("Save Logo Output to File");
            logoFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Logo Output Files", "*.logores"));
            File savedFile = logoFileChooser.showSaveDialog(null);
            String result = new FieldWriter().stringOf(controller.getSession().turtle().getField());
            fileSystemInterface.getFileSystemWriter().writeTo(savedFile,result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void loadProgram(ActionEvent event) {
        fileSystemInterface.getFileSystemLoader().loadProgram(this.codeEditor.getText().lines());
    }

    @FXML
    void runCommand(ActionEvent event) throws InterruptedException {
        runSingleCommand();
    }

    @FXML
    private void runFullProgram(ActionEvent event) throws InterruptedException {
        String c;
        do {
            c = runSingleCommand();
        } while (!Objects.equals(c, String.format("Program completed!%n")));
    }


    private String runSingleCommand() throws InterruptedException {
        String result = controller.runNextCommand();
        if (controller.getLastProcessedCommand().equals("CLEARSCREEN")) {
            clearCanvas();
            return result;
        }
        if (controller.getLastProcessedCommand().equals("SETPENSIZE")) {
            gc.setLineWidth(controller.getSession().turtle().getPenSize());
            updateTurtlePositionInCanvas(turtlePosition);
            return result;
        }
        return processCommand(result);
    }

    private String processCommand(String result) {
        if (controller.getLastProcessedCommand().equals("SETSCREENCOLOR")) {
            setScreenColor(controller.getSession().turtle().getField().getFill());
        }
        checkPath();
        checkForStrokeandFillColor();
        turtlePosition = controller.getSession().turtle().getPosition();
        addPathElement();
        gc.stroke();
        updateTurtlePositionInCanvas(turtlePosition);
        return result;
    }

    private void checkPath() {
        if (controller.getSession().turtle().getField().getDrawingPath().isClosed())
            gc.fill();
        if (lastPath != controller.getSession().turtle().getField().getDrawingPath()) {
            lastPath = controller.getSession().turtle().getField().getDrawingPath();
            gc.closePath();
            gc.beginPath();
            gc.moveTo(turtlePosition.x(),turtlePosition.y());
        }
    }

    private void checkForStrokeandFillColor() {
        if (strokeColor != controller.getSession().turtle().getStroke()) {
            strokeColor = controller.getSession().turtle().getStroke();
            gc.closePath();
            gc.beginPath();
            gc.moveTo(turtlePosition.x(),turtlePosition.y());
            gc.setStroke(setColor(strokeColor));
        }
        if (fillColor != controller.getSession().turtle().getFill()) {
            fillColor = controller.getSession().turtle().getFill();
            gc.setFill(setColor(fillColor));
        }
    }

    private void addPathElement() {
        try {
        GeoSection s = controller.getSession().turtle().getField().getDrawingPath().getPathSections()
                .get(controller.getSession().turtle().getField().getDrawingPath().getPathSections().size()-1);
        if (s.pathElement() instanceof LineTo)
            gc.lineTo(turtlePosition.x(), turtlePosition.y());
        } catch (IndexOutOfBoundsException e) {
            gc.moveTo(turtlePosition.x(), turtlePosition.y());
        }
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, pane.getWidth(), pane.getHeight());
        turtlePosition = controller.getSession().turtle().getPosition();
        drawInitializedElements();
        updateTurtlePositionInCanvas(turtlePosition);
    }

    private void setScreenColor(RGBColor newFill) {
        BackgroundFill backgroundfill = new BackgroundFill(newFill.toJavaFXColor(),null,null);
        this.pane.setBackground(new Background(backgroundfill));
    }
}
