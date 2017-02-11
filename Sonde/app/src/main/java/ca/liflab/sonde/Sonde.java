package ca.liflab.sonde;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Sonde {
    View _view;
    Activity acCurrent;
    /**
     * The name of the server of the form "name:port"
     * e.g.: localhost:10101
     */
    public String server_name = "localhost:10101";
    final StringBuilder st= new StringBuilder("{\"tagname\":\"window\",\"URL\":\"localhost:10101/examples/misaligned-elements.html\",\"aspect-ratio\":2.0031578947368422,\"orientation\":\"portrait\",\"width\":1903,\"height\":950,\"device-width\":1920,\"device-height\":1040,\"device-aspect-ratio\":1.8461538461538463,\"mediaqueries\":{\"2\":\"true\"},\"children\":[{\"children\":[{\"children\":[{\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Example\"}]},{\"children\":[{\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Back to example list\"}]}]},{\"tagname\":\"div\",\"cornipickleid\":0,\"class\":\"playground\",\"top\":116,\"left\":8,\"event\":\"click\",\"children\":[{\"tagname\":\"ul\",\"cornipickleid\":1,\"class\":\"menu\",\"top\":153,\"left\":29,\"children\":[{\"tagname\":\"li\",\"cornipickleid\":2,\"top\":153,\"left\":69,\"children\":[{\"tagname\":\"CDATA\",\"text\":\"First menu item\"}]},{\"tagname\":\"li\",\"cornipickleid\":3,\"top\":172,\"left\":69,\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Second menu item\"}]},{\"tagname\":\"li\",\"cornipickleid\":4,\"top\":191,\"left\":79,\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Another menu item\"}]},{\"tagname\":\"li\",\"cornipickleid\":5,\"top\":210,\"left\":69,\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Final menu item\"}]}]}]},{\"tagname\":\"CDATA\",\"text\":\" Cornipickle explanation \"},{\"tagname\":\"CDATA\",\"text\":\" /explanation \"}]},{\"tagname\":\"CDATA\",\"text\":\" /contents \"}]}]}");


    /**
     * The probe's id
     */
    public String probe_id = "1";

    /**
     * The probe's hash
     */
    public String hash = "interpreter";

    public String interpreter = "";

    JSONObject jsonObj = new JSONObject();
    JSONArray jsonChildreen;
    SendPostRequest _sendRequest;

    ArrayList<String> lstAttributes = new ArrayList<String>();

    ArrayList<String> lstContainer = new ArrayList<String>();

    public void setLstAttributes(ArrayList<String> lst) {

        lstAttributes.clear();
        lstAttributes.addAll(lst);

    }

    public void setLstContainer(ArrayList<String> lst) {

        lstAttributes.clear();
        lstAttributes.addAll(lst);

    }

    public enum RequestName {

        add,
        image,
        autre

    }

    public Sonde(Activity ac) {
        // ac.getWindow().getDecorView().getRootView();
        this._view = ac.findViewById(android.R.id.content);
        this.acCurrent = ac;


    }

    void serialiseWindow() {

        try {
            jsonObj.put("tagname", "window");
            jsonObj.put("aspect-ratio", Util.getAspectRatio(acCurrent));
            jsonObj.put("orientation", Util.getScreenOrientation(acCurrent));
            jsonObj.put("width", Util.getWidth(acCurrent));
            jsonObj.put("height", Util.getHeight(acCurrent));
            jsonObj.put("device-width", Util.getWidth(acCurrent));
            jsonObj.put("device-height", Util.getHeight(acCurrent));
            this.jsonChildreen = new JSONArray();

            jsonObj.put("children", jsonChildreen);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String getDataImage() {
        this.getHierarchyActivity();
      /*  try {
            JSONObject data = new JSONObject();
            data.put("contents", jsonObj.toString());
            data.put("interpreter", interpreter);
            data.put(hash, "interpreter");
            data.put("id", probe_id);
         //   return jsonObj.toString();
            return data.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";*/


        String data ="";
        try {
             data ="contents="+ URLEncoder.encode(jsonObj.toString(),"UTF-8");// "contents=%7B%22tagname%22%3A%22window%22%2C%22URL%22%3A%22localhost%3A11019%2Fexamples%2Fmisaligned-elements.html%22%2C%22aspect-ratio%22%3A3.747072599531616%2C%22orientation%22%3A%22portrait%22%2C%22width%22%3A1600%2C%22height%22%3A427%2C%22device-width%22%3A1615%2C%22device-height%22%3A1026%2C%22device-aspect-ratio%22%3A1.5740740740740742%2C%22mediaqueries%22%3A%7B%220%22%3A%22true%22%7D%2C%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Example%22%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Back%20to%20example%20list%22%7D%5D%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22tagname%22%3A%22ul%22%2C%22cornipickleid%22%3A0%2C%22class%22%3A%22menu%22%2C%22top%22%3A153%2C%22left%22%3A29%2C%22children%22%3A%5B%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A1%2C%22top%22%3A153%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22First%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A2%2C%22top%22%3A172%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Second%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A3%2C%22top%22%3A191%2C%22left%22%3A79%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Another%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A4%2C%22top%22%3A210%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Final%20menu%20item%22%7D%5D%7D%5D%7D%5D%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20Cornipickle%20explanation%20%22%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fexplanation%20%22%7D%5D%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fcontents%20%22%7D%5D%7D%5D%7D";//URLEncoder.encode(jsonObj.toString(),"UTF-8");
            data += "&interpreter=" + URLEncoder.encode(interpreter,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        data += "&id=" + probe_id;
        data += "&hash=" + hash;
    /*   String data="contents=";*/
        return data;
    }

    /**
     * commencer l'envoie de l'hierarchie
     */


    public void sendStart(String url, String data) {
        //   this._sendRequest = new SendPostRequest(url,jsonObj.toString()
        this._sendRequest = new SendPostRequest(url, data);
        this._sendRequest.execute();

    }

    public void sendStart(String url, String data, RequestName add) {
        //   this._sendRequest = new SendPostRequest(url,jsonObj.toString()
        this._sendRequest = new SendPostRequest(url, data, add);

        this._sendRequest.execute();

    }

    public void getHierarchyActivity() {
        serialiseWindow();
       analyseViews((ViewGroup) this._view, 0, jsonChildreen);

    }

    private void decaler(StringBuffer buffer, int level) {
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
    }


    /*
        *Cette methodes analyse les elements de chaque view recursivment et retourne tous les widgets
    	*  les elements retournes sous forme json avec leur proprites
    	*  @parm v :L'objet parent d'une activité
    	*

     */
    private void analyseViews(ViewGroup v, int level, JSONArray jsArrayChildren) {
        final int childCount = v.getChildCount();
        v.getId();
        try {
            JSONObject jNode = new JSONObject();


            jNode.put("id", v.getId());
            jNode.put("tagname", v.getClass().getName());
           // j.put("level", level);
            v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            jNode.put("width", v.getWidth());
            jNode.put("height", v.getHeight());
            jNode.put("left", v.getLeft());
            jNode.put("right", v.getRight());
            jNode.put("top", v.getTop());
            jNode.put("bottom", v.getBottom());
            jsArrayChildren.put(jNode);

            for (int i = 0; i < childCount; i++) {
                View child = v.getChildAt(i);

                JSONObject jNodeChild = new JSONObject();

                if ((child instanceof ViewGroup)) {

                    JSONArray jArrayChild = new JSONArray();
                    //  jsonChildreen.put(jsonObj);
                    jNodeChild.put("children", jArrayChild);
                    jNode.put(String.valueOf(i),jNodeChild);
                    analyseViews((ViewGroup) child, level + 1, jArrayChild);

                } else {
                    jNodeChild.put("id", child.getId());
                    jNodeChild.put("width", v.getWidth());
                    jNodeChild.put("height", v.getHeight());
                    jNodeChild.put("left", v.getLeft());
                    jNodeChild.put("right", v.getRight());
                    jNodeChild.put("top", v.getTop());
                    jNodeChild.put("bottom", v.getBottom());
                    if (child instanceof ToggleButton) {
                        jNodeChild.put("tagname", "CDATA");

                        jNodeChild.put("text", ((ToggleButton) child).getText());
                        jNodeChild.put("class", "Toogle");
                       // jsonObjn.put("level", level);

                    } else if (child instanceof Switch) {

                        jNodeChild.put("tagname", "CDATA");
                        jNodeChild.put("text", ((Switch) child).getText());
                        jNodeChild.put("class", "Switch");
                       // jsonObjn.put("level", level);


                    } else if (child instanceof Button) {

                        jNodeChild.put("tagname", "CDATA");
                        jNodeChild.put("text", ((Button) child).getText());
                        jNodeChild.put("class", "Button");
                     //   jsonObjn.put("level", level);


                    } else if (child instanceof TextView) {

                        jNodeChild.put("tagname", "CDATA");
                        jNodeChild.put("text", ((TextView) child).getText());
                        jNodeChild.put("class", "Text");

                     //  jsonObjn.put("tagname", ((TextView) child).);
                      //  jsonObjn.put("level", level);

                    } else {
                        jNodeChild.put("tagname", "CDATA");
                        jNodeChild.put("text", child.getClass().getName());

                    //    jsonObjn.put("level", level);
                    }

                    jNode.put(String.valueOf(i), jNodeChild);

                }


            }


        } catch (JSONException exc) {
            exc.printStackTrace();

        }

    }


    public class SendPostRequest extends AsyncTask<String, Void, String> {
        String _url = "http://192.168.109.1:10101/addProp";
        String _dataToSend = "gog gog0 gog0ggk";
        boolean _addPro = false;
        RequestName _requestName = RequestName.add;

        public SendPostRequest() {


        }


        public void set_addPro(boolean addpro) {
            this._addPro = addpro;

        }

        public void set_dataToSend(String _dataToSend) {

            this._dataToSend = _dataToSend;

        }

        public SendPostRequest(String url, String _dataToSend) {

            this._url = url;
            this._dataToSend = _dataToSend;

        }

        public SendPostRequest(String url, String _dataToSend, RequestName req) {

            this._url = url;
            this._dataToSend = _dataToSend;
            this._requestName = req;

        }

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {
                URL url1 = new URL(this._url); // here is your URL path
                // url = new URL("http://192.168.2.12:10101/image/"); // here is your URL path
                //URL url = new URL("http://192.168.113.1/test/test.php"); // here is your URL path
                //http://studytutorial.in/post.php


                String urlParameters =
                        "fName=" + URLEncoder.encode("???", "UTF-8") +
                                "&lName=" + URLEncoder.encode("???", "UTF-8");




                HttpURLConnection conn;
                conn = (HttpURLConnection) url1.openConnection();

                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(_dataToSend.toString());
                writer.flush();
                writer.close();


                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        Log.e("params2", sb.toString());
                        handleRepsonse(sb.toString());
                        break;
                    }

                    in.close();

                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (IOException e) {
                return new String("Exception 1: " + e.getMessage());
            }

        }


        @Override
        protected void onPostExecute(String result) {
            Log.e("result", result);
            Toast toast = Toast.makeText(acCurrent.getApplicationContext(), result,
                    Toast.LENGTH_LONG);

            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.show();

        }

        public void handleRepsonse(String result) {
            if (this._requestName == RequestName.add) {


                try {
                    JSONObject jsonObj = new JSONObject(result);
                    // Getting JSON Array node
                    JSONArray arr = jsonObj.getJSONArray("attributes");
                    JSONArray arrTags = jsonObj.getJSONArray("tagnames");
                    String _inter = jsonObj.getString("interpreter");
                    interpreter = _inter;
                    // looping through All Contacts
                    ArrayList<String> lst = new ArrayList<String>();

                    for (int i = 0; i < arr.length(); i++) {
                        String c = arr.getString(i);
                        Log.d("attribute :", c);
                        lst.add(c);
                    }
                    for (int i = 0; i < arrTags.length(); i++) {
                        String c = arrTags.getString(i);
                        Log.d("tags ", c);
                        // lst.add(c);
                    }
                    setLstAttributes(lst);

                } catch (JSONException exc) {
                    exc.printStackTrace();

                }

            } else if (this._requestName == RequestName.image) {

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    String _inter = jsonObj.getString("global-verdict");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }




}

