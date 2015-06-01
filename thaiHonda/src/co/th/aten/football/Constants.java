/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football;

/**
 *
 * @author Aten
 */
public class Constants {

//      public static int NET_STATUS_OK = 1;
    public static int NET_STATUS_OK = 1;
    public static int NET_STATUS_FAIL = 2;
    public static int NET_STATUS_BUSY = 3;
    public static int MSG_NORMAL = 1;
    public static int MSG_ERROR = 2;
    public static int MSG_NORMAL_BLINK = 3;
    public static int MSG_ERROR_BLINK = 4;
    public static final short ID_HQ = 99;
    public static final short NETWORK_SOBRR = 6;
    public static final short OBU_STATUS_OK = 1;
    public static final short OBU_STATUS_REMOVE = 2;
    public static final short OBU_ISBLACKLIST_NO = 0;
    public static final short OBU_ISBLACKLIST_YES = 1;
    public static final short FILE_TYPE_OBU = 1;
    public static final short FILE_TYPE_BLACKLIST = 2;
    public static final short FILE_TYPE_PARAMETER = 3;
    public static final short FILE_TYPE_TRANSACTION = 4;
    public static final short FILE_TYPE_DBERROR = 5;
    public static final short FILE_TYPE_DATA = 6;
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_FILE_DATETIME = "yyyyMMddHHmmss";
    public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_FILE_TIMESTAMP = "yyyyMMddHHmmssSSS";
    public static final short FILE_SCHD_OBU_FULL = 1;
    public static final short FILE_SCHD_OBU_INC = 2;
    public static final short FILE_SCHD_BLACKLIST_FULL = 3;
    public static final short FILE_SCHD_BLACKLIST_INC = 4;
    public static final short FILE_SCHD_TARIFF = 5;
    public static final short FILE_SCHD_PLAZA = 6;
    public static final String INBOX_PARAMETER = "/INBOX/PARAMETER/";
    public static final String INBOX_OBU = "/INBOX/OBU/";
    public static final String INBOX_BOJ = "/INBOX/TRX/";
    public static final String INBOX_EOJ = "/INBOX/TRX/";
    public static final String INBOX_TRX = "/INBOX/TRX/";
    public static final String INBOX_ALARM = "/INBOX/ALARM/";
    public static final String INBOX_DATA = "/INBOX/DATA/";
    public static final String INBOX_BLACKLIST = "/INBOX/BLACKLIST/";
    public static final String INBOX_LOADOBU = "/INBOX/LOADOBU/";
    public static final String OUTBOX_ALARM = "/OUTBOX/ALARM/";
    public static final String OUTBOX_TIMESLOT = "/OUTBOX/TIMESLOT/";
    public static final String OUTBOX_BOJ = "/OUTBOX/TRX/";
    public static final String OUTBOX_EOJ = "/OUTBOX/TRX/";
    public static final String OUTBOX_TRX = "/OUTBOX/TRX/";
    public static final String OUTBOX_PARAMETER = "/OUTBOX/PARAMETER/";
    public static final String OUTBOX_DATA = "/OUTBOX/DATA/";
    public static final String OUTBOX_BLACKLIST = "/OUTBOX/BLACKLIST/";
    public static final String BECL_REPORT_TITLE = "BECL/POS/Point of Sales";
    public final static int GROUP_ADMIN = 10;
    public final static int GROUP_TC = 20;
    public final static int GROUP_SV = 30;
    public final static int GROUP_CTC = 40;
    public final static int GROUP_MT = 50;
    public final static int GROUP_RAD = 60;
    public final static int GROUP_RAD_23 = 62;
    public final static int GROUP_PLAN = 70;
    public final static int GROUP_TCS = 80;
    public static final short OBU_TRANSFER_INITIAL = 2;
    public static final short OBU_TRANSFER_TRANSFER = 2;
    public static final short OBU_TRANSFER_RECEIVE = 3;
    public static final short OBU_TRANSFER_TERMINATE = 4;
    public static final short OBU_TRANSFER_CLAIM = 5;
    public static final short OBU_TRANSFER_RELOCATION = 6;
    public static final short OBU_TRANSFER_MATCH = 7;
    public static final short OBU_TRANSFER_SOLD = 8;
    public static final short OBU_TRANSFER_RECEIVE_TERMINATE = 40;
    public static final short OBU_TRANSFER_CONFIRM_TERMINATE = 41;
    public static final short OBU_TRANSFER_RECEIVE_CLAIM = 50;
    public static final short OBU_TRANSFER_CONFIRM_CLAIM = 51;
    public static final short OBU_TRANSFER_RETURN_SUPPLIER = 52;
    public static final short PAYMENT_FOR_SELL = 1;
    public static final short PAYMENT_FOR_TOPUP = 2;
    public static final short PAYMENT_FOR_STATEMENT = 3;
    // ==========
    public static final int PAYMENT_DETAIL_TYPE = 7;
}
