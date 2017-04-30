package ca.liflab.sonde;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class Probe {
    /**
     * Current View Parent
     */
    View _view;
    /**
     * the current activity
     */

    public Activity acCurrent;


    /**
     * The name of the server of the form "name:port"
     * e.g.: localhost:10101
     */
    public String server_name = "localhost:10101";
    /**
     * The probe's id
     */
    public String probe_id = "1";

    public ArrayList<Integer> posLayout = new ArrayList<Integer>();


    /**
     * propriete
     */
    Boolean propAdded = false;

    /**
     *
     */
    Boolean propSending = false;

    /**
     * The probe's hash
     */
    String hash = "interpreter";
    /**
     *
     */
    public String interpreter = "";

    /**
     * Results in a Json
     */
    JSONObject resultJson = new JSONObject();
    /**
     * First array in JSON
     */
    JSONArray jsonChildreen;
    /**
     * For Sending Request
     */
    SendPostRequest _sendRequest;
    /**
     * A List of attributes to include.
     */

    ArrayList<String> lstAttributes = new ArrayList<String>();
    /**
     * A list of layouts ,container ,widgets to include in json file
     */

    ArrayList<String> lstContainer = new ArrayList<String>();
    /**
     * for generating id for each element in json file
     */
    int cornipickleid = 0;
    /**
     * help to keep all element reference
     */
    Map<Integer, infoHighId> idMap = new HashMap<Integer, infoHighId>();

    final String hashTagResult = "Sonde_Tag_Layout_this";
    final  String hashTagResult_Button = "Sonde_Tag_Layout_this_Button";
    public Boolean getPropSending() {
        return propSending;
    }

    public void setPropSending(Boolean propSending) {
        this.propSending = propSending;
    }

    public Boolean getPropAdded() {
        return propAdded;
    }

    public void setPropAdded(Boolean propAdded) {
        this.propAdded = propAdded;
    }

    public JSONObject getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSONObject resultJson) {
        this.resultJson = resultJson;
    }


    public class infoHighId {

        public int id;
        public float width;
        public float x;
        public float y;
        public float height;

        public infoHighId(int id, float width, float x, float y, float height) {

            this.id = id;
            this.width = width;
            this.x = x;
            this.y = y;
            this.height = height;

        }


    }

    public void setLstAttributes(ArrayList<String> lst) {

        this.lstAttributes.clear();
        this.lstAttributes.addAll(lst);


    }

    public void setLstContainer(ArrayList<String> lst) {

        this.lstContainer.clear();
        this.lstContainer.addAll(lst);


    }


    /**
     * Determines if given points are inside view
     *
     * @param x    - x coordinate of point
     * @param y    - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    public static boolean isViewContains(View view, float x, float y) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth())) &&
                (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }

    public enum RequestName {

        add,
        image,
        others

    }

    /**
     * Constructor of the sonde
     */
    public Probe() {

    }

    public Probe(Activity ac) {
        // ac.getWindow().getDecorView().getRootView();
        this._view = ac.findViewById(android.R.id.content);
        this.acCurrent = ac;
        this.posLayout.clear();
        this.posLayout.add(RelativeLayout.ALIGN_PARENT_BOTTOM);
        this.posLayout.add(RelativeLayout.ALIGN_PARENT_RIGHT);

    }

    public Probe(Activity ac, ArrayList<Integer> posLayoutResult) {

        this._view = ac.findViewById(android.R.id.content);
        this.acCurrent = ac;
        this.posLayout.clear();
        this.posLayout = posLayoutResult;


    }

    /**
     * serialise attribute for window and device
     */

    void serialiseWindow() {

        try {
            resultJson.put("element", "window");
            resultJson.put("aspect-ratio", Util.getAspectRatio(acCurrent));
            resultJson.put("orientation", Util.getScreenOrientation(acCurrent));
            resultJson.put("width", Util.getWidth(acCurrent));
            resultJson.put("height", Util.getHeight(acCurrent));
            resultJson.put("device-width", Util.getWidth(acCurrent));
            resultJson.put("device-height", Util.getHeight(acCurrent));
            resultJson.put("url", "");
            resultJson.put("device-aspect-ratio", Util.getAspectRatio(acCurrent));
            resultJson.put("device-density", Util.getDensity(acCurrent));
            resultJson.put("device-langue", Util.getLangue(acCurrent));
            this.jsonChildreen = new JSONArray();
            resultJson.put("children", jsonChildreen);
            idMap.clear();
            cornipickleid = -1;

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String getDataImage(MotionEvent event) {
        this.getHierarchyActivity(event);

        String data = "";
        try {
            data = "contents=" + URLEncoder.encode(resultJson.toString(), "UTF-8");// "contents=%7B%22element%22%3A%22window%22%2C%22URL%22%3A%22localhost%3A11019%2Fexamples%2Fmisaligned-elements.html%22%2C%22aspect-ratio%22%3A3.747072599531616%2C%22orientation%22%3A%22portrait%22%2C%22width%22%3A1600%2C%22height%22%3A427%2C%22device-width%22%3A1615%2C%22device-height%22%3A1026%2C%22device-aspect-ratio%22%3A1.5740740740740742%2C%22mediaqueries%22%3A%7B%220%22%3A%22true%22%7D%2C%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Example%22%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Back%20to%20example%20list%22%7D%5D%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22element%22%3A%22ul%22%2C%22cornipickleid%22%3A0%2C%22class%22%3A%22menu%22%2C%22top%22%3A153%2C%22left%22%3A29%2C%22children%22%3A%5B%7B%22element%22%3A%22li%22%2C%22cornipickleid%22%3A1%2C%22top%22%3A153%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22First%20menu%20item%22%7D%5D%7D%2C%7B%22element%22%3A%22li%22%2C%22cornipickleid%22%3A2%2C%22top%22%3A172%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Second%20menu%20item%22%7D%5D%7D%2C%7B%22element%22%3A%22li%22%2C%22cornipickleid%22%3A3%2C%22top%22%3A191%2C%22left%22%3A79%2C%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Another%20menu%20item%22%7D%5D%7D%2C%7B%22element%22%3A%22li%22%2C%22cornipickleid%22%3A4%2C%22top%22%3A210%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Final%20menu%20item%22%7D%5D%7D%5D%7D%5D%7D%2C%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22%20Cornipickle%20explanation%20%22%7D%2C%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fexplanation%20%22%7D%5D%7D%2C%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fcontents%20%22%7D%5D%7D%5D%7D";//URLEncoder.encode(resultJson.toString(),"UTF-8");
            data += "&interpreter=" + URLEncoder.encode(interpreter, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        data += "&id=" + probe_id;
        data += "&hash=" + hash;
    /*   String data="contents=";*/
        return data;
    }

    public String getDataImage(View v, MotionEvent event) {
        this.getHierarchyActivity(v, event);

        String data = "";
        try {
            data = "contents=" + URLEncoder.encode(resultJson.toString(), "UTF-8");// "contents=%7B%22element%22%3A%22window%22%2C%22URL%22%3A%22localhost%3A11019%2Fexamples%2Fmisaligned-elements.html%22%2C%22aspect-ratio%22%3A3.747072599531616%2C%22orientation%22%3A%22portrait%22%2C%22width%22%3A1600%2C%22height%22%3A427%2C%22device-width%22%3A1615%2C%22device-height%22%3A1026%2C%22device-aspect-ratio%22%3A1.5740740740740742%2C%22mediaqueries%22%3A%7B%220%22%3A%22true%22%7D%2C%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Example%22%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Back%20to%20example%20list%22%7D%5D%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22element%22%3A%22ul%22%2C%22cornipickleid%22%3A0%2C%22class%22%3A%22menu%22%2C%22top%22%3A153%2C%22left%22%3A29%2C%22children%22%3A%5B%7B%22element%22%3A%22li%22%2C%22cornipickleid%22%3A1%2C%22top%22%3A153%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22First%20menu%20item%22%7D%5D%7D%2C%7B%22element%22%3A%22li%22%2C%22cornipickleid%22%3A2%2C%22top%22%3A172%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Second%20menu%20item%22%7D%5D%7D%2C%7B%22element%22%3A%22li%22%2C%22cornipickleid%22%3A3%2C%22top%22%3A191%2C%22left%22%3A79%2C%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Another%20menu%20item%22%7D%5D%7D%2C%7B%22element%22%3A%22li%22%2C%22cornipickleid%22%3A4%2C%22top%22%3A210%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22Final%20menu%20item%22%7D%5D%7D%5D%7D%5D%7D%2C%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22%20Cornipickle%20explanation%20%22%7D%2C%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fexplanation%20%22%7D%5D%7D%2C%7B%22element%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fcontents%20%22%7D%5D%7D%5D%7D";//URLEncoder.encode(resultJson.toString(),"UTF-8");
            data += "&interpreter=" + URLEncoder.encode(interpreter, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        data += "&id=" + probe_id;
        data += "&hash=" + hash;
    /*   String data="contents=";*/
        return data;
    }

    /**
     * commencer l'envoie de data
     */


    public void sendStart(String url, String data) {
        //   this._sendRequest = new SendPostRequest(url,resultJson.toString()
        this._sendRequest = new SendPostRequest(url, data);
        this._sendRequest.execute();

    }

    public void sendStart(String url, String data, RequestName add) {
        //   this._sendRequest = new SendPostRequest(url,resultJson.toString()
        this._sendRequest = new SendPostRequest(url, data, add);

        this._sendRequest.execute();

    }

    public void getHierarchyActivity(MotionEvent event) {
        serialiseWindow();
        analyseViews((ViewGroup) this._view, 0, jsonChildreen, event);

    }

    public void getHierarchyActivity(View v, MotionEvent event) {
        serialiseWindow();
        analyseViews((ViewGroup) v, 0, jsonChildreen, event);

    }

    private void decaler(StringBuffer buffer, int level) {
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
    }

    public boolean isAttributeExists(String property_name) {


        if (lstAttributes.contains(property_name))

            return true;

        return false;
    }

    void addAttributeIfDefined(JSONObject jNodeChild, View v, MotionEvent event) {
        try {

            // jNodeChild.put("id", v.getId());
            cornipickleid = cornipickleid + 1;
            jNodeChild.put("cornipickleid", cornipickleid);
            idMap.put(cornipickleid, new infoHighId(v.getId(), v.getWidth(), Util.getAbsoluteLeft(v), Util.getAbsoluteTop(v), v.getHeight()));
            if (isAttributeExists("id"))
                if (v.getResources() != null) {
                    if (v.getId() != -1)
                    {
                        String s = v.getResources().getResourceEntryName(v.getId());
                        jNodeChild.put("id", s);
                    }
                }
            if (isAttributeExists("widthdp"))
                jNodeChild.put("widthdp", Util.pxToDp(v.getWidth(), acCurrent));
            if (isAttributeExists("heightdp"))
                jNodeChild.put("heightdp", Util.pxToDp(v.getHeight(), acCurrent));

            if (isAttributeExists("width"))
                jNodeChild.put("width", v.getWidth());

            if (isAttributeExists("height"))
                jNodeChild.put("height", v.getHeight());
            Random r = new Random();
            //   int i1 = r.nextInt(500 - 20) + 20;
            if (isAttributeExists("left"))
                jNodeChild.put("left", Util.getAbsoluteLeft(v));
            if (isAttributeExists("right"))
                jNodeChild.put("right", Util.getAbsoluteRight(acCurrent, v));
            if (isAttributeExists("top"))
                jNodeChild.put("top", Util.getAbsoluteTop(v));
            if (isAttributeExists("bottom"))
                jNodeChild.put("bottom", Util.getAbsoluteBottom(acCurrent, v));
            if (isAttributeExists("size") && v instanceof ViewGroup)
                jNodeChild.put("size", ((ViewGroup) v).getChildCount());
            if (isAttributeExists("bgcolor")) {
                //est appelle juste apres setbacgroundcolor
                if (v.getBackground() instanceof ColorDrawable) {
                    ColorDrawable vColor = (ColorDrawable) v.getBackground();
                    if (vColor != null) {
                        int clr = vColor.getColor();
                        jNodeChild.put("bgcolor", "RGB(" + Color.red(clr) + "," + Color.green(clr) + "," + Color.blue(clr) + ")");
                    }

                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    jNodeChild.put("bgcolor", v.getBackground().getColorFilter());
                }
            }
            if (isAttributeExists("event") && event != null) {


                if (isViewContains(v, (int) event.getX(), (int) event.getY())) {
                    if ((event.getAction() == MotionEvent.ACTION_DOWN))
                        jNodeChild.put("event", "ACTION_DOWN");
                    else if ((event.getAction() == MotionEvent.ACTION_UP))
                        jNodeChild.put("event", "ACTION_UP");
                    else if ((event.getAction() == MotionEvent.ACTION_MOVE))
                        jNodeChild.put("event", "move");
                }
            }
            if (isAttributeExists("parent")) {


                jNodeChild.put("parent", v.getParent().getClass().getSimpleName());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getThemeName() {
        PackageInfo packageInfo;
        try {
            packageInfo = acCurrent.getPackageManager().getPackageInfo(acCurrent.getPackageName(), PackageManager.GET_META_DATA);
            int themeResId = packageInfo.applicationInfo.theme;
            return acCurrent.getResources().getResourceEntryName(themeResId);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public boolean canIncludeThisView(JSONObject jNodeChild, View v) {


        int id = v.getId();
        String _element = v.getClass().getSimpleName();
        for (String s : lstContainer) {

            if (s.toLowerCase().equals(_element.toLowerCase())) {
                try {
                    jNodeChild.put("element", _element);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
            if ((v.getTag() != null) && s.startsWith(".") && s.toLowerCase().equals("." + v.getTag().toString().toLowerCase())) {
                try {
                    jNodeChild.put("element", _element);
                    jNodeChild.put("tag", s.substring(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (s.startsWith("#") && getidResourceByName(s.substring(1)) == v.getId()) {
                try {
                    jNodeChild.put("element", _element);
                    jNodeChild.put("id", s.substring(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
            // all
            if ((s.startsWith(".") && s.toLowerCase().equals(".all"))) {
                try {
                    jNodeChild.put("element", _element);
                    jNodeChild.put("tag", s.substring(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

        }


        return false;

    }

    private int getidResourceByName(String aString) {
        String packageName = acCurrent.getPackageName();
        int resId = acCurrent.getResources().getIdentifier(aString, "id", packageName);
        return resId;
    }



    /*
        *Ce analyse elements de chaque view recursivment et retourne tous les widgets
    	*  les elements retournes sous forme json avec leur proprites
    	*  @parm v :L'objet parent d'une activité
    	*

     */

    public void analyseViews(ViewGroup v, int level, JSONArray jsArrayChildren, MotionEvent event) {
        final int childCount = v.getChildCount();
        v.getId();
        try {
            JSONObject jNode = new JSONObject();


            String _element = v.getClass().getName();

            _element = _element.substring(_element.lastIndexOf(".") + 1);
            Log.d("_element", _element + " ");

            if (canIncludeThisView(jNode, v)) {


                addAttributeIfDefined(jNode, v, event);
            }
            jsArrayChildren.put(jNode);

            JSONArray jArrayChild = new JSONArray();

           if (v instanceof Spinner) {

                Spinner sp = (Spinner) v;

                if (lstContainer.contains(_element)) {
                    if (isAttributeExists("size")) {

                        try {
                            jNode.put("size", sp.getAdapter().getCount());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
                if (lstContainer.contains("item")) {
                    for (int i = 0; i < sp.getAdapter().getCount(); i++) {

                        JSONObject jNodeChild = new JSONObject();
                        Log.d("item", sp.getAdapter().getItem(i).toString());
                        jNodeChild.put("element", "item");
                        if (isAttributeExists("text"))
                            jNodeChild.put("text", sp.getAdapter().getItem(i).toString());
                        if (isAttributeExists("id"))
                            jNodeChild.put("id", sp.getAdapter().getItem(i).hashCode());
                        if (isAttributeExists("position"))
                            jNodeChild.put("position", i);

                        jArrayChild.put(jNodeChild);
                    }

                }

            } else {
                for (int i = 0; i < childCount; i++) {
                    View child = v.getChildAt(i);

                    JSONObject jNodeChild = new JSONObject();

                    if ((child instanceof ViewGroup) && child.getTag() != hashTagResult) {

                        jNodeChild.put("children", jArrayChild);
                        analyseViews((ViewGroup) child, level + 1, jArrayChild, event);

                    } else {

                        _element = child.getClass().getName();

                        _element = _element.substring(_element.lastIndexOf(".") + 1);

                        Log.d("_element", _element + " " + this.lstContainer.size() + " " + v.getTag());


                        if (canIncludeThisView(jNodeChild, child)) {

                            addAttributeIfDefined(jNodeChild, child, event);

                            if (child instanceof Button && child.getTag() != hashTagResult_Button) {
                                if (isAttributeExists("text"))
                                    jNodeChild.put("text", ((Button) child).getText());


                            } else if (child instanceof TextView) {

                                TextView tx = (TextView) child;
                                if (isAttributeExists("length"))
                                    jNodeChild.put("length", tx.getText().length());
                                if (isAttributeExists("text"))
                                    jNodeChild.put("text", tx.getText());
                                if (isAttributeExists("color")) {
                                    int clr = tx.getCurrentTextColor();
                                    jNodeChild.put("color", "RGB(" + Color.red(clr) + "," + Color.green(clr) + "," + Color.blue(clr) + ")");
                                }

                            } else if (child instanceof EditText) {
                                EditText tx = (EditText) child;
                                if (isAttributeExists("length"))
                                    jNodeChild.put("length", tx.getText().length());
                                if (isAttributeExists("text"))
                                    jNodeChild.put("text", tx.getText());
                                if (isAttributeExists("color")) {
                                    int clr = tx.getCurrentTextColor();
                                    jNodeChild.put("color", "RGB(" + Color.red(clr) + "," + Color.green(clr) + "," + Color.blue(clr) + ")");
                                }

                            }
                        }
                        if (jNodeChild.length() > 0 && child.getTag() != hashTagResult_Button)

                            jArrayChild.put(jNodeChild);

                    }


                }
            }


            jNode.put("children", jArrayChild);


        } catch (JSONException exc) {
            exc.printStackTrace();

        }

    }


    public class SendPostRequest extends AsyncTask<String, Void, String> {


        String _url = "";
        String _dataToSend = "";
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
                HttpURLConnection conn;
                conn = (HttpURLConnection) url1.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);/* milliseconds */
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
            if (this._requestName == RequestName.add) {

                // Toast toast = Toast.makeText(acCurrent.getApplicationContext(), "prop added",
                //   Toast.LENGTH_LONG);

                // toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                // toast.show();
                propSending = false;

                propAdded = true;
            } else if (this._requestName == RequestName.image) {

               /* Toast toast = Toast.makeText(acCurrent.getApplicationContext(), "result received",
                        Toast.LENGTH_LONG);

                toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                toast.show();*/

            }

            handleRepsonse(result);
        }

        public void handleRepsonse(String result) {
            if (this._requestName == RequestName.add) {


                try {
                    JSONObject jsonObj = new JSONObject(result);
                    // Getting JSON Array node
                    JSONArray arr = jsonObj.getJSONArray("attributes");
                    JSONArray arrTags = jsonObj.getJSONArray("elements");
                    String _inter = jsonObj.getString("interpreter");
                    interpreter = _inter;
                    // looping through All Contacts
                    ArrayList<String> lst = new ArrayList<String>();
                    ArrayList<String> lst2 = new ArrayList<String>();
                    for (int i = 0; i < arr.length(); i++) {
                        String c = arr.getString(i);
                        Log.d("attribute :", c);
                        lst.add(c);
                    }
                    for (int i = 0; i < arrTags.length(); i++) {
                        String c = arrTags.getString(i);
                        Log.d("tags ", c);
                        lst2.add(c);
                    }
                    setLstAttributes(lst);
                    setLstContainer(lst2);
                    Log.d("size2 ", lst.size() + "");

                } catch (JSONException exc) {
                    exc.printStackTrace();

                }

            } else if (this._requestName == RequestName.image) {

                try {
                    if (result != "") {
                        JSONObject jsonObj = new JSONObject(result.toString());
                        String _inter = jsonObj.getString("global-verdict");
                        JSONArray _hightlight = jsonObj.getJSONArray("highlight-ids");

                        Log.e("verdict", _inter + "/n" + _hightlight.toString());

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        posLayout.size();
                        for (Integer i : posLayout) {

                            params.addRule(i);
                        }


                        ViewGroup vForResult = (ViewGroup) acCurrent.findViewById(android.R.id.content);
                        Button btn = null;
                        if (vForResult != null) {
                            RelativeLayout lr = (RelativeLayout) vForResult.findViewWithTag(hashTagResult);


                            if (lr == null) {

                                lr = new RelativeLayout(acCurrent);
                                lr.setLayoutParams(params);
                                lr.setTag(hashTagResult);
                                vForResult.addView(lr);


                            }

                            btn = (Button) lr.findViewWithTag(hashTagResult_Button);
                            if (btn == null) {
                                btn = new Button(acCurrent);
                                btn.setTag(hashTagResult_Button);
                                btn.setId(0);
                                btn.setLayoutParams(params);
                                lr.addView(btn);
                                params.addRule(RelativeLayout.LEFT_OF, btn.getId());
                                params.width = 80;
                                params.height = 80;
                                btn.setLayoutParams(params);
                            }


                            if (btn != null) {
                                if (_inter.toLowerCase().equals("true") || _inter.toLowerCase().equals("inconclusive"))
                                    btn.setBackgroundColor(Color.GREEN);
                                else btn.setBackgroundColor(Color.RED);
                                btn.setText("");

                            }


                        }
                        // Highlight elements, if any
                        for (int j = 0; j < _hightlight.length(); j++) {

                            JSONObject set_of_tuples1 = (JSONObject) _hightlight.get(j);
                            JSONArray js = set_of_tuples1.getJSONArray("ids");
                            final String jlink = set_of_tuples1.getString("link");
                            if (jlink != "")
                                btn.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jlink));
                                        acCurrent.startActivity(intent);
                                    }
                                });

                            for (int z = 0; z < js.length(); z++) {
                                JSONArray js2 = js.getJSONArray(0);
                                for (int h = 0; h < js2.length(); h++) {
                                    // Log.d("zzzzzz", js2.get(h) + "");
                                    int key = js2.getInt(h);
                                    if (idMap.containsKey(key)) {
                                        View v = acCurrent.findViewById(idMap.get(key).id);
                                        if (v != null)
                                            Util.customViewBorder(v, Color.RED);

                                        // v.setBackgroundResource(R.drawable.shape_border);
                                    }

                                }

                            }
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }


}

