INSERT INTO  users(id , firstname , lastname , password , user_role) VALUES
                                                                        ('200107112' , 'Imangali' , 'Kaliakhmetov' , '$2a$10$gbSDE3D6qfBfFMBc6N8SRe5wjAN6Db0kB4QYTsX4xnF3/hqgOX9nq' , 'ADMIN'),
                                                                        ('200107113', 'Kydyrali' , 'Sagyndyk' , '$2a$10$JrH1DOH/dyAm80m76IM6B.peCb1Z1DER/yuK3rIH7ynGG/hZRGEIS' , 'STUDENT'),
                                                                        ('200107115', 'Amandek' , 'Baktybay', '$2a$10$AN.ObvSAKtGbpdIhRY8J8eY5sw6meGNZX3tcVnRMIK7VmsldDTIMG' , 'TEACHER')
    ON CONFLICT DO NOTHING;;