package com.saleef.mvcyugiohapp.ViewModel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Comparator;
import java.util.Objects;


public class YugiohCard implements Parcelable, Comparator<YugiohCard> {


    private final Integer id;

    private final String name;

    private final String type;

    private final String desc;

    private final Integer atk;

    private final Integer def;

    private final Integer level;

    private final String race;

    private final String attribute;
   // For xyz monsters
    private final Integer scale;



    private final String cardImageUrl;


    // for linkMonsters
    private final Integer linkRating;

    private  String tcgBanlistInfo = "empty";

    private  String ocgBanlistInfo = "empty";


    public YugiohCard(Integer id, String name, String type, String desc, Integer atk, Integer def, Integer level, String race, String attribute,
                      Integer scale, String cardImageUrl,
                       Integer linkRating) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.atk = atk;
        this.def = def;
        this.level = level;
        this.race = race;
        this.attribute = attribute;
        this.scale = scale;
        this.cardImageUrl = cardImageUrl;

        this.linkRating = linkRating;


    }

    protected YugiohCard(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        type = in.readString();
        desc = in.readString();
        if (in.readByte() == 0) {
            atk = null;
        } else {
            atk = in.readInt();
        }
        if (in.readByte() == 0) {
            def = null;
        } else {
            def = in.readInt();
        }
        if (in.readByte() == 0) {
            level = null;
        } else {
            level = in.readInt();
        }
        race = in.readString();
        attribute = in.readString();
        if (in.readByte() == 0) {
            scale = null;
        } else {
            scale = in.readInt();
        }
        cardImageUrl = in.readString();
        if (in.readByte() == 0) {
            linkRating = null;
        } else {
            linkRating = in.readInt();
        }
    }

    public static final Creator<YugiohCard> CREATOR = new Creator<YugiohCard>() {
        @Override
        public YugiohCard createFromParcel(Parcel in) {
            return new YugiohCard(in);
        }

        @Override
        public YugiohCard[] newArray(int size) {
            return new YugiohCard[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getAtk() {
        return atk;
    }

    public Integer getDef() {
        return def;
    }

    public Integer getLevel() {
        return level;
    }

    public String getRace() {
        return race;
    }

    public String getAttribute() {
        return attribute;
    }


    public Integer getScale() {
        return scale;
    }

    public String getCardImageUrl() {
        return cardImageUrl;
    }



    public Integer getLinkRating() {
        return linkRating;
    }

    public String getOcgBanlistInfo() {
        return ocgBanlistInfo;
    }

    public String getTcgBanlistInfo() {
        return tcgBanlistInfo;
    }

    public void setOcgBanlistInfo(String ocgBanlistInfo) {
        this.ocgBanlistInfo = ocgBanlistInfo;
    }

    public void setTcgBanlistInfo(String tcgBanlistInfo) {
        this.tcgBanlistInfo = tcgBanlistInfo;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(desc);
        if (atk == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(atk);
        }
        if (def == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(def);
        }
        if (level == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(level);
        }
        dest.writeString(race);
        dest.writeString(attribute);
        if (scale == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(scale);
        }
        dest.writeString(cardImageUrl);
        if (linkRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(linkRating);
        }
    }



    @Override
    public int compare(YugiohCard o1, YugiohCard o2) {

        return 0;
    }


    public static Comparator<YugiohCard> byAttack = new Comparator<YugiohCard>() {
        @Override
        public int compare(YugiohCard o1, YugiohCard o2) {


            return o1.getAtk() - o2.getAtk();
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };

    public static Comparator<YugiohCard> byDefense = new Comparator<YugiohCard>() {
        @Override
        public int compare(YugiohCard o1, YugiohCard o2) {

            return o1.getDef() - o2.getDef();
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };


    public static Comparator<YugiohCard> byName = new Comparator<YugiohCard>() {
        @Override
        public int compare(YugiohCard o1, YugiohCard o2) {

            return o1.getName().compareTo(o2.getName());
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };

    public static Comparator<YugiohCard> byLevel = new Comparator<YugiohCard>() {
        @Override
        public int compare(YugiohCard o1, YugiohCard o2) {

            return o1.getLevel() - o2.getLevel();
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };

    public static Comparator<YugiohCard> byAttribute = new Comparator<YugiohCard>() {
        @Override
        public int compare(YugiohCard o1, YugiohCard o2) {

            return o1.attribute.compareTo(o2.getAttribute());
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };

    public static Comparator<YugiohCard> byRace = new Comparator<YugiohCard>() {
        @Override
        public int compare(YugiohCard o1, YugiohCard o2) {

            return o1.race.compareTo(o2.race);
        }


        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };


    public static Comparator<YugiohCard> byType = new Comparator<YugiohCard>() {
        @Override
        public int compare(YugiohCard o1, YugiohCard o2) {

            return o1.type.compareTo(o2.type);
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };


    @Override
    public Comparator<YugiohCard> reversed() {
        return null;
    }


    @NonNull
    @Override
    public String toString() {
        return "YugiohCard{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", atk=" + atk +
                ", def=" + def +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YugiohCard)) return false;
        YugiohCard that = (YugiohCard) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(atk, that.atk) &&
                Objects.equals(def, that.def) &&
                Objects.equals(level, that.level) &&
                Objects.equals(race, that.race) &&
                Objects.equals(attribute, that.attribute) &&
                Objects.equals(scale, that.scale) &&
                Objects.equals(cardImageUrl, that.cardImageUrl) &&
                Objects.equals(linkRating, that.linkRating) &&
                Objects.equals(tcgBanlistInfo, that.tcgBanlistInfo) &&
                Objects.equals(ocgBanlistInfo, that.ocgBanlistInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, desc, atk, def, level, race, attribute, scale, cardImageUrl, linkRating, tcgBanlistInfo, ocgBanlistInfo);
    }
}

