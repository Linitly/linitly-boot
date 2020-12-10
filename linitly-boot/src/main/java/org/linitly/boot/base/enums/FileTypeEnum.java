package org.linitly.boot.base.enums;

public enum FileTypeEnum {

    //JPEG(jpg)
    JPEG("ffd8ffe", "jpeg"),
    //PNG(PNG),
    PNG("89504e470d0a1a0a0000", "png"),
    //GIF(gif),
    GIF("47494638396126026f01", "gif"),
    //TIFF(tif),
    TIF("49492a00227105008037", "tif"),
    //16色位图(bmp),
    BMP16("424d228c010000000000", "bmp"),
    //24位位图(bmp),
    BMP24("424d8240090000000000", "bmp"),
    //256色位图(bmp)
    BMP256("424d8e1b030000000000", "bmp"),
    //CAD(dwg),
    DWG("41433130313500000000", "dwg"),
    //HTML(html),
    HTML("3c21444f435459504520", "html"),
    //HTM(htm),
    HTM("3c21646f637479706520", "htm"),
    //css
    CSS("48544d4c207b0d0a0942", "css"),
    //js
    JS("696b2e71623d696b2e71", "js"),
    //Rich Text Format(rtf),
    RTF("7b5c727466315c616e73", "rtf"),
    //Photoshop(psd),
    PSD("38425053000100000000", "psd"),
    //EMAIL [OUTLOOK EXPRESS 6](EML),
    EML("46726f6d3a203d3f6762", "eml"),
    //MS EXCEL 注意：WORD、MSI 和 EXCEL的文件头一样
    DOC("d0cf11e0a1b11ae10000", "doc"),
    //VISIO 绘图
    VSD("d0cf11e0a1b11ae10000", "vsd"),
    //MS ACCESS(MDB),
    MDB("5374616e64617264204a", "mdb"),
    PS("252150532d41646f6265", "ps"),
    //ADOBEACROBAT(PDF),
    PDF("255044462d312e350d0a", "pdf"),
    //RMVB/RM相同
    RMVB("2e524d46000000120001", "rmvb"),
    //FLV与F4V相同
    FLV("464c5601050000000900", "flv"),
    MP4("00000020667479706d70", "mp4"),
    MP3("49443303000000002176", "mp3"),
    MPG("000001ba210001000180", "mpg"),
    //WMV与ASF相同
    WMV("3026b2758e66cf11a6d9", "wmv"),
    //WAVE(WAV),
    WAV("52494646e27807005741", "wav"),
    AVI("52494646d07d60074156", "avi"),
    //MIDI(MID)
    MID("4d546864000000060001", "mid"),
    ZIP("504b0304140008000800", "zip"),
    RAR("526172211a0700cf9073", "rar"),
    INI("235468697320636f6e66", "ini"),
    JAR("504b0304140008080800", "jar"),
    //可执行文件
    EXE("4d5a9000030000000400", "exe"),
    //JSP文件
    JSP("3c25402070616765206c", "jsp"),
    //MF文件
    MF("4d616e69666573742d56", "mf"),
    //XML文件
    XML("3c3f786d6c2076657273", "xml"),
    //XML文件
    SQL("494e5345525420494e54", "sql"),
    //JAVA文件
    JAVA("7061636b616765207765", "java"),
    //BAT文件
    BAT("406563686f206f66660d", "bat"),
    //GZ文件
    GZ("1f8b0800000000000000", "gz"),
    //BAT文件
    PROPERTIES("6c6f67346a2e726f6f74", "properties"),
    //BAT文件
    BATCLASS("cafebabe0000002e0041", "bat"),
    CHM("49545346030000006000", "bat"),
    MXP("04000000010000001300", "bat"),
    //DOCX文件
    DOCX("504b0304140006000800", "docx"),
    //WPS文字WPS、表格ET、演示DPS都是一样的
    WPS("d0cf11e0a1b11ae10000", "wps"),
    TORRENT("6431303a637265617465", "torrent"),
    //QUICKTIME(MOV),
    MOV("6d6f6f76", "mov"),
    //WORDPERFECT(WPD),
    WPD("ff575043", "wpd"),
    //OUTLOOK EXPRESS(DBX),
    DBX("cfad12fec5fd746f", "dbx"),
    //OUTLOOK(PST),
    PST("2142444e", "pst"),
    //QUICKEN(QDF),
    QDF("ac9ebd8f", "qdf"),
    //WINDOWS PASSWORD(PWL),
    PWL("e3828596", "pwl"),
    //Real Audio(ram),
    RAM("2e7261fd", "ram"),
    XLSX("504b03040a0000000000", "xlsx"),
    XLS("d0cf11e0a1b11ae12", "xls"),
    ;

    FileTypeEnum(String value, String code) {
        this.value = value;
        this.code = code;
    }

    private String value;
    private String code;

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

}
