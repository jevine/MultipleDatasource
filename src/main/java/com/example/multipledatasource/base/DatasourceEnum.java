package com.example.multipledatasource.base;


/**
 * @author : JJZheng
 * @since 2022/7/20 19:32
 */
public enum DatasourceEnum{
    FIRST_DATASOURCE("dataSourceFirst","第一个数据源"),
    SECOND_DATASOURCE("dataSourceSecond","第二个数据源");
    private String desc;
    private String value;
    DatasourceEnum(String value,String desc){
        this.desc=desc;
        this.value=value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public static DatasourceEnum valueOfByValueParams(String value){
        for (DatasourceEnum datasourceEnum:DatasourceEnum.values()) {
            if (datasourceEnum.getValue().equals(value)){
                return datasourceEnum;
            }
        }
        return null;
    }

}
