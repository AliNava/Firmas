/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimizedproductions.playingfirmas;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.IplImage;
import static org.bytedeco.javacpp.opencv_core.NORM_L2;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import org.bytedeco.javacpp.opencv_features2d;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_MEDIAN;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY_INV;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvSmooth;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;
import org.bytedeco.javacpp.opencv_xfeatures2d;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 *
 * @author herib_000
 */
public class BusinessLogic {
    
    // Global Variables
    private OpenCVFrameConverter.ToIplImage converter1;
    private Java2DFrameConverter converter2;
    private OpenCVFrameConverter.ToMat converter3 ;
    
    private IplImage image1, image2, backup;
    
    
    public BusinessLogic() {
        converter1 = new OpenCVFrameConverter.ToIplImage();
        converter2 = new Java2DFrameConverter();
        converter3 = new OpenCVFrameConverter.ToMat();
    }
    
    public BufferedImage iplImageToBufferedImage(IplImage src) {
        Frame frame = converter1.convert(src);
        return converter2.getBufferedImage(frame,1);
    }
    public opencv_core.Mat iplImageToMat(IplImage src){
        return converter3.convert( converter3.convert(src ) );
    }
    public byte[] iplImageToByteArray(IplImage image){
        BufferedImage im = iplImageToBufferedImage(image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] barr = null;
        try{
            ImageIO.write(im,"jpg",baos);
            baos.flush();
            barr = baos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                baos.close();
            } catch (IOException ex) {
                Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return barr;
    }

    public IplImage getImage1() {   return image1;  }
    public void setImage1(IplImage image1) {    this.image1 = image1;   }
    public IplImage getImage2() {   return image2;  }
    public void setImage2(IplImage image2) {    this.image2 = image2;   }
    public IplImage getBackup() {   return backup;  }
    public void setBackup(IplImage backup) {    this.backup = backup;   }
    
    
    
    
    
    
    public static interface GetFileHelper{
        void onFileSelected(IplImage image);
    }
    public void getUserFile( GetFileHelper listener ){
        String directory = "D:\\Downloads";
        JFileChooser selector = new JFileChooser(directory);
        int r = selector.showOpenDialog(null);
        if(r == JFileChooser.APPROVE_OPTION){
            File selected_file = selector.getSelectedFile();
            // Verify file
            if (!selected_file.exists()) {
                JOptionPane.showMessageDialog(null, "Image file does not exist: " + selected_file.getAbsolutePath() );
                return;
            }
            // Read input image
            IplImage image = cvLoadImage(selected_file.getAbsolutePath());
            if (image == null) {
                JOptionPane.showMessageDialog(null, "Couldn't load image: " + selected_file.getAbsolutePath());
                return;
            }
            if( listener != null )
                listener.onFileSelected(image);
	}
    }
    public IplImage crop_image(IplImage image, JLabel container){
        if (image == null){
            JOptionPane.showMessageDialog(null, "Image Is Null");
            return null;
        }
        int percent_width = 42,
                percent_height = 28,
                percent_x_padding = 6,
                percent_y_padding = 15,
                real_x_padding = (int) (image.width() * ( percent_x_padding / 100.0 )),
                real_y_padding = (int) (image.height()* ( percent_y_padding / 100.0 )),
                new_width = (int) (image.width() * ( percent_width / 100.0 )),
                new_height = (int) (image.height()* ( percent_height / 100.0 )),
                new_x = image.width() - new_width - real_x_padding,
                new_y = image.height() - new_height - real_y_padding
                ;
        
        System.out.println( new_width + "-" + new_height + "-" + new_x + "-" + new_y);
        
        opencv_core.CvRect r = new opencv_core.CvRect();
        r.x( new_x );
        r.y( new_y );
        r.width( new_width );
        r.height( new_height );
        
        cvSetImageROI(image, r);
        IplImage cropped = cvCreateImage(cvGetSize(image), image.depth(), image.nChannels());
        // Copy original image (only ROI) to the cropped image
        cvCopy(image, cropped);
        image = cropped;
        load_image(image, container);
        return image;
    }
    public IplImage filter_image(IplImage image, JLabel container){
        IplImage image_gray = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
                
        cvCvtColor(image, image_gray, CV_BGR2GRAY);
        image = image_gray;
        // thresholding
        load_image(image, container);
        cvSmooth(image, image,  CV_MEDIAN, 3,0,0,0);
        
        load_image(image, container);
        
        cvThreshold(image, image, 180, 255, CV_THRESH_BINARY);
        
        load_image(image, container);
        
        cvThreshold(image, image, 126, 255, CV_THRESH_BINARY_INV);
        
        load_image(image, container);
        return image;
    }    
    
    public void load_image(IplImage image, JLabel imageContainer){
        int maxWidth = imageContainer.getWidth(),
                maxHeight = imageContainer.getHeight();
        imageContainer.setIcon(
                scaleImage(
                        new ImageIcon( iplImageToBufferedImage(image) )
                        , maxWidth, maxHeight)
        );
        JOptionPane.showMessageDialog(null, "Image Loaded Successfully");
    }
    public ImageIcon scaleImage(ImageIcon icon, int w, int h){
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();
        if(icon.getIconWidth() > w){
          nw = w;
          nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }
        if(nh > h){
          nh = h;
          nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }
        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }
    
    
    public void compareImages( JLabel imageContainer1, JLabel imageContainer2  ){
        opencv_core.Mat images[] = { 
            iplImageToMat(image1),
            iplImageToMat(image2)
        };

        // Setup SURF feature detector and descriptor.
        double hessianThreshold = 2500d;
        int nOctaves = 4;
        int nOctaveLayers = 2;
        boolean extended = true;
        boolean upright = false;
        
        opencv_xfeatures2d.SURF surf = opencv_xfeatures2d.SURF.create(hessianThreshold, nOctaves, nOctaveLayers, extended, upright);
        //DescriptorExtractor surfDesc = DescriptorExtractor.create("SURF");
        
        opencv_core.KeyPointVector keyPoints[] = {
            new opencv_core.KeyPointVector(),
            new opencv_core.KeyPointVector()
        };
        
        
        opencv_core.Mat descriptors[] = new opencv_core.Mat[2];
        
        // Detect SURF features and compute descriptors for both images
        for (int i = 0; i < images.length; i++) {
            surf.detect( images[i], keyPoints[i]  );
            System.out.println( keyPoints[i].size() );
            descriptors[i] = new opencv_core.Mat();
            surf.compute(images[i], keyPoints[i], descriptors[i]);
        }
        
        // Create feature matcher
        opencv_features2d.BFMatcher matcher = new opencv_features2d.BFMatcher(NORM_L2, true);
        opencv_core.DMatchVector matches = new opencv_core.DMatchVector();
        matcher.match(descriptors[0], descriptors[1], matches);
        
        System.out.println("Matched: " + matches.size()   ) ;
        
        
        long minor_keypoints = ( keyPoints[0].size() < keyPoints[1].size() )? keyPoints[0].size() : keyPoints[1].size() ;
        float matched_percentage = matches.size()   *    100.0f    /   minor_keypoints;
        System.out.println("Matching Percentage: " + matched_percentage );
        String msg ;
        if( matched_percentage >= 59.0 )
            msg = "Si, Podría ser la misma firma!";
        else
            msg = "No, No es igual";
        
        JOptionPane.showMessageDialog(null, msg);
        
        long limit = 30;
        if( matches.size() < limit )    limit = matches.size();        
        
        opencv_core.DMatchVector bestMatches = select_best(matches, limit);
        opencv_core.Mat matches_drawn = new opencv_core.Mat();
        opencv_features2d.drawMatches(images[0], keyPoints[0], images[1], keyPoints[1],bestMatches,  matches_drawn  );
        
        IplImage temporal = new IplImage(matches_drawn);
        
        PoiHelper.saveIntent( msg , matched_percentage,
                iplImageToByteArray(image1),
                iplImageToByteArray(image2),
                iplImageToByteArray(temporal)
                );
        
        image1 = temporal;
        load_image(image1, imageContainer1);
        imageContainer2.setIcon(null);
        
        
        
    }
    
    
    
    private opencv_core.DMatchVector select_best(opencv_core.DMatchVector matches, long limit){
        // New List
        long position = matches.position();
        List<opencv_core.DMatch> a = new ArrayList();
        for (int i = 0; i < matches.size(); i++) {
            opencv_core.DMatch src = matches.get(i);
            opencv_core.DMatch dest = new opencv_core.DMatch();
            copy(src, dest);
            a.add(dest);
        }
        matches.position(position);
        
        //Sorting
        a.sort(new DistanceComparator());
        
        // Create new JavaCV list
        opencv_core.DMatchVector best = new opencv_core.DMatchVector(limit);
        for (int i = 0; i < limit; i++) {
            // Since there is no may to `put` objects into a list DMatch,
            // We have to reassign all values individually, and hope that API
            // will not any new ones.
            copy(a.get(i), best.get(i)  );
        }
        
        // Set position to 0 explicitly to avoid issues from other uses of this
        // position-based container.
        best.position(0);
        
        return best;
    }
    public static void copy(opencv_core.DMatch src, opencv_core.DMatch dest) {
        dest.distance(src.distance());
        dest.imgIdx(src.imgIdx());
        dest.queryIdx(src.queryIdx());
        dest.trainIdx(src.trainIdx());
    }
    static class DistanceComparator implements Comparator<opencv_core.DMatch> {
            public int compare(opencv_core.DMatch o1, opencv_core.DMatch o2) {
                if (  o1.distance() < o2.distance()  )
                    return -1;
                else
                    return 1;
            }
        };
    
    
    
}
