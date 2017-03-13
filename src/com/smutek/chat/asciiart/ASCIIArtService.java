package com.smutek.chat.asciiart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by damian on 11.03.17.
 */
public class ASCIIArtService {

    public static String getImage() throws IOException {
        int width = 144;
        int height = 32;

        BufferedImage image = ImageIO.read(new File("/home/damian/IdeaProjects/Chat_Client/src/com/smutek/chat/asciiart/2.png"));
        //BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 24));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //graphics.drawString("JAVA", 10, 20);

        //save this image
        //ImageIO.write(image, "png", new File("/users/mkyong/ascii-art.png"));

        StringBuilder resultImage = new StringBuilder();
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {

                sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");

            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            resultImage.append(sb);
            resultImage.append(System.lineSeparator());
        }
        return resultImage.toString();
    }
}
