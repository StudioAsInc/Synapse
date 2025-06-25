package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kr.co.prnd.readmore.ReadMoreTextView;

public class MessagesActivity extends AppCompatActivity {

    private static final String CACHE_PREFERENCES = "cache";
    private static final String CACHE_KEY_CHAT_INBOX = "chat_inbox_cache";
    private static final String CACHE_KEY_GROUPS = "group_cache";
    private static final String FB_PATH_SKYLINE_INBOX = "skyline/inbox";
    private static final String FB_PATH_SKYLINE_CHATS = "skyline/chats";
    private static final String FB_PATH_SKYLINE_USERS = "skyline/users";
    private static final String FB_PATH_SYNAPSE_GROUPS = "synapse/groups";

    private final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private FloatingActionButton fabButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TabLayout tabLayout;
    private RecyclerView inboxRecyclerView, groupsRecyclerView;
    private LinearLayout noInternetLayout, loadingLayout;
    private LinearLayout inboxLayout, requestsLayout, groupsLayout;
    private TextView noInboxTextView, noInternetSubtitleTextView, retryButton;
    private LinearLayout bottomNavHome, bottomNavSearch, bottomNavVideos, bottomNavChats, bottomNavProfile;
    private ImageView bottomNavHomeIcon, bottomNavSearchIcon, bottomNavVideosIcon, bottomNavChatsIcon, bottomNavProfileIcon;

    private ArrayList<HashMap<String, Object>> chatInboxList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> groupsList = new ArrayList<>();
    private HashMap<String, Object> userInfoCache = new HashMap<>();
    private String currentFabMode = "chat";

    private SharedPreferences sharedPrefsCache;
    private RequestNetwork requestNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.messages);
        FirebaseApp.initializeApp(this);
        initializeViews();
        initializeLogic();
    }

    private void initializeViews() {
        sharedPrefsCache = getSharedPreferences(CACHE_PREFERENCES, Activity.MODE_PRIVATE);
        requestNetwork = new RequestNetwork(this);

        fabButton = findViewById(R.id._fab);
        tabLayout = findViewById(R.id.MessagesPageTabLayout);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        noInternetLayout = findViewById(R.id.noInternetBody);
        loadingLayout = findViewById(R.id.loadingBody);
        inboxLayout = findViewById(R.id.InboxChatMessagesLayout);
        requestsLayout = findViewById(R.id.InboxMessagesRequestsLayout);
        groupsLayout = findViewById(R.id.InboxMessagesGroupsLayout);
        inboxRecyclerView = findViewById(R.id.InboxRecyclerView);
        groupsRecyclerView = findViewById(R.id.groups_name_list);
        noInboxTextView = findViewById(R.id.noInbox);
        noInternetSubtitleTextView = findViewById(R.id.noInternetBodySubtitle);
        retryButton = findViewById(R.id.noInternetBodyRetry);

        bottomNavHome = findViewById(R.id.bottom_home);
        bottomNavSearch = findViewById(R.id.bottom_search);
        bottomNavVideos = findViewById(R.id.bottom_videos);
        bottomNavChats = findViewById(R.id.bottom_chats);
        bottomNavProfile = findViewById(R.id.bottom_profile);

        bottomNavHomeIcon = findViewById(R.id.bottom_home_ic);
        bottomNavSearchIcon = findViewById(R.id.bottom_search_ic);
        bottomNavVideosIcon = findViewById(R.id.bottom_videos_ic);
        bottomNavChatsIcon = findViewById(R.id.bottom_chats_ic);
        bottomNavProfileIcon = findViewById(R.id.bottom_profile_ic);

        setupListeners();
    }

    private void setupListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                inboxLayout.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                requestsLayout.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
                groupsLayout.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                fabButton.setVisibility(position == 1 ? View.GONE : View.VISIBLE);
                currentFabMode = (position == 0) ? "chat" : (position == 2) ? "groups" : "request";
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        swipeRefreshLayout.setOnRefreshListener(this::fetchData);

        retryButton.setOnClickListener(v -> fetchData());

        fabButton.setOnClickListener(v -> {
            if ("groups".equals(currentFabMode)) {
                startActivity(new Intent(getApplicationContext(), CreateGroupActivity.class));
            } else if ("chat".equals(currentFabMode)) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                finish();
            }
        });

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        View.OnClickListener homeListener = v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        };
        bottomNavHome.setOnClickListener(homeListener);

        bottomNavSearch.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));
        bottomNavVideos.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LineVideoPlayerActivity.class)));

        bottomNavProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra("uid", auth.getCurrentUser().getUid());
            startActivity(intent);
        });
    }

    private void initializeLogic() {
        applySystemBarColors(Color.WHITE, Color.WHITE);
        updateBottomNavIcons();
        applyRippleEffect(retryButton, getColor(R.color.colorPrimary), 0xFF9FA8DA, 100, 0, 0xFF1E88E5);

        tabLayout.addTab(tabLayout.newTab().setText("CHATS"));
        tabLayout.addTab(tabLayout.newTab().setText("CHANNELS"));
        tabLayout.addTab(tabLayout.newTab().setText("GROUPS"));
        tabLayout.setTabTextColors(0xFF9E9E9E, getColor(R.color.colorPrimary));
        tabLayout.setTabRippleColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}}, new int[]{0xFFEEEEEE}));
        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.colorPrimary));
        tabLayout.setElevation(2);

        inboxLayout.setVisibility(View.VISIBLE);
        requestsLayout.setVisibility(View.GONE);
        groupsLayout.setVisibility(View.GONE);

        inboxRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inboxRecyclerView.setAdapter(new InboxRecyclerViewAdapter(chatInboxList));

        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupsRecyclerView.setAdapter(new Groups_name_listAdapter(groupsList));

        noInternetSubtitleTextView.setText(getString(R.string.reasons_may_be) + "\n\n" + getString(R.string.err_no_internet) + "\n" + getString(R.string.err_app_maintenance) + "\n" + getString(R.string.err_problem_on_our_side));

        loadDataFromCache();
        fetchData();
    }

    private void loadDataFromCache() {
        String cachedInboxJson = sharedPrefsCache.getString(CACHE_KEY_CHAT_INBOX, "");
        if (!cachedInboxJson.isEmpty()) {
            try {
                chatInboxList.clear();
                chatInboxList.addAll(new Gson().fromJson(cachedInboxJson, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()));
                sortListByDate(chatInboxList, "push_date");
                inboxRecyclerView.getAdapter().notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("MessagesActivity", "Error reading chat cache", e);
            }
        }

        String cachedGroupsJson = sharedPrefsCache.getString(CACHE_KEY_GROUPS, "");
        if (!cachedGroupsJson.isEmpty()) {
            try {
                groupsList.clear();
                groupsList.addAll(new Gson().fromJson(cachedGroupsJson, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()));
                groupsRecyclerView.getAdapter().notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("MessagesActivity", "Error reading group cache", e);
            }
        }
    }

    private void fetchData() {
        if (chatInboxList.isEmpty()) {
            showLoadingState();
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
        checkInternetAndFetchFirebase();
    }

    private void checkInternetAndFetchFirebase() {
        requestNetwork.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "internet_check", new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                showContentState();
                fetchInboxData();
                fetchGroupsData();
            }
            @Override
            public void onErrorResponse(String tag, String message) {
                if (chatInboxList.isEmpty()) {
                    showNoInternetState();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MessagesActivity.this, "Couldn't refresh. Showing cached data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchInboxData() {
        if (auth.getCurrentUser() == null) return;
        DatabaseReference inboxRef = firebase.getReference(FB_PATH_SKYLINE_INBOX).child(auth.getCurrentUser().getUid());
        inboxRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    chatInboxList.clear();
                    GenericTypeIndicator<HashMap<String, Object>> typeIndicator = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        chatInboxList.add(snapshot.getValue(typeIndicator));
                    }
                    sortListByDate(chatInboxList, "push_date");
                    inboxRecyclerView.getAdapter().notifyDataSetChanged();
                    sharedPrefsCache.edit().putString(CACHE_KEY_CHAT_INBOX, new Gson().toJson(chatInboxList)).apply();
                    inboxRecyclerView.setVisibility(View.VISIBLE);
                    noInboxTextView.setVisibility(View.GONE);
                } else {
                    chatInboxList.clear();
                    inboxRecyclerView.getAdapter().notifyDataSetChanged();
                    sharedPrefsCache.edit().remove(CACHE_KEY_CHAT_INBOX).apply();
                    inboxRecyclerView.setVisibility(View.GONE);
                    noInboxTextView.setVisibility(View.VISIBLE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("MessagesActivity", "Firebase inbox fetch cancelled: " + databaseError.getMessage());
            }
        });
    }
    
    private void fetchGroupsData() {
        DatabaseReference groupsRef = firebase.getReference(FB_PATH_SYNAPSE_GROUPS);
        groupsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupsList.clear();
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<HashMap<String, Object>> typeIndicator = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        groupsList.add(snapshot.getValue(typeIndicator));
                    }
                }
                groupsRecyclerView.getAdapter().notifyDataSetChanged();
                sharedPrefsCache.edit().putString(CACHE_KEY_GROUPS, new Gson().toJson(groupsList)).apply();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MessagesActivity", "Firebase groups fetch cancelled: " + databaseError.getMessage());
            }
        });
    }

    private void showLoadingState() {
        swipeRefreshLayout.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
    }
    
    private void showNoInternetState() {
        swipeRefreshLayout.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }
    
    private void showContentState() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void updateBottomNavIcons() {
        setImageViewColor(bottomNavHomeIcon, 0xFFBDBDBD);
        setImageViewColor(bottomNavSearchIcon, 0xFFBDBDBD);
        setImageViewColor(bottomNavVideosIcon, 0xFFBDBDBD);
        setImageViewColor(bottomNavChatsIcon, 0xFF000000);
        setImageViewColor(bottomNavProfileIcon, 0xFFBDBDBD);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void sortListByDate(ArrayList<HashMap<String, Object>> list, final String key) {
        Collections.sort(list, (map1, map2) -> {
            try {
                long date1 = Long.parseLong(map1.get(key).toString());
                long date2 = Long.parseLong(map2.get(key).toString());
                return Long.compare(date2, date1);
            } catch (Exception e) {
                return 0;
            }
        });
    }

    private void setTimeAgo(final double currentTime, final TextView textView) {
        long timeDiff = System.currentTimeMillis() - (long) currentTime;
        long seconds = timeDiff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        
        if (seconds < 60) textView.setText(seconds <= 1 ? "1 second ago" : seconds + " seconds ago");
        else if (minutes < 60) textView.setText(minutes <= 1 ? "1 minute ago" : minutes + " minutes ago");
        else if (hours < 24) textView.setText(hours <= 1 ? "1 hour ago" : hours + " hours ago");
        else if (days < 7) textView.setText(days <= 1 ? "1 day ago" : days + " days ago");
        else {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis((long) currentTime);
            textView.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(c.getTime()));
        }
    }
    
    private void applySystemBarColors(final int statusColor, final int navigationColor) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(statusColor);
        getWindow().setNavigationBarColor(navigationColor);
    }

    private void setImageViewColor(final ImageView imageView, final int color) {
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void applyRippleEffect(final View view, final int color, final int rippleColor, final float radius, final float stroke, final int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(radius);
        shape.setStroke((int) stroke, strokeColor);
        RippleDrawable ripple = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{rippleColor}), shape, null);
        view.setBackground(ripple);
    }
    
    private void setRoundedImage(final ImageView imageView, final float radius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.TRANSPARENT);
        gd.setCornerRadius(radius);
        imageView.setClipToOutline(true);
        imageView.setBackground(gd);
    }

    public class InboxRecyclerViewAdapter extends RecyclerView.Adapter<InboxRecyclerViewAdapter.ViewHolder> {
        private final ArrayList<HashMap<String, Object>> data;

        public InboxRecyclerViewAdapter(ArrayList<HashMap<String, Object>> arr) {
            this.data = arr;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_msg_list_cv_synapse, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout mainLayout, userStatusCircleBG, userStatusCircleIN;
            ImageView profileImage, genderBadge, verifiedBadge, messageStateIcon;
            TextView usernameTextView, lastMessageTextView, pushTimeTextView, unreadCountBadge;

            ViewHolder(View view) {
                super(view);
                mainLayout = view.findViewById(R.id.main);
                profileImage = view.findViewById(R.id.profileCardImage);
                userStatusCircleBG = view.findViewById(R.id.userStatusCircleBG);
                userStatusCircleIN = view.findViewById(R.id.userStatusCircleIN);
                usernameTextView = view.findViewById(R.id.username);
                genderBadge = view.findViewById(R.id.genderBadge);
                verifiedBadge = view.findViewById(R.id.verifiedBadge);
                lastMessageTextView = view.findViewById(R.id.last_message);
                messageStateIcon = view.findViewById(R.id.message_state);
                pushTimeTextView = view.findViewById(R.id.push);
                unreadCountBadge = view.findViewById(R.id.unread_messages_count_badge);
            }

            void bind(final HashMap<String, Object> item) {
                applyRippleEffect(mainLayout, Color.WHITE, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
                applyRippleEffect(userStatusCircleBG, Color.WHITE, Color.TRANSPARENT, 300, 0, Color.TRANSPARENT);
                applyRippleEffect(userStatusCircleIN, 0xFF388E3C, Color.TRANSPARENT, 300, 0, Color.TRANSPARENT);
                applyRippleEffect(unreadCountBadge, getColor(R.color.colorPrimary), Color.TRANSPARENT, 300, 0, Color.TRANSPARENT);
                
                String lastMessage = item.getOrDefault("last_message_text", "No chats yet").toString();
                if ("null".equals(lastMessage)) lastMessage = "No chats yet";
                lastMessageTextView.setText(lastMessage);

                String lastMessageUID = item.getOrDefault("last_message_uid", "").toString();
                if (lastMessageUID.equals(auth.getCurrentUser().getUid())) {
                    messageStateIcon.setVisibility(View.VISIBLE);
                    unreadCountBadge.setVisibility(View.GONE);
                    String state = item.getOrDefault("last_message_state", "sended").toString();
                    messageStateIcon.setImageResource("sended".equals(state) ? R.drawable.icon_done_round : R.drawable.icon_done_all_round);
                    lastMessageTextView.setTextColor(0xFF616161);
                    pushTimeTextView.setTextColor(0xFF616161);
                    lastMessageTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), Typeface.NORMAL);
                } else {
                    messageStateIcon.setVisibility(View.GONE);
                    lastMessageTextView.setTextColor(0xFF000000);
                    pushTimeTextView.setTextColor(0xFF000000);
                    lastMessageTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), Typeface.BOLD);
                    // Handle unread count logic if needed
                }

                setTimeAgo(Double.parseDouble(item.get("push_date").toString()), pushTimeTextView);

                String otherUserID = item.get("uid").toString();
                mainLayout.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra("uid", otherUserID);
                    startActivity(intent);
                });
                
                // Fetch and display user info, preferably from a cache
                // This part would ideally be handled by a ViewModel or Repository pattern to avoid network calls here.
            }
        }
    }

    public class Groups_name_listAdapter extends RecyclerView.Adapter<Groups_name_listAdapter.ViewHolder> {
        private final ArrayList<HashMap<String, Object>> data;

        public Groups_name_listAdapter(ArrayList<HashMap<String, Object>> arr) {
            this.data = arr;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_cv, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout bodyLayout;
            ImageView profileImage;
            TextView groupNameTextView, lastMessageTextView;

            ViewHolder(View view) {
                super(view);
                bodyLayout = view.findViewById(R.id.body);
                profileImage = view.findViewById(R.id.profileCardImage);
                groupNameTextView = view.findViewById(R.id.groupName);
                lastMessageTextView = view.findViewById(R.id.last_message);
            }

            void bind(final HashMap<String, Object> item) {
                groupNameTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/product_b.ttf"), Typeface.NORMAL);
                groupNameTextView.setText(item.getOrDefault("group_name", "Unknown Group").toString());
                lastMessageTextView.setText(item.getOrDefault("created_on", "").toString());
                setRoundedImage(profileImage, 360);
                
                // Load group image with Glide or another library here
                // Glide.with(profileImage.getContext()).load(item.get("group_image").toString()).into(profileImage);
                
                bodyLayout.setOnClickListener(v -> {
                    String password = item.getOrDefault("group_password", "null").toString();
                    String groupKey = item.getOrDefault("group_key", "").toString();
                    if (groupKey.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if ("null".equals(password)) {
                        openGroupChat(item);
                    } else {
                        promptForGroupPassword(item, password);
                    }
                });
            }

            private void openGroupChat(HashMap<String, Object> item) {
                Intent intent = new Intent(getApplicationContext(), GlobalGroupActivity.class);
                intent.putExtra("key", item.get("group_key").toString());
                intent.putExtra("uid", auth.getCurrentUser().getUid());
                intent.putExtra("group_name", item.get("group_name").toString());
                startActivity(intent);
            }

            private void promptForGroupPassword(HashMap<String, Object> item, String correctPassword) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MessagesActivity.this);
                builder.setTitle("Enter group password!");
                builder.setMessage("This is a private group, enter passkey to join.");

                final EditText passwordInput = new EditText(MessagesActivity.this);
                passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(passwordInput);

                builder.setPositiveButton("Enter", (dialog, which) -> {
                    if (correctPassword.equals(passwordInput.getText().toString())) {
                        openGroupChat(item);
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is not correct.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            }
        }
    }
}