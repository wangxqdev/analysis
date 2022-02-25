package com.iot.phoebus;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.iot.phoebus.entity.BasePacket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author xinquan.w@phoebus-iot.com
 * @date 2022/2/24
 */
@Slf4j
@SpringBootApplication
public class CoreMain implements CommandLineRunner {

    private final Map<String, List<BasePacket>> cache = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(CoreMain.class, args);
    }

    @Override
    public void run(String... args) {
        List<String> lineList = FileUtil.readUtf8Lines("xaa");
        for (String line : lineList) {
            List<String> groupList = ReUtil.findAllGroup0("\\[[\\:\\-\\w\\s]*\\]", line);
            String second = CollUtil.get(groupList, 1);
            String dateTimeStr = second.substring(1, second.length() - 1);
            Date dateTime = DateUtil.parse(dateTimeStr);
            String dateStr = DateUtil.format(dateTime, DateTimeFormatter.BASIC_ISO_DATE);
            String last = CollUtil.getLast(groupList);
            if (StrUtil.isNotEmpty(last)) {
                try {
                    String data = last.substring(1, last.length() - 1);
                    String id = data.substring(4, 6);
                    BasePacket packet = ReflectUtil.newInstance("com.iot.phoebus.entity.Id" + id + "Packet");
                    packet.decode(data, cache);
                    packet.setDate(dateStr);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        }
        List<List<String>> sheet = new ArrayList<>();
        for (Map.Entry<String, List<BasePacket>> entry : cache.entrySet()) {
            List<BasePacket> packetList = entry.getValue();
            for (BasePacket packet : packetList) {
                sheet.addAll(packet.buildRows());
            }
        }
        String desktop = System.getProperty("user.home") + "/Desktop/test.xlsx";
        try (BigExcelWriter writer = ExcelUtil.getBigWriter(desktop)) {
            writer.write(sheet);
        }
    }
}
