package com.fyself.post.tools;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import javaxt.io.Image;

import java.io.ByteArrayInputStream;

// Inner class containing image information
public class ImageInformation {

    public  int orientation;
    public  double width;
    public  double height;
    public  Image imageFile;
    public  boolean png;

    public ImageInformation(int orientation, double width, double height, Image imageFile, boolean b) {
        this.orientation = orientation;
        this.width = width;
        this.height = height;
        this.imageFile = imageFile;
        this.png = b;
    }

    public String toString() {
        return String.format("%dx%d,%d", this.width, this.height, this.orientation);
    }


    public static ImageInformation readImageInformation(byte[] imageFile) {
        int orientation = 1;
        Image img = new Image(imageFile);
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(imageFile));
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            rotate(orientation,img);
        } catch (Exception e) {
            return new ImageInformation(orientation, img.getWidth(), img.getHeight(),img, true);
        }
        return new ImageInformation(orientation, img.getWidth(), img.getHeight(),img, false);
    }

    public static void rotate(int orientation, Image image) {

        switch(orientation) {
            case 1:
                return;
            case 2:
                image.flip();
                break;
            case 3:
                image.rotate(180.0D);
                break;
            case 4:
                image.flip();
                image.rotate(180.0D);
                break;
            case 5:
                image.flip();
                image.rotate(270.0D);
                break;
            case 6:
                image.rotate(90.0D);
                break;
            case 7:
                image.flip();
                image.rotate(90.0D);
                break;
            case 8:
                image.rotate(270.0D);
        }

    }

}
