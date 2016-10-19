package uk.ac.reigate.rm13030.visual;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class MainScreen extends Bitmap {

		public BufferedImage image;
		
		public MainScreen(int w, int h) {
			super(w, h);
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		}

}
