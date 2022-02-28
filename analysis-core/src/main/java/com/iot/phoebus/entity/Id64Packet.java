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
    public List<List<Object>> buildRows() {
        List<List<Object>> rows = new ArrayList<>();
        for (int i = 0; i < mcKeyList.size(); i++) {
            List<Object> row = new ArrayList<>();
            row.add(date);
            row.add(id);
            rows.add(row);
        }
        return rows;
    }
}
