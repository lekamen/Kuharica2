package hr.math.android.kuharica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mabel on 26-Feb-18.
 */

public class DBAdapter {
    static final String KEY_ROWID1 = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";
    static final String TAG1 = "DBAdapter";

    static final String ID_RECEPTA = "id_recepta";
    static final String IME_RECEPTA = "ime_recepta";
    static final String PHOTO_RECEPT = "photo_path";
    static final String NOTES = "notes";

    static final String ID_SASTOJKA = "id_sastojka";
    static final String TEKST_SASTOJKA = "tekst_sastojka";

    static final String ID_UPUTE = "id_upute";
    static final String TEKST_UPUTE = "tekst_upute";

    static final String ID_KATEGORIJE = "id_kategorije";
    static final String IME_KATEGORIJE = "ime_kategorije";
    static final String PHOTO_KATEGORIJA = "photo_path";

    static final String DATABASE_NAME = "KuharicaDB";
    static final String DATABASE_RECEPT = "recept";
    static final String DATABASE_KATEGORIJA = "kategorija";
    //baza popisuje sve sastojke za zadani recept
    static final String DATABASE_SASTOJCI = "sastojci";
    //baza popisuje sve upute za zadani recept
    static final String DATABASE_UPUTE = "upute";
    //popisuje za zadanu kategoriju sve recepte koji su u njoj
    static final String DATABASE_RECEPT_U_KATEGORIJI = "recept_kategorija";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE_RECEPT =
            "create table " + DATABASE_RECEPT + " (" + ID_RECEPTA + " integer primary key autoincrement, "
                    + IME_RECEPTA + " text not null, "
                    + PHOTO_RECEPT + " text, " + NOTES + " text);";

    static final String DATABASE_CREATE_KATEGORIJA =
            "create table " + DATABASE_KATEGORIJA + " (" + ID_KATEGORIJE + " integer primary key autoincrement, "
                    + IME_KATEGORIJE + " text not null, " + PHOTO_KATEGORIJA + " text);";

    static final String DATABASE_CREATE_SASTOJCI =
            "create table " + DATABASE_SASTOJCI + " (" + ID_RECEPTA + " integer , "
                    + TEKST_SASTOJKA + " text not null);";
                    //+ ID_RECEPTA + ", " + ID_SASTOJKA + " ));";

    static final String DATABASE_CREATE_UPUTE =
            "create table " + DATABASE_UPUTE + " (" + ID_RECEPTA + " integer, "
                    + TEKST_UPUTE + " text not null);";

    static final String DATABASE_CREATE_RECEPT_KATEGORIJA =
            "create table " + DATABASE_RECEPT_U_KATEGORIJI + " ("
                    + ID_KATEGORIJE + " integer, "
                    + ID_RECEPTA + " integer, PRIMARY KEY ("
                    + ID_KATEGORIJE + ", " + ID_RECEPTA + " ));";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter (Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(ctx);
        Log.w("1", "a tuu");
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.w("3", "prosla");
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                Log.w("dbhelp", "tu sammm");
                sqLiteDatabase.execSQL(DATABASE_CREATE_RECEPT);
                sqLiteDatabase.execSQL(DATABASE_CREATE_SASTOJCI);
                sqLiteDatabase.execSQL(DATABASE_CREATE_UPUTE);
                sqLiteDatabase.execSQL(DATABASE_CREATE_KATEGORIJA);
                sqLiteDatabase.execSQL(DATABASE_CREATE_RECEPT_KATEGORIJA);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            Log.w("DBAdapter", "upgrading db from" + i + "to" + i1);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_RECEPT);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_SASTOJCI);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_UPUTE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_KATEGORIJA);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_RECEPT_U_KATEGORIJI);

            onCreate(sqLiteDatabase);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        Log.w("2", "tu sam");
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertKategorija(String ime, String path) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(IME_KATEGORIJE, ime);
        initialValues.put(PHOTO_KATEGORIJA, path);
        return db.insert(DATABASE_KATEGORIJA, null, initialValues);
    }

    public boolean deleteKategorija(long id) {
        return db.delete(DATABASE_KATEGORIJA,  ID_KATEGORIJE + "=" + id, null) > 0;
    }

    public Cursor getAllKategorije() {
        return db.query(DATABASE_KATEGORIJA, new String[] {IME_KATEGORIJE, PHOTO_KATEGORIJA},
                null, null, null, null, null);
    }

    public Cursor getKategorija(long id) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_KATEGORIJA, new String[]{ID_KATEGORIJE, IME_KATEGORIJE, PHOTO_KATEGORIJA},
                ID_KATEGORIJE + "=" + id, null, null, null, null, null);
        if(mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long insertRecept(String ime, String path, String notes) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(IME_RECEPTA, ime);
        initialValues.put(PHOTO_RECEPT, path);
        initialValues.put(NOTES, notes);
        return db.insert(DATABASE_RECEPT, null, initialValues);
    }

    public boolean deleteRecept(long id) {
        return db.delete(DATABASE_RECEPT, ID_RECEPTA + "=" + id, null) > 0;
    }

    public Cursor getAllRecepti() throws SQLException {
        return db.query(DATABASE_RECEPT, new String[] {IME_RECEPTA, PHOTO_RECEPT, NOTES},
                null, null, null, null, null);
    }

    //za zadani id kategorije nadji sve recepte koji su u kategoriji
    public Cursor getAllReceptiFromKategorija(long idKategorije) throws SQLException {
        //Cursor sviRecepti = db.query(DATABASE_RECEPT_U_KATEGORIJI, new String[]{ID_RECEPTA},
        //       ID_KATEGORIJE + "=" + idKategorije, null, null, null, null);
        String sviRecepti = "select ID_RECEPTA from " + DATABASE_RECEPT_U_KATEGORIJI + " where "
                + ID_KATEGORIJE + "=" + idKategorije;

        return db.query(DATABASE_RECEPT, new String[] {IME_RECEPTA, PHOTO_RECEPT, NOTES},
                ID_RECEPTA + " in (" + sviRecepti + ")", null, null, null, null);
    }

    //za zadani string provjeri sadržava li ime recepta taj string
    public Cursor getAllReceptiKaoTekst(String tekst) throws SQLException {
        return db.query(DATABASE_RECEPT, new String[] {IME_RECEPTA, PHOTO_RECEPT, NOTES},
                IME_RECEPTA + " LIKE " + tekst, null, null, null, null, null);
    }

    public long insertSastojak(long idRecepta, String tekst) {
        ContentValues values = new ContentValues();
        values.put(ID_RECEPTA, idRecepta);
        values.put(TEKST_SASTOJKA, tekst);
        return db.insert(DATABASE_SASTOJCI, null, values);
    }

    public long insertUpute(long idRecepta, String tekst) {
        ContentValues values = new ContentValues();
        values.put(ID_RECEPTA, idRecepta);
        values.put(TEKST_UPUTE, tekst);
        return db.insert(DATABASE_UPUTE, null, values);
    }

    public boolean deleteSastojak(long idRecepta, long idSastojak) {
        return db.delete(DATABASE_SASTOJCI, ID_RECEPTA + "=" + idRecepta + " AND " + ID_SASTOJKA + "=" + idSastojak, null) > 0;
    }

    public boolean deleteUputa(long idRecepta, long idUpute) {
        return db.delete(DATABASE_UPUTE, ID_RECEPTA + "=" + idRecepta + " AND " + ID_UPUTE + "=" + idUpute, null) > 0;
    }

    //za zadani recept vrati sve sastojke
    public Cursor getAllSastojciZaRecept(long idRecepta) throws SQLException{
        return db.query(DATABASE_SASTOJCI, new String[] {TEKST_SASTOJKA},
                ID_RECEPTA + "=" + idRecepta, null, null, null, null, null);
    }

    //za zadani recept vrati sve upute
    public Cursor getAllUputeZaRecept(long idRecepta) throws SQLException {
        return db.query(DATABASE_UPUTE, new String[] {TEKST_UPUTE},
                ID_RECEPTA + "=" + idRecepta, null, null, null, null, null);
    }

    public long insertReceptUKategoriju(long idKategorije, long idRecepta) {
        ContentValues values = new ContentValues();
        values.put(ID_KATEGORIJE, idKategorije);
        values.put(ID_RECEPTA, idRecepta);
        return db.insert(DATABASE_RECEPT_U_KATEGORIJI, null, values);
    }
}
