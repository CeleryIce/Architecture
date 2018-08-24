package cc.ylike.architecture;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cc.ylike.architecture.bean.ChinaCityData;
import cc.ylike.corelibrary.utils.L;
import cc.ylike.corelibrary.utils.ToolsUtils;

/**
 * 中华人民共和国行政区划代码 (8区)
 * json生成
 */
public class CityJsonFileMakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_json_file_make);
        init();
    }

    /**
     * json 文件生成
     */
    private void init(){
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFromAssets(CityJsonFileMakeActivity.this,"行政规划代码.txt");
            }
        });
//        String txtString = getFromAssets(this,"行政规划代码.txt");
    }

    /**
     * 读取assets文件内容
     *
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            List<ChinaCityData>  provinceList = new ArrayList<>();
            List<ChinaCityData>  cityList = new ArrayList<>();
            List<ChinaCityData>  countyList = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                if (!TextUtils.isEmpty(line)) {
                    String Str = line.split("\\s+")[0];
                    //省、市、区（县）区分
                    if (Str.endsWith("0000")) {//省
                        ChinaCityData cityData = new ChinaCityData();
                        cityData.setCode(Str);
                        cityData.setName(line.split("\\s+")[1]);
                        provinceList.add(cityData);
                    } else if (Str.endsWith("00")) {//市
                        ChinaCityData cityData = new ChinaCityData();
                        cityData.setCode(Str);
                        cityData.setName(line.split("\\s+")[1]);
                        cityList.add(cityData);
                    } else {//县、区
                        ChinaCityData cityData = new ChinaCityData();
                        cityData.setCode(Str);
                        cityData.setName(line.split("\\s+")[1]);
                        countyList.add(cityData);
                    }
                }
                Result += line;
            }

            //将数据组合到一起
            List<ChinaCityData>  cityDataList = new ArrayList<>();
            for (int i=0;i<provinceList.size();i++){
                ChinaCityData cityData = new ChinaCityData();
                cityData.setCode(provinceList.get(i).getCode());
                cityData.setName(provinceList.get(i).getName());
                //获取市
                String CityCode = provinceList.get(i).getCode().substring(0,provinceList.get(i).getCode().length()-4);
                List<ChinaCityData.CityListBeanX> cityListBeanXList = new ArrayList<>();
                //四个直辖市处理（包含北京、天津、上海、重庆）
                if (provinceList.get(i).getCode().equals("110000") || provinceList.get(i).getCode().equals("120000")
                        || provinceList.get(i).getCode().equals("310000")|| provinceList.get(i).getCode().equals("500000")){
                    ChinaCityData.CityListBeanX cityListBeanX = new ChinaCityData.CityListBeanX();
                    cityListBeanX.setCode(provinceList.get(i).getCode());
                    cityListBeanX.setName(provinceList.get(i).getName());
                    //获取区
                    List<ChinaCityData.CityListBeanX.CityListBean> cityListBeanList = new ArrayList<>();
                    for (int j = 0;j < countyList.size();j++){
                        if (countyList.get(j).getCode().startsWith(CityCode)){
                            ChinaCityData.CityListBeanX.CityListBean cityListBean = new ChinaCityData.CityListBeanX.CityListBean();
                            cityListBean.setCode(countyList.get(j).getCode());
                            cityListBean.setName(countyList.get(j).getName());
                            cityListBeanList.add(cityListBean);
                        }
                    }
                    cityListBeanX.setCityList(cityListBeanList);
                    cityListBeanXList.add(cityListBeanX);
                }

                //正常的市
                for (int k=0;k<cityList.size();k++) {
                    if (cityList.get(k).getCode().startsWith(CityCode)) {
                        ChinaCityData.CityListBeanX cityListBeanX = new ChinaCityData.CityListBeanX();
                        cityListBeanX.setCode(cityList.get(k).getCode());
                        cityListBeanX.setName(cityList.get(k).getName());
                        //获取区、县
                        String CountryCode = cityList.get(k).getCode().substring(0,cityList.get(k).getCode().length()-2);
                        List<ChinaCityData.CityListBeanX.CityListBean> cityListBeanList = new ArrayList<>();
                        for (int j = 0;j < countyList.size();j++){
                            if (countyList.get(j).getCode().startsWith(CountryCode)){
                                ChinaCityData.CityListBeanX.CityListBean cityListBean = new ChinaCityData.CityListBeanX.CityListBean();
                                cityListBean.setCode(countyList.get(j).getCode());
                                cityListBean.setName(countyList.get(j).getName());
                                cityListBeanList.add(cityListBean);
                            }
                        }
                        cityListBeanX.setCityList(cityListBeanList);
                        cityListBeanXList.add(cityListBeanX);
                    }
                }
                cityData.setCityList(cityListBeanXList);
                cityDataList.add(cityData);
            }
            //将bean转化成Json字符串并保存到本地json文件中
            String jsonStr = new Gson().toJson(cityDataList);
            L.json(jsonStr);
            InputStream in_withcode = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));

            ToolsUtils.savaFile(in_withcode,"ACelery","china_city_data.json");

            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
