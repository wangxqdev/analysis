package com.iot.phoebus.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xinquan.w@phoebus-iot.com
 * @date 2022/2/24
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class BasePacket implements Serializable {

    private static final long serialVersionUID = -3855579684348436040L;

    protected String id;

    protected String mcSendTime;

    protected String agcSendTime;

    /**
     * 解析
     *
     * @param data  String
     * @param cache Map<String, List<BasePacket>>
     * @return BasePacket
     */
    public BasePacket decode(String data, Map<String, List<BasePacket>> cache) {
        this.id = data.substring(4, 6);
        this.mcSendTime = data.substring(8, 14);
        this.agcSendTime = data.substring(14, 20);
        String body = data.substring(20);
        List<String> mcKeyList = decodeBody(body);
        for (String mcKey : mcKeyList) {
            List<BasePacket> packetList = cache.computeIfAbsent(mcKey, k -> new ArrayList<>());
            packetList.add(this);
        }
        return this;
    }

    /**
     * 构建行
     *
     * @return List<List<String>>
     */
    public abstract List<List<String>> buildRows();

    /**
     * 解析本体
     *
     * @param body String
     * @return List<String>
     */
    protected abstract List<String> decodeBody(String body);
}
