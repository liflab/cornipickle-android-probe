package ca.liflab.sonde;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Debug;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;


import static android.R.attr.accessibilityEventTypes;
import static android.R.attr.max;
import static android.R.attr.restoreAnyVersion;

public class Sonde {
    View _view;

    public Activity acCurrent;




    /**
     * The name of the server of the form "name:port"
     * e.g.: localhost:10101
     */
    public String server_name = "localhost:10101";
    final StringBuilder st = new StringBuilder("{\"tagname\":\"window\",\"URL\":\"localhost:10101/examples/misaligned-elements.html\",\"aspect-ratio\":1.4217536071032186,\"orientation\":\"portrait\",\"width\":1281,\"height\":901,\"device-width\":1920,\"device-height\":1080,\"device-aspect-ratio\":1.7777777777777777,\"mediaqueries\":{},\"children\":[{\"children\":[{\"children\":[{\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Example\"}]},{\"children\":[{\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Back to example list\"}]}]},{\"children\":[{\"tagname\":\"ul\",\"cornipickleid\":0,\"class\":\"menu\",\"top\":153,\"left\":29,\"children\":[{\"tagname\":\"li\",\"cornipickleid\":1,\"top\":153,\"left\":69,\"children\":[{\"tagname\":\"CDATA\",\"text\":\"First menu item\"}]},{\"tagname\":\"li\",\"cornipickleid\":2,\"top\":172,\"left\":69,\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Second menu item\"}]},{\"tagname\":\"li\",\"cornipickleid\":3,\"top\":191,\"left\":79,\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Another menu item\"}]},{\"tagname\":\"li\",\"cornipickleid\":4,\"top\":210,\"left\":69,\"children\":[{\"tagname\":\"CDATA\",\"text\":\"Final menu item\"}]}]}]},{\"tagname\":\"CDATA\",\"text\":\" Cornipickle explanation \"},{\"tagname\":\"CDATA\",\"text\":\" /explanation \"}]},{\"tagname\":\"CDATA\",\"text\":\" /contents \"}]}]}");

    /**
     * The probe's id
     */
    public String probe_id = "1";

    public ArrayList<Integer> posLayout = new ArrayList<Integer>();

    public Boolean probAdded=false;
    /**
     * The probe's hash
     */
    public String hash = "interpreter";

    public String interpreter = "";

    public JSONObject jsonObj = new JSONObject();
    JSONArray jsonChildreen;
    SendPostRequest _sendRequest;

    ArrayList<String> lstAttributes = new ArrayList<String>();


    ArrayList<String> lstContainer = new ArrayList<String>();
    int cornipickleid = 0;
    Map<Integer, infoHighId> idMap = new HashMap<Integer, infoHighId>();

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

    public Sonde() {

    }

    public Sonde(Activity ac) {
        // ac.getWindow().getDecorView().getRootView();
        this._view = ac.findViewById(android.R.id.content);
        this.acCurrent = ac;
        this.posLayout.clear();
        this.posLayout.add(RelativeLayout.ALIGN_PARENT_BOTTOM);
        this.posLayout.add(RelativeLayout.ALIGN_PARENT_RIGHT);
        sendStart("http://192.168.109.1:10101/probe","", RequestName.others);
    }

    public Sonde(Activity ac, ArrayList<Integer> posLayoutResult) {
        // ac.getWindow().getDecorView().getRootView();
        this._view = ac.findViewById(android.R.id.content);
        this.acCurrent = ac;
        this.posLayout.clear();
        this.posLayout = posLayoutResult;

        sendStart("http://192.168.109.1:10101/probe","", RequestName.others);

    }

    public void testjson() {


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
            jsonObj.put("url", "");
            jsonObj.put("device-aspect-ratio", Util.getAspectRatio(acCurrent));
            jsonObj.put("device-density", Util.getDensity(acCurrent));
            this.jsonChildreen = new JSONArray();

            jsonObj.put("children", jsonChildreen);
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
            data = "contents=" + URLEncoder.encode(jsonObj.toString(), "UTF-8");// "contents=%7B%22tagname%22%3A%22window%22%2C%22URL%22%3A%22localhost%3A11019%2Fexamples%2Fmisaligned-elements.html%22%2C%22aspect-ratio%22%3A3.747072599531616%2C%22orientation%22%3A%22portrait%22%2C%22width%22%3A1600%2C%22height%22%3A427%2C%22device-width%22%3A1615%2C%22device-height%22%3A1026%2C%22device-aspect-ratio%22%3A1.5740740740740742%2C%22mediaqueries%22%3A%7B%220%22%3A%22true%22%7D%2C%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Example%22%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Back%20to%20example%20list%22%7D%5D%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22tagname%22%3A%22ul%22%2C%22cornipickleid%22%3A0%2C%22class%22%3A%22menu%22%2C%22top%22%3A153%2C%22left%22%3A29%2C%22children%22%3A%5B%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A1%2C%22top%22%3A153%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22First%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A2%2C%22top%22%3A172%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Second%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A3%2C%22top%22%3A191%2C%22left%22%3A79%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Another%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A4%2C%22top%22%3A210%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Final%20menu%20item%22%7D%5D%7D%5D%7D%5D%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20Cornipickle%20explanation%20%22%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fexplanation%20%22%7D%5D%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fcontents%20%22%7D%5D%7D%5D%7D";//URLEncoder.encode(jsonObj.toString(),"UTF-8");
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
            data = "contents=" + URLEncoder.encode(jsonObj.toString(), "UTF-8");// "contents=%7B%22tagname%22%3A%22window%22%2C%22URL%22%3A%22localhost%3A11019%2Fexamples%2Fmisaligned-elements.html%22%2C%22aspect-ratio%22%3A3.747072599531616%2C%22orientation%22%3A%22portrait%22%2C%22width%22%3A1600%2C%22height%22%3A427%2C%22device-width%22%3A1615%2C%22device-height%22%3A1026%2C%22device-aspect-ratio%22%3A1.5740740740740742%2C%22mediaqueries%22%3A%7B%220%22%3A%22true%22%7D%2C%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Example%22%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Back%20to%20example%20list%22%7D%5D%7D%5D%7D%2C%7B%22children%22%3A%5B%7B%22tagname%22%3A%22ul%22%2C%22cornipickleid%22%3A0%2C%22class%22%3A%22menu%22%2C%22top%22%3A153%2C%22left%22%3A29%2C%22children%22%3A%5B%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A1%2C%22top%22%3A153%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22First%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A2%2C%22top%22%3A172%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Second%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A3%2C%22top%22%3A191%2C%22left%22%3A79%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Another%20menu%20item%22%7D%5D%7D%2C%7B%22tagname%22%3A%22li%22%2C%22cornipickleid%22%3A4%2C%22top%22%3A210%2C%22left%22%3A69%2C%22children%22%3A%5B%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22Final%20menu%20item%22%7D%5D%7D%5D%7D%5D%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20Cornipickle%20explanation%20%22%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fexplanation%20%22%7D%5D%7D%2C%7B%22tagname%22%3A%22CDATA%22%2C%22text%22%3A%22%20%2Fcontents%20%22%7D%5D%7D%5D%7D";//URLEncoder.encode(jsonObj.toString(),"UTF-8");
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
        //   this._sendRequest = new SendPostRequest(url,jsonObj.toString()
        this._sendRequest = new SendPostRequest(url, data);
        this._sendRequest.execute();

    }

    public void sendStart(String url, String data, RequestName add) {
        //   this._sendRequest = new SendPostRequest(url,jsonObj.toString()
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
            // res.getResourceEntryName(view.getId())
            //v.getResources().getResourceEntryName(v.getId()
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
                        jNodeChild.put("event", "click");
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
        String _tagname = v.getClass().getSimpleName();
        for (String s : lstContainer) {

            if (s.toLowerCase().equals(_tagname.toLowerCase())) {
                try {
                    jNodeChild.put("tagname", _tagname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
            if ((v.getTag() != null) && s.startsWith(".") && s.toLowerCase().equals("." + v.getTag().toString().toLowerCase())) {
                try {
                    jNodeChild.put("tagname", _tagname);
                    jNodeChild.put("class", s.substring(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (s.startsWith("#") && getidResourceByName(s.substring(1)) == v.getId()) {
                try {
                    jNodeChild.put("tagname", _tagname);
                    jNodeChild.put("id", s.substring(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
            // all
            if ((s.startsWith(".") && s.toLowerCase().equals(".all"))) {
                try {
                    jNodeChild.put("tagname", _tagname);
                    jNodeChild.put("class", s.substring(1));
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
        *Cette methodes analyse les elements de chaque view recursivment et retourne tous les widgets
    	*  les elements retournes sous forme json avec leur proprites
    	*  @parm v :L'objet parent d'une activité
    	*

     */

    public void analyseViews(ViewGroup v, int level, JSONArray jsArrayChildren, MotionEvent event) {
        final int childCount = v.getChildCount();
        v.getId();
        try {
            JSONObject jNode = new JSONObject();
            // Log.d("_tagname5", v.getClass().getSimpleName());

            String _tagname = v.getClass().getName();

            _tagname = _tagname.substring(_tagname.lastIndexOf(".") + 1);//.toLowerCase();
            Log.d("_tagname2", _tagname + " " + this.lstContainer.size() + " " + v.getTag());


            if (canIncludeThisView(jNode, v)) {

                // jNode.put("tagname", _tagname);

                addAttributeIfDefined(jNode, v, event);
            }
            jsArrayChildren.put(jNode);

            // if(childCount>0)
            JSONArray jArrayChild = new JSONArray();

            if (v instanceof BottomNavigationView) {


                Menu m = ((BottomNavigationView) v).getMenu();
                if (lstContainer.contains(_tagname)) {
                    if (isAttributeExists("size")) {
                        jNode.put("size", m.size());
                    }

                }
                if (lstContainer.contains("menuItem")) {
                    for (int i = 0; i < m.size(); i++) {


                        JSONObject jNodeChild = new JSONObject();
                        Log.d("menu", m.getItem(i).toString());
                        jNodeChild.put("tagname", "menuItem");
                        if (isAttributeExists("menuItemText"))
                            jNodeChild.put("menuItemText", m.getItem(i).toString());
                        jArrayChild.put(jNodeChild);
                    }

                }

            } else {
                for (int i = 0; i < childCount; i++) {
                    View child = v.getChildAt(i);

                    JSONObject jNodeChild = new JSONObject();

                    if ((child instanceof ViewGroup)) {

                        jNodeChild.put("children", jArrayChild);
                        analyseViews((ViewGroup) child, level + 1, jArrayChild, event);

                    } else {

                        _tagname = child.getClass().getName();
                        _tagname = _tagname.substring(_tagname.lastIndexOf(".") + 1);//.toLowerCase();
                        Log.d("_tagname", _tagname + " " + this.lstContainer.size() + " " + v.getTag());

                        if (canIncludeThisView(jNodeChild, child)) {

                            //  jNodeChild.put("tagname", _tagname);
                            addAttributeIfDefined(jNodeChild, child, event);

                            if (child instanceof Button) {
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

                            }/* else {

                                jNodeChild.put("CDATA", _tagname);
                            }*/


                        } /*else {

                            jNodeChild.put("", _tagname);
                        }*/
                        if (jNodeChild.length() > 0)

                            jArrayChild.put(jNodeChild);

// fin else
                    }


                }
            }


            jNode.put("children", jArrayChild);


        } catch (JSONException exc) {
            exc.printStackTrace();

        }

    }


    public class SendPostRequest extends AsyncTask<String, Void, String> {
        String _url = "http://192.168.109.1:10101/addProp";
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
                        //    Log.e("params2", sb.toString());
                        //    handleRepsonse(sb.toString());
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

                Toast toast = Toast.makeText(acCurrent.getApplicationContext(), "prop added",
                        Toast.LENGTH_LONG);

                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();
                probAdded=true;
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
                    JSONArray arrTags = jsonObj.getJSONArray("tagnames");
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

                        //  TextView txtView = (TextView) acCurrent.findViewById(R.id.txtResult);
                        //  txtView.setText(_inter);
                        Log.e("verdict", _inter + "/n" + _hightlight.toString());
                        //Nous récupérons un Set contenant des entiers
                        //      Set<Integer> setInt = idMap.keySet();
                        //Utilisation d'un itérateur générique
                        //   Iterator<Integer> it = setInt.iterator();
                        // create the layout params that will be used to define how your
                        // button will be displayed
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        posLayout.size();
                        for (Integer i : posLayout) {

                            params.addRule(i);
                        }


                        System.out.println("Parcours d'une Map avec keySet : ");
                        ViewGroup vForResult = (ViewGroup) acCurrent.findViewById(android.R.id.content);
                        if (vForResult != null) {
                            RelativeLayout lr = (RelativeLayout) vForResult.findViewWithTag("lrResult");
                            //Button btn=new Button(acCurrent);

                            if (lr == null) {
                                // params.set
                                // Create LinearLayout
                                lr = new RelativeLayout(acCurrent);
                                lr.setLayoutParams(params);
                                lr.setTag("lrResult");
                                vForResult.addView(lr);


                            }

                            Button btn = (Button) lr.findViewWithTag("btnResult");
                            if (btn == null) {
                                btn = new Button(acCurrent);
                                btn.setTag("btnResult");
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
                            for (int z = 0; z < js.length(); z++) {
                                JSONArray js2 = js.getJSONArray(0);
                                for (int h = 0; h < js2.length(); h++) {
                                    Log.d("zzzzzz", js2.get(h) + "");
                                    int key = js2.getInt(h);
                                    if (idMap.containsKey(key)) {
                                        View v = acCurrent.findViewById(idMap.get(key).id);
                                        if (v != null)
                                            v.setBackgroundResource(R.drawable.shape_border);
                                    }

                                }

                            }
                        }


                      /*  //ensuite vous savez faire
                        while (it.hasNext()) {
                            int key = it.next();
                            Log.d("Valeur pour la clé ", key + " = " + idMap.get(key).toString());
                            //    View v = acCurrent.findViewById(idMap.get(key).id);

                            // v.setBackgroundResource(R.drawable.shape_border_none);
                        }

*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }


}

