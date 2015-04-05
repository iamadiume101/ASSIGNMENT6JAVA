/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entities.Product;
import entities.ProductList;
import java.io.StringReader;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author c0641055
 */
@MessageDriven (mappedName = "jms/Queue")
public class ProductListener implements MessageListener {
    
    @EJB
    ProductList pro;
    
    @Override
    public void onMessage(Message ms){
        try{
            if(ms instanceof TextMessage  ){
                String jsonStr = ((TextMessage) ms).getText();
                JsonObject json = Json.createReader(
                        new StringReader(jsonStr)).readObject();
                        pro.add(new Product(json));
                }
            } catch (JMSException ex){
            System.err.println("Failure in JMS");
        } catch (Exception ex){
           java.util.logging.Logger.getLogger(ProductListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

