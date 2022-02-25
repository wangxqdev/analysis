package com.iot.phoebus.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinquan.w@phoebus-iot.com
 * @date 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Id32Packet extends BasePacket {

    private static final long serialVersionUID = 5500907983435320611L;

    private final List<String> mcKeyList = new ArrayList<>();

    @Override
    protected List<String> decodeBody(String body) {
        int dataCnt = body.length() / 10;
        for (int i = 0; i < dataCnt; i++) {
            String mcKey = body.substring(i * 10, i * 10 + 8);
            mcKeyList.add(mcKey);
        }
        return mcKeyList;
    }

    @Override
    public List<List<String>> buildRows() {
        List<List<String>> rows = new ArrayList<>();
        for (String mcKey : mcKeyList) {
            List<String> row = new ArrayList<>();
            row.add(date);
            row.add(id);
            row.add(mcSendTime);
            row.add(agcSendTime);
            row.add(mcKey);
            rows.add(row);
        }
        return rows;
    }
}
