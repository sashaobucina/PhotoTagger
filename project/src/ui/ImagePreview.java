package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.FileInfo;

/**
 * Component used to show the icons of images
 */
public class ImagePreview extends JComponent
                          implements ListSelectionListener {
	
	/** Default serialVersionUID */
	private static final long serialVersionUID = -618180627597556095L;

	/** list of images (using FileInfo to represent them) */
	private JList<FileInfo> jImageList;
	
    /** image icon */
	ImageIcon thumbnail = null;
	
	/** file of image */
    File file = null;
    
    public void valueChanged(ListSelectionEvent e) { 
        
        FileInfo fileInfo = (FileInfo) jImageList.getSelectedValue();
        
        File newFile = new File(fileInfo.getAbsolutePath());
        if (file == null || !file.getAbsolutePath().equals(newFile.getAbsolutePath())) {
        	file = newFile;
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
	}
     
    /**
     * Creates a preview of images
     * 
     * @param jImageList
     * 			list of images
     */
    public ImagePreview(JList<FileInfo> jImageList) {
    	this.jImageList = jImageList;
        setPreferredSize(new Dimension(100, 50));
        jImageList.addListSelectionListener(this);
        if (jImageList.getSelectedIndex() != -1) {
            FileInfo fileInfo = (FileInfo) jImageList.getSelectedValue();
            file = new File(fileInfo.getAbsolutePath());
            thumbnail = null;
            if (isShowing()) {
            	loadImage();
            	repaint();
            }
        }      	

    }

    /**
     * Load the image icon
     */
    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }

        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if (tmpIcon != null) {
                thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(250, -1, Image.SCALE_DEFAULT));
        }
    }

    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
