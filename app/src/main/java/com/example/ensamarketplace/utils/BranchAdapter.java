package com.example.ensamarketplace.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ensamarketplace.R;

import java.util.List;


public class BranchAdapter extends BaseAdapter {

        private Context context;
        private List<Branch> branchList;

        public BranchAdapter(Context context, List<Branch> branchList) {
            this.context = context;
            this.branchList = branchList;
        }

        @Override
        public int getCount() {
            return branchList != null ? branchList.size() : 0;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            System.out.println(branchList);
            View rootView = LayoutInflater.from(context)
                    .inflate(R.layout.one_branch_layout, viewGroup, false);

            TextView txtName = rootView.findViewById(R.id.name);
            ImageView image = rootView.findViewById(R.id.image);

            txtName.setText(branchList.get(i).getName());
            image.setImageResource(branchList.get(i).getImage());

            return rootView;
        }
}
