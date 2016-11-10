//package com.example.matthewwatson.peoplemongo.Adapters;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.matthewwatson.peoplemongo.Model.User;
//import com.example.matthewwatson.peoplemongo.R;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
///**
// * Created by Matthew.Watson on 11/9/16.
// */
//
//public class UserListAdapter extends RecyclerView.Adapter <UserListAdapter.UserListHolder> {
//    private Context context;
//    Date date = new Date();
//
//    public ArrayList<User> caughtUsers;
//
//
//
//
//    public UserListAdapter(ArrayList<User> caughtUsers,Context context) {
//        this.caughtUsers = caughtUsers;
//        this.context = context;
//    }
//
//
//    @Override
//    public void onBindViewHolder(UserListHolder holder, int position) {
//        User user = caughtUsers.get(position);
//        holder.bindUser(user);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return caughtUsers == null ? 0 : caughtUsers.size();
//    }
//
//    @Override
//    public UserListHolder onCreateViewHolder(ViewGroup parent, int viewType) {  //ctrl-i
//        View inflatedView = LayoutInflater.from(context)
//                .inflate(R.layout.caught_list_item, parent, false);
//        return new UserListHolder(inflatedView);
//    }
//
//
//
//    class UserListHolder extends RecyclerView.ViewHolder { //populates view
//
//        @Bind(R.id.user_name_field)
//        TextView nameField;
//
//
//        public UserListHolder(View itemView) { //ctrl-o
//            super(itemView);
//
//            ButterKnife.bind(this, itemView);
//        }
//
//        public void bindUser(User user) {
//            nameField.setText(user.getCaughtUserId());
//        }
//    }
//
//}