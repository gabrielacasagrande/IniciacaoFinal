package com.example.mybusguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class TelaCadastro extends AppCompatActivity {

    private TextInputEditText campoNome, campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);


        //Inicializar Componentes
        campoNome = findViewById(R.id.EditCadastroNome);
        campoEmail = findViewById(R.id.EditCadastroEmail);
        campoSenha = findViewById(R.id.EditCadastroSenha);

    }

    public void ValidarCadastroUsuario (View view)
    {
        //Recuperar texto dos Campos
        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Verificar se os campos foram preenchidos
        if (!textoNome.isEmpty())
        {
            if (!textoEmail.isEmpty())
            {
                if (!textoSenha.isEmpty())
                {
                    Usuario usuario = new Usuario();
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);

                    CadastrarUsuario(usuario);

                }
                else
                {
                    Toast.makeText(TelaCadastro.this, "preencha Senha ", Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
                Toast.makeText(TelaCadastro.this, "preencha Email", Toast.LENGTH_SHORT).show();

            }
        }
        else
        {
            Toast.makeText(TelaCadastro.this, "preencha Nome", Toast.LENGTH_SHORT).show();
        }



    }

    public void CadastrarUsuario(final Usuario usuario)
    {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    try {
                        String idUsuario= task.getResult().getUser().getUid();
                        usuario.setId(idUsuario);
                        usuario.Salvar();
                        //Atualizar o nome de usuario
                        UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());
                        Toast.makeText(TelaCadastro.this, "Sucesso ao cadastrar", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TelaCadastro.this,MainActivity.class));
                        finish();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else
                {
                    String execao ="";
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e ){
                        execao ="Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        execao = "Digite um email Valido ";
                    }catch (FirebaseAuthUserCollisionException e){
                        execao="Essa conta já foi cadastrada: " + e.getMessage();
                    }catch (Exception e){
                        execao="Erro ao cadastrar usuario: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(TelaCadastro.this,execao,Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}