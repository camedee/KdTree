package kdtree;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Main 
{
	public static void main(String[] args)
    {
        System.out.println("Entrer le nom de l'image à charger :");
        String filename = new Scanner(System.in).nextLine();
        
        
        try{
            File pathToFile = new File(filename);
            BufferedImage img = ImageIO.read(pathToFile);

            int imgHeight = img.getHeight();
            int imgWidth  = img.getWidth();
            BufferedImage res_img = new BufferedImage(imgWidth, imgHeight, img.getType());
            //BufferedImage id_img = new BufferedImage(imgWidth, imgHeight, img.getType());

/////////////////////////////////////////////////////////////////
//TODO: replace this naive image copy by the quantization
/////////////////////////////////////////////////////////////////
            KdTree<Point3i> tree=new KdTree<Point3i>(3,toArrayList(img),4);
            for (int y = 0; y < imgHeight; y++) {
                for (int x = 0; x < imgWidth; x++) {
                    int Color = img.getRGB(x,y);
                    int R = (Color >> 16) & 0xff;
                    int G = (Color >> 8) & 0xff;
                    int B = Color & 0xff;
                    Point3i p=new Point3i(R,G,B);
                    Point3i val=tree.getNN(p);
                    int resR = val.get(0), resG = val.get(1), resB = val.get(2);

                    int cRes = 0xff000000 | (resR << 16)
                                          | (resG << 8)
                                          | resB;
                    res_img.setRGB(x,y,cRes);
                }
            }
/////////////////////////////////////////////////////////////////

            //ImageIO.write(id_img, "jpg", new File("ResId.jpg"));
            ImageIO.write(res_img, "jpg", new File("ResColor.jpg"));
/////////////////////////////////////////////////////////////////
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
	
	static ArrayList<Point3i> toArrayList(BufferedImage img){
		ArrayList<Point3i> res=new ArrayList<Point3i>();
		int h=img.getHeight(),w=img.getWidth();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int Color = img.getRGB(x,y);
                int R = (Color >> 16) & 0xff;
                int G = (Color >> 8) & 0xff;
                int B = Color & 0xff;
            	res.add(new Point3i(R,G,B));
            }
        }
		return res;
	}
}