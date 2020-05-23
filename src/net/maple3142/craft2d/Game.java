package net.maple3142.craft2d;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.maple3142.craft2d.block.Interactable;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.PlaceableItem;
import net.maple3142.craft2d.item.block.*;
import net.maple3142.craft2d.item.ingredient.Stick;
import net.maple3142.craft2d.item.tool.WoodPickaxe;
import net.maple3142.craft2d.item.tool.WoodShovel;
import net.maple3142.craft2d.item.tool.WoodSword;

import java.util.HashSet;
import java.util.Set;

public class Game {
    public final static int blockWidth = 32;
    public final static int blockHeight = 32;
    public MouseTracker mouseTracker = new MouseTracker();
    private DoubleProperty widthProperty = new SimpleDoubleProperty();
    private DoubleProperty heightProperty = new SimpleDoubleProperty();
    private Scene scene;
    private Pane root;
    private Pane gameLayer;
    private Pane uiBgLayer;
    private Pane uiLayer;
    private Canvas mainCanvas;
    private GraphicsContext mainCtx;
    private Canvas entityCanvas;
    private GraphicsContext entityCtx;
    private Canvas sunCanvas;
    private GraphicsContext sunCtx;
    private Canvas invBarCanvas;
    private GraphicsContext invBarCtx;
    private Canvas uiBgCanvas;
    private GraphicsContext uiBgCtx;
    private Canvas uiCanvas;
    private GraphicsContext uiCtx;
    private World world = World.generateRandom(78456);
    private Player player = new Player(world, world.spawnX, world.spawnY);
    private double leftX = world.spawnX - 10;
    private double bottomY = World.worldHeight;
    private UiOpenable currentUi = null;
    private int lastTimeMs;
    private Image sun = new Image(getClass().getResource("/background/sun.png").toString());
    private int endBreakingTime = 0;
    private int currentBreakingX;
    private int currentBreakingY;
    private Set<KeyCode> pressedKeys = new HashSet<>();

    public Game() {
        this.mainCanvas = new Canvas();
        this.mainCtx = mainCanvas.getGraphicsContext2D();
        this.entityCanvas = new Canvas();
        this.entityCtx = entityCanvas.getGraphicsContext2D();
        this.sunCanvas = new Canvas();
        this.sunCtx = sunCanvas.getGraphicsContext2D();
        this.invBarCanvas = new Canvas();
        this.invBarCtx = invBarCanvas.getGraphicsContext2D();

        this.uiBgCanvas = new Canvas();
        this.uiBgCtx = uiBgCanvas.getGraphicsContext2D();

        this.uiCanvas = new Canvas();
        this.uiCtx = uiCanvas.getGraphicsContext2D();

        this.root = new Pane();
        this.gameLayer = new Pane();
        this.uiBgLayer = new Pane();
        this.uiLayer = new Pane();
        this.root.getChildren().addAll(gameLayer, uiBgLayer, uiLayer);
        root.addEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        root.addEventHandler(KeyEvent.KEY_RELEASED, this::onKeyReleased);
        root.addEventHandler(ScrollEvent.SCROLL, this::onScroll);
        root.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
        mouseTracker.bind(root);
        this.scene = new Scene(root);

        gameLayer.getChildren().addAll(sunCanvas, mainCanvas, entityCanvas, invBarCanvas);
        uiBgLayer.getChildren().addAll(uiBgCanvas);
        uiLayer.getChildren().addAll(uiCanvas);

        heightProperty.bind(scene.heightProperty());
        widthProperty.bind(scene.widthProperty());
        mainCanvas.heightProperty().bind(scene.heightProperty());
        mainCanvas.widthProperty().bind(scene.widthProperty());
        entityCanvas.heightProperty().bind(scene.heightProperty());
        entityCanvas.widthProperty().bind(scene.widthProperty());
        sunCanvas.heightProperty().bind(scene.heightProperty());
        sunCanvas.widthProperty().bind(scene.widthProperty());
        invBarCanvas.heightProperty().bind(scene.heightProperty());
        invBarCanvas.widthProperty().bind(scene.widthProperty());

        uiBgCanvas.heightProperty().bind(scene.heightProperty());
        uiBgCanvas.widthProperty().bind(scene.widthProperty());

        uiCanvas.heightProperty().bind(scene.heightProperty());
        uiCanvas.widthProperty().bind(scene.widthProperty());
    }

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
        player.inventory.storage[0] = new ItemStack(new StoneBlock(), 8);
        player.inventory.storage[1] = new ItemStack(new DirtBlock(), 39);
        player.inventory.storage[2] = new ItemStack(new WoodSword());
        player.inventory.storage[3] = new ItemStack(new Stick(), 64);
        player.inventory.storage[4] = new ItemStack(new WoodPickaxe());
        player.inventory.storage[5] = new ItemStack(new WoodShovel());
        player.inventory.storage[6] = new ItemStack(new CraftingTableBlock());
        player.inventory.storage[8] = new ItemStack(new GrassBlock(), 64);
        player.inventory.storage[9] = new ItemStack(new PlankOakBlock(), 64);
        player.inventory.storage[16] = new ItemStack(new LogOakBlock(), 64);
        player.inventory.storage[17] = new ItemStack(new StoneBlock(), 13);
        player.inventory.storage[21] = new ItemStack(new StoneBlock());
        player.inventory.storage[25] = new ItemStack(new GrassBlock(), 64);
        player.inventory.storage[28] = new ItemStack(new StoneBlock());
        player.inventory.storage[35] = new ItemStack(new DirtBlock(), 26);
    }

    public Scene getScene() {
        return scene;
    }

    private boolean moveCameraAccordingToPlayer(int width, int height) {
        // returns true if camera is moved

        boolean anyChanged = false;

        int wBlocks = width / blockWidth;
        if (player.position.x - leftX > wBlocks * 0.7) {
            leftX += (player.position.x - leftX - wBlocks * 0.7);
            anyChanged = true;
        }
        if (player.position.x - leftX < wBlocks * 0.3) {
            leftX += (player.position.x - leftX - wBlocks * 0.3);
            anyChanged = true;
        }
        int hBlocks = height / blockHeight;
        if (player.position.y - bottomY > hBlocks * 0.7) {
            bottomY += (player.position.y - bottomY - hBlocks * 0.7);
            anyChanged = true;
        }
        if (player.position.y - bottomY < hBlocks * 0.3) {
            bottomY += (player.position.y - bottomY - hBlocks * 0.3);
            anyChanged = true;
        }
        return anyChanged;
    }

    private void loop(long time) {
        int timeMs = (int) (time / 1000000);
        int dt = timeMs - lastTimeMs;
        lastTimeMs = timeMs;

        {
            if (timeMs >= endBreakingTime && endBreakingTime != 0) {
                endBreaking(false);
            }
        }

        {
            int height = (int) heightProperty.get();
            double x = mouseTracker.getX();
            double y = mouseTracker.getY();
            int bx = (int) (leftX + x / blockWidth);
            int by = (int) (bottomY + (double) (height / blockHeight) - (y / blockHeight)); // don't change it to (height - y) / blockHeight
            if (mouseTracker.isPrimaryPressed()) {
                startBreaking(bx, by);
            } else if (endBreakingTime != 0) {
                endBreaking(true);
            }
            if (mouseTracker.isSecondaryPressed()) {
                var stk = player.inventory.getSelectedItemStack();
                if (stk != null && world.blocks[by][bx] == null) {
                    var item = stk.getItem();
                    if (item instanceof PlaceableItem) {
                        world.blocks[by][bx] = ((PlaceableItem) item).getPlacedBlock();
                        if (stk.getItemsNum() == 1) player.inventory.setSelectedItemStack(null);
                        else stk.removeItemsNum(1);
                    }
                } else if (world.blocks[by][bx] instanceof Interactable) {
                    var blk = (Interactable) (world.blocks[by][bx]);
                    blk.onInteracted(this);
                }
            }
        }

        // move player
        {
            if (currentUi == null) {
                if (pressedKeys.contains(KeyCode.D)) {
                    player.moveRight();
                }
                if (pressedKeys.contains(KeyCode.A)) {
                    player.moveLeft();
                }
                if (pressedKeys.contains(KeyCode.W) || pressedKeys.contains(KeyCode.SPACE)) {
                    player.jump();
                }
            }
        }
        player.processMovement(dt);

        int width = (int) widthProperty.get();
        int height = (int) heightProperty.get();
        boolean isCameraMoved = moveCameraAccordingToPlayer(width, height);

        entityCtx.clearRect(0, 0, width, height);
        invBarCtx.clearRect(0, 0, width, height);

        player.inventory.drawInventoryBar(invBarCtx, width, height);

        // ui rendering
        {
            uiBgCtx.clearRect(0, 0, width, height);
            uiCtx.clearRect(0, 0, width, height);
            if (currentUi != null) {
                uiBgLayer.setBlendMode(BlendMode.DARKEN);
                uiBgCtx.setFill(Color.rgb(64, 64, 64, 0.5));
                uiBgCtx.fillRect(0, 0, width, height);
                currentUi.drawUi(uiCtx, mouseTracker, width, height);
            }
        }


        double topY = bottomY + (double) (height / blockHeight);
        double rightX = leftX + (double) (width / blockWidth);
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

    private void startBreaking(int x, int y) {
        if (x != currentBreakingX || y != currentBreakingY) {
            var current = (int) (System.nanoTime() / 1000000);
            currentBreakingX = x;
            currentBreakingY = y;
            var tool = player.inventory.getSelectedTool();
            var time = BreakingTimeCalculator.calculate(tool, world.blocks[currentBreakingY][currentBreakingX]);
            endBreakingTime = current + time;
        }
    }

    private void endBreaking(boolean interrupted) {
        if (!interrupted) {
            var blk = world.blocks[currentBreakingY][currentBreakingX];
            if (blk != null) {
                world.blocks[currentBreakingY][currentBreakingX] = null;
                var tool = player.inventory.getSelectedTool();
                var drop = blk.getDroppedItem(tool);
                System.out.println(drop);
            }
        }
        endBreakingTime = 0;
    }

    public void onKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    public void onKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());

        if (event.getCode() == KeyCode.E) {
            if (currentUi == null) {
                openUi(player.inventory);
            } else {
                currentUi.onClosed();
                currentUi = null;
            }
        }
    }

    public void onScroll(ScrollEvent event) {
        if (currentUi == null) {
            if (event.getDeltaY() > 0) {
                player.inventory.moveSelectionToLeft();
            } else {
                player.inventory.moveSelectionToRight();
            }
        }
    }

    public void onMousePressed(MouseEvent event) {
        if (currentUi != null) {
            currentUi.handleMousePressed(event, widthProperty.get(), heightProperty.get());
        }
    }

    public void openUi(UiOpenable ui) {
        currentUi = ui;
        currentUi.onOpened();
    }
}
