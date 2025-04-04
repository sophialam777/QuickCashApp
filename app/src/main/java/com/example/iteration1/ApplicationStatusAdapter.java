package com.example.iteration1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ApplicationStatusAdapter extends ArrayAdapter<JobApplication> {

    public ApplicationStatusAdapter(Context context, List<JobApplication> applications) {
        super(context, 0, applications);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JobApplication application = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_application_status, parent, false);
        }
        TextView tvJobTitle = convertView.findViewById(R.id.tvJobTitle);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        tvJobTitle.setText(application.getJobTitle());
        tvStatus.setText(application.getStatus());
        return convertView;
    }
}
