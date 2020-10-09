package com.example.mybusguide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static DatabaseReference database;
    private static FirebaseAuth Auth;

    //Retorna a Instancia do FireBase
    public static DatabaseReference getFirebaseDatabase()
    {
            if ( database == null)
            {
                database = FirebaseDatabase.getInstance().getReference();
            }
        return database;
    }

    //Retorna a Instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao()
    {
        if ( Auth == null)
        {
            Auth = FirebaseAuth.getInstance();
        }
        return Auth;

    }

}
