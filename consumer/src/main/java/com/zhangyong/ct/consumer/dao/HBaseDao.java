package com.zhangyong.ct.consumer.dao;

import com.zhangyong.ct.common.bean.BaseDao;
import com.zhangyong.ct.common.constant.Names;
import com.zhangyong.ct.common.constant.ValueConstant;
import org.apache.hadoop.hbase.master.procedure.CreateNamespaceProcedure;
import org.apache.hadoop.hbase.master.procedure.CreateTableProcedure;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-25 14:56
 * @PS: HBase数据访问对象
 */
public class HBaseDao extends BaseDao {
    /**
     * 初始化
     */
    public void init() throws Exception {

        start();

        //NX:表示没有创建
        createNamespaceNX(Names.NAMESPACE.getValue());
        createTableXX(Names.TABLE.getValue(), ValueConstant.REGION_COUNT, Names.CF_CALLER.getValue());

        end();

    }


    /**
     * 插入数据
     *
     * @param value
     */
    public void insertData(String value) {
        //将通话
    }
}
