package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.event.DataComponentValue;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;

import java.util.HashMap;
import java.util.Map;

public class ItemHoveredContent implements HoveredContent {
    private final String id;
    private final String count;
    private final Map<String, String> sdata;
    private final Map<String, String> vdata;

    public ItemHoveredContent(String id, String count, Map<String, String> sdata, Map<String, String> vdata) {
        this.id = id;
        this.count = count;
        this.sdata = sdata;
        this.vdata = vdata;
    }

    @Override
    public HoverEventSource<?> instantiate(InstantiationContext ctx) {
        HoverEvent.ShowItem showItem = HoverEvent.ShowItem.showItem(
                Key.key(ctx.substituteVars(id)),
                Integer.parseInt(ctx.substituteVars(count))
        );
        if(sdata.isEmpty() && vdata.isEmpty()) return HoverEvent.showItem(showItem);

        Map<Key, DataComponentValue> dataComponentMap = new HashMap<>();
        sdata.forEach((k, v) -> {
            dataComponentMap.put(Key.key(k), BinaryTagHolder.binaryTagHolder(ctx.substituteVars(v)));
        });
        vdata.forEach((k, v) -> {
            try {
                dataComponentMap.put(Key.key(k), (DataComponentValue) ctx.getVarRaw(v));
            }
            catch(ClassCastException e) {
                throw new IllegalArgumentException("Value of " + k + ", pointed to by variable " + v + ", is not of type DataComponentValue.");
            }
        });
        return HoverEvent.showItem(showItem.dataComponents(dataComponentMap));
    }
}
