package com.client;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.ws.soap.SOAPBinding;

import service.Materials;



public class ImageClient{
	
	public static void main(String[] args) throws Exception {
	   
		URL url = new URL("http://localhost:8095/WS2/Greeting2?wsdl");
        QName qname = new QName("http://service/", "MaterialsImplService");

        Service service = Service.create(url, qname);
        Materials imageServer = service.getPort(Materials.class);

        /************  test upload ****************/
       // Image imgUpload = ImageIO.read(new File("/home/damian/multa2.pdf"));
		
        try{
        	System.out.println("ingrese el path de su archivo");
        		BufferedReader bufferRead = new BufferedReader(
        					new InputStreamReader(System.in));
        		String path = bufferRead.readLine();
        		
        		
        File file = new File(path);
        //enable MTOM in client
        BindingProvider bp = (BindingProvider) imageServer;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        binding.setMTOMEnabled(true);
   
        String status = imageServer.setArchivo(0,"aca iria su nombre" ,"aca su extension", file);
        System.out.println("imageServer.uploadImage() : " + status);
        
        }catch (IOException e) {
			e.printStackTrace();
        }
        
        
        
        /************  test download  ***************/
    /*    Image image = imageServer.downloadImage("/home/damian/rss.png");
        
        //display it in frame
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);

        System.out.println("imageServer.downloadImage() : Download Successful!");
*/
    }

}