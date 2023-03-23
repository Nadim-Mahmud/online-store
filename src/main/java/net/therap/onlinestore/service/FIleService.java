package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.Item;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static net.therap.onlinestore.constant.Constants.IMAGES_DIR;

/**
 * @author nadimmahmud
 * @since 3/22/23
 */
@Service
public class FIleService {

    private static final String PLACE_HOLDER_IMAGE = "placeholder.png";

    @Autowired
    private ItemService itemService;

    public void saveItemImage(Item item) throws IOException {

        if (nonNull(item.getImage()) && !item.getImage().isEmpty()) {
            String filename = IMAGES_DIR + item.getName().replace(' ', '_') + "." + FilenameUtils.getExtension(item.getImage().getOriginalFilename());

            try {
                item.getImage().transferTo(new File(filename));
            } catch (IOException e) {
                throw e;
            }

            item.setImagePath(filename);
        }
    }

    public byte[] getImageByItemId(int itemId) {
        String imagePath = itemService.findById(itemId).getImagePath();
        String placeHolderImage = IMAGES_DIR + PLACE_HOLDER_IMAGE;

        if (Objects.isNull(imagePath)) {
            imagePath = placeHolderImage;
        }

        try {
            File imageFile = new File(imagePath);
            BufferedImage originalImage = ImageIO.read(imageFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, FilenameUtils.getExtension(imagePath), baos);

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();

            return new byte[1];
        }
    }
}
