package com.example.alumno.helloworld.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.alumno.helloworld.R;

import com.example.alumno.helloworld.Modelo.Alineacion;
import com.example.alumno.helloworld.Modelo.CategoriaEntrenamiento;
import com.example.alumno.helloworld.Modelo.CoordenadasAlineacion;
import com.example.alumno.helloworld.Modelo.CoordenadasEntrenamiento;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.Modelo.ObjetoEntrenamiento;

import java.util.ArrayList;
import java.util.List;

public class GestorBD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "staleCoach";
    private Context context;

    public GestorBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creo la base de datos
        String crearTablaDeporte="create table deporte (" +
                "id_deporte INTEGER PRIMARY KEY AUTOINCREMENT," +
                "deporte varchar (255)," +
                "fondo varchar (255)," +
                "campo varchar(255))";
        db.execSQL(crearTablaDeporte);

        String crearTablaentrenamineto="create table entrenamiento (" +
                "id_entre INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre varchar (255)," +
                "id_deporte integer references deporte(id_deporte)," +
                "imagen_miniatura varchar (255)," +
                "descripcion varchar (255)," +
                "categoria integer references categoria_entrenamiento(id_cat_entre))";
        db.execSQL(crearTablaentrenamineto);

        String objeto_entrenamiento="create table objeto_entrenamiento(" +
                "id_obj INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre varchar (255)," +
                "descripcion varchar (255)," +
                "imagen varchar (255))";
        db.execSQL(objeto_entrenamiento);

        String coordenadas_entrenamiento="create table coordenadas_entrenamiento (" +
                "id_obj INTEGER references objeto_entrenamiento(id_obj)," +
                "id_entr INTEGER references entrenamiento(id_entre)," +
                "coordx REAL," +
                "coordy REAL," +
                "PRIMARY KEY (id_obj, id_entr,coordx,coordy))";
        db.execSQL(coordenadas_entrenamiento);

        String categoria_entrenamiento="create table categoria_entrenamiento(" +
                "id_cat INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre varchar (255)," +
                "descripcion varchar (255)," +
                "foto varchar (255)," +
                "id_deporte integer references deporte(id_deporte))";
        db.execSQL(categoria_entrenamiento);

        String alineacion="create table alineacion(" +
                "id_alin INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre varchar (255)," +
                "id_deporte integer references deporte(id_deporte)," +
                "imagen_miniatura varchar (255)," +
                "descripcion varchar (255))";
        db.execSQL(alineacion);

        String coordenadas_alineacion="create table coordenadas_alineacion(" +
                "id_obj INTEGER references objeto_entrenamiento(id_obj)," +
                "id_alin INTEGER references alineacion(id_alin)," +
                "coordx REAL," +
                "coordy REAL," +
                "PRIMARY KEY (id_obj, id_alin,coordx,coordy))";
        db.execSQL(coordenadas_alineacion);

        //Meto en la base de datos los datos fijos
        String insertDeporte="INSERT INTO deporte ('id_deporte','deporte','fondo','campo') VALUES" +
                "(0,'Futbol','fondo_futbol.jpg','campo_futbol.jpg')," +
                "(1,'Baloncesto','fondo_baloncesto.jpg','campo_baloncesto.jpg')," +
                "(2,'Voleybol','fondo_voleybol.jpg','campo_voleybol.jpg')";
        db.execSQL(insertDeporte);

        String insertaCategoria="INSERT INTO categoria_entrenamiento ('id_cat','nombre','descripcion','foto','id_deporte') VALUES" +
                "(0,'"+context.getResources().getString(R.string.categoriaRemate)+"','','remate.png',1)," +
                "(1,'"+context.getResources().getString(R.string.categoriaPase)+"','','pase.png',1)," +
                "(2,'"+context.getResources().getString(R.string.categoriaVelocidad)+"','','velocidad.png',1)," +
                "(3,'"+context.getResources().getString(R.string.categoriaBalonParado)+"','','balon-parado.png',1)," +
                "(4,'"+context.getResources().getString(R.string.categoriaFisico)+"','','fisico.png',1)," +
                "(5,'"+context.getResources().getString(R.string.categoriaAtaque)+"','','ataque.png',1)," +
                "(6,'"+context.getResources().getString(R.string.categoriaDefensa)+"','','defensa.png',1),"+
                "(7,'"+context.getResources().getString(R.string.categoriaOtros)+"','','otros.png',1)" ;
        db.execSQL(insertaCategoria);

        String insertaObjetos="INSERT INTO objeto_entrenamiento ('id_obj','nombre','descripcion','imagen') VALUES" +
                "(0,'ficharoja','','ficharoja.png')," +
                "(1,'fichaazul','','fichaazul.png')," +
                "(2,'balon','','balon.png')," +
                "(3,'cono','','cono.png')," +
                "(4,'valla','','valla.png'),"+
                "(5,'numeros','','numeros.png'),"+
                "(6,'papelera','','papelera.png')";
        db.execSQL(insertaObjetos);

    }
    public void eliminarEntrenamiento(Entrenamiento e){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("entrenamiento", "id_entre" + " = ?",
                new String[] { String.valueOf(e.getId_entre()) });
                db = this.getWritableDatabase();
        db.delete("coordenadas_entrenamiento", "id_entr" + " = ?",
                new String[] { String.valueOf(e.getId_entre()) });
        db.close();
    }
    public void eliminarAlineacion(Alineacion e){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("alineacion", "id_alin" + " = ?",
                new String[] { String.valueOf(e.getId_alin()) });
        db = this.getWritableDatabase();
        db.delete("coordenadas_alineacion", "id_alin" + " = ?",
                new String[] { String.valueOf(e.getId_alin()) });
        db.close();
    }

    public ObjetoEntrenamiento getObjetoEntrenamient(String nombre){
        ObjetoEntrenamiento cat=null;

        String selectQuery = "SELECT  * FROM objeto_entrenamiento where nombre='"+nombre+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                cat=new ObjetoEntrenamiento(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return  cat;
    }
    public ObjetoEntrenamiento getObjetoEntrenamiento(int id){
        ObjetoEntrenamiento objeto = null;
        // Select All Query
        String selectQuery = "SELECT  * FROM objeto_entrenamiento where id_obj="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                objeto=new ObjetoEntrenamiento(id,cursor.getString(1),cursor.getString(2),cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return objeto;
    }
    public List<CoordenadasEntrenamiento> getCoordenadasEntrenamiento(Entrenamiento e){
        List<CoordenadasEntrenamiento> categorias = new ArrayList<CoordenadasEntrenamiento>();
        // Select All Query
        String selectQuery = "SELECT  * FROM coordenadas_entrenamiento where id_entr="+e.getId_entre();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(new CoordenadasEntrenamiento(getObjetoEntrenamiento(cursor.getInt(0)),e,cursor.getDouble(2),cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }
    public List<CoordenadasAlineacion> getCoordenadasAlineacion(Alineacion e){
        List<CoordenadasAlineacion> categorias = new ArrayList<CoordenadasAlineacion>();
        // Select All Query
        String selectQuery = "SELECT  * FROM coordenadas_alineacion where id_alin="+e.getId_alin();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(new CoordenadasAlineacion(getObjetoEntrenamiento(cursor.getInt(0)),e,cursor.getDouble(2),cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }
    public List<Entrenamiento> getEntrenamientos(CategoriaEntrenamiento cat,Deporte d){
        List<Entrenamiento> categorias = new ArrayList<Entrenamiento>();
        // Select All Query
        String selectQuery = "SELECT  * FROM entrenamiento where categoria="+cat.getId_cat();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(new Entrenamiento(cursor.getInt(0),cursor.getString(1),d,cursor.getString(3),cursor.getString(4),cat));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }
    public List<Alineacion> getAlineaciones(Deporte d){
        List<Alineacion> alineaciones = new ArrayList<Alineacion>();
        // Select All Query
        String selectQuery = "SELECT  * FROM alineacion where id_deporte="+d.getId_deporte();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                alineaciones.add(new Alineacion(cursor.getInt(0),cursor.getString(1),d,cursor.getString(3),cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return alineaciones;
    }
    public CategoriaEntrenamiento getCategoriaEntrenamiento(String nombre,Deporte deporte){
        CategoriaEntrenamiento cat=null;

        String selectQuery = "SELECT  * FROM categoria_entrenamiento where nombre='"+nombre+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                cat=new CategoriaEntrenamiento(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),deporte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return  cat;
    }
    public void modificarEntrenamiento(Entrenamiento e){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nombre", e.getNombre());
        values.put("id_deporte", e.getDeporte().getId_deporte());
        values.put("imagen_miniatura",e.getImagen_miniatura());
        values.put("descripcion",e.getDescripcion());
        values.put("categoria",e.getCategoria_entrenamiento().getId_cat());

        // updating row
        db.update("entrenamiento", values, "id_entre" + " = ?",
                new String[] {String.valueOf(e.getId_entre()) });
        db.close();
    }
    public void modificarAlineacion(Alineacion e){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nombre", e.getNombre());
        values.put("id_deporte", e.getId_deporte().getId_deporte());
        values.put("imagen_miniatura",e.getImagen_miniatura());
        values.put("descripcion",e.getDescripcion());

        // updating row
        db.update("alineacion", values, "id_alin" + " = ?",
                new String[] {String.valueOf(e.getId_alin()) });
        db.close();
    }
    public void modificarCoordenadasEntrenamiento(List<CoordenadasEntrenamiento> coordenadas,Entrenamiento entrenamiento){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("coordenadas_entrenamiento", "id_entr" + " = ?",
                new String[] { String.valueOf(entrenamiento.getId_entre()) });
        db.close();
        for (CoordenadasEntrenamiento c : coordenadas) {
            c.setId_entr(entrenamiento);
            insertarCoordenadasEntrenamiento(c);
        }
    }
    public void modificarCoordenadasAlineacion(List<CoordenadasAlineacion> coordenadas,Alineacion alineacion){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("coordenadas_alineacion", "id_alin" + " = ?",
                new String[] { String.valueOf(alineacion.getId_alin()) });
        db.close();
        for (CoordenadasAlineacion c : coordenadas) {
            c.setId_alin(alineacion);
            insertarCoordenadasAlineacion(c);
        }
    }

    public int insertarAlineacion(Alineacion a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", a.getNombre());
        values.put("id_deporte", a.getId_deporte().getId_deporte());
        values.put("imagen_miniatura",a.getImagen_miniatura());
        values.put("descripcion",a.getDescripcion());
        db.insert("alineacion", null, values);

        Cursor c = db.rawQuery("SELECT last_insert_rowid()", null);
        int devolver=0;
        if (c.moveToFirst()) {
            devolver = (c.getInt(0));
        }

        db.close(); // Closing database connection
        return devolver;
    }
    public int insertarEntrenamiento(Entrenamiento e){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", e.getNombre());
        values.put("id_deporte", e.getDeporte().getId_deporte());
        values.put("imagen_miniatura",e.getImagen_miniatura());
        values.put("descripcion",e.getDescripcion());
        values.put("categoria",e.getCategoria_entrenamiento().getId_cat());
        db.insert("entrenamiento", null, values);

        Cursor c = db.rawQuery("SELECT last_insert_rowid()", null);
        int devolver=0;
        if (c.moveToFirst()) {
            devolver = (c.getInt(0));
        }

        db.close(); // Closing database connection
        return devolver;
    }

    public int insertarCoordenadasEntrenamiento(CoordenadasEntrenamiento ce){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_obj", ce.getId_obj().getId_obj());
        values.put("id_entr", ce.getId_entr().getId_entre());
        values.put("coordx",ce.getCoordx());
        values.put("coordy",ce.getCoordy());
        db.insert("coordenadas_entrenamiento", null, values);

        Cursor c = db.rawQuery("SELECT last_insert_rowid()", null);
        int devolver=0;
        if (c.moveToFirst()) {
            devolver = (c.getInt(0));
        }

        db.close(); // Closing database connection
        return devolver;
    }
    public int insertarCoordenadasAlineacion(CoordenadasAlineacion ce){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_obj", ce.getId_obj().getId_obj());
        values.put("id_alin",ce.getId_alin().getId_alin());
        values.put("coordx",ce.getCoordx());
        values.put("coordy",ce.getCoordy());
        db.insert("coordenadas_alineacion", null, values);

        Cursor c = db.rawQuery("SELECT last_insert_rowid()", null);
        int devolver=0;
        if (c.moveToFirst()) {
            devolver = (c.getInt(0));
        }

        db.close(); // Closing database connection
        return devolver;
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimino la base de datos si existe
        db.execSQL("DROP TABLE IF EXISTS deporte");
        db.execSQL("DROP TABLE IF EXISTS entrenamiento");
        db.execSQL("DROP TABLE IF EXISTS objeto_entrenamiento");
        db.execSQL("DROP TABLE IF EXISTS coordenadas_entrenamiento");
        db.execSQL("DROP TABLE IF EXISTS categoria_entrenamiento");
        db.execSQL("DROP TABLE IF EXISTS alineacion");
        db.execSQL("DROP TABLE IF EXISTS coordenadas_alineacion");
        // Create tables again
        onCreate(db);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimino la base de datos si existe
        db.execSQL("DROP TABLE IF EXISTS deporte");
        db.execSQL("DROP TABLE IF EXISTS entrenamiento");
        db.execSQL("DROP TABLE IF EXISTS objeto_entrenamiento");
        db.execSQL("DROP TABLE IF EXISTS coordenadas_entrenamiento");
        db.execSQL("DROP TABLE IF EXISTS categoria_entrenamiento");
        db.execSQL("DROP TABLE IF EXISTS alineacion");
        db.execSQL("DROP TABLE IF EXISTS coordenadas_alineacion");
        // Create tables again
        onCreate(db);
    }

    public List<CategoriaEntrenamiento> obtenerCategorias(Deporte d){
        List<CategoriaEntrenamiento> categorias = new ArrayList<CategoriaEntrenamiento>();
        // Select All Query
        String selectQuery = "SELECT  * FROM categoria_entrenamiento where id_deporte="+d.getId_deporte();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(new CategoriaEntrenamiento(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),d));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }
    public void crearCategoria(CategoriaEntrenamiento c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_cat", c.getId_cat());
        values.put("nombre", c.getNombre());
        values.put("descripcion", c.getDescripcion());
        values.put("id_deporte", c.getDeporte().getId_deporte());
        db.insert("categoria_entrenamiento", null, values);
        db.close(); // Closing database connection
    }
    public void crearDeporte(Deporte d){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_deporte", d.getId_deporte());
        values.put("deporte", d.getNombre());
        values.put("fondo", d.getFondo());
        values.put("campo", d.getCampo());
        db.insert("deporte", null, values);
        db.close(); // Closing database connection
    }
    public String obtenerFondoDeporte(String deporte){
        String devolver=null;
        String selectQuery = "SELECT  fondo FROM deportefutbol where deporte='"+deporte+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("deportefutbol",new String[] {"fondo"} , "deportefutbol" + "=?",
                new String[] { String.valueOf(deporte) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        devolver=(cursor.getString(0));
        return devolver;
    }
    public List<Deporte> obtenerDeportes(){
        List<Deporte> contactList = new ArrayList<Deporte>();
        // Select All Query
        String selectQuery = "SELECT  * FROM deporte";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                contactList.add(new Deporte(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
/*
    // Adding new contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
*/
}