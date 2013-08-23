
package socket;

import java.io.*;

import java.net.*;
import java.util.Calendar;

class server {

    public static void main(String args[]) throws Exception {
        System.out.println("Esperando solicitudes del cliente");

      DatagramSocket serverSocket = new DatagramSocket(9876);

      byte[] receiveData = new byte[1024];

      byte[] sendData  = new byte[1024];

      while(true)
      {

          DatagramPacket receivePacket =
           new DatagramPacket(receiveData, receiveData.length);

          serverSocket.receive(receivePacket);

          String sentence = new String(receivePacket.getData());

          InetAddress IPAddress = receivePacket.getAddress();

          int port = receivePacket.getPort();

          String capitalizedSentence=sentence.toUpperCase();

          
          
          Calendar date = Calendar.getInstance();
          int second = date.get(Calendar.SECOND);
          int minute = date.get(Calendar.MINUTE);
          int hour = date.get(Calendar.HOUR);
          int day = date.get(Calendar.DATE);
          int month = date.get(Calendar.MONTH)+1;
          int year = date.get(Calendar.YEAR);
          String ip = IPAddress.getHostAddress();
          
          sendData = ("Mensaje recibido "+second+"s "+minute+"m "+hour+"h "+day+"-"+month+"-"+year).getBytes();

          DatagramPacket sendPacket =

             new DatagramPacket(sendData, sendData.length, IPAddress,port);

          serverSocket.send(sendPacket);
          File log = new File("log/"+ip+"-"+second+"-"+minute+"-"+hour+"-"+day+"-"+month+"-"+year+".txt");
          FileWriter writer = new FileWriter(log,true);
          writer.write(second+"-"+minute+"-"+hour+"-"+day+"-"+month+"-"+year+" "+IPAddress.getHostAddress()+": "+sentence);
          writer.close();
          System.out.println("Respondiendo a peticiones desde el puerto "+port+" direccion IP:"+IPAddress);
          System.out.println("Usted envio el siguiente mensaje: "+sentence);
        }
    }
}
