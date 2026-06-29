
INSERT INTO roles (id, nombre) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, nombre) VALUES (2, 'ROLE_ADMIN');


INSERT INTO usuarios (id, nombres_completos, correo, contrasena, activo, creado_en) VALUES (1, 'Ana Torres', 'ana@finai.com', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', true, now());

INSERT INTO usuarios (id, nombres_completos, correo, contrasena, activo, creado_en) VALUES (2, 'Admin FinAI', 'admin@finai.com', '$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', true, now());

INSERT INTO usuario_roles (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuario_roles (usuario_id, rol_id) VALUES (2, 2);


INSERT INTO cuentas (id, saldo, usuario_id) VALUES (1, 1750.00, 1);
INSERT INTO cuentas (id, saldo, usuario_id) VALUES (2, 0.00, 2);

INSERT INTO movimientos (id, tipo, monto, descripcion, creado_en, usuario_id) VALUES (1, 'INGRESO', 2000.00, 'Sueldo Mayo', now(), 1);
INSERT INTO movimientos (id, tipo, monto, descripcion, creado_en, usuario_id) VALUES (2, 'GASTO', 150.00, 'Supermercado Plaza Vea', now(), 1);
INSERT INTO movimientos (id, tipo, monto, descripcion, creado_en, usuario_id) VALUES (3, 'GASTO', 45.50, 'Almuerzo UPC', now(), 1);


INSERT INTO presupuestos (id, nombre_presupuesto, monto_promedio_mensual, categoria, tipo, creado_en, usuario_id) VALUES (1, 'Alimentación', 600.00, 'Alimentación', 'MENSUAL', now(), 1);
INSERT INTO presupuestos (id, nombre_presupuesto, monto_promedio_mensual, categoria, tipo, creado_en, usuario_id) VALUES (2, 'Transporte', 200.00, 'Transporte', 'MENSUAL', now(), 1);


INSERT INTO metas_ahorro (id, nombre_meta, monto_objetivo, monto_actual, fecha_limite, descripcion, estado, usuario_id) VALUES (1, 'Laptop Nueva', 3500.00, 500.00, current_date + 90, 'Ahorro para estudios', 'ACTIVA', 1);


INSERT INTO articulos_recomendacion (id, titulo, descripcion_corta, url_enlace, categoria) VALUES (1, 'Guía de Impuestos 2026', 'Todo sobre la declaración anual SUNAT.', 'https://sunat.gob.pe', 'FISCAL');
INSERT INTO articulos_recomendacion (id, titulo, descripcion_corta, url_enlace, categoria) VALUES (2, 'Calendario Tributario', 'Fechas límite de pago para personas naturales.', 'https://sunat.gob.pe/cronograma', 'FISCAL');
INSERT INTO articulos_recomendacion (id, titulo, descripcion_corta, url_enlace, categoria) VALUES (3, 'Deducciones de Cuarta Categoría', 'Cómo pagar menos impuestos legalmente.', 'https://www.gob.pe', 'FISCAL');


INSERT INTO articulos_recomendacion (id, titulo, descripcion_corta, url_enlace, categoria) VALUES (4, 'Inversión en Bolsa de Valores', 'Cómo empezar a comprar acciones en la BVL.', 'https://bvl.com.pe', 'INVERSION');
INSERT INTO articulos_recomendacion (id, titulo, descripcion_corta, url_enlace, categoria) VALUES (5, 'Fondos Mutuos 101', 'Rentabilidad y seguridad para tus ahorros.', 'https://comparabien.com.pe', 'INVERSION');
INSERT INTO articulos_recomendacion (id, titulo, descripcion_corta, url_enlace, categoria) VALUES (6, 'Depósitos a Plazo Fijo', 'Las mejores tasas de interés en bancos peruanos.', 'https://bcrp.gob.pe', 'INVERSION');


INSERT INTO tickets_soporte (id, asunto, mensaje, estado, creado_en, usuario_id) VALUES (1, 'Duda con presupuesto', 'No puedo editar el monto.', 'ABIERTO', now(), 1);


SELECT setval('roles_id_seq', (SELECT MAX(id) FROM roles));
SELECT setval('usuarios_id_seq', (SELECT MAX(id) FROM usuarios));
SELECT setval('movimientos_id_seq', (SELECT MAX(id) FROM movimientos));
SELECT setval('presupuestos_id_seq', (SELECT MAX(id) FROM presupuestos));
SELECT setval('articulos_recomendacion_id_seq', (SELECT MAX(id) FROM articulos_recomendacion));

INSERT INTO tickets_soporte (id, asunto, mensaje, estado, creado_en, usuario_id)
VALUES (1, 'Problema con Login', 'No puedo entrar desde mi celular', 'ABIERTO', now(), 1);