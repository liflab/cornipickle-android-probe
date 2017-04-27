package ca.liflab.sonde;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;



public class WindowCallback extends SondeConfig implements Window.Callback {
    Window.Callback localCallback;

    public WindowCallback(Window.Callback localCallback, Activity a) {
        nameFile = a.getClass().getSimpleName()+".txt";
        this.a = a;
        this.localCallback = localCallback;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        sendActivityUiToServer(null);
        return localCallback.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {

        return localCallback.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        sendActivityUiToServer(event);

        return localCallback.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return localCallback.dispatchTrackballEvent(event);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return localCallback.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return localCallback.dispatchPopulateAccessibilityEvent(event);
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        //Log.d("tessssssssssssss0","v");
        return localCallback.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return localCallback.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return localCallback.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return localCallback.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return localCallback.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        localCallback.onWindowAttributesChanged(attrs);
    }

    @Override
    public void onContentChanged() {

        localCallback.onContentChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        _OnWindowChanged();
        localCallback.onWindowFocusChanged(hasFocus);

    }

    @Override
    public void onAttachedToWindow() {
        localCallback.onAttachedToWindow();

    }

    @Override
    public void onDetachedFromWindow() {
        localCallback.onDetachedFromWindow();
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        localCallback.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onSearchRequested() {
        return localCallback.onSearchRequested();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return localCallback.onSearchRequested(searchEvent);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return localCallback.onWindowStartingActionMode(callback);
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return localCallback.onWindowStartingActionMode(callback, type);
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        localCallback.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        localCallback.onActionModeFinished(mode);
    }
}
