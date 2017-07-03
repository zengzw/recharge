package com.tsh.broker.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Bean转换器
 * <li>@author Leejean
 * <li>@create 2014-6-24 上午11:48:40
 * <li>本转换器支持自定义日期转换 
 * <li>使用方法直接在成员变量加自定义注解即可
 * <li>例如:
 * <li>@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
 * <li>private String modifyDatetime;
 * <li>后期可拓展数字精度
 * <li>@NumberFmtAnnotation(scale=1)
 * <li>private Double num;
 */
public class BeanConvertor { 
	/**
	 * 将source中的同名属性的值复制到desc的同名属性中
	 * @author Leejean
	 * @param source 源
	 * @param target 目标
	 */
	public static void copyProperties(Object source, Object target) throws Exception{
		if(source == null) return;		
		handle(source, target);		
	}
	/**
	 * 将source中的同名属性赋值到另外一个指定类型的对象中,并返回,
	 * @author Leejean
	 * @create 2014-7-8下午06:45:01
	 * @param <T> 目标类型
	 * @param source 员
	 * @param clazz 目标类.class
	 * @return 目标类型对象
	 * @throws Exception 转换异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyProperties(Object source,Class<T> clazz) throws Exception{
		if(source == null) return null;		
		Object target=clazz.newInstance();
		return (T) handle(source, target);
	}
	/**
	 * 针对集合的操作
	 * @author Leejean
	 * @param srcList 源集合
	 * @param clazz 目标类型
	 * @return 目标集合
	 * @throws Exception  转换异常
	 */
	@SuppressWarnings("rawtypes")
	public static <T> List<T> copyList(List srcList, Class<T> clazz) throws Exception{
		List<T> descList = new ArrayList<T>();
		if(srcList == null) return descList;
		for(Object o : srcList){
			T t = copyProperties(o, clazz);
			descList.add(t);
		}		
		return descList;
	}
	/**
	 * 转换处理
	 * @author Leejean
	 * @create 2014-7-8下午06:44:10
	 * @param source 源
	 * @param target 目标
	 * @return 处理结果
	 * @throws Exception 转换异常
	 */
	private static Object handle(Object source,Object target) throws Exception{
		Field[] sourceFields=source.getClass().getDeclaredFields();
		Class targetClass=target.getClass();
		for (Field field : sourceFields) {
			field.setAccessible(true);
			Object object=field.get(source);
			if(object!=null){
				Field targetFiled=null;
				try {
					targetFiled = targetClass.getDeclaredField(field.getName());
				} catch (Exception e) {
					continue;
				}			
				targetFiled.setAccessible(true);
				if(targetFiled!=null){//属性存在
					boolean isDateAnnotationPresent=targetFiled.isAnnotationPresent(DateFmtAnnotation.class);
					if(isDateAnnotationPresent){
						//被日期注解修饰
						//得到注解内容
						String pattern=targetFiled.getAnnotation(DateFmtAnnotation.class).fmtPattern();
						//获得目标对象属性类型,以便将数据源赋值
						Class targetFiledType=targetFiled.getType();
						Class sourceFiledType=field.getType();
						if(targetFiledType==Date.class&&sourceFiledType==String.class){
							
							object=DateUtil.formatStringToDate(object.toString(), pattern);
							targetFiled.set(target,object);
							
						}else if(targetFiledType==String.class&&sourceFiledType==Date.class){
							
							object=DateUtil.formatDateToString((Date)object, pattern);
							targetFiled.set(target,object);
						}
					}else{
						boolean isNumberAnnotationPresent=targetFiled.isAnnotationPresent(NumberFmtAnnotation.class);
						if(isNumberAnnotationPresent){//被数字格式化注解修饰
							//得到注解内容
							int scale=targetFiled.getAnnotation(NumberFmtAnnotation.class).scale();
							Class targetFiledType=targetFiled.getType();
							Class sourceFiledType=field.getType();
							if(targetFiledType==Double.class&&sourceFiledType==Double.class){
								object=MathUtil.roundHalfUp((Double)object, scale);
								targetFiled.set(target,object);
							}else{
								targetFiled.set(target,object);
							}
						}else{
							//没有被注解的直接赋值							
							targetFiled.set(target,object);
						}
					}
				}
				
			}
		}
		return target;
	}

	public static void main(String[] args)  {
		
		/*SysUser source=new SysUser(1L, DateUtil.formatStringToDate("2012-01-01 11:11:11"),DateUtil.formatStringToDate("2012-01-01 11:11:11"), "111", "111"); 
		UserBean target= new UserBean(2L, "2022-02-21 22:22:22", "2022-02-21 22:22:22", "222", "222");*/
		/*SysUser source=new SysUser(); 
		source.setA(1.2813);
		UserBean target= new UserBean();
		target.setA(4.223298798776);
		List<UserBean> userlist=new ArrayList<UserBean>();
		userlist.add(target);
		try {
			System.out.println(BeanConvertor.copyProperties(target, SysUser.class).toString());
			for (SysUser sysUser : BeanConvertor.copyList(userlist, SysUser.class)) {
				//System.out.println(sysUser.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*List<SysUser> users=new ArrayList<SysUser>();
		users.add(new SysUser(new Date(), new Date(), "yyy", "11"));
		users.add(new SysUser(new Date(), new Date(), "xxx", "22"));
		List<UserBean> beanusers=BeanConvertor.copyList(users, UserBean.class);
		for (UserBean userBean : beanusers) {
			System.out.println(userBean.toString());
		}*/
		
	}


}
