package com.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.encrypt.api.EncryptColumnRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.EncryptRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.EncryptTableRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.EncryptorRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.EncryptDataSourceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ShardingSphere {

    @Bean("tradePlatformDataSource")
    public DataSource dataSource(@Qualifier("druidDataSource") DataSource ds) throws SQLException {
        return EncryptDataSourceFactory.createDataSource(ds, getEncryptRuleConfiguration(), new Properties());
    }

    private EncryptRuleConfiguration getEncryptRuleConfiguration() {
        Properties props = new Properties();

        //自带aes算法需要
        props.setProperty("aes.key.value", "123456");
        EncryptorRuleConfiguration encryptorConfig = new EncryptorRuleConfiguration("AES", props);

        //自定义算法
        //props.setProperty("qb.finance.aes.key.value", aeskey);
        //EncryptorRuleConfiguration encryptorConfig = new EncryptorRuleConfiguration("QB-FINANCE-AES", props);

        EncryptRuleConfiguration encryptRuleConfig = new EncryptRuleConfiguration();
        encryptRuleConfig.getEncryptors().put("aes", encryptorConfig);

        //START: card_info 表的脱敏配置
        {
            EncryptColumnRuleConfiguration columnConfig1 = new EncryptColumnRuleConfiguration("", "name", "", "aes");
            EncryptColumnRuleConfiguration columnConfig2 = new EncryptColumnRuleConfiguration("", "id_no", "", "aes");
            EncryptColumnRuleConfiguration columnConfig3 = new EncryptColumnRuleConfiguration("", "finshell_card_no", "", "aes");
            Map<String, EncryptColumnRuleConfiguration> columnConfigMaps = new HashMap<>();
            columnConfigMaps.put("name", columnConfig1);
            columnConfigMaps.put("id_no", columnConfig2);
            columnConfigMaps.put("finshell_card_no", columnConfig3);
            EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration(columnConfigMaps);
            encryptRuleConfig.getTables().put("card_info", tableConfig);
        }
        //END: card_info 表的脱敏配置

        //START: pay_order 表的脱敏配置
        {
            EncryptColumnRuleConfiguration columnConfig1 = new EncryptColumnRuleConfiguration("", "card_no", "", "aes");
            Map<String, EncryptColumnRuleConfiguration> columnConfigMaps = new HashMap<>();
            columnConfigMaps.put("card_no", columnConfig1);
            EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration(columnConfigMaps);
            encryptRuleConfig.getTables().put("pay_order", tableConfig);
        }

        log.info("脱敏配置构建完成:{} ", encryptRuleConfig);
        return encryptRuleConfig;
    }

}
