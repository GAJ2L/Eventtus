package com.gaj2l.eventtus.lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private final static String DATABASE = "eventtus";
    private final static int VERSION = 1;
    private final Context mContext;

    public DbOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.executeSchema(db, VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        while (oldVersion != newVersion) {
            oldVersion++;
            this.executeSchema(db, oldVersion);
        }
    }

    private void executeSchema(SQLiteDatabase db, int version) {
        String sql = "";

        try {
            InputStream inputStream = this.mContext.getResources().getAssets().open("database/" + version + "/schema.sql");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                if (!row.contains("--")) {
                    sql += row;
                }
            }
            inputStream.close();

            for (String query : sql.split(";")) {
                db.execSQL(query);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDatabase(Context context) {
        boolean delete = context.deleteDatabase("eventtus");
        Message.show(context, "Deletou?" + String.valueOf(delete));
    }

    public static String backupDatabase(Context context) throws Exception {

        final int stringId = context.getApplicationInfo().labelRes;
        final String appName = context.getString(stringId);
        final String path = Environment.getExternalStorageDirectory().getPath() + File.separator + appName + File.separator + "Backup" + File.separator;
        final File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new Exception("Não foi possível criar o diretório de backup.");
            }
        }

        final String databaseName = "eventtus";
        final File dbFile = context.getDatabasePath(databaseName);
        final InputStream in = new FileInputStream(dbFile);
        final String filename = dir.getPath() + File.separator + databaseName + "_" + Calendar.getInstance().getTimeInMillis() + ".bak";
        final OutputStream out = new FileOutputStream(filename);

        final byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }

        out.flush();
        out.close();
        in.close();

        return filename;

    }
}
