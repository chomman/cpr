
INSERT INTO `authority` (`id`, `changed`, `code`, `created`, `enabled`, `description`, `name`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, 'ROLE_ADMIN', '2012-12-01 00:00:00', 1, 'Má práva na všetko.', 'Administrátor', NULL, NULL),
(2, NULL, 'ROLE_USER', '2012-12-01 00:00:00', 1, '', 'Registrovaný uživateľ', NULL, NULL);

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `changed`, `code`, `created`, `enabled`, `change_password_request_date`, `change_password_request_token`, `email`, `first_name`, `last_name`, `password`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, NULL, '2012-12-05 00:00:00', 1, NULL, NULL, 'cpk@azet.sk', 'Peter', 'Jurkovič', 'd033e22ae348aeb5660fc2140aec35850c4da997', NULL, NULL);

--
-- Dumping data for table `user_has_authority`
--

INSERT INTO `user_has_authority` (`user_id`, `authority_id`) VALUES
(1, 1),
(1, 2);
