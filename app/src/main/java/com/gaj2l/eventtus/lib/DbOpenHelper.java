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
        String sql = "";

        try {
            InputStream inputStream = this.mContext.getResources().getAssets().open("database/" + VERSION + "/schema.sql");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                if (!linha.contains("--")) {
                    sql += linha;
                }
            }
            inputStream.close();

            String[] querys = sql.split(";");
            for (String query : querys) {
                db.execSQL(query);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
