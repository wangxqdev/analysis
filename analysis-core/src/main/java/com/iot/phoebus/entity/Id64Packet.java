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
public class Id64Packet extends BasePacket {

    private static final long serialVersionUID = 1112489889848230672L;

    private final List<String> mcKeyList = new ArrayList<>();

    @Override
    protected List<String> decodeBody(String body) {
        String dataCntStr = body.substring(4, 6);
        int dataCnt = Integer.parseInt(dataCntStr);
        String details = body.substring(6);
        for (int i = 0; i < dataCnt; i++) {
            String mcKey = details.substring(i * 8, i* 8 + 8);
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
