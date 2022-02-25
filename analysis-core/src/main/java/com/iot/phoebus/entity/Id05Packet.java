package com.iot.phoebus.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xinquan.w@phoebus-iot.com
 * @date 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Id05Packet extends BasePacket {

    private static final long serialVersionUID = 8598191862354944696L;

    private String mcKey;

    private String carryDiv;

    private String fromStationNo;

    private String toStationNo;

    private String locationNo;

    private String bcData;

    @Override
    protected List<String> decodeBody(String body) {
        this.mcKey = body.substring(0, 8);
        this.carryDiv = body.substring(8, 9);
        this.fromStationNo = body.substring(13, 17);
        this.toStationNo = body.substring(17, 21);
        this.locationNo = body.substring(21, 33);
        this.bcData = body.substring(35, 65);
        List<String> mcKeyList = new ArrayList<>();
        mcKeyList.add(mcKey);
        return mcKeyList;
    }

    @Override
    public List<List<String>> buildRows() {
        List<String> row = new ArrayList<>();
        row.add(date);
        row.add(id);
        row.add(mcSendTime);
        row.add(agcSendTime);
        row.add(mcKey);
        row.add(carryDiv);
        row.add(fromStationNo);
        row.add(toStationNo);
        row.add(locationNo);
        row.add(bcData);
        List<List<String>> rows = new ArrayList<>();
        rows.add(row);
        return rows;
    }
}
