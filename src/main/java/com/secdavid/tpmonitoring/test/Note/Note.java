package com.secdavid.tpmonitoring.test.Note;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * <note>
 * <to>Tove</to>
 * <from>Jani</from>
 * <heading>Reminder</heading>
 * <body>Don't forget me this weekend!</body>
 * </note>
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class Note {
    @XmlAttribute(name = "to")
    String to;

    public Note() {
    }


    @Override
    public String toString() {
        return "Note{" +
                "to='" + to + '\'' +
                '}';
    }
}
