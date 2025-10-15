package com.financemodel.financepro.backend.datawrapplers;

import java.util.UUID;

public class User
{
    String username;
    String password;
    char sexo;
    float renda;
    UUID uuid;

    User()
    {

    }

    /**
     * Construtor da classe User, usado para construir e manipular dados de usuarios importados
     * Da query do banco de dados, de form mais facil
     *
     * @param username nome do usuario (String)
     * @param password senha do usuario (String)
     * @param sexo sexo do usuario (char de length=2)
     * @param renda renda do usuario (float)
     * @param uuid uuid do usuario (uuid)
     */
    User(String username, String password,char sexo,float renda,UUID uuid)
    {
        this.username = username;
        this.password = password;
        this.sexo = sexo;
        this.renda = renda;
        this.uuid = uuid;
    }
    public boolean checkValidSexo(char sexo)
    {
        if(sexo == 'M' || sexo == 'F')
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public char getSexo()
    {
        return sexo;
    }

    public void setSexo(char sexo)
    {
        if(this.checkValidSexo(sexo) == true)
        {
            this.sexo = sexo;
        }
        else
        {
            throw new Error("VALOR DE GENERO INVALIDO");
        }
    }

    public float getRenda() {
        return renda;
    }

    public void setRenda(float renda) {
        this.renda = renda;
    }
}
