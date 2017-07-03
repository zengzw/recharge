package com.tsh.traintickets;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Administrator on 2016/11/25 025.
 */
public class CreateJson {

    public static void main(String args[]) throws Exception {
        Map<String, String> level0Map = new LinkedHashMap();
        Map<String, String> level1Map = new LinkedHashMap();
        Map<String, String> level2Map = new LinkedHashMap();


        List<ProvinceBean> provinceBeanList = new ArrayList();//打印信息

        Map<String ,ProvinceBean> provinceBeanMap = new HashMap();
        Map<String ,CityBean> cityBeanMap = new HashMap();



        File cityFile = new File("d://hcp_city.txt");
        BufferedReader br = new BufferedReader(new FileReader(cityFile));
        String line;

        while (StringUtils.isNotEmpty(line = br.readLine())){
            String params[] = line.split("\t");
            String id = filer(params[0]);
            String parentId = filer(params[1]);
            String level = filer(params[2]);
            String name = filer(params[4]);

            if("0".equals(level)){
                level0Map.put(id, name);

                ProvinceBean provinceBean = new ProvinceBean();
                provinceBean.setName(name);
                provinceBean.setCity(new ArrayList());

                provinceBeanList.add(provinceBean);
                provinceBeanMap.put(name, provinceBean);
            }
            if("1".equals(level)){
                String shengName = level0Map.get(parentId);
                level1Map.put(id, parentId);
                level2Map.put(id, name);

                CityBean cityBean = new CityBean();
                cityBean.setName(name);

                List<CityBean> cityBeanList = provinceBeanMap.get(shengName).getCity();
                if(null == cityBeanList){
                    cityBeanList = new ArrayList();
                }
                cityBeanList.add(cityBean);

                cityBeanMap.put(shengName+name, cityBean);

            }

            if("2".equals(level)){
                String shengId = level1Map.get(parentId);
                String shengName = level0Map.get(shengId);
                String shiName = level2Map.get(parentId);

                DistrictBean districtBean = new DistrictBean();
                districtBean.setName(name);

                List<DistrictBean> districtBeanList = cityBeanMap.get(shengName+shiName).getDistrict();
                if(null == districtBeanList){
                    districtBeanList = new ArrayList();
                    cityBeanMap.get(shengName+shiName).setDistrict(districtBeanList);
                }
                districtBeanList.add(districtBean);
            }

        }
        br.close();

    }

    private static String filer(String value){
        return StringEscapeUtils.unescapeJava(value.replaceAll("\"", "").replaceAll("\uFEFF", "").replaceAll(" ", ""));
    }
}
