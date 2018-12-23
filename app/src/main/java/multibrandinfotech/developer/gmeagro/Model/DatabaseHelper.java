package multibrandinfotech.developer.gmeagro.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gmeagro.db";
    private static final int VERSION = 1;
    private static final String LOGIN_TABLE_NAME = "LOGIN";
    private static final String ORDER_TABLE_NAME = "INDENT";
    private static final String ADD_ITEM_TABLE_NAME = "ADD_ITEM";
    //private static final String ID = "ID";
    private static final String USER_NAME = "UserName";
    private static final String PASSWORD = "Password";

    private static final String PAYMENT_TYPE = "PaymentMethod";
    private static final String ORDER_DATE = "OrderDate";
    private static final String DELIVERY_DATE = "DeliveryDate";
    private static final String DISTRIBUTOR = "Distributor";
    private static final String PARTY_CODE = "PartyCode";

    private static final String ITEM_NAME = "ItemName";
    private static final String ITEM_CODE = "ItemCode";
    private static final String QUANTITY = "Quantity";
    private static final String UNIT_PRICE = "UnitPrice";
    private static final String DISCOUNT = "Discount";
    private static final String TOTAL_PRICE = "TotalPrice";

    private static final String CREATE_LOGIN_TABLE = "CREATE TABLE " + LOGIN_TABLE_NAME + "("
//            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " VARCHAR(50) UNIQUE, "
            + PASSWORD + " VARCHAR(50));";

    private static final String CREATE_INDENT_TABLE = "CREATE TABLE " + ORDER_TABLE_NAME + "("
            + PAYMENT_TYPE + " VARCHAR(10) NOT NULL, "
            + ORDER_DATE + " DATE, "
            + DELIVERY_DATE + " DATE NOT NULL, "
            + DISTRIBUTOR + " VARCHAR(100) NOT NULL UNIQUE, "
            + PARTY_CODE + " VARCHAR(100) NOT NULL UNIQUE);";

    private static final String CREATE_ADD_ITEM_TABLE = "CREATE TABLE " + ADD_ITEM_TABLE_NAME + "( "
            + ITEM_NAME + " VARCHAR(100) NOT NULL, "
            + ITEM_CODE + " VARCHAR(100) NOT NULL, "
            + QUANTITY + " INTEGER NOT NULL, "
            + UNIT_PRICE + " INTEGER NOT NULL, "
            + DISCOUNT + " VARCHAR(10), "
            + TOTAL_PRICE + " DOUBLE);";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_LOGIN_TABLE);
            db.execSQL(CREATE_INDENT_TABLE);
            db.execSQL(CREATE_ADD_ITEM_TABLE);
            db.execSQL("INSERT INTO " + LOGIN_TABLE_NAME + "(" + USER_NAME + ", " + PASSWORD + ") VALUES('admin', 'admin');");
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE_NAME);
        onCreate(db);
    }

    public Cursor fetchLoginData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + LOGIN_TABLE_NAME, null);
        return cursor;
    }

    public Cursor fetchIndentData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + ORDER_TABLE_NAME, null);
        return cursor;
    }


    public Cursor fetchAddItemData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + ADD_ITEM_TABLE_NAME, null);
        return cursor;
    }

    public long insertIndentData(String paymentType, String orderDate, String deliveryDate, String distributor, String partyCode) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PAYMENT_TYPE, paymentType);
        contentValues.put(ORDER_DATE, orderDate);
        contentValues.put(DELIVERY_DATE, deliveryDate);
        contentValues.put(DISTRIBUTOR, distributor);
        contentValues.put(PARTY_CODE, partyCode);

        long input = sqLiteDatabase.insert(ORDER_TABLE_NAME, null, contentValues);
        return input;
    }

    public long insertAddItemData(String itemName, String itemCode, int unitPrice, int quantity, String discount, double totalPrice) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, itemName);
        contentValues.put(ITEM_CODE, itemCode);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(UNIT_PRICE, unitPrice);
        contentValues.put(DISCOUNT, discount);
        contentValues.put(TOTAL_PRICE, totalPrice);

        long input = sqLiteDatabase.insert(ADD_ITEM_TABLE_NAME, null, contentValues);
        return input;
    }
}
