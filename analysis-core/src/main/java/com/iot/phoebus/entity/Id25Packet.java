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
public class Id25Packet extends BasePacket {

    private static final long serialVersionUID = -5719139619093355548L;

    private String mcKey;

    @Override
    protected List<String> decodeBody(String body) {
        this.mcKey = body.substring(0, 8);
        List<String> mcKeyList = new ArrayList<>();
        mcKeyList.add(mcKey);
        return mcKeyList;
    }

    @Override
    public List<List<Object>> buildRows() {
        List<Object> row = new ArrayList<>();
        row.add(date);
        row.add(id);
        List<List<Object>> rows = new ArrayList<>();
        rows.add(row);
        return rows;
    }
}
