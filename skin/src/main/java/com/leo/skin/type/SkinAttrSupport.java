package com.leo.skin.type;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Date:2020-04-04.11:09</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        List<SkinAttr> result = new ArrayList<>();
        int attributeCount = attrs.getAttributeCount();
        SkinAttrType skinAttrType = null;
        for (int i = 0; i < attributeCount; i++) {
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            System.out.println(name + "+++"+value);
            skinAttrType = getSupportAkinAttr(name);
            if (skinAttrType == null)continue;

            if (value.startsWith("@")) {
                int id = -1;
                try {
                    id = Integer.parseInt(value.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == -1) continue;

                String resourceEntryName = context.getResources().getResourceEntryName(id);
                if (resourceEntryName.startsWith(Const.SKIN_PRE)) {
                        result.add(new SkinAttr(resourceEntryName, skinAttrType));
                }
            }
        }
        return result;
    }

    private static SkinAttrType getSupportAkinAttr(String name) {
        for (SkinAttrType value : SkinAttrType.values()) {
            if (name.equals(value.getType())) {
                return value;
            }
        }
        return null;
    }
}
