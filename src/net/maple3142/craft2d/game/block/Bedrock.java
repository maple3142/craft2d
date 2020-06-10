package net.maple3142.craft2d.game.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bedrock implements Block {
    public static Image image = new Image(Bedrock.class.getResource("/block/bedrock.png").toString());

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void draw(GraphicsContext ctx, int x, int y, int w, int h) {
        ctx.drawImage(image, x, y, w, h);
    }
}
