package com.matejdro.pebblenotificationcenter.appsetting;

import com.matejdro.pebblecommons.util.TextUtil;
import com.matejdro.pebblenotificationcenter.appsetting.AppSetting;
import com.matejdro.pebblenotificationcenter.appsetting.AppSettingStorage;

import java.util.ArrayList;
import java.util.List;

public class VibrationPattern
{
    public static List<Byte> parseVibrationPattern(String pattern)
    {
        String split[] = pattern.split(",");

        List<Byte> bytes = new ArrayList<Byte>(40);
        int max = Math.min(20, split.length);
        int total = 0;

        for (int i = 0; i < max; i++)
        {
            try
            {
                int segment = Integer.parseInt(split[i].trim());
                segment = Math.min(segment, 10000 - total);
                total -= segment;

                bytes.add((byte) (segment & 0xFF));
                bytes.add((byte) ((segment >> 8) & 0xFF));

                if (total >= 10000)
                    break;

            } catch (NumberFormatException e)
            {
            }
        }

        if (bytes.size() == 0)
        {
            bytes.add((byte) 0);
            bytes.add((byte) 0);

        }

        return bytes;
    }

    public static boolean validateVibrationPattern(String pattern)
    {
        if (pattern.trim().isEmpty())
            return false;

        String split[] = pattern.split(",");

        for (String s : split)
        {
            if (!TextUtil.isInteger(s.trim()))
                return false;
        }

        return true;
    }
}
