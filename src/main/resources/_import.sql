-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 03, 2013 at 10:20 AM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `cpr`
--

--
-- Dumping data for table `address`
--

INSERT INTO `authority` (`id`, `changed`, `code`, `created`, `enabled`, `name`, `id_user_changed_by`, `id_user_created_by`, `long_description`, `short_description`) VALUES
(1, NULL, 'ROLE_ADMIN', '2012-12-01 00:00:00', 1, 'Editor', NULL, NULL, '<p>Uživatelská role s omezenými právy.</p>\r\n					 	<ul>\r\n					 		<li>Uživatel s přidělenou rolí Editor má plný přístup do sekce CPR.</li>\r\n					 		<li>Má možnost přidávat, editovat, publikovat a odstraňovat aktuality</li>\r\n					 		<li>Má možnost manipulovat se základním nastavením systému.</li>\r\n					 	</ul> ', 'Editor má plný přístup do sekce CPR, nemá právo přidávat / editovat uživatelů.'),
(2, NULL, 'ROLE_EDITOR', '2012-12-01 00:00:00', 1, 'Registrovaný uživateľ', NULL, NULL, NULL, NULL),
(3, NULL, 'ROLE_WEBMASTER', NULL, 1, 'Administrátor', NULL, NULL, '<p>Administrátor má právo na všechny operace v systému.</p>\r\n					 	<ul>\r\n					 		<li>uživatel s rolí administrátor má možnost registrovat nových uživatelů systému, upravovat již existující účty, nebo jejich odstranit</li>\r\n					 		<li>Může kontrolovat uživatelské přístupy</li>\r\n					 		<li>Má možnost manipulovat s nastavením systému</li>\r\n					 	</ul>', 'Administrátor má právo na všechny operace v systému. Má možnost blokovat uživatelů, měnit hesla, registrovat nových.');

--

INSERT INTO `users` (`id`, `changed`, `code`, `created`, `enabled`, `change_password_request_date`, `change_password_request_token`, `email`, `first_name`, `last_name`, `password`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, NULL, '2012-12-05 00:00:00', 1, NULL, NULL, 'cpk@azet.sk', 'Peter', 'Jurkovič', 'a0a823cc5010c05365d4cf9d5fc42270f318252b', NULL, 1),
(9, NULL, NULL, '2013-01-26 10:38:54', 1, NULL, NULL, 'email@peterjurkovic.sk', 'Peter', 'Jurkovič', 'a0a823cc5010c05365d4cf9d5fc42270f318252b', NULL, 1);

--
-- Dumping data for table `users_log`
--

INSERT INTO `user_has_authority` (`user_id`, `authority_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(9, 3);



INSERT INTO `address` (`id`, `changed`, `code`, `created`, `enabled`, `city`, `street`, `zip`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, NULL, '2013-01-12 10:24:01', 1, '', '', '', NULL, NULL),
(3, NULL, NULL, '2013-01-12 11:17:05', 1, '', '', '', NULL, NULL),
(4, NULL, NULL, '2013-01-12 11:17:24', 1, '', '', '', NULL, NULL),
(5, NULL, NULL, '2013-01-12 11:18:17', 1, '', '', '', NULL, NULL),
(6, NULL, NULL, '2013-01-26 14:10:45', 1, '', '', '', NULL, NULL);

--
-- Dumping data for table `assessment_system`
--

INSERT INTO `assessment_system` (`id`, `changed`, `code`, `created`, `enabled`, `system_code`, `description`, `name`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, NULL, '2013-01-12 10:17:24', 1, '1+', '', 'System 1+', NULL, 1),
(2, NULL, NULL, '2013-01-12 17:48:34', 1, '2+', '', 'System 2+', NULL, 1);

--
-- Dumping data for table `authority`
--

-- Dumping data for table `basic_settings`
--

INSERT INTO `basic_settings` (`id`, `changed`, `code`, `created`, `enabled`, `google_analytics_code`, `owner_name`, `csn_online_url`, `main_system_email`, `system_name`, `id_user_changed_by`, `id_user_created_by`, `header_title`) VALUES
(1, '2013-02-02 16:06:42', NULL, NULL, NULL, '', 'ITC - Institut pro testování a certifikace', 'http://www.sgpstandard.cz/editor/files/on_line/csn-redirect.php?k={0}', 'email@example.com', 'Informační systém CPR', 1, NULL, 'Uvádění stavebních výrobků na trh');

--
-- Dumping data for table `country`
--

INSERT INTO `country` (`id`, `changed`, `code`, `created`, `enabled`, `country_name`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, 'cr', '2013-01-12 10:15:46', 1, 'Česká republika', NULL, 1),
(2, NULL, 'sr', '2013-01-12 10:15:55', 1, 'Slovenská republika', NULL, 1),
(3, NULL, 'au', '2013-01-12 10:16:03', 1, 'Rakúsko', NULL, 1);

--
-- Dumping data for table `csn`
--

INSERT INTO `csn` (`id`, `changed`, `code`, `created`, `enabled`, `csn_name`, `csn_online_id`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, NULL, '2013-01-13 21:23:51', 1, NULL, NULL, NULL, 1),
(2, NULL, NULL, '2013-01-13 21:27:33', 1, NULL, NULL, NULL, 1);

--
-- Dumping data for table `mandate`
--

INSERT INTO `mandate` (`id`, `changed`, `code`, `created`, `enabled`, `mandate_file_url`, `mandate_name`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, NULL, '2013-01-12 10:16:53', 1, 'https://github.com/cpk', 'M101', NULL, 1),
(2, NULL, NULL, '2013-01-12 10:16:59', 1, 'https://github.com/cpk', 'M100', NULL, 1),
(3, NULL, NULL, '2013-01-12 10:17:07', 1, '', 'M123', NULL, 1);

--
-- Dumping data for table `notified_body`
--

INSERT INTO `notified_body` (`id`, `changed`, `code`, `created`, `enabled`, `description`, `email`, `is_eta_certification_allowed`, `fax`, `name`, `notified_body_code`, `phone`, `web`, `id_user_changed_by`, `id_user_created_by`, `address_id`, `country_id`) VALUES
(1, NULL, 'strojirensky-zkusebni-ustav-s-p-', '2013-01-12 10:24:01', 1, '', '', 0, '', 'STROJIRENSKY ZKUSEBNI USTAV s.p.', 'AO 202 (NO 1015)', '', '', NULL, 1, 1, 1),
(3, NULL, 'itc-zlin', '2013-01-12 11:17:05', 1, '', '', 0, '', 'ITC Zlin', 'AO 252', '', '', NULL, 1, 3, 1),
(4, NULL, 'testin', '2013-01-12 11:17:24', 1, '', '', 0, '', 'TESTIN', '988', '', '', NULL, 1, 4, 2),
(5, NULL, 'test', '2013-01-12 11:18:17', 1, '', '', 0, '', 'test', 'aaa', '', '', NULL, 1, 5, 3),
(6, NULL, 'astrojirensky-zkusebni-ustav-s-p-', '2013-01-26 14:10:45', 1, '', '', 0, '', 'aSTROJIRENSKY ZKUSEBNI USTAV s.p.', '52', '', '', NULL, 9, 6, 1);



INSERT INTO `standard_group` (`id`, `changed`, `code`, `created`, `enabled`, `comission_decision_file_url`, `description`, `group_code`, `grup_name`, `url_title`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, 'prefabrikovane-vyrobky-z-obycejneho-lehkeho-betonu-a-autoklavovaneho-porobetonu', '2013-01-12 10:16:36', 1, '', '', '585', 'PREFABRIKOVANÉ VÝROBKY Z OBYČEJNÉHO/LEHKÉHO BETONU A AUTOKLÁVOVANÉHO PÓROBETONU', '', NULL, 1),
(2, NULL, 'prefabrikovane-vyrobky-z-obycejneho-lehkeho-2', '2013-01-13 16:12:52', 1, '', '', '', 'PREFABRIKOVANÉ VÝROBKY Z OBYČEJNÉHO/LEHKÉHO 2', '', NULL, 1);

--
-- Dumping data for table `standard_has_assessment_system`
--



INSERT INTO `standard` (`id`, `changed`, `code`, `created`, `enabled`, `replaced_standard_id`, `standard_id`, `standard_name`, `start_concurrent_validity`, `start_validity`, `stop_concurrent_validity`, `stop_validity`, `text`, `id_user_changed_by`, `id_user_created_by`, `standard_group_id`) VALUES
(1, '2013-01-13 14:57:03', 'en-20525l-252', '2013-01-12 10:17:53', 1, '', 'EN 20525L:252', 'Norma xy', NULL, NULL, NULL, NULL, '<p><em>Ahoj 2<br /></em></p>', 1, 1, NULL),
(2, '2013-01-19 17:44:58', 'en-205256l-252', '2013-01-13 15:48:22', 1, '', 'EN 205256L:252', 'Ceska norma bala', NULL, NULL, NULL, NULL, '', 1, 1, 1),
(3, '2013-01-19 13:55:36', 'en-23525l-252', '2013-01-13 16:25:33', 1, '', 'EN 23525L:252', 'Ceska norma bala', NULL, NULL, NULL, NULL, '<p>96</p>', 1, 1, 1),
(4, '2013-01-17 20:12:55', 'en-23525l-25299', '2013-01-13 17:13:36', 0, '', 'EN 23525L:25299', 'Ceska norma bala', NULL, NULL, NULL, NULL, NULL, 1, 1, 2),
(5, '2013-01-27 14:04:28', 'test-x', '2013-01-18 15:43:29', 0, '', 'test x', 'Ceska norma bala', NULL, '2013-01-01 23:00:00', NULL, '2013-01-01 23:00:00', '<p>9959</p>', 1, 1, 1);


--
-- Dumping data for table `standard_has_csn`
--
INSERT INTO `standard_has_assessment_system` (`standard_id`, `notified_body_id`) VALUES
(5, 2);


INSERT INTO `standard_has_csn` (`id`, `changed`, `code`, `created`, `enabled`, `note`, `id_user_changed_by`, `id_user_created_by`, `csn_id`, `standard_id`, `csn_name`, `csn_online_id`) VALUES
(2, NULL, NULL, '2013-01-13 21:23:51', 1, NULL, NULL, 1, 1, 4, '', NULL),
(3, NULL, NULL, '2013-01-13 21:27:33', 1, NULL, NULL, 1, 2, 4, '', NULL);

--
-- Dumping data for table `standard_has_mandate`
--

INSERT INTO `standard_has_mandate` (`standard_id`, `mandate_id`) VALUES
(1, 2),
(1, 3),
(5, 3);

--
-- Dumping data for table `standard_has_notified_body`
--

INSERT INTO `standard_has_notified_body` (`standard_id`, `notified_body_id`) VALUES
(1, 4),
(5, 5);



-- Dumping data for table `webpage_category`
--

INSERT INTO `webpage_category` (`id`, `changed`, `code`, `created`, `enabled`, `name`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, NULL, NULL, 1, 'Hlavni menu ', NULL, NULL);

--
-- Dumping data for table `webpage_content`
--

INSERT INTO `webpage_content` (`id`, `changed`, `code`, `created`, `enabled`, `name`, `id_user_changed_by`, `id_user_created_by`) VALUES
(1, NULL, NULL, NULL, 1, 'Jen text  (Horní obsah sekce  + Dolní obsah sekce)', NULL, NULL);


INSERT INTO `webpage` (`id`, `changed`, `code`, `created`, `enabled`, `bottom_text`, `description`, `keywords`, `name`, `timestamp`, `title`, `top_text`, `id_user_changed_by`, `id_user_created_by`, `webpage_category_id`, `webpage_content_id`) VALUES
(1, '2013-02-02 16:09:19', '/', '2013-02-02 10:19:48', 1, '', '', NULL, 'O portálu', NULL, 'O portálu', '<h1>Vitejte v inrormačn&iacute;m syst&eacute;mu uvaden&iacute; stavebn&iacute;ch v&yacute;robk&uacute; na trh</h1>\r\n<p>čia, keď nezn&aacute;my tlačiar zobral sadzobnicu pln&uacute; tlačov&yacute;ch znakov a pomie&scaron;al ich, aby tak vytvoril vzorkov&uacute; knihu. Prežil nielen p&auml;ť storoč&iacute;, ale aj skok do elektronickej sadzby, a pritom zostal v podstate nezmenen&yacute;. Spopularizovan&yacute; bol v 60-tych rokoch 20.storočia, vydan&iacute;m h&aacute;rkov Letraset, ktor&eacute; obsahovali pas&aacute;že Lorem Ipsum, a nesk&ocirc;r aj publikačn&yacute;m softv&eacute;rom ako Aldus PageMaker, ktor&yacute; obsahoval verzie Lorem Ipsum.</p>', 1, 1, 1, 1),
(2, NULL, '/aktuality', '2013-02-02 10:24:19', 1, '', '', NULL, 'Aktuality', NULL, 'Novnky v oblasti CPR', '', NULL, 1, 1, 1),
(3, NULL, '/uvadeni-vyrobku', '2013-02-02 10:26:53', 1, '', '', NULL, 'Uvádění výrobků', NULL, 'Uvádění stavebních výrobků na trh', '', NULL, 1, 1, 1),
(4, NULL, '/prehled-subjektu', '2013-02-02 10:27:46', 1, '', '', NULL, 'Přehled subjektů', NULL, 'Přehled subjektů', '', NULL, 1, 1, 1),
(5, NULL, 'vygenerovat-prohlaseni', '2013-02-02 10:28:53', 1, '', '', NULL, 'Vygenerovat prohlášení', NULL, 'Vygenerovat prohlášení (DoP)', '', NULL, 1, 1, 1);

--
