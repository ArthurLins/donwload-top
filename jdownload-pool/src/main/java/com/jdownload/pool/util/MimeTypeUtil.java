package com.jdownload.pool.util;

import java.util.HashMap;

public class MimeTypeUtil {

    private static HashMap<String, String> mimeExt = new HashMap<>();

    static {
        mimeExt.put("application/vnd.amazon.ebook", ".azw");
        mimeExt.put("application/octet-stream", ".bin");
        mimeExt.put("application/x-bzip", ".bz");
        mimeExt.put("application/x-bzip2", ".bz2");
        mimeExt.put("application/x-csh", ".csh");
        mimeExt.put("text/css", ".css");
        mimeExt.put("text/csv", ".csv");
        mimeExt.put("application/msword", ".doc");
        mimeExt.put("application/vnd.ms-fontobject", ".eot");
        mimeExt.put("application/epub+zip", ".epub");
        mimeExt.put("image/gif", ".gif");
        mimeExt.put("text/html", ".html");
        mimeExt.put("image/x-icon", ".ico");
        mimeExt.put("text/calendar", ".ics");
        mimeExt.put("application/java-archive", ".jar");
        mimeExt.put("image/jpeg", ".jpg");
        mimeExt.put("application/javascript", ".js");
        mimeExt.put("application/json", ".json");
        mimeExt.put("audio/midi", ".midi");
        mimeExt.put("video/mpeg", ".mpeg");
        mimeExt.put("audio/mpeg", ".mp3");
        mimeExt.put("video/mp4", ".mp4");
        mimeExt.put("application/vnd.apple.installer+xml", ".mpkg");
        mimeExt.put("application/vnd.oasis.opendocument.presentation", ".odp");
        mimeExt.put("application/vnd.oasis.opendocument.spreadsheet", ".ods");
        mimeExt.put("application/vnd.oasis.opendocument.text", ".odt");
        mimeExt.put("audio/ogg", ".oga");
        mimeExt.put("video/ogg", ".ogv");
        mimeExt.put("application/ogg", ".ogx");
        mimeExt.put("font/otf", ".otf");
        mimeExt.put("image/png", ".png");
        mimeExt.put("application/pdf", ".pdf");
        mimeExt.put("application/vnd.ms-powerpoint", ".ppt");
        mimeExt.put("application/x-rar-compressed", ".rar");
        mimeExt.put("application/rtf", ".rtf");
        mimeExt.put("application/x-sh", ".sh");
        mimeExt.put("image/svg+xml", ".svg");
        mimeExt.put("application/x-shockwave-flash", ".swf");
        mimeExt.put("application/x-tar", ".tar");
        mimeExt.put("image/tiff", ".tiff");
        mimeExt.put("application/typescript", ".ts");
        mimeExt.put("font/ttf", ".ttf");
        mimeExt.put("application/vnd.visio", ".vsd");
        mimeExt.put("audio/x-wav", ".wav");
        mimeExt.put("audio/webm", ".weba");
        mimeExt.put("video/webm", ".webm");
        mimeExt.put("image/webp", ".webp");
        mimeExt.put("font/woff", ".woff");
        mimeExt.put("font/woff2", ".woff2");
        mimeExt.put("application/xhtml+xml", ".xhtml");
        mimeExt.put("application/vnd.ms-excel", ".xlsx");
        mimeExt.put("application/xml", ".xml");
        mimeExt.put("application/vnd.mozilla.xul+xml", ".xul");
        mimeExt.put("application/zip", ".zip");
        mimeExt.put("video/3gpp", ".3gp");
        mimeExt.put("video/3gpp2", ".3g2");
        mimeExt.put("application/x-7z-compressed", ".7z");
    }

    public static String mimeToExt(String mimeType) {
        return mimeExt.getOrDefault(mimeType, ".undefined");
    }

}