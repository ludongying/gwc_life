package com.seven.gwc.modular.lawrecord.data.export;

import com.seven.gwc.modular.lawrecord.data.export.anno.FieldName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zzl
 * @Description 文件导出Excel方法
 *              支持继承一级父类
 *              dto表格类查看 @FieldName
 * ex：{@Code
 *    new ExcelData<xxxDTO>(){}.exportExcel();
 *    xxxDTO对象必须使用注解 @FieldName
 *
 *  }
 */
@Slf4j
public abstract class ExcelData<T>{
    private String name;

    private List<String> titles;

    private List<CellData> titleDatas;

    private List<T> rowDatas;

    private List<List<Object>> rows;

    public void exportExcel(){
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = servletRequestAttributes.getResponse();
            ExportExcel.exportExcel(response,this.name,this);
        } catch (Exception e) {
            log.error("生成excel 失败");
        }
    }

    public ExcelData(List<T> dtos){
        this.titleDatas=new ArrayList<>();
        this.rowDatas=dtos;
        Class<?> clazz=getTClass();
        FieldName annotation = clazz.getAnnotation(FieldName.class);
        this.name=annotation.value();
        Class<?> superClass = clazz.getSuperclass();
        addTile(superClass,clazz.getSimpleName());
        addTile(clazz,null);
        initData();
    }

    private void initData(){
        this.titles=titleDatas.stream().map(CellData::getValue).collect(Collectors.toList());
        this.rows=new ArrayList<>();
        if(Objects.nonNull(rowDatas) && !rowDatas.isEmpty()){
            for (T t : rowDatas) {
                rows.add(parseToObj(t));
            }
        }

    }

    private  String upperFirstLatter(String letter) {
        char[] chars = letter.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        return new String(chars);
    }

    private List<Object> parseToObj(T t){
        List<Object> row=new ArrayList<>();
        Class<?> clazz = t.getClass();
        for (CellData titleData : titleDatas) {
            try {
                Method method = clazz.getMethod("get" +upperFirstLatter(titleData.getName()));
                Object object = method.invoke(t);
                row.add(object);
            } catch (Exception e) {
                log.error(clazz.getSimpleName()+"中没有get" + upperFirstLatter(titleData.getName())+"方法。。");
            }
        }
        return row;
    }

    private void addTile(Class<?> clazz,@Nullable String type){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            FieldName annotation = field.getAnnotation(FieldName.class);
            if(Objects.nonNull(annotation)){
                if(annotation.exist()){
                    String flag = annotation.flag();
                    if(Objects.nonNull(flag) && !flag.isEmpty()){
                        if(flag.equals(type)){
                            titleDatas.add(new CellData(field.getName(),annotation.value()));
                        }
                    }else{
                        titleDatas.add(new CellData(field.getName(),annotation.value()));
                    }
                }
            }
        }
    }

    private Class<?> getTClass() {
        Class<?> tClass = (Class<?>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    public String getName() {
        return name;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

}


