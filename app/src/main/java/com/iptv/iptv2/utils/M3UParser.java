package com.iptv.iptv2.utils;

import com.iptv.iptv2.models.Channel;
import com.iptv.iptv2.models.Movie;
import com.iptv.iptv2.models.Show;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class M3UParser {

    public static List<Channel> parseM3UForChannels(String m3uContent) throws Exception {
        List<Channel> channels = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(m3uContent));
        String line;
        String name = null;
        String tvgId = null;
        String tvgName = null;
        String tvgType = null;
        String groupTitle = null;
        String tvgLogo = null;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#EXTINF")) {
                String[] attributes = line.substring(line.indexOf(" ")).split(" ");
                for (String attribute : attributes) {
                    if (attribute.startsWith("tvg-id=")) {
                        tvgId = attribute.substring(8, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-name=")) {
                        tvgName = attribute.substring(10, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-type=")) {
                        tvgType = attribute.substring(10, attribute.length() - 1);
                    } else if (attribute.startsWith("group-title=")) {
                        groupTitle = attribute.substring(13, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-logo=")) {
                        tvgLogo = attribute.substring(10, attribute.length() - 1).replace("\"", ""); // Clean up URL
                    }
                }
                int commaIndex = line.indexOf(",");
                if (commaIndex != -1) {
                    name = line.substring(commaIndex + 1).trim();
                }
            } else if (line.startsWith("http") && name != null) {
                channels.add(new Channel(name, line.trim(), tvgId, tvgName, tvgType, groupTitle, tvgLogo));
                name = null;
                tvgId = null;
                tvgName = null;
                tvgType = null;
                groupTitle = null;
                tvgLogo = null;
            }
        }
        return channels;
    }

    public static List<Movie> parseM3UForMovies(String m3uContent) throws Exception {
        List<Movie> movies = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(m3uContent));
        String line;
        String name = null;
        String tvgId = null;
        String tvgName = null;
        String tvgType = null;
        String groupTitle = null;
        String tvgLogo = null;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#EXTINF")) {
                String[] attributes = line.substring(line.indexOf(" ")).split(" ");
                for (String attribute : attributes) {
                    if (attribute.startsWith("tvg-id=")) {
                        tvgId = attribute.substring(8, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-name=")) {
                        tvgName = attribute.substring(10, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-type=")) {
                        tvgType = attribute.substring(10, attribute.length() - 1);
                    } else if (attribute.startsWith("group-title=")) {
                        groupTitle = attribute.substring(13, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-logo=")) {
                        tvgLogo = attribute.substring(10, attribute.length() - 1).replace("\"", ""); // Clean up URL
                    }
                }
                int commaIndex = line.indexOf(",");
                if (commaIndex != -1) {
                    name = line.substring(commaIndex + 1).trim();
                }
            } else if (line.startsWith("http") && name != null) {
                movies.add(new Movie(name, line.trim(), tvgId, tvgName, tvgType, groupTitle, tvgLogo));
                name = null;
                tvgId = null;
                tvgName = null;
                tvgType = null;
                groupTitle = null;
                tvgLogo = null;
            }
        }
        return movies;
    }

    public static List<Show> parseM3UForShows(String m3uContent) throws Exception {
        List<Show> shows = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(m3uContent));
        String line;
        String name = null;
        String tvgId = null;
        String tvgName = null;
        String tvgType = null;
        String groupTitle = null;
        String tvgLogo = null;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#EXTINF")) {
                String[] attributes = line.substring(line.indexOf(" ")).split(" ");
                for (String attribute : attributes) {
                    if (attribute.startsWith("tvg-id=")) {
                        tvgId = attribute.substring(8, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-name=")) {
                        tvgName = attribute.substring(10, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-type=")) {
                        tvgType = attribute.substring(10, attribute.length() - 1);
                    } else if (attribute.startsWith("group-title=")) {
                        groupTitle = attribute.substring(13, attribute.length() - 1);
                    } else if (attribute.startsWith("tvg-logo=")) {
                        tvgLogo = attribute.substring(10, attribute.length() - 1).replace("\"", ""); // Clean up URL
                    }
                }
                int commaIndex = line.indexOf(",");
                if (commaIndex != -1) {
                    name = line.substring(commaIndex + 1).trim();
                }
            } else if (line.startsWith("http") && name != null) {
                shows.add(new Show(name, line.trim(), tvgId, tvgName, tvgType, groupTitle, tvgLogo));
                name = null;
                tvgId = null;
                tvgName = null;
                tvgType = null;
                groupTitle = null;
                tvgLogo = null;
            }
        }
        return shows;
    }
}
