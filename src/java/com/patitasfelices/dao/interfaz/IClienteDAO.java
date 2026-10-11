package com.patitasfelices.dao.interfaz;

import com.patitasfelices.modelo.Cliente;

public interface IClienteDAO {
    int registrar(Cliente cliente);              // retorna id generado
    Cliente buscarPorDocumento(String tipoDoc, String numDoc);
    Cliente buscarPorId(int idCliente);
    void actualizar(Cliente cliente);             // nuevo: para editar desde el panel admin


}
//actualizacion de la version 2 metod actualizar