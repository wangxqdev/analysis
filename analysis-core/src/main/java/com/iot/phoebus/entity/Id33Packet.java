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
public class Id33Packet extends BasePacket {

    private static final long serialVersionUID = -6522031202403014816L;

    private final List<String> mcKeyList = new ArrayList<>();

    @Override
    protected List<String> decodeBody(String body) {
        int dataCnt = body.length() / 133;
        for (int i = 0; i < dataCnt; i++) {
            String mcKey = body.substring(i * 133, i * 133 + 8);
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
