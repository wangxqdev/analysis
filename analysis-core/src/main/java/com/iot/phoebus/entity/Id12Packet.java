package com.iot.phoebus.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xinquan.w@phoebus-iot.com
 * @date 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Id12Packet extends BasePacket {

    private static final long serialVersionUID = -2395910279408891261L;

    private final List<Id12PacketData> dataList = new ArrayList<>();

    @Override
    protected List<String> decodeBody(String body) {
        int dataCnt = body.length() / 117;
        for (int i = 0; i < dataCnt; i++) {
            Id12PacketData data = new Id12PacketData();
            data.mcKey = body.substring(i * 117, i * 117 + 8);
            data.carryDiv = body.substring(i * 117 + 8, i * 117 + 9);
            data.fromStationNo = body.substring(i * 117 + 15, i * 117 + 19);
            data.toStationNo = body.substring(i * 117 + 19, i * 117 + 23);
            data.locationNo = body.substring(i * 117 + 23, i * 117 + 35);
            data.locationNo2 = body.substring(i * 117 + 35, i * 117 + 47);
            data.bcData = body.substring(i * 117 + 49, i * 117 + 79);
            dataList.add(data);
        }
        return dataList.stream()
                .map(Id12PacketData::getMcKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<List<Object>> buildRows() {
        List<List<Object>> rows = new ArrayList<>();
        for (Id12PacketData data : dataList) {
            List<Object> row = new ArrayList<>();
            row.add(date);
            row.add(id);
            row.add(data.mcKey);
            row.add(data.carryDiv);
            row.add(data.fromStationNo);
            row.add(data.toStationNo);
            row.add(data.locationNo);
            row.add(data.locationNo2);
            row.add(data.bcData);
            rows.add(row);
        }
        return rows;
    }

    @Getter
    private static class Id12PacketData {

        private String mcKey;

        private String carryDiv;

        private String fromStationNo;

        private String toStationNo;

        private String locationNo;

        private String locationNo2;

        private String bcData;
    }
}
