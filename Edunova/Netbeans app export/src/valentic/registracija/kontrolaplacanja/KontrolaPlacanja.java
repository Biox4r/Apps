/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.kontrolaplacanja;

import java.awt.Component;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import sun.net.www.http.HttpClient;

/**
 *
 * @author Basic
 */
public class KontrolaPlacanja {
    /*
     private String name;
     private String orderId;
     private String mail;
     private String address;
     private int zip;
     private String city;
     private String state;
     private String country;
     private BigDecimal amount;
     private String currency;
     private String cc;
     private String cvv;
     */

    private InputStream instream = null;
    private Component rootPane;
    private String result;
    private int status_code;
    private long trans;
    private JSONObject json;
    private String orderId;

    public KontrolaPlacanja() {

    }

    public boolean naplata(String name, String orderId, String mail, String address, int zip, String city, String state, String country, BigDecimal amount, String currency, String cc, String cvv) {

        this.orderId = orderId;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://inchoo.net/payday/capture/");
            List<NameValuePair> params = new ArrayList<>(10);
            params.add(new BasicNameValuePair("order_id", orderId));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("mail", mail));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("zip", String.valueOf(zip)));
            params.add(new BasicNameValuePair("city", city));
            params.add(new BasicNameValuePair("state", state));
            params.add(new BasicNameValuePair("country", country));
            params.add(new BasicNameValuePair("amount", String.valueOf(amount)));
            params.add(new BasicNameValuePair("currency", currency));
            params.add(new BasicNameValuePair("cc", cc));
            params.add(new BasicNameValuePair("cvv", cvv));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            if (respEntity != null) {
                instream = respEntity.getContent();
                result = EntityUtils.toString(response.getEntity());
                System.out.println("RESPONSE: " + result);
                json = new JSONObject(result);
                status_code = json.getInt("status_code");
                trans = json.getLong("transaction_id");

                if (status_code == 1) {
                    JOptionPane.showMessageDialog(
                            rootPane,
                            "Uspješna naplata!",
                            "Obavijest",
                            JOptionPane.PLAIN_MESSAGE);
                } else if (status_code == 2) {
                    JOptionPane.showMessageDialog(
                            rootPane,
                            "Transakcija nije uspješna!",
                            "Pogreška",
                            JOptionPane.ERROR_MESSAGE);
                } else if (status_code == 3) {
                    JOptionPane.showMessageDialog(
                            rootPane,
                            "Podaci nisu validni ili fale!",
                            "Pogreška",
                            JOptionPane.ERROR_MESSAGE);
                } else if (status_code == 4) {
                    JOptionPane.showMessageDialog(
                            rootPane,
                            "Nepoznata greška sustava!",
                            "Pogreška",
                            JOptionPane.ERROR_MESSAGE);
                }
                instream.close();
            } else {
                instream.close();
                JOptionPane.showMessageDialog(
                        rootPane,
                        "Greška kod naplate, pokušajte ponovno!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean povratNovca(Long transaction_id) {

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://inchoo.net/payday/refund/");
            List<NameValuePair> params = new ArrayList<>(2);

            params.add(new BasicNameValuePair("order_id", orderId));
            params.add(new BasicNameValuePair("transaction_id", String.valueOf(transaction_id)));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                // EntityUtils to get the response content
                instream = respEntity.getContent();
                // result = convertStreamToString(instream);
                result = EntityUtils.toString(response.getEntity());
                // now you have the string representation of the HTML request
                System.out.println("RESPONSE: " + result);

                json = new JSONObject(result);

                status_code = json.getInt("status_code");

                if (status_code == 1) {
                    JOptionPane.showMessageDialog(
                            rootPane,
                            "Uspješan povrat sredstava!",
                            "Obavijest",
                            JOptionPane.PLAIN_MESSAGE);

                }
                instream.close();
                /*if (response.getStatusLine().getStatusCode() == 200) {
                 netState.setLogginDone(true);
                 }*/

            } else {
                instream.close();
                JOptionPane.showMessageDialog(
                        rootPane,
                        "Greška kod povrata sredstava, pokušajte ponovno",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public long getTrans() {
        return trans;
    }

    public void setTrans(long trans) {
        this.trans = trans;
    }

}
