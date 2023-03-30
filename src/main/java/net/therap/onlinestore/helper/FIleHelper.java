package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.service.ItemService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * @author nadimmahmud
 * @since 3/22/23
 */
@Service
public class FIleHelper {

    @Value("${item.image.directory}")
    private String imageDirectory;

    @Value("${item.image.placeholder.image}")
    private String placeHolderImageName;

    @Autowired
    private ItemService itemService;

    public void saveItemImage(Item item) throws IOException {

        if (nonNull(item.getImage()) && !item.getImage().isEmpty()) {
            String filename = imageDirectory + item.getName().replace(' ', '_') + "." + FilenameUtils.getExtension(item.getImage().getOriginalFilename());

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
        String placeHolderImage = imageDirectory + placeHolderImageName;

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
