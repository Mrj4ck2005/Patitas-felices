USE db_patitas_felices;
GO

-- Orden obligatorio por las FK: primero lo que depende de otras tablas
DELETE FROM tb_adopciones;
DELETE FROM tb_solicitudes;
DELETE FROM tb_mascotas;
DELETE FROM tb_clientes;

-- Reinicia los contadores IDENTITY para que el proximo registro
-- empiece de nuevo en id = 1 (si no haces esto, seguiria en, por ejemplo, id_mascota = 15)
DBCC CHECKIDENT ('tb_adopciones', RESEED, 0);
DBCC CHECKIDENT ('tb_solicitudes', RESEED, 0);
DBCC CHECKIDENT ('tb_mascotas', RESEED, 0);
DBCC CHECKIDENT ('tb_clientes', RESEED, 0);

PRINT 'Tablas limpiadas. tb_administradores se dejo intacta (tu login sigue funcionando).';