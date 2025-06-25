package com.synapse.social.studioasinc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessagesActivity extends AppCompatActivity {

    private final FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    
    private FloatingActionButton fab;
    private HashMap<String, Object> userInfoCache = new HashMap<>();
    private String fabMode = "chat";

    private ArrayList<HashMap<String, Object>> chatInboxList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> groupsList = new ArrayList<>();

    private TabLayout messagesPageTabLayout;
    private SwipeRefreshLayout swipeLayout;
    private LinearLayout noInternetBody, loadingBody;
    private LinearLayout inboxChatMessagesLayout, inboxMessagesRequestsLayout, inboxMessagesGroupsLayout;
    private RecyclerView inboxRecyclerView, groupsRecyclerView;
    private TextView noInboxTextView;
    private SharedPreferences sharedPreferencesCache;
    private RequestNetwork requestNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
        FirebaseApp.initializeApp(this);
        initializeViews();
        initializeLogic();
    }

    private void initializeViews() {
        fab = findViewById(R.id._fab);
        messagesPageTabLayout = findViewById(R.id.MessagesPageTabLayout);
        swipeLayout = findViewById(R.id.swipeLayout);
        noInternetBody = findViewById(R.id.noInternetBody);
        loadingBody = findViewById(R.id.loadingBody);
        inboxChatMessagesLayout = findViewById(R.id.InboxChatMessagesLayout);
        inboxMessagesRequestsLayout = findViewById(R.id.InboxMessagesRequestsLayout);
        inboxMessagesGroupsLayout = findViewById(R.id.InboxMessagesGroupsLayout);
        inboxRecyclerView = findViewById(R.id.InboxRecyclerView);
        groupsRecyclerView = findViewById(R.id.groups_name_list);
        noInboxTextView = findViewById(R.id.noInbox);

        requestNetwork = new RequestNetwork(this);
        sharedPreferencesCache = getSharedPreferences("cache", Activity.MODE_PRIVATE);
    }
    
    private void initializeLogic() {
        setSystemBarColors(Color.WHITE, Color.WHITE);
        setupBottomNavigation();
        
        TextView noInternetSubtitle = findViewById(R.id.noInternetBodySubtitle);
        noInternetSubtitle.setText(getString(R.string.reasons_may_be) + "\n\n" + getString(R.string.err_no_internet) + "\n" + getString(R.string.err_app_maintenance) + "\n" + getString(R.string.err_problem_on_our_side));
        
        TextView retryButton = findViewById(R.id.noInternetBodyRetry);
        applyRippleStyle(retryButton, getColor(R.color.colorPrimary), 0xFF9FA8DA, 100, 0, 0xFF1E88E5);
        retryButton.setOnClickListener(v -> fetchData());
        
        setupTabLayout();
        setupRecyclerViews();
        
        fab.setOnClickListener(v -> {
            if ("groups".equals(fabMode)) {
                startActivity(new Intent(getApplicationContext(), CreateGroupActivity.class));
            } else if ("chat".equals(fabMode)) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                finish();
            }
        });
        
        loadDataFromCache();
        fetchData();
    }
    
    private void setupTabLayout() {
        messagesPageTabLayout.addTab(messagesPageTabLayout.newTab().setText("CHATS"));
        messagesPageTabLayout.addTab(messagesPageTabLayout.newTab().setText("CHANNELS"));
        messagesPageTabLayout.addTab(messagesPageTabLayout.newTab().setText("GROUPS"));
        messagesPageTabLayout.setTabTextColors(0xFF9E9E9E, getColor(R.color.colorPrimary));
        messagesPageTabLayout.setTabRippleColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}}, new int[]{0xFFEEEEEE}));
        messagesPageTabLayout.setSelectedTabIndicatorColor(getColor(R.color.colorPrimary));
        messagesPageTabLayout.setElevation(2);
        
        messagesPageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                inboxChatMessagesLayout.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                inboxMessagesRequestsLayout.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
                inboxMessagesGroupsLayout.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                fab.setVisibility(position == 1 ? View.GONE : View.VISIBLE);
                fabMode = (position == 0) ? "chat" : (position == 2) ? "groups" : "request";
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });
        
        inboxChatMessagesLayout.setVisibility(View.VISIBLE);
        inboxMessagesRequestsLayout.setVisibility(View.GONE);
        inboxMessagesGroupsLayout.setVisibility(View.GONE);
    }
    
    private void setupRecyclerViews() {
        inboxRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inboxRecyclerView.setAdapter(new InboxRecyclerViewAdapter(chatInboxList));
        
        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupsRecyclerView.setAdapter(new Groups_name_listAdapter(groupsList));
    }
    
    private void setupBottomNavigation() {
        findViewById(R.id.bottom_home).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.bottom_search).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));
        findViewById(R.id.bottom_videos).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LineVideoPlayerActivity.class)));
        findViewById(R.id.bottom_profile).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra("uid", auth.getCurrentUser().getUid());
            startActivity(intent);
        });
        updateBottomNavIcons();
    }
    
    private void updateBottomNavIcons() {
        setImageViewColor(findViewById(R.id.bottom_home_ic), 0xFFBDBDBD);
        setImageViewColor(findViewById(R.id.bottom_search_ic), 0xFFBDBDBD);
        setImageViewColor(findViewById(R.id.bottom_videos_ic), 0xFFBDBDBD);
        setImageViewColor(findViewById(R.id.bottom_chats_ic), 0xFF000000);
        setImageViewColor(findViewById(R.id.bottom_profile_ic), 0xFFBDBDBD);
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

    private void fetchData() {
        if (chatInboxList.isEmpty() && groupsList.isEmpty()) {
            loadingBody.setVisibility(View.VISIBLE);
            swipeLayout.setVisibility(View.GONE);
        } else {
            swipeLayout.setRefreshing(true);
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        requestNetwork.startRequestNetwork(RequestNetworkController.GET, "https://google.com", "internet_check", new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                loadingBody.setVisibility(View.GONE);
                swipeLayout.setVisibility(View.VISIBLE);
                noInternetBody.setVisibility(View.GONE);
                fetchFirebaseData();
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                swipeLayout.setRefreshing(false);
                loadingBody.setVisibility(View.GONE);
                if (chatInboxList.isEmpty() && groupsList.isEmpty()) {
                    noInternetBody.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void fetchFirebaseData() {
        fetchInbox();
        fetchGroups();
    }

    private void loadDataFromCache() {
        String cachedInbox = sharedPreferencesCache.getString("chat_inbox_cache", "");
        if (!cachedInbox.isEmpty()) {
            chatInboxList = new Gson().fromJson(cachedInbox, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
            inboxRecyclerView.getAdapter().notifyDataSetChanged();
        }

        String cachedGroups = sharedPreferencesCache.getString("group_cache", "");
        if (!cachedGroups.isEmpty()) {
            groupsList = new Gson().fromJson(cachedGroups, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
            groupsRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void fetchInbox() {
        if (auth.getCurrentUser() == null) return;
        DatabaseReference inboxRef = firebaseDB.getReference("skyline/inbox").child(auth.getCurrentUser().getUid());
        inboxRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatInboxList.clear();
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        chatInboxList.add(postSnapshot.getValue(t));
                    }
                    Collections.sort(chatInboxList, (m1, m2) -> Long.compare(Long.parseLong(m2.get("push_date").toString()), Long.parseLong(m1.get("push_date").toString())));
                    sharedPreferencesCache.edit().putString("chat_inbox_cache", new Gson().toJson(chatInboxList)).apply();
                    noInboxTextView.setVisibility(View.GONE);
                } else {
                    noInboxTextView.setVisibility(View.VISIBLE);
                    sharedPreferencesCache.edit().remove("chat_inbox_cache").apply();
                }
                inboxRecyclerView.getAdapter().notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                swipeLayout.setRefreshing(false);
            }
        });
    }
    
    private void fetchGroups() {
        DatabaseReference groupsRef = firebaseDB.getReference("synapse/groups");
        groupsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupsList.clear();
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        groupsList.add(postSnapshot.getValue(t));
                    }
                    sharedPreferencesCache.edit().putString("group_cache", new Gson().toJson(groupsList)).apply();
                }
                groupsRecyclerView.getAdapter().notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void setTimeAgo(double timestamp, TextView textView) {
        long timeMillis = (long) timestamp;
        long now = System.currentTimeMillis();
        long diff = now - timeMillis;

        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (seconds < 60) textView.setText(String.format(Locale.US, "%d seconds ago", seconds));
        else if (minutes < 60) textView.setText(String.format(Locale.US, "%d minutes ago", minutes));
        else if (hours < 24) textView.setText(String.format(Locale.US, "%d hours ago", hours));
        else if (days < 7) textView.setText(String.format(Locale.US, "%d days ago", days));
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            textView.setText(sdf.format(new java.util.Date(timeMillis)));
        }
    }

    private void setImageViewColor(ImageView imageView, int color) {
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
    
    private void applyRippleStyle(View view, int color, int rippleColor, float radius, int strokeWidth, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(radius);
        shape.setStroke(strokeWidth, strokeColor);
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{rippleColor}), shape, null);
        view.setBackground(rippleDrawable);
    }
    
    private void setRoundedBackground(View view, float radius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.TRANSPARENT);
        gd.setCornerRadius(radius);
        view.setClipToOutline(true);
        view.setBackground(gd);
    }

    private void setSystemBarColors(int statusBarColor, int navigationBarColor) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(statusBarColor);
        getWindow().setNavigationBarColor(navigationBarColor);
    }

    // Inner Class for Inbox RecyclerView Adapter
    public class InboxRecyclerViewAdapter extends RecyclerView.Adapter<InboxRecyclerViewAdapter.ViewHolder> {
        private final ArrayList<HashMap<String, Object>> data;

        public InboxRecyclerViewAdapter(ArrayList<HashMap<String, Object>> data) {
            this.data = data;
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
            LinearLayout mainLayout, userStatusCircleBG;
            ImageView profileImage, genderBadge, verifiedBadge, messageStateIcon;
            TextView usernameTextView, lastMessageTextView, pushTimeTextView, unreadCountBadge;

            ViewHolder(View view) {
                super(view);
                mainLayout = view.findViewById(R.id.main);
                profileImage = view.findViewById(R.id.profileCardImage);
                userStatusCircleBG = view.findViewById(R.id.userStatusCircleBG);
                usernameTextView = view.findViewById(R.id.username);
                genderBadge = view.findViewById(R.id.genderBadge);
                verifiedBadge = view.findViewById(R.id.verifiedBadge);
                lastMessageTextView = view.findViewById(R.id.last_message);
                messageStateIcon = view.findViewById(R.id.message_state);
                pushTimeTextView = view.findViewById(R.id.push);
                unreadCountBadge = view.findViewById(R.id.unread_messages_count_badge);
            }

            void bind(final HashMap<String, Object> item) {
                mainLayout.setVisibility(View.GONE);
                
                String otherUid = item.get("uid").toString();
                String lastMessage = item.getOrDefault("last_message_text", "null").toString();
                if ("null".equals(lastMessage)) {
                    lastMessageTextView.setText(getString(R.string.m_no_chats));
                } else {
                    lastMessageTextView.setText(lastMessage);
                }

                if (item.get("last_message_uid").toString().equals(auth.getCurrentUser().getUid())) {
                    messageStateIcon.setVisibility(View.VISIBLE);
                    unreadCountBadge.setVisibility(View.GONE);
                    lastMessageTextView.setTypeface(lastMessageTextView.getTypeface(), Typeface.NORMAL);
                    String state = item.getOrDefault("last_message_state", "").toString();
                    messageStateIcon.setImageResource("sended".equals(state) ? R.drawable.icon_done_round : R.drawable.icon_done_all_round);
                } else {
                    messageStateIcon.setVisibility(View.GONE);
                    // This logic should be updated if there are unread messages
                    // For now, setting to BOLD if last message is not from user
                    lastMessageTextView.setTypeface(lastMessageTextView.getTypeface(), Typeface.BOLD);
                }

                setTimeAgo(Double.parseDouble(item.get("push_date").toString()), pushTimeTextView);

                loadUserInfo(otherUid, item);

                mainLayout.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra("uid", otherUid);
                    startActivity(intent);
                });

                mainLayout.setOnLongClickListener(v -> {
                    showChatOptionsBottomSheet(item);
                    return true;
                });
            }

            private void loadUserInfo(String uid, HashMap<String, Object> inboxItem) {
                // Simplified display logic, full caching would be better
                mainLayout.setVisibility(View.VISIBLE);
                
                // This part should ideally use a proper caching mechanism
                DatabaseReference userRef = firebaseDB.getReference("skyline/users").child(uid);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String nickname = snapshot.child("nickname").getValue(String.class);
                            String username = snapshot.child("username").getValue(String.class);
                            usernameTextView.setText(!"null".equals(nickname) ? nickname : "@" + username);

                            // Load avatar using Glide or Picasso here
                            // String avatarUrl = snapshot.child("avatar").getValue(String.class);
                            // if (avatarUrl != null && !avatarUrl.equals("null")) { ... }
                        }
                    }
                    @Override public void onCancelled(@NonNull DatabaseError error) { }
                });
            }

            private void showChatOptionsBottomSheet(HashMap<String, Object> item) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog(MessagesActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.inbox_bs, null);
                bottomSheet.setContentView(sheetView);
                
                TextView deleteButton = sheetView.findViewById(R.id.delete_btn);
                deleteButton.setOnClickListener(v -> {
                    bottomSheet.dismiss();
                    showDeleteConfirmationDialog(item);
                });

                // Set up other buttons like archive, mute, etc. here
                bottomSheet.show();
            }

            private void showDeleteConfirmationDialog(HashMap<String, Object> item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MessagesActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.delete_chat_c_dialog, null);
                builder.setView(dialogView);
                AlertDialog deleteDialog = builder.create();
                
                final CheckBox checkbox = dialogView.findViewById(R.id.checkbox1);
                LinearLayout deleteBtn = dialogView.findViewById(R.id.delete_btn);

                deleteBtn.setOnClickListener(v -> {
                    String otherUid = item.get("uid").toString();
                    String myUid = auth.getCurrentUser().getUid();

                    // Delete for me
                    firebaseDB.getReference("skyline/inbox").child(myUid).child(otherUid).removeValue();
                    
                    if (checkbox.isChecked()) {
                        // Also delete for other person
                        firebaseDB.getReference("skyline/inbox").child(otherUid).child(myUid).removeValue();
                        // Deleting actual chat messages
                        firebaseDB.getReference("skyline/chats").child(myUid).child(otherUid).removeValue();
                        firebaseDB.getReference("skyline/chats").child(otherUid).child(myUid).removeValue();
                    }
                    deleteDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Conversation deleted", Toast.LENGTH_SHORT).show();
                });

                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                deleteDialog.show();
            }
        }
    }

    // Inner Class for Groups RecyclerView Adapter
    public class Groups_name_listAdapter extends RecyclerView.Adapter<Groups_name_listAdapter.ViewHolder> {
        private final ArrayList<HashMap<String, Object>> data;

        public Groups_name_listAdapter(ArrayList<HashMap<String, Object>> data) {
            this.data = data;
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
                groupNameTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/product_b.ttf"), Typeface.BOLD);
                groupNameTextView.setText(item.getOrDefault("group_name", "Unnamed Group").toString());
                lastMessageTextView.setText(item.getOrDefault("created_on", "").toString());
                setRoundedBackground(profileImage, 360);
                
                // Load group image with Glide here
                
                bodyLayout.setOnClickListener(v -> {
                    String password = item.getOrDefault("group_password", "null").toString();
                    String groupKey = item.getOrDefault("group_key", "").toString();
                    
                    if (groupKey.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Group key is missing.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
                    if ("null".equals(password)) {
                        openGroupChat(item);
                    } else {
                        promptForPassword(item, password);
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

            private void promptForPassword(HashMap<String, Object> item, String correctPassword) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MessagesActivity.this);
                builder.setTitle("Enter Group Password");
                builder.setMessage("This is a private group.");
                
                final EditText passwordInput = new EditText(MessagesActivity.this);
                passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(passwordInput);

                builder.setPositiveButton("Enter", (dialog, which) -> {
                    if (correctPassword.equals(passwordInput.getText().toString())) {
                        openGroupChat(item);
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect password.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            }
        }
    }
}