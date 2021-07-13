package com.saleef.mvcyugiohapp.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;



public class YgoInfoFeeder {

    private YgoInfoFeeder(){

    }
    public static final String PROVIDER_NAME = "YugiohCardProvider";
    // The First two parts of the url are standard and the last portion specifies the table Name
    public static final String URL = "content://" + PROVIDER_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static class InfoFeeder implements BaseColumns {
        public static final String TABLE_NAME = "CardEntries";
        public static final String COLUMN_ID = "ID";
        public static final String CARD_ID = "CID";
        public static final String CARD_NAME = "Name";
        public static final String CARD_TYPE = "Type";
        public static final String CARD_DESC = "Effect";
        public static final String  ATTACK = "Attack";
        public static final String DEFENSE = "Defense";
        public static final String LEVEL = "Level";
        public static final String RACE = "Race";
        public static final String ATTRIBUTE = "Attribute";
        public static final String LINK_VAL = "Link";
        public static final String SCALE = "Scale";
        public static final String CARD_JPG = "Image";

        // Projection of columns will be called when we need to query (i.e get all of our data)
        public static final String[] PROJECTION_ALL = {
                COLUMN_ID,
                CARD_ID,
                CARD_NAME,
                CARD_TYPE,
                CARD_DESC,
                ATTACK,
                DEFENSE,
                LEVEL,
                RACE,
                ATTRIBUTE,
                LINK_VAL,
                SCALE,
                CARD_JPG
        };
        public static final String NEW_CARD_SELECTION_CLAUSE = "SELECT," + CARD_ID + " *FROM " + TABLE_NAME;
        // Content Uri for this specific table that stores cards
        public static final Uri CONTENT_URI = Uri.withAppendedPath(YgoInfoFeeder.CONTENT_URI,TABLE_NAME);

    }

    public static class NewCards implements BaseColumns {
        public static final String TABLE_NAME = "NewCardEntries";
        public static final String COLUMN_ID = "ID";
        public static final String CARD_ID = "CID";
        public static final String CARD_NAME = "Name";
        public static final String CARD_TYPE = "Type";
        public static final String CARD_DESC = "Effect";
        public static final String  ATTACK = "Attack";
        public static final String DEFENSE = "Defense";
        public static final String LEVEL = "Level";
        public static final String RACE = "Race";
        public static final String ATTRIBUTE = "Attribute";
        public static final String LINK_VAL = "Link";
        public static final String SCALE = "Scale";
        public static final String CARD_JPG = "Image";

        // Projection of columns will be called when we need to query (i.e get all of our data)
        public static final String[] PROJECTION_ALL = {
                COLUMN_ID,
                CARD_ID,
                CARD_NAME,
                CARD_TYPE,
                CARD_DESC,
                ATTACK,
                DEFENSE,
                LEVEL,
                RACE,
                ATTRIBUTE,
                LINK_VAL,
                SCALE,
                CARD_JPG
        };
        public static final String NEW_CARD_SELECTION_CLAUSE = "SELECT," + CARD_ID + " *FROM " + TABLE_NAME;
        // Content Uri for this specific table that stores cards
        public static final Uri CONTENT_URI = Uri.withAppendedPath(YgoInfoFeeder.CONTENT_URI,TABLE_NAME);

    }

    public static class BanListTcgCards implements BaseColumns {
        public static final String TABLE_NAME = "BanListTcgCardEntries";
        public static final String COLUMN_ID = "ID";
        public static final String CARD_ID = "CID";
        public static final String CARD_NAME = "Name";
        public static final String CARD_TYPE = "Type";
        public static final String CARD_DESC = "Effect";
        public static final String  ATTACK = "Attack";
        public static final String DEFENSE = "Defense";
        public static final String LEVEL = "Level";
        public static final String RACE = "Race";
        public static final String ATTRIBUTE = "Attribute";
        public static final String LINK_VAL = "Link";
        public static final String SCALE = "Scale";
        public static final String BANLIST = "BANLIST";
        public static final String CARD_JPG = "Image";

        // Projection of columns will be called when we need to query (i.e get all of our data)
        public static final String[] PROJECTION_ALL = {
                COLUMN_ID,
                CARD_ID,
                CARD_NAME,
                CARD_TYPE,
                CARD_DESC,
                ATTACK,
                DEFENSE,
                LEVEL,
                RACE,
                ATTRIBUTE,
                LINK_VAL,
                SCALE,
                BANLIST,
                CARD_JPG
        };
        public static final String NEW_CARD_SELECTION_CLAUSE = "SELECT," + CARD_ID + " *FROM " + TABLE_NAME;
        // Content Uri for this specific table that stores cards
        public static final Uri CONTENT_URI = Uri.withAppendedPath(YgoInfoFeeder.CONTENT_URI,TABLE_NAME);

    }

    public static class BanListOcgCards implements BaseColumns {
        public static final String TABLE_NAME = "BanListOcgCardEntries";
        public static final String COLUMN_ID = "ID";
        public static final String CARD_ID = "CID";
        public static final String CARD_NAME = "Name";
        public static final String CARD_TYPE = "Type";
        public static final String CARD_DESC = "Effect";
        public static final String  ATTACK = "Attack";
        public static final String DEFENSE = "Defense";
        public static final String LEVEL = "Level";
        public static final String RACE = "Race";
        public static final String ATTRIBUTE = "Attribute";
        public static final String LINK_VAL = "Link";
        public static final String SCALE = "Scale";
        public static final String BANLIST = "BANLIST";
        public static final String CARD_JPG = "Image";

        // Projection of columns will be called when we need to query (i.e get all of our data)
        public static final String[] PROJECTION_ALL = {
                COLUMN_ID,
                CARD_ID,
                CARD_NAME,
                CARD_TYPE,
                CARD_DESC,
                ATTACK,
                DEFENSE,
                LEVEL,
                RACE,
                ATTRIBUTE,
                LINK_VAL,
                SCALE,
                BANLIST,
                CARD_JPG
        };
        public static final String NEW_CARD_SELECTION_CLAUSE = "SELECT," + CARD_ID + " *FROM " + TABLE_NAME;
        // Content Uri for this specific table that stores cards
        public static final Uri CONTENT_URI = Uri.withAppendedPath(YgoInfoFeeder.CONTENT_URI,TABLE_NAME);

    }

}
