package net.maple3142.craft2d.game.ui.pause;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.maple3142.craft2d.game.FontProvider;
import net.maple3142.craft2d.game.utils.Callback;
import net.maple3142.craft2d.game.utils.MouseTracker;

public class MinecraftCanvasButton {
    final public static double width = 198 * 2.2;
    final public static double height = 18 * 2.2;
    final private Image imgBtnNormal = new Image(getClass().getResource("/ui/button_normal.png").toString(), width, height, false, false);
    final private Image imgBtnHovered = new Image(getClass().getResource("/ui/button_hovered.png").toString(), width, height, false, false);

    private String text;
    private double x;
    private double y;

    public MinecraftCanvasButton(String text) {
        this.text = text;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(GraphicsContext ctx, MouseTracker mouse) {
        var mx = mouse.getX();
        var my = mouse.getY();
        boolean hovered = x <= mx && mx <= x + width && y <= my && my <= y + height;
        var img = hovered ? imgBtnHovered : imgBtnNormal;
        ctx.drawImage(img, x, y);
        ctx.setFill(Color.WHITE);
        ctx.setFont(FontProvider.minecraftFontBig);
        ctx.setTextBaseline(VPos.CENTER);
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.fillText(text, x + width / 2, y + height / 2);
    }

    private Callback onClicked;

    public void setOnClicked(Callback cb) {
        this.onClicked = cb;
    }

    public void handleMousePressed(MouseEvent event) {
        double mx = event.getX();
        double my = event.getY();
        boolean clicked = x <= mx && mx <= x + width && y <= my && my <= y + height;
        if (onClicked != null && clicked) {
            onClicked.call();
        }
    }
}
