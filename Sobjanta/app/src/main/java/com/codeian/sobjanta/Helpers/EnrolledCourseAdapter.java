package com.codeian.sobjanta.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeian.sobjanta.Models.EnrolledDataModel;
import com.codeian.sobjanta.R;

import java.util.ArrayList;

public class EnrolledCourseAdapter extends ArrayAdapter {

    private ArrayList dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        ImageView removeCourse;
    }

    public EnrolledCourseAdapter(ArrayList data, Context context) {
        super(context, R.layout.layout_list_item_my_course, data);
        this.dataSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public EnrolledDataModel getItem(int position) {
        return (EnrolledDataModel) dataSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_my_course, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.removeCourse = (ImageView) convertView.findViewById(R.id.removeCourse);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        EnrolledDataModel item = getItem(position);

        viewHolder.txtName.setText(item.name);

        return result;
    }
}