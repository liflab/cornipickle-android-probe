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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Sonde {
    View _view;
    Activity acCurrent;
    /**
     * The name of the server of the form "name:port"
     * e.g.: localhost:10101
     */
    String server_name = "";

    /**
     * The probe's id
     */
    String probe_id = "";

    /**
     * The probe's hash
     */
    String probe_hash = "";
    JSONObject jsonObj = new JSONObject();
    JSONArray jsonChildreen;
    SendPostRequest _sendRequest;

    public Sonde(Activity ac) {
    // ac.getWindow().getDecorView().getRootView();
        this._view = ac.findViewById(android.R.id.content);
        this.acCurrent = ac;

     serialiseWindow();
    }

    void serialiseWindow(){

        try {
            jsonObj.put("tagname", "window");
            jsonObj.put("aspect-ratio", Util.getAspectRatio(acCurrent));
            jsonObj.put("orienation", Util.getScreenOrientation(acCurrent));
            jsonObj.put("width", Util.getWidth(acCurrent));
            jsonObj.put("height", Util.getHeight(acCurrent));
            jsonObj.put("device-width", Util.getWidth(acCurrent));
            jsonObj.put("device-height", Util.getHeight(acCurrent));
            this.jsonChildreen = new JSONArray();

            jsonObj.put("childreen", jsonChildreen);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
/**
 * commencer l'envoie de l'hierarchie
*/

    public void sendStart() {

        this._sendRequest = new SendPostRequest();
        this._sendRequest.execute();

    }
    public void getHierarchyActivity() {

        analyseViews((ViewGroup) this._view,  0, jsonChildreen);

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
    private void analyseViews(ViewGroup v, int level, JSONArray jsc) {
        final int childCount = v.getChildCount();
        v.getId();
        try {
            JSONObject j = new JSONObject();


            j.put("id", v.getId());
            j.put("name", v.getClass().getName());
            j.put("level", level);
            v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            j.put("width",v.getWidth());
            j.put("height",v.getHeight());
            j.put("left",v.getLeft());
            j.put("right",v.getRight());
            j.put("top",v.getTop());
            j.put("bottom",v.getBottom());
            jsc.put(j);

            for (int i = 0; i < childCount; i++) {
                View child = v.getChildAt(i);

                JSONObject jsonObjn = new JSONObject();

                if ((child instanceof ViewGroup)) {

                    JSONArray jjj = new JSONArray();
                    //  jsonChildreen.put(jsonObj);
                    j.put("childreen", jjj);
                    analyseViews((ViewGroup) child, level + 1, jjj);

                } else {
                    jsonObjn.put("id", child.getId());
                    jsonObjn.put("width",v.getWidth());
                    jsonObjn.put("height",v.getHeight());
                    jsonObjn.put("left",v.getLeft());
                    jsonObjn.put("right",v.getRight());
                    jsonObjn.put("top",v.getTop());
                    jsonObjn.put("bottom",v.getBottom());
                    if (child instanceof ToggleButton) {


                        jsonObjn.put("text", ((ToggleButton) child).getText());
                        jsonObjn.put("class", "Toogle");
                        jsonObjn.put("level", level);

                    }
                    else if (child instanceof Switch) {



                        jsonObjn.put("text", ((Switch) child).getText());
                        jsonObjn.put("class", "Switch");
                        jsonObjn.put("level", level);


                    }

                    else if (child instanceof Button) {



                        jsonObjn.put("text", ((Button) child).getText());
                        jsonObjn.put("class", "Button");
                        jsonObjn.put("level", level);


                    } else if (child instanceof TextView) {


                        jsonObjn.put("text", ((TextView) child).getText());
                        jsonObjn.put("class", "Text");
                        jsonObjn.put("level", level);

                    }

                    else {

                        jsonObjn.put("name", child.getClass().getName());
                        jsonObjn.put("class", "Others");
                        jsonObjn.put("level", level);
                    }

                    j.put("child" + i, jsonObjn);
                }


            }


        } catch (JSONException exc) {
            exc.printStackTrace();

        }

    }



    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {
                URL url = new URL("http://192.168.2.12:10101/mobiletest/"); // here is your URL path
                // url = new URL("http://192.168.2.12:10101/image/"); // here is your URL path
                //URL url = new URL("http://192.168.113.1/test/test.php"); // here is your URL path
                //http://studytutorial.in/post.php


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonObj.toString());

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        Log.e("params2", sb.toString());
                        break;
                    }

                    in.close();

                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
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
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}

