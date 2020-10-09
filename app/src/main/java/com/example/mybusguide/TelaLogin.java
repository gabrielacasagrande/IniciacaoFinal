package com.example.mybusguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class TelaLogin extends AppCompatActivity {

    private TextInputEditText campoEmail,campoSenha;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        //Inicializar componentes
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);

    }

    public void validarLoginUsuario(View view){

        //recuperar textos campos
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if (!textoEmail.isEmpty()){
            if (!textoSenha.isEmpty()){

                Usuario usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);
                LogarUsuario(usuario);

            }else {
                Toast.makeText(TelaLogin.this,"Preencha a Senha",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(TelaLogin.this,"Preencha o Email",Toast.LENGTH_SHORT).show();
        }
    }

    public void LogarUsuario(Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //   startActivity(new Intent(this,Home.class));
                    UsuarioFirebase.redirecionarusuariologado(TelaLogin.this);
                }else{
                    String execao ="";
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e ){
                        execao ="Usuario nao esta Cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        execao ="Email e senha nao correspondem";
                    }catch (Exception e){
                        execao="Senha Incorreta: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(TelaLogin.this,execao,Toast.LENGTH_SHORT).show();

                }
            }
        });

    }







}