/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimizedproductions.playingfirmas;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.DMatch;
import org.bytedeco.javacpp.opencv_core.DMatchVector;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.NORM_L2;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import org.bytedeco.javacpp.opencv_features2d;
import org.bytedeco.javacpp.opencv_features2d.BFMatcher;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_MEDIAN;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY_INV;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvSmooth;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 *
 * @author herib_000
 */
public class MainClass extends javax.swing.JFrame {

    /**
     * Creates new form MainClass
     */
    public MainClass() {
        System.out.println("Starting");
        initComponents();
        System.out.println("Ending");
    }

    // Global Variables
    private OpenCVFrameConverter.ToIplImage converter1 = new OpenCVFrameConverter.ToIplImage();
    private Java2DFrameConverter converter2 = new Java2DFrameConverter();
    private OpenCVFrameConverter.ToMat converter3 = new OpenCVFrameConverter.ToMat();
    private IplImage image1, image2;
    
    
    public BufferedImage iplImageToBufferedImage(IplImage src) {
        Frame frame = converter1.convert(src);
        return converter2.getBufferedImage(frame,1);
    }
    public Mat iplImageToMat(IplImage src){
        return converter3.convert( converter3.convert(src ) );
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageContainer1 = new javax.swing.JLabel();
        imageContainer2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");

        jMenuItem1.setText("Load Image");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem4.setText("Load Img 2");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Operation");

        jMenuItem2.setText("Segmentation");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Other");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Compare");

        jMenuItem5.setText("compare as 1 real");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageContainer2, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(imageContainer2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        getUserFile((IplImage image) -> {
            image1 = image;
            load_image(image, imageContainer1);
        });
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if( image1 != null )    image1 = crop_image(image1, imageContainer1 );
        if( image2 != null )    image2 = crop_image(image2, imageContainer2 );
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if( image1 != null )    image1 = filter_image(image1, imageContainer1 );
        if( image2 != null )    image2 = filter_image(image2, imageContainer2 );        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        getUserFile((IplImage image) -> {
            image2 = image;
            load_image(image, imageContainer2);
        });
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        
        Mat images[] = { 
            iplImageToMat(image1),
            iplImageToMat(image2)
        };

        // Setup SURF feature detector and descriptor.
        double hessianThreshold = 2500d;
        int nOctaves = 4;
        int nOctaveLayers = 2;
        boolean extended = true;
        boolean upright = false;
        
        SURF surf = SURF.create(hessianThreshold, nOctaves, nOctaveLayers, extended, upright);
        //DescriptorExtractor surfDesc = DescriptorExtractor.create("SURF");
        
        opencv_core.KeyPointVector keyPoints[] = {
            new opencv_core.KeyPointVector(),
            new opencv_core.KeyPointVector()
        };
        
        
        Mat descriptors[] = new Mat[2];
        
        // Detect SURF features and compute descriptors for both images
        for (int i = 0; i < images.length; i++) {
            surf.detect( images[i], keyPoints[i]  );
            System.out.println( keyPoints[i].size() );
            descriptors[i] = new Mat();
            surf.compute(images[i], keyPoints[i], descriptors[i]);
        }
        
        // Create feature matcher
        BFMatcher matcher = new BFMatcher(NORM_L2, true);
        opencv_core.DMatchVector matches = new opencv_core.DMatchVector();
        matcher.match(descriptors[0], descriptors[1], matches);
        
        System.out.println("Matched: " + matches.size()   ) ;
        
        
        long minor_keypoints = ( keyPoints[0].size() < keyPoints[1].size() )? keyPoints[0].size() : keyPoints[1].size() ;
        float matched_percentage = matches.size()   *    100.0f    /   minor_keypoints;
        System.out.println("Matching Percentage: " + matched_percentage );
        if( matched_percentage >= 60.0 ){
            JOptionPane.showMessageDialog(this, "Si, Podría ser la misma firma!");
        }else{
            JOptionPane.showMessageDialog(this, "No, No es igual");
        }
        
        
        long limit = 30;
        if( matches.size() < limit )    limit = matches.size();        
        
        DMatchVector bestMatches = select_best(matches, limit);
        Mat matches_drawn = new Mat();
        opencv_features2d.drawMatches(images[0], keyPoints[0], images[1], keyPoints[1],bestMatches,  matches_drawn  );
        
        image1 = new IplImage(matches_drawn);
        load_image(image1, imageContainer1);
        imageContainer2.setIcon(null);
        //opencv_features2d.drawKeypoints(images[0], keyPoints, images[1]);
        
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    
    private DMatchVector select_best(opencv_core.DMatchVector matches, long limit){
        // New List
        long position = matches.position();
        List<DMatch> a = new ArrayList();
        for (int i = 0; i < matches.size(); i++) {
            DMatch src = matches.get(i);
            DMatch dest = new DMatch();
            copy(src, dest);
            a.add(dest);
        }
        matches.position(position);
        
        //Sorting
        a.sort(new DistanceComparator());
        
        // Create new JavaCV list
        DMatchVector best = new DMatchVector(limit);
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
    private static void copy(DMatch src, DMatch dest) {
        dest.distance(src.distance());
        dest.imgIdx(src.imgIdx());
        dest.queryIdx(src.queryIdx());
        dest.trainIdx(src.trainIdx());
    }
    static class DistanceComparator implements Comparator<DMatch> {
            public int compare(DMatch o1, DMatch o2) {
                if (  o1.distance() < o2.distance()  )
                    return -1;
                else
                    return 1;
            }
        };
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainClass().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imageContainer1;
    private javax.swing.JLabel imageContainer2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    // End of variables declaration//GEN-END:variables
    
    
    public static interface GetFileHelper{
        void onFileSelected(IplImage image);
    }
    private void getUserFile( GetFileHelper listener ){
        String directory = "D:\\Downloads";
        JFileChooser selector = new JFileChooser(directory);
        int r = selector.showOpenDialog(null);
        if(r == JFileChooser.APPROVE_OPTION){
            File selected_file = selector.getSelectedFile();
            // Verify file
            if (!selected_file.exists()) {
                JOptionPane.showMessageDialog(this, "Image file does not exist: " + selected_file.getAbsolutePath() );
                return;
            }
            // Read input image
            IplImage image = cvLoadImage(selected_file.getAbsolutePath());
            if (image == null) {
                JOptionPane.showMessageDialog(this, "Couldn't load image: " + selected_file.getAbsolutePath());
                return;
            }
            if( listener != null )
                listener.onFileSelected(image);
	}
    }
    private IplImage crop_image(IplImage image, JLabel container){
        if (image == null){
            JOptionPane.showMessageDialog(this, "Image Is Null");
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
    private IplImage filter_image(IplImage image, JLabel container){
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
    
    private void load_image(IplImage image, JLabel imageContainer){
        int maxWidth = imageContainer.getWidth(),
                maxHeight = imageContainer.getHeight();
        imageContainer.setIcon(
                scaleImage(
                        new ImageIcon( iplImageToBufferedImage(image) )
                        , maxWidth, maxHeight)
        );
        JOptionPane.showMessageDialog(this, "Image Loaded Successfully");
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

}
