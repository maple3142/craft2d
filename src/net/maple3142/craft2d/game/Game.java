package net.maple3142.craft2d.game;

import com.google.gson.annotations.Expose;
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
import javafx.scene.shape.ArcType;
import net.maple3142.craft2d.game.block.Interactable;
import net.maple3142.craft2d.game.block.Loopable;
import net.maple3142.craft2d.game.entity.Entity;
import net.maple3142.craft2d.game.entity.FloatingItem;
import net.maple3142.craft2d.game.entity.Player;
import net.maple3142.craft2d.game.item.PlaceableItem;
import net.maple3142.craft2d.game.key.KeyBinding;
import net.maple3142.craft2d.game.key.KeyType;
import net.maple3142.craft2d.game.ui.BlockBreaking;
import net.maple3142.craft2d.game.ui.UiOpenable;
import net.maple3142.craft2d.game.ui.pause.PauseUi;
import net.maple3142.craft2d.game.utils.Callback;
import net.maple3142.craft2d.game.utils.MouseTracker;
import net.maple3142.craft2d.game.utils.Vector2;

import java.util.HashSet;
import java.util.Set;

public class Game {

    public final static int blockWidth = 32;
    public final static int blockHeight = 32;
    public static final int mouseRange = 6;
    private final DoubleProperty widthProperty = new SimpleDoubleProperty();
    private final DoubleProperty heightProperty = new SimpleDoubleProperty();
    private final Scene scene;
    private final Pane root;
    private final Pane gameLayer;
    private final Pane uiBgLayer;
    private final Pane uiLayer;
    private final Canvas mainCanvas;
    private final GraphicsContext mainCtx;
    private final Canvas entityCanvas;
    private final GraphicsContext entityCtx;
    private final Canvas sunCanvas;
    private final GraphicsContext sunCtx;
    private final Canvas hudCanvas;
    private final GraphicsContext hudCtx;
    private final Canvas uiBgCanvas;
    private final GraphicsContext uiBgCtx;
    private final Canvas uiCanvas;
    private final GraphicsContext uiCtx;
    private final Music music = new Music();
    private final Image sun = new Image(getClass().getResource("/background/sun.png").toString());
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    private final BlockBreaking blockBreaking = new BlockBreaking(this);
    public MouseTracker mouseTracker = new MouseTracker();
    @Expose
    public World world;
    @Expose
    public Player player;
    @Expose
    public Set<Entity> entities = new HashSet<>();
    @Expose
    public Set<Loopable> loopables = new HashSet<>();
    private final boolean DEBUG_FREE_CAMERA_MOVING = false;
    private UiOpenable currentUi = null;
    private int lastTimeMs;
    @Expose
    private double leftX;
    @Expose
    private double bottomY;
    private AnimationTimer timer;
    private Callback exitCallback;
    private KeyBinding keyBinding = new KeyBinding();

    public Game() {
        this.mainCanvas = new Canvas();
        this.mainCtx = mainCanvas.getGraphicsContext2D();
        this.entityCanvas = new Canvas();
        this.entityCtx = entityCanvas.getGraphicsContext2D();
        this.sunCanvas = new Canvas();
        this.sunCtx = sunCanvas.getGraphicsContext2D();
        this.hudCanvas = new Canvas();
        this.hudCtx = hudCanvas.getGraphicsContext2D();
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

        gameLayer.getChildren().addAll(sunCanvas, mainCanvas, entityCanvas, hudCanvas);
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
        hudCanvas.heightProperty().bind(scene.heightProperty());
        hudCanvas.widthProperty().bind(scene.widthProperty());

        uiBgCanvas.heightProperty().bind(scene.heightProperty());
        uiBgCanvas.widthProperty().bind(scene.widthProperty());

        uiCanvas.heightProperty().bind(scene.heightProperty());
        uiCanvas.widthProperty().bind(scene.widthProperty());

        entityCtx.setFont(FontProvider.minecraftFontNormal);
        hudCtx.setFont(FontProvider.minecraftFontNormal);
        mainCtx.setFont(FontProvider.minecraftFontNormal);
        sunCtx.setFont(FontProvider.minecraftFontNormal);
        uiBgCtx.setFont(FontProvider.minecraftFontNormal);
        uiCtx.setFont(FontProvider.minecraftFontNormal);
    }

    public Game(long seed) {
        this();
        world = World.generateRandom(seed);
        player = new Player(world, world.spawnX + 0.5, world.spawnY + 1);
        leftX = world.spawnX - 10;
        bottomY = World.worldHeight;
    }

    public void setKeyBinding(KeyBinding binding) {
        keyBinding = binding;
    }

    public void start() {
        music.playNext();
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                loop(l);
            }
        };
        timer.start();
        root.requestFocus();
        lastTimeMs = (int) (System.nanoTime() / 1000000);
        moveCameraAccordingToPlayer((int) widthProperty.get(), (int) heightProperty.get());
    }

    public void stop() {
        music.stop();
        timer.stop();
        if (exitCallback != null) {
            exitCallback.call();
        }
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

    private boolean isInRange(double x, double y) {
        int height = (int) heightProperty.get();
        double topY = bottomY + (double) (height / blockHeight);
        double pX = (player.position.x - leftX) * Game.blockWidth;
        double pY = (topY - player.position.y) * Game.blockHeight;
        double dx = x - pX;
        double dy = y - pY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist <= mouseRange * blockWidth;
    }

    private int getBx(double x) {
        return (int) (leftX + x / blockWidth);
    }

    private int getBy(double y) {
        int height = (int) heightProperty.get();
        return (int) (bottomY + (double) (height / blockHeight) - (y / blockHeight)); // don't change it to (height - y) / blockHeight
    }

    private void loop(long time) {
        int timeMs = (int) (time / 1000000);
        int dt = timeMs - lastTimeMs;
        lastTimeMs = timeMs;

        int width = (int) widthProperty.get();
        int height = (int) heightProperty.get();
        double topY = bottomY + (double) (height / blockHeight);
        double rightX = leftX + (double) (width / blockWidth);

        {
            if (blockBreaking.isBreaking && timeMs >= blockBreaking.endBreakingTime) {
                var pos = new Vector2(blockBreaking.currentBreakingX + 0.5, blockBreaking.currentBreakingY + 0.5);
                var dropped = blockBreaking.endBreaking(true);
                if (dropped != null) {
                    entities.add(new FloatingItem(dropped, pos));
                }
                var tool = player.inventory.getSelectedTool();
                if (tool != null) {
                    tool.reduceDurabilityByOne();
                    if (tool.isBroken()) {
                        player.inventory.setSelectedItemStack(null);
                    }
                }
            }
        }

        {
            double x = mouseTracker.getX();
            double y = mouseTracker.getY();
            int bx = getBx(x);
            int by = getBy(y);
            boolean inRange = isInRange(x, y);

            if (mouseTracker.isPrimaryPressed()) {
                if (blockBreaking.isBreaking) {
                    if ((bx != blockBreaking.currentBreakingX || by != blockBreaking.currentBreakingY) || !inRange) {
                        blockBreaking.endBreaking(false);
                    }
                } else if (inRange && world.blocks[by][bx] != null) {
                    blockBreaking.startBreaking(bx, by);
                }
            } else if (blockBreaking.isBreaking) { // if mouse isn't pressed while breaking, then it is interrupted
                blockBreaking.endBreaking(false);
            }

        }

        // move entities
        {
            if (currentUi == null) {
                if (pressedKeys.contains(keyBinding.getKeyCode(KeyType.RIGHT))) {
                    player.moveRight();
                }
                if (pressedKeys.contains(keyBinding.getKeyCode(KeyType.LEFT))) {
                    player.moveLeft();
                }
                if (pressedKeys.contains(keyBinding.getKeyCode(KeyType.JUMP1)) ||
                        pressedKeys.contains(keyBinding.getKeyCode(KeyType.JUMP2))) {
                    player.jump();
                }
                if (pressedKeys.contains(keyBinding.getKeyCode(KeyType.FACE))) {
                    player.faceFront();
                }
                if (DEBUG_FREE_CAMERA_MOVING) {
                    if (pressedKeys.contains(KeyCode.RIGHT)) {
                        leftX++;
                    }
                    if (pressedKeys.contains(KeyCode.LEFT)) {
                        leftX--;
                    }
                    if (pressedKeys.contains(KeyCode.UP)) {
                        bottomY++;
                    }
                    if (pressedKeys.contains(KeyCode.DOWN)) {
                        bottomY--;
                    }
                }
            }
        }
        player.loop(world, dt);
        var entIt = entities.iterator();
        while (entIt.hasNext()) {
            var ent = entIt.next();
            ent.loop(world, dt);
            if (Vector2.subtract(player.position, ent.getPosition()).norm() <= 1.5) {
                boolean remove = player.onInteractedWithEntity(ent);
                if (remove) {
                    entIt.remove();
                }
            }
        }

        for (var l : loopables) {
            l.loop(dt);
        }

        if (!DEBUG_FREE_CAMERA_MOVING) moveCameraAccordingToPlayer(width, height);

        entityCtx.clearRect(0, 0, width, height);

        // hud rendering
        {
            hudCtx.clearRect(0, 0, width, height);

            if ((mouseTracker.isPrimaryPressed() || mouseTracker.isSecondaryPressed()) && currentUi == null) {
                // draw interactable circle
                hudCtx.setLineWidth(3);
                hudCtx.setStroke(Color.BLACK);
                double r = mouseRange * blockWidth;
                double pX = (player.position.x - leftX) * Game.blockWidth;
                double pY = (topY - player.position.y) * Game.blockHeight;
                hudCtx.strokeArc(pX - r, pY - r, 2 * r, 2 * r, 0, 360, ArcType.OPEN);
            }

            if (blockBreaking.isBreaking) {
                double percent = blockBreaking.getBreakingPercentage(timeMs);
                blockBreaking.drawProgressBar(hudCtx, mouseTracker, percent);
            }

            player.inventory.drawInventoryBar(hudCtx, width, height);
        }


        // ui rendering
        {
            uiBgCtx.clearRect(0, 0, width, height);
            uiCtx.clearRect(0, 0, width, height);
            if (currentUi != null) {
                uiBgLayer.setBlendMode(BlendMode.DARKEN);
                uiBgCtx.setFill(Color.rgb(64, 64, 64, 0.5));
                uiBgCtx.fillRect(0, 0, width, height);
                currentUi.drawUi(uiCtx, mouseTracker, width, height, this);
            }
        }

        // draw entities
        player.draw(entityCtx, leftX, topY);
        for (var ent : entities) {
            ent.draw(entityCtx, leftX, topY);
        }

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
                        if (pX >= -blockWidth && pX < width && pY >= -blockHeight && pY < height) {
                            blk.draw(mainCtx, pX, pY, blockWidth, blockHeight);
                        }
                    }
                }
            }
        }

        // render sun
        int sunLeft = (int) (width / 5 - leftX / 100);
        int sunTop = (int) (height / 6 + bottomY / 10);
        sunCtx.clearRect(0, 0, width, height);
        sunCtx.drawImage(sun, sunLeft, sunTop, 100, 100);
    }

    public void onKeyReleased(KeyEvent event) {
        var code = event.getCode();
        if (event.getCharacter().equals("\u0000") && code == KeyCode.UNDEFINED) {
            code = KeyCode.SPACE; // I don't know why it is sometimes UNDEFINED
        }
        pressedKeys.remove(code);
    }

    private PauseUi pauseUi = new PauseUi(this);

    public void onKeyPressed(KeyEvent event) {
        var code = event.getCode();
        if (event.getCharacter().equals("\u0000") && code == KeyCode.UNDEFINED) {
            code = KeyCode.SPACE; // I don't know why it is sometimes UNDEFINED
        }
        pressedKeys.add(code);

        if (code == keyBinding.getKeyCode(KeyType.OPEN_INVENTORY)) {
            if (currentUi == null) {
                openUi(player.inventory);
            } else if (!(currentUi instanceof PauseUi)) {
                // pause ui shouldn't be closed by E
                closeUi();
            }
        }

        if (code == keyBinding.getKeyCode(KeyType.PAUSE)) {
            if (currentUi == null) {
                openUi(pauseUi);
            } else {
                closeUi();
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
            if (blockBreaking.isBreaking) {
                // switching tools while breaking results in interruption
                blockBreaking.endBreaking(false);
            }
        }
    }

    public void onMousePressed(MouseEvent event) {
        if (currentUi != null) {
            currentUi.handleMousePressed(event, widthProperty.get(), heightProperty.get(), this);
        } else {
            double x = event.getX();
            double y = event.getY();
            int bx = getBx(x);
            int by = getBy(y);
            boolean inRange = isInRange(x, y);

            if (event.isSecondaryButtonDown() && inRange) {
                var stk = player.inventory.getSelectedItemStack();
                int px = (int) player.position.x;
                int py = (int) player.position.y;
                boolean isOnPlayer = (bx == px) && (by == py || by == py - 1);
                if (stk != null && world.blocks[by][bx] == null && !isOnPlayer) {
                    var item = stk.getItem();
                    if (item instanceof PlaceableItem) {
                        var blk = ((PlaceableItem) item).getPlacedBlock();
                        world.blocks[by][bx] = blk;
                        if (blk instanceof Loopable) {
                            loopables.add((Loopable) blk);
                        }
                        if (stk.getItemsNum() == 1) player.inventory.setSelectedItemStack(null);
                        else stk.removeItemsNum(1);
                    }
                } else if (world.blocks[by][bx] instanceof Interactable) {
                    var blk = (Interactable) (world.blocks[by][bx]);
                    blk.onInteracted(this);
                }
            }
        }
    }

    public void openUi(UiOpenable ui) {
        currentUi = ui;
        currentUi.onOpened(this);
    }

    public void closeUi() {
        currentUi.onClosed(this);
        currentUi = null;
    }

    public void setExitCallback(Callback f) {
        exitCallback = f;
    }
}
