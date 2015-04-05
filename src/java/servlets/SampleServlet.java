///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package servlets;
//
//import static com.google.common.collect.Maps.newHashMap;
//import com.sun.xml.registry.uddi.bindings_v2_2.Description;
//import credentials.Credentials;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringReader;
//import static java.lang.System.out;
//import java.sql.*;
//import java.sql.Connection;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.json.Json;
//import javax.json.JsonArrayBuilder;
//import javax.json.JsonObjectBuilder;
//import javax.json.JsonValue;
//import javax.json.stream.JsonParser;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import static javax.swing.text.html.FormSubmitEvent.MethodType.GET;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import static javax.ws.rs.HttpMethod.DELETE;
//import static javax.ws.rs.HttpMethod.POST;
//import static javax.ws.rs.HttpMethod.PUT;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import org.json.simple.JSONValue;
//
///**
// *
// * @author Ama c0641055
// */
//@Path("/sample")
//public class SampleServlet {
//
//    @GET
//    @Produces("application/json")
//    public String doGet() {
//        String st = getResults("SELECT * FROM product");
//        return st;
//    }
//
//    @GET
//    @Path("{productID}")
//    @Produces("application/json")
//    public String doGet(@PathParam("productID") String productID) {
//        String st = getResults("SELECT * FROM product where productID = ?", productID);
//        return st;
//    }
//
//    private String getResults(String query, String... params) {
//        JsonArrayBuilder productArray = Json.createArrayBuilder();
//        String xxx = new String();
//        try (Connection conn = Credentials.getConnection()) {
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            for (int i = 1; i <= params.length; i++) {
//                pstmt.setString(i, params[i - 1]);
//            }
//            ResultSet rs = pstmt.executeQuery();
//            List list = new LinkedList();
//            while (rs.next()) {
//                       JsonObjectBuilder jsonobj = Json.createObjectBuilder()
//                        .add("productID", rs.getInt("productID"))
//                        .add("Name", rs.getString("Name"))
//                        .add("Description", rs.getString("Description"))
//                        .add("Quantity", rs.getInt("Quantity"));
//
//                xxx = jsonobj.build().toString();
//                productArray.add(jsonobj);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SampleServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//          if (params.length == 0) {
//            xxx = productArray.build().toString();
//        }
//        return xxx;
//    }
//    
//        
//    @POST
//    @Consumes("application/json")
//    public void doPost(String st) {
//        JsonParser parser = Json.createParser(new StringReader(st));
//        Map<String, String> ad = new HashMap<>();
//        String Name = "", value;
//
//        while (parser.hasNext()) {
//            JsonParser.Event e = parser.next();
//            switch (e) {
//                case KEY_NAME:
//                    Name = parser.getString();
//                    break;
//                case VALUE_STRING:
//
//                    value = parser.getString();
//                    ad.put(Name, value);
//                    break;
//                case VALUE_NUMBER:
//                    value = Integer.toString(parser.getInt());
//                    ad.put(Name, value);
//                    break;
//            }
//        }
//        System.out.println(ad);
//        String Name1 = ad.get("Name");
//        String Description = ad.get("Description");
//        String Quantity = ad.get("Quantity");
//        doUpdate("INSERT INTO PRODUCT ( Name, Description, Quantity) values ( ?, ?, ?)", Name1, Description, Quantity);
//    }
//   
//
//    private int doUpdate(String query, String... params) {
//        int numChanges = 0;
//        try (Connection conn = Credentials.getConnection()) {
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            for (int i = 1; i <= params.length; i++) {
//                pstmt.setString(i, params[i - 1]);
//            }
//            numChanges = pstmt.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(SampleServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return numChanges;
//    }
//    
//    @PUT
//    @Path("{productID}")
//    @Consumes("application/json")
//    public void doPut(@PathParam("productID") String productID, String st) {
//        JsonParser parser = Json.createParser(new StringReader(st));
//        Map<String, String> ad = new HashMap<>();
//        String Name = "", value;
//        while (parser.hasNext()) {
//            JsonParser.Event e = parser.next();
//            switch (e) {
//                case KEY_NAME:
//                    Name = parser.getString();
//                    break;
//                case VALUE_STRING:
//                    value = parser.getString();
//                    ad.put(Name, value);
//                    break;
//                case VALUE_NUMBER:
//                    value = Integer.toString(parser.getInt());
//                    ad.put(Name, value);
//                    break;
//            }
//        }
//        System.out.println(ad);
//
//        String Name1 = ad.get("Name");
//        String Description = ad.get("Description");
//        String Quantity = ad.get("Quantity");
//        doUpdate("UPDATE PRODUCT SET productId = ?, Name = ?, Description = ?, Quantity = ? WHERE productID = ?", productID, Name1, Description, Quantity, productID);
//
//    }
//
//    @DELETE
//    @Path("{productID}")
//    public void doDelete(@PathParam("productID") String productID, String st) {
//        doUpdate("DELETE FROM PRODUCT WHERE productID = ?", productID);
//
//    }
//}
