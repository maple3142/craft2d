package net.maple3142.craft2d.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PlankOak implements Block {
    public static Image image = new Image(PlankOak.class.getResource("/block/plank_oak.png").toString());

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void draw(GraphicsContext ctx, int x, int y, int w, int h) {
        ctx.drawImage(image, x, y, w, h);
    }
}
