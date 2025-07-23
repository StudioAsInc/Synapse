package com.synapse.social.studioasinc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SketchwareUtil {

    public static void sortListMap(final ArrayList<HashMap<String, Object>> listMap, final String key, final boolean isNumber, final boolean ascending) {
        Collections.sort(listMap, new Comparator<HashMap<String, Object>>() {
            public int compare(HashMap<String, Object> _compareMap1, HashMap<String, Object> _compareMap2) {
                try {
                    if (isNumber) {
                        int _count1 = Integer.parseInt(_compareMap1.get(key).toString());
                        int _count2 = Integer.parseInt(_compareMap2.get(key).toString());
                        return ascending ? Integer.compare(_count1, _count2) : Integer.compare(_count2, _count1);
                    } else {
                        return ascending ? _compareMap1.get(key).toString().compareTo(_compareMap2.get(key).toString())
                                         : _compareMap2.get(key).toString().compareTo(_compareMap1.get(key).toString());
                    }
                } catch (Exception e) {
                    return 0;
                }
            }
        });
    }

    public static boolean isConnected(Context _context) {
        ConnectivityManager _connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (_connectivityManager != null) {
            Network activeNetwork = _connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities networkCapabilities = _connectivityManager.getNetworkCapabilities(activeNetwork);
                return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }
        return false;
    }

    public static String copyFromInputStream(InputStream _inputStream) {
        ByteArrayOutputStream _outputStream = new ByteArrayOutputStream();
        byte[] _buf = new byte[1024];
        int _i;
        try {
            while ((_i = _inputStream.read(_buf)) != -1){
                _outputStream.write(_buf, 0, _i);
            }
            _outputStream.close();
            _inputStream.close();
        } catch (IOException _e) {
            return null;
        }
        return _outputStream.toString();
    }

    public static void hideKeyboard(Context _context, View view) {
        if (view != null) {
            InputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (_inputMethodManager != null) {
                _inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void showKeyboard(Context _context, View view) {
        if (view != null) {
            InputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (_inputMethodManager != null) {
                _inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    public static void showMessage(Context _context, String _s) {
        Toast.makeText(_context, _s, Toast.LENGTH_SHORT).show();
    }

    public static int getLocationX(View _view) {
        int _location[] = new int[2];
        _view.getLocationInWindow(_location);
        return _location[0];
    }

    public static int getLocationY(View _view) {
        int _location[] = new int[2];
        _view.getLocationInWindow(_location);
        return _location[1];
    }

    public static int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    public static ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<>();
        for (int i = 0; i < _list.getCount(); i++) {
            if (_list.isItemChecked(i)) {
                _result.add((double) i);
            }
        }
        return _result;
    }

    public static float getDip(Context _context, int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());
    }

    public static int getDisplayWidthPixels(Context _context) {
        return _context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getDisplayHeightPixels(Context _context) {
        return _context.getResources().getDisplayMetrics().heightPixels;
    }

    public static void getAllKeysFromMap(Map<String, Object> _map, ArrayList<String> _output) {
        if (_output == null) return;
        _output.clear();
        if (_map == null || _map.size() < 1) return;
        _output.addAll(_map.keySet());
    }
}