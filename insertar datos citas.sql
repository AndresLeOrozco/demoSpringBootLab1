use projectocitas;

INSERT INTO `projectocitas`.`perfiles`
(`perfil_id`, `perfil_nombre`) VALUES 
(1, 'anonimo'),
(2, 'admin'),
(3, 'medico'),
(4, 'paciente')
;

INSERT INTO `projectocitas`.`permisos`
(`permisos_id`, `permisos_nombre`) VALUES 
(1, 'WelcomePage'),
(2, 'RegisterPage'),
(3, 'LoginPage'),
(4, 'BookingPage'),
(5, 'AccessApprovalPage'),
(6, 'MeetingSearch'),
(7, 'ProfilePage');

INSERT INTO `projectocitas`.`permisosperfil`
(`perfil_id`,
`permiso_id`)
VALUES
(1,1)
,(1,2)
,(2,5)
,(3,3)
,(3,7)
,(2,3)
,(4,1)
,(4,4)
,(4,6)
;

INSERT INTO `projectocitas`.`usuario`
(`usuario_id`,
`nombre`,
`apellido`,
`clave`,
`perfil_id`, 
`estado`)
VALUES
('111111111'
,'Admin'
,'Local'
,'PWD123'
,2
,'Activo');

INSERT INTO `projectocitas`.`especialidad`
(`especialidad_nombre`)
VALUES
('Alergología')
,('Anestesiología y reanimación')
,('Aparato digestivo')
,('Cardiología')
,('Endocrinología y nutrición')
,('Geriatría')
,('Hematología y hemoterapia')
,('Medicina de la educación física y del deporte')
,('Medicina espacial')
,('Medicina intensiva')
,('Medicina interna')
,('Medicina legal y forense')
,('Medicina preventiva y salud pública')
,('Medicina del trabajo')
,('Nefrología')
,('Neumología')
,('Neurología')
,('Neurofisiología Clínica')
,('Oncología médica')
,('Oncología radioterápica')
,('Pediatría')
,('Psiquiatría')
,('Rehabilitación')
,('Reumatología')
,('Medicina familiar y comunitaria')
,('Biomedicina');

INSERT INTO `projectocitas`.`localidad`
(`localidad_nombre`)
VALUES
('Ulloa')
,('Mercedez')
,('Uruca');
















