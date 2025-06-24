package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.support.customtabs.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidmads.library.qrgenearator.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.*;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.*;
import androidx.transition.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.budiyev.android.codescanner.*;
import com.caverock.androidsvg.*;
import com.google.android.material.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.playintegrity.*;
import com.google.firebase.perf.*;
/* import com.jsibbold.zoomage.*;
import com.shobhitpuri.custombuttons.*;
import com.sigma.niceswitch.*;
import com.theartofdev.edmodo.cropper.*;
import com.theophrast.ui.widget.*;
import com.wuyr.rippleanimation.*;
import com.yalantis.ucrop.*;
import eightbitlab.com.blurview.*;
import io.noties.markwon.*;
import io.noties.markwon.ext.strikethrough.*;
import io.noties.markwon.ext.tables.*;
import io.noties.markwon.ext.tasklist.*; */
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class LanguagesActivity extends AppCompatActivity {
	
	private HashMap<String, Object> lan = new HashMap<>();
	private String CurrentLanguage = "";
	
	private ArrayList<String> language = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> languages = new ArrayList<>();
	
	private LinearLayout container;
	private LinearLayout linear2;
	private CardView cardview1;
	private ImageView imageview1;
	private TextView textview1;
	private RecyclerView recyclerview1;
	
	private SharedPreferences langsettings;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.languages);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		container = findViewById(R.id.container);
		linear2 = findViewById(R.id.linear2);
		cardview1 = findViewById(R.id.cardview1);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		recyclerview1 = findViewById(R.id.recyclerview1);
		langsettings = getSharedPreferences("langsettings", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		recyclerview1.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				
			}
		});
	}
	
	private void initializeLogic() {
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		
		lan = new HashMap<>();
		lan.put("language", "English (International)");
		lan.put("preview", "English");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Bangla");
		lan.put("preview", "বাংলা");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Spanish");
		lan.put("preview", "Español");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "French");
		lan.put("preview", "Français");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "German");
		lan.put("preview", "Deutsch");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Chinese (Simplified)");
		lan.put("preview", "中文(简体)");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Japanese");
		lan.put("preview", "日本語");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Russian");
		lan.put("preview", "Русский");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Hindi");
		lan.put("preview", "हिन्दी");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Portuguese");
		lan.put("preview", "Português");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Arabic");
		lan.put("preview", "العربية");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Italian");
		lan.put("preview", "Italiano");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Korean");
		lan.put("preview", "한국어");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Dutch");
		lan.put("preview", "Nederlands");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Turkish");
		lan.put("preview", "Türkçe");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Swedish");
		lan.put("preview", "Svenska");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Greek");
		lan.put("preview", "Ελληνικά");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Polish");
		lan.put("preview", "Polski");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Norwegian");
		lan.put("preview", "Norsk");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Finnish");
		lan.put("preview", "Suomi");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Danish");
		lan.put("preview", "Dansk");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Hungarian");
		lan.put("preview", "Magyar");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Czech");
		lan.put("preview", "Čeština");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Romanian");
		lan.put("preview", "Română");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Thai");
		lan.put("preview", "ไทย");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Hebrew");
		lan.put("preview", "עברית");
		languages.add(lan);
		
		lan = new HashMap<>();
		lan.put("language", "Vietnamese");
		lan.put("preview", "Tiếng Việt");
		languages.add(lan);
		
		// Keep adding as needed for your app's reach
		
		
		// Continue adding more languages as needed
		recyclerview1.setAdapter(new Recyclerview1Adapter(languages));
		_stateColor(0xFFFAFAFA, 0xFFFAFAFA);
	}
	
	public void _stateColor(final int _statusColor, final int _navigationColor) {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(_statusColor);
		getWindow().setNavigationBarColor(_navigationColor);
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.language, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout main = _view.findViewById(R.id.main);
			final LinearLayout devider = _view.findViewById(R.id.devider);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView selection_ic = _view.findViewById(R.id.selection_ic);
			final TextView language_name = _view.findViewById(R.id.language_name);
			final TextView language_preview = _view.findViewById(R.id.language_preview);
			
			try{
				language_name.setText(_data.get((int)_position).get("language").toString());
				language_preview.setText(_data.get((int)_position).get("preview").toString());
				main.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						langsettings.edit().putString("langsettings", _data.get((int)_position).get("langcode").toString()).commit();
					}
				});
				selection_ic.setVisibility(View.GONE);
				if (langsettings.getString("langsettings", "").equals(_data.get((int)_position).get("langcode").toString())) {
					selection_ic.setImageResource(R.drawable.icon_check_circle_round);
					selection_ic.setVisibility(View.GONE);
				} else {
					selection_ic.setImageResource(R.drawable.icon_radio_button_unchecked_round);
					selection_ic.setVisibility(View.VISIBLE);
				}
			}catch(Exception e){
				 
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
