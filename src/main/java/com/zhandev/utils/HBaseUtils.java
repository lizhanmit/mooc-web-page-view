package com.zhandev.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HBase operation utils
 * It is recommanded using singleton for Java utlis.
 */
public class HBaseUtils {

    HBaseAdmin admin = null;
    Configuration configuration = null;

    private static HBaseUtils hBaseUtilsObj;

    // private constructor, singleton
    private HBaseUtils() {
        configuration = new Configuration();
        // these two values can be accessed from "/usr/local/hbase/conf/habse-site.xml"
        configuration.set("hbase.zookeeper.quorum", "localhost:2181");
        configuration.set("hbase.rootdir", "hdfs://localhost:9000/hbase");

        try {
            admin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lazy
     */
    public static synchronized HBaseUtils getInstance() {
        if (hBaseUtilsObj == null) {
            hBaseUtilsObj = new HBaseUtils();
        }
        return hBaseUtilsObj;
    }

    /**
     * Get HTable obj by table name.
     *
     * @param tableName  Table name.
     * @return  HTable obj.
     */
    public HTable getTable(String tableName) {
        HTable table = null;
        try {
            table = new HTable(configuration, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * Insert a record of data into HBase.
     *
     * @param tableName
     * @param rowkey
     * @param columnFamilyName
     * @param columnName
     * @param value
     */
    public void put(String tableName, String rowkey, String columnFamilyName, String columnName, String value) {
        HTable table = HBaseUtils.getInstance().getTable(tableName);
        Put put = new Put(Bytes.toBytes(rowkey));
        put.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(columnName), Bytes.toBytes(value));
        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get rowkey and click count of all class type courses in HBase by date value.
     * @param tableName
     * @param date  Date value. E.g. 20180805.
     * @return  (rowkey, clickCount) pairs.
     */
    public Map<String, Long> getClickCount(String tableName, String date) throws IOException {
        Map<String, Long> map = new HashMap<>();

        HTable table = getTable(tableName);
        String columnFamily = "info";
        String qulifier = "click_count";

        Filter filter = new PrefixFilter(Bytes.toBytes(date));

        Scan scan = new Scan();
        scan.setFilter(filter);

        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result : resultScanner) {
            String rowkey = Bytes.toString(result.getRow());
            long clickCount = Bytes.toLong(result.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(qulifier)));
            map.put(rowkey, clickCount);
        }

        return map;
    }


    /**
     * For testing functions.
     */
    public static void main(String[] args) throws IOException {
        //HTable table = HBaseUtils.getInstance().getTable("mooc_course_clickcount");
        //System.out.println(table.getName().getNameAsString());

        //HBaseUtils.getInstance().put("mooc_course_clickcount", "20180803_123", "info", "click_count", "10");

        //HBaseUtils.getInstance().put("mooc_course_search_clickcount", "20180803_www.google.com_123", "info", "click_count", "10");

        Map<String, Long> map = HBaseUtils.getInstance().getClickCount("mooc_course_clickcount", "20180805");
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


}
