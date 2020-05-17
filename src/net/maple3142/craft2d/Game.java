package net.maple3142.craft2d;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import net.maple3142.craft2d.item.*;

import java.util.HashSet;
import java.util.Set;

public class Game {
    public final static int blockWidth = 32;
    public final static int blockHeight = 32;
    private DoubleProperty widthProperty = new SimpleDoubleProperty();
    private DoubleProperty heightProperty = new SimpleDoubleProperty();
    private Scene scene;
    private Pane root;
    private Canvas mainCanvas;
    private GraphicsContext mainCtx;
    private Canvas entityCanvas;
    private GraphicsContext entityCtx;
    private Canvas sunCanvas;
    private GraphicsContext sunCtx;
    private Canvas uiCanvas;
    private GraphicsContext uiCtx;

    private World world = World.generateRandom(78456);
    private Player player = new Player(world, world.spawnX, world.spawnY);

    private double leftX = world.spawnX - 10;
    private double bottomY = World.worldHeight;

    public Game() {
        this.mainCanvas = new Canvas();
        this.mainCtx = mainCanvas.getGraphicsContext2D();
        this.entityCanvas = new Canvas();
        this.entityCtx = entityCanvas.getGraphicsContext2D();
        this.sunCanvas = new Canvas();
        this.sunCtx = sunCanvas.getGraphicsContext2D();
        this.uiCanvas = new Canvas();
        this.uiCtx = uiCanvas.getGraphicsContext2D();
        this.root = new Pane();
        this.scene = new Scene(root);
        root.getChildren().addAll(sunCanvas, mainCanvas, entityCanvas, uiCanvas);
        root.setOnKeyPressed(this::onKeyPressed);
        root.setOnKeyReleased(this::onKeyReleased);
        root.setOnMouseClicked(this::onMouseClicked);
        root.setOnScroll(this::onScroll);


        heightProperty.bind(scene.heightProperty());
        widthProperty.bind(scene.widthProperty());
        mainCanvas.heightProperty().bind(scene.heightProperty());
        mainCanvas.widthProperty().bind(scene.widthProperty());
        entityCanvas.heightProperty().bind(scene.heightProperty());
        entityCanvas.widthProperty().bind(scene.widthProperty());
        sunCanvas.heightProperty().bind(scene.heightProperty());
        sunCanvas.widthProperty().bind(scene.widthProperty());
        uiCanvas.heightProperty().bind(scene.heightProperty());
        uiCanvas.widthProperty().bind(scene.widthProperty());
    }

    private int lastTimeMs;

    public void start() {
        var timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                loop(l);
            }
        };
        timer.start();
        root.requestFocus();
        lastTimeMs = (int) (System.nanoTime() / 1000000);

        // testing inventory
        player.inventory.storage[0] = new ItemStack(new StoneBlock());
        var stk = new ItemStack(new DirtBlock());
        stk.addItems(7);
        player.inventory.storage[1] = stk;
        player.inventory.storage[8] = new ItemStack(new GrassBlock());
        player.inventory.storage[9] = new ItemStack(new StoneBlock());
    }

    public Scene getScene() {
        return scene;
    }

    private Image sun = new Image(getClass().getResource("/background/sun.png").toString());

    private boolean moveCameraAccordingToPlayer(int width, int height) {
        // returns true if camera is moved

        boolean anyChanged = false;

        int wBlocks = width / blockWidth;
        if (player.position.x - leftX > wBlocks * 0.7) {
            while (player.position.x - leftX > wBlocks * 0.7) {
                leftX += 0.01;
            }
            anyChanged = true;
        }
        if (player.position.x - leftX < wBlocks * 0.3) {
            while (player.position.x - leftX < wBlocks * 0.3) {
                leftX -= 0.01;
            }
            anyChanged = true;
        }
        int hBlocks = height / blockHeight;
        if (player.position.y - bottomY > hBlocks * 0.7) {
            while (player.position.y - bottomY > hBlocks * 0.7) {
                bottomY += 0.01;
            }
            anyChanged = true;
        }
        if (player.position.y - bottomY < hBlocks * 0.3) {
            while (player.position.y - bottomY < hBlocks * 0.3) {
                bottomY -= 0.01;
            }
            anyChanged = true;
        }
        return anyChanged;
    }

    private void loop(long time) {
        int timeMs = (int) (time / 1000000);
        int dt = timeMs - lastTimeMs;
        lastTimeMs = timeMs;
        if (pressedKeys.contains(KeyCode.D)) {
            player.moveRight();
        }
        if (pressedKeys.contains(KeyCode.A)) {
            player.moveLeft();
        }
        if (pressedKeys.contains(KeyCode.W) || pressedKeys.contains(KeyCode.SPACE)) {
            player.jump();
        }
        player.loop(dt);
        int width = (int) widthProperty.get();
        int height = (int) heightProperty.get();
        boolean isCameraMoved = moveCameraAccordingToPlayer(width, height);

        entityCtx.clearRect(0, 0, width, height);
        uiCtx.clearRect(0, 0, width, height);

        player.inventory.drawInventoryBar(uiCtx, width, height);

        double topY = bottomY + (double)(height / blockHeight);
        double rightX = leftX + (double)(width / blockWidth);
        player.draw(entityCtx, leftX, topY);

        {
            // render world blocks
            mainCtx.clearRect(0, 0, width, height);
            for (int y = (int) (bottomY - 1); y <= (topY + 1); y++) {
                for (int x = (int) (leftX - 1); x <= (rightX + 1); x++) {
                    if (x < 0 || x >= World.worldWidth || y < 0 || y >= World.worldHeight) continue;
                    var blk = world.blocks[y][x];
                    if (blk != null) {
                        int pX = (int) ((x - leftX) * blockWidth);
                        int pY = (int) ((topY - y - 1) * blockHeight);
                        if (pX >= -blockWidth && pX < width && pY >= 0 && pY < height) {
                            blk.draw(mainCtx, pX, pY, blockWidth, blockHeight);
                        }
                    }
                }
            }
        }

        if (isCameraMoved) {
            // render sun
            int sunLeft = (int) (width / 5 - leftX / 100);
            int sunTop = (int) (height / 6 + bottomY / 10);
            sunCtx.clearRect(0, 0, width, height);
            sunCtx.drawImage(sun, sunLeft, sunTop, 100, 100);
        }
    }

    private Set<KeyCode> pressedKeys = new HashSet<>();

    public void onKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    public void onKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
    }

    public void onMouseClicked(MouseEvent event) {
        int height = (int) heightProperty.get();
        double x = event.getX();
        double y = event.getY();
        double bx = (leftX + x / blockWidth);
        double by = bottomY + (double)(height / blockHeight) - (y / blockHeight); // don't change it to (height - y) / blockHeight
        if (event.getButton() == MouseButton.PRIMARY) {
            world.blocks[(int) by][(int) bx] = null;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            var stk = player.inventory.getSelectedItemStack();
            if (stk != null) {
                var item = stk.getItem();
                if (item instanceof PlaceableItem) {
                    world.blocks[(int) by][(int) bx] = ((PlaceableItem) item).getPlacedBlock();
                }
            }
        }
    }

    public void onScroll(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            player.inventory.moveSelectionToLeft();
        } else {
            player.inventory.moveSelectionToRight();
        }
    }

}
