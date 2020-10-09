package com.example.mybusguide;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();
    }

    public static boolean atualizarNomeUsuario(String nome){
                try {
                    FirebaseUser user  = getUsuarioAtual();
                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nome)
                            .build();
                    user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Log.d("perfil","Erro ao atualizar o nome do perfil");
                        }
                        }
                        });
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }

           public static void redirecionarusuariologado(final Activity activity){

                   FirebaseUser user = getUsuarioAtual();
                   if (user != null){
                       DatabaseReference usuariosref = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios").child(getIndentificadorUsuario());
                       usuariosref.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               Usuario usuario = dataSnapshot.getValue(Usuario.class);
                               String email = usuario.getEmail();
                               String senha = usuario.getSenha();


                                  Intent i = new Intent(activity,Home.class);
                                  activity.startActivity(i);



                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });

                   }


            }
            public static String getIndentificadorUsuario(){
              return getUsuarioAtual().getUid();
              }

    }





