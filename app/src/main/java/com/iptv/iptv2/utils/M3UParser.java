package com.iptv.iptv2.utils;

import com.iptv.iptv2.models.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class M3UParser {

    private static final Pattern TVG_PATTERN = Pattern.compile("tvg-id=\"([^\"]*)\".*tvg-name=\"([^\"]*)\".*tvg-logo=\"([^\"]*)\".*group-title=\"([^\"]*)\",(.*)");

    public static List<Channel> parseM3U(String m3uContent) {
        List<Channel> channels = new ArrayList<>();
        String[] lines = m3uContent.split("\n");

        for (String line : lines) {
            Matcher matcher = TVG_PATTERN.matcher(line);

            if (matcher.find()) {
                String tvgId = matcher.group(1);
                String tvgName = matcher.group(2);
                String tvgLogo = matcher.group(3);
                String groupTitle = matcher.group(4);
                String name = matcher.group(5);
                String url = null;

                if (lines.length > 1) {
                    url = lines[1]; // URL is typically on the next line after the metadata
                }

                Channel channel = new Channel(name, url, tvgId, tvgName, tvgLogo, groupTitle);
                channels.add(channel);
            }
        }

        return channels;
    }
}
