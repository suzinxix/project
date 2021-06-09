package com.example.studyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PhotoStatActivity extends AppCompatActivity {
    HomeFragment fragment_home;
    SearchFragment fragment_studysearch;
    PersonalPageFragment fragment_personal;
    private PieChart pieChart;
    TextView total_date;
    TextView time_stat;
    TextView yoil_stat;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("gallery_url");

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저 정보 가져오기
    String uid = user != null ? user.getUid() : null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_stat);
        pieChart = (PieChart) findViewById(R.id.piechart);
        total_date = (TextView) findViewById(R.id.total_date);
        time_stat = (TextView) findViewById(R.id.time_stat);
        yoil_stat = (TextView) findViewById(R.id.yoil_stat);
        setupPieChart();
        retrieveData();
    }

    private void retrieveData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<PieEntry> entries = new ArrayList<>();
                String[] week = {"일","월", "화", "수","목","금","토"};
                int[] weekdata = {0, 0, 0, 0, 0, 0, 0};
                int[] time_zone={0, 0, 0, 0}; //dawn, morning, afternoon, evening

                if (snapshot.hasChildren()){
                    int i=0; // 갯수 세기

                    for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                        GalleryDB dataPoint = myDataSnapshot.getValue(GalleryDB.class);
                        if (dataPoint.getUserId().equals(uid)){
                            i++;
                            String s = dataPoint.getPhotoText();
                            String date = s.substring(0,8);
                            for (int x=0;x<weekdata.length;x++){
                                try{
                                    if (week[x]==getDateDay(date))
                                        weekdata[x]+=1;
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                            int hour = Integer.parseInt(s.substring(9,11));
                            if (hour>=6 && hour<=11) {
                                time_zone[0] += 1;
                            } else if (hour>=12 && hour<=17) {
                                time_zone[1] += 1;
                            } else if (hour>=18 && hour<=23) {
                                time_zone[2] += 1;
                            } else {
                                time_zone[3] += 1;
                            }
                        }
                    }

                    total_date.setText("출석일수: "+i+" 번");
                    for (int y=0;y<7;y++){
                        if (weekdata[y]!=0)
                            entries.add(new PieEntry(weekdata[y], week[y]));
                    }//요오기ㅣ이이ㅣ
                    String yoil="";
                    int max_yoil=0;
                    for (int z=0;z<7;z++){
                        if (weekdata[z]>max_yoil)
                            max_yoil=z;
                    }

                    yoil_stat.setText(Html.fromHtml("<u>"+week[max_yoil]+"요일</u>에 가장 많이 참여하고 있습니다.", Html.FROM_HTML_MODE_LEGACY));
                    loadPieChartData(entries);

                    int maxindex=0;
                    for(int j=0;j<3;j++){
                        if (time_zone[j]<time_zone[j+1])
                            maxindex=j+1;
                    }
                    String time_z="";
                    if(maxindex==0)
                        time_z="오전";
                    else if (maxindex==1)
                        time_z="오후";
                    else if (maxindex==2)
                        time_z="저녁";
                    else if (maxindex==3)
                        time_z="새벽";
                    time_stat.setText(Html.fromHtml("주로 집중하는 시간대는 <u>"+time_z+"</u> 이군요!", Html.FROM_HTML_MODE_LEGACY));
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61F);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(20);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("요일별 빈도");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDragDecelerationFrictionCoef(0.99F);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }
    private String getDateDay(String date) throws Exception {

        String day = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date nDate = dateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayNum) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;

        }

        return day;

    }
    private void loadPieChartData(ArrayList<PieEntry> entries) {
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(18);

        pieChart.setData(data);
        pieChart.invalidate();
    }

}

