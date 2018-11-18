package com.codeian.sobjanta.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codeian.sobjanta.Models.ScheduleDataModel;
import com.codeian.sobjanta.R;

import java.util.ArrayList;

public class ClassScheduleAdapter extends ArrayAdapter {

    private ArrayList dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtCourseCode;
        TextView txtCourseName;
        TextView txtCourseTeacher;
        TextView txtTime;
        TextView txtRoom;
        TextView txtCancel;
        LinearLayout classData;
    }

    public ClassScheduleAdapter(ArrayList data, Context context) {
        super(context, R.layout.layout_list_item_class_schedule, data);
        this.dataSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public ScheduleDataModel getItem(int position) {
        return (ScheduleDataModel) dataSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_class_schedule, parent, false);
            viewHolder.txtCourseCode = convertView.findViewById(R.id.courseId);
            viewHolder.txtCourseName = convertView.findViewById(R.id.courseName);
            viewHolder.txtCourseTeacher = convertView.findViewById(R.id.courseTeacher);
            viewHolder.txtRoom = convertView.findViewById(R.id.classRoom);
            viewHolder.txtTime = convertView.findViewById(R.id.classTime);
            viewHolder.txtCancel = convertView.findViewById(R.id.classCancel);

            viewHolder.classData = convertView.findViewById(R.id.classData);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        ScheduleDataModel item = getItem(position);

        viewHolder.txtCourseCode.setText(item.getCode());
        viewHolder.txtCourseName.setText(item.getName());
        viewHolder.txtCourseTeacher.setText("("+item.getTeacher()+")");

        if(item.isCancelled() == false){
            viewHolder.txtCancel.setVisibility(View.GONE);
            viewHolder.classData.setVisibility(View.VISIBLE);

            viewHolder.txtTime.setText( "Time: " + item.time);
            viewHolder.txtRoom.setText( "Room: " + item.room);
        }else{
            viewHolder.txtCancel.setVisibility(View.VISIBLE);
            viewHolder.classData.setVisibility(View.GONE);
        }

        return result;
    }
}