package com.tsh.broker.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * 数据计算工具类
 * <li>@author
 * <li>@create 2014-6-24 下午04:13:31
 */
public class MathUtil{
   private final static Double MAX_DOUBLE_VALUE = 999999999999.99;
   private final static Double MIN_DOUBLE_VALUE = -999999999999.99;
   private final static Random random = new Random();
   /**
    * 四舍五入
    * @param value
    * @param scale 保留小数点位数
    * @return
    */
   public static Double roundHalfUp(Double value, int scale)
   {
       try {
           BigDecimal bigDecimal = new BigDecimal(value);
           double resultValue = bigDecimal.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
           return resultValue;
       } catch (Exception e) {
           e.printStackTrace();
       }
       return 0D;
   }
   /**
    * 求和
    * @param values
    * @return
    */
   public static Double sumValue(List<Double> values)
   {
       if (values == null || values.size() == 0){
           return 0.0d;
       }
       Double sumValue = 0.0d;
       for (Double value : values)
       {
           sumValue += value;
       }
       return sumValue;
   }
   /**
    * 求均值.并保存到小数点后面几位
    * @param values
    * @return
    */
   public static Double avgValue(List<Double> values)
   {
       if (values == null || values.size() == 0)
           return 0.0d;
       // 1. 求和
       Double sumValue = sumValue(values);
       // 2. 计算并四舍五入
       return sumValue / values.size();
   }
   /**
    * 计算最大值
    * @param initDoubleArray
    * @return
    */
   public static Double maxValue(List<Double> initDoubleArray)
   {
       Double max = MIN_DOUBLE_VALUE;
       for (Double cur : initDoubleArray) {
           if (cur > max)
               max = cur;
       }
       return max;
   }
   /**
    * 计算最小值
    * @param initDoubleArray
    * @return
    */
   public static Double minValue(List<Double> initDoubleArray)
   {
       Double min = MAX_DOUBLE_VALUE;
       for (Double cur : initDoubleArray) {
           if (cur < min)
               min = cur;
       }
       return min;
   }
   /**
    * 计算方差
    * @param initDoubleArray
    * @return
    */
   public static Double svValue(List<Double> initDoubleArray)
   {
       double sv = 0.0;
       if (initDoubleArray.size() < 1) return 0.0;
       double avg = avgValue(initDoubleArray);
       for (int i=0;i<initDoubleArray.size();i++)
       {
           sv += (avg - initDoubleArray.get(i)) * (avg - initDoubleArray.get(i));
       }
       if (sv > 0)
       {
           if(initDoubleArray.size() - 1!=0)
           {
               return sv/(initDoubleArray.size() - 1);
           }
           else
           {
               return 0.0;
           }
       }

       return 0.0;
   }
   public static void main(String[] args) {
       //四舍五入
       //System.out.println(MathUtil.roundHalfUp(1231.4454232, 2));

       /*List<Double> nums=new ArrayList<Double>();
       nums.add(1.2);
       nums.add(1.6);
       nums.add(3.2);
       nums.add(4.21111);
       //求和
*/		//System.out.println(MathUtil.sumValue(nums));
       //求平均值
       //System.out.println(MathUtil.avgValue(nums));
       //System.out.println(MathUtil.maxValue(nums));
       //System.out.println(MathUtil.minValue(nums));
       /*System.out.println(MathUtil.getRandomInt(1000,9999));
       System.out.println(MathUtil.getRandomDouble(1000,9999));*/
       System.out.println(MathUtil.getRandomDouble(5,6,3));
   }
   /**
    * 获得指定范围随机整数
    * @author Leejean
    * @create 2014年12月19日上午11:46:12
    * @param min 最小值
    * @param max 最大值
    * @return
    */
   public static Integer getRandomInt(Integer min,Integer max){
       return random.nextInt(max)%(max-min+1) + min;
   }
   /**
    * 获得指定范围随机小数
    * @author Leejean
    * @create 2014年12月19日上午11:46:12
    * @param min 最小值
    * @param max 最大值
    * @param scale 精度
    * @return
    */
   public static Double getRandomDouble(Integer min,Integer max,Integer scale){
       return MathUtil.getRandomInt(min,max)+MathUtil.roundHalfUp(random.nextDouble(), scale);
   }


}
